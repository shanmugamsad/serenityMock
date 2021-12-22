package com.git.serenity.utilities;

import io.restassured.RestAssured;
import io.restassured.config.DecoderConfig;
import io.restassured.config.RestAssuredConfig;

public class RestAssuredUtils {
	
	//Basic settings to fix certificate issue
	public static void setProxy() {
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.proxy("proxy-url", 8000);
	}
	
	//Settings to fix Inflater Stream issue
	public static void setInflatedStream() {
		RestAssured.config = RestAssuredConfig.config().decoderConfig(DecoderConfig.decoderConfig().useNoWrapForInflateDecoding(true));
	}

}
