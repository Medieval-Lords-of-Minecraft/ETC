import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IgotProgram {
	public static void main(String[] args) {
		String[] toFind = new String[] { "pnrdata", "kwd", "lbl", "csdrmfr", "mnr", "optmfr",
				"refint", "upa", "tqa", "adt", "refext", "uwpmfr", "pld", "pcd", "sbcdata", "tpnmfr",
				"uoamfr", "einmfr", "lid", "srd", "chaptitl", "msc", "ffrref" };
		try {
	        Console console = System.console();
	        if(console == null && !GraphicsEnvironment.isHeadless()){
	            String filename = "IGotProgram.jar";
	            Runtime.getRuntime().exec(new String[]{"cmd","/c","start","cmd","/k","java -jar \"" + filename + "\""});
	        }else{
	            System.out.println("Starting search...");
	        }
			String currentPath = new java.io.File(".").getCanonicalPath();
			System.out.println("Searching in directory: " + currentPath);
			File dir = new File(currentPath);
			int fileCount = 0;
			int searchCount = 0;
			for (File file : dir.listFiles()) {
				if (file.getName().equals("IGotProgram.jar")) continue;
		        // Let's get XML file as String using BufferedReader
		        // FileReader uses platform's default character encoding
		        // if you need to specify a different encoding, 
		        // use InputStreamReader
		        Reader fileReader = new FileReader(file);
		        BufferedReader bufReader = new BufferedReader(fileReader);
		        
		        StringBuilder sb = new StringBuilder();
		        String line = bufReader.readLine();
		        while( line != null){
		            sb.append(line).append("\n");
		            line = bufReader.readLine();
		        }
		        String xml2String = sb.toString();
		        
		        for (String s : toFind) {
					Matcher m = Pattern.compile(s).matcher(xml2String);
					while (m.find()) {
						searchCount++;
					}
		        }
		        bufReader.close();
		        
		        fileCount++;
		        if (fileCount % 50 == 0) {
		        	System.out.println("Searched through " + fileCount + " files and found " + searchCount + " matches");
		        }
			}

        	System.out.println("Searched through " + fileCount + " files and found " + searchCount + " matches");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
