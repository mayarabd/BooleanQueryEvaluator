package evaluator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by mayara on 2/3/17.
 */

public class BooleanQueryEvaluator extends InvertedIndex {

    /**
     * builds the document index
     */
    public BooleanQueryEvaluator(String indexFileName) {
        super(indexFileName);
    }

    /**
     * Processes a query into terms and gets its posting list from the index
     * @param query a string with a term AND term query
     * @return a map with the two terms and their posting lists
     */
    private List<List<Integer>> getTermsPostingList(String query) {
        //pre-process query
        //split query into tokens
        List<String> tokens = stringTokenizer(query);
        //normalize text to lower case
        tokens = stringNormalizer(tokens);
        //stem text
        tokens = stringStemmer(tokens);
        //stopwords removal
        tokens = stopwordsRemoval(tokens);

        //get posting list for terms
        List<List<Integer>> queryList = new ArrayList<>();
        for (String token : tokens) {
            if (invertedIndex.containsKey(token)) {
                List<Integer> postList = invertedIndex.get(token);
                queryList.add(postList);
            }
        }

        return queryList;
    }

    /**
     * Intersect two lists of Integers
     * @param postOne postOne posting list for termOne
     * @param postTwo posting list for termOne
     * @return a list with the intersection
     */
    private List<Integer> intersect(List<Integer> postOne, List<Integer> postTwo) {
        List<Integer> result = new ArrayList<>();

        int indexOne = 0;
        int indexTwo = 0;
        while (indexOne < postOne.size() && indexTwo < postTwo.size()) {
            //checks if the docId in list one is also in list two
            int docIdOne = postOne.get(indexOne);
            int docIdTwo = postTwo.get(indexTwo);
            if (docIdOne == docIdTwo) {
                result.add(docIdOne);
                indexOne++;
                indexTwo++;
            } else if (docIdOne < docIdTwo) {
                indexOne++;
            } else {
                indexTwo++;
            }
        }
        return result;
    }

    /**
     * Saves the result from evaluating a query
     * @param result a list with documents that contain both query terms
     */
    private void saveQueryResultToFile(List<Integer> result, String query) {
        try (FileWriter writer = new FileWriter("QueryResult.txt", true)) {
            writer.write(query + "\n");
            for (Integer docId : result) {
                writer.write(docId + ",");
            }
            writer.write("\n");
        } catch (IOException ioe) {
            System.out.println(Arrays.toString(ioe.getStackTrace()));
        }
    }

    /**
     * Evaluate a query of type term1 AND term2
     * @param query a string
     */
    public void evaluateQuery(String query) {
        //gets each term's posting list
        List<List<Integer>> queryList = getTermsPostingList(query);
        //gets list of documents that contains those terms
        List<Integer> postOne = queryList.get(0);
        List<Integer> postTwo = queryList.get(1);
        List<Integer> queryResult = intersect(postOne, postTwo);

        //save query result to file
        saveQueryResultToFile(queryResult, query);
    }


    public static void main(String[] args) {
        BooleanQueryEvaluator booleanQueryEvaluator = new BooleanQueryEvaluator("/Users/mayara/IdeaProjects/Test/src/documents.txt");
        boolean exit = false;
        while (!exit) {
            System.out.println("Enter query: ");
            Scanner input = new Scanner(System.in);

            String query = input.nextLine();
            booleanQueryEvaluator.evaluateQuery(query);

            System.out.println("Exit? : (yes or no)");

            String answer = input.nextLine();
            if (answer.equalsIgnoreCase("yes")) {
                exit = true;
            }
        }
    }
}
