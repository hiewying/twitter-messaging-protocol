package clientserver.server;

import java.io.*;
import java.net.*;
import java.security.*;
import javax.net.ssl.*;

/**
 * This module contains the application logic of an echo server
 * which uses a stream-mode socket for interprocess communication.
 * Unlike EchoServer2, this server services clients concurrently.
 * A command-line argument is required to specify the server port.
 * @author M. L. Liu
 */

public class Server {
   public static void main(String[] args) {
      int serverPort = 7;    // default port
      String message;

      ///////////////////////SSL/////////////////////////////
      String ksName = "herong.jks";
      char ksPass[] = "password".toCharArray();
      char ctPass[] = "password".toCharArray();
      ///////////////////////////////////////////////////////

      if (args.length == 1 )
         serverPort = Integer.parseInt(args[0]);       
      try {

         //////////////////SSL/////////////////////////////////////
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(ksName), ksPass);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, ctPass);
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(kmf.getKeyManagers(), null, null);
            SSLServerSocketFactory ssf = sc.getServerSocketFactory();
            SSLServerSocket s = (SSLServerSocket) ssf.createServerSocket(8888);
            printServerSocketInfo(s);
            SSLSocket c = (SSLSocket) s.accept();
            printSocketInfo(c);
         ///////////////////////////////////////////////////////////

         // instantiates a stream socket for accepting onnections
         ServerSocket myConnectionSocket = new ServerSocket(serverPort);
         System.out.println("\n------Twitter Messaging Protocol Server Ready------");


         while (true) {  // forever loop
            // wait to accept a connection 
/**/        System.out.println("Waiting for a connection.\n");
            ServerStreamSocket myDataSocket = new ServerStreamSocket(myConnectionSocket.accept( ));
/**/        System.out.println("Connection accepted");
            // Start a thread to handle this client's session
            Thread theThread = 
               new Thread(new EchoServerThread(myDataSocket));
            theThread.start();
            // and go on to the next client
            } //end while forever
       } // end try
	    catch (Exception ex) {
          ex.printStackTrace( );
	    } // end catch
   } //end main

   ///////////////////////////// SSL ///////////////////////////////
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
   private static void printServerSocketInfo(SSLServerSocket s) {
      System.out.println("Server socket class: "+s.getClass());
      System.out.println("   Socket address = "
              +s.getInetAddress().toString());
      System.out.println("   Socket port = "
              +s.getLocalPort());
      System.out.println("   Need client authentication = "
              +s.getNeedClientAuth());
      System.out.println("   Want client authentication = "
              +s.getWantClientAuth());
      System.out.println("   Use client mode = "
              +s.getUseClientMode());
   }

} // end class
