package com.shenyuan.militarynews.event;

public class PayResultEvent {
	
	private int resultCode;
	
	public PayResultEvent(int code){
		this.resultCode = code;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	
}
