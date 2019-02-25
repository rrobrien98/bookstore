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

  public Integer[] hello(int test) {
  
	System.out.println("recieved request");
	test++;
    	List<Integer> test2 = new ArrayList<Integer>();
    	test2.add(test);
    	XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

	XmlRpcClient client = null;
	


        try {
      	config.setServerURL(new URL("http://turing.bowdoin.edu:8085"));
      	client = new XmlRpcClient();
      	client.setConfig(config);
    	} catch (Exception e) {
     	 	System.err.println("Client exception: " + e);
    	}

	try {
      		Object[] result = (Object[]) client.execute("CatalogServer.hello", test2);
      		return  Arrays.asList(result).toArray(new Integer[0]);
      	
    	} catch (Exception e) {
      	System.err.println("Client exception: " + e);
    	
	}
	
	return null;
}

  public static void main(String[] args) {
    	try {
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
