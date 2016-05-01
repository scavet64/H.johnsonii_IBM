package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import controller.Simulation;
import model.Location;
import model.Seagrass;

public class SimulationView extends JPanel {
	
	private int xLength;
	private int yLength;
	private double zoom = 1;
	//private double percentage = 99;
	private int translationX = 0;
	private int translationY = 0;
	private int OFFSET = 0;
	private BasicStroke boldStroke = new BasicStroke(3);
	private BasicStroke thinStroke = new BasicStroke(1);
	private AffineTransform at = new AffineTransform();
	private boolean zooming;
	private int startingScale = 1000;
	
	
	private ArrayList<Seagrass> population;	
	//private Simulation simulation;
	
	public SimulationView(Simulation simulation){
		
		//this.simulation = simulation;
		Dimension simDimension = simulation.getDimension();
		//xLength = 150 * 10;
		//yLength = 50 * 10;
		
		population = simulation.getPopulation();
		
		xLength = (int) simDimension.getWidth() * startingScale;
		yLength = (int) simDimension.getHeight() * startingScale ;
		this.setBackground(Color.black);
		updateUI();
		
		this.addMouseWheelListener(new MouseWheelListener(){

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation() < 0){
					//translationX = (int) (-e.getX() * zoom);
					//translationY = (int) (-e.getY() * zoom);
					zoom += zoom/2;
					zooming = true;
					repaint();
					//zoom += percentage / zoom;
				} else if(e.getWheelRotation() > 0){
					zoom-= zoom/2;
					zooming = true;
					repaint();
					//translationX = (int) (-e.getX() * zoom);
					//translationY = (int) (-e.getY() * zoom);
					//zoom -= percentage / zoom;
				} else if(e.isShiftDown()){
					//zoom = 1;
				}
				updateUI();
			}
			
			
		});
		
		
		
		InputMap inMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		//create actions for input map
				Action translateUp = new AbstractAction(){
					private static final long serialVersionUID = 1L;
					@Override
					public void actionPerformed(ActionEvent e) {
						translationY -= 50;
						repaint();
					}
				};
				
				Action translateLeft = new AbstractAction(){
					private static final long serialVersionUID = 1L;
					@Override
					public void actionPerformed(ActionEvent e) {
						translationX -= 50;
						repaint();
					}
				};
				
				Action translateRight = new AbstractAction(){
					private static final long serialVersionUID = 1L;
					@Override
					public void actionPerformed(ActionEvent e) {
						translationX += 50;
						repaint();
					}
				};
				
				Action translateDown = new AbstractAction(){
					private static final long serialVersionUID = 1L;
					@Override
					public void actionPerformed(ActionEvent e) {
						translationY += 50;
						repaint();
					}
				};
				
				//TODO Look into multiplying translation points when zooming
				Action zoomIn = new AbstractAction(){
					private static final long serialVersionUID = 1L;
					@Override
					public void actionPerformed(ActionEvent e) {
						zoom += zoom/2;
						zooming = true;
						translationX *= zoom;
						translationY *= zoom;
						repaint();
					}
				};
				
				Action zoomOut = new AbstractAction(){
					private static final long serialVersionUID = 1L;
					@Override
					public void actionPerformed(ActionEvent e) {
						zoom -= zoom/2;
						zooming = true;
						translationX *= zoom;
						translationY *= zoom;
						repaint();
					}
				};
				
				//place input keys into map
				inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "transUp");
				this.getActionMap().put("transUp", translateUp);
				inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "translateLeft");
				this.getActionMap().put("translateLeft", translateLeft);
				inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "translateRight");
				this.getActionMap().put("translateRight", translateRight);
				inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "translateDown");
				this.getActionMap().put("translateDown", translateDown);
				inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0), "zoomIn");
				this.getActionMap().put("zoomIn", zoomIn);
				inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_O, 0), "zoomOut");
				this.getActionMap().put("zoomOut", zoomOut);
				
				
	}
	
