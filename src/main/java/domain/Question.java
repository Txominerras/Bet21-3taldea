package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import exceptions.CuoteAlreadyCreated;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Question implements Serializable {
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer questionNumber;
	private String question; 
	private float betMinimum;
	@XmlIDREF
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Event event;
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
	private ArrayList<Quote> cuotes=new ArrayList<Quote>();
	@XmlIDREF
	private Quote result;  

	
	public Question(){
		super();
	}
	
	public Question(Integer queryNumber, String query, float betMinimum, Event event) {
		super();
		this.questionNumber = queryNumber;
		this.question = query;
		this.betMinimum=betMinimum;
		this.event = event;
	}
	
	public Question(String query, float betMinimum,  Event event) {
		super();
		this.question = query;
		this.betMinimum=betMinimum;

		//this.event = event;
	}
	
	public boolean hasCuotes() {
		return !this.getCuotes().isEmpty();
	}

	/**
	 * Get the  number of the question
	 * 
	 * @return the question number
	 */
	public Integer getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * Set the bet number to a question
	 * 
	 * @param questionNumber to be setted
	 */
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}


	/**
	 * Get the question description of the bet
	 * 
	 * @return the bet question
	 */

	public String getQuestion() {
		return question;
	}


	/**
	 * Set the question description of the bet
	 * 
	 * @param question to be setted
	 */	
	public void setQuestion(String question) {
		this.question = question;
	}



	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @return the minimum bet ammount
	 */
	
	public float getBetMinimum() {
		return betMinimum;
	}


	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @param  betMinimum minimum bet ammount to be setted
	 */

	public void setBetMinimum(float betMinimum) {
		this.betMinimum = betMinimum;
	}


	/**
	 * Get the event associated to the bet
	 * 
	 * @return the associated event
	 */
	public Event getEvent() {
		return event;
	}



	/**
	 * Set the event associated to the bet
	 * 
	 * @param event to associate to the bet
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	
	
	public void setCuotes(ArrayList<Quote> c) {
		this.cuotes = c;
	}
	
	public ArrayList<Quote> getCuotes(){
		return cuotes;
	}


	public void setResult(Quote result) {
		this.result = result;
	}
	public Quote getResult() {
		return this.result;
	}

	public String toString(){
		return questionNumber+";"+question+";"+Float.toString(betMinimum);
	}

	public void addCuote(Quote cuote) {
		this.cuotes.add(cuote);
	}
	public boolean cuoteAlreadyExists(String option) {
		for(Quote c : this.getCuotes()) {
			if(c.getOption().equals(option)) {
				return true;
			}	
		}
		return false;
	}
	public Quote createCuote(String option, float prize, Question question) {
		Quote c = new Quote(option,prize,question);
		this.addCuote(c);
		return c;
	}
}