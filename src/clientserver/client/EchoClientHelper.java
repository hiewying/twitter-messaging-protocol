package clientserver.client;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * This class is a module which provides the application logic
 * for an Echo client using stream-mode socket.
 * @author M. L. Liu
 */

public class EchoClientHelper {

   static final String endMessage = ".";
   private ClientStreamSocket mySocket;
   private InetAddress serverHost;
   private int serverPort;

   EchoClientHelper(String hostName,
                    String portNum) throws SocketException,
                     UnknownHostException, IOException {
                                     
  	   this.serverHost = InetAddress.getByName(hostName);
  		this.serverPort = Integer.parseInt(portNum);
      //Instantiates a stream-mode socket and wait for a connection.
   	this.mySocket = new ClientStreamSocket(this.serverHost,
         this.serverPort); 
/**/  System.out.println("Connection request made");
   } // end constructor

   public String getEcho( String message) throws SocketException,
      IOException{
      String echo = "";
      mySocket.sendMessage( message);
	   // now receive the echo
      echo = mySocket.receiveMessage();
      return echo;
   } // end getEcho



   public String getLogin( String username, String password) throws SocketException,
           IOException{
      String echo = "";
      mySocket.sendLogin( username, password);
      // now receive the echo
      echo = mySocket.receiveMessage();
      return echo;
   } // end getLogin

    public String getDownload() throws SocketException,
            IOException{
        String echo = "";
        mySocket.sendDownload();
        // now receive the echo
        echo = mySocket.receiveMessage();
        return echo;
    } // end getDownload


    public String getLogout( String username, String password) throws SocketException,
           IOException{
      String echo = "";
      mySocket.sendLogout( username, password);
      // now receive the echo
      echo = mySocket.receiveMessage();
      return echo;
   } // end getLogout


   public void done( ) throws SocketException,
                              IOException{
//      mySocket.sendMessage(endMessage);      //sendMessage has ("..." + message) while sendResponse has only (message)
//      mySocket.sendResponse(endMessage);
      mySocket.close( );
   } // end done


} //end class
