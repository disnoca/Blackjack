package game;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import utilities.ObjectSaver;
import utilities.ObjectLoader;

public class Main {
	
	private static Set<Card> allCards;
	private static List<Card> cardDeck;
	private static Hand dealer;
	private static Hand player;
	private static Random random = new Random();
	private static final String HOLECARD = "X";

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Scanner in = new Scanner(System.in);
		
		//ObjectSaver.saveCardsToFile();
		
		loadGame();
		System.out.println("Game loaded");
		System.out.println();
		startGame(in);
		
		in.close();
	}
	
	private static void loadGame() {
		allCards = new HashSet<>();
		
		try {
			ObjectLoader.loadCards(allCards);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void startGame(Scanner in) {
		cardDeck = new ArrayList<>();
		cardDeck.addAll(allCards);
		
		dealHands(in);
		
		
	}
	
	private static void startNewRound(Scanner in) {
		
	}
	
	private static void dealHands(Scanner in) {
		
		Card playerCard1 = cardDeck.get(random.nextInt(cardDeck.size()));
		cardDeck.remove(playerCard1);
		Card playerCard2 = cardDeck.get(random.nextInt(cardDeck.size()));
		cardDeck.remove(playerCard2);
		player = new Hand(playerCard1, playerCard2);
		
		Card dealerCard1 = cardDeck.get(random.nextInt(cardDeck.size()));
		cardDeck.remove(dealerCard1);
		Card dealerCard2 = cardDeck.get(random.nextInt(cardDeck.size()));
		cardDeck.remove(dealerCard2);
		dealer = new Hand(dealerCard1, dealerCard2);
		
		int blackjack = 0;
		System.out.print("Your hand: "+playerCard1.name+", "+playerCard2.name+" (");
		if(player.hasBlackjack()) {
			System.out.println((player.getHandValue()+10)+") Blackjack");
			blackjack++;
		}
		else if(player.hasAce())
			System.out.println(player.getHandValue()+" or "+(player.getHandValue()+10)+")");
		else
			System.out.println(player.getHandValue()+")");
		
		System.out.printf("Dealer's hand: "+dealerCard1.name+", "+HOLECARD+" (");
		
		if(dealerCard1.getValue() == 1) {
			System.out.println(dealerCard1.getValue()+" or "+(dealerCard1.getValue()+10)+")");
			offerInsurance(in);
		}
		if(dealer.hasBlackjack()) {
			if(dealerCard1.getValue() != 1)
				System.out.println(dealerCard1.getValue()+")");
			System.out.println("Dealer's hand: "+dealerCard1.name+", "+dealerCard1.name+" ("+(dealer.getHandValue()+10)+") Blackjack");
			blackjack += 2;
		}
		System.out.println(dealerCard1.getValue()+")");
		
		if(blackjack > 0) 
			endRound(in, blackjack);
		else
			playerAction(in);
	}
	
	private static void offerInsurance(Scanner in) {
		return;
	}
	
	private static void playerAction(Scanner in) {
		System.out.println();
		System.out.println("stand	hit");
		String choice = in.next(); in.nextLine();
		if(choice.equals("stand")) {
			if(dealer.getHandValue() >= 17) {
				System.out.println("Dealer's hand: "+dealer.hand.get(0).getValue()+", "+dealer.hand.get(1).getValue()+" ("+dealer.getHandValue()+")");
				endRound(in, getRoundResult());
			}
			else
				drawDealerHand(in);
		}
		else if(choice.equals("hit"))
			drawPlayerCard(in);
		
	}
	
	private static void drawPlayerCard(Scanner in) {
		System.out.println();
		Card playerCard = cardDeck.get(random.nextInt(cardDeck.size()));
		player.addCard(playerCard);
		cardDeck.remove(playerCard);
		
		System.out.print("Your hand: "+player.hand.get(0).name+", "+player.hand.get(1).name);
		for(int i = 2; i < player.hand.size(); i++)
			System.out.print(", "+player.hand.get(i).name);
		System.out.print(" (");
		if(player.getHandValue() > 21) {
			System.out.println(player.getHandValue()+") Bust");
			endRound(in, 2);
		}
		else if(player.hasAce() && player.getHandValue()+10 <= 21) {
			if(player.getHandValue()+10 == 21) {
				System.out.println(player.getHandValue()+")");
				drawDealerHand(in);
				return;
			}
			else
				System.out.println(player.getHandValue()+" or "+(player.getHandValue()+10)+")");
		}
		else
			System.out.println(player.getHandValue()+")");
		
		if(player.getHandValue() == 21)
			drawDealerHand(in);
		else
			playerAction(in);
	}
	
	private static void drawDealerHand(Scanner in) {
		System.out.println();
		System.out.println("Dealer's hand: "+dealer.hand.get(0).name+", "+dealer.hand.get(1).name+" ("+(dealer.getHandValue())+")");
		
		while(dealer.getHandValue() < 17) {
			Card dealerCard = cardDeck.get(random.nextInt(cardDeck.size()));
			dealer.addCard(dealerCard);
			cardDeck.remove(dealerCard);
			
			
			System.out.print("Dealer's hand: "+dealer.hand.get(0).name+", "+dealer.hand.get(1).name);
			
			for(int i = 2; i < dealer.hand.size(); i++)
				System.out.print(", "+dealer.hand.get(i).name);
			
			System.out.print(" (");
			
			if(dealer.getHandValue() > 21)
				System.out.println(dealer.getHandValue()+") Bust");
			
			else if(player.hasAce() && dealer.getHandValue()+10 <= 21) {
				
				if(dealer.getHandValue()+10 == 21)
					System.out.println(dealer.getHandValue()+")");
				else
					System.out.println(dealer.getHandValue()+" or "+(dealer.getHandValue()+10)+")");
				
			}
			else
				System.out.println(dealer.getHandValue()+")");
			
		}
		
		endRound(in, getRoundResult());
	}
	
	private static int getRoundResult() {
		if(player.getHandValue() > 21)
			return 2;
		if(dealer.getHandValue() > 21)
			return 1;
		if(player.getHandValue() > dealer.getHandValue())
			return 1;
		else if(player.getHandValue() < dealer.getHandValue())
			return 2;
		else
			return 3;
	}
	
	//result: 1 = player wins; 2 = player loses; 3 = tie (push)
	private static void endRound(Scanner in, int result) {	
		int playerPoints;
		int dealerPoints;
		if(player.hasAce() && player.getHandValue()+10 <= 21)
			playerPoints = player.getHandValue()+10;
		else
			playerPoints = player.getHandValue();
		if(dealer.hasAce() && dealer.getHandValue()+10 <= 21)
			dealerPoints = dealer.getHandValue()+10;
		else
			dealerPoints = dealer.getHandValue();
		
		
		System.out.println(playerPoints+" to "+dealerPoints);
		
		switch(result) {
		case 1:	System.out.println("You win"); break;
		case 2:	System.out.println("You lose"); break;
		case 3:	System.out.println("Push"); break;
		}
		System.out.println("Play again? (Y/N)");
		String playAgain = in.next(); in.nextLine();
		if(playAgain.equals("Y"))
			startNewRound(in);
	}

}
