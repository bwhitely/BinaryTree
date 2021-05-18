package Database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Class to represent a PlayStation game.
 * Created for Data Structures, SP2 2017
 * @author James Baumeister
 * @version 1.0
 */
public class Game {
	private String name;
	private Calendar released;
	private Game next;
	private int totalTrophies;

    public Game() {}

    public Game(String name, Calendar released, int totalTrophies) {
    	this.name = name;
    	this.released = released;
    	this.totalTrophies = totalTrophies;
    }

    public String toString() {
    	// date format to Mon dd, yyyy
    	DateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
		simpleDateFormat.setCalendar(getReleased());
		// getting date from Gregorian calendar via getTime() method
		String convertedDate = simpleDateFormat.format(getReleased().getTime());
    	return '"'+getName()+'"' + ", released on: " + convertedDate;
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Game game = (Game) o;
		return totalTrophies == game.totalTrophies &&
				name.equals(game.name) &&
				released.equals(game.released) &&
				next.equals(game.next);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, released, next, totalTrophies);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getReleased() {
		return released;
	}

	public void setReleased(Calendar released) {
		this.released = released;
	}

	public Game getNext() {
		return next;
	}

	public void setNext(Game next) {
		this.next = next;
	}

	public int getTotalTrophies() {
		return totalTrophies;
	}

	public void setTotalTrophies(int totalTrophies) {
		this.totalTrophies = totalTrophies;
	}

}
