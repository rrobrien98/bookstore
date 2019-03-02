/*
 * Order server
 * handles all queries that have to do with buying a copy of a book
 * gets queries from the frontend server
 * queries the catalog server
 */

import org.apache.xmlrpc.webserver.WebServer;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


import java.io.*;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import java.net.URL;


public class Order {
  	
	private static String catalogServer;	
  	
	/*
	 * Called from the frontend server when a client wants to purchase a copy of a book
	 * returns the number of books of that title remaining in the library if a purchase could be made
	 * otherwise, returns a negative int corresponding to the type of error
	 * ints to be returned are returned in an array
	 * This method can only be executed by one thread at a time to prevent from concurrency issues with users modifying the
	 * quantities of books in the library
	 */
	public synchronized Object[] buy(int item_number) {
    		System.out.println("Order recieved message");
    	
		List<Integer> num = new ArrayList<Integer>();
		num.add(item_number);
    		
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
                	Object[] result = (Object[]) client.execute("CatalogServer.query", num);

                   	//check if the item number was found 
                	if( !((String) result[0]).equals("Cannot Find Book\n")){ 
                                
				String[] split = ((String)  result[0]).split(",");
			
                		//only update the amount of books in library if there is at least one left for client to buy
				if (Integer.parseInt(split[2]) >= 1){
					num.add(-1);
					Object[] updated = (Object[]) client.execute("CatalogServer.update", num);
					return updated;
				}
				
				else {
					Object[] failed = new Object[1];
					failed[0] = -1;
					return failed;
				}
			}
		
			else{
				Object[] failed = new Object[1];
                        	failed[0] = -2;
                        	return failed;
			}
                
        	} catch (Exception e) {
        		System.err.println("Receiving exception: " + e);

        	}

		return null;
  
  	}
	/*
	 * Runs the order server
	 * User needs to specify the hostname for the catalog server as the only argument
	 */
	public static void main(String[] args) {
		try {
      			catalogServer = args[0];
      			PropertyHandlerMapping phm = new PropertyHandlerMapping();
      			XmlRpcServer xmlRpcServer;
      			WebServer server = new WebServer(8087);
      			xmlRpcServer = server.getXmlRpcServer();
      			phm.addHandler("OrderServer", Order.class);
      			xmlRpcServer.setHandlerMapping(phm);
      			server.start();
      			System.out.println("XML-RPC server started");
    		} catch (Exception e) {
      			System.err.println("Server exception: " + e);
    		}
  	}

}
