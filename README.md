***Caption Tagger***

Caption tagger is a simple scala application which receives a file with a list of Youtube videos' ids, downloads their captions, selects nouns and looks for their articles on Wikipedia.
The end result is a file with a list that contains: video tokens, raw captions, plain captions, raw wikipedia articles, plain wikipedia articles, links to wikipedia articles.

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
 
 *ExceptionLogger*
 
 *CaptionTagger*
 
 *EntryFileOpener*
 
 *CaptionManager*
 
 *NounChecker*
 
 *ResultFileCreator*
 
 *WikipediaManager*
 

**Tests**
