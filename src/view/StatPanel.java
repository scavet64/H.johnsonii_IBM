package view;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Simulation;

public class StatPanel extends JPanel {
	
	private final JLabel staticPopNumberLabel = new JLabel("Total Population Size");
	private final JLabel staticDayNumber = new JLabel("Day Number");
	private JLabel dynamicPopNumberLabel = new JLabel("0");
	private JLabel dynamicDayNumberLabel = new JLabel("0");

	public StatPanel(){
		super();
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		//add to panel
		add(Box.createVerticalGlue());
		add(Box.createHorizontalGlue());
		add(staticPopNumberLabel);
		add(Box.createRigidArea(new Dimension(2, 2)));
		add(dynamicPopNumberLabel);
		add(Box.createRigidArea(new Dimension(100, 2)));
		add(staticDayNumber);
		add(Box.createRigidArea(new Dimension(2, 2)));
		add(dynamicDayNumberLabel);
		add(Box.createRigidArea(new Dimension(2, 2)));
		add(Box.createVerticalGlue());
		add(Box.createHorizontalGlue());
	}

	public void updatePopulationLabel(Integer populationNumber) {
		dynamicPopNumberLabel.setText(populationNumber.toString());
		
	}
	
	public void updateDayLabel(Integer currentDay) {
		dynamicDayNumberLabel.setText(currentDay.toString());
		
	}
	
}
