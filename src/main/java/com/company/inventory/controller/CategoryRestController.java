package com.company.inventory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.ICategoryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CategoryRestController {

	private ICategoryService service;

	/*
	 * Get all the categories
	 */
	@GetMapping("/categories")
	public ResponseEntity<CategoryResponseRest> searchCategories() {
		ResponseEntity<CategoryResponseRest> response = service.search();
		return response;
	}

	/*
	 * Get categories by id
	 */
	@GetMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> searchCategoiesById(@PathVariable Long id) {
		ResponseEntity<CategoryResponseRest> response = service.searchById(id);
		return response;
	}

	/*
	 * Post categories save
	 */
	@PostMapping("/categories")
	public ResponseEntity<CategoryResponseRest> save(@RequestBody Category category) {
		ResponseEntity<CategoryResponseRest> response = service.save(category);
		return response;
	}

	/*
	 * Put categories by id
	 */
	@PutMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> update(@PathVariable Long id, @RequestBody Category category) {
		ResponseEntity<CategoryResponseRest> response = service.update(category, id);
		return response;
	}

	/*
	 * Delete categories by id
	 */
	@DeleteMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> deleteById(@PathVariable Long id) {
		ResponseEntity<CategoryResponseRest> response = service.deleteById(id);
		return response;
	}
}
