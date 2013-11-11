/** The application model for all the cards.
 * @author funkydelpueblo
 * @version 0.1
 */
 
public class Model {

	//Singleton
	private static final Model instance = new Model();
	
	public static Model getInstance(){
		return instance;
	}
	
	private java.util.ArrayList<Card> cards;
	
	public Model(){
		cards = new java.util.ArrayList<Card>();
	}
	
	public java.util.ArrayList<Card> getAllCards(){
		return this.cards;
	}
	
	public java.util.ArrayList<Card> getAllBlack(){
		java.util.ArrayList<Card> result = new java.util.ArrayList<Card>();
		for(Card c : cards){
			if(!c.isWhite){
				result.add(c);
			}
		}
		return result;
	}
	
	public java.util.ArrayList<Card> getAllWhite(){
		java.util.ArrayList<Card> result = new java.util.ArrayList<Card>();
		for(Card c : cards){
			if(c.isWhite){
				result.add(c);
			}
		}
		return result;
	}
	
	public void addCard(boolean isWhite, String text){
		cards.add(new Card(isWhite, text));
	}
	
	public void addCard(Card c){
		cards.add(c);
	}
	
	public void clearModel(){
		this.cards.clear();
	}
}
