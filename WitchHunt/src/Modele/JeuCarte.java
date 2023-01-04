package Modele;
import java.util.*;

public class JeuCarte {

		private ArrayList<Carte> cards;
		public static final int nbrCartes = 12;

		/**
		* This is the class constructor which initializes 12 cards 
		*
		*/
		public JeuCarte(){
			cards = new ArrayList<Carte>();
			CardName[] cn = CardName.values();
			for(int i=0 ; i < cn.length; i++){
				Carte c = new Carte(cn[i]);
				cards.add(c);
			}
			
			cards.get(0).setFilePath("C:\\Users\\sinyee\\Desktop\\WitchHunt\\img\\angryMob.PNG");
			cards.get(1).setFilePath("C:\\Users\\sinyee\\Desktop\\WitchHunt\\img\\inquisition.PNG");
			cards.get(2).setFilePath("C:\\Users\\sinyee\\Desktop\\WitchHunt\\img\\pointedHat.PNG");
			cards.get(3).setFilePath("C:\\Users\\sinyee\\Desktop\\WitchHunt\\img\\HookedNose.PNG");
			cards.get(4).setFilePath("C:\\Users\\sinyee\\Desktop\\WitchHunt\\img\\broomstick.PNG");
			cards.get(5).setFilePath("C:\\Users\\sinyee\\Desktop\\WitchHunt\\img\\wart.PNG");
			cards.get(6).setFilePath("C:\\Users\\sinyee\\Desktop\\WitchHunt\\img\\duckingStool.PNG");
			cards.get(7).setFilePath("C:\\Users\\sinyee\\Desktop\\WitchHunt\\img\\cualdron.PNG");
			cards.get(8).setFilePath("C:\\Users\\sinyee\\Desktop\\WitchHunt\\img\\evilEye.PNG");
			cards.get(9).setFilePath("C:\\Users\\sinyee\\Desktop\\WitchHunt\\img\\toad.PNG");
			cards.get(10).setFilePath("C:\\Users\\sinyee\\Desktop\\WitchHunt\\img\\blackCat.PNG");
			cards.get(11).setFilePath("C:\\Users\\sinyee\\Desktop\\WitchHunt\\img\\petNewt.PNG");
			
			/*cards.get(0).addWitchRole(Role.TakeNextTurn);
			cards.get(0).addHuntRole(Role.RevealOthersIdentity);
			
			cards.get(1).addWitchRole(Role.DiscardCard);
			cards.get(1).addWitchRole(Role.TakeNextTurn);
			cards.get(1).addHuntRole(Role.ChooseNextPlayer);
			cards.get(1).addHuntRole(Role.RevealOthersIdentity);
			
			cards.get(2).addWitchRole(Role.TakeRevealedCard);
			cards.get(2).addWitchRole(Role.TakeNextTurn);
			cards.get(2).addHuntRole(Role.TakeRevealedCard);
			cards.get(2).addHuntRole(Role.ChooseNextPlayer);
			
			cards.get(3).addWitchRole(Role.TakeAccuseCard);
			cards.get(3).addWitchRole(Role.TakeNextTurn);
			cards.get(3).addHuntRole(Role.ChooseNextPlayer);
			cards.get(3).addHuntRole(Role.TakePlayersCard);
			
			cards.get(4).addWitchRole(Role.TakeNextTurn);
			cards.get(4).addHuntRole(Role.ChooseNextPlayer);
			
			cards.get(5).addWitchRole(Role.TakeNextTurn);
			cards.get(5).addHuntRole(Role.ChooseNextPlayer);
			
			cards.get(6).addWitchRole(Role.ChooseNextPlayer);
			cards.get(6).addHuntRole(Role.ChooseNextPlayerToRorD);
			
			cards.get(7).addWitchRole(Role.AccuseDiscardCard);
			cards.get(7).addWitchRole(Role.TakeNextTurn);
			cards.get(7).addHuntRole(Role.RevealMyIdentity);
			cards.get(7).addHuntRole(Role.TakeNextOrChoose);
			
			cards.get(8).addWitchRole(Role.ChooseNextPlayerAccuse);
			cards.get(8).addHuntRole(Role.ChooseNextPlayerAccuse);
			
			cards.get(9).addHuntRole(Role.TakeNextTurn);
			cards.get(9).addHuntRole(Role.RevealMyIdentity);
			cards.get(9).addHuntRole(Role.TakeNextOrChoose);
			
			cards.get(10).addWitchRole(Role.TakeNextTurn);
			cards.get(10).addHuntRole(Role.TakeAndDiscardCard);
			cards.get(10).addHuntRole(Role.TakeNextTurn);
			
			cards.get(11).addWitchRole(Role.TakeNextTurn);
			cards.get(11).addHuntRole(Role.TakePlayersCard);
			cards.get(11).addHuntRole(Role.ChooseNextPlayer);	*/	
		}
		
		/**
		* This is the class constructor which initializes 12 cards 
		*
		* @return list of cards
		*/
		public ArrayList<Carte> getTasCartes(){
			return cards;
		}
		
		/**
		* Distributes a card to a player and remove the card from the tas
		* 
		* @return carte which will be added to the list of cards of the corresponding player
		*/
		public Carte distribuerUneCarte(){ 
			return cards.remove(0);
		}
		
		/**
		* Shuffles the cards so that they are not in their creating orders
		*
		*/
		public void melanger(){
			Collections.shuffle(cards);
		}
		
		/**
		* Test if all the cards have been removed from the list <br>
		* If yes, means all the cards have been properly distributed <br>
		* The game can start now 
		* 
		* @return boolean
		*/
		public boolean estVide() {
				return cards.isEmpty();
		}
		
}
