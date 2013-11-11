/** Represents a single card object
 * @author funkydelpueblo
 * @version 0.1
 */
 
public class Card {

	public boolean isWhite;
	public String text;
	
	public Card(boolean white, String t){
		this.isWhite = white;
		this.text = t;
	}
	
	public String toString(){
		return this.text;
	}
	
}
