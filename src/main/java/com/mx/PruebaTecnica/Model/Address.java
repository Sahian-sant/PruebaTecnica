package com.mx.PruebaTecnica.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

/*{
"id": <RANDOM_UUID>,
"email": "user1@mail.com",
"name": "user1",
´phone´: “+1 55 555 555 55”
"password": "7c4a8d09ca3762af61e59520943dc26494f8941b",
“tax_id“: “AARR990101XXX“,
"created_at": "01-01-2026 00:00:00",
‘addresses’: [
{
"id": 1,
"name": "workaddress",
"street": "street No. 1",
"country_code": "UK"
},
{
"id": 2,
"name": "homeaddress",
"street": "street No. 2",
"country_code": "AU"
}
]
}
*/

public class Address {

	private Integer id;
	private String name;
	private String street;
	
	@JsonProperty("country_code")
	private String countryCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

}
