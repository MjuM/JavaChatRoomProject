package serveur;

import static org.junit.jupiter.api.Assertions.*;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import mainPackage.Context;


class ServerTest {
	private static DatagramSocket mysocket;
	
	private static ArrayList<ClientInfo> clients = new ArrayList<ClientInfo>();
	
	String message; DatagramPacket packet;
	
	@BeforeEach
	void setUp() throws Exception {
		Server.launch(2003);
		mysocket = new DatagramSocket(2004);
		
		
		
		
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		try {
			
			Context context = new Context(new Server());
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			mysocket.receive(packet);
			clients.add(new ClientInfo("test", 0,packet.getAddress(), packet.getPort()));
			Server.send("test",clients.get(0).getAddress(),clients.get(0).getPort());
			assertTrue(context.executeStrategy("\\dco:",packet), "Erreur Commande DC");
			assertTrue(context.executeStrategy("\\co:",packet), "Erreur Commande CO");
			assertTrue(context.executeStrategy("\\!:",packet), "Erreur Commande !");
			
		} catch(Exception e) {
			fail("Error server Send");
			e.printStackTrace();
		}
	
	
		//fail("Not yet implemented");
	}
	

}
