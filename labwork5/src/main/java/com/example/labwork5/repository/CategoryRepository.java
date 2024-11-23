package com.example.labwork5.repository;
import com.example.labwork5.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {}

