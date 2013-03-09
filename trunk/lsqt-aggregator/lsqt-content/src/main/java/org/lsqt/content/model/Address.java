package org.lsqt.content.model;

import javax.persistence.Embeddable;

@Embeddable
public class Address
{
	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	public String getStreet()
	{
		return street;
	}
	public void setStreet(String street)
	{
		this.street = street;
	}
	public String getZip()
	{
		return zip;
	}
	public void setZip(String zip)
	{
		this.zip = zip;
	}
	private String city,street,zip;
	public Address(String city,String street,String zip){
		this.city=city;
		this.street=street;
		this.zip=zip;
	}
}
