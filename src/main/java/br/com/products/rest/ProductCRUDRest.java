package br.com.products.rest;

import javax.ws.rs.core.Response;

/**
 * Service Interface for Products Rest API
 * 
 * @author Bruno Santos
 *
 */
public interface ProductCRUDRest {

	/**
	 * Find products by filter. All filters is optional.
	 * 
	 * @param id
	 * @param fetchChildProducts
	 * @param fetchImages
	 * @param fetchParentProdut
	 * @return
	 */
	public Response fetchProductByFilter(String id, boolean fetchChildProducts, boolean fetchImages, boolean fetchParentProdut);

	/**
	 * Get all child image from a product
	 * 
	 * @param id
	 * @return
	 */
	public Response getAllImageByProductId(String id);

	/**
	 * Get all childProducts from a product
	 * 
	 * @param id
	 * @return
	 */
	public Response getAllChildProductsByProductId(String id);

	/***
	 * Save or update product with cascade children
	 * 
	 * @param jsonObjectString
	 * @return
	 */
	public Response saveProduct(String jsonObjectString);
	
	/**
	 * Delete product with cascade all
	 * 
	 * @param jsonObjectString
	 * @return
	 */
	public Response deleteProduct(String jsonObjectString);

	/***
	 * Save Image
	 * 
	 * @param jsonObjectString
	 * @return
	 */
	public Response saveImage(String jsonObjectString);
	
	/**
	 * Delete image
	 * 
	 * @param jsonObjectString
	 * @return
	 */
	public Response deleteImage(String jsonObjectString) ;

}
