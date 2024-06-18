package game;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
//import utilities.ObjectSaver;
import utilities.ObjectLoader;

public class Main {

	private static WaitNotify wn = new WaitNotify();
	private static TableFrame frame;
	
	private static Set<Card> allCards;
	private static List<Card> cardDeck;
	
	private static int numDecks;
	private static int deckReshuffleMark;
	private static int balance, bet, betSplit;
	private static boolean canSplit;		//indicates if the player's first cards are equal, does not take balance into account
	private static boolean hasDoubled, hasSplit, splitAces, hasDoubledSplit, hasFinishedHand;		//when the player splits, hasFinishedHand indicates if the players 1st hand is finished
	private static Hand player, dealer, playerSplit;
	private static Random random = new Random();
	
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException {

		//ObjectSaver.saveCardsToFile();
		
		frame = new TableFrame(wn);
		
		loadGame();
		frame.initializeComponents();
		
		//frame.example(allCards);
		
		frame.paintStartMenu();
		
		wn.doWait();					//wait for player to press start

		loadDeck();
		frame.setupTable();
		newRound();

		
	}
	
	
	private static void loadGame() throws ClassNotFoundException, IOException {
		balance = 10000;
		allCards = new HashSet<>();
		
		ObjectLoader.loadCards(allCards);
	}
	
	private static void loadDeck() throws InterruptedException {
		cardDeck = new ArrayList<>();
		
		numDecks = frame.getNumberOfDecks();		//wait for player to select number of decks
		
		for(int i = 0; i < numDecks; i++)
			cardDeck.addAll(allCards);
		
		if(numDecks == 1 || numDecks == 2)
			deckReshuffleMark = cardDeck.size()/2;
		else
			deckReshuffleMark = random.nextInt(cardDeck.size()/4) + cardDeck.size()/2;		//random number between half of the deck's cards and 3/4 of it
	}
	
	
	private static void newRound() throws InterruptedException {
		//TODO: deck reshuffle
		
		canSplit = false;
		hasDoubled = false;
		hasSplit = false;
		splitAces = false;
		hasDoubledSplit = false;
		hasFinishedHand = false;
		
		checkForReshuffle();
		
		bet = frame.getBetAmmount(balance);
		
		balance -= bet;
		
		dealHands();
		
	}
	
	
	private static void checkForReshuffle() {
		
		if(cardDeck.size() <= deckReshuffleMark)
			reshuffleDeck();
		
	}
	
	
	private static void reshuffleDeck() {
		
		cardDeck.clear();
		for(int i = 0; i < numDecks; i++)
			cardDeck.addAll(allCards);
		
		if(numDecks == 1 || numDecks == 2)
			deckReshuffleMark = cardDeck.size()/2;
		else
			deckReshuffleMark = random.nextInt(cardDeck.size()/4) + cardDeck.size()/2;
		
	}
	
	
	private static void dealHands() throws InterruptedException {
		
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
		
		frame.paintStartingHands(player, dealer, playerCard1, playerCard2, dealerCard1);
		
		
		int blackjack = 0;		//at the end of the method, returns 0 if no one had a blackjack, 1 if player got one, 2 if dealer got one or 3 if both the player and the dealer got one
		
		if(playerCard1.rank == playerCard2.rank)
			canSplit = true;
		
		if(player.hasBlackjack())
			blackjack++;
		
		if(dealer.hasBlackjack())
			blackjack += 2;
		
		if(blackjack > 0)
			endRound(blackjack);
		else
			nextPlayerAction();
		
	}
	
	
	private static void nextPlayerAction() throws InterruptedException {
		int playerAction = 0;
		
		playerAction = frame.getPlayerAction(canSplit, hasSplit, hasFinishedHand, balance);			//1 = stand; 2 = hit;
		canSplit = false;
		
		if(hasFinishedHand)
			switch(playerAction) {
			case 1: drawPlayerSplitCard(); break;
			case 2: frame.paintHandScore(hasSplit, hasFinishedHand); drawDealerHand(); break;
			case 3: hasDoubledSplit = true; balance -= betSplit; betSplit *= 2; drawPlayerSplitCard(); break;
			}
		
		else
			switch(playerAction) {
			case 1: drawPlayerCard(); break;
			case 2: 
				frame.paintHandScore(hasSplit, hasFinishedHand);
				if(hasSplit) {
					hasFinishedHand = true;
					nextPlayerAction();
				}
				else
					drawDealerHand(); 
				break;
				
			case 3: hasDoubled = true; balance -= bet; bet *= 2; drawPlayerCard(); break;
			case 4 : splitHand(); break;
			}
		
	}
	
	
	private static void drawPlayerCard() throws InterruptedException {
		
		Card playerCard = cardDeck.get(random.nextInt(cardDeck.size()));
		player.addCard(playerCard);
		cardDeck.remove(playerCard);
		
		
		if(hasSplit) {
			frame.paintPlayerSplitCard(playerCard, hasFinishedHand, splitAces);
			
			if(player.hasFinishedHand() || splitAces || hasDoubled) {
				frame.paintHandScore(hasSplit, hasFinishedHand);
				hasFinishedHand = true;
			}
			
			nextPlayerAction();
			
		}
		else {
			frame.paintPlayerCard(playerCard);
			
			if(player.getHandValue() > 21)
				endRound(2);
			
			else if(player.hasFullHand() || hasDoubled) {
				frame.paintHandScore(hasSplit, hasFinishedHand);
				drawDealerHand();
			}
			
			else
				nextPlayerAction();
			
		}
		
	}
	
