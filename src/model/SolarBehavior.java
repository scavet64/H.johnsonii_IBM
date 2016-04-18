package model;

import java.util.HashMap;
import java.util.Random;

public class SolarBehavior {

	public static final Integer JAN = 0;
	public static final Integer FEB = 1;
	public static final Integer MAR = 2;
	public static final Integer APRIL = 3;
	public static final Integer MAY = 4;
	public static final Integer JUNE = 5;
	public static final Integer JULY = 6;
	public static final Integer AUG = 7;
	public static final Integer SEP = 8;
	public static final Integer OCT = 9;
	public static final Integer NOV = 10;
	public static final Integer DEC = 11;
	
	public static final int ZERO = 0;
	public static final int TWENTYFIVE = 1;
	public static final int FIFTY = 2;
	public static final int SEVENTYFIVE = 3;
	public static final int ONEHUNDRED = 4;
	
	private int numberMonths = 12;
	private int numberQuantiles = 5;
	private int previousLowerQuantile = 0;
	
	private boolean isStorming;
	private int stormCounter;
	
	private Random rng = new Random();
	
	
	//private HashMap<Integer, SolarQuantile> monthToSolarQuantileMap = new HashMap<Integer, SolarQuantile>();
	private double[][] irradianceTable = new double[numberMonths][numberQuantiles];
	private int[] daysInMonthArray = {31,28,31,30,31,30,31,31,30,31,30,31};
	
	public SolarBehavior(){
		//create data based on Miami solar data
		createTable();
	}
	
	/**
	 * Returns a random irradiance value based upon the upper and lower quantiles passed into this function.
	 * The method uses the table created with the data from the NOAA Solar data set.
	 * @param day day to find irradiance for
	 * @param upperQuantile the upper quantile
	 * @param lowerQuantile the lower quantile
	 * @return random irradiance based upon the upper and lower quantiles
	 */
	public double getRandomIrradianceForDay(int day){
		int month = getMonthFromDay(day);
		
		int lowerQuantile = getLowerQuantileForDay(day);
		int upperQuantile = lowerQuantile + 1;
		
		
		double upperQuantileIrradiance = irradianceTable[month][upperQuantile];
		double lowerQuantileIrradiance = irradianceTable[month][lowerQuantile];
		
		//calculate random number between
		double randomValue = rng.nextDouble();
		double irradiance = ((upperQuantileIrradiance - lowerQuantileIrradiance) * randomValue) + lowerQuantileIrradiance;
		
		return irradiance;
	}
	
	/**
	 * Returns a quantile value for the given day. This method will determine if there will 
	 * be a storm today. Only returns up to 75% quantile.
	 * @param day
	 * @return
	 */
	private int getLowerQuantileForDay(int day) {
		int lowerQuantile;
		
		if(day % 1 == 0){
			//stormy day every 15 days (approx 2 per month)
			lowerQuantile = ZERO;
			isStorming = true;
		} else {
			//sunny day
			lowerQuantile = SEVENTYFIVE;
			isStorming = false;
		}
		
		//This could be used later when we want to randomize storms :TODO
//		if(isStorming && stormCounter < 1){
//			lowerQuantile = previousLowerQuantile;
//		} else {
//		
//		}
		return lowerQuantile;
	}

	/**
	 * returns the irradiance for the given day and quantile
	 * @param day
	 * @param quantile
	 * @return
	 */
	private double getIrradianceForDayPerQuantile(int day, int quantile){
		return irradianceTable[getMonthFromDay(day)][quantile];
	}
	
	private int getMonthFromDay(int day){
		int month = 0;
		
		for(int i = 0; day > 0; i++){
			day -= daysInMonthArray[i];
		}
		
		return month;
	}
	
	public boolean isStorming() {
		return isStorming;
	}

