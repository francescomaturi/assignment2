package assignment2;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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

	@Transient
	private Set<HealthProfile> healthProfileHistory;

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

	public Long getPerson_id() {
		return person_id;
	}

	public void setPerson_id(Long person_id) {
		this.person_id = person_id;
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

	public Set<HealthProfile> getHealthProfileHistory() {
		return healthProfileHistory;
	}

	public void setHealthProfileHistory(Set<HealthProfile> healthProfileHistory) {
		this.healthProfileHistory = healthProfileHistory;
	}

}
