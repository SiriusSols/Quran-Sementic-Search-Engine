package org.qsse.server.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import org.qsse.shared.type.UserType;

/*
 * DataBase Entity/Table contain users info 
 * */

@Entity
public class User {

	public static final String KIND = "User";
	
	/*
	 * private Data Members
	 * Entity/Table columns
	 * */
	
	@Id
    @GeneratedValue
    private Key key;
	
	private String email;
	private String password;
	private UserType userType;
	private boolean active;
	private String firstName;
	private String lastName;
	private String gender;
	private Date dob;
	private String phone;
	private String address;
	private String userDp;
	
	/*
	 * Setter and Getter for private Data Members
	 * */
	
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public static Key key(long id) {
		return KeyFactory.createKey(KIND, id);
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getUserDp() {
		return userDp;
	}
	public void setUserDp(String userDp) {
		this.userDp = userDp;
	}
	
}
