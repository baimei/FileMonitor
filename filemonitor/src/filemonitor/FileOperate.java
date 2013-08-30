package filemonitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

public class FileOperate {

	public FileOperate() {
	}

	public String readText(String filePathAndName, String encoding) {
		encoding = encoding.trim();
		StringBuffer str = new StringBuffer("");
		String st = "";
		FileInputStream fs;

		try {
			fs = new FileInputStream(filePathAndName);
			InputStreamReader isr;
			if (encoding.equals("") || encoding == null) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding);
			}
			BufferedReader br = new BufferedReader(isr);
			String data = "";
			while ((data = br.readLine()) != null) {
				str.append(data + "\n ");
			}
			st = str.toString();
			fs.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			st = " 文件不存在，请确认文件路径与文件名称！！！";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			st = "文件编码方式不支持！！";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			st = e.getMessage();
		}
		return st;

	}

	public void createFolder(String folderPath) throws IOException {
		File myFilePath = new File(folderPath);
		if (myFilePath.exists()) {
			throw (new IOException("文件目录已经存在"));
		}
		if (myFilePath.isFile()) {
			throw (new IOException("请输入文件目录"));
		}

		myFilePath.mkdir();

	}

	public void createFolders(String folderPath, String paths)
			throws IOException {
		StringTokenizer st = new StringTokenizer(paths, "|");

		for (; st.hasMoreTokens();) {
			String path = st.nextToken().trim();
			if (folderPath.lastIndexOf("\\") == -1) {
				createFolder(folderPath + path);
			} else {
				createFolder(folderPath + "\\" + path);
			}

		}

	}

	public void createFile(String filePathAndName, String fileContent)
			throws IOException {
		filePathAndName = filePathAndName.trim();
		File myFile = new File(filePathAndName);
		if (myFile.exists()) {
			throw (new IOException("文件已经存在"));
		}
		if (myFile.isDirectory()) {
			throw (new IOException("请输入文件路径和名称"));
		}
		FileWriter writerFile = new FileWriter(myFile);
		PrintWriter printFile = new PrintWriter(writerFile);
		printFile.println(fileContent);
		printFile.close();
		writerFile.close();

	}

	public void createFile(String filePathAndName, String fileContent,
			String encoding) throws IOException {
		filePathAndName = filePathAndName.trim();
		File myFile = new File(filePathAndName);
		if (myFile.exists()) {
			throw (new IOException("文件已经存在"));
		}
		if (myFile.isDirectory()) {
			throw (new IOException("请输入文件路径和名称"));
		}

		PrintWriter printFile = new PrintWriter(myFile, encoding);
		printFile.println(fileContent);
		printFile.close();

	}

	public void deleteFile(String filePathAndName) throws IOException {
		filePathAndName = filePathAndName.trim();
		File myFile = new File(filePathAndName);
		if (!myFile.exists()) {
			throw (new IOException("文件不存在！！"));

		}
		if (!myFile.isFile()) {
			throw (new IOException(filePathAndName + " ： 不是一个文件"));
		}
		myFile.delete();

	}

	public void deleteFolder(String folderPath) throws IOException {
		folderPath = folderPath.trim();

		File myFile = new File(folderPath);
		if (myFile.exists()) {
			deleteAllFile(folderPath);
			myFile.delete();
		} else {
			throw (new IOException("目录不存在"));
		}

	}

	public void deleteAllFile(String folderPath) throws IOException {
		folderPath = folderPath.trim();
		File myFile = new File(folderPath);

		if (!myFile.exists()) {
			throw (new IOException("目录不存在！！"));

		}
		if (!myFile.isDirectory()) {
			throw (new IOException(folderPath + " :不是一个目录！！"));
		}
		String fileList[] = myFile.list();
		for (int i = 0; i < fileList.length; i++) {
			File temp = null;
			if (folderPath.endsWith(File.separator)) {
				temp = new File(folderPath + fileList[i]);
			} else {
				temp = new File(folderPath + File.separator + fileList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				deleteAllFile(folderPath + "\\" + fileList[i]);
				deleteFolder(folderPath + "\\" + fileList[i]);

			}
		}

	}

	public void copyFile(String oldFilePathAndName, String newFilePath)
			throws IOException {

		int byteread = 0;
		oldFilePathAndName = oldFilePathAndName.trim();
		newFilePath = newFilePath.trim();
		File oldFile = new File(oldFilePathAndName);

		File newFile = new File(newFilePath);

		if (!oldFile.exists()) {
			throw (new IOException("源文件不存在"));
		}
		if (!oldFile.isFile()) {
			throw (new IOException(oldFilePathAndName + " ：不是文件"));
		}
		String address[] = oldFilePathAndName.split("\\\\");
		String path = "";
		String filename = address[address.length - 1];
		if (!newFile.exists()) {
			newFile.mkdirs();
		}

		if (newFilePath.endsWith("\\"))
			path = newFilePath + filename;
		else
			path = newFilePath + "\\" + filename;
	    	newFile = new File(path);
		if (!newFile.exists()) {
			newFile.createNewFile();
		} else {

		}
		FileInputStream inStream = new FileInputStream(oldFilePathAndName);
		FileOutputStream outStream = new FileOutputStream(path);
		byte buffer[] = new byte[1024];
		while ((byteread = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, byteread);
		}
		inStream.close();
		outStream.close();

	}

	public void copyFolder(String oldFolderPath, String newFolderPath)
			throws IOException {
		System.out.println(oldFolderPath);
		oldFolderPath = oldFolderPath.trim();
		newFolderPath = newFolderPath.trim();
		File oldFolder = new File(oldFolderPath);
		File newFolder = new File(newFolderPath);
		if (!newFolder.exists()) {
			newFolder.mkdirs();
		}
		if (!oldFolder.exists()) {
			throw (new IOException("源目录不存在！！"));
		}
		if (newFolder.isFile()) {
			throw (new IOException("请输入目的目录名！！"));
		}
		if (oldFolder.isFile()) {
			throw (new IOException("请输入源目录名！！"));
		}

		if (oldFolder.isFile()) {
			throw (new IOException("请输入源目录名！！"));
		}
		String fileList[] = oldFolder.list();
		for (int i = 0; i < fileList.length; i++) {
			File temp = null;
			if (oldFolderPath.endsWith(File.separator)) {
				temp = new File(oldFolderPath + fileList[i]);
			} else {
				temp = new File(oldFolderPath + File.separator + fileList[i]);
			}
			if (temp.isFile()) {

				copyFile(oldFolderPath + "\\" + fileList[i], newFolderPath);
			}
			if (temp.isDirectory()) {
				copyFolder(oldFolderPath + "\\" + fileList[i], newFolderPath
						+ "\\" + fileList[i]);
			}
		}

	}

	public void moveFile(String oldFilePathAndName, String newFilePath)
			throws IOException {
		
		copyFile(oldFilePathAndName, newFilePath);
		deleteFile(oldFilePathAndName);
	}

	public void moveFolder(String oldFolderPath, String newFolderPath)
			throws IOException {
		oldFolderPath=oldFolderPath.trim();
		newFolderPath=newFolderPath.trim();
		String address[]=oldFolderPath.split("\\\\");
		newFolderPath=newFolderPath+"\\"+address[address.length-1];
		copyFolder(oldFolderPath, newFolderPath);
		deleteFolder(oldFolderPath);
	}
	public void writeFile(String filePathAndName,String content) throws IOException{
		File myFile=new File(filePathAndName);
		if(!myFile.exists()){
			myFile.createNewFile();
		}
		FileWriter writerFile = new FileWriter(myFile,true);
	  	writerFile.write(content);
	
		writerFile.close();
	}

}
