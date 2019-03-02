/*
 * catalog server
 * handles all queries reguarding the quantities of books
 * supports updating the number of copies of a title and
 * supports queries about all books in a genre or the details of a single book specified by item num
 */

import org.apache.xmlrpc.webserver.WebServer;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;



public class Catalog {
	private static ArrayList<Book> library = new ArrayList<Book>();
  	static Book book1 = new Book("Mystery", 1001, "Scooby-Doo", 5);
	static Book book2 = new Book("Fantasy", 1002, "Harry Potter", 10);
	static Book book3 = new Book("Fantasy", 1003, "Star Wars", 6);
	static Book book4 = new Book("Romance", 1004, "Romeo and Juliet", 4);
	static Book book5 = new Book("Mystery", 1005, "The Giver", 3);
	static Book book6 = new Book("Romance", 1006, "Shrek", 10);
	static Book book7 = new Book("Fantasy", 1007, "Narnia", 8);
	static Book book8 = new Book("Mystery", 1008, "Sherlock Holmes", 2);
	static Book book9 = new Book("Romance", 1009, "Jane Austen", 7);
	static Book book10 = new Book("Fantasy", 1010, "Lightning Theif", 3);
	
	/*
	 * Called by the buy server, updates the inventory count of specified book.
	 * Returns an array with the updated copy count. 
	 */
	public Integer[] update(int item_number, int qty){
		Integer[] copies = new Integer[1];
		for (Book book : library){
			if (book.getItemNumber() == item_number){
				
				book.setCopies(book.getCopies() + qty);
				copies[0] = book.getCopies();	
			}
		}
		return copies;
	}
	/*
	 * This method is overloaded and has two definitions
	 * This method supports a query by subject
	 * given a book topic, this method finds all the item nums in the library that fit that topic and returns them in an array
	 * Will return -1 upon a unmatched topic request to denote failure
	 *
	 */
	public Integer[] query(String arg) {
  		System.out.println("catalog recieved message" +  arg);
    			
		int topic_matches = 0;
	
		for (Book book : library){
			
			if (book.getTopic().equals( (String) arg)){	
				topic_matches++;
			}
				
		}		
		if(topic_matches == 0){
			
			topic_matches++;
		}
		Integer[] matches = new Integer[topic_matches];
		matches[0] = -1;
		int i = 0;

		for (Book book : library){
			if (book.getTopic().equals((String) arg)){
				System.out.println(book.getName());
				matches[i] = book.getItemNumber();
				i++;
			}
		}
		
		
	
			
      			
		System.out.println(matches[0]);
		return  matches;
  	}
	/*
	 * Second definition of query supports query by item num
	 * will give the name, topic, and num of copies of a book of a specified item num together as one single string
	 * this string is retuned  in an array
	 * will return a specific error string in that array if no book can be found
	 */
	 
	public String[] query(int arg) {
  		System.out.println("catalog recieved message" +  arg);
    		String[] matches = new String[1];
		matches[0] = "Cannot Find Book\n";
		for (Book book : library){
			if (book.getItemNumber() ==  arg){
				System.out.println("matched book");
				matches[0] = book.getName() + "," + book.getTopic() + "," +  book.getCopies();
				break;
				}	
		}
		
		System.out.println("array : " + matches[0] + (matches == null));	
      			
		return  matches;
  	}
	/*
	 * runs the catalog server
	 * no arguments necessary, as this does not need to query other servers
	 */
  	public static void main(String[] args) {
   		//fill library 
		library.add(book1);
		library.add(book2);
		library.add(book3);
		library.add(book4);
		library.add(book5);
		library.add(book6);
		library.add(book7);
		library.add(book8);
		library.add(book9);
		library.add(book10);
		  
		try {
      			PropertyHandlerMapping phm = new PropertyHandlerMapping();
      			XmlRpcServer xmlRpcServer;
      			WebServer server = new WebServer(8085);
      			xmlRpcServer = server.getXmlRpcServer();
      			phm.addHandler("CatalogServer", Catalog.class);
      			xmlRpcServer.setHandlerMapping(phm);
      			server.start();
      	
      			System.out.println("XML-RPC server started");
    			//keep updating the contents of the library every 30 seconds
			while(true){
				TimeUnit.SECONDS.sleep(30);
				for(Book book : library){
					book.setCopies(book.getCopies() + 1);
				}
			}
		
		} catch (Exception e) {
	      		System.err.println("Server exception: " + e);
	    	}

	}
	
}
