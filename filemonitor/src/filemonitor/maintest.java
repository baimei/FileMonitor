package filemonitor;

import java.io.File;

public class maintest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Thread aa = new FileMonitor();
			aa.start();
			System.out.println("#############################");
		File file=new File("D:\\12\\as");
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getCanonicalPath());
		if(!file.exists()){
			file.createNewFile();
		}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
