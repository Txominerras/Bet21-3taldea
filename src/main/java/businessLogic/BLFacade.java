package businessLogic;

import java.util.Vector;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import domain.Booking;
import domain.Question;
import domain.Transaction;
import domain.User;
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

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade {

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
	Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;

	/**
	 * This method retrieves the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public Vector<Event> getEvents(Date date);

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public Vector<Date> getEventsMonth(Date date);

	/**
	 * This method calls the data access to initialize the database with some events
	 * and questions. It is invoked only when the option "initialize" is declared in
	 * the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD();

	/**
	 * Metodo hau erabiltzaileak saioa hasteko erabiltzen da
	 * 
	 * @param user erabiltzailearen izena
	 * @param pass erabiltzailearen pasahitza
	 * 
	 * @throws IsEmpty             aldagietako bat null bada
	 * @throws UserNameAlreadyUsed erabiltzailearen izena dagoeneko erabilita badago
	 * @throws WrongPassword       pasahitza okerra bada
	 * @throws UserNotExist        erabiltzaile hori ez bada existitzen
	 */

	public User login(String u, String p) throws IsEmpty, WrongPassword, UserNameAlreadyUsed, UserNotExist;

	/**
	 * Metodo hau erabiltzaile bat erregistratzeko erabiltzen da
	 * 
	 * @param userName erabiltzaileak erabiliko duen izena
	 * @param pass     erabiltzaileak erabiliko duen pasahitza
	 * @param email    erabiltzaileak erabiliko duen email helbidea
	 * @param pNumber  erabiltzailearen telefono zenbakia
	 * @param age      erabiltzailearen adina
	 * @param rName    erabiltzailearen izena
	 * @param rSurname erabiltzailearen abizena
	 * 
	 * @throws IsEmpty             aldagaietako baten balioa null bada
	 * @throws WrongPassword       pasahitza ez bada zuzena (luzera <3)
	 * @throws UserNameAlreadyUsed izena dagoeneko erabilita badago
	 * @throws AgeException        adina 18 baino txikiagoa bada
	 * @throws PhoneNumberUsed     telefono zenbakia dagoeneko erabilita badago
	 * @throws EmailUsed           email helbidea dagoeneko erabilita badago
	 * 
	 */

	public NormalUser register(String userName, String pass, String email, int pNumber, int age, String rName,
			String rSurname)
			throws IsEmpty, WrongPassword, UserNameAlreadyUsed, AgeException, PhoneNumberUsed, EmailUsed;

	/**
	 * Metodo honek gertaera bat sortzen du
	 * 
	 * @param date gertaeraren data
	 * @param d    gertaeraren deskribapena
	 * 
	 * @throws DatePassed          data dagoeneko pasata badago
	 * @throws EventAlreadyCreated gertaera hori sortuta badago
	 */

	public Event createEvent(String d, Date date) throws DatePassed, EventAlreadyCreated;

	/**
	 * Metodo honek kuota bat sortzen du
	 * 
	 * @param event  kuota gehituko zaion gertaera
	 * @param q      kuota gehituko zaion galdera
	 * @param option kuota mota
	 * @param prize  kuotaren saria
	 * 
	 * @throws CuoteAlreadyCreated   kuota dagoeneko sortuta badago
	 * @throws EventFinished         gertaera bukatuta badago
	 * @throws NoValidMoneyException sariaren zenbakia gaizki badago
	 * 
	 */

	public Quote createCuote(Event e, Question q, String option, float prize)
			throws CuoteAlreadyCreated, EventFinished, NoValidMoneyException;

	/**
	 * Metodo honek erabiltzailearen kontuan dirua gehitzen du
	 * 
	 * @param money sartu nahi den diru kantitatea
	 * @param u     dirua gehitu behar zaion erabiltzailea
	 * 
	 * @throws NoValidMoneyException sartutako diru kantitatea okerra bada
	 * @throws IsEmpty               aldagietako bat null bada
	 */

	public NormalUser addMoney(double money, NormalUser u) throws NoValidMoneyException, IsEmpty;

	/**
	 * Metodo honek erabiltzailearen diru mugimenduak erakusten ditu
	 * 
	 * @param u erabiltzailea
	 */

	public ArrayList<Transaction> seeMoneyMovements(NormalUser u);

	/**
	 * Metodo honek apostu bat sortzen du
	 * 
	 * @param u      apostua egin duen erabiltzailea
	 * @param c      apostatu den kuota
	 * @param amount apostatu den diru kantitatea
	 * @param date   apostuaren eguna
	 * 
	 * @throws NotEnoughMoneyException    erabiltaileak nahiko diru ez badu
	 * @throws BetAlreadyCreatedException apostua dagoeneko sortuta badago
	 * @throws IsEmpty                    aldagietako bat null bada
	 * @throws DatePassed                 apostuaren eguna pasata badago
	 */

	public NormalUser createBet(NormalUser u, Quote c, double amount, Date date)
			throws NotEnoughMoneyException, BetAlreadyCreatedException, IsEmpty, DatePassed;

	/**
	 * Metodo honek gertaera bat ezabatzen du
	 * 
	 * @param e ezabatu nahi den gertaera
	 * @param d gertaeraren eguna
	 * 
	 * @throws IsEmpty e edo d null badira
	 */

	public void deleteEvent(Event e, Date d) throws IsEmpty;

	/**
	 * Metodo honek apostu bat borratzen du
	 * 
	 * @param betNumber apostuaren IDa
	 * @param u         apostua egin duen erabiltzailea
	 * 
	 * @throws NonPositiveNum apostu zenbakia negatiboa bada
	 * @throws IsEmpty        erabiltzailea null bada
	 */

	public User deleteBet(int betNumber, NormalUser u) throws NonPositiveNum, IsEmpty;

	/**
	 * Metodo honek galderen emaitzak jartzen ditu
	 * 
	 * @param WinnerCuote galderaren emaitza asmatu duen kuota
	 * 
	 * @throws CuoteHasNotQuestion kuotak ez badu galderarik
	 * @throws QuestionHasResult   galdera horrek dagoeneko emaitza jarrita badu
	 */

	public Quote PutResults(Quote WinnerCuote) throws CuoteHasNotQuestion, QuestionHasResult;

	/**
	 * Metodo honek apostu anitzak sortzen ditu
	 * 
	 * @param user        apostua egin duen erabiltzailea
	 * @param cuotes      apostuan gehitu dituen kuota guztiak
	 * @param moneyAmount apostatu duen diru kantitatea
	 * @param date        apostuaren eguna
	 * 
	 * @throws NotEnoughMoneyException    erabiltzaileak nahiko diru ez badu
	 * @throws BetAlreadyCreatedException apostua dagoeneko eginda badago
	 * @throws IsEmpty                    aldagaietako bat null bada
	 * @throws DatePassed                 apostuaren data pasata badago
	 * 
	 */

	public void duplicateEvent(Event e, Date date) throws IsEmpty;

	/**
	 * Metodo honek gertaerak bikoizten ditu
	 * 
	 * @param e    bikoiztu nahi den gertaera
	 * @param data gbikoiztutako gertaerari jarri nahi zaion data
	 * 
	 * @throws IsEmpty e edo date aldagaiak null badira
	 */

	public NormalUser createMultiBet(NormalUser user, ArrayList<Quote> cuotes, double moneyAmount, Date date)
			throws NotEnoughMoneyException, BetAlreadyCreatedException, IsEmpty, DatePassed;

	/**
	 * Metodo hhau mezuak bidaltzeko erabiltzen da
	 * 
	 * @param igorle mezua bidali duen erabiltzailea
	 * @param txata  bi erabiltzaileen artean sortutako txata
	 * @param textua bidalitako mezua
	 * @param data   mezua bidalitako eguna
	 */

	public Message SendMessage(User igorle, Chat txata, String textua, Date data);

	/**
	 * Metodo honek txat bat sortzen du
	 * 
	 * @param Izena          txataren izena
	 * @param Erabiltzaileak txatean parte hartzen duten erabiltzaileak
	 */

	public Chat CreateChat(String Izena, ArrayList<User> Erabiltzaileak);

	/**
	 * Metodo honek erabiltzaile bat lortzen du datu basetik
	 */

	public ArrayList<User> getUsers();

	/**
	 * Metodo honek txateatzeko taldeak sortzen ditu
	 * 
	 * @param adminUserName taldeko administratzailea, taldea sortu duena
	 * @param users         erabiltzaile normalak
	 * @param gName         taldearen izena
	 * 
	 * @throws GroupAlreadyCreated taldea dagoeneko sortuta badago
	 * @throws NotEnoughUsers      taldea sortzeko nahiko erabiltzaile ez badaude(2
	 *                             edo gutxiago)
	 * 
	 */

	public User createGroup(String adminUserName, ArrayList<String> users, String gName)
			throws NotEnoughUsers, GroupAlreadyCreated;

	/**
	 * Metodo honek erabiltzaile bat gehitzen du taldera
	 * 
	 * @param group    taldea
	 * @param userName erabiltzailearen izena
	 * 
	 * @throws UserAlreadyInTheGroup erabiltzailea dagoeneko taldean badago
	 * 
	 */

	public Group addUserToGroup(String userName, Group group) throws UserAlreadyInTheGroup;

	/**
	 * metodo honek erabiltzaile bat taldetik botatzen du
	 * 
	 * @param userName erabiltzailearen izena
	 * @param group    taldea
	 */

	public Group quickFromTheGroup(String userName, Group group);

	/**
	 * Metodo honek taldea borratzen du
	 * 
	 * @param u     taldea borratuko duen erabiltzailea, administratzailea izan
	 *              behar da
	 * @param group taldea
	 */

	public User deleteGroup(User u, Group group);

	/**
	 * Metodo hau taldetik irteteko da
	 * 
	 * @param user  taldetik aterako den erabiltzailea
	 * @param group taldea
	 */

	public Group leaveGroup(User user, Group group);

	/**
	 * Metodo hau erabiltzaileak jarraitzeko erabiltzen da, hau da jarraitzaileak
	 * jarraitutakoaren apostu berdinak egingo ditu automatikoki eta apostuak
	 * egiteko diru porzentai bat adieraziko du.
	 * 
	 * @param userToFollow    jarraitu nahi den erabiltzailea
	 * @param userFollowing   jarraitzailea
	 * @param money           jarraitutako pertsona horren apostuetan erabili nahi
	 *                        den dirua
	 * @param percentageToBet jarraitutako pertsonaren apostuetako diru kantitateren
	 *                        zein porzentai apostatu nahi den.
	 * 
	 * @throws notFoundException erabiltzaileak nahiko diru ez badu
	 * @throws invalidPercentage sartutako porzentaia gaizki badago(x>=1 edo x=<0)
	 * @throws NonPositiveNum    diru kantitateak negatiboak badira
	 */

	public NormalUser follow(String userToFollow, NormalUser userFollowing, double money, double percentageToBet)
			throws NotEnoughMoneyException, invalidPercentage, NonPositiveNum;

	/**
	 * Metodo hau erabiltzailea jrraitzeaz uzteko erabiltzen da
	 * 
	 * @param unfollowedUser jarraitzeaz utzi nahi den erabiltzailea
	 * @param mainUser       jarraitzailea
	 */

	public NormalUser unfollow(String unfollowedUser, NormalUser mainUser);

	/**
	 * Metodo honek erabiltzaileen rankina erakusten du asmatu dituzten apostuen
	 * arabera
	 * 
	 * @param user erabiltzailea
	 */

	public List<NormalUser> getNormalUsersRankings(NormalUser user);

	/**
	 * Metodo honek erabiltzaile batek jarraitzen dituen erabiltzaile lista
	 * erakusten du
	 * 
	 * @param user erabiltzailea
	 */

	public List<NormalUser> userFollows(NormalUser user);
}
