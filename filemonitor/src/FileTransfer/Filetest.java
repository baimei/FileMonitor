package FileTransfer;

import java.io.IOException;

import filemonitor.FileOperate;

public class Filetest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileOperate fileoperate=new FileOperate();
		String st=fileoperate.readText("D:\\aa.txt","GBK");
		System.out.println(st);
		try {
	/*	fileoperate.createFolder("D:\\xuliang");
			fileoperate.createFile("D:\\xuliang\\xuliang14425.txt", "���ǰ�ü");
			fileoperate.createFile("D:\\xuliang\\xuliang14246.doc", "���ǰ�ü","GB2312");
			fileoperate.createFile("D:\\xuliang\\xuliang14247.txt", "���ǰ�ü","GBK");
			fileoperate.deleteFile("D:\\xuliang");
			fileoperate.deleteFolder("D:\\xuliang");
			
			*/
		//	fileoperate.copyFolder("D:\\xuliang14", "D:\\");
     	fileoperate.moveFolder("D:\\xuliang14", "D:\\xuliang13");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
