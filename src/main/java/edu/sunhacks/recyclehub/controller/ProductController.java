package edu.sunhacks.recyclehub.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.sunhacks.recyclehub.models.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.sunhacks.recyclehub.models.Product;
import edu.sunhacks.recyclehub.models.ProductRepository;

@RestController
public class ProductController {
	
	private ProductRepository productRepository;

	public ProductController(ProductRepository productRepository){
		this.productRepository = productRepository;
	}
	
	@GetMapping("/pids")
	public List<Product> findByIds(@RequestParam("id") String[] ids){
		System.err.println("In Find By Ids:" + ids[0]);
		return productRepository.getAllByPids(Arrays.asList(ids));
	}

}
