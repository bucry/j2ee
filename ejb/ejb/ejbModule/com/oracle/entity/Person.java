package com.oracle.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "person")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String sex;
	
	
	@OneToMany(cascade = CascadeType.ALL
			, targetEntity=Addr.class)
	@JoinColumn(name = "person_id" , nullable = false)
	private Set<Addr> addr = new HashSet<Addr>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Set<Addr> getAddr() {
		return addr;
	}
	public void setAddr(Set<Addr> addr) {
		this.addr = addr;
	}
	
	

}
