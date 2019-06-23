package clearLog.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ClearLog {
	
	public static void main(String[] args) {
		printStartApp();

		Scanner Sc = new Scanner(System.in);

		int i=0;
		boolean isRun = true;
		
		while(isRun) {
			headTool();
			System.out.println("1 for ClearLog");
			System.out.println("2 for Move backup to source");
			System.out.println("3 for Read errorLog");
			System.out.println("4 for exit program");
			System.out.println("========================================");
			
			System.out.print("Choose you command : ");
			try {
				i = Sc.nextInt();
				Integer.valueOf(i);
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
			else if( i == 3)
			{
				String[] sourceFolder = {"KTB", "CounterService"};
				for (String channel : sourceFolder) 
				{
					channel = channel.trim();
					File aDirectory = new File("/u01/counterpayment/log/" + channel);
					File[] filesInDir = aDirectory.listFiles();

					if (filesInDir != null) 
					{
						for (File fileChild : filesInDir)
						{
							if (fileChild.isFile())
							{
								int lineNo = 1;
								System.out.println("File name : " + fileChild.getName());
								try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileChild), "TIS-620"))) 
									{
										for (String line; (line = br.readLine()) != null;) 
										{
//											"ErrorLog":"
											int start = line.indexOf("\"ErrorLog\":\"") + 12;
											
//											System.out.println(line.substring(start));
											String error = line.substring(start).split("\"")[0];
											System.out.println(lineNo + "." +error);
											lineNo++;
										}
									}
									catch (Exception e) 
									{
										System.out.println(e);
									}
							}
						}
					}
				}
			}
			else if(i == 4)
			{
				isRun = false;
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
	private static String filterJson(String data) 
	{
		String result = "";
		boolean initCh = false;
		int duo = 0;
		int stampNow = 0;
		
		for(int i=0; i<data.length();i++) 
		{
			char ch = data.charAt(i);
			
			if(ch=='{') 
			{
				if(duo==0) 
				{
					stampNow = i;
				}
				initCh = true;
				duo++;
			}
			else if(ch=='}') 
			{
				duo--;
			}
			
			if(duo==0 && initCh) 
			{
				result = data.substring(stampNow, i+1);
				initCh = false;
			}
		}
		return result;
	}
	
	private static void printStartApp()
	{
		System.out.println(" \n" + 
				" █████╗ ███████╗███████╗██╗███████╗████████╗    ████████╗███████╗███████╗████████╗\n" + 
				"██╔══██╗██╔════╝██╔════╝██║██╔════╝╚══██╔══╝    ╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝\n" + 
				"███████║███████╗███████╗██║███████╗   ██║          ██║   █████╗  ███████╗   ██║   \n" + 
				"██╔══██║╚════██║╚════██║██║╚════██║   ██║          ██║   ██╔══╝  ╚════██║   ██║   \n" + 
				"██║  ██║███████║███████║██║███████║   ██║          ██║   ███████╗███████║   ██║   \n" + 
				"╚═╝  ╚═╝╚══════╝╚══════╝╚═╝╚══════╝   ╚═╝          ╚═╝   ╚══════╝╚══════╝   ╚═╝   \n" + 
				"                                                                                  \n" + 
				""
				+ "██╗   ██╗██████╗ \n" + 
				"██║   ██║╚════██╗\n" + 
				"██║   ██║ █████╔╝\n" + 
				"╚██╗ ██╔╝██╔═══╝ \n" + 
				" ╚████╔╝ ███████╗\n" + 
				"  ╚═══╝  ╚══════╝\n" + 
				"                 ");
	}
	
	private static void headTool()
	{
		System.out.println(" ██████╗ ██╗  ██╗███╗   ███╗    ████████╗ ██████╗  ██████╗ ██╗     \n" + 
				"██╔═══██╗██║  ██║████╗ ████║    ╚══██╔══╝██╔═══██╗██╔═══██╗██║     \n" + 
				"██║   ██║███████║██╔████╔██║       ██║   ██║   ██║██║   ██║██║     \n" + 
				"██║   ██║██╔══██║██║╚██╔╝██║       ██║   ██║   ██║██║   ██║██║     \n" + 
				"╚██████╔╝██║  ██║██║ ╚═╝ ██║       ██║   ╚██████╔╝╚██████╔╝███████╗\n" + 
				" ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝       ╚═╝    ╚═════╝  ╚═════╝ ╚══════╝");
	}
}
