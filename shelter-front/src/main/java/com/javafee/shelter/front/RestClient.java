package com.javafee.shelter.front;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javafee.shelter.front.model.Dog;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class RestClient {
	public static void main(String[] args) {
		try {
			setupMapper();
			HttpResponse<Dog[]> httpResponse = Unirest.get("http://localhost:8181" + "/api/shelter").asObject(Dog[].class);
			System.out.println(new ArrayList<>(List.of(httpResponse.getBody())));
		} catch (UnirestException e) {
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
