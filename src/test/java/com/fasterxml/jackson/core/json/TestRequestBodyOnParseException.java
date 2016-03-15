package com.fasterxml.jackson.core.json;

import java.io.IOException;

import com.fasterxml.jackson.core.BaseTest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class TestRequestBodyOnParseException extends BaseTest {
	
	/**
	 * Tests for input data on parsing error
	 * @throws Exception
	 */
	public void testInputOnParseException() throws Exception{
		testInputOnParseExceptionInternal(true,"nul");
		testInputOnParseExceptionInternal(false,"nul");
	}
	
	/**
	 * Tests for no input data on parsing error
	 * @throws Exception
	 */
	public void testNoInputOnParseException() throws Exception{
		testNoInputOnParseExceptionInternal(true,"nul");
		testNoInputOnParseExceptionInternal(false,"nul");
	}
	
	/*
	 * *******************Private Methods*************************
	 */
	private void testInputOnParseExceptionInternal(boolean isStream, String value) throws Exception{
		final String doc = "{ \"key1\" : "+value+" }";
	     JsonParser jp = isStream ? createParserUsingStream(doc, "UTF-8")
	                : createParserUsingReader(doc);
	     jp.addRequestBodyOnError(true);
	     assertToken(JsonToken.START_OBJECT, jp.nextToken());
	     try{
	    	 jp.nextToken();
	    	 fail("Expecting parsing exception");
	     }
	     catch(JsonParseException ex){
	    	 assertEquals("Input data should match", doc, ex.getRequestBody());
	     }
	}

	private void testNoInputOnParseExceptionInternal(boolean isStream, String value) throws Exception{
		final String doc = "{ \"key1\" : "+value+" }";
	     JsonParser jp = isStream ? createParserUsingStream(doc, "UTF-8")
	                : createParserUsingReader(doc);
	     jp.addRequestBodyOnError(false);
	     assertToken(JsonToken.START_OBJECT, jp.nextToken());
	     try{
	    	 jp.nextToken();
	    	 fail("Expecting parsing exception");
	     }
	     catch(JsonParseException ex){
	    	 assertEquals("Input data should be null", null, ex.getRequestBody());
	     }
	}
}
