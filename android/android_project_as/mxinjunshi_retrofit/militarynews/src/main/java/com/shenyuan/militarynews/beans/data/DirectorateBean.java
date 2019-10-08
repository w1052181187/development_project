package com.shenyuan.militarynews.beans.data;

import java.io.Serializable;
import java.util.List;

public class DirectorateBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2957823572402258164L;

	/**
	 * 成员
	 */
	private DirectorateMemberBean member;
	
	/**
	 * 任务列表
	 */
	private List<DirectorateTaskBean> task_list;

	public DirectorateMemberBean getMember() {
		return member;
	}

	public void setMember(DirectorateMemberBean member) {
		this.member = member;
	}

	public List<DirectorateTaskBean> getTask_list() {
		return task_list;
	}

	public void setTask_list(List<DirectorateTaskBean> task_list) {
		this.task_list = task_list;
	}
	
	
}
