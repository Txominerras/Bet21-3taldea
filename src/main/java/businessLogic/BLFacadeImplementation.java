package businessLogic;

import java.util.ArrayList;
//hola
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.Transaction;
import domain.User;
import domain.Bet;
import domain.Chat;
import domain.Quote;
import domain.Event;
import domain.Group;
import domain.Message;
import domain.NormalUser;
import exceptions.AgeException;
import exceptions.BetAlreadyCreatedException;
import exceptions.CuoteAlreadyCreated;
import exceptions.CuoteHasNotQuestion;
import exceptions.DatePassed;
import exceptions.EmailUsed;
import exceptions.EventAlreadyCreated;
import exceptions.EventFinished;
import exceptions.GroupAlreadyCreated;
import exceptions.IsEmpty;
import exceptions.NoValidMoneyException;
import exceptions.NonPositiveNum;
import exceptions.NotEnoughMoneyException;
import exceptions.NotEnoughUsers;
import exceptions.PhoneNumberUsed;
import exceptions.QuestionAlreadyExist;
import exceptions.QuestionHasResult;
import exceptions.UserAlreadyInTheGroup;
import exceptions.UserNameAlreadyUsed;
import exceptions.UserNotExist;
import exceptions.WrongPassword;
import exceptions.invalidPercentage;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			dbManager = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
		} else
			dbManager = new DataAccess();
		dbManager.close();

	}

	public BLFacadeImplementation(DataAccess da) {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager = da;
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished        if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum)
			throws EventFinished, QuestionAlreadyExist {

		// The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		qry = dbManager.createQuestion(event, question, betMinimum);

		dbManager.close();

		return qry;
	};

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public Vector<Event> getEvents(Date date) {
		dbManager.open(false);
		Vector<Event> events = dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

	/**
	 * This method invokes the data access to retrieve the dates a month for which
	 * there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date> dates = dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}

	public void close() {
		DataAccess dB4oManager = new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some
	 * events and questions. It is invoked only when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD() {
		dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}

	@WebMethod
	public User login(String user, String pass) throws IsEmpty, WrongPassword, UserNameAlreadyUsed, UserNotExist {
		User e;
		if (user.isEmpty() || pass.isEmpty())
			throw new IsEmpty(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
		dbManager.open(false);
		e = dbManager.login(user, pass);
		dbManager.close();
		return e;
	}

	@WebMethod
	public NormalUser register(String userName, String pass, String email, int pNumber, int age, String rName,
			String rSurname)
			throws IsEmpty, WrongPassword, UserNameAlreadyUsed, AgeException, PhoneNumberUsed, EmailUsed {
		NormalUser e;
		dbManager.open(false);
		e = dbManager.register(userName, pass, email, pNumber, age, rName, rSurname);
		dbManager.close();
		return e;
	}

	@WebMethod
	public Event createEvent(String d, Date date) throws DatePassed, EventAlreadyCreated {
		Event e;
		dbManager.open(false);
		e = dbManager.createEvent(d, date);
		System.out.println(e.getEventNumber());
		dbManager.close();
		return e;
	}

	@WebMethod
	public Quote createCuote(Event event, Question q, String option, float prize)
			throws CuoteAlreadyCreated, EventFinished, NoValidMoneyException {
		Quote e;
		dbManager.open(false);
		e = dbManager.createCuote(event, q, option, prize);
		dbManager.close();
		return e;
	}

	@WebMethod
	public NormalUser addMoney(double money, NormalUser u) throws NoValidMoneyException, IsEmpty {
		NormalUser b;
		dbManager.open(false);
		b = dbManager.addMoney(money, u);
		dbManager.close();
		return b;
	}

	@WebMethod
	public ArrayList<Transaction> seeMoneyMovements(NormalUser u) {
		dbManager.open(false);
		ArrayList<Transaction> transactions = dbManager.seeMoneyMovements(u);
		dbManager.close();
		return transactions;
	}

	@WebMethod
	public NormalUser createBet(NormalUser u, Quote c, double amount, Date date)
			throws NotEnoughMoneyException, BetAlreadyCreatedException, IsEmpty, DatePassed {
		NormalUser user;
		if (c == null)
			throw new IsEmpty();
		if (!(u.getMoney() >= amount && c.getQuestion().getBetMinimum() < amount))
			throw new NotEnoughMoneyException();
		dbManager.open(false);
		user = dbManager.createBet(u, c, amount, date);
		dbManager.close();
		return user;
	}

	@WebMethod
	public void deleteEvent(Event e, Date d) throws IsEmpty {
		if (e == null || d == null)
			throw new IsEmpty();
		dbManager.open(false);
		dbManager.deleteEvent(e);
		dbManager.close();
	}

	@WebMethod
	public User deleteBet(int betNumber, NormalUser u) throws NonPositiveNum, IsEmpty {
		User user = null;
		if (u == null)
			throw new IsEmpty();
		else if (betNumber <= 0)
			throw new NonPositiveNum();
		else {
			dbManager.open(false);
			user = dbManager.deleteBet(betNumber, u);
			dbManager.close();
		}
		return user;
	}

	@WebMethod
	public Quote PutResults(Quote WinnerCuote) throws CuoteHasNotQuestion, QuestionHasResult {
		Quote cuota;
		if (WinnerCuote.getQuestion() == null)
			throw new CuoteHasNotQuestion();
		else {
			dbManager.open(false);
			cuota = dbManager.PutResults(WinnerCuote);
			dbManager.close();
		}
		if (cuota == null)
			throw new QuestionHasResult();
		return cuota;
	}

	public NormalUser createMultiBet(NormalUser user, ArrayList<Quote> cuotes, double moneyAmount, Date date)
			throws NotEnoughMoneyException, BetAlreadyCreatedException, IsEmpty, DatePassed {
		NormalUser u;
		if (!(user.getMoney() >= moneyAmount))
			throw new NotEnoughMoneyException();
		for (Quote c : cuotes) {
			if (c.getQuestion().getBetMinimum() > moneyAmount)
				throw new NotEnoughMoneyException();
		}
		dbManager.open(false);
		u = dbManager.createMultiBet(user, cuotes, moneyAmount);// );te);
		dbManager.close();
		return u;
	}

	public void duplicateEvent(Event e, Date date) throws IsEmpty {
		dbManager.open(false);
		dbManager.duplicateEvent(e, date);
		dbManager.close();
	}

	public Message SendMessage(User igorle, Chat txata, String textua, Date data) {
		dbManager.open(false);
		Message mezua = dbManager.SendMessage(igorle, txata, textua, data);
		dbManager.close();
		return mezua;
	}

	public Chat CreateChat(String Izena, ArrayList<User> Erabiltzaileak) {
		dbManager.open(false);
		Chat Txat = dbManager.CreateChat(Izena, Erabiltzaileak);
		dbManager.close();
		return (Txat);

	}

	public ArrayList<User> getUsers() {

		dbManager.open(false);
		ArrayList<User> difUsers = dbManager.getUsers();
		dbManager.close();
		return difUsers;
	}

	public User createGroup(String adminUserName, ArrayList<String> users, String gName)
			throws NotEnoughUsers, GroupAlreadyCreated {
		dbManager.open(false);
		User user = dbManager.createGroup(adminUserName, users, gName);
		dbManager.close();
		return user;
	}

	public Group addUserToGroup(String userName, Group group) throws UserAlreadyInTheGroup {
		dbManager.open(false);
		Group g = dbManager.addUserToGroup(userName, group);
		dbManager.close();
		return g;
	}

	public Group quickFromTheGroup(String userName, Group group) {
		dbManager.open(false);
		Group g = dbManager.quickFromTheGroup(userName, group);
		dbManager.close();
		return g;
	}

	public User deleteGroup(User u, Group group) {
		dbManager.open(false);
		User user = dbManager.deleteGroup(u, group);
		dbManager.close();
		return user;
	}

	public Group leaveGroup(User user, Group group) {
		dbManager.open(false);
		Group g = dbManager.leaveGroup(user, group);
		dbManager.close();
		return g;
	}

	public NormalUser follow(String userToFollow, NormalUser userFollowing, double money, double percentageToBet)
			throws NotEnoughMoneyException, invalidPercentage, NonPositiveNum {

		if (percentageToBet >= 1 || percentageToBet <= 0)
			throw new invalidPercentage();
		if (money <= 0)
			throw new NonPositiveNum();
		dbManager.open(false);
		NormalUser u = dbManager.follow(userToFollow, userFollowing, money, percentageToBet);
		dbManager.close();
		return u;
	}

	public NormalUser unfollow(String unfollowedUser, NormalUser mainUser) {
		dbManager.open(false);
		NormalUser u = dbManager.unfollow(unfollowedUser, mainUser);
		dbManager.close();
		return u;

	}

	public List<NormalUser> getNormalUsersRankings(NormalUser user) {
		dbManager.open(false);
		List<NormalUser> ranking = dbManager.getNormalUsersRankings(user);
		dbManager.close();
		return ranking;
	}

	public List<NormalUser> userFollows(NormalUser user) {
		dbManager.open(false);
		List<NormalUser> follows = dbManager.userFollows(user);
		dbManager.close();
		return follows;
	}
}
