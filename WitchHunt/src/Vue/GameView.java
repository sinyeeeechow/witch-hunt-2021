package Vue;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import Controller.*;

import javax.swing.*;

public class GameView {

	public JFrame frame;
	private gameController controller;
	//public JLabel nameAccused,nameAccuser,nameCurrentPlayer;
	private int indexCurrentPlayer, indexAccused,indexAccuser;
	public JButton btnEnter,btnPlayer,btnChooseCard,btnChooseRole,btnStart,btnPlay,btnPlay2,btnAccuse,btnNextPlayer,btnNextPlayerRMI,btnRechoose;
	public JButton btnTakeCard,btnRevealCard, btnDiscardCard,btnTakeCardFromAccuser,btnTakeCardFromOthers,btnTakeCardFromPartieRevealed,btnTakeCardFromPartieDiscarded;
	public JTextField username1,username2,username3,username4,username5,username6;
	public JRadioButton bot1,bot2,bot3,bot4,bot5,bot6;
	public JRadioButton witch,villager;
	public JRadioButton accuse,resolveHunt,revealIdentity,resolveWitch;
	public JComboBox ansNumberPlayer,ansCard,ansPlayer;
	public ButtonGroup btnR;
	/**
	 * Launch the application
	 * @param args parameters of main
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameView window = new GameView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * This is a constructor which creates the application and initializes a controller
	 */
	public GameView() {
		controller = new gameController(this);
		this.initialize();
		indexCurrentPlayer=0;
		indexAccused=0;
		indexAccuser=0;
	}
	
	/**
	 * @return index of current player
	 */
	public int getIndexCurrentPlayer() {
		return indexCurrentPlayer;
	}

	/**
	 * @param indexCurrentPlayer index of current player
	 */
	public void setIndexCurrentPlayer(int indexCurrentPlayer) {
		this.indexCurrentPlayer = indexCurrentPlayer;
	}

	/**
	 * @return index of current player
	 */
	public int getIndexAccused() {
		return indexAccused;
	}

	/**
	 * @param indexAccused index of accused player
	 */
	public void setIndexAccused(int indexAccused) {
		this.indexAccused = indexAccused;
	}
	
	/**
	 * @return index of accused player
	 */
	public int getIndexAccuser() {
		return indexAccuser;
	}

	/**
	 * @param indexAccuser index of accuser
	 */
	public void setIndexAccuser(int indexAccuser) {
		this.indexAccuser = indexAccuser;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    final JLabel label = new JLabel();          
	    label.setIcon(new ImageIcon("C:\\Users\\sinyee\\Desktop\\WitchHunt\\img\\cover.PNG"));

		JPanel panelInit = new JPanel();
		
		JLabel ask1 = new JLabel();
		ask1.setText("Number of players?");
		
		Integer numberOfPlayers[] = {3,4,5,6};
		ansNumberPlayer = new JComboBox(numberOfPlayers);
		
		btnEnter = new JButton("Enter");  
		
		btnEnter.addActionListener(controller);       

		panelInit.add(ask1);
		panelInit.add(ansNumberPlayer);
		panelInit.add(btnEnter);
		panelInit.add(label);
		
		frame.getContentPane().add(panelInit);
	}
	
	/**
	 * Allows player to choose name
	 * @param number number of players
	 * @return JPanel java container 
	 */
	public JPanel initializePlayer(int number) {

			JPanel jPan = new JPanel();
			
			username1 = new JTextField(20);username1.setBounds(100,100,70,20);jPan.add(username1);		
			bot1 = new JRadioButton("Bot"); jPan.add(bot1);
			
			username2 = new JTextField(20);username2.setBounds(100,200,70,20);jPan.add(username2);
			bot2 = new JRadioButton("Bot"); jPan.add(bot2);
			
			username3 = new JTextField(20);username3.setBounds(100,300,70,20);jPan.add(username3);		
			bot3 = new JRadioButton("Bot"); jPan.add(bot3);
			
			username4 = new JTextField(20);username4.setBounds(100,400,70,20);jPan.add(username4);
			username4.setVisible(false);
			bot4 = new JRadioButton("Bot"); jPan.add(bot4);
			bot4.setVisible(false);
			
			username5 = new JTextField(20);username5.setBounds(100,500,70,20);jPan.add(username5);
			username5.setVisible(false);
			bot5 = new JRadioButton("Bot");jPan.add(bot5);
			bot5.setVisible(false);
			
			username6 = new JTextField(20);username6.setBounds(100,600,70,20);jPan.add(username6);
			username6.setVisible(false);
			bot6 = new JRadioButton("Bot");jPan.add(bot6);
			bot6.setVisible(false);
			
			if(number>3) {
				if(number>=4) {
					username4.setVisible(true);
					bot4.setVisible(true);
		
					if(number>=5) {
						username5.setVisible(true);
						bot5.setVisible(true);
						
						if(number>=6) {
							username6.setVisible(true);
							bot6.setVisible(true);
						}
					}
				}
			}
			
			btnPlayer = new JButton("Enter");
			jPan.add(btnPlayer);
			btnPlayer.addActionListener(controller);       
			
			return jPan;

	}
	
