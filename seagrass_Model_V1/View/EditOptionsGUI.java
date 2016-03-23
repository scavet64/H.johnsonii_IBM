package seagrass_Model_V1.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import seagrass_Model_V1.Model.Attribute;
import seagrass_Model_V1.Model.SimulationOptions;

public class EditOptionsGUI extends JPanel {
	
	private final int NUMBER_ROWS = 10;
	private final int NUMBER_COL = 5;
	private final SimulationOptions simOptions;
	private JButton finishButton;
	private JPanel fields;
	
	public EditOptionsGUI(SimulationOptions simOptions, JButton finishButton){
		this.finishButton = finishButton;
		
		//setup the saveButton
		JButton saveButton = new JButton("Apply Options");
		
		ActionListener saveListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int numberOfFields = fields.getComponentCount();
				for(int i = 0; i < numberOfFields; i++){
					FieldPanel fp = (FieldPanel) fields.getComponent(i);
					try {
						double value = fp.getDoubleFromBox();
						System.out.println("button pressed");
						simOptions.applyModification(fp.getEditableAttributes(), value);
					} catch(NumberFormatException nfe){
						//wrong format error
					} catch(Exception ex){
						//no number error
					}
				}
				
			}
			
		};
		
		saveButton.addActionListener(saveListener);
		
		//setup the fields
		fields = new JPanel();
		fields.setLayout(new GridLayout(NUMBER_ROWS,NUMBER_COL));
		//setup the fieldPanels
		fields.add(new FieldPanel(Attribute.XLength, simOptions.getxLength()));
		fields.add(new FieldPanel(Attribute.YLength, simOptions.getyLength()));
		fields.add(new FieldPanel(Attribute.NumDays, simOptions.getNumberDays()));
		fields.add(new FieldPanel(Attribute.NumYears, simOptions.getNumberYears()));
		fields.add(new FieldPanel(Attribute.NumRecruits, simOptions.getNumRecruits()));
		
		//setup button panel on bottom
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.add(saveButton);
		buttonsPanel.add(finishButton);
		
		
		this.setLayout(new BorderLayout());
		this.add(fields, BorderLayout.CENTER);
		this.add(buttonsPanel, BorderLayout.SOUTH);
		
		this.simOptions = simOptions;
	}
	
	public JButton getBackButton(){
		return finishButton;
	}

}
