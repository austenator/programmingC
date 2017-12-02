// Austen Henry
// CS 525
// Programming Assignment C
// This program reads in a client's desired address,
// sends a get request, and prints the server's response.
import java.net.*;
import java.io.*;
import java.util.*;




public class Client {

	private boolean checkAddress(String raw) {
		return false;
	}
	
	private boolean checkPort(String raw) {
		return false;
	}
	
	private static String getUserPage() {
		String userInput = "";
		try {
			System.out.println("What page? \n-> ");
			Scanner scanner = new Scanner(System.in);
			userInput = scanner.nextLine();	
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		return userInput;
			 
	}
	
	public static void main(String[] args) {
		int numArgs = args.length;
		String hostName = "";
		int portNumber = -1;
		
		// If there are no arguments or more than two, exit.
		if( numArgs == 0 || numArgs > 2) {
			System.exit(0);
		}
		
		
		if ( numArgs == 1) {
			// Check if it is an address.
			// If not exit.
		}
		else {
			// Must be 2 args. Check if it is an address / port.
			// If not, exit.
		}
		
		try {
			String userInput = getUserPage();
			
			Socket mainSock = new Socket (hostName, portNumber);
			
			PrintWriter out = new PrintWriter(mainSock.getOutputStream(), true);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(mainSock.getInputStream()));
			
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			
			
			
			while ((userInput = stdIn.readLine()) != null) {
				out.println(userInput);
				System.out.println("From server: " + in.readLine());
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		

	}

}
