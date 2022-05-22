package domain;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
@Entity
public class Admin extends User{

	
	public Admin(String userName, String password, String email, int phoneNumber, int age, String realName,
			String realSurname) {
		super(userName, password, email, phoneNumber, age, realName, realSurname);
		// TODO Auto-generated constructor stub
	}
}
