package Modele;

import java.util.*;

public class Partie {

	private static Partie p=null;
	private ArrayList<JoueurAbs> listJ;
	private static JeuCarte jeu;
	private ArrayList<Carte> discardedCards;
	private ArrayList<Carte> revealedCards;
	private static int numberOfPlayers;
	
	/**
	* This is the public class constructor which implements singleton conception to
	* ensure that only one instance of game is created at one time
	*
	*/
	public static Partie getInstance() {
		
		if (p == null) {
			p = new Partie();	
			jeu = new JeuCarte();
			initialise();
		}
		return p;
	}
	
	/**
	* This is the private class constructor which initializes an empty list of players and cards
	*
	*/
	private Partie(){
		listJ = new ArrayList<JoueurAbs>();
		discardedCards = new ArrayList<Carte>();
		revealedCards = new ArrayList<Carte>();
	}
	
	/**
	* Initializes number of players and name for each one 
	*
	*/
	public static void initialise() {
		System.out.println("A game is initialized.");
		System.out.println("Number of players : ");
		
		Scanner keyboard = new Scanner(System.in);
		int reponseC = keyboard.nextInt();
		numberOfPlayers = reponseC;
		
		if(numberOfPlayers>2 && numberOfPlayers<7) {
		
			for(int i=0;i<numberOfPlayers;i++){
				System.out.println("Name of player"+i+" :");
				keyboard = new Scanner(System.in);
				String playerName = keyboard.next();
				Joueur j=new Joueur(playerName);
				p.getListPlayer().add(j);
			}
			p.displayPlayer();
		}
		else {
			System.out.println("3-6 players are required to launch a game.");
			initialise();
		}
	}
	
	/**
	* Displays list of players 
	*
	*/
	public void displayPlayer() {
		System.out.println("Here is a list of players.");
		for(JoueurAbs j:listJ) {
			System.out.println(j.getName());
		}
		System.out.println();
	}
	
	/**
	* Displays list of cards
	* 
	* @param listOfCards could be list of cards in hand or revealed
	*
	*/
	public void displayCards(ArrayList<Carte> listOfCards) {
		System.out.println("Here is a list of cards available to choose from.");
		for(Carte c:listOfCards) {
			System.out.println(c.getCardName());
		}
		System.out.println();
	}	
	
	/**
	* Returns list of players in the game
	* 
	* @return list of players
	*
	*/
	public ArrayList<JoueurAbs> getListPlayer(){
		return listJ;
	}
	
	/**
	* Distribute cards to players according to number of players <br>
	* When the tas card is empty, we know that all cards have been distributed
	* 
	*
	*/
	public void distributeCards(){
		System.out.println("Distributing cards ......");
		jeu.melanger();
		
		if(numberOfPlayers==5) {
			discardedCards.add(jeu.distribuerUneCarte());
			discardedCards.add(jeu.distribuerUneCarte());
		}
		
		while (!jeu.estVide()){
					Iterator<JoueurAbs> it = listJ.iterator();
					while(it.hasNext() && !jeu.estVide()){
						JoueurAbs j = it.next();
						j.retrieveCard(jeu.distribuerUneCarte());
					}
		}
		System.out.println("Finished!");
		System.out.println();
	}
	
	/**
	* Each player in the list takes turn to choose their role
	*/
	public void chooseRole() {
		Iterator<JoueurAbs> it = listJ.iterator();
		while(it.hasNext()){
			
			JoueurAbs j = it.next();
			j.displayCards(j.getCards());
			
			System.out.println();
			System.out.println(j.getName());
			
			String reponse = getStringFromKeyboard();
			
			if(reponse.contains("W") || reponse.contains("w")) {
				j.setIdentity(true);
			}
			
			else if(reponse.contains("V") || reponse.contains("v")) {
				j.setIdentity(false);
			}
		}
	}
	
	/**
	* Checks if the round has a winner, if yes, announce the winner and stop the game, if no, continue to play
	*/
	public JoueurAbs winnerFound() {
		int numberOfPlayersWithScore5=0;
		JoueurAbs winner = null;
		
		Iterator<JoueurAbs> it = listJ.iterator();
		while(it.hasNext()){
			JoueurAbs j = it.next();
			if (j.getScore()>=5) {
				numberOfPlayersWithScore5++;
				winner=j;
			}
		}
		if(numberOfPlayersWithScore5==1) {
			return winner;
		}
		
		return winner;
	}
	
	/**
	* Player play. Choose whether to accuse someone or to play a card.
	*/
	public void lancer(int indexJoueur) {
		
		while((listJ.size()>1) && (winnerFound()==null)) {
		
			JoueurAbs currentj = listJ.get(indexJoueur);
			System.out.println();

			System.out.println("Hey, its your turn, "+currentj.getName());
			System.out.println("To accuse someone, type A; to reveal a card, type B");
			
			String r = getStringFromKeyboard();
			
			if(r.contains("A") || r.contains("a")) {
				p.displayPlayer();
				System.out.println("You wish to accuse someone.");
				int reponseA = askWhichOne(2);
				lancerAccused(reponseA,currentj);
			}
			
			else if (r.contains("B") || r.contains("b")) {
				currentj.displayCards(currentj.getCards());
				System.out.println("Type the index of card that you wish to reveal.");
				int index = askWhichOne(2);
				Carte c = currentj.revealCard(index,this);
				int indexNextPlayer = c.resolveHunt(currentj,this);
				lancer(indexNextPlayer);
			}		
		}
		
		if(listJ.size()==1) {
			System.out.println(listJ.get(0).getName()+" wins! Congratulations!");
		}
		else if(winnerFound()!=null) {
			System.out.println(winnerFound().getName()+" wins! Congratulations!");
		}
		
	}
	
