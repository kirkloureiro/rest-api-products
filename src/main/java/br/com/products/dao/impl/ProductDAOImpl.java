package br.com.products.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.products.dao.ProductDAO;
import br.com.products.model.entity.Product;
import br.com.products.util.FilterDTO;

@Repository
public class ProductDAOImpl extends AbstractGenericDao<Product, Long> implements ProductDAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Product> findAllChildProductById(final Long parentProductId) {
		Query query = this.entityManager.createNamedQuery(Product.NQ_FIND_ALL_CHILD_PRODUCT_BY_ID, Product.class);
		query.setParameter("id", parentProductId);
		@SuppressWarnings("unchecked")
		List<Product> resultList = query.getResultList();
		return resultList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Product> findByFilter(final FilterDTO filterDTO) {

		StringBuilder jpql = new StringBuilder();

		jpql.append(" SELECT product FROM Product product ");

		if (filterDTO.isFetchParentProdut()) {
			jpql.append(" LEFT JOIN FETCH product.parentProduct ");
		}

		if (filterDTO.isFetchImages()) {
			jpql.append(" LEFT JOIN FETCH product.images ");
		}

		if (filterDTO.isFetchChildProducts()) {
			jpql.append(" LEFT JOIN FETCH product.childProducts ");
		}

		jpql.append(" WHERE 1 = 1 ");

		if (filterDTO.getProductId() != null && !filterDTO.getProductId().trim().isEmpty()) {
			jpql.append(" AND product.id = :productId ");
		}

		jpql.append(" ORDER BY product.id ");

		Query query = this.entityManager.createQuery(jpql.toString(), Product.class);

		if (filterDTO.getProductId() != null && !filterDTO.getProductId().trim().isEmpty()) {
			query.setParameter("productId", Long.valueOf(filterDTO.getProductId()));
		}

		@SuppressWarnings("unchecked")
		List<Product> resultList = query.getResultList();
		return resultList;
	}
}
