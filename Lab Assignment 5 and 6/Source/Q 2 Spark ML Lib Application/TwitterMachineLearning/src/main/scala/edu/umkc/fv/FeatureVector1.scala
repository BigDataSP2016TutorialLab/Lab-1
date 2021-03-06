package edu.umkc.fv

import edu.umkc.fv.NLPUtils._
import edu.umkc.fv.Utils._
import org.apache.spark.SparkConf
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.twitter.TwitterUtils

/**
  * Created by Vamsi on 3/2/2016.
  */
object FeatureVector1 {

   def main(args: Array[String]) {
     System.setProperty("hadoop.home.dir", "D:\\winutils")

     val filters = args
     val Consumer_Key="XJPlyOuAo7tC8YBxE1GDiTq8n"
     val Consumer_Secret="Zeyv8qYR9u5wmOqvBStqLV8HMA1Qvh3R9d3vDj02gfpTfAzrgs"
     val Access_Token="143736380-2VpESDsrWMcOipe7zLIMBr0ncHwnDbMxjckpU7nT"
     val Access_Token_Secret="jEdqt83lwfuDO1rPFpUyGmS4OexheFFsru7CZEH9iqnss"
     System.setProperty("twitter4j.oauth.consumerKey", Consumer_Key)
     System.setProperty("twitter4j.oauth.consumerSecret", Consumer_Secret)
     System.setProperty("twitter4j.oauth.accessToken", Access_Token)
     System.setProperty("twitter4j.oauth.accessTokenSecret", Access_Token_Secret)


     val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark-Machine_Learning-Text-1").set("spark.driver.memory", "3g").set("spark.executor.memory", "3g")
     val ssc = new StreamingContext(sparkConf, Seconds(2))

     val stream = TwitterUtils.createStream(ssc, None, filters)
     stream.print()
     val hashTags = stream.flatMap(status => status.getText.split(" ").filter(_.startsWith("#")))
     val topCounts30 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(30))
       .map{case (topic, count) => (count, topic)}
       .transform(_.sortByKey(false))
     topCounts30.foreachRDD(rdd => {
       val topList = rdd.take(10)
       println("\nPopular topics in last 30 seconds (%s total):".format(rdd.count()))
       topList.foreach{case (count, tag) => println("%s (%s tweets)".format(tag, count))}
       rdd.saveAsSequenceFile("oup1")
     })


     val sc = ssc.sparkContext
     val stopWords = sc.broadcast(loadStopWords("/stopwords.txt")).value
     val labelToNumeric = createLabelMap("data/training/")
     var model: NaiveBayesModel = null
     // Training the data
     val training = sc.wholeTextFiles("data/training/*")
       .map(rawText => createLabeledDocument(rawText, labelToNumeric, stopWords))
     val X_train = tfidfTransformer(training)
     X_train.foreach(vv => println(vv))

     model = NaiveBayes.train(X_train, lambda = 1.0)

     val lines=sc.wholeTextFiles("data/testing/1/*")
     val data = lines.map(line => {

         val test = createLabeledDocumentTest(line._2, labelToNumeric, stopWords)
         println(test.body)
         test


     })

          val X_test = tfidfTransformerTest(sc, data)

            val predictionAndLabel = model.predict(X_test)
            println("PREDICTION")
            predictionAndLabel.foreach(x => {
              labelToNumeric.foreach { y => if (y._2 == x) {
                println(y._1)
              }
              }
            })

     ssc.start()

     ssc.awaitTerminationOrTimeout(300)



   }


 }
