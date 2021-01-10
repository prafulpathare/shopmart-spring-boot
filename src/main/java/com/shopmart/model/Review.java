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
	@Column(name="reviewId")
	private long reviewId;

	@Column(name = "product_id")
	private String productId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
	
	@Column(name = "reply_to")
	private long reply_to;
	
	@Column(name = "review_txt")
	private String reviewText;
	
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	public Review() { }
	
	public Review(String productId, long reply_to, String reviewText,
			Date dateCreated, User user) {
		this.productId = productId;
		this.reply_to = reply_to;
		this.reviewText = reviewText;
		this.dateCreated = dateCreated;
		this.user = user;
	}

	public long getReviewId() {
		return reviewId;
	}

	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", productId=" + productId + ", user=" + user + ", reply_to=" + reply_to
				+ ", reviewText=" + reviewText + ", dateCreated=" + dateCreated + "]";
	}

}