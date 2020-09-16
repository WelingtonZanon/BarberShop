package model.entities;

import java.io.Serializable;

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id,qtd;
	private String name,description;
	private double valuein,valueout;
		
	public Product() {
	}

	public Product(Integer id, Integer qtd, String name, String description, double valuein, double valueout) {
		this.id = id;
		this.qtd = qtd;
		this.name = name;
		this.description = description;
		this.valuein = valuein;
		this.valueout = valueout;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getValuein() {
		return valuein;
	}

	public void setValuein(double valuein) {
		this.valuein = valuein;
	}

	public double getValueout() {
		return valueout;
	}

	public void setValueout(double valueout) {
		this.valueout = valueout;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((qtd == null) ? 0 : qtd.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valuein);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(valueout);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (qtd == null) {
			if (other.qtd != null)
				return false;
		} else if (!qtd.equals(other.qtd))
			return false;
		if (Double.doubleToLongBits(valuein) != Double.doubleToLongBits(other.valuein))
			return false;
		if (Double.doubleToLongBits(valueout) != Double.doubleToLongBits(other.valueout))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", qtd=" + qtd + ", name=" + name + ", description=" + description + ", valuein="
				+ valuein + ", valueout=" + valueout + "]";
	}


}
