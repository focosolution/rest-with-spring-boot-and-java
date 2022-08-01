package br.com.focosolution.model;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private long firstName; 
	private long lastName;
	private long address;
	private long gender;
	
	// JPA exige tal construtor	
	public Person() {}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getFirstName() {
		return firstName;
	}
	public void setFirstName(long firstName) {
		this.firstName = firstName;
	}
	public long getLastName() {
		return lastName;
	}
	public void setLastName(long lastName) {
		this.lastName = lastName;
	}
	public long getAddress() {
		return address;
	}
	public void setAddress(long address) {
		this.address = address;
	}
	public long getGender() {
		return gender;
	}
	public void setGender(long gender) {
		this.gender = gender;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, firstName, gender, id, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return address == other.address && firstName == other.firstName && gender == other.gender && id == other.id
				&& lastName == other.lastName;
	}
	
	
	
}
