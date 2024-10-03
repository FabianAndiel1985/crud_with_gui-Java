package crudWithGui;

import lombok.Getter;
import lombok.Setter;
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
	
}
