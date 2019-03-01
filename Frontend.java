/*
 * frontend server
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

public Object[] search(String arg) {
  
	System.out.println("recieved request " + arg);
	
    	List<String> topic = new ArrayList<String>();
    	topic.add(arg);
    	XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

	XmlRpcClient client = null;
	
	

        try {
      	config.setServerURL(new URL(catalogServer));
      	client = new XmlRpcClient();
      	client.setConfig(config);
    	} catch (Exception e) {
     	 	System.err.println("Client exception: " + e);
    	}

	try {
      		Object[] result = (Object[]) client.execute("CatalogServer.query", topic);
      
		//int i = 0;
		
		//for (Book book : (Book[]) result){
		//	System.out.println(book.getName());
		//	bookNums[i] = book.getItemNumber();
		//	i++;
		//}
		return  result;
		// Arrays.asList(result).toArray(new Integer[0]);
      	
    	} catch (Exception e) {
      	System.err.println("Receiving exception: " + e);
    	
	}
	
	return null;
}

	public Object[] lookup(int item_number){
	
		System.out.println("recieved request " + item_number);
				
    		List<Integer> item_num = new ArrayList<Integer>();
    		item_num.add( item_number);
    		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

		XmlRpcClient client = null;
	
		//Book[] book = new Book[1];

	        try {
	      	config.setServerURL(new URL(catalogServer));
	      	client = new XmlRpcClient();
	      	client.setConfig(config);
    		} catch (Exception e) {
    	 	 	System.err.println("Client exception: " + e);
    		}

		try {
	      		Object[] result = (Object[]) client.execute("CatalogServer.query", item_num);
	      		
				

			
			return  result;
			// Arrays.asList(result).toArray(new Integer[0]);
      	
    		} catch (Exception e) {
   	   	System.err.println("Receiving exception: " + e);
    	
		}
	
		return null;
	}

	public Object[] buy(int item_number){
		List<Integer> item_num = new ArrayList<Integer>();
		item_num.add(item_number);

    		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

		XmlRpcClient client = null;
	
		

	        try {
	      	config.setServerURL(new URL(orderServer));
	      	client = new XmlRpcClient();
	      	client.setConfig(config);
    		} catch (Exception e) {
    	 	 	System.err.println("Client exception: " + e);
    		}

		try {
	      		Object[] result = (Object[]) client.execute("OrderServer.buy", item_num);
	      		
				

			
			return  result;
			// Arrays.asList(result).toArray(new Integer[0]);
      	
    		} catch (Exception e) {
   	   	System.err.println("Receiving exception: " + e);
    	
		}
	
		return null;
	}


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
