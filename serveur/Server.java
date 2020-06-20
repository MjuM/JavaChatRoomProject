package serveur;

import java.net.DatagramSocket;

public class Server {

	
	private static DatagramSocket mysocket;
	
	public static void run(int port) {
		try {
			mysocket = new DatagramSocket(port);
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
		
	}
	private static void broadcast() {
		
	}

}
