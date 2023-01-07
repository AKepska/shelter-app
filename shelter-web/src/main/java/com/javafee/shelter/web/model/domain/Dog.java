package com.javafee.shelter.web.model.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
// to były POJO class (plain old Java Objects)
public class Dog extends BaseDog {
	private String name;
	private String breed;
	private int age;
	private char urg;
	private boolean adopted;
	private char lineage;
	@Temporal(TemporalType.DATE)
	private Date updateDate;

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
}
