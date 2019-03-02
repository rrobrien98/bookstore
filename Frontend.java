/*
 * frontend server
 * processes requests from the client servers and redirects them to either the catalog server or order server
 *
 */
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.apache.xmlrpc.webserver.WebServer;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import java.net.URL;
public class Frontend {

	private static String catalogServer;
	private static String orderServer;

	/*
	 * Called by the client when they want to look up books by a topic
	 * takes in the topic requested by the user, establishes connection with the catalog server
	 * and calls query on the catalog server to find all books of that topic
	 * 
	 */

	public Object[] search(String arg) {
  
		System.out.println("recieved request " + arg);
	
 	   	List<String> topic = new ArrayList<String>();
    		topic.add(arg);
    		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
	
		XmlRpcClient client = null;
	
	

        	try {
      			config.setServerURL(new URL("http://" + catalogServer + ":8085"));
      			client = new XmlRpcClient();
      			client.setConfig(config);
    		} catch (Exception e) {
     	 		System.err.println("Client exception: " + e);
    		}

		try {
      			//will get an array of item nums from the catalog, just returns these to client
			Object[] result = (Object[]) client.execute("CatalogServer.query", topic);
      
		
			return  result;
		
      	
    		} catch (Exception e) {
      			System.err.println("Receiving exception: " + e);
    	
		}
	
		return null;
	}

	
	/*
	 * Called by the client when they want to  look up inventory details for a 
	 * particular book. Establishes a connection with the catalog server. 
	 * Returns a string split by commas, of the particular book details.  
	 */	
	public Object[] lookup(int item_number){
	
		System.out.println("recieved request " + item_number);
				
    		List<Integer> item_num = new ArrayList<Integer>();
    		item_num.add( item_number);
    		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

		XmlRpcClient client = null;
	
		

	        try {
	      		config.setServerURL(new URL("http://" + catalogServer + ":8085"));
	      		client = new XmlRpcClient();
	      		client.setConfig(config);
    		} catch (Exception e) {
    	 	 	System.err.println("Client exception: " + e);
    		}

		try {
			//will get the book details from the catalog, just returns to client
	      		Object[] result = (Object[]) client.execute("CatalogServer.query", item_num);
	      		
				
			return  result;
      	
    		} catch (Exception e) {
   	   		System.err.println("Receiving exception: " + e);
    	
		}
	
		return null;
	}
	/*
	 * Will be called by client who will want to purchase a copy of a book specified by item number
	 * this method makes connection with the order server, and asks it to complete the purchase if possible
	 * will return the number of books remaining after the purchase,
	 * or a negative number denoting the reason why the purchase could not be completed
	 */ 
	public Object[] buy(int item_number){
		List<Integer> item_num = new ArrayList<Integer>();
		item_num.add(item_number);

    		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

		XmlRpcClient client = null;
	
		

	        try {
	      		config.setServerURL(new URL("http://" + orderServer +":8087"));
	      		client = new XmlRpcClient();
	      		client.setConfig(config);
    		} catch (Exception e) {
    	 	 	System.err.println("Client exception: " + e);
    		}

		try {
			//will just forward result from buy function in order server to client
	      		Object[] result = (Object[]) client.execute("OrderServer.buy", item_num);
	      		
			return  result;
			
      	
    		} catch (Exception e) {
   	   		System.err.println("Receiving exception: " + e);
    	
		}
	
		return null;
	}

	/*
	 * Starts the frontend server running
	 * takes in the host names of 1) the client server and 2) the order server
	 */
  	public static void main(String[] args) {
    		try {
			catalogServer = args[0];
			orderServer = args[1];
      			PropertyHandlerMapping phm = new PropertyHandlerMapping();
      			XmlRpcServer xmlRpcServer;
      			WebServer server = new WebServer(8084);
      			xmlRpcServer = server.getXmlRpcServer();
     			phm.addHandler("FrontServer", Frontend.class);
      			xmlRpcServer.setHandlerMapping(phm);
      			server.start();
      			System.out.println("XML-RPC server started");
    		
		} catch (Exception e) {
      			System.err.println("Server exception: " + e);
		}
  	}

}
