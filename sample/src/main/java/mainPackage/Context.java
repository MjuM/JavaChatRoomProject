package mainPackage;
import java.net.DatagramPacket;
public class Context {
	   private Strategy strategy;

	   public Context(Strategy strategy){
	      this.strategy = strategy;
	   }
	  
	  public boolean executeStrategy(String message, DatagramPacket packet){
	      return strategy.isCommand(message, packet);
	   }
	  public void secondStrategy() {
		  	strategy.listen();
	  }
	  public void printline() {
		  System.out.println("it works");
	  }
	}