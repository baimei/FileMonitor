package FileTransfer;

import java.net.*;
import java.io.*;

/**
 * UDPSender is an implementation of the Sender interface, using UDP as the
 * transport protocol. The object is bound to a specified receiver host and port
 * when created, and is able to send the contents of a file to this receiver.
 * 
 * @author Alex Andersen (alex@daimi.au.dk)
 */
public class UDPSender implements Sender {
	private File theFile;
	private FileInputStream fileReader;
	private DatagramSocket s;
	private int fileLength, currentPos, bytesRead, toPort;
	private byte[] msg, buffer;
	private String toHost, initReply;
	private InetAddress toAddress;

	/**
	 * Class constructor. Creates a new UDPSender object capable of sending a
	 * file to the specified address and port.
	 * 
	 * @param address
	 *            the address of the receiving host
	 * @param port
	 *            the listening port on the receiving host
	 */
	public UDPSender(InetAddress address, int port) throws IOException {
		toPort = port;
		toAddress = address;
		msg = new byte[8192];
		buffer = new byte[8192];
		s = new DatagramSocket();
		s.connect(toAddress, toPort);
	}

	/**
	 * Sends a file to the bound host. Reads the contents of the specified file,
	 * and sends it via UDP to the host and port specified at when the object
	 * was created.
	 * 
	 * @param theFile
	 *            the file to send
	 */
	public void sendFile(File theFile) throws IOException {
		// Init stuff
		fileReader = new FileInputStream(theFile);
		fileLength = fileReader.available();

		System.out.println(" -- Filename: " + theFile.getName());
		System.out.println(" -- Bytes to send: " + fileLength);
		send(("File").getBytes());
		// 1. Send the filename to the receiver
		send((theFile.getAbsolutePath() + "::" + fileLength).getBytes());

		// 2. Wait for a reply from the receiver
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		s.receive(reply);
		// initReply = (new String(reply.getData(), 0, reply.getLength()));

		// 3. Send the content of the file
		if (new String(reply.getData(), 0, reply.getLength()).equals("OK")) {
			System.out.println("  -- Got OK from receiver - sending the file ");

			while (currentPos < fileLength) {
				// System.out.println("Will read at pos: "+currentPos);
				bytesRead = fileReader.read(msg);
				send(msg);
				// System.out.println("Bytes read: "+bytesRead);
				currentPos = currentPos + bytesRead;
			}
			System.out.println("  -- File transfer complete...");
		} else {
			System.out.println("Recieved something other than OK... exiting");
		}
	}

	public void sendFolder(File folderPath) throws IOException {	
		System.out.println(" Folder Name: " + folderPath.getAbsoluteFile());	
		send(("Folder").getBytes());
		send((folderPath.getAbsoluteFile().toString()).getBytes());	
	}

	public void sendFoldersALL(File folderPath) throws IOException {
		// Init stuff
		if (!folderPath.exists()) {
			new IOException(folderPath.getAbsolutePath() + "������");
		}
		if (folderPath.isDirectory()) {
			sendFolder(folderPath);
			String fileList[] = folderPath.list();
			for (int i = 0; i < fileList.length; i++) {
				File temp = null;
				if (folderPath.getAbsolutePath().endsWith(File.separator)) {
					temp = new File(folderPath.getAbsolutePath() + fileList[i]);
				} else {
					temp = new File(folderPath.getAbsolutePath()
							+ File.separator + fileList[i]);
				}
				if (temp.isFile()) {
					sendFile(temp);
				}
				if (temp.isDirectory()) {
					sendFoldersALL(temp);
				}
			}
		}

	}

	private void send(byte[] message) throws IOException {
		DatagramPacket packet = new DatagramPacket(message, message.length);
		s.send(packet);
	}
}