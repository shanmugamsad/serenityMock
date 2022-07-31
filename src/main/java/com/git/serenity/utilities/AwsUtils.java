package com.git.serenity.utilities;

import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import org.reflections.vfs.Vfs.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;
import com.amazonaws.services.stepfunctions.model.StartExecutionResult;

import software.amazon.awssdk.auth.credentials.AwsCredentials;

public class AwsUtils {
	
	final static Logger logger = LoggerFactory.getLogger(AwsUtils.class);
	
	public void awsLogin() {
		try {
			/*
			 * AwsCredentials credentials = new
			 * BasicAWSCredentials(System.getProperty("awsUsername"),
			 * System.getProperty("awsPassword"));
			 * 
			 * AmazonS3 s3Client = AmazonS3ClientBuilder.standard() .withCredentials(new
			 * AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_1)
			 * .build();
			 */
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Upload file to S3
	public String uploadToS3Bucket(String s3BucketName, String fileName) {
		java.io.File localFile = new java.io.File("src/test/resources/"+fileName+".txt");
		String objectKeyName = "axay/input"+ localFile.getName();
		AmazonS3 s3Client= AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1)
				.build();
		
		PutObjectRequest request = new PutObjectRequest(s3BucketName, objectKeyName, localFile);
		PutObjectResult result = s3Client.putObject(request);
		String rawFileS3Path = "s3://"+s3BucketName+"/"+objectKeyName;
		return rawFileS3Path;
	}
	
	//Invoke Lambda Functions
	public void invokeLambda(String lambdaName) {
		try {
			AWSLambda lambdaClient = AWSLambdaClientBuilder.standard()
					.withRegion(Regions.US_EAST_1)
					.build();
			
			InvokeRequest lambdarequest = new InvokeRequest().withFunctionName(lambdaName)
					.withPayload("{}");
			lambdarequest.setInvocationType(InvocationType.RequestResponse);
			InvokeResult lambdaResult = lambdaClient.invoke(lambdarequest);
			//To verify job is invoked successfully
			org.junit.Assert.assertEquals(lambdaName+" Lambda Fucntion failed with Response Code: "+lambdaResult.getSdkHttpMetadata().getHttpStatusCode(), lambdaResult.getSdkHttpMetadata().getHttpStatusCode(), 200);
			String res = new String(lambdaResult.getPayload().array(),StandardCharsets.UTF_8);
			JSONObject resultJson = new JSONObject();
			if(resultJson.has("errorMessage")) {
				org.junit.Assert.assertTrue(lambdaName+" Lambda Function failed with error message: \n"+resultJson.getString("errorMessage"), false);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//invoke Step functions
	public void invokeStepFunction(String stepFunctionName, String jobId) {
		try {
			AWSStepFunctionsClientBuilder builder = AWSStepFunctionsClientBuilder.standard()
					.withRegion(Regions.US_EAST_1);
			
			//Build the client, which will ultimately do the work
			AWSStepFunctions client = builder.build();
			
			//Construct the JSON input to the state machine
			JSONObject sfnInput = new JSONObject();
			sfnInput.put("jobId", Integer.parseInt(jobId));
			
			//Create a request to start execution with needed parameters
			StartExecutionRequest request = new StartExecutionRequest()
					.withStateMachineArn("arn:aws:state:us-east-1:AWSACCNUM:statemachine:"+ stepFunctionName)
					.withInput(sfnInput.toString());
			
			//Start the state machine and capture response
			StartExecutionResult result = client.startExecution(request);
			
			//To verify job is invoked successfully
			org.junit.Assert.assertEquals(stepFunctionName+" Lambda Fucntion failed with Response Code: "+result.getSdkHttpMetadata().getHttpStatusCode(), result.getSdkHttpMetadata().getHttpStatusCode(), 200);
					
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
