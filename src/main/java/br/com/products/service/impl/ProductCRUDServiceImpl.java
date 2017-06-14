package br.com.products.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.products.dao.ImageDAO;
import br.com.products.dao.ProductDAO;
import br.com.products.exception.AbstractAPIException;
import br.com.products.exception.InfrastructureException;
import br.com.products.model.entity.Image;
import br.com.products.model.entity.Product;
import br.com.products.service.ProductCRUDService;
import br.com.products.util.FilterDTO;

/**
 * Service Implementation for Products API
 *
 */
@Service
public class ProductCRUDServiceImpl implements ProductCRUDService {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private ImageDAO imageDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Product createOrUpdateProduct(final Product product) throws AbstractAPIException {

		try {
			return productDAO.update(product);
		} catch (Exception e) {
			logger.error("an error occurred while processing your request", e);
			throw new InfrastructureException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Image createOrUpdateImage(final Image image) throws AbstractAPIException {

		try {
			return imageDAO.update(image);
		} catch (Exception e) {
			logger.error("an error occurred while processing your request", e);
			throw new InfrastructureException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Product> findProductsByFilter(final FilterDTO filterDTO) throws AbstractAPIException {
		try {
			return productDAO.findByFilter(filterDTO);
		} catch (Exception e) {
			logger.error("an error occurred while processing your request", e);
			throw new InfrastructureException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Product> findAllChildProductById(final Long parentProductId) throws AbstractAPIException {
		try {
			return productDAO.findAllChildProductById(parentProductId);
		} catch (Exception e) {
			logger.error("an error occurred while processing your request", e);
			throw new InfrastructureException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Image> findAllImageByProductId(final Long productId) throws AbstractAPIException {
		try {
			return imageDAO.findAllByProductId(productId);
		} catch (Exception e) {
			logger.error("an error occurred while processing your request", e);
			throw new InfrastructureException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void deleteProduct(final Product product) throws AbstractAPIException {
		try {
			productDAO.delete(product);
		} catch (Exception e) {
			logger.error("an error occurred while processing your request", e);
			throw new InfrastructureException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void deleteImage(final Image image) throws AbstractAPIException {
		try {
			imageDAO.delete(image);
		} catch (Exception e) {
			logger.error("an error occurred while processing your request", e);
			throw new InfrastructureException(e);
		}
	}
}
