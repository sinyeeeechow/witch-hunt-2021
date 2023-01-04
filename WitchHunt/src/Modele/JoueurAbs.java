package Modele;
import java.util.*;


public abstract class JoueurAbs {
	private String name;
	private int score;
	//true if witch,false if villager
	private boolean identity;
	private boolean identityRevealed;
	private ArrayList<Carte> cards;
	private ArrayList<Carte> revealedCards;
	
	/**
	 * This is the class constructor which assigns name to the player and initializes an empty list of cards
	 * @param name
	 */
	public JoueurAbs(String name) {
		this.name = name;
		this.score = 0;
		this.cards = new ArrayList<Carte>();
		this.revealedCards = new ArrayList<Carte>();
		this.identityRevealed = false;
	}
	
	
	/**
	* Reveal the identity of other player <br>
	* Player can choose next player if his is a villager<br>
	* If not, choose the player to the left
	* 
	* @see JoueurAbs
	* 
	* @param j player whose identity should be revealed
	* @param secret boolean that tells if this is a secret reveal or not 
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
	* Reveal the identity of other player
	* It returns a boolean which will be further integrated into another method 
	* @see revealOthersIdentity() in this class
	* 
	* @param j player whose identity should be revealed
	* @param secret boolean that tells if this is a secret reveal or not 
	* @return boolean
	*/	
	public boolean revealIdentity(JoueurAbs j,boolean secret) {
		if(secret) {
			System.out.println("This is a secret reveal");
		}
		if(identity) {
			this.identityRevealed = true;
			System.out.println();
			
			System.out.println(this.name+" is a witch");
			if(!secret) {
				System.out.println(this.name+", you lose!");
				j.addScore(2);
				System.out.println();
				System.out.println(j.getName()+" gets 2 points");
				System.out.println();
			}
		}
		else {
			System.out.println();
			System.out.println(this.name+" is a villager");
			this.identityRevealed = true;
			System.out.println();
		}
		return identity;
	}
	
	/**
	* Reveal the identity of other player
	* 
	* 
	* @param listJ list of players whose identity could be revealed
	* @param secret boolean that tells if this is a secret reveal or not 
	*/	
	public void revealOthersIdentity(int index,ArrayList<JoueurAbs> listJ,boolean secret) {
		//if the player is a witch
		if(listJ.get(index).revealIdentity(this,secret)) {
			//remove the player from the round
			listJ.remove(index);
		}
	}
		
	/**
	* Display player's list of cards (in hand, discarded or revealed)
	* 
	* @param listOfCards player whose identity should be revealed
	*/	
	public void displayCards(ArrayList<Carte> listOfCards) {
		System.out.println("Here is a list of cards in your hand.");
		for(Carte c:listOfCards) {
			System.out.println(c.getCardName()+": "+c.getStatus());
		}
		System.out.println();
	}	
	
	/**
	* Player put a card into his hand and the status of this card is modified 
	* 
	* @param carte a card
	*/	
	public void retrieveCard(Carte carte){
		cards.add(carte);
		carte.setStatus(Status.InHand);
	}
	
	/**
	* Reveal a card from your hand
	* 
	* @param i index of the card 
	* @param p game 
	* @return a card
	*/		
	public Carte revealCard(int i,Partie p) {
		Carte c=cards.get(i);
		this.revealedCards.add(c);
		p.getRevealedCards().add(c);
		c.setStatus(Status.Revealed);
		return cards.get(i);
	}
	
	/**
	* Discard a card from your hand to the round <br>
	* Remove the card from player's hand and
	* add the card to the round's discarded card and set the status
	* @param i index of the card 
	* @param p game 
	*/		
	public void discardYourCard(Partie p,int index) {
		Carte c = this.getCards().get(index);
		this.getCards().remove(index);
		p.getDiscardedCards().add(c);
		c.setStatus(Status.Discarded);
	}
	
