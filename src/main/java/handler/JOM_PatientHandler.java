package handler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.Address;
import entity.Patient;
import entity.Test;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.json.JsonWriter;

public class JOM_PatientHandler {
	public List<Patient> getListOfPatients () {
		List<Patient> list = new ArrayList<>();;
		List<String> telephones = null;
		List<Test> tests = null;
		
		Patient patient = null;
		Address address = null;
		Test test = null;
		
		try (JsonReader  reader = Json.createReader(new FileReader("fileJson/patient.json"))){
			JsonArray ja = reader.readArray();
			for (JsonValue jsonValue : ja) {
				JsonObject job = (JsonObject) jsonValue;
				
				//address
				JsonObject job_address = job.getJsonObject("address");
				address = new Address();
				address.setCity(job_address.getString("city"));
				address.setDistrict(job_address.getString("district"));
				address.setStreet(job_address.getString("street"));
				address.setWard(job_address.getString("ward"));
				
				//Telephones
				telephones = new ArrayList<>();
				JsonArray ja_tele = job.getJsonArray("telephones");
				for (JsonValue jsonValue2 : ja_tele) {
					telephones.add(jsonValue2.toString());
				}
				
				//Tests
				tests = new ArrayList<>();
				JsonArray ja_test = job.getJsonArray("tests");
				for (JsonValue jv : ja_test) {
					JsonObject job_test = (JsonObject) jv;
					JsonObject job_date = job_test.getJsonObject("date");
					LocalDate date = LocalDate.of(job_date.getInt("year"), job_date.getInt("month"),job_date.getInt("day"));
					
					test = new Test();
					test.setDate(date);
					test.setResult(job_test.getString("result"));
					test.setTest_id(job_test.getInt("test_id"));
					test.setTest_type(job_test.getString("test_type"));
					test.setCost(job_test.getJsonNumber("cost").bigDecimalValue().doubleValue());
					
					tests.add(test);
				}
				
				
				//Patient
				patient = new Patient();
				patient.setId(job.getString("_id"));
				patient.setFirstName(job.getString("first_name"));
				patient.setLastName(job.getString("last_name"));
				patient.setBloodType(job.getString("blood_type"));
				patient.setGender(job.getString("gender"));
				patient.setAddress(address);
				patient.setTelephones(telephones);
				patient.setTests(tests);
				patient.setYearOfBirth(job.getInt("year_of_birth"));
				
				list.add(patient);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
 	}
	
	public void wirteToJson(List<Patient> patients) {
		try (JsonWriter writer = Json.createWriter(new FileWriter("fileJson/PhanTienSinh_21010761_JOM.json"))){
			
			JsonArrayBuilder aBuilder = Json.createArrayBuilder();
			JsonObjectBuilder oBuilder = Json.createObjectBuilder();
			
			patients.forEach(p ->{
				
				//address
				JsonObject job_address = oBuilder.add("city",p.getAddress().getCity())
							.add("district",p.getAddress().getDistrict())
							.add("street",p.getAddress().getStreet())
							.add("ward", p.getAddress().getWard())
							.build();
				
				JsonArrayBuilder aBuilder_tele = Json.createArrayBuilder();
				p.getTelephones().forEach(aBuilder_tele::add);
				JsonArray ja_tele = aBuilder_tele.build();
				
				//tests
				JsonArrayBuilder aBuilder_tests = Json.createArrayBuilder();
				p.getTests().forEach(t -> {
					JsonObject job_date = oBuilder.add("year", t.getDate().getYear())
							.add("month", t.getDate().getMonthValue())
							.add("day", t.getDate().getDayOfMonth())
							.build();
					
					JsonObject job_test = oBuilder.add("date", job_date)
							.add("result", t.getResult())
							.add("test_id", t.getTest_id())
							.add("test_type", t.getTest_type())
							.add("cost",t.getCost())
							.build();
					aBuilder_tests.add(job_test);
				});
						JsonArray ja_tests = aBuilder_tests.build();
				//patient
				JsonObject job = oBuilder.add("_id",p.getId())
						.add("first_name",p.getFirstName())
						.add("last_name", p.getLastName())
						.add("blood_type",p.getBloodType())
						.add("gender", p.getGender())
						.add("address", job_address)
						.add("telephones",ja_tele)
						.add("tests", ja_tests)
						.add("year_of_birth", p.getYearOfBirth())
						.build();
				aBuilder.add(job);					
			
			});
			JsonArray ja = aBuilder.build();
			writer.writeArray(ja);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}

