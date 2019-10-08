package com.shenyuan.militarynews.beans.data;

import java.io.Serializable;
import java.util.ArrayList;

public class DirectoratePointBean implements Serializable{

	private static final long serialVersionUID = 7314578241219037736L;
	private ArrayList<DirectorateTaskBean> credits_rule;
	public ArrayList<DirectorateTaskBean> getCredits_rule() {
		return credits_rule;
	}
	public void setCredits_rule(ArrayList<DirectorateTaskBean> credits_rule) {
		this.credits_rule = credits_rule;
	}
}
