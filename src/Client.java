// Austen Henry
// CS 525
// Programming Assignment C
// This program reads in a client's desired address,
// sends a get request, and prints the server's response.

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.StringBuilder;

// Client class that handles the client side of sending a request and receiving
// and processing the server's response.
public class Client 
{
	private static final int maxLength = 100;
	
	// Quits after outputting the user specified message.
	private static void errorQuit(String message) 
	{
		System.out.println(message);
		System.exit(1);
	}

	// Checks the given string and validates it for input security.
	private static String  checkAddress(String raw) 
	{
		// Check to see if the length is greater than the maxLength of user input.
		if(raw.length() > maxLength) 
		{
			return "";
		}
		return raw;
	}
	
	// Checks the given string, parses it, and verifies it for input security.
	private static int checkPort(String raw) 
	{
		int portNumber = -1;
		try 
		{
			portNumber = Integer.parseInt(raw);
			
			if(portNumber <= 0)
			{
				throw new Exception("Port must be positive.");
			}
		}catch(Exception e) 
		{
			errorQuit("Invalid port.");
		}
		return portNumber;
	}
	
	// Handles getting the user's input for the page 
	// they want to get.
	private static String getUserPage() 
	{
		String userInput = "";
		try 
		{
			System.out.println("What page?");
			System.out.print("-> ");
			Scanner scanner = new Scanner(System.in);
			userInput = scanner.nextLine();
			
			// Check to see if their input is greater than the maxLength.
			// Error and quit with the message below if it is.
			if(userInput.length() > maxLength)
			{
				throw new Exception("Please request a page with fewer characters.");
			}
		} catch (Exception e) 
		{
			errorQuit(e.getMessage());
		}		
		return userInput;
			 
	}
	
	// This handles the creation of the get request.
	private static String makeRequest(String page, String host) {
		String format =  "GET %s HTTP/1.0\r\n"
						+"host: %s\r\n\r\n";
		return String.format(format, page, host);
	}

	// Processes the server's response and prints the entire response
	// if the request was successful. If it wasn't successful it 
	// will only print the error code.
	private static void responseHandler(StringBuilder sb)
	{
		String s = sb.toString();		
		String[] lines = s.split("\n");		
		String firstLine = "";
		
		// Only search through the first five lines. If it isn't 
		// in the first five lines it probably isn't there.
		for(int i = 0; i < 5; i++)
		{
			if(lines[i].contains("HTTP/1"))
			{
				firstLine = lines[i];
				break;
			}
		}
		
		// Error and quit if it is still empty.
		if(firstLine.equals(""))
		{
			errorQuit("Whoops something went wrong.");
		}
		else
		{
			if(firstLine.contains("200"))
			{
				System.out.print(s);
			}
			else
			{
				System.out.println(firstLine);
			}
		}
	}

	public static void main(String[] args) 
	{
		int numArgs = args.length;
		String hostName = "";
		int portNumber = -1;
		
		// If there are no arguments or more than two, exit.
		if( numArgs == 0 || numArgs > 2) 
		{
			errorQuit("Please enter either web server name, or a web server name and a port as arguments. ");
		}
		else if ( numArgs == 1) 
		{
			// Check the hostname.
			hostName = checkAddress(args[0]);
			
			// Check if it is an address.
			// If not exit.
			if(hostName.equals(""))
			{
				errorQuit("This was not a valid web server address.");
			}
			else 
			{
				// Assign the port number to the default 80.
				portNumber = 80;
			}
		}
		else 
		{
			// Must be 2 args. Check if it is an address / port.
			// If not, exit.
			hostName = checkAddress(args[0]);
			portNumber = checkPort(args[1]);
			if(hostName.equals("") || portNumber == -1) 
			{
				errorQuit("This was not a valid web server address.");
			}
		}
		
		try 
		{
			// Get the user's input for the page they want to go to.
			String userInput = getUserPage();
			StringBuilder serverResponse = new StringBuilder("From Server:\n");
			String fromServer;
			
			if(userInput.equals(""))
			{
				errorQuit("Please enter a valid page to get. ");
			}
			
			// Set up the socket and in/out streams for the socket.
			Socket mainSock = new Socket (hostName, portNumber);			
			PrintWriter out = new PrintWriter(mainSock.getOutputStream(), false);			
			BufferedReader in = new BufferedReader(new InputStreamReader(mainSock.getInputStream()));

			// Create the request based on what page the user wants.
			String request = makeRequest(userInput, hostName);

			// Send it to the socket.
			out.print(request);
			out.flush();
			
			// Read in the response from the server and append it to our string builder.
			while((fromServer = in.readLine()) != null) 
			{
				serverResponse.append(fromServer+"\n");
			}
			
			// Process and display the response to the user.
			responseHandler(serverResponse);

			// Close our streams and socket.
			out.close();
			in.close();
			mainSock.close();
			
		} catch (Exception e) 
		{
			errorQuit(e.getMessage());
		}		
	}

}
