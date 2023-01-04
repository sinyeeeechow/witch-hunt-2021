package Modele;
import java.util.*;

enum Role{
	DiscardCard,AccuseDiscardCard, TakeNextTurn, RevealMyIdentity, ChooseNextPlayer,ChooseNextPlayerToRorD, TakeRevealedCard, TakeAccuseCard, RevealOthersIdentity, TakePlayersCard, TakeNextOrChoose, ChooseNextPlayerAccuse, TakeAndDiscardCard;
}

enum Status{
	Discarded,Revealed,InHand;
}

/**
 * @author sinyee
 *
 */

public class Carte {
	private CardName cardName;
	private Status status;
	private String filepath;
	
/**
* This is the class constructor which attribute name to a card and set the status of each card as inhand
*
* @param  cardName an enumeration 
*/
	public Carte(CardName cardName) {
		this.cardName = cardName;
		status = Status.InHand;
	}
	
	public int resolveHunt(JoueurAbs currentj,Partie p){
		CardName c = this.getCardName();
		ArrayList<JoueurAbs> listJ = p.getListPlayer();
		int indexNextPlayer = listJ.indexOf(currentj)+1;
		
		if(c==CardName.AngryMob) {
			if(currentj.getIdentityRevealed() && !currentj.getIdentity()) {
				System.out.println("Reveal other's identity");
				int index=p.askWhichOne(2);
				currentj.revealOthersIdentity(index, listJ, false);
			}
			else {
				System.out.println("You cannot reveal this card as you have not been revealed as a villager");
				System.out.println("Restart your round");
				p.lancer(p.getListPlayer().indexOf(currentj));
			}
		}
		
		else if(c==CardName.TheInquisition) {
			if(currentj.getIdentityRevealed() && !currentj.getIdentity()) {
				indexNextPlayer=currentj.chooseNextPlayer(listJ,p);
				currentj.revealOthersIdentity(indexNextPlayer,listJ,true);
			}
			else {
				System.out.println("You cannot reveal this card as you have not been revealed as a villager");
				System.out.println("Restart your round");
				p.lancer(p.getListPlayer().indexOf(currentj));
			}

		}
		
		else if(c==CardName.PointedHat) {
			if(!currentj.getRevealedCards().isEmpty()) {
				currentj.takeYourRevealedCard(p);
				indexNextPlayer=currentj.chooseNextPlayer(listJ,p);
			}
			else {
				System.out.println("You cannot play this card as you have not revealed any card yet");
				System.out.println("Restart your round");
				p.lancer(p.getListPlayer().indexOf(currentj));
			}

			
		}
		
		else if(c==CardName.HookedNose) {
			System.out.println("You can take a card from the next player");
			indexNextPlayer = currentj.chooseNextPlayer(listJ,p);
			JoueurAbs nextPlayer = p.getListPlayer().get(indexNextPlayer);
			nextPlayer.displayCards(nextPlayer.getCards());
			int index=p.askWhichOne(1);
			currentj.takeACardInSomeonesHand(listJ.get(indexNextPlayer),index,p);
		}
		
		else if(c==CardName.Cualdron || c==CardName.Toad) {
			indexNextPlayer = currentj.revealYourIdentity(listJ,p);
		}
		
		else if(c==CardName.PetNewt) {
			if (!p.getRevealedCards().isEmpty()) {
				p.displayCards(p.getRevealedCards());
				int index=p.askWhichOne(1);
				currentj.takeOthersRevealedCard(p,index);
				indexNextPlayer = currentj.chooseNextPlayer(listJ,p);
			}
			else {
				System.out.println("You cannot play this card as no one has revealed any card yet");
				System.out.println("Restart your round");
				p.lancer(p.getListPlayer().indexOf(currentj));
			}
		}
		
		else if(c==CardName.BlackCat) {
			if (!p.getDiscardedCards().isEmpty()) {
				p.displayCards(p.getDiscardedCards());
				int index=p.askWhichOne(1);
				currentj.takeADiscardedCard(index,p);
				currentj.getCards().remove(c);
			}
			else {
				System.out.println("You cannot play this card as no one has discarded any card yet");
				System.out.println("Restart your round");
				p.lancer(p.getListPlayer().indexOf(currentj));
			}
			
		}
		
		else {
			indexNextPlayer=currentj.chooseNextPlayer(listJ,p);
		}
		
		return indexNextPlayer;
	}
	
