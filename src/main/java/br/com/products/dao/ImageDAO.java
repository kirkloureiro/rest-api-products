package br.com.products.dao;

import java.util.List;

import br.com.products.model.entity.Image;

public interface ImageDAO extends GenericDao<Image, Long> {

	/**
	 * Find all image by product id
	 * 
	 * @param productId
	 * @return List<{@link Image}>
	 */
	public List<Image> findAllByProductId(Long productId);
}
