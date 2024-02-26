package model;

import java.util.List;

import entity.Patient;
import handler.JOM_PatientHandler;

public class JOM_PatientModel {
	public static void main(String[] args) {
		JOM_PatientHandler handler = new JOM_PatientHandler();
		List<Patient> p = handler.getListOfPatients();
//		p.forEach(System.out::println);
		handler.wirteToJson(p);
		System.out.println("Done!!");
	}
}
