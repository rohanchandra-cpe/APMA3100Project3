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
	static ArrayList<Double> numArray = new ArrayList<>();
	static ArrayList<Double> modArray = new ArrayList<>();
	static ArrayList<Double> m10 = new ArrayList<>();
	static ArrayList<Double> m30 = new ArrayList<>();
	static ArrayList<Double> m50 = new ArrayList<>();
	static ArrayList<Double> m100 = new ArrayList<>();
	static ArrayList<Double> m150 = new ArrayList<>();
	static ArrayList<Double> m250 = new ArrayList<>();
	static ArrayList<Double> m500 = new ArrayList<>();
	static ArrayList<Double> m1000 = new ArrayList<>();

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
		System.out.println("All values calculated!");

	}
	/**
	 * Generates a random number betwwen 0 and 1. Adds these values to modarray.
	 */
	public static void randomNumGen() {
		numArray.add(xStart);
		//modArray.add(xStart % K);
		while(i < desiredNumber) { 
			nextNum = ((a * xStart) + c) % K;
			xStart = nextNum;
			numArray.add(nextNum);
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
				mean += invFx(numArray.remove(0));
			}
			mean = mean/ ((double) n);
			ret.add(mean);
			System.out.println(n + " " + mean);
		}
		return ret;
	}
	
	
}
