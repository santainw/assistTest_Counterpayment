package clearLog.app;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ClearLog {
	@FunctionalInterface
	interface Calculator{
		 int apply(int value1, int value2);
		 
		 static void main(String... args) {
		        Calculator add = (x, y) -> x + y;
		        Calculator minus = (x, y) -> x - y;
		        Calculator multiply = (x, y) -> x * y;

		        System.out.println(add.apply(1, 2));
		        System.out.println(minus.apply(2, 1));
		        System.out.println(multiply.apply(1, 2));
		    }
	}
	public static void main(String[] args) {
		Calculator add = (x, y) -> x + y;
        Calculator minus = (x, y) -> x - y;
        Calculator multiply = (x, y) -> x * y;

        System.out.println(add.apply(1, 2));
        System.out.println(minus.apply(2, 1));
        System.out.println(multiply.apply(1, 2));
		// TODO Auto-generated method stub
		System.out.println("=========Welcome to Ohm Tool==========");

		Scanner Sc = new Scanner(System.in);

//		System.out.println("30009999".matches("^([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-1]{1})"
//								+ "([0]{1}[1-3|5|7-8]{1}|[1]{1}[0]{1}|[1]{1}[2]{1})[0-9]{4}$"
//								+ "|^([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0]{1})"
//								+ "([0]{1}[4|6|9]|[1]{1}[1]{1}){1}[0-9]{4}$"));
//		System.out.println("99990001".matches("^[0-9]{4}"
//				+ "([0]{1}[1-3|5|7-8]{1}|[1]{1}[0]{1}|[1]{1}[2]{1})[0-9]{4}([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-1]{1})$"
//				+ "|^[0-9]{4}"
//				+ "([0]{1}[4|6|9]|[1]{1}[1]{1})([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0]{1})$"));
		
		System.out.println("05052018".matches("^[0-9]{4}" + 
				"([0]{1}[1-3|5|7-8]{1}|[1]{1}[0]{1}|[1]{1}[2]{1})([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-1]{1})$" + 
				"|^[0-9]{4}" + 
				"([0]{1}[4|6|9]|[1]{1}[1]{1})([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0]{1})$"));
		int i=0;
		while(true) {
			System.out.println("1 for clearLog");
			System.out.println("2 for move backup to source");
			System.out.print("Choose you command : ");
			try {
				i = Sc.nextInt();
			}catch(Exception e) {
				System.out.println("Error input must be number!");
			}
			if(i==1) {
				System.out.println("Start clearLog");
				File ktbDirectory = new File("/u01/counterpayment/log/KTB/");
				File csDirectory = new File("/u01/counterpayment/log/CounterService/");
				File zipDirectory = new File("/u01/counterpayment/log/Zip/");
				
				List<File> listDirect = new ArrayList<File>();
				listDirect.add(ktbDirectory);
				listDirect.add(csDirectory);
				listDirect.add(zipDirectory);
				
				for(File curFile: listDirect) {
					File[] filesInDir = curFile.listFiles();
					System.out.println("PATH : "+curFile.getPath());
					if(filesInDir != null) {
						for(File fileChild : filesInDir) {
							System.out.println("Name : "+fileChild.getName());
							fileChild.delete();
							System.out.println("Deleted");
						}
					}
				}
			}else if(i==2) {
				System.out.println("Start move backup to source");
				File ktbDirectory = new File("/u01/counterpayment/backup/KTB/");
				File csDirectory = new File("/u01/counterpayment/backup/CounterService/");
				
				File ktbDestination = new File("/u01/counterpayment/source/KTB/");
				File csDestination = new File("/u01/counterpayment/source/CounterService/");
				
				List<File> listDirect = new ArrayList<File>();
				listDirect.add(ktbDirectory);
				listDirect.add(csDirectory);
				
				List<File> listDestintaion = new ArrayList<File>();
				listDestintaion.add(ktbDestination);
				listDestintaion.add(csDestination);
				int swap = 0;
				for(File curFile: listDirect) {
					File[] filesInDir = curFile.listFiles();
					System.out.println("PATH : "+curFile.getPath());
					if(filesInDir != null) {
						for(File fileChild : filesInDir) {
							System.out.println("Move : " + fileChild.getName());
							moveFile(curFile.getPath()+"/"+fileChild.getName(), listDestintaion.get(swap).getPath()+"/"+fileChild.getName());
							System.out.println("to : " + listDestintaion.get(swap).getPath());
						}
					}
					swap++;
				}
			}
			System.out.println("========================================");
		}

	}
	public static boolean moveFile(String sourcePath, String targetPath) {
	    boolean fileMoved = true;
	    try {
	        Files.move(Paths.get(sourcePath), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
	    } catch (Exception e) {
	        fileMoved = false;
	        e.printStackTrace();
	    }
	    return fileMoved;
	}

	public static String thai(String data) {
		try {
			byte strByte[] = data.getBytes();
			return new String(strByte, "TIS-620");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
