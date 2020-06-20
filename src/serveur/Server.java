package serveur;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server {
	
	private static int ClientID;
	private static ArrayList<ClientInfo> clients = new ArrayList<ClientInfo>();
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
		running = false;
		
		
	}
	private static void send(String message, InetAddress address, int port) {
		try {
			
			message += "\\e";
			byte[] data = message.getBytes();
			DatagramPacket packet = new DatagramPacket(data,data.length, address, port);
			mysocket.send(packet);
			System.out.println("Message envoyé à, "+address.getHostAddress()+":"+port);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
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
						if(!isCommand(message,packet)) {
						broadcast(message);
						}
					}
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			}
		}; listenThread.start();
	}
	private static void broadcast(String message) {
		for(ClientInfo info: clients) {
			send(message, info.getAddress(), info.getPort());
		}
		
	}
	private static void listUser(String message) {
		for(ClientInfo info: clients) {
			send("!"+message, info.getAddress(), info.getPort());
		}
		
	}
	private static boolean isCommand(String message, DatagramPacket packet) {
		
		if(message.startsWith("\\dco:")) {
			String name = message.substring(message.indexOf(":")+1);
			broadcast("User, "+name+", leave your room");
			for(int i = 0 ; i < clients.size() ; i++) {
				if(clients.get(i).getName().equals(name)) {
					clients.remove(i);
				}
			}
			broadcast("?");
			for(int i = 0 ; i < clients.size() ; i++) {
				listUser("!"+clients.get(i).getName()+" is online");
			}
			
			return true;
		}
		if(message.startsWith("\\co:")) {
			broadcast("?");
			String name = message.substring(message.indexOf(":")+1);
			
			clients.add(new ClientInfo(name, ClientID++,packet.getAddress(), packet.getPort()));
			broadcast("User, "+name+", join your room");
			//System.out.println("this is a test");
			for(int i = 0 ; i < clients.size() ; i++) {
				System.out.println(clients.get(i).getName());
			}
			return true;
		}
		if(message.startsWith("\\!:")) {
			for(int i = 0 ; i < clients.size() ; i++) {
				listUser("!"+clients.get(i).getName()+" is online");
			}
			return true;
		}
	
		
		
		return false;
	}

}
