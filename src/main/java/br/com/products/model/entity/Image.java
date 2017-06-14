package br.com.products.model.entity;

import java.io.Serializable;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity class for the table <code>IMAGE</code>
 *
 */
@Entity
@Table(schema = "products", name = "IMAGE")
@NamedQueries( {
	
	@NamedQuery(name = Image.NQ_FIND_ALL_BY_PRODUCT_ID, 
	  query = " SELECT image FROM Image image "
			+ " INNER JOIN FETCH image.product "
			+ " WHERE "
			+ " image.product.id = :id "
			+ " ORDER BY image.id ")
	
 })
public class Image implements Serializable {

	private static final long serialVersionUID = -4589663064191020388L;
	
	public static final String NQ_FIND_ALL_BY_PRODUCT_ID = "Image.findAllByProductId";

	@Id
	@SequenceGenerator(name = "SEQ_IMAGE", sequenceName = "SEQ_IMAGE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IMAGE")
	@Column(name = "id")
	private Long id;

	@Column(name = "type")
	private String type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
	private Product product;

	public Image() {
		super();
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public void setProduct(final Product product) {
		this.product = product;
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
		Image other = (Image) obj;
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
		return this.getClass().getName() + " [id=" + id + ", type=" + type + ", product=" + product + "]";
	}
}
