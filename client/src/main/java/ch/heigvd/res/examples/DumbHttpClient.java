package ch.heigvd.res.examples;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is not really an HTTP client, but rather a very simple program that
 * establishes a TCP connection with a real HTTP server. Once connected, the 
 * client sends "garbage" to the server (the client does not send a proper
 * HTTP request that the server would understand). The client then reads the
 * response sent back by the server and logs it onto the console.
 * 
 * @author Olivier Liechti
 */
public class DumbHttpClient {

	static final Logger LOG = Logger.getLogger(DumbHttpClient.class.getName());

	final static int BUFFER_SIZE = 1024;

	/**
	 * This method does the whole processing
	 */
	public void sendRequest() {
		Socket clientSocket = null;
		OutputStream os = null;
		InputStream is = null;
		
		try {
			clientSocket = new Socket("localhost", 2323);
			os = clientSocket.getOutputStream();
			is = clientSocket.getInputStream();

			Scanner sc= new Scanner(System.in);
			String userInput = "";

			while(userInput != "QUIT") {
				LOG.log(Level.INFO, "Start");
				ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
				byte[] buffer = new byte[BUFFER_SIZE];
				int newBytes;
				while ((newBytes = is.read(buffer)) != -1) {
					responseBuffer.write(buffer, 0, newBytes);
				}

				LOG.log(Level.INFO, "Response sent by the server: ");
				LOG.log(Level.INFO, responseBuffer.toString());

				userInput = sc.nextLine();

				os.write(userInput.getBytes());
			}
		} catch (IOException ex) {
			LOG.log(Level.SEVERE, null, ex);
		} finally {
			try {
				is.close();
			} catch (IOException ex) {
				Logger.getLogger(DumbHttpClient.class.getName()).log(Level.SEVERE, null, ex);
			}
			try {
				os.close();
			} catch (IOException ex) {
				Logger.getLogger(DumbHttpClient.class.getName()).log(Level.SEVERE, null, ex);
			}
			try {
				clientSocket.close();
			} catch (IOException ex) {
				Logger.getLogger(DumbHttpClient.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		LOG.log(Level.INFO, "QUIT");
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");

		DumbHttpClient client = new DumbHttpClient();
		client.sendRequest();

	}

}
