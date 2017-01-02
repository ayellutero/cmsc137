import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client{

	public static void runClient() throws Exception{

		int bitSize = 4;
		int i = 0, packetLength = 0;					// Counter
		int syn = 0, ack = 0, synFlag = 0, ackFlag = 0; // Flags
		String line;


		//Scanner input = new Scanner(System.in);

		DatagramSocket clientSocket = new DatagramSocket();

		// Gets address of localhost
		InetAddress IPAddress = InetAddress.getByName("localhost");

		// For sending and receiving packets
		byte[] sendData = new byte[1024];
		byte[] recData = new byte[1024];

		//packetLength = packetInput.length();
		String packetInput = Main.packetTextArea.getText();
		packetLength = packetInput.length();

		System.out.println(packetInput);

		if(packetLength%4 != 0){ // Check if length is divisible by 4
			System.out.println("ERROR: Packet length should be divisible by 4!");
		}

		while(syn != packetLength-1){
			line = "";

			if(i==packetLength) break; // all packets (whole line) received

			while((i!=(syn+bitSize))){ // If syn + bitSize != packet size
				line = line + packetInput.charAt(i);
				i++;

				if(i==packetLength || i>packetLength-1){
					break;
				}
			} // enf of inner loop

			syn = i;

			System.out.println("CLIENT: Sending packet " + line);
			System.out.println("--F L A G S--\nSYN: " + syn + "\nSYN Flag: " + synFlag + "\nACK: " + ack + "\nACK Flag: " + ackFlag + "\n\n");

			line = line + "_" + syn + "_"; // Gets the packet to be sent to the server
			sendData = line.getBytes();

			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
			clientSocket.send(sendPacket);

			String[] temp0;

			DatagramPacket recPacket = new DatagramPacket(recData, recData.length);
			clientSocket.receive(recPacket);
			String line0 = new String(recPacket.getData());
			temp0 = line0.split("_");
		} // end of outer loop

		// Disconnection
		line = "FIN=1_";
		sendData = line.getBytes();

		Thread.sleep(1500);

		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket);

		System.out.println("CLIENT: Sending FIN bit");
		System.out.println("--F L A G S--\nFIN bit: 1\nSYN: " + syn + "\nSYN Flag: " + synFlag + "\nACK: " + ack + "\nACK Flag: " + ackFlag + "\n\n");


		DatagramPacket recPacket = new DatagramPacket(recData, recData.length);
		clientSocket.receive(recPacket);	
		String ackData = new String(recPacket.getData());
		String[] temp;
		temp = ackData.split("_");

		if(temp[0].equals("ACK=1 FIN=1")){
		System.out.println("CLIENT SENT: ACK bit\n");
	
		line = "ACK=1_";	
		sendData = line.getBytes();

		Thread.sleep(1500);
		DatagramPacket sendPacket0 = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket0);
		clientSocket.close();

	}
}
}