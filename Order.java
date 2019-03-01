/*
 * order server
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


/**
 * A simple example XML-RPC server program.
 */
public class Order {
  private static String catalogServer;	
  public synchronized Object[] buy(int item_number) {
    	System.out.println("Order recieved message");
    	
	List<Integer> num = new ArrayList<Integer>();
	num.add(item_number);
    	
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
                Object[] result = (Object[]) client.execute("CatalogServer.query", num);

                    
                
                                
		String[] split = ((String)  result[0]).split(",");
			
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

                // Arrays.asList(result).toArray(new Integer[0]);

        } catch (Exception e) {
        System.err.println("Receiving exception: " + e);

        }

	return null;
  
  }

  public static void main(String[] args) {
    try {
      catalogServer = args[0];
      PropertyHandlerMapping phm = new PropertyHandlerMapping();
      XmlRpcServer xmlRpcServer;
      WebServer server = new WebServer(8086);
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
