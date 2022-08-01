package com.lanbui.quarkus.address.model;

import java.util.Objects;

public class Address {
	private String houseNo;
	private String street;

	@Override
	public int hashCode() {
		return Objects.hash(houseNo, street);
	}

	@Override
	public String toString() {
		return "Address [houseNo=" + houseNo + ", street=" + street + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return Objects.equals(houseNo, other.houseNo) && Objects.equals(street, other.street);
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
}
