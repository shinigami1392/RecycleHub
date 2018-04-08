package edu.sunhacks.recyclehub.controller;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.sunhacks.recyclehub.models.UserRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

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
//    @RequestMapping(value = "/getStackedHistory" , method = RequestMethod.GET)
//    public @ResponseBody String showProducts() {
//        try {
//            JSONArray cellarray = new JSONArray();
//            JSONObject item1 = new JSONObject();
//            JSONObject item2 = new JSONObject();
//            item1.put("pid","123123");
//            item1.put("quantity","1");
//            item1.put("productName", "Beers");
//            item1.put("amount","$20.0");
//            item2.put("pid","090123");
//            item2.put("productName", "Whiskey");
//            item2.put("quantity","2");
//            item2.put("amount","$120.0");
//            cellarray.put(item1);
//            cellarray.put(item2);
//            System.out.println("Mock : " +cellarray.toString());
//            return cellarray.toString();
//        }catch(Exception exception){
//            System.out.println("error is "+exception);
//            return null;
//        }
//    }

	@GetMapping("/pids")
	public List<Product> findByPids(@RequestParam("pid") String[] ids){
		return mongoOperations.find(query(where("pid").in(Arrays.asList(ids))), Product.class);
	}

    @GetMapping("/all")
    public List<Product> findAll(){
        return productRepository.findAll();
    }

}
