package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.Simulation;

public class SimulationGUI extends JPanel{

	private SimulationView simView;
	private ControlPanel controlPanel;
	private StatPanel statPanel;
	private SimulationThread thread;
	private boolean isPaused;
	private boolean hasStarted;
	
	/**
	 * @param simView
	 * @param finishButton
	 */
	public SimulationGUI(Simulation sim, JButton finishButton) {
		this.isPaused = false;
		this.hasStarted = false;
		this.thread = new SimulationThread(sim);
		
		controlPanel = new ControlPanel(finishButton);
		statPanel = new StatPanel();
		this.simView = new SimulationView(sim);
		
		sim.setSimGUI(this);
		
		this.setLayout(new BorderLayout());
		this.add(simView, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.EAST);
		this.add(statPanel, BorderLayout.NORTH);
		
		ActionListener startSim = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!hasStarted){
					thread.start();
					hasStarted = true;
				}
			}
			
		};
		
		ActionListener stopSim = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(hasStarted){
					thread.interrupt();
				} else {
					//show error or do nothing TODO
				}
			}
			
		};
		
		//TODO look into if pausing is possible
		ActionListener pauseSim = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(isPaused && hasStarted){
					thread.notify();
				} else if(hasStarted){
					try {
						thread.wait();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//thread.interrupt();
					isPaused = true;
				} else {
					//show error or do nothing TODO
				}
			}
			
		};
		
		controlPanel.getStartButton().addActionListener(startSim);
		controlPanel.getStopButton().addActionListener(stopSim);
	}
	
	public void updateDayLabel(int dayCounter){
		statPanel.updateDayLabel(dayCounter);
	}
	
	public void updatePopulationLabel(int populationNumber){
		statPanel.updatePopulationLabel(populationNumber);
	}
	
	
	class SimulationThread extends Thread{
		Simulation sim;
		public SimulationThread(Simulation sim){
			this.sim = sim;
		}
        
		public void run(){
			try {
				sim.runSimulation();
				//pause();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
	/**
	 * @return the simView
	 */
	public SimulationView getSimView() {
		return simView;
	}

	/**
	 * @param simView the simView to set
	 */
	public void setSimView(SimulationView simView) {
		this.simView = simView;
	}

	public void repaintView() {
		simView.repaint();
	}	
}
