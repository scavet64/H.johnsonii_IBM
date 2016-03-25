package model;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Patch {
	
	private LinkedBlockingQueue<Seagrass> patch;
	private double meanDensity;
	private double stdevDensity;
	private double meanLight;
	private double stdevLight;
	private double meanDepth;
	private double stdevDepth;
	
	
	public Patch(){
		LinkedBlockingQueue<Seagrass> patch = new LinkedBlockingQueue<Seagrass>();
	}
	
	public Patch(LinkedBlockingQueue<Seagrass> patch){
		this.patch = patch;
	}
	
	public boolean add(Seagrass seagrass){
		return patch.add(seagrass);
	}
	
	public Seagrass remove(){
		return patch.remove();
	}
	
	public int getSize(){
		return patch.size();
	}
	
	

}
