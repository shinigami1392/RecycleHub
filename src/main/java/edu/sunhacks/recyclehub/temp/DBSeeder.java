package edu.sunhacks.recyclehub.temp;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import edu.sunhacks.recyclehub.models.Product;
import edu.sunhacks.recyclehub.models.ProductRepository;
import edu.sunhacks.recyclehub.models.State;

@Component
public class DBSeeder implements CommandLineRunner{
	
	private ProductRepository productRepository;
	
	public DBSeeder(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		Product product1 = new Product("123", "Namkeen", "2", "1", "1", Arrays.asList(new State("AZ","0.10"),new State("CA","0.15")));
		Product product2 = new Product("456", "Frooti", "4", "2", "2", Arrays.asList(new State("AZ","0.13"),new State("CA","0.18")));
		this.productRepository.deleteAll();
		List<Product> products = Arrays.asList(product1,product2);
		this.productRepository.saveAll(products);
	}

}
