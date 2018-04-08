package edu.sunhacks.recyclehub.controller;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.sunhacks.recyclehub.models.UserRepository;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RestController;

import edu.sunhacks.recyclehub.models.Product;
import edu.sunhacks.recyclehub.models.ProductRepository;

@RestController
public class ProductController {
	
	private ProductRepository productRepository;
	private MongoOperations mongoOperations;

	public ProductController(ProductRepository productRepository, MongoOperations mongoOperations){
		this.productRepository = productRepository;
		this.mongoOperations = mongoOperations;
	}
	
	/*@GetMapping("/pids")
	public List<Product> findByIds(@RequestParam("pid") String[] ids){
		System.err.println("In Find By Ids:");
		for(String id : ids){
			System.err.println(id);
		}
		List<Product> products = productRepository.findByPidIn(Arrays.asList(ids));
		System.err.println("printing list: "+ products.size());
		for(Product product: products){
			System.err.println(product.getProductName());
		}
		return products;
	}*/
	
	@GetMapping("/pids")
	public List<Product> findByPids(@RequestParam("pid") String[] ids){
		return mongoOperations.find(query(where("pid").in(Arrays.asList(ids))), Product.class);
	}

}
