package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;

import javax.swing.JTextField;
import java.awt.Scrollbar;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;

public class ClientGUI {

	private JFrame frame;
	private JTextField messageField;
	private static JTextArea textArea = new JTextArea();
	private static JTextArea textArea_1 = new JTextArea();
	private String username;
	private Client client;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					ClientGUI window = new ClientGUI();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	public ClientGUI() {
		initialize();
		String name = JOptionPane.showInputDialog("Enter Name");
		client = new Client(name,"localhost",2002);
		username = name;
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Java ChatRoom");
		frame.setBounds(100, 100, 564, 406);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		textArea.setBackground(new Color(224, 255, 255));
		
		
		textArea.setEditable(false);
		
		WindowListener listener = new WindowAdapter() {
	         public void windowClosing(WindowEvent evt) {
	            Frame frame = (Frame) evt.getSource();
	            System.out.println("Closing = "+frame.getTitle());
	            client.send("\\dco:"+username);
	           // client.send(username);
	         }
	      };
		
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		textArea_1.setBackground(Color.GREEN);
		textArea_1.setEditable(false);
		scrollPane.setColumnHeaderView(textArea_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(e -> {
			if(!messageField.getText().equals("")) {
			client.send(messageField.getText());
			messageField.setText("");
			}
			
		});
		
		messageField = new JTextField();
		panel.add(messageField);
		messageField.setColumns(45);
		panel.add(btnNewButton);
		frame.addWindowListener(listener);
		frame.setLocationRelativeTo(null);
	}
	
		public static void printToTop(String message) {
			textArea_1.setText(textArea_1.getText()+message+"\n");
		}
		public static void clearTop() {
			textArea_1.setText("");
		}
		public static void printToConsole(String message) {
			textArea.setText(textArea.getText()+message+"\n");
		}
}