//	public void zoomIn(){
//		
//	}
//	
//	public void runSim(){
//		try {
//			updateUI();
//			simulation.runSimulation(0, 250);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}	
	
	/**
	 * Draw the CardImagePanel's image.
	 */
	@Override
	protected void paintComponent(Graphics g1) {
		super.paintComponent(g1);
        Graphics2D graphics = (Graphics2D) g1;
        //graphics.translate(translationX *zoom, translationY *zoom);
        if(zooming){
        	graphics.translate((this.getWidth()/2) + translationX, this.getHeight()/2 + translationY);
        	//graphics.translate((this.getWidth()/2), this.getHeight()/2);
        	//graphics.translate(translationX,translationY);
        	graphics.scale(zoom, zoom);
        	//graphics.translate(-(this.getWidth()/2), -(this.getHeight()/2));
        	graphics.translate(-(this.getWidth()/2) + translationX, -(this.getHeight()/2 + translationY));
        	//graphics.translate(translationX,translationY);
        	//translationX = (int) ((translationX + this.getWidth()/2) - (translationX*zoom)/2);
        	//translationY = (int) ((translationY + this.getHeight()/2) - ((translationX*zoom)/2));
        	//graphics.translate(translationX, translationY);
        	//graphics.translate(((translationX + this.getWidth()/2) - (translationX*zoom)/2), (translationY + this.getHeight()/2) - ((translationX*zoom)/2));
        	//graphics.translate(((this.getWidth()/2) - (translationX*zoom)/2), (this.getHeight()/2) - ((translationX*zoom)/2));
        	//graphics.translate(-translationX, -translationY);
        	zooming = false;      
       } else {
        	//graphics.scale(zoom, zoom);
        	//graphics.translate(translationX *zoom, translationY *zoom);
        	graphics.translate(translationX, translationY);
        	//graphics.translate((this.getWidth()/2), this.getHeight()/2);
        	graphics.scale(zoom, zoom);
        	//graphics.translate(-translationX, -translationY);
        	
        }
        //graphics.scale(zoom, zoom);
        graphics.setColor(Color.white);
        
        
        for(int x = OFFSET; x <= xLength+OFFSET; x=x+startingScale){
        	graphics.setStroke(boldStroke);
        	graphics.drawLine(x, OFFSET, x, yLength+OFFSET);
        }
        
        for(int y = 0; y <= yLength+OFFSET; y=y + startingScale){
        	graphics.drawLine(OFFSET, y, xLength+OFFSET, y);
        }
        graphics.setStroke(thinStroke);
        graphics.setColor(Color.green);
        boolean finishedWrite = false;
        while(!finishedWrite){
        try{
	        for(Seagrass seagrass: population){
	        	double seagrassX = seagrass.getLocation().getxLocation() * startingScale;
				double seagrassY = seagrass.getLocation().getyLocation() * startingScale;
	        	graphics.setColor(Color.green);
	        	g1.drawOval((int)seagrassX + OFFSET , (int)seagrassY + OFFSET, 1, 1);
	        	Location cl = seagrass.getChildLocation();
	        	if(cl != null){
	        		
	        		double seagrassChildX = cl.getxLocation() * startingScale;
	        		double seagrassChildY = cl.getyLocation() * startingScale;
	        		
	        		graphics.setColor(Color.CYAN);
	        		graphics.drawLine((int) seagrassX, (int)seagrassY, (int)seagrassChildX, (int)seagrassChildY);
	        	}
	        	Location bcl = seagrass.getBranchChildLocation();
	        	if(bcl != null){
	        		
	        		double seagrassChildX = bcl.getxLocation() * startingScale;
	        		double seagrassChildY = bcl.getyLocation() * startingScale;
	        		
	        		graphics.setColor(Color.ORANGE);
	        		graphics.drawLine((int) seagrassX, (int)seagrassY, (int)seagrassChildX, (int)seagrassChildY);
	        	}
	        }
	        finishedWrite = true;
        } catch(ConcurrentModificationException cme){
        		finishedWrite = false;
	        }
        }
	}
}
