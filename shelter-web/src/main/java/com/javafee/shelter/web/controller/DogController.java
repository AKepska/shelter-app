package com.javafee.shelter.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javafee.shelter.web.model.domain.Dog;
import com.javafee.shelter.web.service.DogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/shelter", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class DogController {
	private final DogService dogService;

	@GetMapping
	public ResponseEntity<List<Dog>> findAll() {
		return ResponseEntity.ok(dogService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Dog> findById(@PathVariable Integer id) {
		Dog dog = dogService.findById(id);
		return !Objects.isNull(dog) ? ResponseEntity.ok(dog) : ResponseEntity.notFound().build();
	}

	@GetMapping("/search/{adopted}/adopted")
	public ResponseEntity<List<Dog>> findByAdopted(@PathVariable Boolean adopted) {
		return ResponseEntity.ok(dogService.findByAdopted(adopted));
	}

	@PostMapping("/saveNewDog")
	public ResponseEntity<Integer> save(@RequestBody Dog dog) {
		return ResponseEntity.ok(dogService.save(dog));
	}

	@GetMapping("/search/{name}/name")
	public ResponseEntity<List<Dog>> findByName(@PathVariable String name) {
		return ResponseEntity.ok(dogService.findByName(name));
	}

	@GetMapping("/search/{age}/age")
	public ResponseEntity<List<Dog>> findAllByAge(@PathVariable Integer age) {
		return ResponseEntity.ok(dogService.findAllByAge(age));
	}

	@GetMapping("/search/{age1}/{age2}")
	public ResponseEntity<List<Dog>> findAllFromToAge(@PathVariable Integer age1, Integer age2) {
		return ResponseEntity.ok(dogService.findAllFromToAge(age1, age2));
	}

	@GetMapping("/search/{letter}")
	public ResponseEntity<List<Dog>> findAllNameStartsWith(@PathVariable String letter) {
		return ResponseEntity.ok(dogService.findAllNameStartsWith(letter));
	}

	@GetMapping("/search/theOldest")
	public ResponseEntity<Dog> findTheOldestDog() {
		return ResponseEntity.ok(dogService.findTheOldestDog());
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Integer> remove(@PathVariable Integer id) {
		return ResponseEntity.ok(dogService.remove(id));
	}

	@PutMapping("/search/{id}")
	public ResponseEntity<Dog> adoptDog(@PathVariable Integer id) {
		return ResponseEntity.ok(dogService.adoptDog(id));
	}

	@PutMapping("/search/update")
	public ResponseEntity<Dog> updateDog(@RequestBody Dog dog) {
		return ResponseEntity.ok(dogService.updateDog(dog));
	}

	@GetMapping("/averageAgeOfAdoptedDogs")
	public ResponseEntity<Double> averageAgeOfAdoptedDogs() {
		return ResponseEntity.ok(dogService.averageAgeOfAdoptedDogs());
	}

	@GetMapping("/amountOfAdoptedDogs")
	public ResponseEntity<Integer> amountOfAdoptedDogs() {
		return ResponseEntity.ok(dogService.amountOfAdoptedDogs());
	}

	@GetMapping("/amountOfAllDogsEver")
	public ResponseEntity<Integer> amountOfAllDogsEver() {
		return ResponseEntity.ok(dogService.amountOfAllDogsEver());
	}

	@GetMapping("avgAge")
	public ResponseEntity<Double> avgAge() {
		return ResponseEntity.ok(dogService.avgAge());
	}

	@PutMapping("/adoptMultipleDogs")
	public ResponseEntity<List<Dog>> adoptMultipleDogs(@RequestParam List<Integer> idList) {
		return ResponseEntity.ok(dogService.adoptMultipleDogs(idList));
	}

	@GetMapping("/search/consist/{word}")
	public ResponseEntity<List<Dog>> findAllNameConsist(@PathVariable String word) {
		return ResponseEntity.ok(dogService.findAllNameConsist(word));
	}

	@PutMapping("/adoptOrNot/{adopted}")
	public ResponseEntity<List<Dog>> adoptOrNot(@RequestParam List<Integer> idList, boolean adopted) {
		return ResponseEntity.ok(dogService.adoptOrNot(idList, adopted));
	}

	@PutMapping("/adoptOrNotByName/{name}/{adopted}")
	public ResponseEntity<Dog> adoptOrNotByName(@PathVariable String name, @PathVariable boolean adopted) {
		return ResponseEntity.ok(dogService.adoptOrNotByName(name, adopted));
	}

	@PutMapping("/adoptOrNotManyDogsByName")
	public ResponseEntity<List<Dog>> adoptOrNotManyDogsByName(@RequestParam List<String> names, @RequestParam List<Boolean> adopted) {
		return ResponseEntity.ok(dogService.adoptOrNotManyDogsByName(names, adopted));
	}

	@GetMapping("/adoptDogsByDate/{number}/{adopted}")
	public ResponseEntity<List<Dog>> adoptDogsByDate(@PathVariable Integer number, @PathVariable boolean adopted) {
		return ResponseEntity.ok(dogService.adoptDogsByDate(number, adopted));
	}

	@GetMapping("/displayUpdatedDogs/{number}")
	public ResponseEntity<List<Dog>> displayUpdatedDogs(@PathVariable Integer number) {
		return ResponseEntity.ok(dogService.displayUpdatedDogs(number));
	}

	@GetMapping("/displayDogsUpdated7DaysAgo")
	public ResponseEntity<List<Dog>> dogsUpdated7DaysAgo() {
		return ResponseEntity.ok(dogService.dogsUpdated7DaysAgo());
	}

	@GetMapping("/lastAdoptionDate")
	public ResponseEntity<Date> lastUpdateDate() {
		return ResponseEntity.ok(dogService.lastUpdateDate().getUpdateDate());
	}

	@GetMapping("/lastAdoptedDogsName")
	public ResponseEntity<String> lastAdoptedDogsName() {
		return ResponseEntity.ok(dogService.lastUpdateDate().getName());
	}

	@GetMapping("/amountOfDogsForAdoption")
	public ResponseEntity<Integer> amountOfDogsForAdoption() {
		return ResponseEntity.ok(dogService.findByAdopted(false).size());
	}

	@GetMapping("/findByNameContains/{name}")
	public ResponseEntity<List<Dog>> findByNameContains(@PathVariable String name) {
		return ResponseEntity.ok(dogService.findByNameContains(name));
	}
}
