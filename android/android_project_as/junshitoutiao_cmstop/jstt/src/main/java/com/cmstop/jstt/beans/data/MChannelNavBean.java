package com.cmstop.jstt.beans.data;

import java.io.Serializable;

public class MChannelNavBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6885467432519504543L;
	private String name;
	private String url;
	private String image;
	private int showtype;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getShowtype() {
		return showtype;
	}

	public void setShowtype(int showtype) {
		this.showtype = showtype;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
