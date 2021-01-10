package com.shopmart.service;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.shopmart.model.Review;
import com.shopmart.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired private ReviewRepository reviewRepo;
	@Autowired private JdbcTemplate jdbc;
	@Autowired private MongoDatabase mongodb;
	@Autowired private UserService userServ;
	private static Logger log = LoggerFactory.getLogger(ReviewService.class);
	
	public ReviewsResponse getReviewsForProduct(String productId){
		MongoCollection<Document> collection = mongodb.getCollection("products");
		Document doc = collection.find(Filters.eq("_id", new ObjectId(productId))).first();
		if(doc == null) {
			 return null;
		} else {
			int count = jdbc.queryForObject("select count(*) from reviews where product_id = '"+productId+"' ", Integer.class);
			if(count >= 1){
				String getReviews = "SELECT users.uid, users.name, reviews.review_id, reviews.review_txt, reviews.date_created FROM `reviews` INNER JOIN `users` on users.uid = reviews.user_id and reviews.product_id = '"+productId+"' order by reviews.date_created DESC";
				List<TReview> reviews = jdbc.query(getReviews, new BeanPropertyRowMapper(TReview.class));
				return new ReviewsResponse(count, reviews);
			}
			return new ReviewsResponse(0, null);
		}
	}
	public int addReply(Review review) {
		return jdbc.update("insert into reviews (product_id, user_id, review_txt, reply_to) values (?, ?, ?, ?)", review.getProductId(), userServ.getUserId(), review.getReviewText(), review.getReply_to());
	}
	public int addReview(Review review) {
		review.setReply_to(0);
		return addReply(review);
	}
}

class ReviewsResponse {
	private int count = 0;
	private List<TReview> reviews;
	ReviewsResponse(){}
	ReviewsResponse(int count, List<TReview> reviews){
		this.count = count;
		this.reviews = reviews;
	}
	public void setCount(int count){this.count = count;}
	public void setReviews(List<TReview> reviews){this.reviews = reviews;}
	public int getCount(){ return this.count; }
	public List<TReview> getReviews(){ return this.reviews; }
}

class TReview{
	private long review_id;
	private long uid;
	private String name;
	private String review_txt;
	private Date date_created;
	TReview(){}
	TReview(long review_id, long uid, String name, String review_txt, Date date_created){
		this.review_id = review_id;
		this.uid = uid;
		this.name = name;
		this.review_txt = review_txt;
		this.date_created = date_created;
	}

	public void setReview_id(long review_id){this.review_id = review_id;}
	public void setUid(long uid){this.uid = uid;}
	public void setName(String name){this.name = name;}
	public void setReview_txt(String review_txt){ this.review_txt = review_txt; }
	public void setDate_created(Date date_created){ this.date_created = date_created; }
	public long getReview_id(){return this.review_id;}
	public long getUid(){ return this.uid;}
	public String getName(){ return  this.name;}
	public String getReview_txt(){ return this.review_txt; }
	public Date getDate_created(){ return this.date_created; }
}