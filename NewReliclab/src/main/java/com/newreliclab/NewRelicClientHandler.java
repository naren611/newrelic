package com.newreliclab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NewRelicClientHandler implements Runnable{
	
	   final static Log logger = LogFactory.getLog(NewRelicClientHandler.class);
	
	   private Socket conn;
	   private BufferedReader in = null;
	   private PrintWriter out =  null;
		
	   public NewRelicClientHandler(Socket conn) 
	    {
			this.conn = conn;
	    }

		public void run() 
	    {
			String message ;
			try
			{
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				out = new PrintWriter(conn.getOutputStream(), true);
				
				logger.debug("Welcome to the Server");

				do
				{
					message = (String)in.readLine();
					if(message != null)
					{
						if(message.equals("terminate") ) {
							NewRelicServer.getInstance().gracefulShutdown();
							break;
						}
						NewRelicCache.addNew(message);
					}
					else
					{
						logger.debug("Client has disconnected");
						break;
					}
				}
				while(true);
			}	catch (IOException ex){
				logger.error(ex);
			}

			try {
				in.close();
				out.close();
				conn.close();
				NewRelicServer.clientCount--;
			}	catch(IOException ioEx) {
				logger.error(ioEx);
			}	catch(Exception ex) {
				logger.error(ex);
			}
	    }
		
		public void shutdown() {
			try {
				logger.debug("Closing Connection");
				in.close();
				out.close();
				conn.close();
				logger.debug("Closing Connection done");
			}	catch(Exception ex) {
				logger.error(ex);
			}
		}
}
