package com.shopmart.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "reviews")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="review_id")
	private long review_id;

	@Column(name = "product_id")
	private String product_id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
	
	@Column(name = "reply_to")
	private long reply_to;
	
	@Column(name = "review_txt")
	private String review_txt;
	
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_created;

	public Review() { }

	public Review(
		String product_id, long reply_to,
		String review_txt, Date date_created,
		User user
	) {
		this.product_id = product_id;
		this.user = user;
		this.reply_to = reply_to;
		this.review_txt = review_txt;
		this.date_created = date_created;
	}

	public long getReview_id() {
		return review_id;
	}

	public void setReview_id(long review_id) {
		this.review_id = review_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getReply_to() {
		return reply_to;
	}

	public void setReply_to(long reply_to) {
		this.reply_to = reply_to;
	}

	public String getReview_txt() {
		return review_txt;
	}

	public void setReview_txt(String review_txt) {
		this.review_txt = review_txt;
	}

	public Date getDate_created() {
		return date_created;
	}

	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}
}