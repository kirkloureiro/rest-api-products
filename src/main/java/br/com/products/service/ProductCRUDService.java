package br.com.products.service;

import java.util.List;

import br.com.products.exception.AbstractAPIException;
import br.com.products.model.entity.Image;
import br.com.products.model.entity.Product;
import br.com.products.util.FilterDTO;

/**
 * Service Interface for ProductCRUDServiceImpl
 *
 */
public interface ProductCRUDService {

	/***
	 * Save {@link Product} with children Cascaded
	 * 
	 * @param product
	 * @return {@link Product}
	 * @throws AbstractAPIException
	 */
	public Product createOrUpdateProduct(Product product) throws AbstractAPIException;

	/**
	 * Delete {@link Product} with children Cascaded
	 * 
	 * @param product
	 * @throws AbstractAPIException
	 */
	public void deleteProduct(Product product) throws AbstractAPIException;

	/***
	 * Save {@link Image}
	 * 
	 * @param image
	 * @return {@link Image}
	 * @throws AbstractAPIException
	 */
	public Image createOrUpdateImage(Image image) throws AbstractAPIException;

	/**
	 * Delete {@link Image}
	 * 
	 * @param image
	 * @throws AbstractAPIException
	 */
	public void deleteImage(Image image) throws AbstractAPIException;

	/**
	 * Find Products by filter
	 * 
	 * @param filterDTO
	 * @return List<{@link Product}>
	 * @throws AbstractAPIException
	 */
	public List<Product> findProductsByFilter(FilterDTO filterDTO) throws AbstractAPIException;

	/**
	 * Find all child product
	 * 
	 * @param parentProductId
	 * @return List<{@link Product}>
	 * @throws AbstractAPIException
	 */
	public List<Product> findAllChildProductById(Long parentProductId) throws AbstractAPIException;

	/**
	 * Find all Image by product id
	 * 
	 * @param productId
	 * @return List<{@link Image}>
	 * @throws AbstractAPIException
	 */
	public List<Image> findAllImageByProductId(Long productId) throws AbstractAPIException;

}
