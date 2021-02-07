package NewReliclab;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.newreliclab.NewRelicClient;
import com.newreliclab.NewRelicServer;

public class NewRelicClientTest {
	
    private static final int port = 4000;
    private static final String host = "localhost";
    private final static String SYSTEM_NEWLINE = System.getProperty("line.separator");
    private final static String TERMINATION_SEQUENCE = "terminate";
    private final static int LOOP_LIMIT = 1000;
    
     NewRelicServer newRelicServer = null;
	 NewRelicClient client;

	    @BeforeEach
	    void init() {
	        try
			{
	        	newRelicServer = NewRelicServer.getInstance();
	        	newRelicServer.initServer();
	        	client = new NewRelicClient();
			}	catch(Exception ex)
			{
				ex.printStackTrace();
			}    	
	       
	    }

	    @Test
	    void testRandomGenerator() {
	    	
	    	for (int i=0;i< LOOP_LIMIT;i++) {
	    		String randomString = client.generateRandomString().trim();
	    		client.sendMessage(randomString);
	    	}
	        /*
	        boolean isDigit = randomString.matches("^\\d+$");
	        if (isDigit) {
	            Assertions.assertTrue(randomString.length() < 10);
	        }
	        else {
	        	Assertions.assertEquals(randomString, "terminate");
	        }*/
	    }
	    
	    @AfterEach
	    void termConnection() {
	        client.terminateConnection();
	        newRelicServer.gracefulShutdown(); 
	    }


}