	private static void drawPlayerSplitCard() throws InterruptedException {
		
		Card playerCard = cardDeck.get(random.nextInt(cardDeck.size()));
		playerSplit.addCard(playerCard);
		cardDeck.remove(playerCard);
		
		frame.paintPlayerSplitCard(playerCard, hasFinishedHand, splitAces);
		
		
		int result = 0;
		
		if((player.getHandValue() > 21 || player.hasBlackjack()) && (playerSplit.getHandValue() > 21 || playerSplit.hasBlackjack())) {
			if(player.hasBlackjack()) result = 1;
			else result = 2;
			
			frame.paintHandScore(hasSplit, hasFinishedHand);
			endRound(result);
		}
		
		else if(playerSplit.hasFinishedHand() || splitAces || hasDoubledSplit) {
			frame.paintHandScore(hasSplit, hasFinishedHand);
			drawDealerHand();
		}
		
		else
			nextPlayerAction();
		
	}
	
	
	private static void splitHand() throws InterruptedException {
		
		hasSplit = true;
		betSplit = bet;
		balance -= betSplit;
		
		if(player.hasAce())
			splitAces = true;
		
		Card playerSplitCard = player.hand.get(1);
		player.hand.remove(1);
		
		playerSplit = new Hand(playerSplitCard, null);
		playerSplit.hand.remove(1);
		
		frame.paintSplit(playerSplit, balance);
		
		nextPlayerAction();
		
	}
	
	
	private static void drawDealerHand() throws InterruptedException {
		
		List<Card> dealerCards = new ArrayList<>();
		
		while(dealer.getFinalHandValue() < 17) {
			
			Card dealerCard = cardDeck.get(random.nextInt(cardDeck.size()));
			dealer.addCard(dealerCard);
			dealerCards.add(dealerCard);
			cardDeck.remove(dealerCard);
			
		}
		
		frame.paintDealerCards(dealerCards.iterator());
		
		endRound(getRoundResults());
		
	}
	
	
	private static int getRoundResults() throws InterruptedException {
		
		int result = 0;
		
		
		if(player.hasBustedHand() || (!dealer.hasBustedHand() && player.getFinalHandValue() < dealer.getFinalHandValue()))		//have to verify player's hand for bust because of split
			result = 2;
		else if(player.getFinalHandValue() == dealer.getFinalHandValue())
			result = 3;
		else
			result = 1;
		
		return result;
		
	}
	
	
	private static int getSplitRoundResults() {
		
		int splitResult = 0;
		
		
		if(playerSplit.hasBustedHand() || (!dealer.hasBustedHand() && playerSplit.getFinalHandValue() < dealer.getFinalHandValue()))
			splitResult = 2;
		else if(playerSplit.getFinalHandValue() == dealer.getFinalHandValue())
			splitResult = 3;
		else
			splitResult = 1;
		
		return splitResult;
		
	}
	
	
	private static void endRound(int result) throws InterruptedException {		//1 = player won; 2 = dealer won; 3 = tie (push);
		
		switch(result) {
		case 1:
			if(player.hasBlackjack())
				balance += bet*2.5; 
			else
				balance += bet*2;
			break;
			
		case 2: break;
		case 3: balance += bet; break;
		}
		
		
		if(hasSplit) {
			int splitResult = getSplitRoundResults();
			
			switch(splitResult) {
			case 1:
				if(playerSplit.hasBlackjack())
					balance += betSplit*2.5; 
				else
					balance += betSplit*2;
				break;
				
			case 2: break;
			case 3: balance += betSplit; break;
			}
			
			frame.paintRoundEnd(result, splitResult, balance);
		}
		
		else
			frame.paintRoundEnd(result, balance, hasSplit);
		
		newRound();
		
	}
	
	
	//TODO:
	//add surrender
	//add choice to hit more than once and/or double after splitting aces
	//add reshuffle
	//add insurance
	//add penetration
	

}

