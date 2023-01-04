package Controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;

import javax.swing.*;

import Modele.*;
import Vue.*;
import java.util.*;

/**
 * @author sinyee
 *
 */
public class gameController implements ActionListener{
	private Partie p;
	private GameView view;

/**
* This is the class constructor which initializes the view and the model (Partie object)
*
* @param  view  a GameView which is the start page of the game 
*/
	public gameController(GameView view) {
		this.view = view;
		this.p=Partie.getInstance();
	}

/**
* Initializes the game by asking number of players and distributing cards accordingly
* 
*/
	public void startGame() {
		p.getJeu().melanger();
		p.distributeCards();
	}
	
/**
* Create a new player based on informations given by user, then add the player into list  
* of players
*
* @param  username a name keyed in by user 
* @param  isBot a boolean to create virtual player if the value is true
*/
	public void addPlayer(String username,boolean isBot) {
		if(!isBot) {
			Joueur j = new Joueur(username);
			p.getListPlayer().add(j);
		}
		else {
			Bot b = new Bot(username);
			p.getListPlayer().add(b);
		}
	}

/**
* Returns a Joueur object based on a given index <br>
* Allows view to retrieve informations from model on selected player
*
* @param  index       a numeric value which indicates which player is selected 
* @return JoueurAbs   a Joueur object
*/
	public JoueurAbs getPlayer(int index) {
			return p.getListPlayer().get(index);
	}

/**
* Returns a java container containing the list of cards in hand of a selected player <br>
* This container will be charged by view to show the current player his cards
*
* @param  index   a numeric value which indicates which player is selected 
* @return JPanel  a Java container containing lists of cards
*/
	public  JPanel getYourCards(int index) {
		JoueurAbs j = p.getListPlayer().get(index);
		ArrayList<Carte> listC = j.getCards();
		JPanel jPan = new JPanel();
		for(Carte c:listC) {
			 JLabel card = new JLabel();
			 card.setIcon(new ImageIcon(c.getFilepath()));
			 card.setBounds(100, 100, 500, 500);
			 jPan.add(card);
		}
		return jPan;
	}
	
/**
* Returns a java container containing the result of a reveal, such as player's identity and personalized message <br>
* Calls the function in model to determine the effect of this reveal,for example, to add score or to choose next player
*
* @return JPanel  a Java container containing the result of a reveal
*/	
	public JPanel revealIdentity() {
		JoueurAbs accused = p.getListPlayer().get(view.getIndexAccused());
		JoueurAbs accuser = p.getListPlayer().get(view.getIndexAccuser());
		
		JPanel jPan = new JPanel();	
		JLabel result= new JLabel();
		
		String identity ="";
		if(accused.getIdentity()) {
			identity="witch";
			p.respondToAccusedRevealIdentity(view.getIndexAccused(), view.getIndexAccuser());
			result.setText(accuser.getName()+"earns a point while "+accused.getName()+" has lost the round.");
			view.setIndexCurrentPlayer(getIndexNextPlayer());
		}
		else {
			identity="villager";
			result.setText(accused.getName()+" plays.");
			view.setIndexCurrentPlayer(view.getIndexAccused());
		}
		
		JLabel l=new JLabel("You've accused "+accused.getName()+"! The player is "+identity);
		jPan.add(l);jPan.add(result);
		
		return jPan;
	}
	
/**
* Returns a java container containing the result of my own reveal<br>
* Calls the function in model to determine the effect of this reveal <br>
* For example, to add score or to choose next player
*
* @return JPanel  a Java container containing the result of my own reveal
*/		
	public JPanel revealMyIdentity() {
		JPanel jPan = new JPanel();
		JLabel l = new JLabel();
		JoueurAbs j = p.getListPlayer().get(view.getIndexCurrentPlayer());
		j.setIdentityRevealed(true);
		if(j.getIdentity()) {
			l.setText(j.getName()+" is a witch");
		}
		else {
			l.setText(j.getName()+" is a villager");
		}
		jPan.add(l);
		return jPan;
	}
	
/**
* Returns a list of players that i can accuse (excluding myself)
* 
* @param  index   my index so that i can be removed from the list
* @return ArrayList names of players that i can accuse
*/		
	public ArrayList<String> getListsOfPlayersToAccuse(int index) {
		
		ArrayList<String> listJa=new ArrayList<String>();
		ArrayList<JoueurAbs> list = p.getListPlayer();
		list.remove(index);
		for(JoueurAbs j:list) {
			listJa.add(j.getName());
		}
			
		return listJa;
	}

/**
* Receives action events (click on button, select a check box etc) and react
* 
* @param  e   action event
*/		
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == view.btnEnter) {
			Integer number = (Integer) view.ansNumberPlayer.getItemAt(view.ansNumberPlayer.getSelectedIndex());
			p.setNumberOfPlayers(number);
	        view.frame.setContentPane(view.initializePlayer(number));
			view.frame.invalidate();
			view.frame.validate();
		}
		
		if(e.getSource() == view.btnPlayer) {
			addPlayer(view.username1.getText(),view.bot1.isSelected());
			addPlayer(view.username2.getText(),view.bot1.isSelected());
			addPlayer(view.username3.getText(),view.bot1.isSelected());
        	
        	if(p.getNumberOfPlayers()==4) addPlayer(view.username4.getText(),view.bot4.isSelected());
        	if(p.getNumberOfPlayers()==5) addPlayer(view.username5.getText(),view.bot5.isSelected());
        	if(p.getNumberOfPlayers()==6) addPlayer(view.username6.getText(),view.bot6.isSelected());	
        	startGame();
        	 //changeFrame(getYourCards(view.getIndexCurrentPlayer()));
        	 changeFrame(view.chooseRole(0));
		}
		
		if(e.getSource() == view.btnChooseRole) {
			int index = view.getIndexCurrentPlayer();
			p.getListPlayer().get(index).setIdentity(view.witch.isSelected());
   		 	index++;
       	 	if(index<p.getNumberOfPlayers()) {
          	 view.setIndexCurrentPlayer(index);
       	 	 changeFrame(view.chooseRole(index));
       	 	}
       	 	else {
       	 		changeFrame(view.playerInterface1(0));
       	    }
		}
		
		if(e.getSource() == view.btnPlay) {
			if(view.accuse.isSelected()) {
				changeFrame(view.playerInterfaceAccuseSomeone(view.getIndexCurrentPlayer()));
			}
			else if (view.resolveHunt.isSelected()) {
				view.frame.setContentPane(view.playerInterfaceChooseCard(view.getIndexCurrentPlayer(),1,0));
       	 		view.frame.invalidate();
       	 		view.frame.validate();
			}
		}
		
		if(e.getSource() == view.btnAccuse) {
			String name = (String) view.ansPlayer.getSelectedItem();
			JoueurAbs accused= p.searchPlayerByName(name); 
			int indexAccused = p.getListPlayer().indexOf(accused);
			view.setIndexAccused(indexAccused);
			changeFrame(view.playerInterface2(indexAccused));
		}
		
		if(e.getSource() == view.btnPlay2) {
			if(view.revealIdentity.isSelected()) {
				changeFrame(view.playerInterfaceRevealIdentity());
			}
			else if (view.resolveWitch.isSelected()) {
				view.frame.setContentPane(view.playerInterfaceChooseCard(view.getIndexCurrentPlayer(),0,0));
       	 		view.frame.invalidate();
       	 		view.frame.validate();
			}
		}
		
		if(e.getSource() == view.btnNextPlayer) {
			String name = (String) view.ansPlayer.getSelectedItem();
			JoueurAbs j= p.searchPlayerByName(name); 
			view.setIndexCurrentPlayer(p.getListPlayer().indexOf(j));
			changeFrame(view.playerInterface1(view.getIndexCurrentPlayer()));
		}
		
		if(e.getSource() == view.btnChooseCard) {
			int cardIndex = view.ansCard.getSelectedIndex();
			Carte c=p.getListPlayer().get(view.getIndexCurrentPlayer()).getCards().get(cardIndex);
			changeFrame(this.resolveHunt(c));	
		}
		
		if(e.getSource() == view.btnDiscardCard) {
			int cardIndex = view.ansCard.getSelectedIndex();
			JoueurAbs j = p.getListPlayer().get(view.getIndexCurrentPlayer());
			
			j.discardYourCard(p,cardIndex);

			// take next turn
			changeFrame(view.playerInterface1(getIndexNextPlayer()));	
		}
		
		// Take my own revealed card
		if(e.getSource() == view.btnTakeCard) {
			int cardIndex = view.ansCard.getSelectedIndex();
			Carte c=p.getListPlayer().get(view.getIndexCurrentPlayer()).getCards().get(cardIndex);
			JoueurAbs j = p.getListPlayer().get(view.getIndexCurrentPlayer());
			
			// add the card into card in hands
			j.getCards().add(c);
			
			// the card is remove from player's revealed list
			j.getRevealedCards().remove(c);
			
			// take next turn
			changeFrame(view.playerInterface1(getIndexNextPlayer()));	
		}
		
		// Take from my accuser 
			if(e.getSource() == view.btnTakeCardFromAccuser) {
				int cardIndex = view.ansCard.getSelectedIndex();
				Carte c=p.getListPlayer().get(view.getIndexCurrentPlayer()).getCards().get(cardIndex);
				JoueurAbs j = p.getListPlayer().get(view.getIndexCurrentPlayer());
				JoueurAbs a = p.getListPlayer().get(view.getIndexAccuser());
				
				// add the card into card in hands
				j.getCards().add(c);
				
				// the card is remove from accuser's hand
				a.getCards().remove(cardIndex);
				
				// take next turn
				changeFrame(view.playerInterface1(getIndexNextPlayer()));	
			}
			
		// Take from anyone
			if(e.getSource() == view.btnTakeCardFromOthers) {
				int cardIndex = view.ansCard.getSelectedIndex();
				Carte c=p.getListPlayer().get(view.getIndexCurrentPlayer()).getCards().get(cardIndex);
				JoueurAbs j = p.getListPlayer().get(view.getIndexCurrentPlayer());
				JoueurAbs a = p.getListPlayer().get(view.getIndexAccuser());
				
				// add the card into card in hands
				j.getCards().add(c);
				
				// the card is remove from accuser's hand
				a.discardYourCard(p, cardIndex);
				
				// take next turn
				changeFrame(view.playerInterface1(getIndexNextPlayer()));	
			}
			
			// Take from partie's discarded cards
				if(e.getSource() == view.btnTakeCardFromPartieDiscarded) {
					int cardIndex = view.ansCard.getSelectedIndex();
					Carte c=p.getListPlayer().get(view.getIndexCurrentPlayer()).getCards().get(cardIndex);
					JoueurAbs j = p.getListPlayer().get(view.getIndexCurrentPlayer());
					
					// add the card into card in hands
					j.getCards().add(c);
					
					// the card is remove from partie's discarded cards
					p.getDiscardedCards().remove(c);
					// take next turn
					changeFrame(view.playerInterface1(getIndexNextPlayer()));	
				}
				
				// Take from partie's discarded cards
				if(e.getSource() == view.btnTakeCardFromPartieRevealed) {
					int cardIndex = view.ansCard.getSelectedIndex();
					Carte c=p.getListPlayer().get(view.getIndexCurrentPlayer()).getCards().get(cardIndex);
					JoueurAbs j = p.getListPlayer().get(view.getIndexCurrentPlayer());
					
					// add the card into card in hands
					j.getCards().add(c);
					
					// the card is remove from accuser's hand
					p.getRevealedCards().remove(c);
					
					// take next turn
					changeFrame(view.playerInterface1(getIndexNextPlayer()));	
				}
				
				// I have revealed my identity
				if(e.getSource() == view.btnNextPlayerRMI) {
					JoueurAbs j = p.getListPlayer().get(view.getIndexCurrentPlayer());
					// If I am a witch
					if(j.getIdentity()) {
						// the player to my left (assuming previous player takes next turn)
						changeFrame(view.playerInterface1(getIndexPreviousPlayer()));	
					}
					// If I am a villager
					else {
						// I can choose next player
						changeFrame(view.playerInterfaceChooseNextPlayer());
					}
				}
				
				if(e.getSource() == view.btnRechoose) {
					changeFrame(view.playerInterface1(view.getIndexCurrentPlayer()));
				}
	
	}
	
