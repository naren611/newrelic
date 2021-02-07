package com.newreliclab;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;


public class NewRelicClient {
	
    private static final int port = 4000;
    private static final String host = "localhost";
    private final static String SYSTEM_NEWLINE = System.getProperty("line.separator");
    private final static String TERMINATION_SEQUENCE = "terminate";
    private final static int LOOP_LIMIT = 1000;
    
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    
    public NewRelicClient() {
    	initConnection();
    }

    public void initConnection() {
    	try {
		    	socket = new Socket(host, port);
		        out = new PrintWriter(socket.getOutputStream(), true);
		        in = new BufferedReader(new InputStreamReader(
		        		socket.getInputStream()));
	    		new MessagesThread().start();
    	}	catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
   
    public void sendMessage(String msg) {
    	try {
    		out.println(msg);
	    }	catch(Exception ex) {
			ex.printStackTrace();
		}
    }

    public void terminateConnection() {
	    try {
	    	in.close();
	        out.close();
	        socket.close();
	    }	catch(Exception ex) {
	    	ex.printStackTrace();
		}
	    System.exit(0);
    }
    
    class  MessagesThread extends Thread {
        public void run() {
            String line;
            try {
                while(true) {
                    line = in.readLine();
                    if(line == null || line.equals("terminate")) {
                    	break;
                    }
                } 
            } catch(Exception ex) {
            	//ex.printStackTrace();
            }
            terminateConnection(); 
        }
    }
    
    public String generateRandomString() {
        Random random = new Random();
        int upperBound = 100000;
        int randomNumber = random.nextInt(upperBound);

        if (randomNumber > upperBound / 2) {
            return padLeftZeros(randomNumber + "", 9);
        }
        else {
            return padLeftZeros(randomNumber + "", 9);
        }
    }

    private String padLeftZeros(
        String inputString,
        int length
    ) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString).append(SYSTEM_NEWLINE);

        return sb.toString();
    }
    
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
/*
    	NewRelicClient newRelicClient = null;
        BufferedReader stdIn = null;
    	try {
    		newRelicClient = new NewRelicClient();
            stdIn = new BufferedReader(
                    new InputStreamReader(System.in));
            String userInput;
    		while ((userInput = stdIn.readLine()) != null) {
    			if(userInput.matches("\\d{9}") || userInput.equals("terminate")) {
    				newRelicClient.sendMessage(userInput);
    			}	else {
    				break;
    			}
    		}
    		newRelicClient.terminateConnection();
        }	catch (IOException ex) {
            ex.printStackTrace();
        }
*/
    	NewRelicClient client = null;
    	try {
        		client = new NewRelicClient();
		    	for (int i=0;i< LOOP_LIMIT;i++) {
		    		String randomString = client.generateRandomString().trim();
		    		//System.out.println(randomString);
		    		client.sendMessage(randomString);
		    	}
		    	client.terminateConnection();
    	}	catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}