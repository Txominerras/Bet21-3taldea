package domain;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Chat {

	@Id
	@XmlID
	String ChatIzena;
	@XmlIDREF
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	ArrayList<User> partaideak;
	@XmlIDREF
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	ArrayList<Message> mezuak;
	
	public Chat(String ChatIzena, ArrayList<User> Partaideak){
		partaideak= Partaideak;
		this.ChatIzena =ChatIzena;
		mezuak = new ArrayList<Message>();
	}
	
	public Chat() {
		
	}
	
	public String getChatIzena() {
		return ChatIzena;
	}
	public void setChatIzena(String chatIzena) {
		ChatIzena = chatIzena;
	}
	public ArrayList<User> getPartaideak() {
		return partaideak;
	}
	public void setPartaideak(ArrayList<User> partaideak) {
		this.partaideak = partaideak;
	}
	public ArrayList<Message> getMezuak() {
		return mezuak;
	}
	public void setMezuak(ArrayList<Message> mezuak) {
		this.mezuak = mezuak;
	}
	
	public void AddMessage(Message mezua) {
		this.mezuak.add(mezua);
	}
	
	
	
	
	
}