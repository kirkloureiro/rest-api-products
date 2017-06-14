package br.com.products.rest.impl;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.products.model.entity.Image;
import br.com.products.model.entity.Product;
import br.com.products.rest.ProductCRUDRest;
import br.com.products.service.ProductCRUDService;
import br.com.products.util.FilterDTO;
import br.com.products.util.JsonParserUtil;

@Component
@Path("/product")
public class ProductCRUDRestImpl implements ProductCRUDRest {

	public Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ProductCRUDService productCRUDService;
	
	/**
	 * {@inheritDoc}
	 */
	@GET
	@Path("/query")
	@Override
	public Response fetchProductByFilter(@DefaultValue("") @QueryParam("id") final String id,
			 @DefaultValue("false") @QueryParam("fetchChildProducts") final boolean fetchChildProducts,
			 @DefaultValue("false") @QueryParam("fetchImages") final boolean fetchImages,
			 @DefaultValue("false") @QueryParam("fetchParentProdut") final boolean fetchParentProdut) {
		try {
			FilterDTO filterDTO = FilterDTO.build()
					.setProductId(id)
					.setFetchChildProducts(fetchChildProducts)
					.setFetchImages(fetchImages)
					.setFetchParentProdut(fetchParentProdut);
			
			List<Product> products = productCRUDService.findProductsByFilter(filterDTO);
			return Response.status(Response.Status.OK).entity(JsonParserUtil.parseObjectToJson(products)).build();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@GET
	@Path("/images/{id}")
	@Override
	public Response getAllImageByProductId(@PathParam("id") final String id) {

		try {
			List<Image> images = productCRUDService.findAllImageByProductId(Long.valueOf(id));
			return Response.status(Response.Status.OK).entity(JsonParserUtil.parseObjectToJson(images)).build();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@GET
	@Path("/child-products/{id}")
	@Override
	public Response getAllChildProductsByProductId(@PathParam("id") final String id) {
		try {
			List<Product> images = productCRUDService.findAllChildProductById(Long.valueOf(id));
			return Response.status(Response.Status.OK).entity(JsonParserUtil.parseObjectToJson(images)).build();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@POST
	@Path("/save-product")
	@Consumes("text/plain; charset=UTF-8")
	@Override
	public Response saveProduct(String jsonObjectString) {
		try {
			Product product = productCRUDService.createOrUpdateProduct(JsonParserUtil.parseJsonToObject(jsonObjectString, Product.class));
			return Response.status(Response.Status.OK).entity(JsonParserUtil.parseObjectToJson(product)).build();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@POST
	@Path("/delete-product")
	@Consumes("text/plain; charset=UTF-8")
	@Override
	public Response deleteProduct(final String jsonObjectString) {
		try {
			productCRUDService.deleteProduct(JsonParserUtil.parseJsonToObject(jsonObjectString, Product.class));
			return Response.status(Response.Status.OK).entity("Successfully Deleted").build();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@POST
	@Path("/save-image")
	@Consumes("text/plain; charset=UTF-8")
	@Override
	public Response saveImage(String jsonObjectString) {
		try {
			Image image = productCRUDService.createOrUpdateImage(JsonParserUtil.parseJsonToObject(jsonObjectString, Image.class));
			return Response.status(Response.Status.OK).entity(JsonParserUtil.parseObjectToJson(image)).build();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@POST
	@Path("/delete-image")
	@Consumes("text/plain; charset=UTF-8")
	@Override
	public Response deleteImage(final String jsonObjectString) {
		try {
			productCRUDService.deleteImage(JsonParserUtil.parseJsonToObject(jsonObjectString, Image.class));
			return Response.status(Response.Status.OK).entity("Successfully Deleted").build();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
}
