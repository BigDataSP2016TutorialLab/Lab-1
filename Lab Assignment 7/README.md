# BigDataApps-Spring2016-LabAssignments
##V Vamsi Krishna Bhuvanam

Lab Assignment 5 and 6 :

##Question: 1
###Spark and Smartphone/Watch Application

Implement a smart application with big data analytics related to your project showing the collaboration between Spark and Smart Apps. Implement Twitter Streaming and perform word count on it and publish the results and showcase it in your Smart Phone/Watch Application.Implement MapReduce algorithm for finding Facebook common friends problem and run the Map Reduce job on Apache Hadoop. Write a report including your algorithm and result screenshots.

##Brief Description about your implementation : 

This assignment can be approached by developing two different applications. One is an android application which will run in mobile device in a network and another application will run in IntelliJ application in desktop which will run in the same network.

The android application will start as a socket client where it will create a port and assign to the application and will wait for incoming connection. Then if we run IntelliJ application with the IP address same as mobile, then the application will connect to mobile and will send the twitter word count data output to mobile in form of streaming until the application was terminated.


##Question: 2
###Spark ML Lib Application

Perform a machine learning algorithm with the Twitter Streaming data to categorize each Tweet
1) Training datasets: Collect different categories of Tweets related to your project.(Categories can be based on HashTags /Subjects etc.) 
2) Test data: The upcoming twitter stream
