package com.shopmart.controller;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.client.MongoDatabase;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/supplier")
public class SupplierController {

	@Autowired private MongoDatabase mongodb;
	private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);
	
	@PostMapping(value = "/product/add/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public int addProduct2(@RequestPart("imgs") MultipartFile[] imgs, @RequestPart("product") String product) throws IOException, Exception {

		
		
		Arrays.asList(imgs).stream().forEach(img -> {
			if(!img.getOriginalFilename().endsWith(".png") && !img.getOriginalFilename().endsWith(".jpg") && !img.getOriginalFilename().endsWith(".jpeg")) {
				throw new IllegalStateException("invalid product image"+img.getOriginalFilename());
			}

		});
		
		List<String> imgurls = new ArrayList<>();

		Arrays.asList(imgs).stream().forEach(img -> {
			String extension = ".jpg";
			if(img.getOriginalFilename().endsWith(".png")) { extension = ".png"; }
			if(img.getOriginalFilename().endsWith(".jpeg")) { extension = ".jpeg"; }
			String imgurl = SecurityContextHolder.getContext().getAuthentication().getName()+"_"+UUID.randomUUID().toString().replaceAll("-", "x")+extension;
			File imgx = new File("/opt/lampp/htdocs/cdn.shopmart/products/"+imgurl);
			try {
				img.transferTo(imgx);
				imgurls.add(imgurl);
				Document prd = Document.parse(product);
				prd.append("imgurl", imgurls);
				mongodb.getCollection("products").insertOne(prd);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		});
		return 1;
	}
	
	
}