package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Card implements Serializable {
	
	private static final long serialVersionUID = 1330856187194136137L;
	
	String name;
	int rank;	//number = value; ace = 1; jack = 11; queen = 12; king = 13
	int suit;	//clubs = 1; spades = 2; diamonds = 3; hearts = 4
	BufferedImage cardImage;
	

	public Card(String name, int rank, int suit) {
		this.name = name;
		this.rank = rank;
		this.suit = suit;
	}
	
	public void addImage(BufferedImage cardImage) {
		this.cardImage = cardImage;
	}
	
	public int getValue() {
		if(rank >= 10)
			return 10;
		else
			return rank;
	}
	
}
