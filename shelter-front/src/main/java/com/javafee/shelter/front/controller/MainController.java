package com.javafee.shelter.front.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.javafee.shelter.front.controller.utils.Params;
import com.javafee.shelter.front.model.Dog;
import com.javafee.shelter.front.view.MainForm;
import com.javafee.shelter.front.view.model.ShelterTableModel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MainController {
	private MainForm mainForm;
	private DogController dogController;

	public MainController() {
		this.mainForm = new MainForm();
		this.dogController = new DogController();
	}

	public void control() throws Exception {
		initializeWindow();

		mainForm.getBtnDodaj().addActionListener(e -> onClickBtnDodaj());
		mainForm.getBtnModyfikuj().addActionListener(e -> onClickBtnModyfikuj());
		mainForm.getBtnUsun().addActionListener(e -> onClickBtnUsun());
		mainForm.getBtnAdoptuj().addActionListener(e -> onClickBtnAdoptuj());
		mainForm.getButtonSzukaj().addActionListener(e -> onClickBtnSzukaj());
	}

	private void initializeWindow() throws Exception {
		reloadDogList();
		reloadstatistic();
		mainForm.getFrame().setVisible(true);
	}

	public void reloadDogList() {
		HttpResponse<Dog[]> httpResponse = null;
		try {
			httpResponse = Unirest.get("http://localhost:8181/api/shelter/search/false/adopted").asObject(Dog[].class);
			((ShelterTableModel) mainForm.getTableShelter().getModel()).setAllDogs(List.of(httpResponse.getBody()));
		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}
	}

	public void reloadstatistic() {
		HttpResponse<Integer> httpResponse;
		HttpResponse<Double> httpResponseDouble;
		HttpResponse<Date> httpResponseDate;
		HttpResponse<String> httpResponseString;
		try {
			httpResponse = Unirest.get("http://localhost:8181/api/shelter/amountOfAdoptedDogs").asObject(Integer.class);
			mainForm.getFieldiloscPsowAdoptowanych().setText(String.valueOf(httpResponse.getBody()));
		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}
		try {
			httpResponse = Unirest.get("http://localhost:8181/api/shelter/amountOfAllDogsEver").asObject(Integer.class);
			mainForm.getFieldIloscPsowPrzyjetych().setText(String.valueOf(httpResponse.getBody()));
		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}
		try {
			httpResponseDouble = Unirest.get("http://localhost:8181/api/shelter/avgAge").asObject(Double.class);
			mainForm.getFieldSredniWiek().setText(String.valueOf(httpResponseDouble.getBody()));
		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}
		try {
			httpResponseDouble = Unirest.get("http://localhost:8181/api/shelter/averageAgeOfAdoptedDogs").asObject(Double.class);
			mainForm.getSredniWiekPsowAdoptowanych().setText(String.valueOf(httpResponseDouble.getBody()));
		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}
		try {
			httpResponseDouble = Unirest.get("http://localhost:8181/api/shelter/averageAgeOfAdoptedDogs").asObject(Double.class);
			mainForm.getSredniWiekPsowAdoptowanych().setText(String.valueOf(httpResponseDouble.getBody()));
		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}
		try {
			httpResponseDate = Unirest.get("http://localhost:8181/api/shelter/lastAdoptionDate").asObject(Date.class);
			httpResponseString = Unirest.get("http://localhost:8181/api/shelter/lastAdoptedDogsName").asObject(String.class);
			mainForm.getFieldOstatniaAdopcja().setText(String.valueOf(new SimpleDateFormat("MM.dd.yyy").format(httpResponseDate.getBody())) + " " + httpResponseString.getBody());
		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}
	}

	private void onClickBtnDodaj() {
		dogController.control(this);
		reloadDogList();
	}

	private void onClickBtnModyfikuj() {
		int index = mainForm.getTableShelter().convertRowIndexToModel(mainForm.getTableShelter().getSelectedRow());
		Params.getInstance().add("SELECTED_DOG", ((ShelterTableModel) mainForm.getTableShelter().getModel()).getDog(index));

		//        Params.getInstance().add("SELECTED_DOG", mainForm.getListListaPsow().getSelectedValue());
		dogController.control(this);
		reloadDogList();
	}

	private void onClickBtnUsun() {
		HttpResponse<Integer> httpResponse = null;
		int index = mainForm.getTableShelter().convertRowIndexToModel(mainForm.getTableShelter().getSelectedRow());
		Dog dog = ((ShelterTableModel) mainForm.getTableShelter().getModel()).getDog(index);
		try {
			httpResponse = Unirest.delete("http://localhost:8181/api/shelter/delete/" + dog.getId()).asObject(Integer.class);
		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}
		reloadDogList();
		reloadstatistic();
	}

	private void onClickBtnAdoptuj() {
		int index = mainForm.getTableShelter().convertRowIndexToModel(mainForm.getTableShelter().getSelectedRow());
		Dog dog = ((ShelterTableModel) mainForm.getTableShelter().getModel()).getDog(index);
		Integer id = dog.getId();

		HttpResponse<Dog> httpResponse = null;
		try {
			httpResponse = Unirest.get("http://localhost:8181/api/shelter/" + dog.getId()).asObject(Dog.class);
		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}
		Dog responseDog = httpResponse.getBody();
		Dog selectedDog = new Dog(
				responseDog.getName(),
				responseDog.getBreed(),
				responseDog.getAge(),
				responseDog.getUrg(),
				false,
				responseDog.getLineage(),
				new Date(),
				responseDog.getId());
		HttpResponse<Dog> httpResponseAdoptedDog = null;
		try {
			httpResponseAdoptedDog = Unirest.put("http://localhost:8181/api/shelter/search/" + responseDog.getId()).asObject(Dog.class);
		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}
		reloadDogList();
		reloadstatistic();
	}

	private void onClickBtnSzukaj() {
		HttpResponse<Dog[]> httpResponse = null;
		String name = mainForm.getTextFieldWyszukaj().getText();
		if (!"".equals(name)) {
			try {
				httpResponse = Unirest.get("http://localhost:8181/api/shelter/findByNameContains/" + name).asObject(Dog[].class);
				List<Dog> filteredList = new ArrayList<>(List.of(httpResponse.getBody()));
				((ShelterTableModel) mainForm.getTableShelter().getModel()).setAllDogs(filteredList);
			} catch (UnirestException e) {
				throw new RuntimeException(e);
			}
		} else {
			reloadDogList();
		}
	}

}
