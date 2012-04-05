package org.qsse.client.actions;


import java.util.Date;

import org.qsse.shared.dto.VoidResult;
import org.qsse.shared.type.UserType;

import net.customware.gwt.dispatch.shared.Action;

/*
 * Action class for user registration
 */

public class CreateUser implements Action<VoidResult> {
	
	private String email;
	private String password;
	private UserType userType;
	private String firstName;
	private String lastName;
	private String gender;
	private Date dob;
	private String phone;
	private String address;
	private String userDp;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public String getUserDp() {
		return userDp;
	}
	public void setUserDp(String userDp) {
		this.userDp = userDp;
	}
	
}
