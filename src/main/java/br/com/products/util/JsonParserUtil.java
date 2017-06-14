package br.com.products.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;
import org.hibernate.proxy.HibernateProxy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import br.com.products.model.entity.Image;
import br.com.products.model.entity.Product;

/***
 * Json Parser Util
 *
 */
public final class JsonParserUtil {

	private JsonParserUtil() {
		super();
	}

	/**
	 * Parses object {@link T} to {@link String} Json
	 * 
	 * @param type
	 * @return {@link String}
	 */
	public static <T> String parseObjectToJson(final T type) {
		Gson gson = new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).setPrettyPrinting().create();
		return gson.toJson(type);
	}

	/***
	 * Parse objects List<{@link T}> to {@link String} Json
	 * 
	 * @param type
	 * @return {@link String}
	 */
	public static <T> String parseObjectToJson(final List<T> type) {
		Gson gson = new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).setPrettyPrinting().create();
		return gson.toJson(type);
	}

	/**
	 * Parse Json {@link String} to {@link T} object
	 * 
	 * @param stringJson
	 * @param clazz
	 * @return {@link T}
	 */
	public static <T> T parseJsonToObject(final String stringJson, final Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(stringJson, clazz);
	}

	private static class HibernateProxyTypeAdapter extends TypeAdapter<HibernateProxy> {
		public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
			@Override
			@SuppressWarnings("unchecked")
			public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
				TypeAdapter<T> resultAdapter = null;

				if (HibernateProxy.class.isAssignableFrom(type.getRawType())) {
					resultAdapter = (TypeAdapter<T>) new HibernateProxyTypeAdapter(gson);
				}
				if (Product.class.isAssignableFrom(type.getRawType())) {
					resultAdapter = (TypeAdapter<T>) new ProductTypeAdapter();
				}
				if (Image.class.isAssignableFrom(type.getRawType())) {
					resultAdapter = (TypeAdapter<T>) new ImageTypeAdapter();
				}

				return resultAdapter;
			}
		};
		private final Gson context;

		private HibernateProxyTypeAdapter(final Gson context) {
			this.context = context;
		}

		@Override
		public HibernateProxy read(final JsonReader in) throws IOException {
			throw new UnsupportedOperationException("Not supported");
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void write(final JsonWriter out, final HibernateProxy value) throws IOException {
			if (value == null) {
				out.nullValue();
				return;
			}
			// Retrieve the original (not proxy) class
			Class<?> baseType = Hibernate.getClass(value);
			// Get the TypeAdapter of the original class, to delegate the
			// serialization
			TypeAdapter delegate = context.getAdapter(TypeToken.get(baseType));
			// Get a filled instance of the original class
			Object unproxiedValue = value.getHibernateLazyInitializer().getImplementation();
			// Serialize the value
			delegate.write(out, unproxiedValue);
		}
	}

	private static class ProductTypeAdapter extends TypeAdapter<Product> {

		@Override
		public void write(final JsonWriter out, final Product entityInstance) throws IOException {
			if (entityInstance == null) {
				return;
			}

			verifyLazyException(entityInstance);

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			out.jsonValue(gson.toJson(entityInstance));
		}

		

		@Override
		public Product read(final JsonReader in) throws IOException {
			throw new UnsupportedOperationException("Not supported");
		}
	}

	private static class ImageTypeAdapter extends TypeAdapter<Image> {

		@Override
		public void write(final JsonWriter out, final Image entityInstance) throws IOException {

			if (entityInstance == null) {
				return;
			}

			verifyLazyException(entityInstance);

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			out.jsonValue(gson.toJson(entityInstance));
		}

		private void verifyLazyException(final Image entityInstance) {
			try {
				if (entityInstance.getProduct() != null) {
					entityInstance.setProduct(new Product(entityInstance.getProduct().getId()));
				}
			} catch (LazyInitializationException e) {
				entityInstance.setProduct(null);
			}
		}

		@Override
		public Image read(final JsonReader in) throws IOException {
			throw new UnsupportedOperationException("Not supported");
		}
	}

	/***
	 * Workaround avoiding Lazy exception, setting null to objects failed
	 * 
	 * @param entityInstance - Detached Entity Product
	 */
	private static void verifyLazyException(final Product entityInstance) {
		try {
			if (entityInstance.getImages() != null && !entityInstance.getImages().isEmpty()) {
				for (Image childImage : entityInstance.getImages()) {
					childImage.setProduct(null);
				}
				entityInstance.setImages(new HashSet<>(entityInstance.getImages()));
			}
		} catch (LazyInitializationException e) {
			entityInstance.setImages(null);
		}
		try {
			if (entityInstance.getChildProducts() != null && !entityInstance.getChildProducts().isEmpty()) {
				for (Product childProduct : entityInstance.getChildProducts()) {
					childProduct.setParentProduct(null);
					verifyLazyException(childProduct);
				}
				entityInstance.setChildProducts(new HashSet<>(entityInstance.getChildProducts()));
			}
		} catch (LazyInitializationException e) {
			entityInstance.setChildProducts(null);
		}
		try {
			if (entityInstance.getParentProduct() != null) {
				verifyLazyException(entityInstance.getParentProduct());
				entityInstance.setParentProduct(entityInstance.getParentProduct());
			}
		} catch (LazyInitializationException e) {
			entityInstance.setParentProduct(null);
		}
	}
}
