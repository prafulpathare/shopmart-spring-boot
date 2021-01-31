package com.shopmart.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.shopmart.model.Product;
import com.shopmart.repository.ProductRepository;
import com.shopmart.service.SupplierService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired private ProductRepository productRepository;
	@Autowired private SupplierService supplierService;
	@Autowired private com.shopmart.config.MongoConfig mongoConfig;
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@PostMapping(value = "")
	public ResponseEntity<?> createProduct(
		@RequestParam(value = "files", required = true) MultipartFile[] files,
		@RequestParam(value = "product", required = true) String product) {
		
		System.out.println(files.toString());
		System.out.println(product);
		if(files.length < 1) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NOT_ENOUGH_FILES");
		
    	JSONObject productObject = new JSONObject(product);

		try {			
        	Document productForMongo = Document.parse(product);
        	productForMongo.append("views", 0);

            System.out.println("78");
        	String dir = "/opt/lampp/htdocs/cdn.shopmart/products/" + String.valueOf(supplierService.get().getUser_id());
        	Files.createDirectories(Paths.get(dir));
        	
        	List<String> imgurls = new ArrayList<>();

            for (MultipartFile file : files) {
            	int extn = file.getOriginalFilename().lastIndexOf(".");
            	
            	String fileName =  UUID.randomUUID().toString().replaceAll("-", "") + file.getOriginalFilename().substring(extn);
            	Path copyLocation = Paths.get(dir + "/" + fileName);				
            	Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

            	imgurls.add(dir.replaceAll("/opt/lampp/htdocs/", "http://127.0.0.1/") + "/" + fileName);
			}

        	// save in mongodb
            productForMongo.append("is_approved", false);
            productForMongo.append("imgurl", imgurls);
            productForMongo.append("user_id", supplierService.userService.getId());
            mongoConfig.productCollection().insertOne(productForMongo);
        	            
        	// save in mysql
        	productRepository.save(new Product(productForMongo.getObjectId("_id").toString(), supplierService.get()));
        	
            return ResponseEntity.status(200).body(null);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	@PostMapping(value = "/analytics")
	public ResponseEntity<?> getAnalytics(@RequestBody ProductAnalyticsRequest req) {
		String sql;	

		if(req.product_id == null || req.product_id.equals("ALL")) {
			sql = "select sum(product_analytics.orders) orders, sum(product_analytics.views) views, product_analytics.date date FROM products INNER JOIN product_analytics on product_analytics.product_id = products.product_id where products.user_id = '"+supplierService.userService.getId() +"'  and (date between '"+ new java.sql.Date(req.from_date.getTime()) +"' and '"+new java.sql.Date(req.to_date.getTime())+"')  GROUP by product_analytics.date order by product_analytics.date asc;";
		}
		else {
			sql = "select product_analytics.orders, product_analytics.views, product_analytics.date FROM products INNER JOIN product_analytics on product_analytics.product_id = products.product_id where products.user_id = "+supplierService.userService.getId()+" and products.product_id = '"+req.product_id+"' and (date between '"+ new java.sql.Date(req.from_date.getTime()) +"' and '"+new java.sql.Date(req.to_date.getTime())+"') GROUP by product_analytics.date order by product_analytics.date asc;";
		}
		System.out.println("Jdbc: "+sql);
		
		return ResponseEntity.ok(
			supplierService.userService.jdbc.query(sql, new BeanPropertyRowMapper(ProductAnalyticsResponse.class))
		);
	}
	

	@DeleteMapping(value = "/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable String productId) {
		mongoConfig.productCollection().deleteOne(new Document("_id", new ObjectId(productId)));
		supplierService.userService.jdbc.update("delete from shopmart.products where product_id = ?", new Object[] {productId});
		return ResponseEntity.status(200).body(null);
	}
	@GetMapping(value = "/approve/{productId}")
	public ResponseEntity<?> approveProduct(@PathVariable String productId) {
		supplierService.userService.jdbc.update("update shopmart.products set is_approved = true where product_id = ?", new Object[] {productId});
		return ResponseEntity.status(200).body(null);
	}
	@PutMapping(value = "/increment-views/{productId}")
	public ResponseEntity<?> incrementProductViews(@PathVariable String productId) {
		int inserted = supplierService.userService.jdbc.update("update shopmart.product_analytics set views = views + 1 where product_id = ? and date = ?", new Object[] {productId,  new SimpleDateFormat("yyyy-MM-dd").format(new Date())});
		if(inserted == 0) supplierService.userService.jdbc.update("insert into shopmart.product_analytics (product_id, views, date) values (?, ?, ?)", new Object[] {productId, 1, new Date()});
		return ResponseEntity.status(200).body(null);
	}
	@PutMapping(value = "/increment-orders/{productId}")
	public ResponseEntity<?> incrementProductOrders(@PathVariable String productId) {
		int inserted = supplierService.userService.jdbc.update("update shopmart.product_analytics set orders = orders + 1 where product_id = ? and date = ?", new Object[] {productId,  new SimpleDateFormat("yyyy-MM-dd").format(new Date())});
		if(inserted == 0) supplierService.userService.jdbc.update("insert into shopmart.product_analytics (product_id, orders, date) values (?, ?, ?)", new Object[] {productId, 1,  new Date()});
		return ResponseEntity.status(200).body(null);
	}

}

class ProductAnalyticsResponse {
	private String product_id;
	private int views, orders;
	private Date date;
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getOrders() {
		return orders;
	}
	public void setOrders(int orders) {
		this.orders = orders;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}

class ProductAnalyticsRequest {
	public String product_id;
	public Date from_date, to_date;
}