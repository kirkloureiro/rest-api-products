package br.com.products.dao;

import java.util.List;

import br.com.products.model.entity.Product;
import br.com.products.util.FilterDTO;

public interface ProductDAO extends GenericDao<Product, Long> {

	/***
	 * Find all Child Products from Product
	 * 
	 * @param parentProductId
	 * @return List<{@link Product}>
	 */
	public List<Product> findAllChildProductById(Long parentProductId);

	/***
	 * Find product by filter
	 * 
	 * @param filterDTO
	 * @return List<{@link Product}>
	 */
	public List<Product> findByFilter(FilterDTO filterDTO);
}
