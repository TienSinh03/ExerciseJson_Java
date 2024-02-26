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
import jakarta.json.JsonObject;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.stream.JsonParser;
import jakarta.json.stream.JsonParser.Event;

public class StreamAPI_PaintHandler {
	public List<Patient> getListOfPatients() {
		List<Patient> list = new ArrayList<>();

		List<String> telephones = null;
		List<Test> tests = null;

		Patient patient = null;
		Address address = null;
		Test test = null;
		String keyName = "";

		try (JsonParser parser = Json.createParser(new FileReader("fileJson/patient.json"))) {

			while (parser.hasNext()) {
				Event event = parser.next();
				switch (event) {
				case START_ARRAY:
					if (keyName.equals("telephones")) {
						telephones = new ArrayList<>();
					} else if (keyName.equals("tests")) {
						tests = new ArrayList<>();
					} else {
						list = new ArrayList<>();
					}
					break;
				case START_OBJECT:
					if (keyName.equals("address")) {
						address = new Address();
					} else if ((keyName.equals("tests") || keyName.equals("cost")) && patient.getTests() == null) {
						test = new Test();
					} else if (keyName.equals("date")) {
						JsonObject ja = parser.getObject();
						LocalDate date = LocalDate.of(ja.getInt("year"), ja.getInt("month"), ja.getInt("day"));
						test.setDate(date);
					} else {
						patient = new Patient();
					}
					break;
				case KEY_NAME:
					keyName = parser.getString();
					break;
				case VALUE_STRING:
					String vaString = parser.getString();
					switch (keyName) {
					case "_id":
						patient.setId(vaString);
						break;
					case "first_name":
						patient.setFirstName(vaString);
						break;
					case "last_name":
						patient.setLastName(vaString);
						break;
					case "blood_type":
						patient.setBloodType(vaString);
						break;
					case "gender":
						patient.setGender(vaString);
						break;
					case "city":
						address.setCity(vaString);
						break;
					case "district":
						address.setDistrict(vaString);
						break;
					case "street":
						address.setStreet(vaString);
						break;
					case "ward":
						address.setWard(vaString);
						break;
					case "result":
						test.setResult(vaString);
						break;
					case "test_type":
						test.setTest_type(vaString);
						break;
					case "telephones":
						telephones.add(vaString);
						break;
					default:
						break;
					}
					break;
				case VALUE_NUMBER:
					switch (keyName) {
					case "test_id":
						test.setTest_id(parser.getInt());
						break;
					case "cost":
						test.setCost(parser.getBigDecimal().doubleValue());
						break;
					case "year_of_birth":
						patient.setYearOfBirth(parser.getInt());
						break;
					default:
						break;
					}
					break;
				case END_OBJECT:
					if(keyName.equals("ward")) {
						patient.setAddress(address);
					} else if(keyName.equals("cost")||patient.getTests()==null){
						 tests.add(test);
					} else if (keyName.equals("year_of_birth")) {
						list.add(patient);
					}
					break;
				case END_ARRAY:
					if(keyName.equals("telephones")) {
						patient.setTelephones(telephones);
					} else if(keyName.equals("cost")) {
						patient.setTests(tests);
					}
					break;

				default:
					break;
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
	public void writeToJson(List<Patient> patients) {
		try (JsonGenerator gen = Json.createGenerator(new FileWriter("fileJson/PhanTienSinh_21010761_JsAPI.json"))) {
			gen.writeStartArray();
			patients.forEach(p -> {
				gen.writeStartObject()
				.write("_id", p.getId())
				.write("first_name", p.getFirstName())
				.write("last_name", p.getLastName())
				.write("blood_type", p.getBloodType())
				.write("gender", p.getGender())
				.writeKey("address")
				.writeStartObject()				
				.write("city", p.getAddress().getCity())
				.write("district", p.getAddress().getDistrict())
				.write("street", p.getAddress().getStreet())
				.write("ward", p.getAddress().getWard())				
				.writeEnd()
				.writeKey("telephones")
				.writeStartArray()
				.write(p.getTelephones().get(0))
				.write(p.getTelephones().get(1))
				.writeEnd()
				.writeKey("tests")
				.writeStartArray();
				
				p.getTests().forEach(t -> {
					
					gen.writeStartObject()
					.writeKey("date")
					.writeStartObject()
					.write("year", t.getDate().getYear())
					.write("month", t.getDate().getMonthValue())
					.write("day", t.getDate().getDayOfMonth())
					.writeEnd()
					.write("result", t.getResult())
					.write("test_id",t.getTest_id())
					.write("test_type", t.getTest_type())
					.write("cost", t.getCost())
					.writeEnd();
				});
				gen.writeEnd()
				.write("year_of_birth", p.getLastName())
				.writeEnd();
			});
			gen.writeEnd();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
