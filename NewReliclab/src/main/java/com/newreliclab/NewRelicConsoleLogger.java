package com.newreliclab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NewRelicConsoleLogger implements Runnable{
	
		final static Log logger = LogFactory.getLog(NewRelicConsoleLogger.class);
		
		private final int logDuration;
	    
	   public NewRelicConsoleLogger(int logDuration) 
	    {
		   this.logDuration = logDuration;
		   logger.debug("NewRelicConsoleLogger");
	    }

		public void run() 
	    {
			try {
				while(true) {
					Thread.sleep(10 * 1000);
					logger.info("#" + NewRelicCache.runCount + ": Received " +  NewRelicCache.rcvdCount + " unique numbers, " + NewRelicCache.dupCount + "  duplicates " +  ". Unique total: " + NewRelicCache.uniqueNumbers.size());
					NewRelicCache.reset();
				}
			}catch (InterruptedException e) {
			       Thread.currentThread().interrupt(); // restore interrupted status
			}	catch (Exception e) 
			{
				logger.error(e);
			}
	    }
}
