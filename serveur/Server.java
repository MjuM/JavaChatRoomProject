package serveur;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

	private static boolean running;
	
	private static DatagramSocket mysocket;
	
	public static void launch(int port) {
		try {
			mysocket = new DatagramSocket(port);
			
			running = true;
			listen();
			System.out.println("Le serveur a été lancé sur le port: "+port);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void arret() {
		
	}
	private static void send() {
		
	}
	private static void listen() {
		Thread listenThread = new Thread("JavaChatRoom Listener") {
			public void run() {
				try {
					while(running) {
						byte[] data = new byte[1024];
						DatagramPacket packet = new DatagramPacket(data, data.length);
						mysocket.receive(packet);
						
						String message = new String(data);
						message = message.substring(0, message.indexOf("\\e")); // backslash e is the end of the msg sent by the data
						
						broadcast(message);
					}
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			}
		}; listenThread.start();
	}
	private static void broadcast(String message) {
		
	}

}
