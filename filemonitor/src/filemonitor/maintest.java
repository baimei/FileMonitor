package filemonitor;

import java.io.File;

public class maintest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Thread aa = new FileMonitor();
			aa.start();
			System.out.println("#############################");
		File file=new File("D:\\11.txt");
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getParent());
		

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
