package com.git.serenity.utilities;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

public class JavaUtils {
	
	ResultSet rs = null;
	FileIoUtils fileReadWrite = new FileIoUtils();
	JsonPathUtils jsonPathUtils = new JsonPathUtils();
	
	//To convert Resultset from DB into List
	public ArrayList<String> resultSetToList(ResultSet rs, String fieldName){
		ArrayList<String> rsVals = new ArrayList<>();
		try {
			while(rs.next()) {
				rsVals.add(rs.getString(fieldName));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return rsVals;
	}
	
	//To fetch Xpath value with NO varaibles
	public String formXpath(String xpathName,String oldVar,String newvar) {
		String xpathVal = null;
		try {
			xpathVal = fileReadWrite.propertyFileReader("pageElement", xpathName).replace(oldVar, newvar);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return xpathVal;
	}
	
	//Encode String
	public String baseEncode(String propertName) throws IOException {
		String password = fileReadWrite.propertyFileReader("application", propertName);
		String encodedString = Base64.getEncoder().encodeToString(password.getBytes());
		return encodedString;
	}

}
