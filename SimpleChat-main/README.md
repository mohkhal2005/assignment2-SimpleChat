


 
Rapport de tests
Testcase	Pass/Fail
Testcase 2001
Server startup check with default arguments.

Instructions:
•	Start the server program.
Expected result:
•	The server reports that it is listening for clients by displaying the following message:
o	« Server listening for clients on port 5555 »
•	The server console waits for user input.
Cleanup: 
•	Terminate the server program.	PASS
Testcase 2002
Client startup check without a login.

Instructions:
•	Start the Client program without specifying the loginID as an argument.
Expected result:
•	The client reports it cannot connect without a login by displaying:
o	« ERROR - No login ID specified.  Connection aborted.”
•	The client terminates.
Cleanup: (if client is still active)
•	Terminate the client program.	PASS
Testcase 2003
Client startup check with a login and without a server.

Instructions:
•	Start the Client program while specifying loginID as an argument.
Expected result:
•	The client reports it cannot connect to a server by displaying:
o	« ERROR - Can't setup connection! Terminating client. »
•	The client terminates.
Cleanup: (if client is still active)
•	Terminate the client program.	PASS
Testcase 2004
Client connection with default arguments.

Instructions:
•	Start a server (Testcase 2001, instruction 1).
•	Start a client (Testcase 2003, instruction 1).
Expected result:
•	The server displays the following messages in sequence:
o	« A new client has connected to the server. »
o	« Message received: #login <loginID> from null. »
o	« <loginID> has logged on. »
o	Note: the server specifies that it received a message from null as this is the first message received from this client. It will record the loginID of this client for later messages. Hence, for later messages, it should display:
	« Message received: <user input> from <loginID> »
•	Where <user input> is the content of the message received and <loginID> is the loginID of the sending client. 
•	The client displays message:
o	« <loginID> has logged on. »
•	The client and the server wait for user input.
Cleanup: (unless proceeding to Testcase 2005)
•	Terminate the client program.
•	Terminate the server program.	PASS
Testcase 2005
Client Data transfer and data echo.

Instructions:
•	Start a server and a client using default arguments (Testcase 2004 instructions).
•	Once connected, type in data on the client console and press ENTER.
Expected results:
•	The message is echoed on the client side, but is preceded by the sender's loginID and the greater than symbol (">").
•	The server displays a message similar to the following:
o	« Message received: <user input> from <loginID>”
Cleanup: 
•	Terminate the client program.
•	Terminate the server program.	PASS


Testcase 2006
Multiple local connections.

Instructions:
•	Start a server and multiple clients with DIFFERENT loginIDs and connect them to the server using default arguments.  (Testcase 2005 instructions).
•	Start typing on all the client consoles AND the server console, pressing ENTER to send each message. 
Expected result:
•	All client messages are echoed as in Testcase 2005.
•	All messages from the server console are echoed on the server console and to all clients, but are preceded by "SERVER MESSAGE> ".
Cleanup:
•	Terminate the clients.
•	Terminate the server program.	PASS
Testcase 2007
Server termination command check.

Instructions:
•	Start a server (Testcase 2001 instruction 1) using default arguments.
•	Type "#quit" into the server's console.
Expected result:
•	The server quits.
Cleanup: (if the server is still active)
•	Terminate the server program.	PASS
Testcase 2008
Server close command check.

Instructions:
•	Start a server and connect a client to it. (Testcase 2004)
•	Stop the server using the #stop command.
•	Type "#close" into the server's console.
Expected result:
•	Server displays in sequence:
o	« Server has stopped listening for connections. »
o	« <loginID> has disconnected. »
•	The client displays:
o	« The server has shut down. »
•	The client terminates
Cleanup:
•	Terminate the client program.
•	Terminate the server program.	PASS
Testcase 2009
Server restart.

Instructions:
•	Start a server. 
•	Close the server using the #close command.
•	Type "#start" into the server's console.
•	Attempt to connect a client.
Expected result:
•	The server closes, restarts and then displays:
o	« Server listening for connections on port 5555. »
•	The client connects normally as described in Testcase 2004.
Cleanup:
•	Terminate the client program.
•	Type #quit to kill the server.	PASS
Testcase 2010
Client termination command check.

Instructions:
•	Start a server
•	Connect a client.
•	Type "#quit" into the client's console.
Expected result:
•	Client terminates.
Cleanup: (if client is still active)
•	Terminate the client program.	
Testcase 2011
Client logoff check.
Instructions:
•	Start a server (Testcase 1001, instruction 1), and then connect a single client to this server.
•	Type "#logoff" into this client's console.
Expected result:
•	Client disconnects and displays :
o	« Connection closed. »
Cleanup:
•	Type "#quit" to kill the client.	PASS
Testcase 2012
Starting a server on a non-default port.

Instructions:
•	Start a server while specifying port 1234 as an argument.
Expected result:
•	The server displays
o	 “Server listening for connections on port 1234.”
Cleanup:
•	Type #quit to kill the server.	PASS
Testcase 2013
Connecting a client to a non-default port.

Instructions:
•	Start a server on port 1234.
•	Start a client with the arguments: <loginID> <host> 1234 (replace the parameters with appropriate values). 
Expected result:
•	The connection occurs normally.	PASS

_________________________________
![image](https://github.com/user-attachments/assets/c273f17b-dcb7-485c-88da-d75527a39138)
