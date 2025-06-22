// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package edu.seg2105.client.backend;

import java.io.IOException;

import com.lloseng.ocsf.client.AbstractClient;

import edu.seg2105.client.common.ChatIF;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  String loginID;
  
  public ChatClient(String loginID, String host, int port, ChatIF clientUI) 
    throws IOException 
  {
	  super(host, port); //Call the superclass constructor
	  this.clientUI = clientUI;
	  this.loginID = loginID;
	  try{openConnection();}
	    catch(Exception ex) {
	    System.out.println("Cannot open connection.  Awaiting command.");}
	    if(isConnected())
		sendToServer("#login "+loginID);
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message) throws IOException
  {	
	if(message.charAt(0) =='#'){
		  switch(message.split(" ")[0]) {
			  case "#quit":
				  System.out.println("Chat closed");
				  System.exit(0);
				  break;
		  	  case "#logoff":
		  		  System.out.println("Disconnected");
		  		  closeConnection();
		  		  break;
		  	  case "#sethost":
		  		
		  	  		if(!isConnected()) {
		  	  				setHost(message.split(" ")[1]);
		  	  			  	System.out.println("Host set to: " +message.split(" ")[1]);
		  	  			  	break;
		  	  		}
		  	  		else{
		  	  				 System.out.println("You're connected. Disconnect to change host");
		  	  		}
		  	  		break;
		  	  case  "#setport":
		  		  	if(!isConnected()) {
		  		  			setPort(Integer.parseInt(message.split(" ")[1]));
		  		  			System.out.println("Port set to: "+message.split(" ")[1]);
		  		  	}
		  		  	else{
					 		System.out.println("You're connected. Disconnect to change port");
		  		  	}
		  		  	break;
		  	  case "#login":
		  		if(!isConnected()) {
					openConnection();
					sendToServer(message);
			  	}
				else{
					 System.out.println("You're connected. Disconnect to Login again");
					 }
		  		break;
		  	  case "#gethost":
		  		  clientUI.display("Host: "+ getHost());
		  	  case "getport":
		  		  clientUI.display("Port: "+ getPort());
		  		  break;
		  		
		  	  default :
		  		System.out.println("Unvalid Command");
		  		  
		  }
	 }
  else {
    try
    {
      sendToServer(message);
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      		quit();
    }
  }
  }
  
	
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
  protected void connectionException(Exception exception) {
	  System.out.println("WARNING - The server has stopped listening for connections\n"
	  		+ "SERVER SHUTTING DOWN! DISCONNECTING!\n");
	  connectionClosed();
  }
  
  
}