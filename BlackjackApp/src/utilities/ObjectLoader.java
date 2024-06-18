package utilities;
import game.Card;

import java.util.Scanner;
import java.util.Set;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectLoader {

	public static void loadCards(Set<Card> allCards) throws IOException, ClassNotFoundException {
		
		Scanner cardsList = new Scanner(new FileReader(".\\resources\\objects\\cards\\.cardslist.txt"));
		ObjectInputStream cardLoader;
		
		while(cardsList.hasNext()) {
			String filename = cardsList.nextLine().trim();
			cardLoader = new ObjectInputStream(new FileInputStream(".\\resources\\objects\\cards\\"+filename+".txt"));
			Card card = (Card) cardLoader.readObject();
			card.addImage(ImageIO.read(new File(".\\resources\\images\\cards\\"+filename+".png")));
			allCards.add(card);
		}
		
	}
	
}
