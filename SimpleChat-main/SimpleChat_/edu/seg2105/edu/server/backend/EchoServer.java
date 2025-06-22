package edu.seg2105.edu.server.backend;
// This file contains material supporting section 3.7 of the textbook:

import java.io.IOException;

// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

import edu.seg2105.client.common.ChatIF;

/**
 * This class overrides some of the methods in the abstract
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  //Server User Interface
  ChatIF serverUI;
  //Boolean qui refere a letat du serveur(en marche ou non)
  private boolean etat_serveur;
  
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port,ChatIF serverUI) 
  {
    super(port);
    this.serverUI = serverUI;
    etat_serveur = true;
  }

  
  //Instance methods ************************************************
  
  public EchoServer(int port) {
	  super(port);
	  etat_serveur= true;
}


/**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient(Object msg, ConnectionToClient client)
  {
	System.out.println("Message received: " + msg + " from " + client.getInfo("loginID"));
	if((boolean)client.getInfo("First Login")){
		String loginID = ((String) msg).split(" ")[1];  
		client.setInfo("loginID", loginID);
		client.setInfo("First Login", false);
		System.out.println(client.getInfo("loginID")+" has logged on.");	
	}
	else {
		if(((String) msg).split(" ")[0].equals("#login")) {
			try {
				client.sendToClient("Already logged in . Closing .");
				client.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}  
	  
    
    this.sendToAllClients((String)client.getInfo("loginID")+ ">"+ msg);
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
    etat_serveur = true;
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
    etat_serveur = false;
  }
  
  protected void clientConnected(ConnectionToClient client) {
	  System.out.println("A new client is attempting to connect to the server.");
	  client.setInfo("First Login", true);
	  

  }
  synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
	  clientDisconnected(client);
  }
  
  synchronized protected void clientDisconnected( ConnectionToClient client) {
	  
	  System.out.println((String)client.getInfo("loginID")+ "  has disconnected.");
	  this.sendToAllClients((String)client.getInfo("loginID")+" has disconnected.");
  }

  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
  
  public void handleMessageFromServerUI(String message) {
		
	  if(message.charAt(0) == '#'){
			try{
				switch(message) {
				  case "#quit":
					  	System.out.println("Closing Server");
					  	System.exit(0);
					  	break;
					  	
			  	  case "#stop":
			  		  	System.out.println("No longer listening for new clients");
			  		  	stopListening();
			  		  	break;
			  	  case "#close":
			  		  	System.out.println("Closing all connections");
			  	  		close();
			  	  		break;
			  	  case  "#setport":
			  		if(!etat_serveur) {
				  		setPort(Integer.parseInt(message.split(" ")[1]));
				  		System.out.println("Setting Port to: " +message.split(" ")[1]);
				  	}
					else{
						 System.out.println("Server running. Close it to set a port");
					}
			  		break;
			  	  case "#start":
			  		if(!isListening()) {
			  			listen();
				  	}
					else{
						 System.out.println("Server Already listening");
						 }
			  		break;
			  	  case "getport":
			  		  serverUI.display("Port: "+ getPort());
			  		  break;
			  		
			  	  default :
			  		System.out.println("Unvalid Command");
			  		  
			  }
			}
			catch(IOException e){
				//System.out.println(e);
			}
		}
	  else{
		  serverUI.display("SERVER MSG>" + message);
		  sendToAllClients("SERVER MSG>" + message); }
	  }
}
//End of EchoServer class