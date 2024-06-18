package game;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	
	List<Card> hand;

	Hand(Card card1, Card card2) {
		hand = new ArrayList<>();
		hand.add(card1);
		hand.add(card2);
	}
	
	public boolean hasAce() {
		boolean hasAce = false;
		for(int i = 0; i < hand.size() && !hasAce; i++)
			if(hand.get(i).getValue() == 1)
				hasAce = true;
		return hasAce;
	}
	
	public void addCard(Card card) {
		hand.add(card);
	}
	
	public boolean hasBlackjack() {
		return hand.size() == 2 && hasAce() && getHandValue() == 11;	//getHandValue() == 11 since Ace is worth 1
	}
	
	public int getHandValue() {
		int value = 0;
		for(int i = 0; i < hand.size(); i++) 
			value += hand.get(i).getValue();
		return value;
	}
	
}
