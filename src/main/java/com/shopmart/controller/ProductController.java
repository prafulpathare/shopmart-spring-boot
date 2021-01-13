package com.shopmart.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping(value = "/approve/{product_id}")
	public ResponseEntity<?> approve(@PathVariable(name = "product_id") String productId){
	      mongoConfig.productCollection().updateOne(Filters.eq("_id", new ObjectId(productId)), Updates.set("is_approved", true));
	      return ResponseEntity.status(200).body(null);
	}

	@PostMapping(value = "")
	public ResponseEntity<?> createProduct(
		@RequestParam(value = "files", required = true) MultipartFile[] files,
		@RequestParam(value = "product", required = true) String product) {
		
		if(files.length < 3) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NOT_ENOUGH_FILES");
		
    	JSONObject productObject = new JSONObject(product);
    	try {
    		if(productObject.getString("name").length() < 5 ||
				productObject.getDouble("price") < 100.00 ||
				Arrays.asList("electronics", "appearel").stream().anyMatch(category -> category.equals(productObject.getString("category"))) == false ||
				productObject.getJSONArray("description").length() < 3
	    	) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {{
	    		put("message", "bad or insufficient data");
	    	}}); 
    	}
    	catch (JSONException e) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {{
	    		put("message", "bad or insufficient data");
	    	}});
		}
    	
    	
		// upload files and save data
		try {			
        	Document productForMongo = Document.parse(product);
        	productForMongo.append("views", 0);
        	
        	String fullPath = "/opt/lampp/htdocs/cdn.shopmart/products/" + String.valueOf(supplierService.get().getUser_id());
        	Files.createDirectories(Paths.get(fullPath));
        	
        	List<String> imgurls = new ArrayList<>();
        	
            for (MultipartFile file : files) {
            	String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
            	Path copyLocation = Paths.get(fullPath + "/" + fileName);				
            	Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            	imgurls.add(fileName);
			}
            
        	// save in mongodb
            productForMongo.append("is_approved", false);
            productForMongo.append("imgurl", imgurls);
            mongoConfig.productCollection().insertOne(productForMongo);
        	
        	// save in mysql
        	productRepository.save(new Product(productForMongo.getObjectId("_id").toString(), supplierService.get()));
        	
            return ResponseEntity.status(200).body(null);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	@DeleteMapping(value = "/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable String orderId) {
		return ResponseEntity.status(200).body(null);
	}
	
}