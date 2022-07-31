package com.git.serenity.utilities;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBUtils {
	
	final static Logger logger = LogManager.getLogger(DBUtils.class);
	static FileIoUtils fileReadWrite = new FileIoUtils();
	
	static ResultSet rs = null;
	static Connection conn = null;
	static Statement stmt = null;
	String query = "";
	
	//Open DB2 connection
	public void openDb2Connection() throws IOException {
		String jdbcDriver = fileReadWrite.propertyFileReader("application", "db2.driver");
		String dbUrl = fileReadWrite.propertyFileReader("application", System.getProperty("env").toLowerCase()+".db2.url");
		String username = System.getProperty("username");
		String password = System.getProperty("password");
		try {
			Class.forName(jdbcDriver);
			
			Properties props = new Properties();
			props.setProperty("user", username);
			props.setProperty("password", password);
			props.setProperty("autoReconnect", "true");
			
			//Create connection
			conn = DriverManager.getConnection(dbUrl, props);
			
			//Create Statement
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Open Oracle connection
	public void openOracleConnection() throws IOException {
		String jdbcDriver = fileReadWrite.propertyFileReader("application", "oracle.driver");
		String dbUrl = fileReadWrite.propertyFileReader("application", System.getProperty("env").toLowerCase()+".oracle.url");
		String username = System.getProperty("username");
		String password = System.getProperty("password");
		try {
			Class.forName(jdbcDriver);
			
			Properties props = new Properties();
			props.setProperty("user", username);
			props.setProperty("password", password);
			props.setProperty("autoReconnect", "true");
			
			//Create connection
			conn = DriverManager.getConnection(dbUrl, props);
			
			//Create Statement
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Close DB Connection
	public void closeDbConnection() {
		try {
			rs.close();
			if(conn!=null) {
				conn.close();
			}
			if(stmt != null) {
				stmt.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//To alter the query based on env
	private static String prepareQueryForEnv(String query) {
		switch(System.getProperty("env").toUpperCase()) {
		case "DEV":
			query = query.replaceAll("G1DBO", "D1DBO");
			break;
		case "ALPHA":
			query = query.replaceAll("G1DBO", "C1DBO");
			break;
		case "PERF":
			query = query.replaceAll("G1DBO", "M1DBO");
			break;
		}
		return query;
	}
	
	//Execute SQL query with no variables
	public ResultSet executeQuery(String queryName) throws IOException {
		query = fileReadWrite.propertyFileReader("db", queryName);
		if(!System.getProperty("env").equalsIgnoreCase("sit")) {
			query = prepareQueryForEnv(queryName);
		}
		logger.info("Executing DB2 Select Query : [ "+query+" ]");
		try {
			rs = stmt.executeQuery(query);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	//Execute SQL query with ONE variable
	public ResultSet executeQuery(String queryName,String variable1) throws IOException {
		query = fileReadWrite.propertyFileReader("db", queryName).replace("var1", variable1);
		if(!System.getProperty("env").equalsIgnoreCase("sit")) {
			query = prepareQueryForEnv(queryName);
		}
		logger.info("Executing DB2 Select Query : [ "+query+" ]");
		try {
			rs = stmt.executeQuery(query);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	//Execute SQL query with TWO variables
	public ResultSet executeQuery(String queryName,String variable1,String variable2) throws IOException {
		query = fileReadWrite.propertyFileReader("db", queryName).replace("var1", variable1).replace("var2", variable2);
		if(!System.getProperty("env").equalsIgnoreCase("sit")) {
			query = prepareQueryForEnv(queryName);
		}
		logger.info("Executing DB2 Select Query : [ "+query+" ]");
		try {
			rs = stmt.executeQuery(query);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	//Execute query with THREE variables
	public ResultSet executeQuery(String queryName,String variable1,String variable2,String variable3) throws IOException {
		query = fileReadWrite.propertyFileReader("db", queryName).replace("var1", variable1).replace("var2", variable2).replace("var3", variable3);
		if(!System.getProperty("env").equalsIgnoreCase("sit")) {
			query = prepareQueryForEnv(queryName);
		}
		logger.info("Executing DB2 Select Query : [ "+query+" ]");
		try {
			rs = stmt.executeQuery(query);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	//Execute query with FOUR variables
	public ResultSet executeQuery(String queryName,String variable1,String variable2,String variable3,String variable4) throws IOException {
		query = fileReadWrite.propertyFileReader("db", queryName).replace("var1", variable1).replace("var2", variable2).replace("var3", variable3).replace("var4", variable4);
		if(!System.getProperty("env").equalsIgnoreCase("sit")) {
			query = prepareQueryForEnv(queryName);
		}
		logger.info("Executing DB2 Select Query : [ "+query+" ]");
		try {
			rs = stmt.executeQuery(query);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	//Return Resultset as ArrayList for specified column
	public ArrayList<String> returnResultsetAsList(ResultSet rs, String columnName) throws SQLException{
		ArrayList<String> colValList = new ArrayList<>();
		rs.beforeFirst();
		while(rs.next()) {
			colValList.add(rs.getString(columnName));
		}
		return colValList;
	}
}
