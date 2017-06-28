# Boolean Query Evaluator

This program simulates a simple search engine. The user is prompted to enter a query of form term1 AND term2 and the program will retrieve the documents that contain the correct result.
It generates an Inverted Index and Query results. 

## Dependencies
* Stemmer library:[kstem-3.4.jar](https://sourceforge.net/projects/lemur/files/lemur/KrovetzStemmer-3.4/kstem-3.4.jar/download)

## Build

```
compile: javac -cp absolute_path_to_kstem-3.4.jar src/*.java
run:     java -cp absolute_path_to_kstem-3.4.jar:. evaluator.BooleanQueryEvaluator documents.txt
```

## Output

* InvertedIndex.txt - the index generated from documents.txt file
* QueryResult.txt - the list of documents ID that contain the term1 AND term2 query

##  Technologies
* Java
* IntelliJ IDEA 










