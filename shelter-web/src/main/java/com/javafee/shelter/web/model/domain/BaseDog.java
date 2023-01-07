package com.javafee.shelter.web.model.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@SequenceGenerator(name = "seq_dog", sequenceName = "seq_dog", allocationSize = 1)
public class BaseDog {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
}
