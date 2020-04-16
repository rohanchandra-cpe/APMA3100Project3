import java.util.ArrayList;
import  java.lang.Math;

public class RandomNumberGenerator {
	static double xStart = 1000.0;
	static double nextNum = 0.0;
	
	static int a = 24693;
	static int c = 3967;
	static double K = Math.pow(2.0, 17.0);
	static int i = 0; 
	static int desiredNumber = 1000000; //To get values 51,52, and 53

	static double tau = 57;
	static double scaleParameter = 1/tau;
	static double expectedValue = (1 / scaleParameter) * Math.sqrt(Math.PI / 2);
	static double variance = (4 - Math.PI) / (2 * Math.pow(scaleParameter, 2));
	static double standardDeviation = Math.sqrt(variance);

	//static ArrayList<Double> numArray = new ArrayList<>();
	static double[] zj = {-1.4, -1.0, -0.5, 0, 0.5, 1.0, 1.4};
	static double[] zjNormalCDFValues = {0.0256, 0.0262, 0.0268, 0.0281, 0.0294, 0.0301, 0.0307};

	static ArrayList<Double> modArray = new ArrayList<>();
	static ArrayList<Double> m10 = new ArrayList<>();
	static ArrayList<Double> m30 = new ArrayList<>();
	static ArrayList<Double> m50 = new ArrayList<>();
	static ArrayList<Double> m100 = new ArrayList<>();
	static ArrayList<Double> m150 = new ArrayList<>();
	static ArrayList<Double> m250 = new ArrayList<>();
	static ArrayList<Double> m500 = new ArrayList<>();
	static ArrayList<Double> m1000 = new ArrayList<>();

	static ArrayList<Double> z10 = new ArrayList<>();
	static ArrayList<Double> z30 = new ArrayList<>();
	static ArrayList<Double> z50 = new ArrayList<>();
	static ArrayList<Double> z100 = new ArrayList<>();
	static ArrayList<Double> z150 = new ArrayList<>();
	static ArrayList<Double> z250 = new ArrayList<>();
	static ArrayList<Double> z500 = new ArrayList<>();
	static ArrayList<Double> z1000 = new ArrayList<>();

	static ArrayList<Double> f10 = new ArrayList<>();
	static ArrayList<Double> f30 = new ArrayList<>();
	static ArrayList<Double> f50 = new ArrayList<>();
	static ArrayList<Double> f100 = new ArrayList<>();
	static ArrayList<Double> f150 = new ArrayList<>();
	static ArrayList<Double> f250 = new ArrayList<>();
	static ArrayList<Double> f500 = new ArrayList<>();
	static ArrayList<Double> f1000 = new ArrayList<>();


	public static void main(String[] args) {
		//call random num gen
		randomNumGen();

		//print out u51, u52, u53
		for(int i = 51; i <= 53 ; i++){
			System.out.println(modArray.get(i));
		}

		System.out.println("------------------------------");

		//do the mean 110 times using a sample size of values. 
		m10 = samples110(10);
		m30 = samples110(30);
		m50 = samples110(50);
		m100 = samples110(100);
		m150 = samples110(150);
		m250 = samples110(250);
		m500 = samples110(500);
		m1000 = samples110(1000);

		z10 = standardizedSamples110(m10, 10);
		z30 = standardizedSamples110(m30, 30);
		z50 = standardizedSamples110(m50, 50);
		z100 = standardizedSamples110(m100, 100);
		z150 = standardizedSamples110(m150, 150);
		z250 = standardizedSamples110(m250, 250);
		z500 = standardizedSamples110(m500, 500);
		z1000 = standardizedSamples110(m1000, 1000);

		f10 = calculateCDFs(z10);
		f30 = calculateCDFs(z30);
		f50 = calculateCDFs(z50);
		f100 = calculateCDFs(z100);
		f150 = calculateCDFs(z150);
		f250 = calculateCDFs(z250);
		f500 = calculateCDFs(z500);
		f1000 = calculateCDFs(z1000);

		double mad10 = calculateMadMax(f10);
		System.out.println(mad10);
		double mad30 = calculateMadMax(f30);
		System.out.println(mad30);
		double mad50 = calculateMadMax(f50);
		System.out.println(mad50);
		double mad100 = calculateMadMax(f100);
		System.out.println(mad100);
		double mad150 = calculateMadMax(f150);
		System.out.println(mad150);
		double mad250 = calculateMadMax(f250);
		System.out.println(mad250);
		double mad500 = calculateMadMax(f500);
		System.out.println(mad500);
		double mad1000 = calculateMadMax(f1000);
		System.out.println(mad1000);

		System.out.println("All values calculated!");

	}
	/**
	 * Generates a random number betwwen 0 and 1. Adds these values to modarray.
	 */
	public static void randomNumGen() {
		//numArray.add(xStart);
		//modArray.add(xStart % K);
		while(i < desiredNumber) { 
			nextNum = ((a * xStart) + c) % K;
			xStart = nextNum;
			//numArray.add(nextNum);
			modArray.add(nextNum / K);
			nextNum = 0;
			i++;
		}
	}
	/**
	 * Function to calculate the inverse of F(x) given a = 1/57 for raleigh cdf
	 *     1 - e^( -.5 * (1/57)^2 * x^2)
	 * Used wolfram to find the inverse
	 * @param u the nummber from 
	 * @return
	 */
	public static double invFx(double u){
		double x = 57*Math.sqrt(2.0) * Math.sqrt( Math.log(1 / (1-u)) );
		return x;
	}
	/**
	 * Function to create an arraylist of 110 sample means and print them to terminal
	 * @param n sample size of each mean value
	 * @return arraylist size 110 of each sample mean
	 */
	public static ArrayList<Double> samples110(int n){
		ArrayList<Double> ret = new ArrayList<Double>();
		for(int i = 0; i < 110; i++){
			double mean = 0;
			for(int j = 0; j < n; j++){
				double u = modArray.remove(0);
				//System.out.println(invFx(u));
				mean += invFx(u);
			}
			mean = mean/ ( n);
			ret.add(mean);
			//System.out.println(n + " " + mean);
		}
		return ret;
	}
	/**
	 * Function to generate an arraylist of 100 standardized values. 
	 * @param arg
	 * @param n
	 * @return
	 */
	public static ArrayList<Double> standardizedSamples110(ArrayList<Double> arg, int n){
		ArrayList<Double> returnList = new ArrayList<>();
		for(Double mVal: arg){
			double toBeInserted = (mVal - expectedValue) / (standardDeviation / Math.sqrt(n));
			//System.out.println(n + ": " + toBeInserted);
			returnList.add(toBeInserted);
		}
		return returnList;
	}
	public static ArrayList<Double> calculateCDFs(ArrayList<Double> zArray){
		double lessThanCount = 0;
		double valToInsert = 0.0;
		ArrayList<Double> retArray = new ArrayList<>();
		for(double z: zj){
			for(double zArrayVal: zArray){
				if(zArrayVal <= z){
					lessThanCount++;
				}
				else{
					//Nothing
				}
			}
			valToInsert = lessThanCount / 110;
			lessThanCount = 0;
			retArray.add(valToInsert);
		}
		return retArray;
	}
	public static double calculateMadMax(ArrayList<Double> fArray){
		ArrayList<Double> MADVals = new ArrayList<>();
		for(int i = 0; i < zjNormalCDFValues.length; i++){
			MADVals.add(Math.abs(fArray.get(i) - zjNormalCDFValues[i]));
		}
		return MADVals.get(MADVals.size() - 1);
	}
	
}
