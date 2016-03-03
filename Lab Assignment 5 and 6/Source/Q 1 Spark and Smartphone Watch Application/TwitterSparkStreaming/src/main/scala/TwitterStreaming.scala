
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by Vamsi on 3/1/2016.
  */
object TwitterStreaming {

  def main(args: Array[String]) {

    System.setProperty("hadoop.home.dir","D:\\winutils" )
    val filters = args

    //Setting the API keys of twitter to variables
    val Consumer_Key="XJPlyOuAo7tC8YBxE1GDiTq8n"
    val Consumer_Secret="Zeyv8qYR9u5wmOqvBStqLV8HMA1Qvh3R9d3vDj02gfpTfAzrgs"
    val Access_Token="143736380-2VpESDsrWMcOipe7zLIMBr0ncHwnDbMxjckpU7nT"
    val Access_Token_Secret="jEdqt83lwfuDO1rPFpUyGmS4OexheFFsru7CZEH9iqnss"

    //Assigning the API keys
    System.setProperty("twitter4j.oauth.consumerKey", Consumer_Key)
    System.setProperty("twitter4j.oauth.consumerSecret", Consumer_Secret)
    System.setProperty("twitter4j.oauth.accessToken", Access_Token)
    System.setProperty("twitter4j.oauth.accessTokenSecret", Access_Token_Secret)

    //Creating Spark Configuration
    val sparkConf = new SparkConf().setAppName("STweetsApp").setMaster("local[*]")
    //Creating streaming context
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    val stream = TwitterUtils.createStream(ssc, None, filters)
    stream.print()
    //Getting Hashtags stream data based on criteria like "#"
    val hashTags = stream.flatMap(status => status.getText.split(" ").filter(_.startsWith("#")))
    //Getting top hashtags for 30 seconds
    val topCounts30 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(30))
      .map{case (topic, count) => (count, topic)}
      .transform(_.sortByKey(false))
    //Searching for each RDD and sending the values to SocketClient.scala
    topCounts30.foreachRDD(rdd => {
      val topList = rdd.take(10)
      println("\nPopular topics in last 30 seconds (%s total):".format(rdd.count()))
      topList.foreach{case (count, tag) => println("%s (%s tweets)".format(tag, count))}
      topList.foreach{case (count, tag) => SocketClient.sendCommandToRobot("\n( " + tag +" , "+ count+" )")}
    })
    //Starting streaming context
    ssc.start()
    //Stop the streaming context either by terminating or if the timeout happens for 1000000 seconds
    ssc.awaitTerminationOrTimeout(100000)
  }

}