/**
 * 
 */
package seagrass_Model_V1.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


import seagrass_Model_V1.Controller.Menu;
import seagrass_Model_V1.Model.SimulationOptions;

/**
 * @author Vincent Scavetta
 *
 */
public class MainGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	//sizes for this frame
	private static final int PREFERRED_WIDTH = 500;
	private static final int PREFERRED_HIGHT = 500;
	private static final Dimension PREFERRED_SIZE = new Dimension (PREFERRED_WIDTH,PREFERRED_HIGHT);
	private static final int MIN_WIDTH = 350;
	private static final int MIN_HIGHT = 350;
	private static final Dimension MIN_SIZE = new Dimension(MIN_WIDTH, MIN_HIGHT);
	private static final int MAX_WIDTH = 510;
	private static final int MAX_HIGHT = 510;
	private static final Dimension MAX_SIZE = new Dimension(MAX_WIDTH,MAX_HIGHT);
	private Menu menu;
	private SimulationOptions simulationOptions;
	private JPanel mainPanel = new JPanel();
	private JLabel topLabel = new JLabel();
	private JLabel errorLabel = new JLabel();
	private JButton finishButton = new JButton();
	private JPanel activeCenterPanel;

	/**
	 *
	 */
	public MainGUI() {
		simulationOptions = SimulationOptions.getInstance();
		this.menu = menu;
		//setResizable(false);
		
		//setIcon();
		
		//creates the actionListener to return to the main menu
		ActionListener finishListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				mainPanel.remove(activeCenterPanel);
				initializeMenu();
			}
		};
		
		//sets location of frame
		this.setLocation(650, 250);
		
		//set sizes, Color, text, and default close option
		mainPanel.setLayout(new BorderLayout());
		setSize(PREFERRED_SIZE);
		setMinimumSize(MIN_SIZE);
		setMaximumSize(MAX_SIZE);
		mainPanel.setBackground(Color.black);
		setTitle("Halophila johnsonii Model Beta 0.5");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//sets up the top label's properties
		topLabel.setForeground(Color.WHITE);
		topLabel.setSize(new Dimension(100,100));
		//topLabel.setFont(font.deriveFont(20.0f));
		topLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mainPanel.add(topLabel, BorderLayout.NORTH);
				
		//label down at the bottom, shows up if there is an error
		errorLabel = new JLabel(" ");
		errorLabel.setForeground(Color.red);
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mainPanel.add(errorLabel, BorderLayout.SOUTH);
		
		//adds the main panel to the frame and initializes the menu
		add(mainPanel);
		initializeMenu();
		finishButton.addActionListener(finishListener);
		this.setVisible(true);
	}

	private void initializeMenu() {
		setLocation(650, 250);
		errorLabel.setText("");
		setSize(PREFERRED_SIZE);
		topLabel.setText("Main Menu");
		topLabel.setForeground(Color.WHITE);
		errorLabel.setText(" ");
		MenuPanel menuPanel = new MenuPanel();
		
		ActionListener myButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JButton button = (JButton) event.getSource();
				switch(button.getText()){

				case "Run Simulation":
					//initializeSimulationGUI();
					break;

				case "Change Simulation Options":
					//edit options GUI
					mainPanel.remove(menuPanel);
					initializeEditOptionsGUI();
					break;

				case "View Previous Simulation Results":
					//store GUI
					//mainPanel.remove(menuPanel);
					//initializePreviousSimGUI();
					break;

				case "Quit":
					System.exit(0);
					break;
				}

			}
		};
		// Add listeners to buttons
		menuPanel.getRunSimButton().addActionListener(myButtonListener);
		menuPanel.getEditOptionsButton().addActionListener(myButtonListener);
		menuPanel.getViewPrevButton().addActionListener(myButtonListener);
		menuPanel.getQuitButton().addActionListener(myButtonListener);
		
		mainPanel.add(menuPanel, BorderLayout.CENTER);			
		mainPanel.updateUI();
	}
	
	private void initializePreviousSimGUI() {
		// TODO determine what information to show and how
		
	}

	private void initializeEditOptionsGUI() {
		topLabel.setText("Edit Simulation Options");
		setLocation(450, 200);
		setSize(new Dimension(850,750));
		
		//Adds the gui to the center
		
		finishButton.setText("Finish Editing");
		EditOptionsGUI editOptionsGUI = new EditOptionsGUI(simulationOptions, finishButton);
		mainPanel.add(editOptionsGUI, BorderLayout.CENTER);
		this.activeCenterPanel = editOptionsGUI;
		
		mainPanel.updateUI();
	}

	private void initializeSimulationGUI() {
		// TODO Auto-generated method stub
		
	}

	private void setIcon() {
		try {
			this.setIconImage(ImageIO.read(new File("Images\\icon.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}


}
