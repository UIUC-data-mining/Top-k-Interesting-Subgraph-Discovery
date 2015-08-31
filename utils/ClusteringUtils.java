package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ClusteringUtils {
	/**
	 * This is a Java version of the NIPSdemo of the tutorial by Yee Whye Teh
	 * Original Matlab code at http://pmtksupport.googlecode.com/svn/trunk/dpmixturesTeh07/
	 * References made to a pdf are for this pdf: http://videolectures.net/site/normal_dl/tag=11014/teh_yee_whye_dp_talk.pdf
	 * Test code:
	 * 	Double[][] matrix = SharedUtils.getRandomMatrix(1000, 100);
		Integer[][] counts = new Integer[matrix.length][matrix[0].length];
		for(int i=0;i<matrix.length;i++)
			for(int j=0;j<matrix[0].length;j++)
				counts[i][j]=(int) (matrix[i][j]*10000);
		SharedUtils.dirichletProcessMixtureModelClustering(counts, "C:\\Users\\manish\\Desktop\\research\\current\\ECODA2\\junk\\tmp.txt");
	 * @param data
	 * @param outputFile
	 * @throws Throwable
	 */
	public static Integer[][] dpmhardClusterBelongingnessSamples;
	public static void dirichletProcessMixtureModelClustering(Integer[][] data, String outputFile) throws Throwable {
		data=(Integer[][]) MatrixUtils.transposeMatrix(data);
		dpmhardClusterBelongingnessSamples = new Integer[5000][data[0].length];//500
		System.out.println("Loading data done\n");
		System.out.println("Starting Dirichlet based clustering ... ");
		int numDimensions = data.length;//num of words
		int numObjects = data[0].length;//num of papers
		int maxActiveClusters = 1;//max number of expected clusters
		int concParam = 2;//alpha controls the a priori expected number of clusters.
		int bb = 10000;//Parameter b controls the number of words assigned to each cluster
		int burnIn=10000;//1000
		int numiter = burnIn+dpmhardClusterBelongingnessSamples.length*10;
		HH hh = new HH(numDimensions, bb);
		// construct data
		ArrayList<Integer[]> inputData = new ArrayList<Integer[]>(); //representation of a paper in the words dimension space.
		//inputData.get(i) gives the word vector corresponding to the paper i.
		for(int i=0;i<data[0].length;i++)
		{
			Integer arr[] = new Integer[data.length];
			for(int j=0;j<arr.length;j++)
				arr[j]=data[j][i];
			inputData.add(arr);
		}
		// initialize cluster assignment to each paper.
		Integer initialClusterAssignment [] = new Integer[numObjects];
		for(int i=0;i<numObjects;i++)
			initialClusterAssignment[i]=(int)(Math.random()*maxActiveClusters);

		// initialize finite mixture
		DPM dpm = dpm_init(maxActiveClusters,concParam, hh, inputData,initialClusterAssignment);

		// run
//		Integer[] oldZZ;
		int tick=0;
		int sampleCount=0;
		for(int iter=0;iter<numiter;iter++)
		{
//			oldZZ=new Integer[dpm.hardClusterBelongingness.length];
//			for(int i=0;i<dpm.hardClusterBelongingness.length;i++)
//				oldZZ[i]=dpm.hardClusterBelongingness[i];
		  //gibbs iteration 
		    if(iter==0)
		    	dpm = dpm_gibbs(dpm,1,1);
		    else
		    	dpm = dpm_gibbs(dpm,1,0);
		    if(iter>=burnIn)
		    {
		    	if(tick==0)
		    	{
		    		for(int i=0;i<dpm.hardClusterBelongingness.length;i++)
		    			dpmhardClusterBelongingnessSamples[sampleCount][i]=dpm.hardClusterBelongingness[i];
		    		sampleCount++;
		    	}
		    	tick++;
		    	if(tick==10)
		    		tick=0;
		    }
		  // record keeping
		  int num=0;
		  for(int i=0;i<dpm.numObjectsPerCluster.length;i++)
			  if(dpm.numObjectsPerCluster[i]>0)
				  num++;
//		  int diff=checkConvergence(oldZZ, dpm.hardClusterBelongingness);
//		  System.out.println("Difference: "+diff);
//		  if(diff==0)
//			  break;
		  System.out.println("DP mixture: iter# "+iter+" ended with #clusters: "+dpm.numActiveClusters);
		}
		writeDPMModelAndClusterAssignments(dpm, outputFile, inputData);
		System.out.println("Dirichlet based clustering done ...");
	}
	
	public static void writeDPMModelAndClusterAssignments(DPM dpm, String outputFile, ArrayList<Integer[]> inputData) throws Throwable {
		//first build consensus and get the values.
		HashMap<Integer, Integer> numClus2Freq = new HashMap<Integer, Integer>();
		for(int i=0;i<dpmhardClusterBelongingnessSamples.length;i++)
		{
			//get num of clusters for this sample.
			HashSet<Integer> clusters = new HashSet<Integer>();
			for(int j=0;j<dpmhardClusterBelongingnessSamples[i].length;j++)
				clusters.add(dpmhardClusterBelongingnessSamples[i][j]);
			int numclus=clusters.size();
			if(numClus2Freq.containsKey(numclus))
				numClus2Freq.put(numclus, numClus2Freq.get(numclus)+1);
			else
				numClus2Freq.put(numclus, 1);
		}
		int max=0;
		int maxi=0;
		for(int i:numClus2Freq.keySet())
		{
			if(maxi<numClus2Freq.get(i))
			{
				maxi=numClus2Freq.get(i);
				max=i;
			}
		}
		Double softClusterBelongingness[][] = new Double[dpm.hardClusterBelongingness.length][max];
		for(int i=0;i<dpm.hardClusterBelongingness.length;i++)
			for(int j=0;j<max;j++)
				softClusterBelongingness[i][j]=0.;
		for(int i=0;i<dpmhardClusterBelongingnessSamples.length;i++)
		{
			//get num of clusters for this sample.
			HashSet<Integer> clusters = new HashSet<Integer>();
			for(int j=0;j<dpmhardClusterBelongingnessSamples[i].length;j++)
				clusters.add(dpmhardClusterBelongingnessSamples[i][j]);
			int numclus=clusters.size();
			if(numclus!=max)
				continue;
			for(int j=0;j<dpmhardClusterBelongingnessSamples[i].length;j++)
				softClusterBelongingness[j][dpmhardClusterBelongingnessSamples[i][j]]++;
		}
		//normalize
		for(int i=0;i<dpm.hardClusterBelongingness.length;i++)
		{
			double sum=0;
			for(int j=0;j<max;j++)
				sum+=softClusterBelongingness[i][j];
			for(int j=0;j<max;j++)
				softClusterBelongingness[i][j]/=sum;
		}
		//write out the model
		BufferedWriter out = FileUtils.getFileWriter(outputFile);
		out.write("Conc Parameter: "+dpm.concentrationParameter+"\n");
		out.write("numNormal: "+dpm.numObjects+"\n");
		out.write("numDimensions: "+inputData.get(0).length+"\n");
		out.write("numActiveClusters: "+max+"\t"+"#samplesWithThisNumberOfClusters: "+maxi+"\n");
		out.write("softClusterBelongingness: \n");
		for(int i=0;i<dpm.hardClusterBelongingness.length;i++)
		{
			for(int j=0;j<max;j++)
				out.write(softClusterBelongingness[i][j]+" ");
			out.write("\n");
		}
		Double mixtureComponents[][] = new Double[max][inputData.get(0).length];
		for(int clus=0;clus<max;clus++)
			for(int i=0;i<inputData.get(0).length;i++)
				mixtureComponents[clus][i]=0.;
		//get the mixture components
		for(int object=0;object<dpm.numObjects;object++)
		{
			for(int clus=0;clus<max;clus++)
			{
				Integer[] xx = inputData.get(object);
				for(int i=0;i<xx.length;i++)
					mixtureComponents[clus][i]+=softClusterBelongingness[object][clus]*xx[i];
			}
		}
		for(int clus=0;clus<max;clus++)
			for(int j=0;j<inputData.get(0).length;j++)
				mixtureComponents[clus][j]=(int)(Math.round(mixtureComponents[clus][j]))+0.;
		out.write("\nMixture Components: \n");
		for(int i=0;i<max;i++)
		{
			for(int j=0;j<inputData.get(0).length;j++)
				out.write((int)(Math.round(mixtureComponents[i][j]))+" ");
			out.write("\n");
		}
		out.close();
	}
	/**
	 *  initialize DP mixture model, with 
	 *  numActiveClusters active mixture components,
	 *  concentrationParameter concentration parameter,
	 *  hh prior for components,
	 *  inputData data, x_i=inputData{i}
	 *  hardClusterBelongingness initial cluster assignments (between 1 and numActiveClusters).
	 * @param hh 
	 * @return
	 */
	public static DPM dpm_init(int KK, int aa, HH hh, ArrayList<Integer[]> inputData, Integer[] initClusterAssignments)
	{
		DPM dpm = new DPM();
		dpm.numActiveClusters = KK;
		dpm.numObjects = inputData.size();
		dpm.concentrationParameter = aa;
		dpm.mixtureComponents = new Multinomial[KK+1];
		dpm.inputData = inputData;
		dpm.hardClusterBelongingness = initClusterAssignments;
		dpm.numObjectsPerCluster = new Double[KK];
		for(int kk=0;kk<KK;kk++)
			dpm.numObjectsPerCluster[kk]=0.;
		// initialize mixture components
		// component numActiveClusters+1 takes care of all inactive components
		for(int kk=0;kk<KK+1;kk++)
		  dpm.mixtureComponents[kk] = new Multinomial(hh);

		// add data items into mixture components based on the initial cluster assignments
		for(int object=0;object<dpm.numObjects;object++)
		{
		  int clus = initClusterAssignments[object];
		  dpm.mixtureComponents[clus].additem(inputData.get(object));
		  dpm.numObjectsPerCluster[clus] = dpm.numObjectsPerCluster[clus] + 1;
		}
		return dpm;
	}

	/**
	 * run numiter number of iterations of gibbs sampling in the DP mixture
	 * @param dpm
	 * @param i
	 * @return
	 * @throws Throwable 
	 */
	private static DPM dpm_gibbs(DPM dpm, int numiter, int first) throws Throwable {
		int KK = dpm.numActiveClusters; // number of active clusters
		int NN = dpm.numObjects; // number of data items
		int aa = dpm.concentrationParameter; // alpha parameter
		Multinomial[] qq = dpm.mixtureComponents; // row cell vector of mixture components
		ArrayList<Integer[]> xx = dpm.inputData; // row cell vector of data items
		Integer[] zz = dpm.hardClusterBelongingness; // row vector of cluster indicator variables
		Double[] nn = dpm.numObjectsPerCluster; // row vector of number of data items per cluster

		if(first==1)
		{
			 for(int kk=0;kk<nn.length;kk++)
			 {
					// delete active component if it has become empty
				    if(nn[kk] == 0)
				    {
				      KK = KK - 1;
				      //mixtureComponents[kk] = []
				      Multinomial[] newqq = new Multinomial[qq.length-1];
				      for(int i=0;i<kk;i++)
				    	  newqq[i]=qq[i];
				      for(int i=kk+1;i<qq.length;i++)
				    	  newqq[i-1]=qq[i];
				      qq=newqq;
				      Double[] newnn= new Double[nn.length-1];
				      for(int i=0;i<kk;i++)
				    	  newnn[i]=nn[i];
				      for(int i=kk+1;i<nn.length;i++)
				    	  newnn[i-1]=nn[i];
				      nn=newnn;
			
				      ArrayList<Integer> idx = new ArrayList<Integer>();
				      for(int i=0;i<zz.length;i++)
				    	  if(zz[i]>kk)
				    		  idx.add(i);
				      for(int i=0;i<idx.size();i++)
				    	  zz[idx.get(i)]--;
				      kk--;
			    }
			 }
		}
	    
		for(int iter=0;iter<numiter;iter++)
		{
		  // in each iteration, remove each data item from model, then add it back in
		  // according to the conditional probabilities.

		  for(int ii = 0;ii<NN;ii++) // iterate over data items ii
		  {
		    // remove data item inputData{ii} from component mixtureComponents{kk}
		    int kk = zz[ii]; // kk is current component that data item ii belongs to
		    nn[kk] = nn[kk] - 1; // subtract from number of data items in component kk
		    qq[kk].delitem(xx.get(ii)); // subtract data item sufficient statistics

		    // delete active component if it has become empty
		    if(nn[kk] == 0)
		    {
		      KK = KK - 1;
		      //mixtureComponents[kk] = []
		      Multinomial[] newqq = new Multinomial[qq.length-1];
		      for(int i=0;i<kk;i++)
		    	  newqq[i]=qq[i];
		      for(int i=kk+1;i<qq.length;i++)
		    	  newqq[i-1]=qq[i];
		      qq=newqq;
		      Double[] newnn= new Double[nn.length-1];
		      for(int i=0;i<kk;i++)
		    	  newnn[i]=nn[i];
		      for(int i=kk+1;i<nn.length;i++)
		    	  newnn[i-1]=nn[i];
		      nn=newnn;

		      ArrayList<Integer> idx = new ArrayList<Integer>();
		      for(int i=0;i<zz.length;i++)
		    	  if(zz[i]>kk)
		    		  idx.add(i);
		      for(int i=0;i<idx.size();i++)
		    	  zz[idx.get(i)]--;
		    }

		    // compute conditional probabilities pp(kk) of data item ii
		    // belonging to each component kk
		    // compute probabilities in log domain, then exponential
		    Double pp[] = new Double[nn.length+1];
		    for(int i=0;i<pp.length-1;i++)
		    	pp[i]=Math.log(nn[i]);
		    pp[pp.length-1]=Math.log(aa);
		    for (kk =0;kk<KK+1;kk++)
		    	pp[kk] = pp[kk] + qq[kk].logpredictive(xx.get(ii));
		    double max=-Double.MAX_VALUE;
		    for(int i=0;i<pp.length;i++)
		    	if(pp[i]>max)
		    		max=pp[i];
		    for(int i=0;i<pp.length;i++)
		    	pp[i] = Math.pow(Math.E,pp[i] - max); // -max(p) for numerical stability
		    double sum=0;
		    for(int i=0;i<pp.length;i++)
		    	sum+=pp[i];
		    for(int i=0;i<pp.length;i++)
		    	pp[i] = pp[i] / sum;
		    // choose component kk by sampling from conditional probabilities
		    double uu = Math.random();
		    double[] cumsumpp = new double[pp.length];
		    for(int i=0;i<pp.length;i++)
		    	if(i!=0)
		    		cumsumpp[i]=cumsumpp[i-1]+pp[i];
		    	else
		    		cumsumpp[i]=pp[i];
		    int num=0;
		    for(int i=0;i<pp.length;i++)
		    	if(uu>cumsumpp[i])
		    		num++;
		    kk = num;

		    // instantiates a new active component if needed
		    if(kk == KK)
		    {
		      KK = KK + 1;
		      //numNormal[kk] = 0.;
		      Double tmp[]=new Double[nn.length+1];
		      for(int i=0;i<nn.length;i++)
		    	  tmp[i]=nn[i];
		      tmp[kk]=0.;
		      nn=tmp;
//		      qq[kk+1] = qq[kk];
		      Multinomial tmp2[] = new Multinomial[qq.length+1];
		      for(int i=0;i<qq.length;i++)
		    	  tmp2[i]=qq[i];
		      tmp2[kk+1]=tmp2[kk];
		      qq=tmp2;
		    }

		    // add data item inputData{ii} back into model (component mixtureComponents{kk})
		    zz[ii] = kk; 
		    nn[kk]++; // increment number of data items in component kk
		    qq[kk].additem(xx.get(ii)); // add sufficient stats of data item
		}
		}

		// save variables into dpm struct
		dpm.mixtureComponents = qq;
		dpm.hardClusterBelongingness = zz;
		dpm.numObjectsPerCluster = nn;
		dpm.numActiveClusters = KK;
		return dpm;
	}
	/**
	 * Test this by 
	 * SharedUtils.writeMatrixToFile(SharedUtils.softKMeans(SharedUtils.getRandomMatrix(100, 10),5), "C:\\Users\\manish\\Desktop\\tmp.txt");
	 * @param data
	 * @param K
	 * @return
	 */
	public static Double[][] softKMeans(Double[][] data, int K, double parameter)
	{
		int rows = data.length;
		int cols = data[0].length;
		System.out.println("#instances: "+rows);
		System.out.println("#dimensions: "+cols);
		int maxIterations=100;
		Double[][] currLabels = new Double[rows][K];
		MatrixUtils.initMatToValue(currLabels, 0);
		Double oldCurrLabels[][] = new Double[rows][K];
		Double clusterCentroids[][]= new Double[K][cols];
		double changed=1;
		double tolerance=1e-6;
		//randomly assign points to clusters.
		int pointsPerCluster=rows/K;
		int cluster=-1;
		for(int i=0;i<rows;i++)
		{
			if(i%pointsPerCluster==0)
				cluster++;
			if(cluster>=K)
				cluster--;
			currLabels[i][cluster]=1.;
		}
		int iter=0;
		while(changed>tolerance &&iter<maxIterations)
		{
//			printMatrix(clusterCentroids);
			iter++;
			oldCurrLabels=MatrixUtils.copyMatrix(currLabels);
			MatrixUtils.initMatToValue(clusterCentroids, 0);
			double sum[] = new double[K];
			for(int i=0;i<rows;i++)
				for(int k=0;k<K;k++)
					sum[k]+=currLabels[i][k];
			//compute current cluster centroids
			for(int i=0;i<rows;i++)
				for(int k=0;k<K;k++)
					for(int j=0;j<cols;j++)
						clusterCentroids[k][j]+=data[i][j]*currLabels[i][k];
			for(int i=0;i<K;i++)
			{
				for(int j=0;j<cols;j++)
					clusterCentroids[i][j]/=sum[i];
			}
			//recompute distance of one point from all cluster centroids and assign it to nearest cluster, maintain "changed"
			for(int i=0;i<rows;i++)
			{
				for(int j=0;j<K;j++)
				{
					double dist=StatUtils.EuclideanDistance(data[i], clusterCentroids[j]);
					currLabels[i][j]=Math.pow(Math.E, -parameter*dist*dist);
				}
			}
			MatrixUtils.convertMatrixToRightStochastic(currLabels);
			changed=MatrixUtils.getFrobeniusNorm(MatrixUtils.diffMatrix(currLabels, oldCurrLabels));
			System.out.println("Iter: "+iter+" Change: "+changed);
		}
		double max=0;
		for(int i=0;i<rows;i++)
		{
			Double tmp[]= StatUtils.getMinMaxValueFromArray(currLabels[i]);
			max+=tmp[1];
		}
		System.out.println(max/rows);
		return currLabels;
	}
	public static int[] KMeans(Double[][] data, int K)
	{
		int rows = data.length;
		int cols = data[0].length;
		System.out.println("#instances: "+rows);
		System.out.println("#dimensions: "+cols);
		int maxIterations=100;
		int currLabels[] = new int[rows];
		Double clusterCentroids[][]= new Double[K][cols];
		int changed=1;
		//randomly assign points to clusters.
		int pointsPerCluster=rows/K;
		int cluster=-1;
		for(int i=0;i<rows;i++)
		{
			if(i%pointsPerCluster==0)
				cluster++;
			if(cluster>=K)
				cluster--;
			currLabels[i]=cluster;
		}
		HashMap<Integer, Integer> pointsPerClusterMap = new HashMap<Integer, Integer>();
		int iter=0;
		while(changed!=0 &&iter<maxIterations)
		{
			iter++;
			System.out.println("Iter: "+iter+" Points changed clusters: "+changed);
			changed=0;
			MatrixUtils.initMatToValue(clusterCentroids, 0);
			for(int i=0;i<K;i++)
				pointsPerClusterMap.put(i,0);
			//compute current cluster centroids
			for(int i=0;i<rows;i++)
			{
				cluster=currLabels[i];
				for(int j=0;j<cols;j++)
					clusterCentroids[cluster][j]+=data[i][j];
				pointsPerClusterMap.put(cluster, pointsPerClusterMap.get(cluster)+1);
			}
			for(int i=0;i<K;i++)
			{
				int num= pointsPerClusterMap.get(i);
				for(int j=0;j<cols;j++)
					clusterCentroids[i][j]/=num;
			}
			//recompute distance of one point from all cluster centroids and assign it to nearest cluster, maintain "changed"
			for(int i=0;i<rows;i++)
			{
				double min=Double.MAX_VALUE;
				int mini=0;
				for(int j=0;j<K;j++)
				{
					double dist= StatUtils.EuclideanDistance(data[i], clusterCentroids[j]);
					if(dist<min)
					{
						min=dist;
						mini=j;
					}
				}
				if(currLabels[i]!=mini)
					changed++;
				currLabels[i]=mini;
			}
		}
		return currLabels;
	}
    /**
     * Initialization of cluster centroids should be done before calling this method.
     * @param data
     * @param K
     * @param clusterCentroids
     * @return
     */
    public static ArrayList<Object> KMeans(Double[][] data, int K, Double clusterCentroids[][])
    {
    	ArrayList<Object> list = new ArrayList<Object>();
    	int rows = data.length;
    	if(rows!=0)
    	{
    	int cols = data[0].length;
    	int maxIterations=100;
    	int currLabels[] = new int[rows];
    	int changed=1;
    	int cluster=-1;
    	HashMap<Integer, Integer> pointsPerClusterMap = new HashMap<Integer, Integer>();
    	int iter=0;
    	while(iter<maxIterations)
    	{
    		iter++;
    		changed=0;
    		//recompute distance of one point from all cluster centroids and assign it to nearest cluster, maintain "changed"
    		for(int i=0;i<rows;i++)
    		{
    			double min=Double.MAX_VALUE;
    			int mini=0;
    			for(int j=0;j<K;j++)
    			{
    				double dist= StatUtils.EuclideanDistance(data[i], clusterCentroids[j]);
    				if(dist<min)
    				{
    					min=dist;
    					mini=j;
    				}
    			}
    			if(currLabels[i]!=mini)
    				changed++;
    			currLabels[i]=mini;
    		}
    		if(changed==0)
    			break;
    		MatrixUtils.initMatToValue(clusterCentroids, 0);
    		for(int i=0;i<K;i++)
    			pointsPerClusterMap.put(i,0);
    		//compute current cluster centroids
    		for(int i=0;i<rows;i++)
    		{
    			cluster=currLabels[i];
    			for(int j=0;j<cols;j++)
    				clusterCentroids[cluster][j]+=data[i][j];
    			pointsPerClusterMap.put(cluster, pointsPerClusterMap.get(cluster)+1);
    		}
    		for(int i=0;i<K;i++)
    		{
    			int num= pointsPerClusterMap.get(i);
    			for(int j=0;j<cols;j++)
    				clusterCentroids[i][j]/=num;
    		}
    	}
    	list.add(currLabels);
    	list.add(clusterCentroids);
    	}
    	list.add(new int[rows]);
    	list.add(clusterCentroids);
    	return list;
    }
    /**
     * The GMeans algorithm: GMeans(data, initK);
     * @param data
     * @param initK
     * @return
     */
    public static Double[][] GMeans(Double[][]data, int initK)
    {
    	//get initial cluster centroids.
    	int m=data[0].length;
    	int rows=data.length;
    	int cl[] = new int[rows];
    	int pointsPerCluster=rows/initK;
    	//randomly assign points to clusters.
    	int cluster=-1;
    	for(int i=0;i<rows;i++)
    	{
    		if(i%pointsPerCluster==0)
    			cluster++;
    		if(cluster>=initK)
    			cluster--;
    		cl[i]=cluster;
    	}
    	Double clusterCentroids[][]=new Double[initK][m];
    	MatrixUtils.initMatToValue(clusterCentroids, 0);
    	HashMap<Integer, Integer> pointsPerClusterMap = new HashMap<Integer, Integer>();
    	for(int i=0;i<initK;i++)
    		pointsPerClusterMap.put(i,0);
    	//compute current cluster centroids
    	for(int i=0;i<rows;i++)
    	{
    		cluster=cl[i];
    		for(int j=0;j<m;j++)
    			clusterCentroids[cluster][j]+=data[i][j];
    		pointsPerClusterMap.put(cluster, pointsPerClusterMap.get(cluster)+1);
    	}
		for(int i=0;i<initK;i++)
		{
			int num= pointsPerClusterMap.get(i);
			for(int j=0;j<m;j++)
				clusterCentroids[i][j]/=num;
		}
    	int[] labels=null;
    	int changed=1;
    	while(changed!=0)
    	{
	    	//run KMeans
    		int k=clusterCentroids.length;
    		System.out.println("Number of clusters: "+k);
	    	ArrayList<Object> list = KMeans(data, k, clusterCentroids);
			clusterCentroids=(Double[][]) list.get(1);
			labels=(int[]) list.get(0);
			HashMap<Integer, Integer> counts = new HashMap<Integer, Integer>();
			for(int i=0;i<labels.length;i++)
			{
				if(counts.containsKey(labels[i]))
					counts.put(labels[i], counts.get(labels[i])+1);
				else
					counts.put(labels[i], 1);
			}
			if(counts.size()<k)
			{
				System.err.println("Use a lower value for initial number of clusters");
				System.exit(0);
			}
			ArrayList<Double[]> newClusterCentroids = new ArrayList<Double[]>();
			for(int i=0;i<k;i++)
			{
				//if the size is less than or equal to 5, do not split.
				if(counts.get(i)<5)
				{
					newClusterCentroids.add(clusterCentroids[i]);
				}
				else
				{
					//get 2 centers for children
					Double localClusterCentroids[][]=new Double[2][m];
					Double[][] localData= new Double[counts.get(i)][m];
					int count=0;
					for(int j=0;j<labels.length;j++)
					{
						if(labels[j]==i)
						{
							for(int t=0;t<m;t++)
								localData[count][t]=data[j][t];
							count++;
						}
					}
					ArrayList<Object> list2 = MatrixUtils.getMaxEigenValueAndVector(localData);
					double amplitude=Math.sqrt(2*(Double)list2.get(0)/Math.PI);
					Double[] vecM= (Double[]) list2.get(1);
					for(int j=0;j<m;j++)
					{
						localClusterCentroids[0][j]=clusterCentroids[i][j]+amplitude*vecM[j];
						localClusterCentroids[1][j]=clusterCentroids[i][j]-amplitude*vecM[j];
					}
					ArrayList<Object> list3 = KMeans(localData, 2, localClusterCentroids);
					localClusterCentroids=(Double[][]) list3.get(1);
					Double[] v= new Double[m];
					double vAmp = 0;
					for(int j=0;j<m;j++)
					{
						v[j]=localClusterCentroids[0][j]-localClusterCentroids[1][j];
						vAmp+=v[j]*v[j];
					}
					double projectedVector[] = new double[localData.length];
					for(int j=0;j<localData.length;j++)
						projectedVector[j]=StatUtils.cosineSim(localData[j], v)/vAmp;
					Boolean decision = StatUtils.andersonDarlingNormal(projectedVector, 0.0001);
					if(decision)
					{
						//value is less than 1.8692 -- reject the split
						newClusterCentroids.add(clusterCentroids[i]);
					}
					else
					{
						//accept the split.
						newClusterCentroids.add(localClusterCentroids[0]);
						newClusterCentroids.add(localClusterCentroids[1]);
					}
				}
			}
			if(newClusterCentroids.size()==clusterCentroids.length)
				changed=0;
			else
				changed=1;
			//assign new cluster centroids to cluster centroids
			clusterCentroids= new Double[newClusterCentroids.size()][m];
			for(int i=0;i<newClusterCentroids.size();i++)
				clusterCentroids[i]=newClusterCentroids.get(i);
    	}
    	return clusterCentroids;
    }
    
	/**
	 * Implements the XMeans clustering algorithm
	 * Use this to run the algorithm:
	 * XMeans(SharedUtils.readMatrixFromARFFFile("C:\\Users\\manish\\Desktop\\cpu.arff"), 20, "C:\\Users\\manish\\Desktop\\research\\workspace\\jars\\");
	 * @param data
	 * @param kMax
	 * @param wekaJarLocation
	 * @return
	 * @throws Throwable
	 */
	public static Double[][] XMeans(ArrayList<Double[]> dataList, int kMax, String wekaJarLocation, boolean printDetails) throws Throwable
	{
		Double[][] data = new Double[dataList.size()][];
		for(int d=0;d<dataList.size();d++)
			data[d]=dataList.get(d);
		String file=wekaJarLocation+"tmp.arff";
		MatrixUtils.writeMatrixToARFFFile(data, file);
		Runtime rt2 = Runtime.getRuntime();
		Process pr2 =null;
		if(wekaJarLocation.contains("/home"))
		{
			String processString[]=new String[3];
			processString[0]="sh";
			processString[1]="-c";
			processString[2]="java -Xmx1g -cp "+wekaJarLocation+"weka.jar weka.clusterers.XMeans -I 1 -M 1000 -J 1000 -L 2 -H "+kMax+" -B 1.0 -C 0.5 -D \"weka.core.EuclideanDistance -D -R first-last\" -U 1 -S 10 -t "+file;
			if(kMax==1)
				processString[processString.length-1]="java -Xmx1g -cp "+wekaJarLocation+"weka.jar weka.clusterers.XMeans -I 1 -M 1000 -J 1000 -L 1 -H "+kMax+" -B 1.0 -C 0.0 -D \"weka.core.EuclideanDistance -D -R first-last\" -U 1 -S 10 -t "+file;
			pr2 = rt2.exec(processString);
		}
		else
		{
			String processString= "java -Xmx1g -cp "+wekaJarLocation+"weka.jar weka.clusterers.XMeans -I 1 -M 1000 -J 1000 -L 2 -H "+kMax+" -B 1.0 -C 0.5 -D \"weka.core.EuclideanDistance -D -R first-last\" -U 1 -S 10 -t "+file;
			if(kMax==1)
				processString="java -Xmx1g -cp "+wekaJarLocation+"weka.jar weka.clusterers.XMeans -I 1 -M 1000 -J 1000 -L 1 -H "+kMax+" -B 1.0 -C 0.0 -D \"weka.core.EuclideanDistance -D -R first-last\" -U 1 -S 10 -t "+file;
			pr2 = rt2.exec(processString);
		}
		ArrayList<String> processOutput = new ArrayList<String>();
	    
	    BufferedReader input2 = new BufferedReader(new InputStreamReader(pr2.getInputStream()));
	    int numClusters=0;
	    String line2=null;
	    while((line2=input2.readLine()) != null) 
	    	processOutput.add(line2);
	    pr2.waitFor();
	    Double clusterCentroids[][]=null;
	    for(int i=0;i<processOutput.size();i++)
	    {
	    	String line3=processOutput.get(i);
	    	if(line3.contains("Cluster centers"))
	    	{
	    		numClusters=Integer.parseInt(line3.split("\\s+")[3]);
	    		clusterCentroids= new Double[numClusters][data[0].length];
	    		MatrixUtils.initMatToValue(clusterCentroids, 0);
	    		i+=3; line3=processOutput.get(i);
	    		for(int k=0;k<numClusters;k++)
	    		{
	    			String tokens[]=line3.trim().split("\\s+");
	    			for(int j=0;j<data[0].length;j++)
	    				clusterCentroids[k][j]=Double.parseDouble(tokens[j]);
	    			i+=2; line3=processOutput.get(i);
	    		}
	    	}
	    }
	    new File(wekaJarLocation+"/tmp.arff").delete();
	    int rows=data.length;
	    int K= clusterCentroids.length;
	    int currLabels[] = new int[rows];
	    int counts[]=new int[K];
		for(int i=0;i<rows;i++)
		{
			double min=Double.MAX_VALUE;
			int mini=0;
			for(int j=0;j<K;j++)
			{
				double dist= StatUtils.EuclideanDistance(data[i], clusterCentroids[j]);
				if(dist<min)
				{
					min=dist;
					mini=j;
				}
			}
			currLabels[i]=mini;
			counts[mini]++;
		}
		HashSet<Integer> remove = new HashSet<Integer>();
		for(int i=0;i<counts.length;i++)
			if(counts[i]==0)
				remove.add(i);
		Double[][] finalClusterCentroids = new Double[clusterCentroids.length-remove.size()][];
		int count=0;
		for(int i=0;i<counts.length;i++)
			if(!remove.contains(i))
				finalClusterCentroids[count++]=clusterCentroids[i];
		if(printDetails)
		{
			for(int i=0;i<rows;i++)
				System.out.println(i+" "+currLabels[i]);
			for(int i=0;i<K;i++)
				System.out.println("Cluster [ "+i+" ]: "+counts[i]);
			System.err.println("Warning: Note these labels and Cluster numbers may not match the finalClusterCentroids returned by this method.");
		}
		return finalClusterCentroids;
	}

	public static int[] HierarchicalClusterer(Double[][] data, int k, String wekaJarLocation) throws Throwable
	{
		ArrayList<Double[]> list = new ArrayList<Double[]>();
		for(int i=0;i<data.length;i++)
			list.add(data[i]);
		return HierarchicalClusterer(list, k, wekaJarLocation);
	}
	
	
	public static int[] KMeans(Double[][] data, int k, String wekaJarLocation) throws Throwable
	{
		ArrayList<Double[]> list = new ArrayList<Double[]>();
		for(int i=0;i<data.length;i++)
			list.add(data[i]);
		return KMeans(list, k, wekaJarLocation);
	}
	
	public static int[] HierarchicalClusterer(ArrayList<Double[]> dataList, int k, String wekaJarLocation) throws Throwable
	{
		Double[][] data = new Double[dataList.size()][];
		for(int d=0;d<dataList.size();d++)
			data[d]=dataList.get(d);
		String s= ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
		String file=wekaJarLocation+"tmp"+s+".arff";
		MatrixUtils.writeMatrixToARFFFile(data, file);
		Runtime rt2 = Runtime.getRuntime();
		Process pr2 =null;
		if(wekaJarLocation.contains("/home"))
		{
			String processString[]=new String[3];
			processString[0]="sh";
			processString[1]="-c";
			processString[2]="java -Xmx1g -cp "+wekaJarLocation+"weka.jar weka.clusterers.HierarchicalClusterer -L SINGLE -P -p 1 -N "+k+"  -t "+file;
			pr2 = rt2.exec(processString);
		}
		else
		{
			String processString= "java -Xmx1g -cp "+wekaJarLocation+"weka.jar weka.clusterers.HierarchicalClusterer -L SINGLE -P -p 1 -N "+k+" -t "+file;
			pr2 = rt2.exec(processString);
		}
		ArrayList<String> processOutput = new ArrayList<String>();
	    BufferedReader input2 = new BufferedReader(new InputStreamReader(pr2.getInputStream()));
	    String line2=null;
	    while((line2=input2.readLine()) != null) 
	    	processOutput.add(line2);
	    pr2.waitFor();
		int labels[]= new int[data.length];
		for(int i=0;i<data.length;i++)
		{
			labels[i]=Integer.parseInt(processOutput.get(i).split("\\s+")[1]);
		}
		new File(file).delete();
		return labels;
	}

	
	public static int[] KMeans(ArrayList<Double[]> dataList, int k, String wekaJarLocation) throws Throwable
	{
		Double[][] data = new Double[dataList.size()][];
		for(int d=0;d<dataList.size();d++)
			data[d]=dataList.get(d);
		String s= ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
		String file=wekaJarLocation+"tmp"+s+".arff";
		MatrixUtils.writeMatrixToARFFFile(data, file);
		Runtime rt2 = Runtime.getRuntime();
		Process pr2 =null;
		if(wekaJarLocation.contains("/home"))
		{
			String processString[]=new String[3];
			processString[0]="sh";
			processString[1]="-c";
			processString[2]="java -Xmx10g -cp "+wekaJarLocation+"weka.jar weka.clusterers.SimpleKMeans -p 1 -I 100 -N "+k+" -O  -t "+file;
			pr2 = rt2.exec(processString);
		}
		else
		{
			String processString= "java -Xmx10g -cp "+wekaJarLocation+"weka.jar weka.clusterers.SimpleKMeans -p 1 -I 100 -N "+k+" -O  -t "+file;
			pr2 = rt2.exec(processString);
		}
		ArrayList<String> processOutput = new ArrayList<String>();
	    BufferedReader input2 = new BufferedReader(new InputStreamReader(pr2.getInputStream()));
	    String line2=null;
	    while((line2=input2.readLine()) != null) 
	    	processOutput.add(line2);
	    pr2.waitFor();
		int labels[]= new int[data.length];
		for(int i=0;i<data.length;i++)
		{
			labels[i]=Integer.parseInt(processOutput.get(i).split("\\s+")[1]);
		}
		new File(file).delete();
		return labels;
	}

	
	/**
	 * Converts a soft clustering matrix to hard cluster label vector.
	 * Such hard cluster label vector may be useful as input to cluster matching softwares like bestMap.m
	 * @param mat
	 * @return
	 */
	public static int[] convertSoftClusteringMatrixToHardClusterLabelVector(Double mat[][])
	{
		int rows=mat.length;
		int[] arr = new int[rows];
		for(int i=0;i<rows;i++)
		{
			Double[] tmp = StatUtils.getMinMaxValueFromArray(mat[i]);
			arr[i]=(int) Math.round(tmp[3]);
		}
		return arr;
	}
}
class HH{
	public HH(int dd, int bb) {
		numDimensions=dd;
		b=bb;
	}
	int numDimensions;
	int b;
}