	/**
	 * Allows players to choose role based on their cards
	 * @param index index of current player
	 * @return JPanel java container
	 */
	public JPanel chooseRole(int index) {
		JPanel jPan = controller.getYourCards(index);
		indexCurrentPlayer = index;
		
		ButtonGroup b = new ButtonGroup();
		witch = new JRadioButton("Witch");
		villager = new JRadioButton("Villager");
		b.add(witch);b.add(villager);
		
		btnChooseRole = new JButton("Next");
		btnStart = new JButton("startGame");btnStart.setVisible(false);
		jPan.add(villager);jPan.add(witch);jPan.add(btnChooseRole);jPan.add(btnStart);
		btnChooseRole.addActionListener(controller);
		btnStart.addActionListener(controller);
		return jPan;
	}
	
	/**
	 * Allows players to choose whether to accuse someone or play cards
	 * @param index index of player
	 * @return JPanel java container
	 */
	public JPanel playerInterface1(int index) {
		
		JPanel jPan = new JPanel();
		indexCurrentPlayer = index;
		
		String playerName = controller.getPlayer(index).getName();
		JLabel l = new JLabel("Hey, "+playerName+", it's your turn");
		
		ButtonGroup btn = new ButtonGroup();
		accuse = new JRadioButton("Accuse Someone");
		resolveHunt = new JRadioButton("Play the hunt effect");
		btn.add(accuse);btn.add(resolveHunt);
		
		btnPlay = new JButton("Next");
		jPan.add(l);jPan.add(accuse);jPan.add(resolveHunt);jPan.add(btnPlay);
		btnPlay.addActionListener(controller);
		return jPan;
	}
	
	/**
	 * Allows players to respond to an accusation by revealing identity or play cards
	 * @param index index of player
	 * @return JPanel java container
	 */
	public JPanel playerInterface2(int index) {
		
		JPanel jPan = new JPanel();
		indexAccuser = indexCurrentPlayer;
		indexAccused = index;
		
		String playerName = controller.getPlayer(indexAccused).getName();
		JLabel l = new JLabel("Hey, "+playerName+", your're being accused, do something!");
		
		ButtonGroup btn = new ButtonGroup();
		revealIdentity = new JRadioButton("Reveal My Identity");
		resolveWitch = new JRadioButton("Play the witch effect");
		btn.add(revealIdentity);btn.add(resolveWitch);
		
		btnPlay2 = new JButton("Next");
		jPan.add(l);jPan.add(revealIdentity);jPan.add(resolveWitch);jPan.add(btnPlay2);
		btnPlay2.addActionListener(controller);
		return jPan;
	}
	
	/**
	 * Allows players to choose a card
	 * @param index index of player
	 * @param wOrh witch or hunt effect of the card
	 * @param typeCard cards in your hand or in others' hand, revealed or discarded
	 * @return JPanel java container
	 */
	public JPanel playerInterfaceChooseCard(int index,int wOrh,int typeCard) {
		JPanel jPan = controller.getYourCards(index);
		indexCurrentPlayer = index;
		
		ArrayList<String> listCn = controller.getPlayer(indexCurrentPlayer).getCardsName();
		
		ansCard = new JComboBox(listCn.toArray());
		jPan.add(ansCard);
		
		btnChooseCard = new JButton("Next");
		jPan.add(ansCard);jPan.add(btnChooseCard);
		btnChooseCard.addActionListener(controller);
		
		return jPan;
	}
	
	/**
	 * Allows players to choose a card from another player
	 * @param index index of current player
	 * @param indexSelectedPlayer index of selected player
	 * @param wOrh witch or hunt effect of the card
	 * @param typeCard cards in your hand or in others' hand, revealed or discarded
	 * @return JPanel java container
	 */
	public JPanel playerInterfaceChooseCardFromSelectedPlayer(int index,int indexSelectedPlayer,int wOrh,int typeCard) {
		JPanel jPan = controller.getYourCards(index);
		
		indexCurrentPlayer = index;

		ArrayList<String> listCn = controller.getPlayer(indexSelectedPlayer).getCardsName();
		
		ansCard = new JComboBox(listCn.toArray());
		jPan.add(ansCard);
		
		btnChooseCard = new JButton("Next");
		jPan.add(ansCard);jPan.add(btnChooseCard);
		btnChooseCard.addActionListener(controller);
		
		return jPan;
	}
	
	/**
	 * Allows players to choose a card to discard
	 * @param index index of current player
	 * @param indexSelectedPlayer index of selected player
	 * @param wOrh witch or hunt effect of the card
	 * @param typeCard cards in your hand or in others' hand, revealed or discarded
	 * @return JPanel java container
	 */
	public JPanel playerInterfaceDiscardACard(int index,int indexSelectedPlayer,int wOrh,int typeCard) {
		JPanel jPan = controller.getYourCards(index);
		
		indexCurrentPlayer = index;

		ArrayList<String> listCn = controller.getPlayer(indexSelectedPlayer).getCardsName();
		
		ansCard = new JComboBox(listCn.toArray());
		jPan.add(ansCard);
		
		btnDiscardCard = new JButton("Discard this card");
		jPan.add(ansCard);jPan.add(btnDiscardCard);
		btnDiscardCard.addActionListener(controller);
		
		return jPan;
	}
	
