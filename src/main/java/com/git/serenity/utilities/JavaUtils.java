package com.git.serenity.utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

}
