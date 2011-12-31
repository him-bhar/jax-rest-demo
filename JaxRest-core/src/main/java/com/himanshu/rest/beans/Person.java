package com.himanshu.rest.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {
	private int id;
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
	private String name;
}
