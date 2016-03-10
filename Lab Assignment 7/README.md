# BigDataApps-Spring2016-LabAssignments
##V Vamsi Krishna Bhuvanam | Class ID: 3
##Vikas Kondapalli | Class ID: 11

Lab Assignment 7 :

##Question: 1
###Sentimental analysis using twitter streaming (related to your project)

##Brief Description about your implementation : 

The task here was to perform sentiment analysis on Twitter streaming data. I performed the following steps to complete the task.
1.	Create Twitter stream using Twitter4j package.
2.	Filter the Tweets where the language is English.
3.	Extract only the Tweet text from the Tweet.
4.	For every Tweet text perform sentiment analysis using the Stanford NLP class. 
5.	Output the Tweet Text along with the sentiment of the Tweet text.


##Question: 2
###Make recommendations (related to your own project) 
a.Training Data:the Twitter Streaming/categorized data (The categorization here would be from your previouslab 5&6). 
b.Testing Datasete.g., UserId, Category, Rating
c.Therating based on sentiment analysis, retweet count would be interesting.
d.Expected outcome is to make a recommendationbased on user profile (e.g., preferences, location,gender, age)

##Brief Description about your implementation : 

This task was to suggest recommendations to the user based on his ratings for music genres. The following steps were performed to complete the task.
1.	Create training data through Twitter stream filtering and classification of data.
2.	Manually formatting the collected data into the desired format.
3.	Performing collaborative filtering on the training data to train the model.
4.	Get sample data from Twitter to serve as test data.
5.	Perform recommendation for the test data


##Question: 3
###Twitter trend notification to smartphone/smartwatch

##Brief Description about your implementation : 

The task here was to send the recommendations to phone through socket connection.
The following steps were performed to accomplish the task.
1.	Create a socket server at Android phone side.
2.	Analyze the tweet using sentiment analysis.
3.	Send the Analyzed sentiment data and tweet data to socket client.
4.	Display the received string on the UI and the Android client end. 


##Question: 4
###Searching or recommendationthrough smartphone/smartwatch

##Brief Description about your implementation : 

The task here was to send the recommendations to phone through socket connection.
The following steps were performed to accomplish the task.
5.	Create a socket server at Android phone side.
6.	Create a client socket for the recommendation process that provides recommendations.
7.	Send the recommendations as string over the socket from the client to the server.
8.	Display the received string on the UI and the Android client end. 

