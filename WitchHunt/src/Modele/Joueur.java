package Modele;
import java.util.*;

public class Joueur extends JoueurAbs {
	private String name;
	private int score;
	//true if witch,false if villager
	private boolean identity;
	private boolean identityRevealed;
	private ArrayList<Carte> cards;
	private ArrayList<Carte> revealedCards;
	
	/**
	* Reveal the identity of other player
	* 
	* @see JoueurAbs
	* 
	* @param j player whose identity should be revealed
	* @param secret boolean that tells if this is a secret reveal or not 
	* @return boolean
	*/	
	public boolean revealIdentity(JoueurAbs j,boolean secret) {
		return super.revealIdentity(j, secret);
	}
	
	/**
	* Reveal my own identity <br>
	* Player can choose next player if he is a villager <br>
	* If not, choose player to the left to take next turn 
	* 
	* @param listJ list of players
	* @return boolean
	*/	
	public int revealYourIdentity(ArrayList<JoueurAbs> listJ,Partie p) {
		//Choose next player if i am a villager
		if(identity) return chooseNextPlayer(listJ,p);
		else {
			//Choose player to your left to take next turn
			if(p.getListPlayer().indexOf(this)==0) return listJ.size()-1;
			else return p.getListPlayer().indexOf(this)-1;
		}
	}
		
	/**
	* Display player's list of cards (in hand, discarded or revealed)
	* 
	* @see JoueurAbs
	* @param listOfCards player whose identity should be revealed
	*/	
	public void displayCards(ArrayList<Carte> listOfCards) {
		super.displayCards(listOfCards);
	}	
	
	/**
	* Discard my own card to the round 
	* 
	* @see JoueurAbs
	* @param indexCard index of the card which should be discarded
	* @param p game 
	*/	
	public void discardYourCard(Partie p,int indexCard) {
		super.discardYourCard(p, indexCard);
	}
	
	/**
	* Take a card from the round's discarded cards
	* 
	* @see JoueurAbs
	* @param indexCard index of the card which should be discarded
	* @param p game 
	*/		
	public void takeADiscardedCard(int indexCard, Partie p) {
		super.takeADiscardedCard(indexCard, p);
	}
	
	/**
	* Take a card that is in the hand of another player 
	* 
	* @see JoueurAbs
	* @param anotherj a selected player 
	* @param index index of the card
	* @param p game 
	*/			
	public void takeACardInSomeonesHand(JoueurAbs anotherj,int index, Partie p) {
		super.takeACardInSomeonesHand(anotherj, index,p);
	}
	
	/**
	* Take a card that has been revealed (not by the player itself) and is currently in the round
	* 
	* @see JoueurAbs
	* @param index index of the card 
	* @param p game 
	*/			
	public void takeOthersRevealedCard(Partie p,int index){
		super.takeOthersRevealedCard(p, index);
	}
	
	/**
	* Check if the round admits a winner
	* 
	* @return boolean
	*/		
	public boolean isWinner(){
		return super.isWinner();
	}
	
	/**
	* Reveal a card from your hand
	* 
	* @see JoueurAbs
	* @param index index of the card 
	* @param p game 
	* @return a card
	*/		
	public Carte revealCard(int index,Partie p) {
		return super.revealCard(index,p);
	}
	
	/**
	* Take a card that has been revealed by the player itself 
	* @see JoueurAbs
	* @param p game 
	*/			
	public void takeYourRevealedCard(Partie p) {
		super.takeYourRevealedCard(p);
	}
	
	/**
	* Player takes next turn
	*  
	* @see JoueurAbs 
	*/		
	public void takeNextTurn() {
		super.takeNextTurn();
	}

	
	/**
	* This is the class constructor inherited from mother class
	* @see JoueurAbs
	*
	* @param  name  a name for the player 
	*/
	public Joueur(String name) {
		super(name);
	}
	
	/**
	* Get the name of the player
	*
	*@return name
	*/
	public String getName() {
		return super.getName();
	}
	
	/**
	* Get the cards in hand of the player
	*
	*@return list of cards
	*/
	public ArrayList<Carte> getCards(){
		return super.getCards();
	}
	
	/**
	* Get the name of all the cards in hand of the player
	*
	*@return list of strings (names of cards)
	*/
	public ArrayList<String> getCardsName(){
		return super.getCardsName();
	}
	
	/**
	* Get the revealed cards of the player
	*
	*@return list of cards
	*/
	public ArrayList<Carte> getRevealedCards(){
		return super.getRevealedCards();
	}
	
	/**
	* Get the score of the player
	*
	*@return score
	*/
	public int getScore() {
		return super.getScore();
	}	
	
	/**
	* Add the score of the player
	*
	*@param score player's score
	*/	
	public void addScore(int score) {
		super.addScore(score);
	}
	
	/**
	* Get the identity of the player, true if witch, false if villager
	* 
	*@return boolean
	*/
	public boolean getIdentity() {
		return super.getIdentity();
	}
	
	/**
	* Set the identity of the player, true if witch, false if villager
	* 
	*@param identity a boolean
	*/
	public void setIdentity(boolean identity) {
		super.setIdentity(identity);
	}
	
	/**
	* Check if identity of player has been revealed 
	* 	 
	* @return boolean
	*/
	public boolean getIdentityRevealed() {
		return super.getIdentityRevealed();
	}
	
	/**
	* Set revealed=true if identity of player has been revealed 
	* 	 
	* @param b a boolean
	*/
	public void setIdentityRevealed(boolean b) {
		super.setIdentityRevealed(b);
	}

}
