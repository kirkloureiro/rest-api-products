package br.com.products.rest.impl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Ignore;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.com.products.AbstractClassTest;
import br.com.products.model.entity.Image;
import br.com.products.model.entity.Product;
import br.com.products.util.JsonParserUtil;

public class ProductCRUDRestImplTest extends AbstractClassTest<ProductCRUDRestImpl> {

	private static final String HOST = "http://localhost:";
	private static final String PORT = "8080";
	private static final String CONTEXT_API_BASE_URL = HOST + PORT + "/products/rest/product";

	@Override
	public void startUp() {

		boolean isServerRunning = false;
		try {
			HttpURLConnection http = (HttpURLConnection) new URL(CONTEXT_API_BASE_URL).openConnection();
			http.connect();
			isServerRunning = true;
		} catch (Exception e) {
			System.out.println("Server not running to execute integrated tests, Ignoring Test");
		}
		Assume.assumeTrue("Server not running to execute integrated tests, Ignoring Test", isServerRunning);
	}

	/**
	 * 1) Create, update and delete products
	 * 
	 * Operation - Create
	 * 
	 * @see #ProductCRUDRestImpl.saveProduct
	 * 
	 * @throws Exception
	 */
	@Test
	public void createProductTest() throws Exception {

		String uri = CONTEXT_API_BASE_URL + "/save-product";

		Product product = new Product();

		product.setName("Test Create");
		product.setDescription("Just a Test");

		String jsonObject = JsonParserUtil.parseObjectToJson(product);

		Client client = Client.create();
		WebResource resource = client.resource(uri);
		ClientResponse response = resource.post(ClientResponse.class, jsonObject);

		String retornoJson = response.getEntity(String.class);

		Assert.assertNotNull(response);
		Assert.assertNotNull(retornoJson);
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		product = JsonParserUtil.parseJsonToObject(retornoJson, Product.class);
		Assert.assertNotNull(product);
		Assert.assertNotNull(product.getId());
	}

