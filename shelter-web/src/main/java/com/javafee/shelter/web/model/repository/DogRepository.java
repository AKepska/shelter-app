package com.javafee.shelter.web.model.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.javafee.shelter.web.model.domain.Dog;

public interface DogRepository extends JpaRepository<Dog, Integer> {
	List<Dog> findAllByAdopted(Boolean adopted);

	List<Dog> findByName(String name);

	List<Dog> findByNameContains(String name);

	@Query("select round(avg(d.age),2) from Dog d where d.adopted is true")
	Double avgOfAdopted();

	@Query("select count(*) from Dog d where d.adopted is true")
	Integer amountOfAdoptedDogs();

	@Query("select count(*) from Dog d")
	Integer amountOfAllDogsEver();

	@Query("select round(avg(d.age),2) from Dog d")
	Double avgAge();

	@Query("from Dog d where d.updateDate is not null and d.adopted is true order by d.updateDate desc")
	List<Dog> orderByDate();

	@Query("from Dog d where d.updateDate > ?1")
	List<Dog> dogsUpdated7DaysAgo(Date date);

}
