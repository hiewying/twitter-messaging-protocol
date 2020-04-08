package clientserver.client;

import java.io.*;
import java.util.ArrayList;
import java.net.*;
import javax.net.ssl.*;

/**
 * This module contains the presentaton logic of an Echo Client.
 * @author M. L. Liu
 */

public class Client {
    static final String quitMessage = "q";

   public static void main(String args[]) {

      //variables
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      boolean done = false;
      String username;
      String password;
      String message, echo;
      String hostName = "localhost";
      String portNum = "7";

      //////////////////////SSL//////////////////////////
       SSLSocketFactory f = (SSLSocketFactory) SSLSocketFactory.getDefault();
       ////////////////////////////////////////////////////

      try {

          ////////////////////////SSL//////////////////////////
          SSLSocket c = (SSLSocket) f.createSocket("localhost", 8888);
          printSocketInfo(c);
          c.startHandshake();
          //////////////////////////////////////////////////

         System.out.println("\n------Secure Client-Server Twitter Messaging Protocol------");
         EchoClientHelper helper = new EchoClientHelper(hostName, portNum);

         //program loop
         while(!done) {
            System.out.println("\n---------- Enter option -----------\n" +
                    "1. Login\n" + "2. Upload\n" + "3. Download\n" + "4. Logout\n" );
            String option = br.readLine();

            switch(option) {
               case "1":
                  System.out.println("Prepare to log in");
                  System.out.println("Enter username: ");
                  username = br.readLine();
                  System.out.println("Enter password: ");
                  password = br.readLine();

                   if(username.trim().length() == 0 || password.trim().length() == 0 ) {
                       username = "empty";
                       password = "empty";
                       echo = helper.getLogin(username, password);
                       System.out.println(echo);
                       break;
                   }else {
                       echo = helper.getLogin(username, password);
                       System.out.println(echo);
                   }
                   break;
               case "2":
                  System.out.println("Prepare to upload message to server");
                  System.out.println("Enter a line to receive an echo from the server, or enter 'q' to exit program.");
                  message = br.readLine( );

                   if(message.trim().length() == 0 ) {
                       message = "empty";
                       echo = helper.getEcho(message);
                       System.out.println(echo);
                       break;
                   }
                   else if ((message.trim()).equals (quitMessage)){
                    // back to options
                     break;
                  }
                  echo = helper.getEcho(message);
                  System.out.println(echo);
                  break;
               case "3":
                  System.out.println("Prepare to download ");
                  echo = helper.getDownload();
                  System.out.println(echo);
                  break;
               case "4":
                   System.out.println("Prepare to logout");
                   System.out.println("Enter username: ");
                   username = br.readLine();
                   System.out.println("Enter password: ");
                   password = br.readLine();
//
                   if(username.trim().length() == 0 || password.trim().length() == 0 ) {
                       username = "empty";
                       password = "empty";
                       echo = helper.getLogout(username, password);
                       System.out.println(echo);
                       break;
                   }else {
                       echo = helper.getLogout(username, password);
                       System.out.println(echo);
                   }
                   break;
               default:
                  System.out.println("Invalid option! Please try again.");
                  break;
            } //end switch
         } //end while
      } catch(Exception ex) {
         ex.printStackTrace();
      } //end catch
   } //end main

    private static void printSocketInfo(SSLSocket s) {
        System.out.println("Socket class: "+s.getClass());
        System.out.println("   Remote address = "
                +s.getInetAddress().toString());
        System.out.println("   Remote port = "+s.getPort());
        System.out.println("   Local socket address = "
                +s.getLocalSocketAddress().toString());
        System.out.println("   Local address = "
                +s.getLocalAddress().toString());
        System.out.println("   Local port = "+s.getLocalPort());
        System.out.println("   Need client authentication = "
                +s.getNeedClientAuth());
        SSLSession ss = s.getSession();
        System.out.println("   Cipher suite = "+ss.getCipherSuite());
        System.out.println("   Protocol = "+ss.getProtocol());
    }
} // end class
