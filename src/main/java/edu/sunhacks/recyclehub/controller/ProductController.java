package edu.sunhacks.recyclehub.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.sunhacks.recyclehub.models.Product;
import edu.sunhacks.recyclehub.models.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	private ProductRepository productRepository;

	public ProductRepository getProductRepository() {
		return productRepository;
	}

	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@GetMapping("/findByIds")
	public List<Product> findByIds(@RequestParam("id") String[] ids){
		System.err.println("IN IDS: "+Arrays.toString(ids));
		return new ArrayList<Product>();
		//return (List<Product>) productRepository.findAllById(Arrays.ite);
	}

}