	public int resolveWitch(JoueurAbs currentj,JoueurAbs playerWhoAccusedYou,Partie p){
		CardName c = this.getCardName();
		ArrayList<JoueurAbs> listJ=p.getListPlayer();
		int indexNextPlayer = listJ.indexOf(playerWhoAccusedYou)+1;
		
		if(c==CardName.TheInquisition) {
			int indexCard = p.askWhichOne(1);
			currentj.discardYourCard(p,indexCard);
			currentj.takeNextTurn();
		}
		else if(c==CardName.PointedHat) {
			if(!currentj.getRevealedCards().isEmpty()) {
				currentj.takeYourRevealedCard(p);
				indexNextPlayer=currentj.chooseNextPlayer(listJ,p);
			}
			else {
				System.out.println("You cannot play this card as you have not revealed any card yet");
				System.out.println("Restart your round");
				p.lancer(p.getListPlayer().indexOf(currentj));
			}
		}
		else if(c==CardName.HookedNose) {
			System.out.println("You can take a card from the player who accused you.");
			p.displayCards(p.getRevealedCards());
			int index=p.askWhichOne(1);
			currentj.takeACardInSomeonesHand(playerWhoAccusedYou,index,p);
			indexNextPlayer = currentj.chooseNextPlayer(listJ,p);
		}
		else if(c==CardName.Cualdron) {
			System.out.println("You are required to discard a card by the player whom you have accused.");
			int indexCard = p.askWhichOne(1);
			playerWhoAccusedYou.discardYourCard(p,indexCard);
			currentj.takeNextTurn();
		}
		else if(c==CardName.EvilEye||c==CardName.DuckingStool) {
			currentj.chooseNextPlayer(listJ,p);
		}
		else {
			currentj.takeNextTurn();
		}
		
		return indexNextPlayer;
	}

/**
* Get the status of this card
*
* @return cardName a name of the card
*/
	public CardName getCardName() {
		return cardName;
	}

/**
* Get the status of this card
*
* @return status an enumeration (in hand, discarded, revealed)
*/
	public Status getStatus() {
		return status;
	}
	
/**
* Set the status of this card
*
* @param status an enumeration (in hand, discarded, revealed)
*/
	public void setStatus(Status status) {
		this.status = status;
	}	

/**
* Set the file path towards the image of this card
*
* @param fp a file path
*/
	public void setFilePath(String fp) {
		this.filepath=fp;
	}
	
/**
* Get the file path towards the image of this card
*
* @return string file path
*/
	public String getFilepath() {
		return filepath;
	}


/**
* Check the condition to see if a card can be played by the player 
*
* @param  c name of the card
* @param  j player who wish to use the card
* @param  p game
* @return boolean yes if the player can play the card, no if not
*/
	public boolean checkConditionHuntOfCards(CardName c,JoueurAbs j,Partie p) {
		boolean b=false;
		if(  ((c==CardName.AngryMob || c==CardName.TheInquisition) && j.getIdentityRevealed() && !j.getIdentity()) ||
			 (c==CardName.PointedHat && !j.getRevealedCards().isEmpty()) ||
			 (c==CardName.PetNewt && !p.getRevealedCards().isEmpty()) ||
			 (c==CardName.BlackCat && !p.getDiscardedCards().isEmpty()))
			b=true;
		
		return b;
	}

/**
* Compare the card name of a card to this card to determine if the two are identical
*
* @param  aC a card
* @return boolean yes if it is the same card, no if not
*/
	public boolean sameCard(Carte aC) {
		if(aC.getCardName()==this.getCardName()) return true;
		else return false;
	}
	
}
