package com.company.inventory.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;
import com.company.inventory.services.IProductService;
import com.company.inventory.utils.ImageConverter;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = { "http://localhost:4200" })
@RequestMapping("/api/v2")
@RestController
@AllArgsConstructor
public class ProductResController {

	private IProductService service;

	/**
	 * @RequestParam("picture") MultipartFile picture, @RequestParam("name") String
	 * name, @RequestParam("price") int price, @RequestParam("stock") int
	 * stock, @RequestParam("category") long category) @PostMapping("/products")
	 * 
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/products")
	public ResponseEntity<ProductResponseRest> save(@RequestParam("picture") MultipartFile picture,
			@RequestParam("name") String name, @RequestParam("price") int price, @RequestParam("stock") int stock,
			@RequestParam("category") long category) throws IOException {

		Product product = new Product();
		product.setName(name);
		product.setPrice(price);
		product.setStock(stock);
		product.setPicture(ImageConverter.compressZLib(picture.getBytes()));

		ResponseEntity<ProductResponseRest> response = service.save(product, category);

		return response;
	}

	/**
	 * search by id
	 * @param id
	 * @return
	 */
	@GetMapping("/products/{id}")
	public ResponseEntity<ProductResponseRest> findById(@PathVariable Long id) {
		ResponseEntity<ProductResponseRest> response = service.findById(id);
		return response;
	}
}
