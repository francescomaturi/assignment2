package assignment2;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@Entity
@Table
public class HealthProfile {

	@Id
	@GeneratedValue
	@XmlTransient
	private Long healthprofile_id;

	private Long person_id;

	private Double weight;
	private Double height;
	private Date date;

	public HealthProfile() {
		super();
	}

	public HealthProfile(Long personId, Double weight, Double height, Date date) {
		super();
		this.person_id = personId;
		this.weight = weight;
		this.height = height;
		this.date = date;
	}

	public Long getHealthprofile_id() {
		return healthprofile_id;
	}

	public void setHealthprofile_id(Long healthprofile_id) {
		this.healthprofile_id = healthprofile_id;
	}

	@XmlTransient
	public Long getPerson_id() {
		return person_id;
	}

	public void setPerson_id(Long person_id) {
		this.person_id = person_id;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getBMI() {
		return weight / (height * height);
	}

}
