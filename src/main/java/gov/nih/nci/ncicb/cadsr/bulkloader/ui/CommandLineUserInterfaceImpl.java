package gov.nih.nci.ncicb.cadsr.bulkloader.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommandLineUserInterfaceImpl implements UserInterface {

	private static Log log = LogFactory.getLog(CommandLineUserInterfaceImpl.class);
	private static final String SERIALIZE_FILE_NAME = "userinput.ser";
	
	public UserInput getLoadUserInput() {
		UserInput userInput = getSerializedInput();
		UserInput completedUserInput = getInput(userInput);
		serializeInput(completedUserInput);
		
		return completedUserInput;
	}
	
	public UserInput getUnloadUserInput() {
		UserInput userInput = new UserInput();
		UserInput dbDetails = getDBDetails(userInput);
		
		UserInput unloadData = getUnloadData(dbDetails);
		
		return unloadData;
	}

	private static UserInput getInput(UserInput userInput) {
		
		try {
			UserInput dbDetails = getDBDetails(userInput);
			
			UserInput processDetails = getProcessDetails(userInput);
			
			dbDetails.setInputDir(processDetails.getInputDir());
			
			return dbDetails;
		} catch (Exception e) {
			log.error("Error getting user input", e);
		}
		
		
		return userInput;
	}
	
	private static UserInput getDBDetails(UserInput userInput) {
		String dbURL = userInput.getDbURL();

		try {
			UserInput ip = null;
			if (dbURL != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String ans = null;
				while (ans == null || (!ans.equalsIgnoreCase("y") && !ans.equalsIgnoreCase("n"))) {
					System.out.print("Use database ["+dbURL+"]? [y/n]");
					ans = reader.readLine();
				}
				if (ans.equalsIgnoreCase("y")) {
					ip = userInput;
				}
				else {
					ip = new UserInput();
				}
				
				ip = getDBUserInput(ip);
				return ip;
			}
			else {
				ip = getDBUserInput(userInput);
				return ip;
			}
		} catch (Exception e) {
			log.error("Error getting db details from user", e);
		}
		
		return userInput;
	}
	
	private static UserInput getDBUserInput(UserInput userInput) {
		try {
			String dbURL = userInput.getDbURL();
			String dbUserId = userInput.getDbUser();
			String dbPassword = userInput.getDbPassword();
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			if (dbURL == null || dbURL.trim().equals("")) {
				System.out.print("Please enter the database URL: ");
				dbURL = reader.readLine();
				System.out.print("Please enter the database username: ");
				dbUserId = reader.readLine();
				System.out.print("Please enter the database password: ");
				dbPassword = reader.readLine();
				
				userInput.setDbURL(dbURL);
				userInput.setDbUser(dbUserId);
				userInput.setDbPassword(dbPassword);
			}
			else if (dbUserId == null || dbUserId.trim().equals("")) {
				System.out.print("Please enter the username for database ["+dbURL+"]: ");
				dbUserId = reader.readLine();
				System.out.print("Please enter the database password: ");
				dbPassword = reader.readLine();
				
				userInput.setDbUser(dbUserId);
				userInput.setDbPassword(dbPassword);
			}
			else {
				System.out.print("Please enter the db password for database ["+dbURL+"] for user ["+dbUserId+"]: ");
				dbPassword = reader.readLine();
				
				userInput.setDbPassword(dbPassword);
			}
			
			
		} catch (Exception e) {
			log.error("Error getting user input", e);
		}
		
		return userInput;
	}
	
	private static UserInput getProcessDetails(UserInput userInput) {
		UserInput ip = new UserInput();
		String inputDir = userInput.getInputDir();
		
		try {
			if (inputDir != null && !inputDir.trim().equals("")) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String ans = null;
				while (ans == null || (!ans.equalsIgnoreCase("y") && !ans.equalsIgnoreCase("n"))) {
					System.out.print("Use ["+inputDir+"] as input directory? [y/n]");
					ans = reader.readLine();
				}
				if (ans.equalsIgnoreCase("y")) {
					ip.setInputDir(inputDir);
				}
				else {
					ip = getProcessingInputs(userInput);
				}
			}
			else {
				ip = getProcessingInputs(userInput);
			}
		} catch (Exception e) {
			log.error("Error retrieving process details", e);
		}
		
		return ip;
	}
	
	private static UserInput getProcessingInputs(UserInput userInput) {
		String inputDir = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Please enter the input directory: ");
			inputDir = reader.readLine();
		} catch (Exception e) {
			log.error("Error getting user input", e);
		}
		userInput.setInputDir(inputDir);
		
		return userInput;
	}
	
	private static UserInput getUnloadData(UserInput userInput) {
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Please enter the unload Classification Scheme: ");
			userInput.setClassificationSchemeName(reader.readLine());
			
			System.out.print("Please enter the unload Classification Scheme Version: ");
			userInput.setClassificationSchemeVersion(reader.readLine());
			
			System.out.print("Please enter the unload Classification Scheme Item: ");
			userInput.setClassificationSchemeItemName(reader.readLine());
			
			System.out.print("Please enter the unload Classification Scheme Item Version: ");
			userInput.setClassificationSchemeItemVersion(reader.readLine());
		} catch (Exception e) {
			log.error("Error getting user input", e);
		}
		
		return userInput;
	}
	
	private static UserInput getSerializedInput() {
		UserInput userInput = null;
		
		try {
			File serFile = new File(SERIALIZE_FILE_NAME);
			
			if (!serFile.exists()) {
				return new UserInput();
			}
			
			FileInputStream fis = new FileInputStream(serFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();
			
			if (obj instanceof UserInput) {
				userInput = (UserInput) obj;
			}
		} catch (Exception e) {
			log.error("Error de-serializing input", e);
		}
		
		if (userInput == null) {
			userInput = new UserInput();
		}
		
		return userInput;
	}
	
	private static void serializeInput(UserInput userInput) {
		try {
			FileOutputStream fos = new FileOutputStream(SERIALIZE_FILE_NAME);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(userInput);
		} catch (Exception e) {
			log.error("Error serializing input", e);
		}
	}
}
