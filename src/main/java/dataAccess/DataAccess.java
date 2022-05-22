package dataAccess;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
//hello
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Admin;
import domain.Bet;
import domain.Chat;
import domain.Quote;
import domain.Event;
import domain.Follow;
import domain.Group;
import domain.Message;
import domain.NormalUser;
import domain.Question;
import domain.Transaction;
import domain.User;
import exceptions.AgeException;
import exceptions.BetAlreadyCreatedException;
import exceptions.CuoteAlreadyCreated;
import exceptions.DatePassed;
import exceptions.EmailUsed;
import exceptions.EventAlreadyCreated;
import exceptions.EventFinished;
import exceptions.GroupAlreadyCreated;
import exceptions.IsEmpty;
import exceptions.NoValidMoneyException;
import exceptions.NotEnoughMoneyException;
import exceptions.NotEnoughUsers;
import exceptions.PhoneNumberUsed;
import exceptions.QuestionAlreadyExist;
import exceptions.UserAlreadyInTheGroup;
import exceptions.UserNameAlreadyUsed;
import exceptions.UserNotExist;
import exceptions.WrongPassword;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public DataAccess(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccess() {
		this(false);
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();
		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}

			Event ev1 = new Event(1, "Atlético-Athletic", UtilDate.newDate(year, month, 17));
			Event ev2 = new Event(2, "Eibar-Barcelona", UtilDate.newDate(year, month, 17));
			Event ev3 = new Event(3, "Getafe-Celta", UtilDate.newDate(year, month, 17));
			Event ev4 = new Event(4, "Alavés-Deportivo", UtilDate.newDate(year, month, 17));
			Event ev5 = new Event(5, "Español-Villareal", UtilDate.newDate(year, month, 17));
			Event ev6 = new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
			Event ev7 = new Event(7, "Malaga-Valencia", UtilDate.newDate(year, month, 17));
			Event ev8 = new Event(8, "Girona-Leganés", UtilDate.newDate(year, month, 17));
			Event ev9 = new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
			Event ev10 = new Event(10, "Betis-Real Madrid", UtilDate.newDate(year, month, 17));

			Event ev11 = new Event(11, "Atletico-Athletic", UtilDate.newDate(year, month, 1));
			Event ev12 = new Event(12, "Eibar-Barcelona", UtilDate.newDate(year, month, 1));
			Event ev13 = new Event(13, "Getafe-Celta", UtilDate.newDate(year, month, 1));
			Event ev14 = new Event(14, "Alavés-Deportivo", UtilDate.newDate(year, month, 1));
			Event ev15 = new Event(15, "Español-Villareal", UtilDate.newDate(year, month, 1));
			Event ev16 = new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));

			Event ev17 = new Event(17, "Málaga-Valencia", UtilDate.newDate(year, month + 1, 28));
			Event ev18 = new Event(18, "Girona-Leganés", UtilDate.newDate(year, month + 1, 28));
			Event ev19 = new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year, month + 1, 28));
			Event ev20 = new Event(20, "Betis-Real Madrid", UtilDate.newDate(year, month + 1, 28));

			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
				q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
				q3 = ev11.addQuestion("¿Quién ganará el partido?", 1);
				q4 = ev11.addQuestion("¿Cuántos goles se marcarán?", 2);
				q5 = ev17.addQuestion("¿Quién ganará el partido?", 1);
				q6 = ev17.addQuestion("¿Habrá goles en la primera parte?", 2);
			} else if (Locale.getDefault().equals(new Locale("en"))) {
				q1 = ev1.addQuestion("Who will win the match?", 1);
				q2 = ev1.addQuestion("Who will score first?", 2);
				q3 = ev11.addQuestion("Who will win the match?", 1);
				q4 = ev11.addQuestion("How many goals will be scored in the match?", 2);
				q5 = ev17.addQuestion("Who will win the match?", 1);
				q6 = ev17.addQuestion("Will there be goals in the first half?", 2);
			} else {
				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
				q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2);

			}

			Quote quote = new Quote("aa", 2, q3);
			quote.setQuestion(q3);
			ArrayList<Quote> quotes;
			quotes = new ArrayList<Quote>();
			quotes.add(quote);
			q3.setCuotes(quotes);
			db.persist(quote);
			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);

			NormalUser u = new NormalUser("user", "1234", "hasizaldua@gmail.com", 999999999, 19, "Hasier", "Zaldua");
			NormalUser u2 = new NormalUser("Alex", "1234", "hasizaldua@gmail.com", 999999999, 19, "Hasier", "Zaldua");
			NormalUser u3 = new NormalUser("Hasier", "1234", "hasizaldua@gmail.com", 999999999, 19, "Hasier", "Zaldua");
			NormalUser u4 = new NormalUser("Txomin", "1234", "hasizaldua@gmail.com", 999999999, 19, "Hasier", "Zaldua");
			NormalUser u5 = new NormalUser("Paul", "1234", "hasizaldua@gmail.com", 999999999, 19, "Hasier", "Zaldua");
			NormalUser u6 = new NormalUser("user2", "1234", "hasizaldua@gmail.com", 999999999, 19, "Hasier", "Zaldua");
			NormalUser u7 = new NormalUser("user3", "1234", "hasizaldua@gmail.com", 999999999, 19, "Hasier", "Zaldua");
			NormalUser u8 = new NormalUser("user4", "1234", "hasizaldua@gmail.com", 999999999, 19, "Hasier", "Zaldua");
			NormalUser u9 = new NormalUser("user5", "1234", "hasizaldua@gmail.com", 999999999, 19, "Hasier", "Zaldua");
			NormalUser u10 = new NormalUser("user6", "1234", "hasizaldua@gmail.com", 999999999, 19, "Hasier", "Zaldua");
			NormalUser u11 = new NormalUser("user7", "1234", "hasizaldua@gmail.com", 999999999, 19, "Hasier", "Zaldua");
			NormalUser u12 = new NormalUser("user8", "1234", "hasizaldua@gmail.com", 999999999, 19, "Hasier", "Zaldua");
			NormalUser u13 = new NormalUser("user9", "1234", "hasizaldua@gmail.com", 999999999, 19, "Hasier", "Zaldua");
			User a = new Admin("admin", "admin", "soyElJefe@gmail.com", 000000000, 102, "Paco", "Jones");

			db.persist(u);
			db.persist(a);
			db.persist(u2);
			db.persist(u3);
			db.persist(u4);
			db.persist(u5);
			db.persist(u6);
			db.persist(u7);
			db.persist(u8);
			db.persist(u9);
			db.persist(u10);
			db.persist(u11);
			db.persist(u12);
			db.persist(u13);

			Event ev21 = new Event(21, "Betis-Real Madrid", UtilDate.newDate(year, 2, 28));
			Question q7 = ev21.addQuestion("Â¿HabrÃ¡ goles en la primera parte?", 2);

			Event ev22 = new Event(22, "Betis-Real Madrid", UtilDate.newDate(year, 2, 26));
			Question q8 = ev22.addQuestion("Â¿HabrÃ¡ goles en la primera parte?", 2);
			Quote c8 = new Quote("1", (float) 1.2, q7);

			Quote c9 = new Quote("x", (float) 1.5, q8);

			Quote c10 = new Quote("2", (float) 1.7, q8);

			Event ev23 = new Event(23, "Betis-Real Madrid", UtilDate.newDate(year, 2, 18));
			Question q9 = ev23.addQuestion("Â¿HabrÃ¡ goles en la primera parte?", 2);
			Quote c11 = new Quote("1", (float) 1.2, q9);
			Quote c12 = new Quote("x", (float) 1.5, q9);
			Quote c13 = new Quote("2", (float) 1.7, q9);

			ArrayList<Quote> cl11 = new ArrayList<Quote>();
			cl11.add(c11);
			ArrayList<Quote> cl12 = new ArrayList<Quote>();
			cl12.add(c12);
			ArrayList<Quote> cl9 = new ArrayList<Quote>();
			cl9.add(c9);
			Bet b1 = new Bet(10.0, u, cl11);
			Bet b2 = new Bet(1, u, cl11);
			Bet b3 = new Bet(2, u, cl12);

			Bet b4 = new Bet(5.0, u, cl12);
			Bet b5 = new Bet(4, u, cl12);
			Bet b6 = new Bet(2, u, cl9);

			c11.addBet(b1);
			c11.addBet(b2);
			c12.addBet(b3);
			c12.addBet(b4);
			c12.addBet(b5);
			c9.addBet(b6);

			q7.addCuote(c8);
			q8.addCuote(c9);
			q8.addCuote(c10);
			q9.addCuote(c11);
			q9.addCuote(c12);
			q9.addCuote(c13);

			q7.setEvent(ev21);
			q8.setEvent(ev22);
			q9.setEvent(ev23);

			u.addBet(b1);
			u.addBet(b2);
			u.addBet(b3);
			u.addBet(b4);
			u.addBet(b5);
			u.addBet(b5);

			c8.setQuestion(q7);
			c9.setQuestion(q8);
			c10.setQuestion(q8);
			c11.setQuestion(q9);
			c12.setQuestion(q9);
			c13.setQuestion(q9);

			Quote c14 = new Quote("1", (float) 1.2, q7);
			Quote c15 = new Quote("x", (float) 1.5, q8);
			ArrayList<Quote> cs1 = new ArrayList<Quote>();
			cs1.add(c14);
			cs1.add(c15);
			Bet mb1 = new Bet(10.0, u, cs1);
			c14.addBet(mb1);
			c15.addBet(mb1);
			u.addBet(mb1);
			q7.addCuote(c14);
			q8.addCuote(c15);

			// MultiBet mb2 = new MultiBet(1, u, c11);
			// MultiBet mb3 = new MultiBet(2, u, c12);

			// MultiBet mb4 = new MultiBet(5.0, u, c12);
			// MultiBet mb5 = new MultiBet(4, u, c12);
			// MultiBet mb6 = new MultiBet(2, u, c9);

			db.persist(ev21);
			db.persist(ev22);
			db.persist(ev23);

			db.persist(c8);
			db.persist(c9);
			db.persist(c10);
			db.persist(c11);
			db.persist(c12);
			db.persist(c13);
			db.persist(c14);
			db.persist(c15);

			db.persist(mb1);

			db.persist(b1);
			db.persist(b2);
			db.persist(b3);
			db.persist(b4);
			db.persist(b5);
			db.persist(b6);

			db.persist(q7);
			db.persist(q8);
			db.persist(q9);

			Event ev30 = new Event(30, "City-Madrid", UtilDate.newDate(year, 2, 25));
			Event ev31 = new Event(31, "Villareal-liverpool", UtilDate.newDate(year, 2, 26));
			Question q10 = ev30.addQuestion("Bat", 15);
			Question q11 = ev31.addQuestion("Bi", 10);
			q10.setEvent(ev30);
			q11.setEvent(ev31);

			db.persist(ev30);
			db.persist(ev31);
			db.persist(q10);
			db.persist(q11);
			Quote c16 = new Quote("aa", (float) 2, q10);
			Quote c17 = new Quote("bb", (float) 4, q11);
			Quote c18 = new Quote("cc", (float) 3, q1);
			q10.addCuote(c16);
			q11.addCuote(c17);
			q1.addCuote(c18);
			ArrayList<Quote> cuotes = new ArrayList<Quote>();
			cuotes.add(c16);
			cuotes.add(c17);
			// cuotes.add(c18);
			Bet bet = new Bet(50.0, u, cuotes);
			db.persist(bet);
			db.persist(c16);
			db.persist(c17);
			db.persist(c18);
			u.addBet(bet);
			c16.addBet(bet);
			c17.addBet(bet);
			c18.addBet(bet);

			Question q20 = ev1.addQuestion("?", 5);
			Quote c21 = new Quote("cc", (float) 3, q20);
			q20.addCuote(c21);
			db.persist(q20);
			u.setMoney(1000);

			Quote c819 = new Quote("1", (float) 1.2, q7);
			q2.addCuote(c819);

			ArrayList<User> users = new ArrayList<User>();
			users.add(a);
			Group g = new Group(u.getUserName(), users, "patata");
			db.persist(g);

			db.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);

		// db.persist(q);
		// db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is
		// added in questions property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	public Vector<Event> getQuestions(Date date) {
		System.out.println(">> DataAccess: getQuestions");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}

	public void open(boolean initializeMode) {

		System.out.println("Opening DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}

	}

	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= " + event + " question= " + question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);

	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	/**
	 * 
	 * @param user
	 * @param pass
	 * @return
	 */
	public User login(String user, String pass) throws IsEmpty, WrongPassword, UserNameAlreadyUsed, UserNotExist {
		System.out.println(">> DataAccess: existUser=> userName= " + user + "");
		if (user.isEmpty() || pass.isEmpty())
			throw new IsEmpty(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
		User u = db.find(User.class, user);
		if (u == null) {
			throw new UserNotExist(ResourceBundle.getBundle("Etiquetas").getString("UserNameNotExist"));
		} else {
			if (!u.correctPassword(pass)) {
				System.out.println("Gakoa okerra da");
				throw new WrongPassword();
			} else {
				return u;
			}
		}
	}

	public NormalUser register(String userName, String pass, String email, int pNumber, int age, String rName,
			String rSurname)
			throws IsEmpty, WrongPassword, UserNameAlreadyUsed, AgeException, PhoneNumberUsed, EmailUsed {
		if (userName.isEmpty() || pass.isEmpty() || email.isEmpty() || rName.isEmpty() || rSurname.isEmpty()
				|| pNumber == 0 || age == 0)
			throw new IsEmpty();

		if (db.find(User.class, userName) != null) { // username hori erabilita dago
			throw new UserNameAlreadyUsed();
		}
		if (!validPassword(pass)) {
			System.out.println("Pasahitzak gutxienez 4 karakterekoa izan behar du");
			throw new WrongPassword();
		}
		TypedQuery<User> query = db.createQuery("SELECT p FROM User p", User.class);
		List<User> users = query.getResultList();
		for (User u : users) {
			if (!u.availableEmail(email)) {
				System.out.println("Email hori jadanik erabilita dago");
				throw new EmailUsed();
			}
			if (!u.availablePhoneNumber(pNumber)) {
				System.out.println("Telefono zenbaki hori dagoeneko erabilita dago");
				throw new PhoneNumberUsed();
			}
		}
		if (age < 18) {
			System.out.println("Adina ez da baliozkoa");
			throw new AgeException();
		}
		db.getTransaction().begin();
		NormalUser u = new NormalUser(userName, pass, email, pNumber, age, rName, rSurname);
		db.persist(u);
		db.getTransaction().commit();
		System.out.println("kontua gorde da");

		return u;
	}

	public Event createEvent(String d, Date date) throws DatePassed, EventAlreadyCreated {
		if (datePassed(date)) {
			throw new DatePassed();
		}
		TypedQuery<Event> query = db.createQuery("SELECT p FROM Event p", Event.class);
		List<Event> events = query.getResultList();
		for (Event e : events) {
			if (e.alreadyCreated(d, date)) {
				throw new EventAlreadyCreated();
			}
		}
		db.getTransaction().begin();
		Event ev = new Event(d, date);
		db.persist(ev);
		db.getTransaction().commit();
		return ev;
	}

	public Quote createCuote(Event e, Question q, String option, float prize)
			throws CuoteAlreadyCreated, EventFinished, NoValidMoneyException {
		if (new Date().compareTo(e.getEventDate()) > 0)
			throw new EventFinished();
		if (prize <= 1)
			throw new NoValidMoneyException();
		Question question = db.find(Question.class, q.getQuestionNumber());
		boolean thereIs = question.cuoteAlreadyExists(option);

		if (!thereIs) {
			db.getTransaction().begin();
			Quote cuote = question.createCuote(option, prize, question);
			db.getTransaction().commit();
			return cuote;
		} else {
			throw new CuoteAlreadyCreated();
		}
	}

	public NormalUser addMoney(double money, NormalUser u) throws IsEmpty, NoValidMoneyException {
		if (u == null)
			throw new IsEmpty();
		if (money <= 0)
			throw new NoValidMoneyException();

		db.getTransaction().begin();
		NormalUser user = db.find(NormalUser.class, u.getUserName());
		user.addMoney(money);
		Transaction transaction = user.createMoneyTransaction(1, money);
		db.getTransaction().commit();
		// u.addMoney(money);
		// u.addMoneyTransactions(transaction);
		return user;
	}

	public ArrayList<Transaction> seeMoneyMovements(NormalUser u) {
		NormalUser user = db.find(NormalUser.class, u.getUserName());
		ArrayList<Transaction> transactions = user.getMoneyTransactions();
		return transactions;
	}

	public NormalUser createBet(NormalUser u, Quote c, double amount, Date date)
			throws BetAlreadyCreatedException, DatePassed {
		db.getTransaction().begin();
		NormalUser user = db.find(NormalUser.class, u.getUserName());
		if (datePassed(date))
			throw new DatePassed();
		// if(u.betNotCreated(c)) {
		user.restMoney(2, amount);
		Quote cuote = db.find(Quote.class, c.getCuoteNumber());
		ArrayList<Quote> cl = new ArrayList<Quote>();
		cl.add(cuote);
		Bet b = user.createBet(amount, cl);
		cuote.addBet(b);
		// u.addBet(b);
		// u.setMoney(user.getMoney());
		db.getTransaction().commit();

		ArrayList<Follow> followers = user.getFollowers();
		if (followers != null) {
			for (Follow f : followers) {
				NormalUser use = f.getFollower();
				double moneybet = f.moneyBet(amount);
				if (f.getMoney() >= moneybet && moneybet > c.getQuestion().getBetMinimum() && use.betNotCreated(c)) {
					use.addMoney(moneybet);
					f.setMoney(f.getMoney() - moneybet);
					createBet(use, c, moneybet, date);

				}
			}
		}
		return user;
	}

	public void deleteEvent(Event e) {
		if (e.hasQuestions()) {
			for (Question q : e.getQuestions()) {
				if (q.hasCuotes()) {
					for (Quote c : q.getCuotes()) {
						if (c.hasBets()) {
							for (Bet b : c.getBetList()) {
								if (!b.getMultiBet())
									deleteBet(b.getBetNumber(), (NormalUser) b.getUser());
								else {
									deleteMultiBetCuote(b.getBetNumber(), c.getCuoteNumber(), (NormalUser) b.getUser());
									isBetWinned(b, (NormalUser) b.getUser());
								}
							}
						}
					}
				}
			}
		}
		db.getTransaction().begin();
		Event event = db.find(Event.class, e.getEventNumber());
		db.remove(event);
		db.getTransaction().commit();
	}

	public User deleteBet(int betNumber, NormalUser u) {
		db.getTransaction().begin();
		NormalUser user = (NormalUser) db.find(NormalUser.class, u.getUserName());
		Bet bet = db.find(Bet.class, betNumber);
		if (bet != null) {
			for (Quote cuote : bet.getCuote()) {
				cuote.deleteBet(betNumber);
			}
			user.deleteBet(betNumber);
			// u.deleteBet(betNumber);

			user.addMoney(bet.getBetedMoney());
			// u.addMoney(bet.getBetedMoney());

			Transaction transaction = user.createMoneyTransaction(4, bet.getBetedMoney());
			// u.addMoneyTransactions(transaction);
			db.remove(bet);
			db.getTransaction().commit();
		}
		return user;
	}

	public void deleteMultiBetCuote(int betNumber, int cuoteNumber, NormalUser user) {
		db.getTransaction().begin();
		Bet bet = db.find(Bet.class, betNumber);
		Quote c = db.find(Quote.class, cuoteNumber);
		NormalUser u = db.find(NormalUser.class, user.getUserName());

		bet.getCuote().remove(c);
		if (bet.getCuote().size() == 1)
			bet.setMultiBet(false);
		db.getTransaction().commit();
	}

	// Apostu anitzetik kuota bat ezabatzean irabazlea den ala ez konprobatu
	public void isBetWinned(Bet b, NormalUser u) {
		Bet bet = db.find(Bet.class, b.getBetNumber());
		NormalUser user = db.find(NormalUser.class, u.getUserName());
		boolean winner = true;
		for (Quote c : bet.getCuote()) {
			if (c.getQuestion().getResult() == null
					|| !(c.getQuestion().getResult().getCuoteNumber() == c.getCuoteNumber())) {
				winner = false;
			}
		}
		if (winner) {
			user.addMoney(bet.WonMoney());
			Transaction transaction = new Transaction(3, bet.WonMoney(), user);
			user.addMoneyTransactions(transaction);
			user.updateRanking(true);
		}
	}

	public Quote PutResults(Quote winnerCuote) {
		db.getTransaction().begin();
		Question quest = db.find(Question.class, winnerCuote.getQuestion().getQuestionNumber());
		if (quest.getResult() != null) {
			return null;
		} else {
			Quote cuote = db.find(Quote.class, winnerCuote.getCuoteNumber());
			// TypedQuery<NormalUser> Us = db.createQuery("SELECT p FROM User
			// p",NormalUser.class);
			quest.setResult(winnerCuote);
			for (Quote cuote1 : cuote.getQuestion().getCuotes()) {
				boolean eginda = false;
				for (Bet b : cuote1.getBetList()) {
					if (b.getMultiBet() == false) {
						if (!cuote1.equals(cuote)) {
							db.find(Bet.class, b.getBetNumber()).setWonBet(false);
							NormalUser user = (NormalUser) db.find(Bet.class, b.getBetNumber()).getUser();
							user.deleteBet(b.getBetNumber());
							user.updateRanking(false);
						}

						else {
							Bet bet = (Bet) b;
							NormalUser u = db.find(NormalUser.class, b.getUser().getUserName());
							u.addMoney(bet.WonMoney());
							Transaction transaction = new Transaction(3, bet.WonMoney(), u);
							u.addMoneyTransactions(transaction);
							db.find(Bet.class, b.getBetNumber()).setWonBet(true);
							u.deleteBet(bet.getBetNumber());
							u.updateRanking(true);
						}
					} else {
						if (b.getWonBet() == null || b.getWonBet() != false) {
							if (!cuote1.equals(cuote)) {
								db.find(Bet.class, b.getBetNumber()).setWonBet(false);
								NormalUser user = (NormalUser) db.find(Bet.class, b.getBetNumber()).getUser();
								db.find(Bet.class, b.getBetNumber()).setWonBet(false);
								user.updateRanking(false);
							} else {
								Bet multiBet = (Bet) b;
								NormalUser u = db.find(NormalUser.class, b.getUser().getUserName());
								db.find(Bet.class, b.getBetNumber()).setWonBet(true);
								boolean winner = true;
								for (Quote c : multiBet.getCuote()) {
									if (c.getQuestion().getResult() == null
											|| c.getQuestion().getResult().getCuoteNumber() != c.getCuoteNumber())
										winner = false;
									if (winner == false)
										break;
								}
								if (winner == true) {
									u.addMoney(multiBet.WonMoney());
									Transaction transaction = new Transaction(3, multiBet.WonMoney(), u);
									u.addMoneyTransactions(transaction);
									u.deleteBet(b.getBetNumber());
									u.updateRanking(true);
								}
							}
						}
					}
				}
			}
		}
		db.getTransaction().commit();
		return winnerCuote;
	}

	public NormalUser createMultiBet2(NormalUser u, ArrayList<Quote> cuotes, double moneyAmount, Date date)
			throws BetAlreadyCreatedException, DatePassed {
		NormalUser user = db.find(NormalUser.class, u.getUserName());
		if (datePassed(date))
			throw new DatePassed();
		// boolean sartu = true;
		// for(Cuote c : cuotes) {
		// if(u.betNotCreated(c)==false) sartu=false;
		// Hau ondo sekuentzi diagramatan????
		// }
		// if(sartu) {
		db.getTransaction().begin();
		user.restMoney(1, moneyAmount);
		ArrayList<Quote> cs = new ArrayList<Quote>();
		for (Quote cu : cuotes) {
			Quote cuote = db.find(Quote.class, cu.getCuoteNumber());
			cs.add(cuote);
		}

		Bet b = new Bet(moneyAmount, user, cs);
		for (Quote cuote : cs)
			cuote.addBet(b);
		user.addBet(b);
		// u.addBet(b);
		db.getTransaction().commit();

		ArrayList<Follow> followers = user.getFollowers();
		if (followers != null) {
			for (Follow f : followers) {
				boolean bool = true;
				NormalUser use = f.getFollower();
				double moneybet = f.moneyBet(moneyAmount);
				for (Quote c : cuotes) {
					if (c.getQuestion().getBetMinimum() > moneybet || !use.betNotCreated(c))
						bool = false;
				}
				if (bool && f.getMoney() > moneybet) {
					use.addMoney(moneybet);
					f.setMoney(f.getMoney() - moneybet);
					createMultiBet(use, cs, moneybet);
				}
			}
		}

		return user;
	}
	// else {
	// throw new BetAlreadyCreatedException();
	// }

	public NormalUser createMultiBet(NormalUser u, ArrayList<Quote> cuotes, double moneyAmount)
			throws BetAlreadyCreatedException, DatePassed {
		if (datePassed(new Date()))
			throw new DatePassed();
		NormalUser user = db.find(NormalUser.class, u.getUserName());
		user.restMoney(2, moneyAmount);
		db.getTransaction().begin();

		ArrayList<Quote> cs = new ArrayList<Quote>();
		for (Quote cu : cuotes) {
			Quote cuote = db.find(Quote.class, cu.getCuoteNumber());
			cs.add(cuote);
		}
		Bet b = user.createBet(moneyAmount, cs);
		for (Quote cuote : cs)
			cuote.addBet(b);
		db.getTransaction().commit();

		ArrayList<Follow> followers = user.getFollowers();
		if (followers != null) {
			for (Follow f : followers) {
				boolean bool = true;
				NormalUser use = f.getFollower();
				double moneybet = f.moneyBet(moneyAmount);
				for (Quote c : cuotes) {
					if (c.getQuestion().getBetMinimum() > moneybet || !use.betNotCreated(c))
						bool = false;
				}
				if (bool && f.getMoney() >= moneybet) {
					use.addMoney(moneybet);
					f.setMoney(f.getMoney() - moneybet);
					createMultiBet(use, cs, moneybet);
				}
			}
		}

		return user;
	}

	public boolean validPassword(String pass) {
		if (pass.length() <= 3 || pass.equals(""))
			return false;
		return true;
	}

	public boolean datePassed(Date date) {
		if (new Date().compareTo(date) > 0)
			return true;
		return false;
	}

	public List<NormalUser> getNormalUsers() {
		TypedQuery<NormalUser> Us = db.createQuery("SELECT p FROM User p", NormalUser.class);
		List<NormalUser> normalusers = Us.getResultList();
		return normalusers;
	}

	public NormalUser returnMoneyToUser(Bet b, double amount) {
		db.getTransaction().begin();
		NormalUser user = db.find(NormalUser.class, b.getUser().getUserName());
		user.setMoney(user.getMoney() + amount);
		user.addMoneyTransactions(new Transaction(4, amount, user));
		db.getTransaction().commit();
		return user;
	}

	public void duplicateEvent(Event e, Date date) throws IsEmpty {
		if (e == null)
			throw new IsEmpty();
		Event duplicatedEvent = null;
		try {
			duplicatedEvent = createEvent(e.getDescription(), date);
		} catch (DatePassed e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (EventAlreadyCreated e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Event duplicatedEvent = new Event(e.getDescription(), date);
		db.getTransaction().begin();
		for (Question q : e.getQuestions()) {
			Question question = duplicatedEvent.addQuestion(q.getQuestion(), q.getBetMinimum());
			for (Quote c : q.getCuotes()) {
				question.createCuote(c.getOption(), c.getPrize(), question);

			}
		}
		// db.persist(duplicatedEvent);
		db.getTransaction().commit();
	}

	public Message SendMessage(User igorle, Chat txata, String textua, Date data) {
		db.getTransaction().begin();
		Chat txat = db.find(Chat.class, txata.getChatIzena());
		User u = db.find(User.class, igorle);
		Message Mezua = new Message(u, data, textua, txat);
		txat.AddMessage(Mezua);
		db.persist(Mezua);
		db.getTransaction().commit();
		return Mezua;
	}

	public Chat CreateChat(String Izena, ArrayList<User> Erabiltzaileak) {
		db.getTransaction().begin();
		Chat txata = null;
		if (db.find(Chat.class, Izena) == null) {
			ArrayList<User> dbErabil = new ArrayList<User>();
			for (User erabil : Erabiltzaileak) {
				User partaide = db.find(User.class, erabil.getUserName());
				dbErabil.add(partaide);
			}
			txata = new Chat(Izena, dbErabil);
			for (User u : dbErabil)
				u.addChat(txata);
			db.persist(txata);
		}
		db.getTransaction().commit();
		return txata;
	}

	public ArrayList<User> getUsers() {
		db.getTransaction().begin();
		ArrayList<User> res = new ArrayList<User>();
		TypedQuery<User> query = db.createQuery("SELECT p FROM User p", User.class);
		res.addAll(query.getResultList());
		db.getTransaction().commit();
		return res;
	}

	public User createGroup(String adminUserName, ArrayList<String> users, String gName)
			throws NotEnoughUsers, GroupAlreadyCreated {
		db.getTransaction().begin();
		if (users.size() < 3) {
			throw new NotEnoughUsers();
		}
		if (db.find(Group.class, gName) == null) {
			User admin = db.find(User.class, adminUserName);
			ArrayList<User> usersToAdd = new ArrayList<User>();
			for (String u : users) {
				User us = db.find(User.class, u);
				usersToAdd.add(us);
			}
			admin.createGroup(usersToAdd, gName);
			db.getTransaction().commit();
			return admin;
		} else
			throw new GroupAlreadyCreated();
	}

	public Group addUserToGroup(String userName, Group group) throws UserAlreadyInTheGroup {
		db.getTransaction().begin();
		User u = db.find(User.class, userName);
		boolean t = group.userAlreadyIn(u);
		if (t)
			throw new UserAlreadyInTheGroup();
		Group g = db.find(Group.class, group.getChatIzena());
		u.addChat(g);
		g.addUser(u);
		db.getTransaction().commit();
		return g;
	}

	public Group quickFromTheGroup(String userName, Group group) {
		db.getTransaction().begin();
		User u = db.find(User.class, userName);
		Group g = db.find(Group.class, group.getChatIzena());
		u.removeGroup(g);
		g.removeUser(u);
		db.getTransaction().commit();
		return g;
	}

	public User deleteGroup(User u, Group group) {
		db.getTransaction().begin();
		User user = db.find(User.class, u.getUserName());
		Group g = db.find(Group.class, group.getChatIzena());
		ArrayList<User> groupMembers = g.getPartaideak();
		for (User us : groupMembers) {
			us.removeGroup(g);
		}
		user.removeGroup(g);
		db.remove(g);
		db.getTransaction().commit();
		return user;
	}

	public Group leaveGroup(User user, Group group) {
		db.getTransaction().begin();
		System.out.println("hola");
		User u = db.find(User.class, user.getUserName());
		Group g = db.find(Group.class, group.getChatIzena());
		boolean b = g.isAdmin(u);
		u.removeGroup(g);
		g.removeUser(u);
		if (b) {
			g.setNewAdmin();
		}
		db.getTransaction().commit();
		return g;
	}

	public NormalUser follow(String userToFollow, NormalUser userFollowing, double money, double percentageToBet)
			throws NotEnoughMoneyException {

		db.getTransaction().begin();
		NormalUser userFollowed = db.find(NormalUser.class, userToFollow);
		NormalUser mainUser = db.find(NormalUser.class, userFollowing.getUserName());
		if (!(mainUser.getMoney() >= money)) {
			throw new NotEnoughMoneyException();
		} else {
			Follow jarraitu = new Follow(userFollowed, mainUser, money, percentageToBet);
			userFollowed.addFollower(jarraitu);
			mainUser.addFollows(jarraitu);
			mainUser.restMoney(5, money);
			userFollowing.setMoney(mainUser.getMoney());
			db.getTransaction().commit();
			return mainUser;
		}
	}

	public NormalUser unfollow(String unfollowedUser, NormalUser mainUser) {
		db.getTransaction().begin();
		NormalUser unfollowed = db.find(NormalUser.class, unfollowedUser);
		NormalUser follower = db.find(NormalUser.class, mainUser.getUserName());
		follower.unfollow(unfollowed);
		double money = unfollowed.beUnfollowed(follower);
		follower.restMoney(6, -money);
		mainUser.setMoney(follower.getMoney());
		db.getTransaction().commit();
		return follower;

	}

	public List<NormalUser> getNormalUsersRankings(NormalUser user) {
		db.getTransaction().begin();
		NormalUser u = db.find(NormalUser.class, user);
		TypedQuery<NormalUser> Us = db.createQuery("SELECT p FROM NormalUser p ORDER BY p.points DESC",
				NormalUser.class);
		List<NormalUser> normalusers = Us.getResultList();
		List<NormalUser> rankings = new ArrayList<NormalUser>();
		for (NormalUser n : normalusers) {
			if (!u.jarraitzenDu(n)) {
				rankings.add(n);
			}
		}
		db.getTransaction().commit();
		return rankings;
	}

	public List<NormalUser> userFollows(NormalUser user) {
		db.getTransaction().begin();
		NormalUser u = db.find(NormalUser.class, user);
		List<Follow> followslist = u.getFollows();
		List<NormalUser> follows = new ArrayList<NormalUser>();
		for (Follow f : followslist) {
			follows.add(db.find(NormalUser.class, f.getFollowed().getUserName()));
		}
		db.getTransaction().commit();
		return follows;
	}
}