package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import serveur.ClientInfo;

public class Client {
	private DatagramSocket mysocket;
	private InetAddress address;
	private int port;
	private String name;
	private boolean running;
	public Client(String name,String address, int port) {
		try {
			this.name = name;
			this.address = InetAddress.getByName(address);
			this.port = port;
			mysocket = new DatagramSocket();
			running = true;
			listen();
			send("\\co:"+name);
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		
	}
	public void send(String message) {
	try {
			if (!message.startsWith("\\")) {
				message = name+" : "+message;
			}
			message += "\\e";
			byte[] data = message.getBytes();
			DatagramPacket packet = new DatagramPacket(data,data.length, address, port);
			mysocket.send(packet);
			System.out.println("Message envoyé à, "+address.getHostAddress()+":"+port);
			
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	private  void listen() {
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
							ClientGUI.printToConsole(message);
							//System.out.println(running);
						}
					}
				
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				
				
				
			}
		}; listenThread.start();
	}

	
	private static boolean isCommand(String message, DatagramPacket packet) {
		
		
		if(message.startsWith("\\co:")) {
			
		
			return true;
		}
		
		
		return false;
	}
	
	
	
}
