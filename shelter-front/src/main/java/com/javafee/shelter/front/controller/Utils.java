package com.javafee.shelter.front.controller;

import java.util.List;
import java.util.Vector;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {
	public Vector toVector(List list) {
		Vector v = new Vector();
		list.forEach(e -> v.add(e));
		return v;
	}
}
