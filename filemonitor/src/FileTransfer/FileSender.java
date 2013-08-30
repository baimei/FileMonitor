package FileTransfer;
import java.net.*;
import java.io.*;
public class FileSender{

    public static void main(String[] args) throws IOException
    {
    String arg[]=new String [4];
    arg[0]="udp";
    arg[1]="localhost";
    arg[2]="1000";
    arg[3]="D:\\xuliang";
	Sender theSender;
	int thePort = 0;
	File theFile;
	InetAddress theAddress = null;

	// Checking the arguments
	if (arg.length != 4) printError("Wrong number of arguments");
	
	if (!arg[0].equals("tcp") && !arg[0].equals("udp")) printError("Unrecognized protocol: "+arg[0]);
	
	try
	    {thePort = Integer.parseInt(arg[2]);}
	catch(NumberFormatException e)
	    { printError("The port must be a number between 0 and 65536");}
	if(thePort<0 || thePort>65536) printError("The port must be a number between 0 and 65536");
	
	try
	    {theAddress = InetAddress.getByName(arg[1]);}
	catch(UnknownHostException e)
	    {printError("The specified host could not be found on the network");}

        theFile = new File(arg[3]);
	if(!theFile.canRead()) printError("There was an error opening the specified file");



	// Create the sender object
	if (arg[0].equals("udp")) {
	    theSender = new UDPSender(theAddress, thePort);
	    theSender.sendFoldersALL(theFile);
	}
	else 
	    theSender = new TCPSender(theAddress, thePort);

	// Send the file
	
    }

    public static void printError(String error)
    {
	System.out.println(" - Error: "+error);
	System.out.println(" - Usage: FileSender [protocol] [host] [port] [filename]");
	System.exit(1);
    }
}