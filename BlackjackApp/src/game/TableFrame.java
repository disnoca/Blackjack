package game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.*;

public class TableFrame extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 8478814026672287498L;

	private WaitNotify wn;
	
	private final Dimension SCREEN_SIZE = new Dimension(1000, 1000);
	private final int CARD_WIDTH = 175;
	private final int CARD_HEIGHT = 254;
	private final int CARD_GAP = 50;
	private final int REDUCED_CARD_WIDTH = 140;
	private final int REDUCED_CARD_HEIGHT = 203;
	private final int REDUCED_CARD_GAP = 40;
	
	
	private JLayeredPane playerCards, dealerCards, playerSplitCards1, playerSplitCards2;
	private JPanel deckAmountPanel, betAmountPanel, playerChoicesPanel, roundEnd;
	private JLabel loading, blackjackStart, decksAmountLabel, betAmountLabel;
	private JLabel playerLabel, dealerLabel, playerScore, dealerScore, playerBust, dealerBust;
	private JLabel balanceLabel, betLabel, balanceAmount, betAmount;
	private JLabel playerSplitLabel, playerSplitScore, betSplitLabel, betSplitAmount, playerSplitBust;
	private JLabel playerSplitArrow1, playerSplitArrow2;
	private JLabel win, lose, push, splitRoundResult, money;
	private JLabel card, cardBack;
	private JButton startButton;
	private JButton stand, hit, doubleDown, split, surrender;
	private JButton deckAmount1, deckAmount2, deckAmount4, deckAmount6, deckAmount8;
	private JButton betAmount100, betAmount500, betAmount1000, betAmount1500, betAmount5000, betAmount10000;
	private List<JButton> betAmounts;
	
	private BufferedImage background;
	private Hand player, dealer, playerSplit;
	private int numDecks, bet, splitBet;
	private int playerChoice;
	
	
	
	TableFrame(WaitNotify wn) {
		this.wn = wn;
		
		try {
			background = ImageIO.read(new File(".\\resources\\images\\background.png"));
			cardBack = new JLabel(new ImageIcon(".\\resources\\images\\cardBack.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setTitle("Blackjack App");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(SCREEN_SIZE);
		this.setLayout(null);
		this.setContentPane(new TableBackground(background));
		this.setLocationRelativeTo(null);
		
		loading = new JLabel("loading...");
		loading.setBounds(385, 460, 230, 80);
		loading.setFont(new Font("Arial", Font.BOLD, 50));
		loading.setForeground(Color.BLACK);
		this.add(loading);
		
		this.setVisible(true);

	}
	
	
	public void initializeComponents() {
		
		//start menu components
		blackjackStart = new JLabel("Blackjack");
		blackjackStart.setBounds(250, 100, 500, 120);
		blackjackStart.setFont(new Font("Arial", Font.BOLD, 108));
		blackjackStart.setForeground(Color.BLACK);
		
		startButton = new JButton("Start");
		startButton.setBounds(250, 425, 500, 150);
		startButton.setFont(new Font("Arial", Font.PLAIN, 108));
		startButton.setForeground(Color.black);
		startButton.setFocusable(false);
		startButton.addActionListener(this);
		
		
		//panel with buttons to choose amount of decks for the game
		decksAmountLabel = new JLabel("Choose the number of decks to play with:");
		decksAmountLabel.setBounds(235, 50, 600, 40);
		decksAmountLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		decksAmountLabel.setForeground(Color.BLACK);
		
		deckAmount1 = new JButton("1 Deck");
		deckAmount1.setFocusable(false);
		deckAmount1.addActionListener(this);
		deckAmount2 = new JButton("2 Decks");
		deckAmount2.setFocusable(false);
		deckAmount2.addActionListener(this);
		deckAmount4 = new JButton("4 Decks");
		deckAmount4.setFocusable(false);
		deckAmount4.addActionListener(this);
		deckAmount6 = new JButton("6 Decks");
		deckAmount6.setFocusable(false);
		deckAmount6.addActionListener(this);
		deckAmount8 = new JButton("8 Decks");
		deckAmount8.setFocusable(false);
		deckAmount8.addActionListener(this);
		
		deckAmountPanel = new JPanel();
		deckAmountPanel.setBounds(350, 200, 300, 600);
		deckAmountPanel.setLayout(new GridLayout(5, 1, 0, 30));
		deckAmountPanel.setOpaque(false);
		deckAmountPanel.add(deckAmount1);
		deckAmountPanel.add(deckAmount2);
		deckAmountPanel.add(deckAmount4);
		deckAmountPanel.add(deckAmount6);
		deckAmountPanel.add(deckAmount8);
		
		
		//panel with bet amounts
		betAmountLabel = new JLabel("Choose the bet amount:");
		betAmountLabel.setBounds(338, 385, 450, 40);
		betAmountLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		betAmountLabel.setForeground(Color.BLACK);
		
		betAmount100 = new JButton("$100");
		betAmount100.setFocusable(false);
		betAmount100.addActionListener(this);
		betAmount500 = new JButton("$500");
		betAmount500.setFocusable(false);
		betAmount500.addActionListener(this);
		betAmount1000 = new JButton("$1000");
		betAmount1000.setFocusable(false);
		betAmount1000.addActionListener(this);
		betAmount1500 = new JButton("$1500");
		betAmount1500.setFocusable(false);
		betAmount1500.addActionListener(this);
		betAmount5000 = new JButton("$5000");
		betAmount5000.setFocusable(false);
		betAmount5000.addActionListener(this);
		betAmount10000 = new JButton("$10000");
		betAmount10000.setFocusable(false);
		betAmount10000.addActionListener(this);
		
		betAmounts = new ArrayList<>(6);
		betAmounts.add(betAmount100);
		betAmounts.add(betAmount500);
		betAmounts.add(betAmount1000);
		betAmounts.add(betAmount1500);
		betAmounts.add(betAmount5000);
		betAmounts.add(betAmount10000);
		
		betAmountPanel = new JPanel();
		betAmountPanel.setBounds(200, 450, 600, 60);
		betAmountPanel.setLayout(new GridLayout(1, 6, 20, 0));
		betAmountPanel.setOpaque(false);
		betAmountPanel.add(betAmount100);
		betAmountPanel.add(betAmount500);
		betAmountPanel.add(betAmount1000);
		betAmountPanel.add(betAmount1500);
		betAmountPanel.add(betAmount5000);
		betAmountPanel.add(betAmount10000);
		
		
		//labels for player and dealer's name and score, among others
		dealerLabel = new JLabel("Dealer:");
		dealerLabel.setBounds(50, 5, 100, 50);
		dealerLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		dealerLabel.setForeground(Color.BLACK);
		
		playerLabel = new JLabel("Player:");
		playerLabel.setBounds(50, 900, 100, 50);
		playerLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		playerLabel.setForeground(Color.BLACK);
		
		playerSplitLabel = new JLabel("Player:");
		playerSplitLabel.setBounds(50, 691, 100, 50);
		playerSplitLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		playerSplitLabel.setForeground(Color.BLACK);
		
		dealerScore = new JLabel();
		dealerScore.setBounds(153, 6, 200, 50);
		dealerScore.setFont(new Font("Arial", Font.PLAIN, 30));
		dealerScore.setForeground(Color.BLACK);
		
		playerScore = new JLabel();
		playerScore.setBounds(151, 901, 200, 50);
		playerScore.setFont(new Font("Arial", Font.PLAIN, 30));
		playerScore.setForeground(Color.BLACK);
		
		playerSplitScore= new JLabel();
		playerSplitScore.setBounds(151, 692, 200, 50);
		playerSplitScore.setFont(new Font("Arial", Font.PLAIN, 30));
		playerSplitScore.setForeground(Color.BLACK);
		
		ImageIcon bust = new ImageIcon(".\\resources\\images\\bust.png");
		dealerBust = new JLabel(bust);
		dealerBust.setBounds(190, 8, 45, 45);
		playerBust = new JLabel(bust);
		playerBust.setBounds(188, 903, 45, 45);
		playerSplitBust = new JLabel(bust);
		playerSplitBust.setBounds(188, 694, 45, 45);
		
		ImageIcon arrow = new ImageIcon(".\\resources\\images\\arrow.png");
		playerSplitArrow1 = new JLabel(arrow);
		playerSplitArrow1.setBounds(220, 760, 50, 30);
		playerSplitArrow2 = new JLabel(arrow);
		playerSplitArrow2.setBounds(220, 551, 50, 30);
		
		balanceLabel = new JLabel("Balance:");
		balanceLabel.setBounds(700, 900, 200, 50);
		balanceLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		balanceLabel.setForeground(Color.BLACK);
		
		betLabel = new JLabel("Bet:");
		betLabel.setBounds(700, 860, 100, 50);
		betLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		betLabel.setForeground(Color.BLACK);
		
		betSplitLabel = new JLabel("Bet:");
		betSplitLabel.setBounds(700, 691, 100, 50);
		betSplitLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		betSplitLabel.setForeground(Color.BLACK);
		
		balanceAmount = new JLabel("$10000");
		balanceAmount.setBounds(830, 901, 200, 50);
		balanceAmount.setFont(new Font("Arial", Font.PLAIN, 30));
		balanceAmount.setForeground(Color.BLACK);
		
		betAmount = new JLabel();
		betAmount.setBounds(765, 861, 200, 50);
		betAmount.setFont(new Font("Arial", Font.PLAIN, 30));
		betAmount.setForeground(Color.BLACK);
		
		betSplitAmount = new JLabel();
		betSplitAmount.setBounds(765, 691, 200, 50);
		betSplitAmount.setFont(new Font("Arial", Font.PLAIN, 30));
		betSplitAmount.setForeground(Color.BLACK);
		
		
		//messages for round ends to indicate whether the player won, lost or drew along with the respective amount of money
		roundEnd = new JPanel();
		roundEnd.setBounds(340, 380, 300, 160);
		roundEnd.setLayout(new GridLayout(2, 1));
		roundEnd.setOpaque(false);
		
		win = new JLabel("You Win");
		win.setFont(new Font("Arial", Font.PLAIN, 50));
		win.setHorizontalAlignment(JLabel.CENTER);
		win.setForeground(Color.BLACK);
		
		lose = new JLabel("You Lose");
		lose.setFont(new Font("Arial", Font.PLAIN, 50));
		lose.setHorizontalAlignment(JLabel.CENTER);
		lose.setForeground(Color.BLACK);
		
		push = new JLabel("Push");
		push.setFont(new Font("Arial", Font.PLAIN, 50));
		push.setHorizontalAlignment(JLabel.CENTER);
		push.setForeground(Color.BLACK);
		
		splitRoundResult = new JLabel();			//used in case of split, gets set up on split round end
		splitRoundResult.setFont(new Font("Arial", Font.PLAIN, 50));
		splitRoundResult.setHorizontalAlignment(JLabel.CENTER);
		splitRoundResult.setForeground(Color.BLACK);
		
		money = new JLabel();
		money.setFont(new Font("Arial", Font.PLAIN, 50));
		money.setHorizontalAlignment(JLabel.CENTER);
		money.setForeground(Color.BLACK);
		
		
		//layeredpane for cards to stack on top of each other and preset cardBack bounds since its always same place
		dealerCards = new JLayeredPane();
		dealerCards.setBounds(280, 8, 750, CARD_HEIGHT);
		playerCards = new JLayeredPane();
		playerCards.setBounds(280, 700, 750, CARD_HEIGHT);
		playerSplitCards1 = new JLayeredPane();
		playerSplitCards1.setBounds(280, 751, 751, REDUCED_CARD_HEIGHT);
		playerSplitCards2 = new JLayeredPane();
		playerSplitCards2.setBounds(280, 542, 750, REDUCED_CARD_HEIGHT);
		
		cardBack.setBounds(CARD_GAP, 0, CARD_WIDTH, CARD_HEIGHT);
		
		
		//panel with choices for the player when its his action
		stand = new JButton("Stand");
		stand.setFocusable(false);
		stand.addActionListener(this);
		hit = new JButton("Hit");
		hit.setFocusable(false);
		hit.addActionListener(this);
		doubleDown = new JButton("Double");
		doubleDown.setFocusable(false);
		doubleDown.addActionListener(this);
		split = new JButton("Split");
		split.setFocusable(false);
		split.addActionListener(this);
		surrender = new JButton("Surrender");
		surrender.setFocusable(false);
		surrender.addActionListener(this);
		
		playerChoicesPanel = new JPanel();
		playerChoicesPanel.setOpaque(false);
		playerChoicesPanel.setBounds(200, 475, 600, 50);
		playerChoicesPanel.setLayout(new GridLayout(1, 3, 50, 0));
		playerChoicesPanel.add(hit);
		playerChoicesPanel.add(stand);
		playerChoicesPanel.add(doubleDown);
		
		surrender.setBounds(850, 905, 100, 40);
		
	}
	
	
	public void example(Set<Card> allCards) {
		this.remove(loading);


		
		this.retard();
		this.repaint();
	}
	
	
	public void paintStartMenu() {	//Adds a label with the text "Blackjack" and a button to start the game
		
		this.remove(loading);
		
		this.add(blackjackStart);
		this.add(startButton);
		this.repaint();
		
	}
	
	
	public int getNumberOfDecks() throws InterruptedException {
		
		this.remove(blackjackStart);
		this.remove(startButton);
		this.remove(loading);
		
		this.add(decksAmountLabel);
		this.add(deckAmountPanel);
		retard();
		this.repaint();
		
		wn.doWait();			//wait for player to select number of decks
		
		this.remove(decksAmountLabel);
		this.remove(deckAmountPanel);
		this.repaint();
		
		return numDecks;
		
	}
	
	
	public int getBetAmmount(int balance) throws InterruptedException {
		
		for(int i = 0; i < betAmounts.size(); i++) {		//deactivate any bets you can't make with your current balance
			JButton betButton = betAmounts.get(i);
			if(Integer.parseInt(betButton.getText().substring(1)) > balance && betButton.isEnabled())
				betButton.setEnabled(false);
		}
		
		this.add(betAmountLabel);
		this.add(betAmountPanel);
		
		retard();
		this.repaint();
		
		wn.doWait();
		
		splitBet = bet;
		
		this.remove(betAmountLabel);
		this.remove(betAmountPanel);
		
		balanceAmount.setText("$"+(balance-bet));
		betAmount.setText("$"+bet);
		
		return bet;
		
	}
	
	
	public void setupTable() {
		
		this.add(playerLabel);
		this.add(dealerLabel);
		this.add(playerScore);
		this.add(dealerScore);
		
		this.add(balanceLabel);
		this.add(balanceAmount);
		this.add(betLabel);
		this.add(betAmount);
		
		this.repaint();
		
	}
	
	public void paintStartingHands(Hand player, Hand dealer, Card playerCard1, Card playerCard2, Card dealerCard1) throws InterruptedException {
		
		this.player = player;
		this.dealer = dealer;
		
		this.add(playerCards);
		this.add(dealerCards);
		this.repaint();
		
		Thread.sleep(1000);
		
		
		card = new JLabel(new ImageIcon(playerCard1.cardImage));
		card.setBounds(0, 0, CARD_WIDTH, CARD_HEIGHT);
		
		playerCards.add(card, 0);
		playerCards.repaint();
		
		if(playerCard1.getValue() == 1)
			playerScore.setText("1 or 11");
		else 
			playerScore.setText(String.valueOf(playerCard1.getValue()));
		playerScore.repaint();
		
		Thread.sleep(1000);
		
		
		card = new JLabel(new ImageIcon(playerCard2.cardImage));
		card.setBounds(CARD_GAP, 0, CARD_WIDTH, CARD_HEIGHT);
		playerCards.add(card, 0);
		playerCards.repaint();
		
		if(player.hasBlackjack())
			playerScore.setText("21");
		else if(player.hasAce())
			playerScore.setText(player.getHandValue()+" or "+(player.getHandValue()+10));
		else
			playerScore.setText(String.valueOf(player.getHandValue()));
		playerScore.repaint();
		
		Thread.sleep(1000);
			
		
		
		card = new JLabel(new ImageIcon(dealerCard1.cardImage));
		card.setBounds(0, 0, CARD_WIDTH, CARD_HEIGHT);
		dealerCards.add(card, 0);
		dealerCards.repaint();
		
		if(dealerCard1.getValue() == 1)
			dealerScore.setText("1 or 11");
		else 
			dealerScore.setText(String.valueOf(dealerCard1.getValue()));
		dealerScore.repaint();
		
		Thread.sleep(1000);
		
		
		dealerCards.add(cardBack, 0);
		dealerCards.repaint();
		
		Thread.sleep(1000);
		
		if(dealer.hasBlackjack()) {
			dealerCards.remove(cardBack);
			card = new JLabel(new ImageIcon(dealer.hand.get(1).cardImage));
			card.setBounds(CARD_GAP, 0, CARD_WIDTH, CARD_HEIGHT);
			dealerCards.add(card, 0);
		}

	}
	
	
	public int getPlayerAction(Boolean canSplit, Boolean hasSplit, Boolean hasFinishedHand, int balance) throws InterruptedException {
		
		if(balance < bet || (hasSplit && player.hand.get(0).getValue() == 1))		//if balance is less than bet or the player has split aces, double button is disabled
			doubleDown.setEnabled(false);
		else
			doubleDown.setEnabled(true);
		
		if(canSplit) {
			playerChoicesPanel.setLayout(new GridLayout(1, 4, 25, 0));
			playerChoicesPanel.add(split);
				if(balance >= bet)
					split.setEnabled(true);
				else
					split.setEnabled(false);
				
		}
		else {
			playerChoicesPanel.setLayout(new GridLayout(1, 3, 50, 0));
			playerChoicesPanel.remove(split);
		}
		
		this.add(playerChoicesPanel);
		retard();
		this.repaint();
		
		wn.doWait();
		
		this.remove(playerChoicesPanel);
		this.repaint();
		
		if(playerChoice == 3) {
			if(hasFinishedHand)
				balance -= splitBet;
			else
				balance -= bet;
			
			balanceAmount.setText("$"+balance);
			
			if(hasFinishedHand) {
				splitBet *= 2;
				betSplitAmount.setText("$"+splitBet);
			}
			
			else {
				bet *= 2;
				betAmount.setText("$"+bet);
			}
			
			this.repaint();
		}
		
		
		return playerChoice;
		
	}
	
	
	public void paintPlayerCard(Card playerCard) throws InterruptedException {	
		
		card = new JLabel(new ImageIcon(playerCard.cardImage));
		card.setBounds((CARD_GAP*(player.hand.size()-1)), 0, CARD_WIDTH, CARD_HEIGHT);
		
		Thread.sleep(1000);
		
		playerCards.add(card, 0);
		
		if(player.hasFullHand())
			playerScore.setText("21");
		else if(player.hasAce() && player.getHandValue() < 11)
			playerScore.setText(player.getHandValue()+" or "+(player.getHandValue()+10));
		else {
			playerScore.setText(String.valueOf(player.getHandValue()));
			if(player.getHandValue() > 21)
				this.add(playerBust);
		}
		
		this.repaint();
		
	}
	
	
	public void paintSplit(Hand playerSplit, int balance) throws InterruptedException {
		
		this.playerSplit = playerSplit;
		
		int splitCardValue = player.hand.get(0).getValue();
		
		playerCards.removeAll();
		
		JLabel card1 = new JLabel(new ImageIcon(player.hand.get(0).cardImage.getScaledInstance(REDUCED_CARD_WIDTH, REDUCED_CARD_HEIGHT, Image.SCALE_SMOOTH)));
		JLabel card2 = new JLabel(new ImageIcon(playerSplit.hand.get(0).cardImage.getScaledInstance(REDUCED_CARD_WIDTH, REDUCED_CARD_HEIGHT, Image.SCALE_SMOOTH)));
		card1.setBounds(0, 0, REDUCED_CARD_WIDTH, REDUCED_CARD_HEIGHT);
		card2.setBounds(0, 0, REDUCED_CARD_WIDTH, REDUCED_CARD_HEIGHT);
		
		playerSplitCards1.add(card1, 0);
		playerSplitCards2.add(card2, 0);
		
		if(splitCardValue == 1) {
			playerScore.setText(splitCardValue+" or "+(splitCardValue+10));
			playerSplitScore.setText(splitCardValue+" or "+(splitCardValue+10));
		}
		else {
			playerScore.setText(String.valueOf(splitCardValue));
			playerSplitScore.setText(String.valueOf(splitCardValue));
		}
		
		betSplitAmount.setText("$"+splitBet);
		this.add(playerSplitCards1);
		this.repaint();
		
		Thread.sleep(1000);
		
		balanceAmount.setText("$"+balance);
		this.add(playerSplitCards2);
		this.add(betSplitLabel);
		this.add(playerSplitLabel);
		this.add(playerSplitScore);
		this.add(betSplitAmount);
		
		this.repaint();
		
		Thread.sleep(1000);
		
		this.add(playerSplitArrow1);
		
	}
	
	
	public void paintPlayerSplitCard(Card playerCard, boolean hasFinishedHand, boolean splitAces) throws InterruptedException {		//splitHand = 1 if fist hand is being played, 2 if second hand

		JLabel card = new JLabel(new ImageIcon(playerCard.cardImage.getScaledInstance(REDUCED_CARD_WIDTH, REDUCED_CARD_HEIGHT, Image.SCALE_SMOOTH)));
		
		Thread.sleep(1000);
		
		if(hasFinishedHand) {
			card.setBounds((REDUCED_CARD_GAP*(playerSplit.hand.size()-1)), 0, REDUCED_CARD_WIDTH, REDUCED_CARD_HEIGHT);
			playerSplitCards2.add(card, 0);
			
			if(playerSplit.hasFullHand())
				playerSplitScore.setText("21");
			else if(playerSplit.hasAce() && playerSplit.getHandValue() < 11)
				playerSplitScore.setText(playerSplit.getHandValue()+" or "+(playerSplit.getHandValue()+10));
			else {
				playerSplitScore.setText(String.valueOf(playerSplit.getHandValue()));
				if(playerSplit.getHandValue() > 21)
					this.add(playerSplitBust);
			}
			
		}
		
		else {
			card.setBounds((REDUCED_CARD_GAP*(player.hand.size()-1)), 0, REDUCED_CARD_WIDTH, REDUCED_CARD_HEIGHT);
			playerSplitCards1.add(card, 0);
			
			if(player.hasFullHand())
				playerScore.setText("21");
			else if(player.hasAce() && player.getHandValue() < 11)
				playerScore.setText(player.getHandValue()+" or "+(player.getHandValue()+10));
			else {
				playerScore.setText(String.valueOf(player.getHandValue()));
				if(player.getHandValue() > 21)
					this.add(playerBust);
			}
			
		}
		
		this.repaint();
		
	}
	
	
	public void paintHandScore(boolean hasSplit, boolean hasFinishedHand) {
		
		if(hasFinishedHand) {
			if(playerSplit.hasAce())
				playerSplitScore.setText(String.valueOf(playerSplit.getFinalHandValue()));
			this.remove(playerSplitArrow2);
		}
		else {
			if(player.hasAce())
				playerScore.setText(String.valueOf(player.getFinalHandValue()));
			if(hasSplit)
				switchSplitArrow();
		}
		
		this.repaint();
	}
	
	
	private void switchSplitArrow() {
		
		this.remove(playerSplitArrow1);
		this.add(playerSplitArrow2);
		this.repaint();
		
	}
	

	public void paintDealerCards(Iterator<Card> dealerCardsIt) throws InterruptedException {
		
		Hand tempDealer = new Hand(dealer.hand.get(0), dealer.hand.get(1));
		
		Thread.sleep(1000);
		
		dealerCards.remove(cardBack);
		card = new JLabel(new ImageIcon(dealer.hand.get(1).cardImage));
		card.setBounds(CARD_GAP, 0, CARD_WIDTH, CARD_HEIGHT);
		dealerCards.add(card, 0);

		if(tempDealer.hasAce() && tempDealer.getHandValue() >= 7 && tempDealer.getHandValue() <= 11)
			dealerScore.setText(String.valueOf(tempDealer.getHandValue()+10));
		else if(tempDealer.hasAce() && tempDealer.getHandValue() < 7)
			dealerScore.setText(tempDealer.getHandValue()+" or "+(tempDealer.getHandValue()+10));
		else
			dealerScore.setText(String.valueOf(tempDealer.getHandValue()));
		
		while(dealerCardsIt.hasNext()) {
			Thread.sleep(1000);
			
			Card dealerCard = dealerCardsIt.next();
			tempDealer.addCard(dealerCard);
			
			card = new JLabel(new ImageIcon(dealerCard.cardImage));
			card.setBounds((CARD_GAP*(tempDealer.hand.size()-1)), 0, CARD_WIDTH, CARD_HEIGHT);
			dealerCards.add(card, 0);
			
			if(tempDealer.hasAce() && tempDealer.getHandValue() >= 7 && tempDealer.getHandValue() <= 11)
				dealerScore.setText(String.valueOf(tempDealer.getHandValue()+10));
			else if(tempDealer.hasAce() && tempDealer.getHandValue() < 7)
				dealerScore.setText(tempDealer.getHandValue()+" or "+(tempDealer.getHandValue()+10));
			else {
				dealerScore.setText(String.valueOf(tempDealer.getHandValue()));
				if(tempDealer.getHandValue() > 21)
					this.add(dealerBust);
			}
		}
		
	}
	
	
	public void paintRoundEnd(int result, int balance, boolean hasSplit) throws InterruptedException {		//1 = round won; 2 = round lost; 3 = tie (push)
		
		roundEnd.removeAll();
		
		switch(result) {
		
		case 1: 
			if(player.hasBlackjack())
				money.setText("$"+(int)(bet*2.5));
			else
				money.setText("$"+(bet*2));
			
			roundEnd.add(win);
			roundEnd.add(money);
			
			for(int i = 0; i < betAmounts.size(); i++) {		//enable any bets you can now make with your new balance
				JButton betButton = betAmounts.get(i);
				if(Integer.parseInt(betButton.getText().substring(1)) <= balance && !betButton.isEnabled())
					betButton.setEnabled(true);
			}
			
			break;
			
		case 2: 
			roundEnd.add(lose);
			break;
		
		case 3: 
			money.setText("$"+bet);
			roundEnd.add(push);
			roundEnd.add(money);
			break;
		
		}
		
		Thread.sleep(2000);
		
		balanceAmount.setText("$"+balance);
		this.add(roundEnd);
		this.repaint();
		retard();
		
		Thread.sleep(3000);
		
		playerCards.removeAll();
		dealerCards.removeAll();
		playerScore.setText("0");
		dealerScore.setText("0");
		betAmount.setText("$0");
		
		this.remove(roundEnd);
		this.remove(playerBust);
		this.remove(dealerBust);
		
		this.repaint();
		
	}
	
	public void paintRoundEnd(int mainResult, int splitResult, int balance) throws InterruptedException {		//used for split rounds
		
		this.remove(playerSplitArrow2);
		roundEnd.removeAll();
		splitRoundResult.setText("");
		
		int profit = 0;
		
		switch(mainResult) {
		
		case 1: 
			if(player.hasBlackjack())
				profit += (int)bet*2.5;
			else
				profit += bet*2;
			
			splitRoundResult.setText("Win/");
			
			break;
			
		case 2: 
			splitRoundResult.setText("Loss/");
			break;
		
		case 3: 
			profit += bet;
			splitRoundResult.setText("Push/");
			break;
		
		}
		
		
		String mainHandResult = splitRoundResult.getText();
		
		switch(splitResult) {
		
		case 1: 
			if(playerSplit.hasBlackjack())
				profit += (int)bet*2.5;
			else
				profit += bet*2;
			
			splitRoundResult.setText(mainHandResult+"Win");
			
			break;
			
		case 2: 
			splitRoundResult.setText(mainHandResult+"Loss");
			break;
		
		case 3: 
			profit += bet;
			splitRoundResult.setText(mainHandResult+"Push");
			break;
	
		}
		
		money.setText("$"+profit);
		
		roundEnd.add(splitRoundResult);
		if(!splitRoundResult.getText().equals("Loss/Loss"))
			roundEnd.add(money);
		
		
		for(int i = 0; i < betAmounts.size(); i++) {		//enable any bets you can now make with your new balance
			JButton betButton = betAmounts.get(i);
			if(Integer.parseInt(betButton.getText().substring(1)) <= balance && !betButton.isEnabled())
				betButton.setEnabled(true);
		}

		
		Thread.sleep(2000);
		
		balanceAmount.setText("$"+balance);
		this.add(roundEnd);
		retard();
		this.repaint();
		
		Thread.sleep(3000);
		
		playerCards.removeAll();
		dealerCards.removeAll();
		playerSplitCards1.removeAll();
		playerSplitCards2.removeAll();
		playerScore.setText("0");
		dealerScore.setText("0");
		betAmount.setText("$0");
		
		this.remove(roundEnd);
		this.remove(playerBust);
		this.remove(dealerBust);
		this.remove(playerSplitBust);
		this.remove(playerSplitLabel);
		this.remove(playerSplitScore);
		this.remove(betSplitLabel);
		this.remove(betSplitAmount);
		this.remove(playerSplitCards1);
		this.remove(playerSplitCards2);
		
		this.repaint();
	}
	
	
	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == startButton)
			wn.doNotify();
		
		
		if(e.getSource() == deckAmount1) {
			numDecks = 1;
			wn.doNotify();
		}
		
		if(e.getSource() == deckAmount2) {
			numDecks = 2;
			wn.doNotify();
		}
		
		if(e.getSource() == deckAmount4) {
			numDecks = 4;
			wn.doNotify();
		}
		
		if(e.getSource() == deckAmount6) {
			numDecks = 6;
			wn.doNotify();
		}
		
		if(e.getSource() == deckAmount8) {
			numDecks = 8;
			wn.doNotify();
		}
		
		
		if(e.getSource() == betAmount100) {
			bet = 100;
			wn.doNotify();
		}
		
		if(e.getSource() == betAmount500) {
			bet = 500;
			wn.doNotify();
		}
		
		if(e.getSource() == betAmount1000) {
			bet = 1000;
			wn.doNotify();
		}
		
		if(e.getSource() == betAmount1500) {
			bet = 1500;
			wn.doNotify();
		}
		
		if(e.getSource() == betAmount5000) {
			bet = 5000;
			wn.doNotify();
		}
		
		if(e.getSource() == betAmount10000) {
			bet = 10000;
			wn.doNotify();
		}
		
		if(e.getSource() == hit) {
			playerChoice = 1;
			wn.doNotify();
		}
		
		if(e.getSource() == stand) {
			playerChoice = 2;
			wn.doNotify();
		}
		
		if(e.getSource() == doubleDown) {
			playerChoice = 3;
			wn.doNotify();
		}
		
		if(e.getSource() == split) {
			playerChoice = 4;
			wn.doNotify();
		}
		
		if(e.getSource() == surrender) {
			playerChoice = 0;
			wn.doNotify();
		}
		
	}
	
	
	private void retard() {
		this.setSize(1000, 999);			
		this.setSize(SCREEN_SIZE);	
	}

	
}
