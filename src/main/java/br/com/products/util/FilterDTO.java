package br.com.products.util;

public class FilterDTO {

	private String productId;
	private boolean fetchParentProdut;
	private boolean fetchChildProducts;
	private boolean fetchImages;

	public FilterDTO() {
		super();
	}

	/***
	 * Returns new instance of DTO
	 * 
	 * @return {@link FilterDTO}
	 */
	public static FilterDTO build() {
		return new FilterDTO();
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public FilterDTO setProductId(final String productId) {
		this.productId = productId;
		return this;
	}

	/**
	 * @return the fetchParentProdut
	 */
	public boolean isFetchParentProdut() {
		return fetchParentProdut;
	}

	/**
	 * @param fetchParentProdut
	 *            the fetchParentProdut to set
	 */
	public FilterDTO setFetchParentProdut(final boolean fetchParentProdut) {
		this.fetchParentProdut = fetchParentProdut;
		return this;
	}

	/**
	 * @return the fetchChildProducts
	 */
	public boolean isFetchChildProducts() {
		return fetchChildProducts;
	}

	/**
	 * @param fetchChildProducts
	 *            the fetchChildProducts to set
	 */
	public FilterDTO setFetchChildProducts(final boolean fetchChildProducts) {
		this.fetchChildProducts = fetchChildProducts;
		return this;
	}

	/**
	 * @return the fetchImages
	 */
	public boolean isFetchImages() {
		return fetchImages;
	}

	/**
	 * @param fetchImages
	 *            the fetchImages to set
	 */
	public FilterDTO setFetchImages(final boolean fetchImages) {
		this.fetchImages = fetchImages;
		return this;
	}
}
