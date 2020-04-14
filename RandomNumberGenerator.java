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
		for(int i = 51; i < 53 ; i++){
			System.out.println(modArray.get(i));
		}

		System.out.println("------------------------------");

		//do the mean 110 times using a sample size of 10. 
		//TODO Maybe should abstract this into a function?
		for(int i = 0; i < 110; i++){
			double mean = 0;
			for(int j = 0; j < 10; j++){
				mean += invFx(numArray.remove(0));
			}
			m10.add(mean/10);
		}
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
	
	
}
