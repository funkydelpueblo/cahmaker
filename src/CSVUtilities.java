/** Utilities for loading and saving card sets to file as .csv files
 * @author funkydelpueblo
 * @version 0.1
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class CSVUtilities {

	public static void readInCards(String filename){
		//Format:
		//Card Text | 0 = white, 1 = black
		Model.getInstance().clearModel();
		
		CSVReader reader;
		try {
			reader = new CSVReader(new java.io.FileReader(filename), '|');
			
			List<String[]> myEntries = reader.readAll();
			
			for(String[] entry : myEntries){
				String text = entry[0];
				boolean white = Integer.parseInt(entry[1]) == 0;
				Model.getInstance().addCard(white, text);
			}
			
			System.out.println("Cards read in successfully.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void exportCards(String filename){
		//Format:
		//Card Text | 0 = white, 1 = black
			
		java.util.ArrayList<String[]> output = new java.util.ArrayList<String[]>();
		java.util.ArrayList<Card> cards = Model.getInstance().getAllCards();
		
		for(Card c : cards){
			String[] temp = {c.text, (c.isWhite? "0": "1")};
			output.add(temp);
		}
			
		CSVWriter writer;
		try {
			writer = new CSVWriter(new java.io.FileWriter(filename + ".csv"), '|');
			writer.writeAll(output);
			writer.close();
			System.out.println("Card list saved.");
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
}
