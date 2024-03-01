package com.employeemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
	private int id;
	private String name;
	private String address;
	public Student(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}
	

}
