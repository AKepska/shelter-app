package com.javafee.shelter.front.view.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.table.AbstractTableModel;

import com.javafee.shelter.front.model.Dog;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ShelterTableModel extends AbstractTableModel {
	protected List<Dog> dogs;
	private String[] columns;

	public ShelterTableModel() {
		super();
		this.prepareData();
		this.columns = new String[]{
				"name",
				"breed",
				"age",
				"urgency",
				"lineage",
				"update date"
		};
	}

	protected void prepareData() {
		HttpResponse<Dog[]> httpResponse = null;
		try {
			dogs = new ArrayList<>(List.of(
					Unirest.get("http://localhost:8181/api/shelter/search/false/adopted").asObject(Dog[].class).getBody()));
		} catch (
				UnirestException e) {
			throw new RuntimeException(e);
		}
	}

	public Dog getDog(int index) {
		return dogs.get(index);
	}

	public void setDog(int index, Dog dog) {
		dogs.set(index, dog);
	}

	public void setAllDogs(List<Dog> dogs) {
		this.dogs.clear();
		this.dogs.addAll(dogs);
		this.fireTableDataChanged();
	}

	public void add(Dog dog) {
		dogs.add(dog);
		this.fireTableDataChanged();
	}

	public void remove(Dog dog) {
		dogs.remove(dog);
		this.fireTableDataChanged();
	}

	private void executeUpdate(Dog dog) {
		try {
			HttpResponse<Object> httpResponse = Unirest.put("http://localhost:8181/api/shelter/search/update")
					.header("content-type", "application/json").body(dog).asObject(Object.class);
			System.out.println(httpResponse.getStatus());
		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public int getRowCount() {
		return dogs.size();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		Dog dog = dogs.get(row);
		switch (ShelterTableColumn.getByNumber(col)) {
			case COL_NAME:
				return dog.getName();
			case COL_BREED:
				return dog.getBreed();
			case COL_AGE:
				return dog.getAge();
			case COL_URG:
				if (dog.getUrg() == 'y') {
					return "pilne";
				} else {
					return "tryb zwykły";
				}
			case COL_LINEAGE:
				if (dog.getUrg() == 'y') {
					return "tak";
				} else {
					return "nie";
				}
			case COL_UPDATE_DATE:
				return new SimpleDateFormat("MM.dd.yyyy").format(dog.getUpdateDate());
			default:
				return null;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		Dog dog = dogs.get(row), dogShallowClone = null;
		try {
			dogShallowClone = (Dog) dog.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println(e.getMessage());
		}

		switch (ShelterTableColumn.getByNumber(col)) {
			case COL_NAME:
				dogShallowClone.setName(value.toString());
				break;
			case COL_BREED:
				dogShallowClone.setBreed(value.toString());
			case COL_AGE:
				dogShallowClone.setAge((Integer) value);
				break;
			case COL_URG:
				dogShallowClone.setUrg((Character) value);
				break;
			case COL_LINEAGE:
				dogShallowClone.setLineage((Character) value);
				break;
			case COL_UPDATE_DATE:
				dogShallowClone.setUpdateDate((Date) value);
				break;
		}
		executeUpdate(dog);
		dogs.set(row, dogShallowClone);
		this.fireTableRowsUpdated(row, row);
	}

	@Override
	public String getColumnName(int col) {
		return columns[col];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public enum ShelterTableColumn {
		COL_NAME(0), COL_BREED(1), COL_AGE(2), COL_URG(3),
		COL_LINEAGE(4), COL_UPDATE_DATE(5);

		private final Integer value;

		ShelterTableColumn(final int newValue) {
			value = newValue;
		}

		public static ShelterTableColumn getByNumber(int clientTableSelectedIndex) {
			return Stream.of(ShelterTableColumn.values())
					.filter(item -> item.getValue().equals(clientTableSelectedIndex)).findFirst().get();
		}

		public Integer getValue() {
			return value;
		}
	}
}

