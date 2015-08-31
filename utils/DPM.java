package utils;

import java.util.ArrayList;

public class DPM {
	int numActiveClusters;
	int numObjects;
	int concentrationParameter;
	Multinomial[] mixtureComponents;
	ArrayList<Integer[]> inputData;
	Integer[] hardClusterBelongingness;
	Double[] numObjectsPerCluster;
}