/**
* Returns index of next player that will take the round<br>
* Index is sent to view to show the player's interface (name, cards, etc)
* 
* @return integer  index of next player
*/			
	public int getIndexNextPlayer() {
		int indexNextPlayer = view.getIndexCurrentPlayer()+1;
		if(indexNextPlayer>=p.getListPlayer().size()) {
			indexNextPlayer=0;
		}
		return indexNextPlayer;
	}

	
/**
* Returns index of previous player, useful to find out who has accused the current player therefore the current
* player could take action upon the accuser<br>
* Index is sent to view to show the player's interface (name, cards, etc)
* 
* @return integer  index of previous player
*/	
	public int getIndexPreviousPlayer() {
		int indexPreviousPlayer = view.getIndexCurrentPlayer()-1;
		if(indexPreviousPlayer<0) {
			indexPreviousPlayer=p.getListPlayer().size()-1;
		}
		return indexPreviousPlayer;
	}

/**
* Factorises lines of codes that charges java container to the view 
* 
* @param jPan a container that will be charged by the main frame
*/	
	public void changeFrame(JPanel jPan) {
		 view.frame.setContentPane(jPan);
    	 view.frame.invalidate();
    	 view.frame.validate();
	}

/**
* Resolve the hunt effect of cards<br>
* Returns appropriate java container based on the selected card<br>
* For example, asking next player to play, reveal identity, choose a card, etc
* 
* @param c a card picked by player
* @return JPanel  a Java container containing the result of my own reveal
*/	
	public JPanel resolveHunt(Carte c){
		
		CardName cn = c.getCardName();
		JPanel jPan = new JPanel();
		JoueurAbs j = p.getListPlayer().get(view.getIndexCurrentPlayer());
		
		if(cn==CardName.AngryMob) {
			if(c.checkConditionHuntOfCards(cn,j,p)) {
				jPan = view.playerInterfaceRevealIdentity();
			}
			else jPan = view.playerInterfaceRechooseCard();			
		}
		else if(cn==CardName.TheInquisition) {
			if(c.checkConditionHuntOfCards(cn,j,p)) {
				jPan = view.playerInterfaceRevealIdentity();
			}
			else jPan = view.playerInterfaceRechooseCard();
			
		}
		else if(cn==CardName.PointedHat) {
			if(c.checkConditionHuntOfCards(cn,j,p)) {
				jPan = view.playerInterfaceChooseCard(view.getIndexCurrentPlayer(),0,2);
			}
			else jPan = view.playerInterfaceRechooseCard();
		}
		else if(cn==CardName.HookedNose) {
			jPan = view.playerInterfaceChooseNextPlayer();
		}
		else if(cn==CardName.Cualdron || cn==CardName.Toad) {
			jPan = view.playerInterfaceRevealMyIdentity();
		}
		else if(cn==CardName.PetNewt) {
			if (c.checkConditionHuntOfCards(cn, j, p)) {
				jPan = view.playerInterfaceChooseNextPlayer();
			}
			else jPan = view.playerInterfaceRechooseCard();
		}
		else if(cn==CardName.BlackCat) {
			if (c.checkConditionHuntOfCards(cn, j, p)) {
				jPan = view.playerInterfaceChooseCard(view.getIndexCurrentPlayer(),0,2);
			}
			else jPan = view.playerInterfaceRechooseCard();
		}
		else jPan = view.playerInterfaceChooseNextPlayer();

		return jPan;
	}
	
