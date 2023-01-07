package com.javafee.shelter.front.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dog extends BaseDog implements Cloneable {
	private String name;
	private String breed;
	private int age;
	private char urg;
	private boolean adopted;
	private char lineage;
	private Date updateDate;

	public Dog(String name, String breed, int age, char urg, boolean adopted, char lineage, Date updateDate, Integer id) {
		this.name = name;
		this.breed = breed;
		this.age = age;
		this.urg = urg;
		this.adopted = adopted;
		this.lineage = lineage;
		this.updateDate = updateDate;
		this.setId(id);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Dog dog = (Dog) o;
		return age == dog.age && urg == dog.urg && adopted == dog.adopted
				&& name.equals(dog.getName()) && breed.equals(dog.getBreed())
				&& lineage == dog.getLineage(); // && updateDate.equals(dog.getUpdateDate());
	}

	@Override
	public String toString() {
		return "Dog{" +
				"id='" + super.getId() + '\'' +
				"name='" + name + '\'' +
				'}';
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
