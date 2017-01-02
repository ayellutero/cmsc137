/*
Author: Lutero, Levie Abigail D.
Section: CMSC 137 B-1L

For completion

*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Main extends JFrame implements ActionListener{
	static Server server = new Server();
	static Client client = new Client();

	JPanel window = new JPanel();
	JButton send = new JButton("SEND");
	static JTextArea packetTextArea = new JTextArea("");

	public Main(){

		send.addActionListener(this);
		window.setLayout(new GridLayout(1,2));
		window.add(packetTextArea);
		window.add(send);

		this.add(window);
		this.pack();
		this.setLocationRelativeTo(null); // sa gitna lang yung window, can't be dragged
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);

	}

	public static void main(String[] args){
		try{
			new Main(); // opens a small window where you can input data
			System.out.println("Program Running...");
			server.runServer();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==send){
			try {
				client.runClient();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
	}



}