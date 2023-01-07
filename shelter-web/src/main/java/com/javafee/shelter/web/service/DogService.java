package com.javafee.shelter.web.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.javafee.shelter.web.model.domain.Dog;
import com.javafee.shelter.web.model.repository.DogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DogService {
	private final DogRepository dogRepository;

	public List<Dog> findAll() {
		return dogRepository.findAll();
	}

	public Dog findById(Integer id) {
		return dogRepository.findById(id).orElse(null);
	}

	public List<Dog> findByAdopted(Boolean adopted) {
		return dogRepository.findAllByAdopted(adopted);
	}

	public Integer save(Dog dog) {
		Dog savedDog = dogRepository.save(dog);
		return savedDog.getId();
	}

	public List<Dog> findByName(String name) {
		return dogRepository.findByName(name);
	}

	public List<Dog> findAllByAge(int age) {
		List<Dog> dogs = dogRepository.findAll();
		List<Dog> dogsByAge = new ArrayList<>();
		for (Dog d : dogs)
			if (d.getAge() > age)
				dogsByAge.add(d);
		return dogsByAge;
	}

	public List<Dog> findAllFromToAge(int age1, int age2) {
		List<Dog> dogs = dogRepository.findAll();
		List<Dog> dogsByAge = new ArrayList<>();
		for (Dog d : dogs)
			if (d.getAge() > age1 & d.getAge() < age2)
				dogsByAge.add(d);
		return dogsByAge;
	}

	public List<Dog> findAllNameStartsWith(String letter) {
		List<Dog> dogs = dogRepository.findAll();
		List<Dog> dogsByLetter = new ArrayList<>();
		for (Dog d : dogs)
			if (d.getName().startsWith(letter))
				dogsByLetter.add(d);
		return dogsByLetter;
	}

	public List<Dog> findByNameContains(String name) {
		return dogRepository.findByNameContains(name);
	}

	public Dog findTheOldestDog() {
		List<Dog> dogs = dogRepository.findAll();
		Dog oldDog = new Dog();
		oldDog = dogs.get(0);
		for (Dog d : dogs)
			if (d.getAge() > oldDog.getAge())
				oldDog = d;

		return oldDog;
	}

	public Integer remove(Integer id) {
		dogRepository.delete(findById(id));
		return id;
	}

	public Dog adoptDog(Integer id) {
		Dog adoptedDog = findById(id);
		adoptedDog.setAdopted(true);
		adoptedDog.setUpdateDate(new Date());
		dogRepository.save(adoptedDog);
		return adoptedDog;
	}

	public Dog updateDog(Dog dog) {
		dogRepository.save(dog);
		return dog;
	}

	public double averageAgeOfAdoptedDogs() {
		return dogRepository.avgOfAdopted();
	}

	public Integer amountOfAdoptedDogs() {
		return dogRepository.amountOfAdoptedDogs();
	}

	public Integer amountOfAllDogsEver() {
		return dogRepository.amountOfAllDogsEver();
	}

	public double avgAge() {
		return dogRepository.avgAge();
	}

	public List<Dog> adoptMultipleDogs(List<Integer> idList) {
		List<Dog> newAdoptedDogs = new ArrayList<>();
		for (Integer i : idList) {
			Dog adoptedDog = findById(i);
			newAdoptedDogs.add(adoptDog(i));
		}
		return newAdoptedDogs;
	}

	public List<Dog> findAllNameConsist(String word) {
		List<Dog> dogs = dogRepository.findAll();
		List<Dog> dogsByWord = new ArrayList<>();
		for (Dog d : dogs)
			if (d.getName().contains(word))
				dogsByWord.add(d);
		return dogsByWord;
	}

	public List<Dog> adoptOrNot(List<Integer> idList, boolean adopted) {
		List<Dog> dogs = new ArrayList<>();
		for (Integer i : idList) {
			Dog dog = findById(i);
			dog.setAdopted(adopted);
			dogs.add(dog);
			dogRepository.save(dog);
		}
		return dogs;
	}

	public Dog adoptOrNotByName(String name, boolean adopted) {
		Dog dogByName = dogRepository.findByName(name).get(0);
		dogByName.setAdopted(adopted);
		dogRepository.save(dogByName);
		return dogByName;
	}

	public List<Dog> adoptOrNotManyDogsByName(List<String> names, List<Boolean> adopted) {
		List<Dog> changedDogs = new ArrayList<>();
		int i = 0;
		for (String name : names) {
			Dog d = dogRepository.findByName(name).get(0);
			d.setAdopted(adopted.get(i));
			changedDogs.add(d);
			dogRepository.save(d);
			i++;
		}
		return changedDogs;
	}

	public List<Dog> adoptDogsByDate(Integer number, boolean adopted) {
		List<Dog> changedDogs = new ArrayList<>();
		List<Dog> orderedDogs = dogRepository.orderByDate();
		int i = 0;
		for (Dog d : orderedDogs) {
			if (i <= number - 1) {
				d.setAdopted(adopted);
				dogRepository.save(d);
				changedDogs.add(d);
				i++;
			}
		}
		return changedDogs;
	}
	//metoda, która wypisuje psy, które były update'owane 7 dni wstecz

	public List<Dog> displayUpdatedDogs(Integer number) {
		List<Dog> updatedDogs = new ArrayList<>();
		List<Dog> orderedDogs = dogRepository.orderByDate();
		int i = 0;
		for (Dog d : orderedDogs) {
			if (i <= number - 1) {
				updatedDogs.add(d);
				i++;
			}
		}
		return updatedDogs;
	}

	public List<Dog> dogsUpdated7DaysAgo() {
		LocalDateTime localDateTime = LocalDateTime.of(LocalDateTime.now().minusDays(7).toLocalDate(), LocalTime.MIN);
		Date date = Utils.toDate(localDateTime);
		return dogRepository.dogsUpdated7DaysAgo(date);
	}

	public Dog lastUpdateDate() {
		return dogRepository.orderByDate().get(0);
	}
}

