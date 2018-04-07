package edu.sunhacks.recyclehub.models;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductRepository extends MongoRepository{
	public Product findByName(String name);
}
