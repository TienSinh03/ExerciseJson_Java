package entity;

import java.util.List;

public class Patient {
	private String id;
	private String firstName;
	private String lastName;
	private String bloodType;
	private String gender;
	private Address address;
	private List<String> telephones;
	private List<Test> tests;
	private int yearOfBirth;

	public Patient() {
		// TODO Auto-generated constructor stub
	}

	public Patient(String id, String firstName, String lastName, String bloodType, String gender, Address address,
			List<String> telephones, List<Test> tests, int yearOfBirth) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.bloodType = bloodType;
		this.gender = gender;
		this.address = address;
		this.telephones = telephones;
		this.tests = tests;
		this.yearOfBirth = yearOfBirth;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<String> getTelephones() {
		return telephones;
	}

	public void setTelephones(List<String> telephones) {
		this.telephones = telephones;
	}

	public List<Test> getTests() {
		return tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

	public int getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", bloodType=" + bloodType
				+ ", gender=" + gender + ", address=" + address + ", telephones=" + telephones + ", tests=" + tests
				+ ", yearOfBirth=" + yearOfBirth + "]";
	}

}
