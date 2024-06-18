package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
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
		for(int i = 0; i < hand.size(); i++)
			if(hand.get(i).getValue() == 1)
				return true;
		return false;
	}
	
	public void addCard(Card card) {
		hand.add(card);
	}
	
	public boolean hasFullHand() {			//hand has 21, ace +10 value included
		return getHandValue() == 21 || (hasAce() && getHandValue() == 11);
	}
	
	public boolean hasFinishedHand() {		//hand has 21, ace +10 value included, or bust
		return getHandValue() >= 21 || (hasAce() && getHandValue() == 11);
	}
	
	public boolean hasBustedHand() {
		return getHandValue() > 21;
	}
	
	public boolean hasBlackjack() {
		return hand.size() == 2 && hasAce() && getHandValue() == 11;
	}
	
	public int getHandValue() {
		int value = 0;
		for(int i = 0; i < hand.size(); i++) 
			value += hand.get(i).getValue();
		return value;
	}
	
	public int getFinalHandValue() {		//returns the hand value with bonuses from the ace if possible
		int value = getHandValue();
		if(hasAce() && value <= 11)
			value = getHandValue()+10;
		return value;
	}
	
}
