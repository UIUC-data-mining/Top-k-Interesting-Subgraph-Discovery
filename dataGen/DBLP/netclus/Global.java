package dataGen.DBLP.netclus;
import java.util.HashMap;


/**
 * This class defines parameters and configuration settings for NetClus
 * @author gupta58
 * Originally coded by Yizhou Sun in C#
 */
public class Global {
	/**
	 * configuration parameters
	 */
    static public double lambda_P = 0.2; //weight for prior knowledge
    static public double lambda_S = 0.85; //smoothing rate -- denotes how much we should utilize the ranking distribution from global ranking
    static public double minScoreToConsiderStrongClusterMembership = 0.;
    static public double eps_ranking = 1e-5;
    public static double eps_clustering = 1e-4;
    public static double eps_em=1e-6;
    
    /**
     * DBLP/fourArea settings
     */
//    public static String dataDir = "/home/cs/gupta58/2012/hnoda1/DBLP/";
////    public static String dataDir = "/tmp/DBLP/";
////    static public String dataDir = "C:\\Users\\manish\\Desktop\\research\\current\\HNODA1\\four_area\\";
//    static public String outDir = "";
//    
//    static public String[] typeList = { "paper", "conf", "author", "term" };
//    static public String[] typeFileList = { "paper.txt", "conf.txt", "author.txt", "term.txt" };
//    static public String[] fromtypeList =  { "paper", "paper", "paper" };
//    static public String[] totypeList =  { "conf", "author", "term" };
//    static public String[] relFileList =  { "paper_conf.txt", "paper_author.txt", "paper_term.txt" };
//    static public String targetType = typeList[0];
//    static public String[] attributeType = { "conf", "author", "term" };
//
//    static public double[] rankingweight =  {1.0, 1.0, 1.0 };
//    static public String[] priorType =  { "term" };
//    static public String[] priorFile =  { "prior_term.txt" };
//    static public int K =14;
    // ALSO CHANGE THE NUMBER OF CLUSTERS IN OUTLIERDETECTOR2
    
    
    /**
     * DBLP/fourArea settings ends
     */
    
    /**
     * Delicious settings
     */
//    static public String dataDir = "C:\\Users\\manish\\Desktop\\research\\current\\HNODA1\\delicious\\toy\\";
//    static public String dataDir = "/home/cs/gupta58/2012/hnoda1/delicious/";
    static public String dataDir = "C:\\Users\\gupta58\\Desktop\\HNODA3\\delicious\\";
    static public String outDir = "";
    
    static public String[] typeList = { "event", "website", "user", "tag" };
    static public String[] typeFileList = { "event.txt", "website.txt", "user.txt", "tag.txt" };
    static public String[] fromtypeList =  { "event", "event", "event" };
    static public String[] totypeList =  { "website", "user", "tag" };
    static public String[] relFileList =  { "EW.txt", "EU.txt", "ET.txt" };
    static public String targetType = typeList[0];
    static public String[] attributeType = { "website", "user", "tag" };

    static public double[] rankingweight =  {1.0, 1.0, 1.0 };
    static public String[] priorType =  { "tag" };
    static public String[] priorFile =  { "prior_tag.txt" };
    static public int K =10;
////     ALSO CHANGE THE NUMBER OF CLUSTERS IN OUTLIERDETECTOR2
    
    /**
     * Delicious settings ends
     */
    static public int MAX_ITER_CLUSTER = 20;
    static public int MAX_ITER_RANKING = 40;
    public static int MAX_ITER_EM = 150;

    /**
     * data structures
     */
    static public EntitySet entitySet;
    static public RelationSet relationSet;
    static public TypeClusterRank priorRank;
    static public HashMap<String, Double> rankingweightHT;
	
    
    /**
     * Initialize the data structures
     * @throws Throwable
     */
    public Global() throws Throwable
    {
        entitySet = new EntitySet(typeList, typeFileList);
        relationSet = new RelationSet(fromtypeList, totypeList, relFileList);
        priorRank = new TypeClusterRank(priorType, priorFile, K);
        rankingweightHT = new HashMap<String, Double>();
        for (int i = 0; i < rankingweight.length; i++)
        {
            rankingweightHT.put(attributeType[i], rankingweight[i]); 
        }
    }
    /**
     * generating priors for small(four_area) dataset.
IR
WSDM WWW SIGIR CIKM ECIR
DM
KDD PAKDD PKDD SDM ICDM
DB
PODS VLDB SIGMOD ICDE EDBT
ML
IJCAI CVPR  AAAI ECML ICML

     */
}