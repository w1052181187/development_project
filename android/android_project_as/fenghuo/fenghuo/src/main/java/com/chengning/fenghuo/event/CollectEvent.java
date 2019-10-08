package com.chengning.fenghuo.event;

import com.chengning.fenghuo.Consts.ArticleType;

public class CollectEvent {

	private int favState;
	private ArticleType articleType;
	
	public CollectEvent(){
		
	}

	public int getFavState() {
		return favState;
	}

	public void setFavState(int favState) {
		this.favState = favState;
	}

	public ArticleType getArticleType() {
		return articleType;
	}

	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}

	
}
