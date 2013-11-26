package assignment2.model;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import assignment2.adapter.BirthDateAdapter;
import assignment2.adapter.DateAdapter;

@XmlRootElement
@Entity
@Table
@XmlType(propOrder = { "person_id", "firstname", "lastname", "birthdate",
		"height", "weight", "bmi", "lastupdate", "healthProfileHistory",
		"healthProfileIds" })
public class Person {

	@Id
	@GeneratedValue
	private Long person_id;

	private String firstname;
	private String lastname;

	private Date birthdate;

	private Double height;
	private Double weight;
	private Date lastupdate;

	@Transient
	private ArrayList<HealthProfile> healthProfileHistory;

	@Transient
	private ArrayList<Long> healthProfileIds;

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
			Double height, Double weight, Date lastupdate) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.height = height;
		this.weight = weight;
		this.lastupdate = lastupdate;
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

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	@XmlElement(name = "bmi")
	public Double getBmi() {
		return weight / (height * height);
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

	public ArrayList<Long> getHealthProfileIds() {
		return healthProfileIds;
	}

	public void setHealthProfileIds(ArrayList<Long> healthProfileIds) {
		this.healthProfileIds = healthProfileIds;
	}
}
