package domain;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlIDREF;

import com.sun.xml.bind.v2.schemagen.xmlschema.List;
@Entity
public class NormalUser extends User {
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private ArrayList<Transaction> moneyTransactions;
	private double money;
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private ArrayList<Bet> betList;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    private ArrayList<Follow> followings;
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    private ArrayList<Follow> followers;
    private int points;
	
	public NormalUser(String userName, String password, String email, int phoneNumber, int age, String realName,
			String realSurname) {
		super(userName, password, email, phoneNumber, age, realName, realSurname);
		this.money=0;
		this.moneyTransactions= new ArrayList<Transaction>();
		this.betList= new ArrayList<Bet>();
		this.points = 0;
        this.followings = new ArrayList<Follow>();
        this.followers = new ArrayList<Follow>();
		// TODO Auto-generated constructor stub
	}

	
	public void setMoney(double m) {
		money = m;
	}
	public double getMoney() {
		return money;
	}
	public void addMoney(double m) {
		money += m;
	}
	public ArrayList<Transaction> getMoneyTransactions(){
		return this.moneyTransactions;
	}
	public void setMoneyTransactions(ArrayList<Transaction> transactions) {
		this.moneyTransactions = transactions;
	}
	public void addMoneyTransactions(Transaction transactions) {
		this.moneyTransactions.add(transactions);
	}
	public ArrayList<Bet> getBetList() {
		return betList;
	}
	public void setBetList(ArrayList<Bet> betList) {
		this.betList = betList;
	}
	public void addBet(Bet bet) {
		this.betList.add(bet);
	}
	public boolean betNotCreated(Quote c) {
		if(betList.size()>0)
		for(Bet bet : this.betList) {
			if(bet.getMultiBet()==false) {
				if(bet.getBetCuote().getCuoteNumber()==c.getCuoteNumber()){
				//if(bet.getCuote().get(0).getCuoteNumber()==c.getCuoteNumber()) { //Hau metodo baetekin ordeazkeatu, sekuentzi diagramman errazagoa izango da modelatzeko
					return false;
				}
			}
			else {
				for(Quote cuote : bet.getCuote())
					if(cuote.getCuoteNumber()==c.getCuoteNumber()) {
						return false;
					}
			}
		}
		return true;
	}
	public void deleteBet(int betNumber) {
        for(Bet ubet: betList) {
            if(betNumber == ubet.getBetNumber()) {
                Bet bet = ubet;
                betList.remove(bet);
                break;
            }
        }
    }
	public void restMoney(int type, double amount) {
		this.money = this.money-amount;
		Transaction transaction = new Transaction(type, amount, this);
		this.addMoneyTransactions(transaction);
		transaction.setUser(this);
	}
	public Bet createBet(double amount, ArrayList<Quote> cuotes) {
		Bet b = new Bet(amount, this, cuotes);
		this.addBet(b);
		//b.setUser(this);
		//for(Cuote c :cuotes) c.addBet(b);
		return b;
	}
	public Transaction createMoneyTransaction(int type,double money) {
		Transaction transaction = new Transaction(type, money, this);
		this.addMoneyTransactions(transaction);
		//transaction.setUser(this);
		return transaction;
	}
	public void addFollower(Follow f) {
        boolean b = true;
        for(Follow f1 : followers) {
            if(f1.getFollower().getUserName().equals(f.getFollower().getUserName())) b = false;
            }
        if(b) this.followers.add(f);
    }

    public void addFollows(Follow f) {
        boolean b = true;
        for(Follow f1 : followings) {
        if(f1.getFollowed().getUserName().equals(f.getFollowed().getUserName())) b = false;
        }
        if(b) this.followings.add(f);
    }

    public void unfollow(NormalUser user) {
        for(Follow f : followings) {
            if(f.getFollowed().getUserName().equals(user.getUserName())) {
                followings.remove(f);
                break;
            }
        }
    }

    public double beUnfollowed(NormalUser user) {
        double money;
        for(Follow f : followers) {
            if(f.getFollower().getUserName().equals(user.getUserName())) {
                money = f.getMoney();
                followers.remove(f);
                return money;
            }
        }
        return 0;
    }
    public void updateRanking(Boolean irabazi) {
        if(irabazi) {
            this.points  = this.points + 20;
        }else {
            this.points  = this.points - 10;
        }
    }

    public ArrayList<Follow> getFollowers() {
        return this.followers;
    }

    public ArrayList<Follow> getFollows() {
        return this.followings;
    }

    public boolean jarraitzenDu(NormalUser u){
    	for(Follow f : followings) {
    		if(u.getUserName().equals(f.getFollowed().getUserName())) {
    			return true;
    		}
    	}
    	return false;
    }
    public int getPoints() {
    	return this.points;
    }
    public void setPoint(int points) {
    	this.points=points;
    }
    @Override
    public String toString() {
       return getUserName() + " " + points ;
    }
}