	/**
	 * 1) Create, update and delete products
	 * 
	 * Operation - Update
	 * 
	 * @see #ProductCRUDRestImpl.saveProduct
	 * 
	 * @throws Exception
	 */
	@Test
	public void updateProductTest() throws Exception {

		String uri = CONTEXT_API_BASE_URL + "/save-product";

		Product product = new Product();

		product.setId(1L);
		product.setName("Test Create");
		product.setDescription("Just a Test");

		String jsonObject = JsonParserUtil.parseObjectToJson(product);

		Client client = Client.create();
		WebResource resource = client.resource(uri);
		ClientResponse response = resource.post(ClientResponse.class, jsonObject);

		String retornoJson = response.getEntity(String.class);

		Assert.assertNotNull(response);
		Assert.assertNotNull(retornoJson);
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	/**
	 * 1) Create, update and delete products
	 * 
	 * Operation - Delete
	 * 
	 * @see #ProductCRUDRestImpl.deleteProduct
	 * 
	 * @throws Exception
	 */
	@Ignore("Execute just with a valid #ProductId# to reference")
	@Test
	public void deleteProductTest() throws Exception {

		String uri = CONTEXT_API_BASE_URL + "/delete-product";

		Product product = new Product();
		/* Sets the PK to delete */
		product.setId(1L);

		Assume.assumeTrue("Id product not existent", isProductExistent(product.getId()));

		String jsonObject = JsonParserUtil.parseObjectToJson(product);

		Client client = Client.create();
		WebResource resource = client.resource(uri);
		ClientResponse response = resource.post(ClientResponse.class, jsonObject);

		Assert.assertNotNull(response);
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	/***
	 * 2) Create, update and delete images
	 * 
	 * Operation - Create
	 * 
	 * @see #ProductCRUDRestImpl.saveImage
	 * 
	 * @throws Exception
	 */
	@Test
	public void createImageTest() throws Exception {

		String uri = CONTEXT_API_BASE_URL + "/save-image";

		Image image = new Image();
		image.setType("Churros");
		/* Sets the ProductId to reference */
		image.setProduct(new Product(1L));
		Assume.assumeTrue("Id product not existent", isProductExistent(image.getProduct().getId()));

		String jsonObject = JsonParserUtil.parseObjectToJson(image);
		Client client = Client.create();
		WebResource resource = client.resource(uri);
		ClientResponse response = resource.post(ClientResponse.class, jsonObject);

		Assert.assertNotNull(response);
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	/***
	 * 2) Create, update and delete images
	 * 
	 * Operation - Update
	 * 
	 * @see #ProductCRUDRestImpl.saveImage
	 * 
	 * @throws Exception
	 */
	@Test
	public void updateImageTest() throws Exception {

		String uri = CONTEXT_API_BASE_URL + "/save-image";

		Image image = new Image();
		image.setId(1L);
		image.setType("Churros");
		/* Sets the ProductId to reference */
		image.setProduct(new Product(1L));
		Assume.assumeTrue("Id product not existent", isProductExistent(image.getProduct().getId()));

		String jsonObject = JsonParserUtil.parseObjectToJson(image);
		Client client = Client.create();
		WebResource resource = client.resource(uri);
		ClientResponse response = resource.post(ClientResponse.class, jsonObject);

		Assert.assertNotNull(response);
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	/***
	 * 2) Create, update and delete images
	 * 
	 * Operation - Delete
	 * 
	 * @see #ProductCRUDRestImpl.deleteImage
	 * 
	 * @throws Exception
	 */
	@Ignore("Execute with a valid #ImageId# to reference")
	@Test
	public void deleteImageTest() throws Exception {

		String uri = CONTEXT_API_BASE_URL + "/delete-image";

		Image image = new Image();
		/* Sets the PK to delete */
		image.setId(1L);

		String jsonObject = JsonParserUtil.parseObjectToJson(image);
		Client client = Client.create();
		WebResource resource = client.resource(uri);
		ClientResponse response = resource.post(ClientResponse.class, jsonObject);

		Assert.assertNotNull(response);
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	/***
	 * 3) Get all products excluding relationships (child products, images,
	 * parentProduct)
	 * 
	 * <code> fetchChildProducts=false</code> <code>fetchImages=false</code>
	 * <code>fetchParentProdut=false</code>
	 * 
	 * @see #ProductCRUDRestImpl.fetchProductByFilter
	 * 
	 * @throws Exception
	 */
	@Test
	public void getAllProductsExcludingRelationshipsTest() throws Exception {

		String uri = CONTEXT_API_BASE_URL + "/query?fetchChildProducts=false&fetchImages=false&fetchParentProdut=false";

		Client client = Client.create();
		WebResource resource = client.resource(uri);
		ClientResponse response = resource.get(ClientResponse.class);

		Assert.assertNotNull(response);
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	/***
	 * 4) Get all products including specified relationships (child product
	 * and/or images)
	 * 
	 * <code> fetchChildProducts=true</code> <code>fetchImages=true</code>
	 * <code>fetchParentProdut=true</code>
	 * 
	 * @see #ProductCRUDRestImpl.fetchProductByFilter
	 * 
	 * @throws Exception
	 */
	@Test
	public void getAllProductsIncludingAllRelationshipsTest() throws Exception {

		String uri = CONTEXT_API_BASE_URL + "/query?fetchChildProducts=true&fetchImages=true&fetchParentProdut=true";

		Client client = Client.create();
		WebResource resource = client.resource(uri);
		ClientResponse response = resource.get(ClientResponse.class);

		Assert.assertNotNull(response);
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	/***
	 * 5) Same as 3 using specific product identity
	 * 
	 * <code> fetchChildProducts=false</code> <code>fetchImages=false</code>
	 * <code>fetchParentProdut=false</code> <code>id=1</code>
	 * 
	 * @see #ProductCRUDRestImpl.fetchProductByFilter
	 * 
	 * @throws Exception
	 */
	@Test
	public void getAllProductsExcludingRelationshipsByIdTest() throws Exception {

		String uri = CONTEXT_API_BASE_URL + "/query?fetchChildProducts=false&fetchImages=false&fetchParentProdut=false&id=1";

		Client client = Client.create();
		WebResource resource = client.resource(uri);
		ClientResponse response = resource.get(ClientResponse.class);

		Assert.assertNotNull(response);
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	/***
	 * 6) Same as 4 using specific product identity
	 * <code> fetchChildProducts=false</code> <code>fetchImages=true</code>
	 * <code>fetchParentProdut=false</code> <code>id=1</code>
	 * 
	 * @see #ProductCRUDRestImpl.fetchProductByFilter
	 * 
	 * @throws Exception
	 */
	@Test
	public void getAllProductsIncludingImageRelationshipTest() throws Exception {

		String uri = CONTEXT_API_BASE_URL + "/query?fetchChildProducts=false&fetchImages=true&fetchParentProdut=false&id=1";

		Client client = Client.create();
		WebResource resource = client.resource(uri);
		ClientResponse response = resource.get(ClientResponse.class);

		Assert.assertNotNull(response);
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	/**
	 * 7) Get set of child products for specific product
	 * 
	 * /child-products/{id} - id=1
	 * 
	 * @see #ProductCRUDRestImpl.getAllChildProductsByProductId
	 * 
	 * @throws Exception
	 */
	@Test
	public void getSetChildProductsForSpecificProductTest() throws Exception {

		String uri = CONTEXT_API_BASE_URL + "/child-products/1";

		Client client = Client.create();
		WebResource resource = client.resource(uri);
		ClientResponse response = resource.get(ClientResponse.class);

		Assert.assertNotNull(response);
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	/**
	 * 8) Get set of images for specific product
	 * 
	 * /images/{id} - id=1
	 * 
	 * @see #ProductCRUDRestImpl.getAllImageByProductId
	 * 
	 * @throws Exception
	 */
	@Test
	public void getSetImagesForSpecificProductTest() throws Exception {

		String uri = CONTEXT_API_BASE_URL + "/images/1";

		Client client = Client.create();
		WebResource resource = client.resource(uri);
		ClientResponse response = resource.get(ClientResponse.class);

		Assert.assertNotNull(response);
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	private boolean isProductExistent(final Long productId) {
		boolean result = false;
		try {

			String uri = CONTEXT_API_BASE_URL + "/query?fetchChildProducts=false&fetchImages=false&fetchParentProdut=false&id=" + productId;

			Client client = Client.create();
			WebResource resource = client.resource(uri);
			ClientResponse response = resource.get(ClientResponse.class);

			@SuppressWarnings("unchecked")
			List<Product> products = JsonParserUtil.parseJsonToObject(response.getEntity(String.class), List.class);

			if (products != null && !products.isEmpty()) {
				result = true;
			}
		} catch (Exception e) {
		}
		return result;
	}

	@Override
	public ProductCRUDRestImpl createObject() {
		throw new UnsupportedOperationException("Not used");
	}
}
