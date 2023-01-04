package Modele;
import java.util.ArrayList;

public class Bot extends JoueurAbs {
	
	private String name;
	private int score;
	private int order;
	//true if witch,false if villager
	private boolean identity;
	private boolean identityRevealed;
	private ArrayList<Carte> cards;
	private ArrayList<Carte> revealedCards;

/**
* This is the class constructor which attribute name to a bot
*
* @param  name an enumeration of name 
*/
	public Bot(String name) {
		super(name);
	}
	
/**
* Get the name of this bot
* 
* @return a name 
*/	
	public String getName() {
		return super.getName();
	}
	
/**
* Get list of cards in hand
* 
* @return list of cards in hand
*/		
	public ArrayList<Carte> getCards(){
		return super.getCards();
	}
	
/**
* Get list of cards that has been revealed
* 
* @return list of revealed cards 
*/
	public ArrayList<Carte> getRevealedCards(){
		return super.getRevealedCards();
	}
	
/**
* Generate a random number from a given range
* 
* @param min minumum value
* @param max maximum value
* @return a random number
*/
	public int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}

/**
* Take a card held by another player
* 
* @param anotherj a selected player whom the current player wishes to take his card from
* @param p game
*/
	public void takeACardInSomeonesHand(JoueurAbs anotherj,Partie p) {
		anotherj.displayCards(anotherj.getCards());
		int index=getRandomNumber(0,anotherj.getCards().size()-1);
		super.takeACardInSomeonesHand(anotherj, index,p);
	}
	
/**
* Take a card that has been revealed into the round
* 
* @param p game
*/	
	public void takeOthersRevealedCard(Partie p){
		p.displayCards(p.getRevealedCards());
		int index=getRandomNumber(0,p.getRevealedCards().size()-1);
		super.takeOthersRevealedCard(p, index);
	}

/**
* Take your own revealed card 
* 
* @param p game
*/	
	public void takeYourRevealedCard(Partie p) {
		if(!this.getRevealedCards().isEmpty()) {
			System.out.println("You can retrieve one of your own revealed cards.");
			this.displayCards(this.getRevealedCards());
			int index=getRandomNumber(0,this.getRevealedCards().size()-1);
			super.takeYourRevealedCard(p);
		}
		else {
			System.out.println("You couldn't use the witch effect of this card, as you haven't revealed any card.");
			p.lancer(p.getListPlayer().indexOf(this));
		}
	}

/**
* Determines automatically which player will take next turn, either naturally or being selected  by others
* 
*/	
	public void takeNextTurn() {
		super.takeNextTurn();
	}

/**
* Throw a card from your hand to the round
* 
* @param p game
*/	
	public void discardYourCard(Partie p) {
		this.displayCards(this.getCards());
		int index=getRandomNumber(0,this.getCards().size()-1);
		super.discardYourCard(p, index);
	}

/**
* Choose a player from the list to play
* 
* @param listJ list of players
* @param p game
* @return int player's index
*/	
	public int chooseNextPlayer(ArrayList<JoueurAbs> listJ,Partie p) {
		System.out.println();
		for(JoueurAbs j:listJ) {
			System.out.println(j.getName());
		}
			return getRandomNumber(0,listJ.size()-1);
	}
	
/**
* Reveal the identity of other player
* 
* @param listJ list of players
* @param secret boolean that tells if this is a secret reveal or not 
* @param p game
*/
	public void revealIdentityOfOthers(ArrayList<JoueurAbs> listJ,boolean secret,Partie p) {
		int index=getRandomNumber(0,listJ.size()-1);
		super.revealOthersIdentity(index, listJ, secret);
	}

	@Override
	public int revealYourIdentity(ArrayList<JoueurAbs> listJ, Partie p) {
			//Choose next player if i am a villager
			//if(!this.getIdentity()) 
				//return getRandomNumber(0,listJ.size()-1);
			//else {
				//Choose player to your left to take next turn
				//if(this.getOrder()==0) return listJ.size()-1;
				//else return this.getOrder()-1;
			//}
		return 0;
	}

/**
* Set identity=revealed if the player's identity has been revealed 
* 
* @param b boolean that tells if identity revealed or not 
*/
	public void setIdentityRevealed(boolean b) {
		super.setIdentityRevealed(b);
	}
	
}
