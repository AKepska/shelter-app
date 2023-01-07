package com.javafee.shelter.front.controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Objects;

import com.javafee.shelter.front.controller.utils.Params;
import com.javafee.shelter.front.model.Dog;
import com.javafee.shelter.front.view.DogForm;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class DogController {
	private DogForm dogForm;
	private MainController mainController;

	private boolean isModify = false;

	public DogController() {
	}

	public void control(MainController mainController) {
		isModify = !Objects.isNull(Params.getInstance().get("SELECTED_DOG"));
		if (Objects.isNull(dogForm)) {
			dogForm = new DogForm();
			initializeWindow();
			initEventHandlers();
		} else
			initializeWindow();
		this.mainController = mainController;
	}

	private void initEventHandlers() {
		dogForm.getButtonZatwierdz().addActionListener(e -> performSave());
		dogForm.getButtonAnuluj().addActionListener(e -> dogForm.getFrame().setVisible(false));
		dogForm.getFrame().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Params.getInstance().remove("SELECTED_DOG");
				super.windowClosing(e);
			}
		});
	}

	private void initializeWindow() {
		if (isModify) {
			HttpResponse<Dog> httpResponseDog;
			Dog dog = (Dog) Params.getInstance().get("SELECTED_DOG");
			try {
				httpResponseDog = Unirest.get("http://localhost:8181/api/shelter/" + dog.getId()).asObject(Dog.class);
				dogForm.getTextImie().setText(httpResponseDog.getBody().getName());
				dogForm.getTextRasa().setText(httpResponseDog.getBody().getBreed());
				dogForm.getTextWiek().setText(String.valueOf(httpResponseDog.getBody().getAge()));
				dogForm.getCheckBoxPilnosc().setSelected(httpResponseDog.getBody().getUrg() == 'y');
				dogForm.getCheckBoxRodowod().setSelected(httpResponseDog.getBody().getLineage() == 'y');
				dog.getUpdateDate();
				//                ((Dog) Params.getInstance().get("SELECTED_DOG")).getUpdateDate();
				dog.getId();
				//                ((Dog) Params.getInstance().get("SELECTED_DOG")).getId();
			} catch (UnirestException e) {
				throw new RuntimeException(e);
			}
		} else {
			clearForm();
		}
		HttpResponse<Integer> httpResponse;
		try {
			httpResponse = Unirest.get("http://localhost:8181/api/shelter/amountOfDogsForAdoption").asObject(Integer.class);
			dogForm.getLblIloscPsowDoAdopcji().setText(String.valueOf(httpResponse.getBody()));
		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}
		dogForm.getFrame().setVisible(true);
	}

	private void clearForm() {
		dogForm.getTextImie().setText("");
		dogForm.getTextRasa().setText("");
		dogForm.getTextWiek().setText("");
		dogForm.getCheckBoxPilnosc().setSelected(false);
		dogForm.getCheckBoxRodowod().setSelected(false);
	}

	private void performSave() {
		Dog dog = new Dog(
				dogForm.getTextImie().getText(),
				dogForm.getTextRasa().getText(),
				Integer.parseInt(dogForm.getTextWiek().getText()),
				dogForm.getCheckBoxPilnosc().isSelected() ? 'y' : 'n',
				false,
				dogForm.getCheckBoxRodowod().isSelected() ? 'y' : 'n',
				new Date(),
				(isModify ? ((Dog) Params.getInstance().get("SELECTED_DOG")).getId() : null));
		if (isModify) {

			try {
				HttpResponse<Object> httpResponse = Unirest.put("http://localhost:8181/api/shelter/search/update")
						.header("content-type", "application/json").body(dog).asObject(Object.class);
				System.out.println(httpResponse.getStatus());
			} catch (UnirestException e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				HttpResponse<Object> httpResponse = Unirest.post("http://localhost:8181/api/shelter/saveNewDog")
						.header("content-type", "application/json").body(dog).asObject(Object.class);
				System.out.println(httpResponse.getStatus());
			} catch (UnirestException e) {
				throw new RuntimeException(e);
			}
		}

		Params.getInstance().remove("SELECTED_DOG");
		mainController.reloadDogList();
		dogForm.getFrame().setVisible(false);
		mainController.reloadstatistic();
	}
}
