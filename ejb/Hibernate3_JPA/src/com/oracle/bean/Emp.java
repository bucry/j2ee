package com.oracle.bean;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Emp
 *
 */
@Entity
@Table(name="emp")
public class Emp implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Emp() {
		super();
	}
	private int id;
	private String name;
	private String salary;
	private int age;
	 @Id
	/* @GeneratedValue(strategy=GenerationType.IDENTITY)*/
	/* @Column(name="id" ,unique=true,nullable=false)*/
	 @Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="salary")
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	
	@Column(name="age")
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
   
}
