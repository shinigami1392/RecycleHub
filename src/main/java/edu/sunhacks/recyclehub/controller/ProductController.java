package edu.sunhacks.recyclehub.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@GetMapping("/findByName")
	public List<Product> findByName(List<String> names){
		return productRepository.findByName(names);
	}

}
