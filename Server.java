import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner; 

public class Server{

	public static void runServer() throws Exception{

		int bitSize = 4;
		int i = 0;
		int syn = 0, synFlag = 0, ack = 0, ackFlag = 0;

		String tempLine = "";

		DatagramSocket serverSocket = new DatagramSocket(9876);

		byte[] recData = new byte[1024];
		byte[] sendData = new byte[1024];


		while(true){
			// Receive packets
			DatagramPacket recPacket = new DatagramPacket(recData, recData.length);
			serverSocket.receive(recPacket);
			String line = new String(recPacket.getData());
			String[] temp;
			// Parse packet received from client
			temp = line.split("_");

			// If client is finished sending packets
			if(temp[0].equals("FIN=1")){ 
				
				//Thread.sleep(1500);
				System.out.println("SERVER: Sending ACK bit and FIN bit");
				System.out.println("SERVER: Preparing for disconnection...");
				System.out.println("--F L A G S--\nSYN: " + syn + "\nSYN Flag: " + synFlag + "\nACK: " + ack + "\nACK Flag: " + ackFlag + "\n\n");


				// Get address and port of client
				InetAddress IPAddress = recPacket.getAddress();
				int port = recPacket.getPort();

				// Send back flags to client
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);
				break;
			}else if(temp[0].equals("ACK=1")){ // If packet was succesfully ACKnowledged,
				System.out.println("NO");
				Thread.sleep(1500);
				System.out.println("SUCCESS: Disconnection");
				serverSocket.close();
				break;

			}else{

				ack = Integer.parseInt(temp[1]);
				tempLine = "";

				ack++;
				ackFlag = 1;

				System.out.println("SERVER: Received packet "+temp[0]);
				System.out.println("--F L A G S--\nSYN: " + syn + "\nSYN Flag: " + synFlag + "\nACK: " + ack + "\nACK Flag: " + ackFlag + "\n\n");


				// Notify client about received packet
				InetAddress IPAddress = recPacket.getAddress();
				int port = recPacket.getPort();

				// Send data to client
				String ackData = Integer.toString(ack) + "_";
				sendData = ackData.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);
			}

		}
	}
}