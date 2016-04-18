package model;

public class SolarQuantile {

	private int percentileNumber;
	private double upperIrradiance;
	private double lowerIrradiance;

	
	public SolarQuantile(int percentileNumber, double lowerIrradiance, double upperIrradiance){
		this.percentileNumber = percentileNumber;
		this.lowerIrradiance = lowerIrradiance;
		this.upperIrradiance = upperIrradiance;
	}


	/**
	 * @return the percentileNumber
	 */
	public int getPercentileNumber() {
		return percentileNumber;
	}


	/**
	 * @param percentileNumber the percentileNumber to set
	 */
	public void setPercentileNumber(int percentileNumber) {
		this.percentileNumber = percentileNumber;
	}


	/**
	 * @return the irradiance
	 */
	public double getUpperIrradiance() {
		return upperIrradiance;
	}


	/**
	 * @param irradiance the irradiance to set
	 */
	public void setUpperIrradiance(double upperIrradiance) {
		this.upperIrradiance = upperIrradiance;
	}


	/**
	 * @return the lowerIrradiance
	 */
	public double getLowerIrradiance() {
		return lowerIrradiance;
	}


	/**
	 * @param lowerIrradiance the lowerIrradiance to set
	 */
	public void setLowerIrradiance(double lowerIrradiance) {
		this.lowerIrradiance = lowerIrradiance;
	}
	
	
	
}
