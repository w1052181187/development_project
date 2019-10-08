package com.chengning.fenghuo.data.bean;

import java.io.Serializable;

public class CreditsRuleBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4529342000499458609L;
	String rulename;
	String action;
	String extcredits1;
	String extcredits2;
	String complete;
	
	public String getRulename() {
		return rulename;
	}
	public void setRulename(String rulename) {
		this.rulename = rulename;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getExtcredits1() {
		return extcredits1;
	}
	public void setExtcredits1(String extcredits1) {
		this.extcredits1 = extcredits1;
	}
	public String getExtcredits2() {
		return extcredits2;
	}
	public void setExtcredits2(String extcredits2) {
		this.extcredits2 = extcredits2;
	}
	public String getComplete() {
		return complete;
	}
	public void setComplete(String complete) {
		this.complete = complete;
	}
	
}
