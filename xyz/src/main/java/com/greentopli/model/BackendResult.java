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

}
