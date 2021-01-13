package com.shopmart.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.bson.Document;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shopmart.model.Product;
import com.shopmart.repository.ProductRepository;
import com.shopmart.service.SupplierService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/product")
public class ProductController {

//	@Autowired private ProductService productService;
	@Autowired private ProductRepository productRepository;
	@Autowired private SupplierService supplierService;
	@Autowired private com.shopmart.config.MongoConfig mongoConfig;
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


	@PostMapping(value = "")
	public ResponseEntity<?> createProduct(
		@RequestParam(value = "files", required = true) MultipartFile[] files,
		@RequestParam(value = "product", required = true) String product) {
		
		if(files.length < 3) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NOT_ENOUGH_FILES");
		
    	JSONObject productObject = new JSONObject(product);
    	if(productObject.getString("name") == null || productObject.getString("name").length() < 5) ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NO_OR_BAD_PRODUCT_NAME");
    	if(String.valueOf(productObject.getDouble("price")) == null || productObject.getDouble("price") < 100.00) ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NO_OR_BAD_PRODUCT_PRICE"); 
    	
    	
		// upload files and save data
		try {
			String fullPath = "/opt/lampp/htdocs/cdn.shopmart/products/" + String.valueOf(supplierService.get().getUser_id());
			Files.createDirectories(Paths.get(fullPath));
            for (MultipartFile file : files) {
            	Path copyLocation = Paths.get(fullPath + "/" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg");				
            	Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);            	
			}

        	// save in mongodb
        	Document productForMongo = Document.parse(product);
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