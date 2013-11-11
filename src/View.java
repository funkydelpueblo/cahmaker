/** The application view for the interface.
 * @author funkydelpueblo
 * @version 0.1
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

public class View {
	
	private JTextField text;
	private JComboBox color;
	private JList whiteCards;
	private JList blackCards;
	
	public JPanel makePanel(){
		JPanel panel = new JPanel(new MigLayout("", "[][][]", "[][grow]10[][grow]10[]2[]5[]"));
		
		panel.add(new JLabel("White Cards"), "cell 0 0");
		java.util.ArrayList<Card> white = Model.getInstance().getAllWhite();
		whiteCards = new JList(white.toArray());
		javax.swing.JScrollPane scrollerW = new javax.swing.JScrollPane(whiteCards);
		panel.add(scrollerW, "cell 0 1, span 3");
		
		panel.add(new JLabel("Black Cards"), "cell 0 2");
		java.util.ArrayList<Card> black = Model.getInstance().getAllBlack();
		blackCards = new JList(black.toArray());
		blackCards.setBackground(Color.BLACK);
		blackCards.setForeground(Color.WHITE);
		javax.swing.JScrollPane scrollerB = new javax.swing.JScrollPane(blackCards);
		panel.add(scrollerB, "cell 0 3, span 3");
		
		whiteCards.addListSelectionListener(new CardSelectedListener(true));
		blackCards.addListSelectionListener(new CardSelectedListener(false));
		
		text = new JTextField(50);
		panel.add(text, "cell 0 4, grow");
		
		JButton add = new JButton("Add card");
		JButton remove = new JButton("Remove");
		JButton edit = new JButton("Edit");
		
		add.addActionListener(new AddCardListener());
		
		String[] cols = {"White", "Black"};
		color = new JComboBox(cols);
		
		panel.add(add, "cell 1 4");
		panel.add(color, "cell 2 4");
		
		panel.add(new JLabel("Selected Card:"), "cell 0 6, align right");
		panel.add(remove, "cell 1 6");
		panel.add(edit, "cell 2 6");
		
		return panel;
		
	}
	
	public JMenuBar makeMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu print = new JMenu("Print");
		
		JMenuItem save = new JMenuItem("Save");
		JMenuItem load = new JMenuItem("Load");
		JMenuItem printWhite = new JMenuItem("Print white cards");
		JMenuItem printBlack = new JMenuItem("Print black cards");
		JMenuItem whiteBacks = new JMenuItem("Print white backs");
		JMenuItem blackBacks = new JMenuItem("Print black backs");
		
		load.addActionListener(new ImportCardsListener());
		save.addActionListener(new ExportCardsListener());
		printWhite.addActionListener(new PrintMenuListener(true, true));
		printBlack.addActionListener(new PrintMenuListener(false, true));
		whiteBacks.addActionListener(new PrintMenuListener(true, false));
		blackBacks.addActionListener(new PrintMenuListener(false, false));
		
		file.add(save);
		file.add(load);
		print.add(printWhite);
		print.add(printBlack);
		print.addSeparator();
		print.add(whiteBacks);
		print.add(blackBacks);
		menuBar.add(file);
		menuBar.add(print);
		
		return menuBar;
	}
	
	//
	// Button Listeners
	//
	
	private class AddCardListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//Create new card
			String content = text.getText();
			boolean isWhite = color.getSelectedIndex() == 0;
			Model.getInstance().addCard(isWhite, content);
			
			//Reset text box
			text.setText("");
			
			//Update list
			if(isWhite){
				DefaultComboBoxModel model = new DefaultComboBoxModel(Model.getInstance().getAllWhite().toArray());
				whiteCards.setModel(model);
			}else{
				DefaultComboBoxModel model = new DefaultComboBoxModel(Model.getInstance().getAllBlack().toArray());
				blackCards.setModel(model);
			}
		}
		
	}
	
	//
	// Menu Listeners
	//
	
	private class ImportCardsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(fc);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File cards = fc.getSelectedFile();
	            try {
	            	//Read in activities
	            	CSVUtilities.readInCards(cards.getCanonicalPath());
	            	
					//Update Panel
	            	DefaultComboBoxModel modelW = new DefaultComboBoxModel(Model.getInstance().getAllWhite().toArray());
					whiteCards.setModel(modelW);
					DefaultComboBoxModel modelB = new DefaultComboBoxModel(Model.getInstance().getAllBlack().toArray());
					blackCards.setModel(modelB);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	            System.out.println("Card list imported successfully.");
	        } else {
	            System.out.println("cannot open file");
	        }
		}
		
	}
	
	private class ExportCardsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(fc);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File toSave = fc.getSelectedFile();
	            try {
	            	//Read in activities
	            	CSVUtilities.exportCards(toSave.getCanonicalPath());
	            	
	            	//Provide feedback
	            	//TODO
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	            System.out.println("Card list imported successfully.");
	        } else {
	            System.out.println("cannot open file");
	        }
		}
		
	}
	
	private class PrintMenuListener implements ActionListener{

		private boolean isWhite;
		private boolean fronts;
		
		public PrintMenuListener(boolean white, boolean f){
			this.isWhite = white;
			this.fronts = f;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(this.fronts){
				PrintUtility.printCards(isWhite);
			}else{
				PrintUtility.printCardBacks(isWhite);
			}
		}
		
	}
	
	//
	// Card Selection (ensures only one card selected at once)
	//
	
	private class CardSelectedListener implements ListSelectionListener{

		private boolean isWhite;
		
		public CardSelectedListener(boolean white){
			this.isWhite = white;
		}
		
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if(!arg0.getValueIsAdjusting()){
				if(this.isWhite){
					blackCards.clearSelection();
					whiteCards.grabFocus(); 
				}else{
					whiteCards.clearSelection();
					blackCards.grabFocus();
				}
			}	
		}
	}
	
	//
	// Edit current card
	//
	
	//todo
	
}
