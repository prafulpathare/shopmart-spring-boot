package com.shopmart.dto;

import java.util.Date;

public class ReviewResponse {

	private long review_id, reply_to;
	private String product_id, review_txt, name;  
	private Date date_created;

	public ReviewResponse() { }
	
	
	public ReviewResponse(long review_id, long reply_to, String product_id, String review_txt, String name,
			Date date_created) {
		this.review_id = review_id;
		this.reply_to = reply_to;
		this.product_id = product_id;
		this.review_txt = review_txt;
		this.name = name;
		this.date_created = date_created;
	}


	public long getReview_id() {
		return review_id;
	}
	public void setReview_id(long review_id) {
		this.review_id = review_id;
	}
	public long getReply_to() {
		return reply_to;
	}
	public void setReply_to(long reply_to) {
		this.reply_to = reply_to;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getReview_txt() {
		return review_txt;
	}
	public void setReview_txt(String review_txt) {
		this.review_txt = review_txt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate_created() {
		return date_created;
	}
	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}
}
