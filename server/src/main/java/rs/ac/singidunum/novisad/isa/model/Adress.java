package rs.ac.singidunum.novisad.isa.model;

import javax.persistence.Embeddable;

@Embeddable
public class Adress {
	
	private String country;
	private String city;
	private String streetAndNumber;
	
	public Adress() {
		super();
	}

	public Adress(String country, String city, String streetAndNumber) {
		super();
		this.country = country;
		this.city = city;
		this.streetAndNumber = streetAndNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreetAndNumber() {
		return streetAndNumber;
	}

	public void setStreetAndNumber(String streetAndNumber) {
		this.streetAndNumber = streetAndNumber;
	}
	
}
