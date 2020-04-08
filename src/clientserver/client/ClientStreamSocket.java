package clientserver.client;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * A wrapper class of Socket which contains 
 * methods for sending and receiving messages
 * @author M. L. Liu
 */
public class ClientStreamSocket extends Socket {
   private Socket  socket;
   private BufferedReader input;
   private PrintWriter output;

    private ObjectOutputStream out; //The output stream

   ClientStreamSocket(InetAddress acceptorHost,
                      int acceptorPort ) throws SocketException,
                                   IOException{
      socket = new Socket(acceptorHost, acceptorPort );
      setStreams( );

   }

   ClientStreamSocket(Socket socket)  throws IOException {
      this.socket = socket;
      setStreams( );
   }

   private void setStreams( ) throws IOException{
      // get an input stream for reading from the data socket
      InputStream inStream = socket.getInputStream();
      input = 
         new BufferedReader(new InputStreamReader(inStream));
      OutputStream outStream = socket.getOutputStream();
      // create a PrinterWriter object for character-mode output
      output = 
         new PrintWriter(new OutputStreamWriter(outStream));
   }


   public void sendMessage(String message)
   		          throws IOException {
      output.print("600 " + message + "\n");
      //The ensuing flush method call is necessary for the data to
      // be written to the socket data stream before the
      // socket is closed.
      output.flush();
   } // end sendMessage

   public String receiveMessage( )
		throws IOException {	
      // read a line from the data stream
      String message = input.readLine( );  
      return message;
   } //end receiveMessage


   public void sendLogin(String username, String password)
           throws IOException {
       output.print("500 " + username + " " + password + "\n");
      //The ensuing flush method call is necessary for the data to
      // be written to the socket data stream before the
      // socket is closed.
      output.flush();
   } // end

    public void sendDownload()
            throws IOException {
        output.print("700 " + "\n");
        //The ensuing flush method call is necessary for the data to
        // be written to the socket data stream before the
        // socket is closed.
        output.flush();
    } // end

   public void sendLogout(String username, String password)
           throws IOException {
      output.print("800 " + username + " " + password + "\n");
      //The ensuing flush method call is necessary for the data to
      // be written to the socket data stream before the
      // socket is closed.
      output.flush();
   } // end
   
   public void close( )
		throws IOException {	
      socket.close( );
   }
} //end class
