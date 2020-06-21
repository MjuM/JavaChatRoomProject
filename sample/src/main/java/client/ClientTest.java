package client;

import static org.junit.jupiter.api.Assertions.*;

import java.net.DatagramPacket;
import java.net.DatagramSocket;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mainPackage.Context;
import serveur.Server;

class ClientTest {
	private DatagramSocket mysocket;
	private Client client;
	@BeforeEach
	void setUp() throws Exception {
		mysocket = new DatagramSocket();
		Server.launch(2003);
		client = new Client("test","localhost",2003);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		try {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			mysocket.receive(packet);
			client.send("Test");
			Context context = new Context(new Client());
			assertTrue(context.executeStrategy("\\dco:",packet), "Erreur Commande DC");
			assertTrue(context.executeStrategy("\\co:",packet), "Erreur Commande CO");
			assertTrue(context.executeStrategy("\\!:",packet), "Erreur Commande !");
			
			
			
			
		} catch(Exception e) {
			fail("Not yet implemented");
			e.printStackTrace();
		}
		
	}

}
