package view;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ControlPanel extends JPanel{
	
	private final Dimension BUTTON_SIZE = new Dimension(200, 25);
	private JButton finishButton, startButton, stopButton;
	

	/**
	 * @param finishButton
	 */
	public ControlPanel(JButton finishButton) {
		super();
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
		
		this.finishButton = finishButton;
		startButton = new JButton("Start Simulation");
		stopButton = new JButton("Stop Simulation");
		
		//set button sizes
		finishButton.setName("Exit");
		finishButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		finishButton.setHorizontalAlignment(SwingConstants.CENTER);
		finishButton.setMinimumSize(BUTTON_SIZE);
		finishButton.setMaximumSize(BUTTON_SIZE);
		
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.setHorizontalAlignment(SwingConstants.CENTER);
		startButton.setMinimumSize(BUTTON_SIZE);
		startButton.setMaximumSize(BUTTON_SIZE);
		
		stopButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		stopButton.setHorizontalAlignment(SwingConstants.CENTER);
		stopButton.setMinimumSize(BUTTON_SIZE);
		stopButton.setMaximumSize(BUTTON_SIZE);
		
		
		this.add(startButton);
		this.add(stopButton);
		this.add(finishButton);
	}
	
	
	//setters and getters

	/**
	 * @return the finishButton
	 */
	public JButton getFinishButton() {
		return finishButton;
	}

	/**
	 * @param finishButton the finishButton to set
	 */
	public void setFinishButton(JButton finishButton) {
		this.finishButton = finishButton;
	}

	/**
	 * @return the startButton
	 */
	public JButton getStartButton() {
		return startButton;
	}

	/**
	 * @param startButton the startButton to set
	 */
	public void setStartButton(JButton startButton) {
		this.startButton = startButton;
	}

	/**
	 * @return the pauseButton
	 */
	public JButton getStopButton() {
		return stopButton;
	}

	/**
	 * @param pauseButton the pauseButton to set
	 */
	public void setStopButton(JButton pauseButton) {
		this.stopButton = pauseButton;
	}
	
	

}
