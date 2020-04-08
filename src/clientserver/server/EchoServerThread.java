package clientserver.server;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This module is to be used with a concurrent Echo server.
 * Its run method carries out the logic of a client session.
 * @author M. L. Liu
 */

class EchoServerThread implements Runnable {
    static final String endMessage = ".";
    ServerStreamSocket myDataSocket;

    String code;
    String username;
    String password;
    String uploadMessage;
    String echo;

    ArrayList messageArray = new ArrayList();
    File filename = new File ("MessageList.txt");

    EchoServerThread(ServerStreamSocket myDataSocket) {
        this.myDataSocket = myDataSocket;
    }

    public void run( ) {
        boolean done = false;
        String message;
        try {
            while (!done) {
                message = myDataSocket.receiveMessage( );

                //references: https://www.mkyong.com/java/java-how-to-split-a-string/
                String[] messages = message.split(" ");
                code = messages[0].trim();

                switch (code) {
                    case "500":
                        System.out.println("\n---------- 500 LOGIN ----------");
                        username = messages[1].trim();
                        password = messages[2].trim();
                        if ("admin".equals(username) && "admin".equals(password)) {
                            System.out.println("501: LOGIN SUCCESSFUL");
                            echo = "LOGIN SUCCESSFUL WITH USERNAME: " + username + " AND PASSWORD: " + password;
                            System.out.println(echo);
                            myDataSocket.sendMessage(echo);
                        }
                        else if ("empty".equals(username) || "empty".equals(password)){
                            System.out.println("502: LOGIN UNSUCCESSFUL");
                            echo = "USERNAME AND/OR PASSWORD MUST NOT BE EMPTY";
                            myDataSocket.sendMessage(echo);
                        }
                        else{
                            System.out.println("502: LOGIN UNSUCCESSFUL");
                            echo = "INVALID USERNAME AND PASSWORD";
                            myDataSocket.sendMessage(echo);
                        }
                    break;
                    case "600":
                        System.out.println("\n---------- 600 UPLOAD ----------");
                        uploadMessage = messages[1].trim();

                        if ("empty".equals(uploadMessage) ){
                            System.out.println("602: UPLOAD UNSUCCESSFUL");
                            echo = "MESSAGE MUST NOT BE EMPTY";
                            myDataSocket.sendMessage(echo);
                        }
                        else
                        {
                            System.out.println("601: UPLOAD SUCCESSFUL");
                            echo = "MESSAGE UPLOADED: " + uploadMessage;
                            System.out.println(echo);
                            myDataSocket.sendMessage(echo);
                            // add into array
                            messageArray.add(uploadMessage);
                            System.out.println("LIST OF MESSAGES: " + messageArray);
                        }
                    break;
                    case "700":
                        System.out.println("\n---------- 700 DOWNLOAD ----------");

                        if (messageArray.isEmpty()){
                            System.out.println("702: DOWNLOAD UNSUCCESSFUL");
                            echo = "NO MESSAGE IS UPLOADED";
                            myDataSocket.sendMessage(echo);
                        }else {

                            // reference: https://www.youtube.com/watch?v=aakSrwq1ZO8
                            try {
                                FileWriter fw = new FileWriter(filename);
                                Writer output = new BufferedWriter(fw);
                                int sz = messageArray.size();
                                for (int i = 0; i < sz; i++) {
                                    output.write(messageArray.get(i).toString() + "\n");
                                }
                                output.close();
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                            System.out.println("701: DOWNLOAD SUCCESSFUL");
                            echo = "MESSAGE DOWNLOADED IN MessageList.txt SUCCESSFULLY";
                            myDataSocket.sendMessage(echo);
                        }
                        break;
                    case "800":
                        System.out.println("\n---------- 800 LOGOUT ----------");
                        username = messages[1].trim();
                        password = messages[2].trim();
                        if ("admin".equals(username) && "admin".equals(password)) {
                            System.out.println("801: LOGOUT SUCCESSFUL");
                            echo = "LOGOUT SUCCESSFUL WITH USERNAME: " + username + " AND PASSWORD: " + password;
                            System.out.println(echo);
                            myDataSocket.sendMessage(echo);
                        }
                        else if ("empty".equals(username) || "empty".equals(password)){
                            System.out.println("802: LOGOUT UNSUCCESSFUL");
                            echo = "USERNAME AND/OR PASSWORD MUST NOT BE EMPTY";
                            myDataSocket.sendMessage(echo);
                        }
                        else{
                            System.out.println("802: LOGOUT UNSUCCESSFUL");
                            echo = "INVALID USERNAME AND PASSWORD";
                            myDataSocket.sendMessage(echo);
                        }
                        break;
                    default:
                        System.out.println("\n---------- 900 SYSTEM ERROR ----------");
                }
            } //end while !done
        }// end try
        catch (Exception ex) {
            System.out.println("Exception caught in thread: " + ex);
        } // end catch
    } //end run
} //end class 