	/**
	 * Allows players to take a card and add it to his hand
	 * @param index index of current player
	 * @param indexSelectedPlayer index of selected player
	 * @param wOrh witch or hunt effect of the card
	 * @param typeCard cards in your hand or in others' hand, revealed or discarded
	 * @return JPanel java container
	 */
	public JPanel playerInterfacerTakeACard(int index,int indexSelectedPlayer,int wOrh,int typeCard) {
		JPanel jPan = controller.getYourCards(index);
		
		indexCurrentPlayer = index;

		ArrayList<String> listCn = controller.getPlayer(indexSelectedPlayer).getCardsName();
		
		ansCard = new JComboBox(listCn.toArray());
		jPan.add(ansCard);
		
		btnTakeCard = new JButton("Take this card");
		jPan.add(ansCard);jPan.add(btnTakeCard);
		btnTakeCard.addActionListener(controller);
		
		return jPan;
	}
	
	/**
	 * Allows players to reveal a card
	 * @param index index of current player
	 * @param indexSelectedPlayer index of selected player
	 * @param wOrh witch or hunt effect of the card
	 * @param typeCard cards in your hand or in others' hand, revealed or discarded
	 * @return JPanel java container
	 */
	public JPanel playerInterfaceRevealACard(int index,int indexSelectedPlayer,int wOrh,int typeCard) {
		JPanel jPan = controller.getYourCards(index);
		
		indexCurrentPlayer = index;

		ArrayList<String> listCn = controller.getPlayer(indexSelectedPlayer).getCardsName();
		
		ansCard = new JComboBox(listCn.toArray());
		jPan.add(ansCard);
		
		btnRevealCard = new JButton("Reveal this card");
		jPan.add(ansCard);jPan.add(btnRevealCard);
		btnRevealCard.addActionListener(controller);
		
		return jPan;
	}

	/**
	 * Allows players to respond when his identity is being accused
	 * 
	 * @return JPanel java container
	 */
	public JPanel playerInterfaceRevealIdentity() {
		JPanel jPan= controller.revealIdentity();
		btnNextPlayer = new JButton("Next"); 
		btnNextPlayer.addActionListener(controller);
		jPan.add(btnNextPlayer);
		return jPan;
	}
	
	/**
	 * Allows players to reveal his own identity
	 * 
	 * @return JPanel java container
	 */
	public JPanel playerInterfaceRevealMyIdentity() {
		JPanel jPan= controller.revealMyIdentity();
		btnNextPlayerRMI = new JButton("Next"); 
		btnNextPlayerRMI.addActionListener(controller);
		jPan.add(btnNextPlayerRMI);
		return jPan;
	}
	
	/**
	 * Allows players to re-choose a card when the previous card couldn't be played
	 * 
	 * @return JPanel java container
	 */
	public JPanel playerInterfaceRechooseCard() {
		JPanel jPan = controller.getYourCards(indexCurrentPlayer);
		JLabel l = new JLabel("You cannot pick this card. Please choose another card");
		jPan.add(l);
		btnRechoose = new JButton("Rechoose");
		btnRechoose.addActionListener(controller);
		jPan.add(btnRechoose);
		return jPan;
	}
	
	/**
	 * Allows players to accuse someone
	 * @param index index of current player
	 * 
	 * @return JPanel java container
	 */
	public JPanel playerInterfaceAccuseSomeone(int index) {
		this.setIndexAccuser(indexCurrentPlayer);
		
		JPanel jPan = playerInterfaceChoosePlayer(index);
		String playerName = controller.getPlayer(index).getName();
		JLabel l = new JLabel("Hey, "+playerName+", you choose to accuse someone, please select a suspected witch");
		jPan.add(l);
		
		btnAccuse = new JButton("Next");
		btnAccuse.addActionListener(controller);jPan.add(btnAccuse);
		return jPan;
	}
	
	/**
	 * Allows players to choose next player 
	 * 
	 * @return JPanel java container
	 */
	public JPanel playerInterfaceChooseNextPlayer() {
		JPanel jPan = playerInterfaceChoosePlayer(indexCurrentPlayer);
		JLabel l = new JLabel("Please choose next player");
		jPan.add(l);
		
		btnNextPlayer = new JButton("Next");
		btnNextPlayer.addActionListener(controller);
		jPan.add(btnNextPlayer);
		return jPan;
	}
	
	public JPanel playerInterfaceChoosePlayer(int index) {
		JPanel jPan = new JPanel();
		ArrayList<String> listJa = controller.getListsOfPlayersToAccuse(index);
		ansPlayer = new JComboBox(listJa.toArray());
		jPan.add(ansPlayer);
		
		return jPan;
	}
	
	
}
