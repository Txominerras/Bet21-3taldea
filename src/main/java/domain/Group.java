package domain;

import java.util.ArrayList;

import javax.persistence.Entity;

@Entity
public class Group extends Chat {

	private String groupAdmin;

	public Group(String ChatIzena, ArrayList<User> Partaideak, String groupAdmin) {
		super(ChatIzena, Partaideak);
		this.groupAdmin = groupAdmin;
	}

	public void setGroupAdmin(String userName) {
		this.groupAdmin = userName;
	}

	public String getGroupAdmin() {
		return this.groupAdmin;
	}

	public void addUser(User u) {
		this.partaideak.add(u);
	}

	public boolean userAlreadyIn(User u) {
		for (User user : this.getPartaideak()) {
			if (u.getUserName().equals(user.getUserName()))
				return true;
		}
		return false;
	}

	public void removeUser(User u) {
		this.getPartaideak().remove(u);
	}

	public boolean isAdmin(User user) {
		if (user.getUserName().equals(groupAdmin))
			return true;
		else
			return false;
	}

	public void setNewAdmin() {
		this.setGroupAdmin(this.getPartaideak().get(0).getUserName());

	}
}
