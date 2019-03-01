/*
 * java client
 * 
 *
 */
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.net.URL;

public class Client {



	private static String frontServer;
	public static void main(String[] args){
		//String hostname = args[0];

		frontServer =  args[0];
		
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();			
		XmlRpcClient client = null;

		try {
			config.setServerURL(new URL(frontServer));
			client = new XmlRpcClient();
			client.setConfig(config);
		}catch (Exception e) {
			System.err.println("Client error " + e);
		}
		List<Object> params = new ArrayList<Object>();
		String function = "";
		while(true){
		System.out.println("Select operation: \n1)Lookup Book By Item Number\n2)Search For All Books By Topic\n3)Buy A Book By Item Number\n");
		Scanner op = new Scanner(System.in);
		int operation = op.nextInt();
		switch (operation) {
			case 1:
				function = "lookup";
				System.out.println("Enter Item Number\n");
				Scanner num = new Scanner(System.in);
				params.add(num.nextInt());
				break;
			case 2:
				function = "search";
				System.out.println("Enter Topic\n");
				Scanner topic = new Scanner(System.in);
				params.add(topic.nextLine());
				break;
			case 3:
				function = "buy";
				System.out.println("Enter Item Number\n");
				Scanner buy_num = new Scanner(System.in);
				params.add(buy_num.nextInt());
				break;
			default:
				System.out.println("Invalid Command");
				break;
		}
		try {




			Object[] result = (Object[]) client.execute("FrontServer."+ function, params);
			params.clear();
		      	System.out.println("result ");
			for (Object res : result){
				System.out.println(res);
			}
		} catch (Exception e) {
			System.err.println("Client exception: " + e);
		}	
		}
	}		


}
