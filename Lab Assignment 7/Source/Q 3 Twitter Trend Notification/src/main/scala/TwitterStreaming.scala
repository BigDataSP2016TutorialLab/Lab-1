
import java.io._
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Milliseconds, Seconds, StreamingContext}

/**
  * Created by Vamsi on 3/1/2016.
  */
object TwitterStreaming {

  def main(args: Array[String]) {

    System.setProperty("hadoop.home.dir","D:\\winutils" )
    val filters = args
    var tweetWithSentiment: TweetWithSentiment = new TweetWithSentiment

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
    val ssc = new StreamingContext(sparkConf, Milliseconds(500))
    val stream = TwitterUtils.createStream(ssc, None, filters)
    //stream.print()


    val t1 = stream.foreachRDD(rdd=>

      rdd.map(t=>{
        var i = 1
        val user = t.getUser.getScreenName
        val created_at = t.getCreatedAt.toInstant.toString
        val location = Option(t.getGeoLocation).map(geo => { s"${geo.getLatitude}${geo.getLongitude}" })
        val text = t.getText
        val hashtags = t.getHashtagEntities.map(_.getText)
        val retweet = t.getRetweetCount

        var tweet1 = senti(t.getText,hashtags(1),i)
        i=i+1

      }).saveAsTextFile("test/tweet"))
    //Starting streaming context
    ssc.start()
    //Stop the streaming context either by terminating or if the timeout happens for 1000000 seconds
    ssc.awaitTermination()

    //SocketClient.sendCommandToRobot("test")
  }
  def senti(text: String,hashtags: String,i: Integer)  {


    val pw = new PrintWriter(new File("twitterData.dat" ))
    pw.append(i+"::"+text+"::"+hashtags)
    System.out.println(i+"::"+text+"::"+hashtags)
    pw.close

    val sentimentAnalyzer: SentimentAnalyzer = new SentimentAnalyzer
    var tweetWithSentiment: TweetWithSentiment = new TweetWithSentiment
    tweetWithSentiment = sentimentAnalyzer.findSentiment(text)
    SocketClient.sendCommandToRobot(text+" ; "+tweetWithSentiment)
    System.out.println(text+" ; "+tweetWithSentiment)
  }

}