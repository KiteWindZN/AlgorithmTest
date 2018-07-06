package AntAlgorithm;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		ACO aco=new ACO();
		int antNum=10000;
		int generationNum=20;
		aco.init("cities.txt", antNum);
		aco.run(generationNum);
		aco.getBestLength();
	}

}
