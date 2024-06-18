package utilities;
import game.Card;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


public class ObjectSaver {

	private static HashMap<String, Card> cardsList = new HashMap<>();
	
	public static void saveCardsToFile() throws FileNotFoundException, IOException {
		
		//create cards
		cardsList.put("ClubsAce", new Card("Ace of Clubs", 1, 1));
		cardsList.put("Clubs2", new Card("2 of Clubs", 2, 1));
		cardsList.put("Clubs3", new Card("3 of Clubs", 3, 1));
		cardsList.put("Clubs4", new Card("4 of Clubs", 4, 1));
		cardsList.put("Clubs5", new Card("5 of Clubs", 5, 1));
		cardsList.put("Clubs6", new Card("6 of Clubs", 6, 1));
		cardsList.put("Clubs7", new Card("7 of Clubs", 7, 1));
		cardsList.put("Clubs8", new Card("8 of Clubs", 8, 1));
		cardsList.put("Clubs9", new Card("9 of Clubs", 9, 1));
		cardsList.put("Clubs10", new Card("10 of Clubs", 10, 1));
		cardsList.put("ClubsJack", new Card("Jack of Clubs", 11, 1));
		cardsList.put("ClubsQueen", new Card("Queen of Clubs", 12, 1));
		cardsList.put("ClubsKing", new Card("King of Clubs", 13, 1));
		cardsList.put("SpadesAce", new Card("Ace of Spades", 1, 2));
		cardsList.put("Spades2", new Card("2 of Spades", 2, 2));
		cardsList.put("Spades3", new Card("3 of Spades", 3, 2));
		cardsList.put("Spades4", new Card("4 of Spades", 4, 2));
		cardsList.put("Spades5", new Card("5 of Spades", 5, 2));
		cardsList.put("Spades6", new Card("6 of Spades", 6, 2));
		cardsList.put("Spades7", new Card("7 of Spades", 7, 2));
		cardsList.put("Spades8", new Card("8 of Spades", 8, 2));
		cardsList.put("Spades9", new Card("9 of Spades", 9, 2));
		cardsList.put("Spades10", new Card("10 of Spades", 10, 2));
		cardsList.put("SpadesJack", new Card("Jack of Spades", 11, 2));
		cardsList.put("SpadesQueen", new Card("Queen of Spades", 12, 2));
		cardsList.put("SpadesKing", new Card("King of Spades", 13, 2));
		cardsList.put("DiamondsAce", new Card("Ace of Diamonds", 1, 3));
		cardsList.put("Diamonds2", new Card("2 of Diamonds", 2, 3));
		cardsList.put("Diamonds3", new Card("3 of Diamonds", 3, 3));
		cardsList.put("Diamonds4", new Card("4 of Diamonds", 4, 3));
		cardsList.put("Diamonds5", new Card("5 of Diamonds", 5, 3));
		cardsList.put("Diamonds6", new Card("6 of Diamonds", 6, 3));
		cardsList.put("Diamonds7", new Card("7 of Diamonds", 7, 3));
		cardsList.put("Diamonds8", new Card("8 of Diamonds", 8, 3));
		cardsList.put("Diamonds9", new Card("9 of Diamonds", 9, 3));
		cardsList.put("Diamonds10", new Card("10 of Diamonds", 10, 3));
		cardsList.put("DiamondsJack", new Card("Jack of Diamonds", 11, 3));
		cardsList.put("DiamondsQueen", new Card("Queen of Diamonds", 12, 3));
		cardsList.put("DiamondsKing", new Card("King of Diamonds", 13, 3));
		cardsList.put("HeartsAce", new Card("Ace of Hearts", 1, 4));
		cardsList.put("Hearts2", new Card("2 of Hearts", 2, 4));
		cardsList.put("Hearts3", new Card("3 of Hearts", 3, 4));
		cardsList.put("Hearts4", new Card("4 of Hearts", 4, 4));
		cardsList.put("Hearts5", new Card("5 of Hearts", 5, 4));
		cardsList.put("Hearts6", new Card("6 of Hearts", 6, 4));
		cardsList.put("Hearts7", new Card("7 of Hearts", 7, 4));
		cardsList.put("Hearts8", new Card("8 of Hearts", 8, 4));
		cardsList.put("Hearts9", new Card("9 of Hearts", 9, 4));
		cardsList.put("Hearts10", new Card("10 of Hearts", 10, 4));
		cardsList.put("HeartsJack", new Card("Jack of Hearts", 11, 4));
		cardsList.put("HeartsQueen", new Card("Queen of Hearts", 12, 4));
		cardsList.put("HeartsKing", new Card("King of Hearts", 13, 4));
		
		
		//save cards to files
		Iterator<Entry<String, Card>> cardIt = cardsList.entrySet().iterator();
		Entry<String, Card> cardE;
		ObjectOutputStream cardSaver;
		
		while(cardIt.hasNext()) {
			cardE = cardIt.next();
			cardSaver = new ObjectOutputStream(new FileOutputStream(".\\resources\\cards\\"+cardE.getKey()+".txt"));
			cardSaver.writeObject(cardE.getValue());
			cardSaver.close();
		}
		
	}
	
}
