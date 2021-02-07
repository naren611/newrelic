package com.newreliclab;

import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NewRelicCache {
	
	final static Log logger = LogFactory.getLog(NewRelicCache.class);
	
	public static Vector<String> uniqueNumbers = new Vector<String>();
	public static long runCount = 0;
	public static long dupCount = 0;
	public static long rcvdCount = 0;
	
	public static void addNew(String newNum) {
		rcvdCount++;
		if(uniqueNumbers.contains(newNum)) {
			dupCount++;
		}	else {
			uniqueNumbers.add(newNum);
			logger.info(newNum);
		}
	}
	
	public static void reset() {
		rcvdCount = 0;
		dupCount = 0;
		runCount++;
	}
	
}
