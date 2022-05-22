package domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Message {
	
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	int Messageid;
	String textua;
	@XmlIDREF
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	Chat chata;
	@XmlIDREF
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	User igorlea;
	Date Data;
	
	
	
	public Message(User igorlea,Date data,String Text, Chat chata) {
		textua=Text;
		this.chata= chata;
		this.igorlea=igorlea;
		Data=data;
	}
	
	public User getIgorlea() {
		return igorlea;
	}

	public void setIgorlea(User igorlea) {
		this.igorlea = igorlea;
	}
	
	public int getMessageid() {
		return Messageid;
	}
	public void setMessageid(int messageid) {
		Messageid = messageid;
	}
	public String getTextua() {
		return textua;
	}
	public void setTextua(String textua) {
		this.textua = textua;
	}
	public Chat getChata() {
		return chata;
	}
	public void setChata(Chat chata) {
		this.chata = chata;
	}
	
	public Date getData() {
		return Data;
	}
	
	
	
	

}