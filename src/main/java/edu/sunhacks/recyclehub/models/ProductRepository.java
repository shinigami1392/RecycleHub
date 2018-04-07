package edu.sunhacks.recyclehub.models;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{
	public List<Product> findByName(@Param("name") List<String> name);
}