	private void createTable(){
		
		irradianceTable[JAN][ZERO] 			= 0.424;
		irradianceTable[JAN][TWENTYFIVE] 	= 0.436;
		irradianceTable[JAN][FIFTY] 		= 0.619;
		irradianceTable[JAN][SEVENTYFIVE] 	= 0.699;
		irradianceTable[JAN][ONEHUNDRED] 	= 0.806;
		
		irradianceTable[FEB][ZERO] 			= 0.264;
		irradianceTable[FEB][TWENTYFIVE] 	= 0.514;
		irradianceTable[FEB][FIFTY] 		= 0.734;
		irradianceTable[FEB][SEVENTYFIVE] 	= 0.782;
		irradianceTable[FEB][ONEHUNDRED] 	= 0.866;
		
		irradianceTable[MAR][ZERO] 			= 0.183;
		irradianceTable[MAR][TWENTYFIVE] 	= 0.584;
		irradianceTable[MAR][FIFTY] 		= 0.738;
		irradianceTable[MAR][SEVENTYFIVE] 	= 0.907;
		irradianceTable[MAR][ONEHUNDRED] 	= 0.968;
		
		irradianceTable[APRIL][ZERO] 		= 0.372;
		irradianceTable[APRIL][TWENTYFIVE] 	= 0.821;
		irradianceTable[APRIL][FIFTY] 		= 0.899;
		irradianceTable[APRIL][SEVENTYFIVE]	= 0.946;
		irradianceTable[APRIL][ONEHUNDRED] 	= 0.975;
		
		irradianceTable[MAY][ZERO] 			= 0.262;
		irradianceTable[MAY][TWENTYFIVE] 	= 0.748;
		irradianceTable[MAY][FIFTY] 		= 0.868;
		irradianceTable[MAY][SEVENTYFIVE] 	= 0.921;
		irradianceTable[MAY][ONEHUNDRED] 	= 1.000;
		
		irradianceTable[JUNE][ZERO] 		= 0.212;
		irradianceTable[JUNE][TWENTYFIVE] 	= 0.415;
		irradianceTable[JUNE][FIFTY] 		= 0.549;
		irradianceTable[JUNE][SEVENTYFIVE] 	= 0.799;
		irradianceTable[JUNE][ONEHUNDRED] 	= 0.946;
		
		irradianceTable[JULY][ZERO] 		= 0.377;
		irradianceTable[JULY][TWENTYFIVE] 	= 0.768;
		irradianceTable[JULY][FIFTY] 		= 0.858;
		irradianceTable[JULY][SEVENTYFIVE] 	= 0.925;
		irradianceTable[JULY][ONEHUNDRED] 	= 0.968;
		
		irradianceTable[AUG][ZERO] 			= 0.153;
		irradianceTable[AUG][TWENTYFIVE] 	= 0.713;
		irradianceTable[AUG][FIFTY] 		= 0.831;
		irradianceTable[AUG][SEVENTYFIVE] 	= 0.865;
		irradianceTable[AUG][ONEHUNDRED] 	= 0.925;
		
		irradianceTable[SEP][ZERO] 			= 0.220;
		irradianceTable[SEP][TWENTYFIVE] 	= 0.608;
		irradianceTable[SEP][FIFTY] 		= 0.795;
		irradianceTable[SEP][SEVENTYFIVE] 	= 0.873;
		irradianceTable[SEP][ONEHUNDRED] 	= 0.961;
		
		irradianceTable[OCT][ZERO] 			= 0.179;
		irradianceTable[OCT][TWENTYFIVE] 	= 0.476;
		irradianceTable[OCT][FIFTY] 		= 0.616;
		irradianceTable[OCT][SEVENTYFIVE] 	= 0.713;
		irradianceTable[OCT][ONEHUNDRED] 	= 0.884;
		
		irradianceTable[NOV][ZERO] 			= 0.143;
		irradianceTable[NOV][TWENTYFIVE] 	= 0.468;
		irradianceTable[NOV][FIFTY] 		= 0.656;
		irradianceTable[NOV][SEVENTYFIVE] 	= 0.702;
		irradianceTable[NOV][ONEHUNDRED] 	= 0.770;
		
		irradianceTable[DEC][ZERO] 			= 0.165;
		irradianceTable[DEC][TWENTYFIVE] 	= 0.448;
		irradianceTable[DEC][FIFTY] 		= 0.633;
		irradianceTable[DEC][SEVENTYFIVE] 	= 0.667;
		irradianceTable[DEC][ONEHUNDRED] 	= 0.748;

		
	}
	
	
}
