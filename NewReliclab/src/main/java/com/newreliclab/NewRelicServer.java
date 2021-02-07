package com.newreliclab;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NewRelicServer {
    
	final static Log logger = LogFactory.getLog(NewRelicServer.class);
	
	private final Properties properties = new Properties();
	
    private ServerSocket server;
    private static final int port = 4000;
    public static int clientCount = 0;
    private final static int DEFAULT_MAX_CLIENTS = 6;
    private final static int LOGGING_DURATION = 6;
    
    ExecutorService executor = Executors.newFixedThreadPool(DEFAULT_MAX_CLIENTS);
    
    ArrayList<NewRelicClientHandler> clientList = new ArrayList<NewRelicClientHandler>();
    
    private static NewRelicServer _newRelicServer  = null ;
    
    private NewRelicServer () {
    }
    
    
    public void initServer() {
       	Socket socket = null;
        try {
        	server = new ServerSocket(port);

        	// Add ConsoleHandler first
        	Runnable worker = new NewRelicConsoleLogger(LOGGING_DURATION);  
        	executor.execute(worker);
        	
            while(true)
			{
            	socket = server.accept();
            	clientCount++;
            	if (clientCount < DEFAULT_MAX_CLIENTS) {
	            	logger.debug("creating new client connection " + clientCount);
	            	Runnable iWorker = new NewRelicClientHandler(socket);  
	            	executor.execute(iWorker);
	            	clientList.add((NewRelicClientHandler) iWorker);
	            }	else {
	            	socket.close();
	            }
			}
        }	catch(IOException ex){
        	logger.error(ex);
		}
        try {
        	server.close();
        }	catch(Exception ex) {
        	//logger.error(ex);
        }
    }
    
    public void gracefulShutdown() {
    		try {
    			System.out.println("	clientList.size() " + clientList.size());
    			logger.debug("Closing client connections Started");
    			for (NewRelicClientHandler item : clientList) {
    				item.shutdown();
    			}
    			
    			logger.debug("Closing client connections done");
    			// executor.shutdownNow();
    			server.close();
    			logger.debug("Server shutdown");
    			
    			System.exit(0);
    			
    		}	catch(Exception ex) {
    			ex.printStackTrace();
    			logger.error(ex);
    		}
    }
    
    public static NewRelicServer getInstance() {
    	if (_newRelicServer == null) {
    		_newRelicServer = new NewRelicServer();
    	}
    	return _newRelicServer;
    }
    
    public static void main(String args[]) throws IOException{
    	NewRelicServer _newRelicServer = null;
        try
		{
        	_newRelicServer = NewRelicServer.getInstance();
        	_newRelicServer.initServer();
		}	catch(Exception ex)
		{
			logger.error(ex);
		}    	
    }
    
}