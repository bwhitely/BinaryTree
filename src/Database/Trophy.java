package Database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class to represent a PlayStation game trophy. A trophy comes in
 * four ranks: bronze, silver, gold and platinum. The date the trophy was
 * earned and its respective game is also stored.
 * Created for Data Structures, SP2 2017
 * @author James Baumeister
 * @version 1.0
 */
public class Trophy {
    public enum Rank {
		BRONZE, SILVER, GOLD, PLATINUM

	}

	public enum Rarity {
		COMMON, UNCOMMON, RARE, VERY_RARE, ULTRA_RARE

	}

	private String name;
    private Rank rank;
    private Rarity rarity;
    private Calendar obtained;
    private Game game;

	public Trophy() {}

    public Trophy(String name, Rank rank, Rarity rarity, Calendar obtained, Game game) {
		this.name = name;
		this.rank = rank;
		this.rarity = rarity;
		this.obtained = obtained;
		this.game = game;
    }

    public String toString() {
		// date format to Mon dd, yyyy
		DateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
		simpleDateFormat.setCalendar(getObtained());
		// getting date from Gregorian calendar via getTime() method
		String convertedDate = simpleDateFormat.format(getObtained().getTime());
		return '"' + getName() + '"' + ", rank: " + getRank() + ", rarity: " + getRarity() + ", obtained on: " + convertedDate;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}

	public Calendar getObtained() {
		return obtained;
	}

	public void setObtained(Calendar obtained) {
		this.obtained = obtained;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
