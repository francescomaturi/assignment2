package assignment2.model;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import assignment2.adapter.BirthDateAdapter;

@XmlRootElement
@Entity
@Table
public class Person {

	@Id
	@GeneratedValue
	private Long person_id;

	private String firstname;
	private String lastname;

	private Date birthdate;

	private Double height;
	private Double weight;

	@Transient
	private ArrayList<HealthProfile> healthProfileHistory;

	public Person() {
		super();
	}

	public Person(String firstname, String lastname) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public Person(String firstname, String lastname, Date birthdate) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
	}

	public Person(String firstname, String lastname, Date birthdate,
			Double height, Double weight) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.height = height;
		this.weight = weight;
	}

	public Long getPerson_id() {
		return person_id;
	}

	public void setPerson_id(Long person_id) {
		this.person_id = person_id;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@XmlJavaTypeAdapter(BirthDateAdapter.class)
	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public ArrayList<HealthProfile> getHealthProfileHistory() {
		return healthProfileHistory;
	}

	public void setHealthProfileHistory(
			ArrayList<HealthProfile> healthProfileHistory) {
		this.healthProfileHistory = healthProfileHistory;
	}
}