	/**
	* Lance a session to accuse someone. The accused respond to this accusation.
	*/
	public void lancerAccused(int indexAccused,JoueurAbs playerWhoAccused) {
		JoueurAbs accusedj = listJ.get(indexAccused);
		int indexAccuser = listJ.indexOf(playerWhoAccused);
		
		System.out.println();
		System.out.println("Oops "+accusedj.getName()+", someone has accused you!");
		System.out.println("To reveal your identity, type A; to reveal a card, type B");
		
		String reponse = getStringFromKeyboard();
		
		if(reponse.contains("A")||reponse.contains("a")) {                                                                                                                                               
			boolean witch = accusedj.revealIdentity(playerWhoAccused,false);
			if(witch) {
				listJ.remove(indexAccused);
				playerWhoAccused.addScore(1);
			}
			else {
				lancer(indexAccused);
			}
			respondToAccusedRevealIdentity(indexAccuser, indexAccused);
		}
		
		else if(reponse.contains("b")||reponse.contains("B")) {
			accusedj.displayCards(accusedj.getCards());
			System.out.println("Reveal a card : ");
			
			int reponseC = askWhichOne(1);
			
			Carte c = accusedj.revealCard(reponseC,this);
			accusedj.getRevealedCards().add(c);
			p.getRevealedCards().add(c);
			int indexNextPlayer=c.resolveWitch(accusedj,playerWhoAccused,this);
			lancer(indexNextPlayer);
		}
	}

	/**
	* Get the discarded cards in the round
	*
	*@return list of discarded cards
	*/
	public ArrayList<Carte> getDiscardedCards() {
		return discardedCards;
	}
	
	/**
	* Get the revealed cards in the round
	*
	*@return list of revealed cards
	*/
	public ArrayList<Carte> getRevealedCards() {
		return revealedCards;
	}

	/**
	* Get jeu
	*
	*@return jeu
	*/
	public static JeuCarte getJeu() {
		return jeu;
	}
	
	/**
	* Get the number of players in the round
	*
	*@return number of players
	*/
	public static int getNumberOfPlayers() {
		return numberOfPlayers;
	}	
	
	/**
	* Set the number of players in the round
	*
	*@param numberOfPlayers number of players
	*/

	public static void setNumberOfPlayers(int numberOfPlayers) {
		Partie.numberOfPlayers = numberOfPlayers;
	}

	/**
	* Output in the terminal to ask which player/card to choose 
	* Input collected for further actions
	* @param i index of selected item (player/card)
	* return index
	*/
	public int askWhichOne(int i) {
		System.out.println();
		if(i==1) {
			System.out.println("Type the index of a card : ");
		}
		else if(i==2) {
			System.out.println("Type the index of a player : ");
		}
		Scanner keyboard = new Scanner(System.in);
		int reponseC = keyboard.nextInt();
		return reponseC;
	}
	
	/**
	* Scanner to get keyboard input from players
	*/
	public String getStringFromKeyboard() {
		Scanner keyboard = new Scanner(System.in);
		return keyboard.next();
	}

	/**
	* Output in the terminal to ask which player/card to choose 
	* Input collected for further actions
	* @param i index of selected item (player/card)
	* return index
	*/
	public JoueurAbs searchPlayerByName(String name) {
		JoueurAbs player=null;
		for(JoueurAbs j:p.getListPlayer()) {
			if(j.getName().equals(name)) player=j;
		}
		return player;
	}
	
	/**
	* After the session of accusations, if the accused is a witch, remove the accused from player's list
	* as the accused has lost the game, then give points to the accuser <br>
	* If the accused is a villager, player to the left takes next turn
	* 
	* @param indexAccused index of player
	* @param indexAccuser index of player
	*/
	public void respondToAccusedRevealIdentity(int indexAccused, int indexAccuser) {

		JoueurAbs playerBeingAccused = listJ.get(indexAccused);
		JoueurAbs playerWhoAccused = listJ.get(indexAccuser);
		
		//if player is a witch
		if(playerBeingAccused.getIdentity()) {
			listJ.remove(playerBeingAccused);
			playerBeingAccused.setIdentityRevealed(true);
			playerWhoAccused.addScore(1);
		}
		//if player is a villager
		else {
			p.lancer(p.getListPlayer().indexOf(playerBeingAccused));
		}
		
	}
	
	/**
	* Iterates through list of cards and remove a card passed by parameter from the list
	* @param list list of cards
	* @param c a card
	*/
	/*public void removeCard(Carte c,ArrayList<Carte> list) {
		ListIterator<Carte> iter = list.listIterator();
		while(iter.hasNext()){
		    if(iter.next().equals(c)){
		        iter.remove();
		    }
		}
	}*/


}

