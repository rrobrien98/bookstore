/*
 * Book Object
 * 
 * represents a single book title in the library
 * contains the name of the book, the topic of the book, and the number of copies left in the library
 */
import java.io.*;

public class Book {
	
	private String topic;
	private int item_number;
	private String name;
	private int copies;

	public Book(String topic, int item_number, String name, int copies){
		this.topic = topic;
		this.item_number = item_number;
		this.name = name;
		this.copies = copies;
	}

	public String getName(){
		return this.name;
	}

	public String getTopic(){
		return this.topic;
	}
	public int getItemNumber(){
		return this.item_number;
	}
	public int getCopies(){
		return this.copies;
	}
	public void setCopies(int qty){
		this.copies = qty;
	}
}
