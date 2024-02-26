package model;

import java.util.Iterator;
import java.util.List;

import entity.Patient;
import handler.StreamAPI_PaintHandler;

public class StreamAPI_PateintModel {
	public static void main(String[] args) {
		StreamAPI_PaintHandler handler = new StreamAPI_PaintHandler();
		List<Patient> patients = handler.getListOfPatients();
//		patients.forEach(System.out::println);
		handler.writeToJson(patients);
		System.out.println("DOne!!");
	}
}
