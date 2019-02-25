/*
 * java client
 * 
 *
 */
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;


import java.util.List;
import java.util.ArrayList;
import java.net.URL;

public class Client {




	public static void main(String[] args){
		//String hostname = args[0];

		int command = Integer.parseInt(args[0]);
		System.out.println("argument = " + command);
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();			
		XmlRpcClient client = null;

		try {
			config.setServerURL(new URL("http://turing.bowdoin.edu:8084"));
			client = new XmlRpcClient();
			client.setConfig(config);
		}catch (Exception e) {
			System.err.println("Client error " + e);
		}
		try {
			List<Integer> test = new ArrayList<Integer>();
			test.add(5);

			Object[] result = (Object[]) client.execute("FrontServer.hello", test);
		      	System.out.println("resulting string " + result[0]);
		} catch (Exception e) {
			System.err.println("Client exception: " + e);
		}	
	}		


}
