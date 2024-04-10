package com.company.inventory.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.inventory.model.Product;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.response.ProductResponseRest;
import com.company.inventory.services.IProductService;
import com.company.inventory.utils.CategoryExcelExporter;
import com.company.inventory.utils.ImageConverter;
import com.company.inventory.utils.ProductExcelExporter;

import jakarta.servlet.http.HttpServletResponse;
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
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/products/{id}")
	public ResponseEntity<ProductResponseRest> findById(@PathVariable Long id) {
		ResponseEntity<ProductResponseRest> response = service.findById(id);
		return response;
	}

	/**
	 * search by name
	 * 
	 * @param name
	 * @return
	 */
	@GetMapping("/products/filter/{name}")
	public ResponseEntity<ProductResponseRest> findByName(@PathVariable String name) {
		ResponseEntity<ProductResponseRest> response = service.findByName(name);
		return response;
	}

	/**
	 * delete by id
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/products/{id}")
	public ResponseEntity<ProductResponseRest> deleteById(@PathVariable Long id) {
		ResponseEntity<ProductResponseRest> response = service.deleteById(id);
		return response;
	}

	/**
	 * find all products
	 * @return
	 */
	@GetMapping("/products")
	public ResponseEntity<ProductResponseRest> findAll() {
		ResponseEntity<ProductResponseRest> response = service.findAll();
		return response;
	}
	
	
	/**
	 * update product
	 * @param picture
	 * @param name
	 * @param price
	 * @param stock
	 * @param category
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@PutMapping("/products/{id}")
	public ResponseEntity<ProductResponseRest> update(@RequestParam("picture") MultipartFile picture,
			@RequestParam("name") String name, @RequestParam("price") int price, @RequestParam("stock") int stock,
			@RequestParam("category") long category, @PathVariable Long id) throws IOException {

		Product product = new Product();
		product.setName(name);
		product.setPrice(price);
		product.setStock(stock);
		product.setPicture(ImageConverter.compressZLib(picture.getBytes()));

		ResponseEntity<ProductResponseRest> response = service.update(product, category, id);
		return response;
	}

	/**
	 * export product in excel file
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/products/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {

		response.setContentType("application/octet-stream");

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename= result_products.xlsx";
		response.setHeader(headerKey, headerValue);

		ResponseEntity<ProductResponseRest> productResponse = service.findAll();

		ProductExcelExporter excelExporter = new ProductExcelExporter(
				productResponse.getBody().getProduct().getProducts());

		excelExporter.export(response);
	}
}
