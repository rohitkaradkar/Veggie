package com.greentopli.model;

/**
 * Created by rnztx on 10/10/16.
 */

public class BackendResult {
	private boolean result;
	private String message;

	public void setResult(boolean result) {
		this.result = result;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isResult() {
		return result;
	}

	public String getMessage() {
		return message;
	}

	public BackendResult(boolean result, String message) {
		this.result = result;
		this.message = message;
	}

	public BackendResult(){}

	public static BackendResult success(){
		return success("Success");
	}

	public static BackendResult success(String message){
		return new BackendResult(true,message);
	}

	public static BackendResult error(){
		return error("Error");
	}

	public static BackendResult error(String message){
		return new BackendResult(false,message);
	}
}
