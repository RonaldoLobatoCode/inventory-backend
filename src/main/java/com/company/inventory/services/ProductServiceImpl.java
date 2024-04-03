package com.company.inventory.services;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.dao.IProductDao;
import com.company.inventory.model.Category;
import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;
import com.company.inventory.utils.ImageConverter;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements IProductService {

	private ICategoryDao categoryDao;
	private IProductDao productDao;

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {

		ProductResponseRest response = new ProductResponseRest(); // Objeto de respuesta del producto
		List<Product> list = new ArrayList<>(); // Almacena la informacion que guardamos

		try {
			// Buscar la categoria para setear al objeto producto
			Optional<Category> category = categoryDao.findById(categoryId);

			if (category.isPresent()) {
				product.setCategory(category.get());
			} else {
				response.setMetadata("Respuesta invalida", "-1", "Categoria no asociada al producto");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}

			// Save the product
			Product productSave = productDao.save(product);

			if (productSave != null) {
				list.add(productSave);
				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta ok", "00", "Producto guardado");
			} else {
				response.setMetadata("Respuesta invalida", "-1", "Producto no guardado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("Respuesta invalida", "-1", "Error al guardar el producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);

	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductResponseRest> findById(Long id) {

		ProductResponseRest response = new ProductResponseRest(); // Objeto de respuesta del producto
		List<Product> list = new ArrayList<>(); // Almacena la informacion que guardamos

		try {
			
			// Search product by id
			Optional<Product> product = productDao.findById(id);

			if (product.isPresent()) {
				// decompress image
				byte[] imageDescompress = ImageConverter.decompressZLib(product.get().getPicture());
				product.get().setPicture(imageDescompress);
				list.add(product.get());
				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta ok", "00", "Producto encontrado");
			} else {
				response.setMetadata("Respuesta invalida", "-1", "Producto no encontrado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("Respuesta incorrecta", "-1", "Error al consultar por id");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);

	}

}












