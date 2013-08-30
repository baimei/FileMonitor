package filemonitor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyException;
import net.contentobjects.jnotify.JNotifyListener;

public class FileMonitor extends Thread {
	private FileOperate fileOPerate = new FileOperate();
	private List<String> records = new ArrayList<String>();
	private String filePathAndName = "E:\\records.txt";

	public FileMonitor() {

	}

	class Listener implements JNotifyListener {

		void print(String s) {
			System.out.println(s);
		}

		@Override
		public void fileCreated(int arg0, String arg1, String arg2) {
			// TODO Auto-generated method stub

			String filepath = arg1 + "\\" + arg2;
			// System.out.println(filepath);
			File file = new File(filepath);
			String record = "";
			if (file.isFile())
				record = "CreateFile::" + arg1 + "\\" + arg2 + "\r\n";
			if (file.isDirectory())
				record = "CreateFolder::" + arg1 + "\\" + arg2 + "\r\n";
			addRecords(record);
			try {
				writeFile(filePathAndName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/*
			 * if(!file.isDirectory()){
			 * print("文件："+arg1+":"+arg2+"大小为："+file.length());
			 * fileOPerate.writeFile("E:\\record.txt",
			 * "CreateFile#"+arg1+"#"+arg2+"\r\n"); }else{
			 * print("目录："+arg1+":"+arg2+"大小为："+file.length());
			 * fileOPerate.writeFile("E:\\record.txt",
			 * "CreateFile#"+arg1+"#"+arg2+"\r\n"); } } catch (IOException e) {
			 * // TODO Auto-generated catch block e.printStackTrace(); }
			 */

		}

		@Override
		public void fileDeleted(int arg0, String arg1, String arg2) {
			// TODO Auto-generated method stub
			print("Deleted:" + arg1 + ":" + arg2);
			String record = "Delete::" + arg1 + "\\" + arg2 + "\r\n";
			String ss = arg1 + "\\" + arg2;
			System.out.println(ss);
			/*
			 * for(int i=0;i<records.size();i++){
			 * if(((String)records.get(i)).indexOf(ss)!=-1){ records.remove(i);
			 * } }
			 */
			addRecords(record);
			try {
				writeFile(filePathAndName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			for (int i = 0; i < records.size(); i++)
				System.out.println(records.get(i));
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		}

		@Override
		public void fileModified(int arg0, String arg1, String arg2) {
			// TODO Auto-generated method stub

			String filepath = arg1 + "\\" + arg2;
			System.out.println(filepath);
			File file = new File(filepath);
			String record = "";
			if (file.isFile()) {
				print("Modified:" + arg1 + ":" + arg2);
				record = "Modifie::" + arg1 + "\\" + arg2 + "\r\n";
				addRecords(record);
			}
			try {
				writeFile(filePathAndName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void fileRenamed(int arg0, String arg1, String arg2, String arg3) {
			// TODO Auto-generated method stub
			print("Renamed:" + arg1 + ":" + arg2 + "--->" + arg1 + ":" + arg3);
			String filepath = arg1 + "\\" + arg3;
			System.out.println(filepath);
			File file = new File(filepath);
			String record = "Rename#" + arg1 + "\\" + arg2 + "#" + arg3
					+ "\r\n";
			addRecords(record);
			try {
				writeFile(filePathAndName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*
	 * public static void main(String[] args) { // TODO Auto-generated method
	 * stub try { new FileMonitor().sample();
	 * System.out.print("#############################");
	 * 
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */

	public void run() {
		// path to watch
		// String path = System.getProperty("user.home");
		String path = "D:\\Documents\\Downloads";
		// watch mask, specify events you care about,
		// or JNotify.FILE_ANY for all events.
		int mask = JNotify.FILE_CREATED | JNotify.FILE_DELETED
				| JNotify.FILE_MODIFIED | JNotify.FILE_RENAMED;

		// watch subtree?
		boolean watchSubtree = true;

		// add actual watch
		int watchID;
		try {

			watchID = JNotify
					.addWatch(path, mask, watchSubtree, new Listener());

			Thread.sleep(1000000);

			// to remove watch the watch
			boolean res = JNotify.removeWatch(watchID);
			if (!res) {
				// invalid watch ID specified.
			}
		} catch (JNotifyException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// sleep a little, the application will exit if you
		// don't (watching is asynchronous), depending on your
		// application, this may not be required

	}

	public void addRecords(String record) {
		if (records.indexOf(record) == -1) {
			records.add(record);

		}

	}

	public void writeFile(String filePathAndName) throws IOException {
		File myFile = new File(filePathAndName);
		if (!myFile.exists()) {
			myFile.createNewFile();
		}
		FileWriter writerFile = new FileWriter(myFile, false);
		String s = "";
		for (int i = 0; i < records.size(); i++)
			s = s + records.get(i);
		writerFile.write(s);

		writerFile.close();
	}

	public FileOperate getFileOPerate() {
		return fileOPerate;
	}

	public void setFileOPerate(FileOperate fileOPerate) {
		this.fileOPerate = fileOPerate;
	}

	public List<String> getRecords() {
		return records;
	}

	public void setRecords(List<String> records) {
		this.records = records;
	}

	public String getFilePathAndName() {
		return filePathAndName;
	}

	public void setFilePathAndName(String filePathAndName) {
		this.filePathAndName = filePathAndName;
	}

}
