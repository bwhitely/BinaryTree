package Database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Class to represent a PlayStation user.
 * Created for Data Structures, SP2 2017
 * @author James Baumeister
 * @version 1.0
 */
public class User {
    private String username;
	private int level;
	private double key;
	private ArrayList<Trophy> trophies;
	private GameList games;
	private Calendar dob;
	private User left;
	private User right;

	/** INSTANCE VARIABLE FOR AVL */
	private User parent;

	public User(String username, Calendar dob, int level) {
		this.username = username;
		this.dob = dob;
		this.level = level;
		this.key = calculateKey();
    }

    /**
     * DO NOT MODIFY THIS METHOD
     * This method uses the username and level to create a unique key.
     * As we don't want the username's hash to increase the level, it's first converted
     * to a floating point, then added to the level.
     * @return the unique key
     */
    public double calculateKey() {
        int hash = Math.abs(username.hashCode());
        // Calculate number of zeros we need
        int length = (int)(Math.log10(hash) + 1);
        // Make a divisor 10^x
        double divisor = Math.pow(10, length);
        // Return level.hash
        return level + hash / divisor;
    }

    public String toString() {
    	String trophyList = "";
    	// loop over to get each trophy and correct formatting
    	for (int i = 0; i < trophies.size(); i++) {
    		trophyList = trophyList + trophies.get(i).toString() + "\n";
		}

    	String gameList ="";
    	Game ref = games.head;
    	while (ref != null) {
    		gameList = gameList + getGames();
			System.out.println(gameList);
    		ref = ref.getNext();
		}

		// date format to Mon dd, yyyy
		DateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
		simpleDateFormat.setCalendar(getDob());
		// getting date from Gregorian calendar via getTime() method
		String convertedDate = simpleDateFormat.format(getDob().getTime());

		String x = "User: " + getUsername() + "\n\nTrophies: \n" + trophyList + "\nGames: " +
				"\n" + getGames() + "\n\n" + "Birth Date: "+ convertedDate; // do after GameList

		return x;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getKey() {
		return key;
	}

	public void setKey(double key) {
		this.key = key;
	}

	public ArrayList<Trophy> getTrophies() {
		return trophies;
	}

	public void setTrophies(ArrayList<Trophy> trophies) {
		this.trophies = trophies;
	}

	public GameList getGames() {
		return games;
	}

	public void setGames(GameList games) {
		this.games = games;
	}

	public Calendar getDob() {
		return dob;
	}

	public void setDob(Calendar dob) {
		this.dob = dob;
	}

	public User getLeft() {
		return left;
	}

	public void setLeft(User left) {
		this.left = left;
	}

	public User getRight() {
		return right;
	}

	public void setRight(User right) {
		this.right = right;
	}

	public User getParent() {
		return parent;
	}

	public void setParent(User parent) {
		this.parent = parent;
	}

}
