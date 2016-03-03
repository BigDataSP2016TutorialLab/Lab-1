import java.io._
import java.net.{Socket, InetAddress}
/**
  * Created by Vamsi on 3/1/2016.
  */
object SocketClient {
  
    def findIpAdd():String =
  {
    val localhost = InetAddress.getLocalHost
    val localIpAddress = localhost.getHostAddress

    return localIpAddress
  }
  def sendCommandToRobot(string: String)
  {
    try {

      //Assigning the IP value of the mobile to address variable
      lazy val address: Array[Byte] = Array(192.toByte, 168.toByte, 1.toByte, 120.toByte)
      val ia = InetAddress.getByAddress(address)

      //Create port address
      val socket = new Socket(ia, 1234)

      //Creating out variable and print the stream to
      val out = new PrintStream(socket.getOutputStream)

      //Printing the string and sending to Mobile Client
      out.print(string)
      out.flush()
      out.close()
      socket.close()
    }
    catch {
      case e: IOException =>
        e.printStackTrace()
    }
  }

}
