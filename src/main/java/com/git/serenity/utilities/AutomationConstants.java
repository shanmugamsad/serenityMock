package com.git.serenity.utilities;

import org.openqa.selenium.WebDriver;

public class AutomationConstants {
	
	public static WebDriver driver;

	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver driver) {
		AutomationConstants.driver = driver;
	}

}
