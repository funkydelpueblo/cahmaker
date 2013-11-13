/** The functionality for printing the sets of cards.
 * @author funkydelpueblo
 * @version 0.1
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class PrintUtility {

	//
	// Card Text Printing
	//
	
	public static void printCards(boolean areWhite){
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new CardsPrintable(areWhite));
		boolean doPrint = job.printDialog();
	
		if (doPrint) {
		    try {
		        job.print();
		    } catch (PrinterException e) {
		       //pass
		    }
		}
	}
	
	public static class CardsPrintable implements Printable{

		private Color foreground;
		private Color background;
		private boolean white;
		
		public CardsPrintable(boolean areWhite){
			if(areWhite){
				foreground = Color.BLACK;
				background = Color.WHITE;
			}else{
				foreground = Color.WHITE;
				background = Color.BLACK;
			}
			this.white = areWhite;
		}
		
		@Override
		public int print(Graphics g, PageFormat pf, int page)
				throws PrinterException {
			
			java.util.ArrayList<String> data = new java.util.ArrayList<String>();
			java.util.ArrayList<Card> cards;
			
			if(this.white){
				cards = Model.getInstance().getAllWhite();
			}else{
				cards = Model.getInstance().getAllBlack();
			}
			
			if(page > Math.ceil(cards.size() / 9.0) - 1){
				return NO_SUCH_PAGE;
			}
			
			for(Card c : cards){
				data.add(c.text);
			}
			
			Graphics2D g2d = (Graphics2D)g;
		    g2d.translate(pf.getImageableX(), pf.getImageableY());
		    
		    System.out.println(pf.getImageableX() + pf.getImageableWidth());
		    
		    g2d.setRenderingHint(
		            java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
		            java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			final int TOP = 2;
			final int WIDTH = 175;
			final double ratio = 1.4; //This is true.
			final int HEIGHT = (int)(WIDTH * ratio);
		    int y = TOP;
		    int x = TOP;
		    
		    Font helvetica = new Font("Helvetica", Font.BOLD, 14);
		    g2d.setFont(helvetica);
		    
		    //Draw all card text
		    for(int i = page * 9; i < (page + 1) * 9 && i < cards.size(); i++ ){
		    	String s = cards.get(i).text;
		    	
		    	//Background
		    	g2d.setColor(this.background);
		    	g2d.fillRect(x, y, WIDTH, HEIGHT);
		    	
		    	//Draw text
		    	g2d.setColor(this.foreground);
		    	PrintUtility.drawString(g2d, s, x + 15, y + 25, WIDTH - 30, 2);
		    	
		    	//Bound card
		    	g2d.drawLine(x, y, x + WIDTH, y);
		    	g2d.drawLine(x, y, x, y + HEIGHT);
		    	g2d.drawLine(x + WIDTH, y, x + WIDTH, y + HEIGHT);
		    	g2d.drawLine(x, y + HEIGHT, x + WIDTH, y + HEIGHT);
		    	
		    	x += WIDTH;
		    	if(x > 400){
		    		x = TOP;
		    		y += HEIGHT;
		    	}
		    }
		    
		    // tell the caller that this page is part
		    // of the printed document
		    return PAGE_EXISTS;
		}
		
	}
	
	//
	// Card Back Printing
	//
	
	public static void printCardBacks(boolean areWhite){
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new CardBacksPrintable(areWhite));
		boolean doPrint = job.printDialog();
	
		if (doPrint) {
		    try {
		        job.print();
		    } catch (PrinterException e) {
		       //pass
		    }
		}
		
	}
	
	
	public static class CardBacksPrintable implements Printable{

		private Color foreground;
		private Color background;
		private boolean white;
		
		public CardBacksPrintable(boolean areWhite){
			if(areWhite){
				foreground = Color.BLACK;
				background = Color.WHITE;
			}else{
				foreground = Color.WHITE;
				background = Color.BLACK;
			}
			this.white = areWhite;
		}
		
		@Override
		public int print(Graphics g, PageFormat pf, int page)
				throws PrinterException {
			
			int numCards;
			
			if(this.white){
				numCards = Model.getInstance().getAllWhite().size();
			}else{
				numCards = Model.getInstance().getAllBlack().size();
			}
			
			if(page > Math.ceil(numCards / 9.0) - 1){
				return NO_SUCH_PAGE;
			}
			
			Graphics2D g2d = (Graphics2D)g;
		    g2d.translate(pf.getImageableX(), pf.getImageableY());
		    
		    g2d.setRenderingHint(
		            java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
		            java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			final int TOP = 2;
			final int LEFT = 49;
			final int WIDTH = 175;
			final double ratio = 1.4; //This is true.
			final int HEIGHT = (int)(WIDTH * ratio);
		    int y = TOP;
		    int x = LEFT;
		    
		    Font helvetica = new Font("Helvetica", Font.BOLD, 30);
		    g2d.setFont(helvetica);
		    
		    //Draw all card text
		    for(int i = page * 9; i < (page + 1) * 9; i++ ){		    	
		    	//Background
		    	g2d.setColor(this.background);
		    	g2d.fillRect(x, y, WIDTH, HEIGHT);
		    	
		    	//Draw text
		    	g2d.setColor(this.foreground);
		    	PrintUtility.drawString(g2d, "Calls About Hermajesty", x + 15, y + 35, WIDTH - 30, 2);
		    	
		    	//Bound card
		    	/*g2d.drawLine(x, y, x + WIDTH, y);
		    	g2d.drawLine(x, y, x, y + HEIGHT);
		    	g2d.drawLine(x + WIDTH, y, x + WIDTH, y + HEIGHT);
		    	g2d.drawLine(x, y + HEIGHT, x + WIDTH, y + HEIGHT);*/
		    	//We don't draw the lines on the back, because it leads to issues with printer accuracy
		    	
		    	x += WIDTH;
		    	if(x > 534){
		    		x = LEFT;
		    		y += HEIGHT;
		    	}
		    }
		    
		    return PAGE_EXISTS;
		}
		
	}
	
	/**
	 * From: http://stackoverflow.com/questions/400566/full-justification-with-a-java-graphics-drawstring-replacement
	 * Edited to include parameter for extra height between lines.
	 * @author coobird (StackOverflow.com)
	 * @param g
	 * @param s
	 * @param x
	 * @param y
	 * @param width
	 */
	private static void drawString(Graphics g, String s, int x, int y, int width, int extraHeight)
	{
		// FontMetrics gives us information about the width,
		// height, etc. of the current Graphics object's Font.
		java.awt.FontMetrics fm = g.getFontMetrics();

		int lineHeight = fm.getHeight() + extraHeight;

		int curX = x;
		int curY = y;

		String[] words = s.split(" ");

		for (String word : words)
		{
			// Find out thw width of the word.
			int wordWidth = fm.stringWidth(word + " ");

			// If text exceeds the width, then move to next line.
			if (curX + wordWidth >= x + width)
			{
				curY += lineHeight;
				curX = x;
			}

			g.drawString(word, curX, curY);

			// Move over to the right for next word.
			curX += wordWidth;
		}
	}
	
}
