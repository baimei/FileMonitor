package FileTransfer;
import java.net.*;
import java.io.*;

/**
 * UDPSender is an implementation of the Sender interface, using UDP as the transport protocol.
 * The object is bound to a specified receiver host and port when created, and is able to 
 * send the contents of a file to this receiver.
 *
 * @author Alex Andersen (alex@daimi.au.dk)
 */
public class TCPSender implements Sender{
    private File theFile;
    private FileInputStream fileReader;
    private Socket s;
    private int fileLength, currentPos, bytesRead, toPort, length;
    private byte[]  msg, buffer;
    private String toHost,initReply;
    private InetAddress toAddress;
    private OutputStream theOutstream; 
    private InputStream theInstream;

    /**
     * Class constructor.
     * Creates a new UDPSender object capable of sending a file to the specified address and port.
     *
     * @param address  the address of the receiving host
     * @param port    the listening port on the receiving host
     */
    public TCPSender(InetAddress address, int port) throws IOException{
	toPort = port;
	toAddress = address;
	msg = new byte[8192];
	buffer = new byte[8192];
	s = new Socket(toAddress, toPort);
	theOutstream = s.getOutputStream();
	theInstream = s.getInputStream();
    }
    

    /**
     * Sends a file to the bound host.
     * Reads the contents of the specified file, and sends it via UDP to the host 
     * and port specified at when the object was created.
     *
     * @param theFile  the file to send
     */

    public void sendFile(File theFile) throws IOException {
		// Init stuff
		fileReader = new FileInputStream(theFile);
		fileLength = fileReader.available();

		System.out.println(" -- Filename: " + theFile.getName());
		System.out.println(" -- Bytes to send: " + fileLength);
		
		theOutstream.write(("File").getBytes());
		theOutstream.flush();
	
		length = 0;
		while (length <= 0){
		    length = theInstream.read(buffer);
		    if (length>0) initReply = (new String(buffer, 0, length));
		}
	
		theOutstream.write((theFile.getAbsolutePath() + "::" + fileLength).getBytes());
		theOutstream.flush();
		// 2. Wait for a reply from the receiver
		
		length = 0;
		while (length <= 0){
		    length = theInstream.read(buffer);
		    if (length>0) initReply = (new String(buffer, 0, length));
		}

		// 3. Send the content of the file
		if (initReply.equals("OK"))
	    {
		System.out.println("  -- Got OK from receiver - sending the file ");

		
		while (currentPos<fileLength){
		    //System.out.println("Will read at pos: "+currentPos);
		    bytesRead = fileReader.read(msg);
		    theOutstream.write(msg);
		   
		    //System.out.println("Bytes read: "+bytesRead);
		    currentPos = currentPos + bytesRead;
		}
		theOutstream.flush();
		
		System.out.println("  -- File transfer complete...");
	    }
	else{System.out.println("  -- Recieved something other than OK... exiting");}
    
	}

	public void sendFolder(File folderPath) throws IOException {
		
		theOutstream.write(("Folder").getBytes());
		theOutstream.flush();
		length = 0;
		while (length <= 0){
		    length = theInstream.read(buffer);
		    if (length>0) initReply = (new String(buffer, 0, length));
		}
		theOutstream.write((folderPath.getAbsoluteFile().toString()).getBytes());
		theOutstream.flush();
		

	}

	public void sendFoldersALL(File folderPath) throws IOException {
		// Init stuff
		if (!folderPath.exists()) {
			new IOException(folderPath.getAbsolutePath() + "不存在");
		}
		if (folderPath.isDirectory()) {
			sendFolder(folderPath);
			System.out.println("会更好的获得更好"+folderPath.getAbsolutePath());
			String fileList[] = folderPath.list();
			for (int i = 0; i < fileList.length; i++) {
				System.out.println("会更好的获得更好"+fileList[i]+"        "+fileList.length);
				File temp = null;
				if (folderPath.getAbsolutePath().endsWith(File.separator)) {
					temp = new File(folderPath.getAbsolutePath() + fileList[i]);
				} else {
					temp = new File(folderPath.getAbsolutePath()
							+ File.separator + fileList[i]);
				}
				System.out.println("会更好的获得更好"+temp.getAbsolutePath());
				if (temp.isFile()) {
					sendFile(temp);
					System.out.println("会更好的获得更好111");
				}
				if (temp.isDirectory()) {
					sendFoldersALL(temp);
					System.out.println("会更好的获得更好222");
				}
			}
		}

	}
	}