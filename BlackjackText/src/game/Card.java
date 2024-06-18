package game;
import java.io.Serializable;

public class Card implements Serializable {
	
	private static final long serialVersionUID = -2061769048156042558L;
	
	String name;
	int rank;	//number = value; ace = 1; jack = 11; queen = 12; king = 13
	int suit;	//clubs = 1; spades = 2; diamonds = 3; hearts = 4
	

	public Card(String name, int rank, int suit) {
		this.name = name;
		this.rank = rank;
		this.suit = suit;
	}
	
	public int getValue() {
		if(rank >= 10)
			return 10;
		else
			return rank;
	}
	
}
