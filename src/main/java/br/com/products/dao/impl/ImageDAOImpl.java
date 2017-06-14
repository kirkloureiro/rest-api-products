package br.com.products.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.products.dao.ImageDAO;
import br.com.products.model.entity.Image;

@Repository
public class ImageDAOImpl extends AbstractGenericDao<Image, Long> implements ImageDAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Image> findAllByProductId(final Long productId) {
		Query query = this.entityManager.createNamedQuery(Image.NQ_FIND_ALL_BY_PRODUCT_ID, Image.class);
		query.setParameter("id", productId);
		@SuppressWarnings("unchecked")
		List<Image> resultList = query.getResultList();
		return resultList;
	}
}
