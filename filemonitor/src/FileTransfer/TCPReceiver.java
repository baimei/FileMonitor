package FileTransfer;
import java.net.*;
import java.io.*;
import java.util.*;

public class TCPReceiver {
	int length;
	ServerSocket listener;
	Socket s;
	String filename, initString;
	byte[] buffer;
	FileOutputStream fileWriter;
	int bytesReceived, bytesToReceive;
	InputStream theInstream;
	OutputStream theOutstream;

	public TCPReceiver(int port) throws IOException {
		// Init stuff
		listener = new ServerSocket(port);
		buffer = new byte[8192];
		while (true) {

			// 1. Wait for a sender to transmit the filename
			s = listener.accept();
			theInstream = s.getInputStream();
			theOutstream = s.getOutputStream();
			
			length = theInstream.read(buffer);
			initString = new String(buffer, 0, length);
			System.out.println("111111111122"+initString);
			if (initString.equals("File")) {
				theOutstream.write((new String("OK")).getBytes());
				theOutstream.flush();
				length = theInstream.read(buffer);
				initString =new String(buffer, 0, length);
				System.out.println("111111111122"+initString);
		    	String []filepath=initString.split("::");

				filename = filepath[0];
				System.out.println("11111111111111"+filename);
				String []path=filename.split(":");
				filename="E:\\"+path[1];			
				bytesToReceive = new Integer(filepath[1]).intValue();

				System.out.println("  -- The file will be saved as: "
						+ filename);
				System.out.println("  -- Expecting to receive: "
						+ bytesToReceive + " bytes");

				// 2. Send an reply containing OK to the sender
				theOutstream.write((new String("OK")).getBytes());
				theOutstream.flush();
				// System.out.println("send something to port: "+initPacket.getPort());

				// 3. Receive the contents of the file
				fileWriter = new FileOutputStream( filename);

				while (bytesReceived < bytesToReceive) {
					length = theInstream.read(buffer);
					fileWriter.write(buffer, 0, length);
					bytesReceived = bytesReceived + length;
				}
			
				System.out.println("  -- File transfer complete.");
			} else {
				
				if(initString.equals("Folder")){
					
					theOutstream.write((new String("OK")).getBytes());	
					theOutstream.flush();
				    length=theInstream.read(buffer);
					initString=new String(buffer,0,length);
					System.out.println("111111111122"+initString);
					String [] path=initString.split(":");
			     	initString="E:"+path[1];			
					System.out.println("##########################"+initString);
					File file=new File(initString);
					if(!file.exists()){
						file.mkdirs();
						System.out.println("%%%%%%%%%%%%%%%%%");
					}
				}

			}
		}
	}
}