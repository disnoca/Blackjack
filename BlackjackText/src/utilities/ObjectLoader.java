package utilities;
import game.Card;

import java.util.Scanner;
import java.util.Set;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectLoader {

	public static void loadCards(Set<Card> allCards) throws IOException, ClassNotFoundException {
		
		Scanner cardsList = new Scanner(new FileReader(".\\resources\\cards\\.cardslist.txt"));
		ObjectInputStream cardLoader;
		
		while(cardsList.hasNext()) {
			cardLoader = new ObjectInputStream(new FileInputStream(".\\resources\\cards\\"+cardsList.nextLine().trim()+".txt"));
			allCards.add((Card) cardLoader.readObject());
		}
		
	}
	
}
