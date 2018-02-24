# IN4325 - Bing Search Api with Hints extension
The aim of the project is to create an experimental setup to determine the effects of providing users with hints when searching to find the answer to complex questions.
This allows us to reproduce the study as described in the following [paper](https://dl.acm.org/citation.cfm?id=2609523):
    
>Savenkov, D., Agichtein, E. (2014) To Hint or Not: Exploring the Effectiveness of Search Hints for Complex Informational Tasks. SIGIR ’14, July 6-11, 2014, Gold Coast, Australia

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management
* [Jooby](https://jooby.org/) - Web Framework for Java
* [Bing Web Search Api v7](https://azure.microsoft.com/en-us/services/cognitive-services/bing-web-search-api/) - For web searching

## Requirements
* Maven 
* JDK8+ 
* A Bing Web Search API Key

## Running the project
To run the project you can simple run the following command:  
```
mvn jooby:run -Dbing.apiKey=[YOUR KEY]
```

Make sure to replace YOUR KEY with your Bing Search API license key. 