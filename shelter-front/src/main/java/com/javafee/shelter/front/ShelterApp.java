package com.javafee.shelter.front;

import javax.swing.UIManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javafee.shelter.front.controller.MainController;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;

public class ShelterApp {
	public static void main(String[] args) {
		try {
			setupMapper();
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new MainController().control();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void setupMapper() {
		Unirest.setObjectMapper(new ObjectMapper() {
			com.fasterxml.jackson.databind.ObjectMapper mapper
					= new com.fasterxml.jackson.databind.ObjectMapper();

			public String writeValue(Object value) {
				try {
					return mapper.writeValueAsString(value);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			}

			public <T> T readValue(String value, Class<T> valueType) {
				try {
					return mapper.readValue(value, valueType);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
}
