package com.git.serenity.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

public class FileIoUtils {
	
	InputStream inputStream;
	String result;
	
	//Read from property file based on file and property name
	public String propertyFileReader(String fileName,String propertyName) throws IOException {
		try {
			Properties prop = new Properties();
			
			inputStream = getClass().getClassLoader().getResourceAsStream(propertyName+".properties");
			
			if(inputStream!=null) {
				prop.load(inputStream);
			}
			else {
				throw new FileNotFoundException("PROPERTY FILe "+fileName+" IS NOT FOUND");
			}
			result = prop.getProperty(propertyName);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			inputStream.close();
		}
		return result;
	}
	
	//Write into property file based on file and property name
	public void propertyFileWriter(String fileName,String propertyName,String propertyValue) {
		try {
			Properties prop = new Properties();
			
			inputStream = getClass().getClassLoader().getResourceAsStream(propertyName+".properties");
			
			if(inputStream!=null) {
				prop.load(inputStream);
			}
			else {
				throw new FileNotFoundException("PROPERTY FILe "+fileName+" IS NOT FOUND");
			}
			//Update the property value
			prop.setProperty(propertyName, propertyValue);
			//Save the property file
			prop.store(new FileOutputStream(propertyName+".properties"), null);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Delete the property from the property file
	public void deletePropertyInPropertyFile(String fileName,String propertyName,String propertyValue) {
		try {
			Properties prop = new Properties();
			
			inputStream = getClass().getClassLoader().getResourceAsStream(propertyName+".properties");
			
			if(inputStream!=null) {
				prop.load(inputStream);
			}
			else {
				throw new FileNotFoundException("PROPERTY FILe "+fileName+" IS NOT FOUND");
			}
			//Delete the property from property file
			prop.remove(propertyName);
			//Save the property file
			prop.store(new FileOutputStream(propertyName+".properties"), null);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Convert the property file to Hashmap
	public HashMap<String, String> propFileToMap(String fileName) throws IOException{
		HashMap<String, String> map = new HashMap<>();
		Properties propFile = new Properties();
		FileInputStream ip =  new FileInputStream("src\\test\\resources\\"+fileName+".properties");
		propFile.load(ip);
		Set<Object> keys = propFile.keySet();
		for(Object k:keys) {
			String key = (String) k;
			String propVal = propFile.getProperty(key);
			map.put(key, propVal);
		}
		return map;
	}
	
	//Read the contents from a TEXT File
	public String readTxtFile(String fileName) {
		String data = null;
		try {
			File file = new File(fileName);
			Scanner fileReader = new Scanner(file);
			while(fileReader.hasNextLine()) {
				data = fileReader.nextLine();
			}
			fileReader.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	//Write the output to TEXT File
	public void writeTtFile(String fileName,String content) {
		try {
			File file = new File("src\\main\\resources\\appTestData\\"+fileName);
			FileOutputStream writer = new FileOutputStream(file);
			writer.write(("").getBytes());
			writer.write(content.getBytes());
			writer.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Delete the specified file
	public void deleteFile(String fileName) {
		try {
			File file = new File("src\\main\\resources\\appTestData\\"+fileName);
			if(file.exists()) {
				file.delete();
			}
			else {
				throw new Exception("File with name: "+fileName+" not found");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
