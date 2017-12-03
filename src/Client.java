// Austen Henry
// CS 525
// Programming Assignment C
// This program reads in a client's desired address,
// sends a get request, and prints the server's response.
import java.net.*;
import java.io.*;
import java.util.*;




public class Client {
	
	private static void errorQuit(String message) {
		System.out.println(message);
		System.exit(1);
	}

	private static String  checkAddress(String raw) {
		if(raw.length() > 100) {
			return "";
		}
		return raw;
	}
	
	private static int checkPort(String raw) {
		int portNumber = -1;
		try {
			portNumber = Integer.parseInt(raw);
		}catch(Exception e) {
			System.out.println("Invalid port.");
			portNumber = -1;
		}
		return portNumber;
	}
	
	private static String getUserPage() {
		String userInput = "";
		try {
			System.out.println("What page?");
			System.out.print("-> ");
			Scanner scanner = new Scanner(System.in);
			userInput = scanner.nextLine();	
		} catch (Exception e) {
			System.out.println(e.getMessage());
			userInput = "";
		}		
		return userInput;
			 
	}
	
	private static String makeRequest(String page, String host) {
//		snprintf(sendline, MAXSUB, 
//			     "GET %s HTTP/1.0\r\n"  // POST or GET, both tested and works. Both HTTP 1.0 HTTP 1.1 works, but sometimes 
//			     "Host: %s\r\n"     // but sometimes HTTP 1.0 works better in localhost type
//			     "Content-type: application/x-www-form-urlencoded\r\n"
//			     "Content-length: %d\r\n\r\n"
//			     "%s\r\n", page, host, (unsigned int)strlen(poststr), poststr);
		String format =  "GET %s HTTP/1.0 \r\n"
						+"Host: %s\r\n"
						+"Content-type: application/xwww-form-urlencoded\r\n\\r\\n";
		String request = String.format(format, page, host);
		return request;
	}
	
	public static void main(String[] args) {
		int numArgs = args.length;
		String hostName = "";
		int portNumber = -1;
		
		// If there are no arguments or more than two, exit.
		if( numArgs == 0 || numArgs > 2) {
			errorQuit("Please enter either web server name, or a web server name and a port as arguments. ");
		}
		
		
		if ( numArgs == 1) {
			
			hostName = checkAddress(args[0]);
			System.out.println(hostName);
			System.out.println(numArgs);
			// Check if it is an address.
			// If not exit.
			if(hostName.equals("")){
				errorQuit("This was not a valid web server address.");
			}
			else {
				hostName = args[0];
				portNumber = 80;
			}
		}
		else {
			// Must be 2 args. Check if it is an address / port.
			// If not, exit.
			hostName = checkAddress(args[0]);
			portNumber = checkPort(args[1]);
			if(hostName.equals("") || portNumber == -1) {
				errorQuit("This was not a valid web server address.");
			}
			else {
				hostName = args[0];
				portNumber = Integer.parseInt(args[1]);
			}
		}
		
		try {
			String userInput = getUserPage();
			
			if(userInput.equals("")) {
				errorQuit("Please enter a valid page to get. ");
			}
			
			Socket mainSock = new Socket (hostName, portNumber);
			
			PrintWriter out = new PrintWriter(mainSock.getOutputStream(), true);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(mainSock.getInputStream()));
			
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			
			
			
			//while ((userInput = stdIn.readLine()) != null) {
			String fromServer = "";
				String request = makeRequest(userInput, hostName);
				out.print(request);
				while((fromServer = in.readLine()) != null) {
					System.out.println("From server: " + fromServer);
				}
				
			//}
			
			out.close();
			in.close();
			stdIn.close();
			
		} catch (Exception e) {
			errorQuit(e.getMessage());
		}
		

	}

}
