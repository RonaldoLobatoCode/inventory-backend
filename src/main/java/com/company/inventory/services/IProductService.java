package com.company.inventory.services;

import org.springframework.http.ResponseEntity;

import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;

public interface IProductService {

	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);

	public ResponseEntity<ProductResponseRest> findById(Long id);

	public ResponseEntity<ProductResponseRest> findByName(String name);

	public ResponseEntity<ProductResponseRest> deleteById(Long id);

	public ResponseEntity<ProductResponseRest> findAll();

	public ResponseEntity<ProductResponseRest> update(Product product, Long categoryId, Long id);

}
