/*
 * catalog server
 */

import org.apache.xmlrpc.webserver.WebServer;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import java.util.Array;
import java.util.ArrayList;
/**
 * A simple example XML-RPC server program.
 */
public class Catalog {
	private ArrayList<Book> library = new Arraylist<Book>();
  public Integer[] hello(int test) {
    System.out.println("catalog recieved message");
    Integer[] array = new Integer[1];
    array[0] = test +1;
    return array;
  }

  public static void main(String[] args) {
    try {
      PropertyHandlerMapping phm = new PropertyHandlerMapping();
      XmlRpcServer xmlRpcServer;
      WebServer server = new WebServer(8085);
      xmlRpcServer = server.getXmlRpcServer();
      phm.addHandler("CatalogServer", Catalog.class);
      xmlRpcServer.setHandlerMapping(phm);
      server.start();
      System.out.println("XML-RPC server started");
    } catch (Exception e) {
      System.err.println("Server exception: " + e);
    }
  }

}