	/**
	* Take a card from the round's discarded cards, remove the discarded card from the round
	* then give the card to the player and set the status from discarded to inhand
	* 
	* @see JoueurAbs
	* @param indexCard index of the card which should be discarded
	* @param p game 
	*/		
	public void takeADiscardedCard(int index,Partie p) {
		// remove the discarded card from the round
		Carte c=p.getDiscardedCards().remove(index);
		// give the card to the player and set the status from discarded to inhand
		this.retrieveCard(c);
	}
	
	/**
	* Take a card that is in the hand of another player 
	* 
	* @param anotherj a selected player 
	* @param index index of the card
	* @param p game 
	*/			
	public void takeACardInSomeonesHand(JoueurAbs anotherj,int index,Partie p) {
		Carte c = anotherj.getCards().get(index);
		anotherj.discardYourCard(p,index);
		this.retrieveCard(c);
	}
	
	/**
	* Take a card that has been revealed (not by the player itself) and is currently in the round
	* 
	* @see JoueurAbs
	* @param index index of the card 
	* @param p game 
	*/			
	public void takeOthersRevealedCard(Partie p,int index){
		Carte c=p.getRevealedCards().remove(index);
		this.retrieveCard(c);
	}	

	/**
	* Take a card that has been revealed by the player itself 
	*  
	* @param index index of the selected card 
	*/	
	public void takeYourRevealedCard(Partie p) {
		if(!this.getRevealedCards().isEmpty()) {
			System.out.println("You can retrieve one of your own revealed cards.");
			this.displayCards(this.getRevealedCards());
			int index=p.askWhichOne(1);
			Carte c=this.getRevealedCards().remove(index);
			this.retrieveCard(c);
		}
		else {
			System.out.println("You couldn't use the witch effect of this card, as you haven't revealed any card.");
			p.lancer(p.getListPlayer().indexOf(this));
		}

	}
	
	/**
	* Check if the round admits a winner
	* 
	* @return boolean
	*/		
	public boolean isWinner(){
		if(this.score>=5) return true;
		else return false;
	}
	
	/**
	* Player takes next turn
	* 
	* @return boolean
	*/	
	public void takeNextTurn() {
		System.out.println(this.getName()+", you take next turn");
	}
	
	
	public ArrayList<String> getCardsName(){
		ArrayList<String> list = new ArrayList<String>();
		for(Carte c:this.getCards()) {
			list.add(c.getCardName().toString());
		};
		return list;
	}
	
	/**
	 * Player chooses a player that will take next turn
	 * 
	 * @param listJ list of players
	 * @param p game
	 * @return
	 */
	public int chooseNextPlayer(ArrayList<JoueurAbs> listJ,Partie p) {
		System.out.println();
		System.out.println("Choose next player");
		for(JoueurAbs j:listJ) {
			System.out.println(j.getName());
		}
			return p.askWhichOne(2);
	}
	
	/**
	* Get the name of the player
	*
	*@return name
	*/
	public String getName() {
		return this.name;
	}
	
	/**
	* Get the cards in hand of the player
	*
	*@return list of cards
	*/
	public ArrayList<Carte> getCards(){
		return this.cards;
	}
	
	/**
	* Get the revealed cards of the player
	*
	*@return list of cards
	*/
	public ArrayList<Carte> getRevealedCards(){
		return this.revealedCards;
	}
	
	/**
	* Get the score of the player
	*
	*@return score
	*/
	public int getScore() {
		return this.score;
	}	
	
	/**
	* Add the score of the player
	*
	*@param score player's score
	*/	
	public void addScore(int score) {
		this.score += score;
	}
	
	/**
	* Set the identity of the player, true if witch, false if villager
	* 
	*@param identity a boolean
	*/
	public void setIdentity(boolean identity) {
		this.identity = identity;
	}

	/**
	* Get the identity of the player, true if witch, false if villager
	* 
	*@return boolean
	*/
	public boolean getIdentity() {
		return this.identity;
	}
	
	/**
	* Check if identity of player has been revealed 
	* 	 
	* @return boolean
	*/
	public boolean getIdentityRevealed() {
		return this.identityRevealed;
	}
	
	/**
	* Set revealed=true if identity of player has been revealed 
	* 	 
	* @param b a boolean
	*/
	public void setIdentityRevealed(boolean b) {
		this.identityRevealed=b;
	}
		
}

