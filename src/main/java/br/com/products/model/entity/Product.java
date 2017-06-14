package br.com.products.model.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity class for the table <code>PRODUCT</code>
 *
 */
@Entity
@Table(schema = "products", name = "PRODUCT")
@NamedQueries( {
	
	@NamedQuery(name = Product.NQ_FIND_ALL, 
	  query = " SELECT product FROM Product product ORDER BY product.id "),
	
	@NamedQuery(name = Product.NQ_FIND_ALL_WITH_ALL_RELATIONSHIPS, 
	  query = " SELECT product FROM Product product "
			+ " LEFT JOIN FETCH product.parentProduct "
			+ " LEFT JOIN FETCH product.images "
			+ " LEFT JOIN FETCH product.childProducts "
			+ " ORDER BY product.id "),
	
	@NamedQuery(name = Product.NQ_FIND_ALL_CHILD_PRODUCT_BY_ID, 
	  query = " SELECT productChild FROM Product productChild "
			+ " INNER JOIN FETCH productChild.parentProduct " /** Not using aliasing fetch join just to keep the JPA specification */
			+ " INNER JOIN productChild.parentProduct parentProduct "
			+ " WHERE "
			+ " parentProduct.id = :id "
			+ " ORDER BY productChild.id ")
	
 })
public class Product implements Serializable {

	private static final long serialVersionUID = -4359362636514091924L;
	
	public static final String NQ_FIND_ALL = "Product.findAll";
	public static final String NQ_FIND_ALL_WITH_ALL_RELATIONSHIPS = "Product.findAllWithAllRelationships";
	public static final String NQ_FIND_ALL_CHILD_PRODUCT_BY_ID = "Product.findAllChildProductById";

	@Id
	@SequenceGenerator(name = "SEQ_PRODUCT", sequenceName = "SEQ_PRODUCT", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCT")
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_product_id", referencedColumnName = "id")
	private Product parentProduct;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
	private Set<Image> images;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parentProduct")
	private Set<Product> childProducts;

	public Product() {
		super();
	}
	
	public Product(final Long id) {
		super();
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @return the parentProduct
	 */
	public Product getParentProduct() {
		return parentProduct;
	}

	/**
	 * @param parentProduct
	 *            the parentProduct to set
	 */
	public void setParentProduct(final Product parentProduct) {
		this.parentProduct = parentProduct;
	}

	/**
	 * @return the images
	 */
	public Set<Image> getImages() {
		return images;
	}

	/**
	 * @param images
	 *            the images to set
	 */
	public void setImages(final Set<Image> images) {
		this.images = images;
	}

	/**
	 * @return the childProducts
	 */
	public Set<Product> getChildProducts() {
		return childProducts;
	}

	/**
	 * @param childProducts
	 *            the childProducts to set
	 */
	public void setChildProducts(final Set<Product> childProducts) {
		this.childProducts = childProducts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + " [id=" + id + ", name=" + name + ", description=" + description + ", parentProduct="
				+ parentProduct + "]";
	}
}
