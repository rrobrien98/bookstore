/*
 * Bookstore Client
 * Client specifies the hostname of the server they would like to request book info from
 * Program will then instruct the client to choose an operation
 * The client will select the operation by typing its corresponding number
 * they will then be prompted to give further arguments
 *
 * The client can select book numbers 1001-1010, and the library has books of topics "Fantasy" "Romance" and "Mystery"
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
	
	/*
	 * Start the connection to the bookstore server
	 * User needs to specify the host name of the frontend server
	 */

	public static void main(String[] args){
	
		if (args.length != 1){
			System.out.println("Invalid argument");
			return;
		}
		frontServer =  args[0];
		
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();			
		XmlRpcClient client = null;

		try {
			config.setServerURL(new URL("http://"+frontServer + ":8084"));
			client = new XmlRpcClient();
			client.setConfig(config);
		}catch (Exception e) {
			System.err.println("Client error " + e);
		}
	
		List<Object> params = new ArrayList<Object>();
		String function = "";
		
		//keep requesting requests from the user
		while(true){
			System.out.println("Select operation: \n1)Lookup Book By Item Number\n2)Search For All Books By Topic\n3)Buy A Book By Item Number\n");
			Scanner op = new Scanner(System.in);
			int operation = op.nextInt();
			int invalid = 0;
			
			//user specifies what type of command they want to perform
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
					invalid = 1;
					break;
			}
			
			try {
				if (invalid == 0){
			
					Object[] result = (Object[]) client.execute("FrontServer."+ function, params);
				
					//after receiving data from other servers, processes output based on command type
					switch (operation) {
                        			case 1:
                                			
                                			if (result[0].equals("Cannot Find Book\n")){
								System.out.println("Cannot Find Book");
							}
							else{
								String[] book_data = ((String)result[0]).split(",");
								System.out.println("Name: " + book_data[0] + "\nTopic: " + book_data[1] + "\nRemaining Copies: " + book_data[2]);
							}
                                			break;
                        			case 2:
                        			        if ((int) result[0] == -1){
								System.out.println("Cannot Find Book");
							}
							else{
								System.out.println("Book Nums of Matching Books\n");
								for (int i = 0; i < result.length; i ++){
									System.out.println(result[i]);
								}
							}
                                			break;
                        			case 3:
              						if((int) result[0] == -1){
								System.out.println("No More Copies");
							}
							else if((int) result[0] == -2){
								System.out.println("Cannot Find Book");
							}
							else{
								System.out.println("Purchase Success, Quantity of Item Remaining:\n" + result[0]);
							}
							break;
					}		
		

					params.clear();
				
				
				}

				//no valid command was given by the user
				else{
					params.clear();
					System.out.println("Invalid argument");
				}
			} catch (Exception e) {
				System.err.println("Client exception: " + e);
			}	
		}
	}		

}