/**
* Resolve the witch effect of cards<br>
* Returns appropriate java container based on the selected card<br>
* For example, asking next player to play, reveal identity, choose a card, etc
* 
* @param c a card picked by player
* @return JPanel  a Java container containing the result of my own reveal
*/		
	public JPanel resolveWitch(Carte c){
		JPanel jPan = new JPanel();
		CardName cn = c.getCardName();
		JoueurAbs j=p.getListPlayer().get(view.getIndexCurrentPlayer());
		
		if(cn==CardName.TheInquisition) {
			// player choose a card to discard
			jPan = view.playerInterfaceChooseCard(view.getIndexCurrentPlayer(), 0, 0);
		}
		
		else if(cn==CardName.PointedHat) {
			if(!j.getRevealedCards().isEmpty()) {
				jPan = view.playerInterfaceChooseCard(view.getIndexCurrentPlayer(),0,2);
			}
			else jPan = view.playerInterfaceRechooseCard();		
		}
	
		else if(cn==CardName.HookedNose) {
			jPan =  view.playerInterfaceChooseCard(view.getIndexCurrentPlayer(),0,2);
		}
		
		else if(cn==CardName.Cualdron) {
			jPan = view.playerInterfaceChooseCard(view.getIndexCurrentPlayer(),0,2);
		}
		else if(cn==CardName.EvilEye||cn==CardName.DuckingStool) {
			jPan = view.playerInterfaceChooseNextPlayer();
		}
		else {
			view.setIndexCurrentPlayer(getIndexNextPlayer());
			jPan = view.playerInterface1(view.getIndexCurrentPlayer());
		}
		
		return jPan;
	}

}
