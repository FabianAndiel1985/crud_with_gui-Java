package crudWithGui;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;


@Getter
@Setter
@AllArgsConstructor
public class Person {
	
	@Setter(AccessLevel.NONE) 
	Integer Id;
	String first_name;
	String last_name;
	String street;
	String housenumber;
	String doornumber;
	String zip;
	String city;
	String email;
	
	public Person(String first_name, String last_name, String street, String housenumber, String doornumber, String zip,
			String city, String email) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.street = street;
		this.housenumber = housenumber;
		this.doornumber = doornumber;
		this.zip = zip;
		this.city = city;
		this.email = email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, doornumber, email, first_name, housenumber, last_name, street, zip);
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
	
	
	public boolean continueEqualityCheck(Object obj) {
		if (this == obj)
			return false;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
	
	
	

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Person other = (Person) obj;
//		
//		//add list with not equals
//		//step by step		
//		
//		return Objects.equals(city, other.city) && Objects.equals(doornumber, other.doornumber)
//				&& Objects.equals(email, other.email) && Objects.equals(first_name, other.first_name)
//				&& Objects.equals(housenumber, other.housenumber) && Objects.equals(last_name, other.last_name)
//				&& Objects.equals(street, other.street) && Objects.equals(zip, other.zip);
//	}
//	
	
	
}
