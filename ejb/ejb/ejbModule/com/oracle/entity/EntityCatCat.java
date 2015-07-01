package com.oracle.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cat")
public class EntityCatCat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "name" , 
				column=@Column(name = "cat_name" , length = 35)),
				
		@AttributeOverride(name = "color" , 
				column=@Column(name = "cat_color" , length = 35))
		
	})
	private EntityCat cat;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EntityCat getCat() {
		return cat;
	}

	public void setCat(EntityCat cat) {
		this.cat = cat;
	}
	
	
	
}
