***Caption Tagger***

Caption tagger is a simple scala application which receives on its entry a file with a list of Youtube videos' ids, downloads their captions, selects nouns and looks for their articles on Wikipedia.

The end result is a file with a list that contains: video tokens, raw captions, plain captions, raw wikipedia articles, plain wikipedia articles, links to wikipedia articles.

Used libraries: play-json (for JSON operations), os-lib (for file operations), scalaj-http (for http request operations) and scalatest (for tests).


**Resources**

The applications uses files which are grouped into three folders

*examples*

This folder contains the entry files (the once that have a list of Youtube tokens).

*logs*

This folder contains a file named logs.txt that gathers all exception reports that were caught (wrong Youtube token, word that is ambigous/may reffer to many articles, word that does not posses an article). 

*results*

This folder contains a file results.json where the end result list of (token, raw captions, plain captions, raw article, plain article, links) is written. I have decided to use this format because it is both readable for a human and easily processable for a computer.

**Code**
 
 The code is based on 8 Scala objects.
 
 *Configuration*
 
 This objects contains constants that are used in the application, includes: paths, urls, pointers, atom-like strings and others.
 
 *ExceptionLogger*
 
This object is responsible for reporting exceptions which occured during app's operations. Reports are written into the logs.txt file which is placed in the logs folder.
 
 *CaptionTagger*
 
CaptionTagger is the main app's object. That's the place where `main` function is called. At first the app makes sure, that the logs.txt file does not exists (in order to log each session seprarately), then the file name is taken from the keyboard. If a file with given name does not exists in the examples folder an exception is written to the console.
 
 *EntryFileReader*
 
 The object is responsible for getting the Yt's tokens in the given file. The each file line is streamed and processed by the CaptionManager seprately.
 
 *CaptionManager*
 
CaptionManager is the object which makes a request to the youtube API, maps the result into the list of strings, then ask the NounChecker to select the nouns and passes the noun list to the ResultFileCreator.

The http request is made in the `makeRequestAndManageResponse` function. The response body is then passed to the `processResponseBody` fuction, where the plain captions are tried to be extracted (if the video exists they are placed in the fields called "utf8"). Then the result if filtered from \n and " chars and the potential nouns are marked. Then by using the `NounChecker.filterNouns` function nouns are selected and the following parameters are passed to the `ResultFileCreator` object: (token, raw captions, plain captions, list of nouns). If a video with certain token does not exists an exception is reported.
 
 *NounChecker*
 
 The object is responsible for all noun-related operations. The object uses only some simplified metrics to determine whether a word is a noun or not. At first I wanted to use some internet dictionary API, however the ones I found were either request limited or required paid substribtion. Another more reliable way of finding a noun is using a local dictionary database. 
 
 Function `markPotentialNouns` marks words that are preceded by the articles or personal pronouns as potential nouns.
 
 Function `filterNouns` filters words that end with noun endings and are marked as potential nouns barring they: do not end with an adverb ending, are not passive versions of the verbs, are not words like same/next which occur quite often after the definite article. 
 
 *ResultFileCreator*
 
 The object is responsible for: gathering the wikipedia's articles for the given nouns, writing the results into the results.json file.
 
 The first task is fullfiled in the `apply` function as foreach loop assings a wikipedia request result to the each noun. If the wikipedia request went alright the whole result is written into the file by function `saveResults`. If an exception occured in the process a report is written into the logs.txt file. 
 
 In the `saveResults` function given results are mapped into a JSON object and written into the result file.
 
 *WikipediaManager*
 
 The object is responsible for making a http request to the wikipedia API and then processing the response. 
 
 The request is made `apply` function. The result is passed to the `processWikipediaResponse` function.
 
 The response is parsed into a JSON format. If an exception was thrown it means that there was no wikipedia article for this word.
 If the field name `type` in the response containes a value `disambiguation` it means that the word refferred to multiple articles. 
 If everything went alright links and plain article is extracted from the response and returned.
 
 The object returns a tuple which contains (success marker, links, plain article, raw article)

**Tests**

*NounCheckerTest*

The test `Filter noun-like words` checks whether `NounChecker.filterNouns()` function selects words which may be nouns (ex. posses a noun ending, contains a noun marker).

The test `Mark potential nouns` checks if words that are preceded by possessive pronouns/articles are marked as potential nouns.

*WikipediaRequestTest*

The test `No wikipedia article` checks whether the fact that there is no article about the word 'ggf' exists is marked in the logs.

The test `Word's ambiguity` checks whether the fact that there is no specific article about the 'great' word is marked in the logs. (as there is e.x. an article about great britain, great depression,...)


*YoutubeRequestTest*

The test `Wrong youtube token` checks whether the fact that there is no youtube video with a token 'qwerty' is marked in the logs.
