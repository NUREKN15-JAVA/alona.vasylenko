package com.nixsolutions.usermanagement;

import java.util.Calendar;
import java.util.Date;

public class User {
	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirthd;
	private Calendar calendar;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Date getDateOfBirthd() {
		return dateOfBirthd;
	}
	public void setDateOfBirthd(Date dateOfBirthd) {
		this.dateOfBirthd = dateOfBirthd;
	}
	public String getFullName() {
		return getLastName()+"," + getFirstName();
	}
	public Object getAge() {
	    Calendar calendar2 = Calendar.getInstance();
	    calendar2.setTime(new Date());
	    int currentYear = calendar2.get(Calendar.YEAR);
	    calendar2.setTime(getDateOfBirthd());
	    int year = calendar.get(Calendar.YEAR);
		return currentYear - year;
	}

}
