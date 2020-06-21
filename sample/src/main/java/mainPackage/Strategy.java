package mainPackage;

import java.net.DatagramPacket;

public interface Strategy {
	public boolean isCommand(String message, DatagramPacket packet);
	public void listen();
}
