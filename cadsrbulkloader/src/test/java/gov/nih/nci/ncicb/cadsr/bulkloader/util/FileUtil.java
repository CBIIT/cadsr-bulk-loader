package gov.nih.nci.ncicb.cadsr.bulkloader.util;

import gov.nih.nci.ncicb.cadsr.MainTestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FileUtil
{	
	/**
	 * There is a much cleaner implementation of doing this, but good
	 * enough since it is only used for testing
	 */
	public static String getFileContent(InputStream is)
			throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		StringBuffer buff = new StringBuffer();
		
		while((line = br.readLine()) !=null)
		{
			buff.append(line + "\n");
		}

		return buff.toString();
	}
	
	public void copyFilesToWorkingDir(String workingInDir, String workingOutDir, String[] ipFiles) {
		try {
			createIPAndOPDirs(workingInDir, workingOutDir);
			for (String ipFileStr: ipFiles) {
				File fromFile = getClasspathFile(ipFileStr);
				File toFile = new File(workingInDir+File.separatorChar+fromFile.getName());
				toFile.createNewFile();
				BufferedReader in = new BufferedReader(new FileReader(fromFile));
				PrintWriter out = new PrintWriter(new FileOutputStream(toFile));
				
				try {
					String line = null;
					while ((line = in.readLine()) != null) {
						out.println(line);
					}
				} finally {
					in.close();
					out.flush();
					out.close();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createIPAndOPDirs(String workingInDir, String workingOutDir) {
		createCleanDirs(workingInDir, ".xml");
		createCleanDirs(workingOutDir, ".xml");
	}
	
	private void createCleanDirs(String dirPath, final String filePattern) {
		File dir = new File(dirPath);
		if (dir.exists()) {
			File[] files = dir.listFiles(new FileFilter() {
						public boolean accept(File pathname) {
							if (pathname.getAbsolutePath().endsWith(filePattern)) {
								return true;
							}
							return false;
						}
					});
			for (File f: files) {
				f.delete();
			}
		}
		else {
			dir.mkdirs();
		}
	}
	
	protected String getClasspath() {
		ClassLoader classLoader = FileUtil.class.getClassLoader();
		String filePath = classLoader.getResource(".").getPath();
		
		return filePath;
	}
	
	protected File getClasspathFile(String fileName) {
		String classpath = getClasspath();
		File f  = new File(classpath+fileName);
		
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}
}
