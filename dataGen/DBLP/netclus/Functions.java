package dataGen.DBLP.netclus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;


/**
 * This class implements the netclus ranking functions and the rank-clustering algorithm
 * @author gupta58
 * Originally coded by Yizhou Sun in C#
 */
public class Functions {
    /**
     * ranking for a sub network -- corresponds to section 4.2 of paper
     * @param network
     * @param targetType
     * @param attributeType
     * @param typeMembership
     * @param priorRank
     * @param globalRank
     * @param lambda_C
     * @return
     */
    @SuppressWarnings("unchecked")
	public static TypeRankVec Ranking(RelationSet network, String targetType, String[] attributeType, TypeMemberShip typeMembership, TypeRankVec priorRank, TypeRankVec globalRank, double lambda_C)
    {
        double epsi_ranking = 1;
        int MAX_ITER_R = Global.MAX_ITER_RANKING;//40;
        int iter_r = 0;

        //initialize ranking for targetType as 1/(cardinality of target type)
        TypeRankVec typeRankVec = new TypeRankVec();
        Set<Integer> targetKeys = Global.entitySet.getKeys(targetType);
        int targetCard = targetKeys.size();
        typeRankVec.set(targetType, new RankVec(targetKeys,1.0/targetCard));

     	//initialize ranking for attribute type as 1/(cardinality of attribute type)
        Set<Integer>[] attributeKeys = new Set[attributeType.length];
        double sumProb = 0;
        for (int i = 0; i < attributeType.length ; i++)
        {
            attributeKeys[i] = Global.entitySet.getKeys(attributeType[i]);
            String curAttributeType = attributeType[i];
            typeRankVec.set(curAttributeType, new RankVec(attributeKeys[i], 1.0/attributeKeys[i].size()));
            sumProb += attributeKeys[i].size();
        }
        //computes P(T_x|G)
        double []attriProb = new double[attributeType.length];
        for (int i = 0; i < attributeType.length; i++)
        {
            attriProb[i] = attributeKeys[i].size() / sumProb;
        }

        //loop, first consider --- compute P(x|T_x,G) where x is conf or author (ranking conf and authors) 
        while (iter_r < MAX_ITER_R && epsi_ranking > Global.eps_ranking)
        {
//            TypeRankVec newTypeRankVec = Ranking_Iter_Authority(network, targetType, attributeType, typeMembership, priorRank, typeRankVec);
        	TypeRankVec newTypeRankVec = Ranking_Iter_PGRank(network, targetType, attributeType, typeMembership, priorRank, typeRankVec);
            RankVec newAttributeRankVec = newTypeRankVec.get(attributeType[0]);
            epsi_ranking = RankVec.difference(newAttributeRankVec, typeRankVec.get(attributeType[0]));
            System.err.println("Iteration "+iter_r+": Epsi_Ranking "+epsi_ranking);
            typeRankVec.copy(newTypeRankVec);
            iter_r++;
        }//until no change 
        
        //RankVec confVec = Ranking_Iter_Count(network, targetType, attributeType[0], attributeType, typeMembership, priorRank, global.lambda_P, typeRankVec);
        //typeRankVec[attributeType[0]] = confVec;
        //RankVec authorVec = Ranking_Iter_Count(network, targetType, attributeType[1], attributeType, typeMembership, priorRank, global.lambda_P, typeRankVec);
        //typeRankVec[attributeType[1]] = authorVec;
        
        
        /******************************************************************************/
        //second, calculate the probabilities P(x|T_x,G) where x is term (ranking terms)
        if (priorRank != null)
            typeRankVec.get(attributeType[2]).copy(priorRank.get(attributeType[2]));
        
        iter_r = 0;
        epsi_ranking = 1;
        while (iter_r < 1 && epsi_ranking > Global.eps_ranking)
        {
            RankVec newTermVec = Ranking_Iter_Authority3(network, targetType, attributeType[2], attributeType, typeMembership, typeRankVec);
            epsi_ranking = RankVec.difference(newTermVec, typeRankVec.get(attributeType[2]));
            System.err.println("Iteration "+iter_r+": Epsi_Ranking "+epsi_ranking);
            typeRankVec.get(attributeType[2]).copy(newTermVec);
            iter_r++;
        }
        
        /******************************************************************************/
        //third, calculate the probs of documents
 
        RankVec curTar_RankVec = typeRankVec.get(targetType);
        //get relationship for each attribute type
        //change here for the second level
        for (int i = 2; i < attributeType.length; i++)
        {
            Relation curRelation = network.get(targetType, attributeType[i]);
            RankVec curAttri_RankVec = typeRankVec.get(attributeType[i]);
            for(int rowID : targetKeys)
            {
                //get current attribute relation for current paper
                HashMap<Integer, Double> curRowHT = curRelation.getRow(rowID);
                double score = 1;
                if (curRowHT == null)
                {
                    curTar_RankVec.set(rowID, 0); //if a paper doesn't have author, or conf, or title, it should equal to 0
                    continue;
                }
                int card = 0;
                for (int colID: curRowHT.keySet())
                {
                    //method 1: multiplication, handle the problem of zero using smoothing
                    if (globalRank == null)
                        score *= Math.pow(curAttri_RankVec.get(colID), curRowHT.get(colID)) * attriProb[i];
                    else
                    {
                        double newP = globalRank.get(attributeType[i],colID);
                        double lambda = lambda_C;
                        if (i == 2)
                        {
                            lambda = 0.9;
                        }
                        score *= Math.pow(curAttri_RankVec.get(colID)*lambda+ newP*(1-lambda), curRowHT.get(colID)) * attriProb[i];
                    }
                    card++;
                }
                curTar_RankVec.set(rowID, curTar_RankVec.get(rowID) * score) /*Math.Pow(score,1.0/card)*/;
            }
        }

        curTar_RankVec.normalization();
        
        typeRankVec.set(targetType, curTar_RankVec);

        return typeRankVec;
    }

/**
 *     //two type ranking -- for conference and author.
 *     This implements equation (6) from the paper
 * @param network
 * @param targetType
 * @param attributeType
 * @param typeMembership
 * @param priorRank
 * @param typeRankVec
 * @return
 */
	@SuppressWarnings("unchecked")
	public static TypeRankVec Ranking_Iter_Authority(RelationSet network, String targetType, String[] attributeType, TypeMemberShip typeMembership, TypeRankVec priorRank, double lambda,TypeRankVec typeRankVec)
    {
        //for attribute types
        TypeRankVec newTypeRankVec = new TypeRankVec();
        RankVec[] curAttri_RankVec = new RankVec[attributeType.length-1];
        Set<Integer> [] attriKeys = new Set[attributeType.length-1];
        Relation [] relation = new Relation [attributeType.length - 1];

        for (int i = 0; i < attributeType.length - 1; i++)
        {
            curAttri_RankVec[i] = new RankVec();
            curAttri_RankVec[i].copy(typeRankVec.get(attributeType[i]));
            attriKeys[i] = Global.entitySet.getByType(attributeType[i]).getKeys();
            relation[i] = network.get(targetType, attributeType[i]);
        }

        for (int i = 0; i < attributeType.length - 1; i++ )
        {
            //for curattriID, suppose C
            for(int attriID:attriKeys[i])
            {
                //CP
                HashMap<Integer, Double> colHT = relation[i].getCol(attriID);
                if (colHT == null)
                {
                    curAttri_RankVec[i].set(attriID,0.0);
                    continue;
                }
                double score_attriID = 0;
                //paper's score 
                for (int targetID:  colHT.keySet()) {
                    //PA
                    HashMap<Integer, Double> colRowHT = relation[1 - i].getRow(targetID);
                    //PC
                    HashMap<Integer, Double> colSelfHT = relation[i].getRow(targetID);
                    double score = 0;
                    if (colRowHT == null)
                    {
                        continue;
                    }
                    //P's score = sum of author's score
                    for (int attriID_other: colRowHT.keySet()) {
                        score += colRowHT.get(attriID_other) * curAttri_RankVec[1-i].get(attriID_other)* typeMembership.get(targetType).get(targetID);
                    }
                    //P's score = sum of author's score /number of conf of this paper
                    score = score / colSelfHT.size();
                    score_attriID += score;
                }
                curAttri_RankVec[i].set(attriID, score_attriID);
            }
            curAttri_RankVec[i].normalization();
            newTypeRankVec.set(attributeType[i], curAttri_RankVec[i]);
        }
        return newTypeRankVec;
    }

    /**
     * This implements the NetClus algorithm as follows:<br>
     * 1. Computing global ranks<br>
     * 2. while condition<br>
     * 	a. Rank target objects<br>
     * 	b. Cluster<br>
     * 	c. Refine clusters using EM<br>
     * 3. Rank target objects<br>
     * 4. Refine clusters using EM one last time.<br>
     * 5. Compute posterior probabilities for each attribute object also<br>
     * 6. Compute important objects within each cluster.<br>
     * <br>
     * <br>
     * Files produced<br>
     * .rank: Global ranking files<br>
     * .clus: Final cluster membership scores for each object<br>
     * .rep: Important nodes in each cluster for each type, sorting by P(o|k)/e^ entropy(k|o)<br>
     * .hub: Hub for current level of network, sorting by P(o)*e^entropy(k|o)<br>
     * @param network
     * @param targetType
     * @param attributeType
     * @param typeClusterMembership 0 ... K, where (K+1)th value store the global information
     * @param K
     * @param priorType
     * @param priorRank
     * @param lambda_C
     * @return
     * @throws Throwable
     */
    public static void RankClustering(RelationSet network, String targetType, String [] attributeType, TypeMemberShip [] typeClusterMembership, int K, String [] priorType, TypeClusterRank priorRank, double lambda_C) throws Throwable
    {
        double epsi_cluster = 1;
        int MAX_ITER_C = Global.MAX_ITER_CLUSTER;
        int MAX_ITER_PK = Global.MAX_ITER_EM;
        int iter_c = 0;

        double[] PK = new double[K + 1];
        double epsi_pk = 1;
        int iter_pk = 0;

        //calculate global rank
        TypeRankVec globalRankVec = new TypeRankVec();
        //this global rank vector would be used later for smoothing
        globalRankVec = Ranking(network, targetType, attributeType, typeClusterMembership[K], null,null, lambda_C);
        
        /**
         * writes the global ranking values for every type (target+attribute) to files
         */
        String[] outfile =  { "conf.rank", "author.rank", "term.rank", "paper.rank" };
        String[] outfile_clus =  { "conf.clus", "author.clus", "term.clus", "paper.clus" };
        for (int i = 0; i < attributeType.length; i++)
        {
            Set<Integer> keys = Global.entitySet.getByType(attributeType[i]).getKeys();
            writeToFile(outfile[i],attributeType[i],keys,globalRankVec.get(attributeType[i]));
        }
        Set<Integer> targetKeys = typeClusterMembership[K].get(targetType).keySet();
        RankVec targetGlobalRankVec = globalRankVec.get(targetType);
        writeToFile(outfile[3], targetType, targetKeys, targetGlobalRankVec);
       
        
        //record the posterior probability for K clusters, include the background
        TypeClusterIndicator clusterResult = new TypeClusterIndicator();
        for (int i = 0; i < attributeType.length; i++)
        {
            ClusterIndicator clusterInd = new ClusterIndicator(K + 1);
            clusterResult.set(attributeType[i], clusterInd);
        }
        ClusterIndicator targetClusterInd = new ClusterIndicator(K + 1);
        clusterResult.set(targetType, targetClusterInd);
    
        //record current target cluster indicator according to the membership, not include background, assignment of paper
        ClusterIndicator TargetClusterIndicator = new ClusterIndicator(K);

        for (int targetID : targetKeys)
        {
            for (int k = 0; k < K ; k++)
            {
                HashMap<Integer, Double> membershipHT = (typeClusterMembership[k]).get(targetType);
                if(membershipHT.containsKey(targetID))
                	TargetClusterIndicator.set(targetID,k,membershipHT.get(targetID));
                else
                	TargetClusterIndicator.set(targetID,k,0);
            }
        }

        //loop
        TypeRankVec[] curTypeRankVecVec = new TypeRankVec[K];

        TypeMemberShip[] curTypeMembership = new TypeMemberShip[K];
        for (int k = 0; k < K; k++)
        {
            curTypeMembership[k] = new TypeMemberShip();
            curTypeMembership[k].copy(typeClusterMembership[k]);
        }
        while (epsi_cluster > Global.eps_clustering && iter_c < MAX_ITER_C)
        {
            //extract sub-network: typeClusIndic has determined the network
        	//i.e. build the probabilistic generative model -- everything upto section 4.2 in paper is done here
            for (int k = 0; k < K; k++)
            {
                //ranking according to current cluster
                System.err.println("Cluster "+k+":");
                if (priorRank != null)
                {
                    curTypeRankVecVec[k] = Ranking(network, targetType, attributeType, curTypeMembership[k], priorRank.get(k), globalRankVec, lambda_C);
                }
                else
                {
                    curTypeRankVecVec[k] = Ranking(network, targetType, attributeType, curTypeMembership[k], null, globalRankVec, lambda_C);
                }
            }
            //create space for k-dimensional vector representation of every entity instance across K different clusters.
            MeasureMatrix[] curMM = new MeasureMatrix[K];
            for (int k = 0; k < K ; k++)
            {
                curMM[k] = new MeasureMatrix(curTypeMembership[k].get(targetType), K );
            }

            //get posterior probability
            //initial is uniform distribution
            for (int k = 0; k < K + 1; k++)
                PK[k] = 1.0 / (K + 1);
            
            //using EM algorithm to iteratively improve the probability
            epsi_pk = 1;
            iter_pk = 0;

            while (iter_pk < MAX_ITER_PK && epsi_pk > Global.eps_em)
            {
                double[] newPK = new double[K + 1];
                double sum_newPK = 0;
                for (int k = 0; k < K + 1; k++)
                    newPK[k] = 0;

                for (int key : targetKeys)
                {
                    ArrayList<Double> newMeasure = new ArrayList<Double>(K + 1); //use K+1 to denote the background model, later
                    double sumM = 0;
                    for (int k = 0; k < K; k++)
                    {
                        newMeasure.add(k, curTypeRankVecVec[k].get(targetType).get(key) * PK[k]);
                        sumM += newMeasure.get(k);
                    }
                    newMeasure.add(K, targetGlobalRankVec.get(key) * PK[K]);
                    sumM += newMeasure.get(K);

                    for (int k = 0; k < K + 1; k++)
                    {
                        if (sumM != 0)
                            newMeasure.set(k,newMeasure.get(k) / sumM);
                        else
                        	newMeasure.set(k,0.);
                        if (k != K)
                        {
                            for (int kk = 0; kk < K; kk++)
                            {
                                curMM[kk].set(key, k, newMeasure.get(k));
                            }
                        }
                        newPK[k] += newMeasure.get(k);
                        sum_newPK += newMeasure.get(k);
                    }

                }
                epsi_pk = 0;
                for (int k = 0; k < K + 1; k++)
                {
                	if(sum_newPK!=0.0)
                	{
                		newPK[k] = newPK[k] / sum_newPK;
                		epsi_pk += Math.abs(newPK[k] - PK[k]);
                	}
                    PK[k] = newPK[k]; //-------- SIZE OF CLUSTER IS FIXED AS 1/number of clusters ----------
                }
                    System.err.println("EM: iteration "+iter_pk+" of P(Z=k), epsi: "+ epsi_pk);
                iter_pk++;
            }
            
            //compute loglikelihood finally
            double loglikelihood=0;//lower loglikelihood implies better clusters
            for (int key : targetKeys)
            {
            	double sum=0;
            	 for (int k = 0; k < K; k++)
            		sum+= curTypeRankVecVec[k].get(targetType).get(key) * PK[k];
            	 if(sum>0)
            		 loglikelihood+=Math.log(sum);
            }
            System.err.println("Log likelihood: "+loglikelihood);
            
            //get center for the original cluster

            MeasureRecord[] clusterCenterMR = new MeasureRecord[K]; // each contains centroid for one cluster.
            for (int k = 0; k < K; k++)
            {
                clusterCenterMR[k] = curMM[k].getMean();
            }

            ClusterIndicator oldTargetClusterIndicator = new ClusterIndicator(K);
            oldTargetClusterIndicator.copy(TargetClusterIndicator);
            BufferedWriter outDist = new BufferedWriter(new FileWriter(new File(Global.outDir, "paper.dist")));
            //k-means readjust clusters, get new cluster
            for(int key :targetKeys)
            {
                ArrayList<Double> dis = new ArrayList<Double>(K);
                outDist.write(key+"\t");
                for (int k = 0; k < K; k++)
                {
                    dis.add(k, MeasureRecord.cosine_sim(curMM[0].get(key), clusterCenterMR[k]));
                    outDist.write(MeasureRecord.cosine_sim(curMM[0].get(key), clusterCenterMR[k])+"\t");
                }
                outDist.write("\n");
                int clusterIndex = getMaxIndex(dis);
                ArrayList<Double> memberAL = new ArrayList<Double>(K);
                for (int k = 0; k < K; k++)
                {
                    memberAL.add(0.0);
                }
                if (typeClusterMembership[K].get(targetType).get(key) != 0)
                    memberAL.set(clusterIndex, 1.0);

                TargetClusterIndicator.set(key, memberAL);

            }
            outDist.close();
            iter_c++;
            epsi_cluster = ClusterIndicator.diff(oldTargetClusterIndicator, TargetClusterIndicator);
            System.err.println("Iteration "+iter_c+" in clustering: epsi_clustering is "+ epsi_cluster);

            //reassign membership
            for (int k = 0; k < K; k++)
            {
                HashMap<Integer, Double> membershipHT = new HashMap<Integer, Double> ();
                for (int key: targetKeys)
                {
                    membershipHT.put(key,TargetClusterIndicator.get(key,k));
                }
                curTypeMembership[k].set(targetType, membershipHT);
            }

        }//endloop until no change/minor change

        //Compute cluster membership (posterior probabilities) for attribute objects using the ones obtained for target objects using EM as above
        for (int k = 0; k < K; k++)
        {
            //ranking according to current cluster
            System.err.println("Cluster "+k+":");
            if (priorRank != null)
            {
                curTypeRankVecVec[k] = Ranking(network, targetType, attributeType, curTypeMembership[k], priorRank.get(k), globalRankVec, lambda_C);
            }
            else
            {
                curTypeRankVecVec[k] = Ranking(network, targetType, attributeType, curTypeMembership[k], null, globalRankVec, lambda_C);
            }
        }


        //get posterior probability
        //initial is uniform distribution
        
        for (int k = 0; k < K + 1; k++)
        {
            PK[k] = 1.0 / (K + 1);
        }
        //using EM algorithm to iteratively improve the probability
        epsi_pk = 1;
        iter_pk = 0;

        while (iter_pk < MAX_ITER_PK && epsi_pk > Global.eps_em)
        {
            double[] newPK = new double[K + 1];
            double sum_newPK = 0;
            for (int k = 0; k < K + 1; k++)
            {
                newPK[k] = 0;
            }

            for (int key :targetKeys)
            {
                ArrayList<Double> newMeasure = new ArrayList<Double>(K + 1); //use K+1 to denote the background model, later
                double sumM = 0;
                for (int k = 0; k < K; k++)
                {
                    newMeasure.add(k, curTypeRankVecVec[k].get(targetType).get(key) * PK[k]);
                    sumM += newMeasure.get(k);

                }
                newMeasure.add(K, targetGlobalRankVec.get(key) * PK[K]);
                sumM += newMeasure.get(K);


                for (int k = 0; k < K + 1; k++)
                {
                    if (sumM != 0)
                    {
                        newMeasure.set(k,newMeasure.get(k) / sumM);
                    }
                    else
                    	newMeasure.set(k, 0.);
                    newPK[k] += newMeasure.get(k);
                    sum_newPK += newMeasure.get(k);
                }

            }
            epsi_pk = 0;
            for (int k = 0; k < K + 1; k++)
            {
            	if(sum_newPK!=0.0)
            	{
            		newPK[k] = newPK[k] / sum_newPK;
            		epsi_pk += Math.abs(newPK[k] - PK[k]);
            	}
                PK[k] = newPK[k];
            }
            System.err.println("iteration "+iter_pk+" of P(Z=k), epsi: "+ epsi_pk);
            iter_pk++;
        }

        //use PK to get posterior prob for all types of objects
        for(int key: targetKeys)
        {
            ArrayList<Double> newMeasure = new ArrayList<Double>(K + 1); //use K+1 to denote the background model, later

            double sumM = 0;
            for (int k = 0; k < K; k++)
            {
                newMeasure.add(k, curTypeRankVecVec[k].get(targetType).get(key)* PK[k]);
                sumM += newMeasure.get(k);
            }
            newMeasure.add(K, targetGlobalRankVec.get(key) * PK[K]);
            sumM += newMeasure.get(K);

            for(int k=0; k<K+1; k++)
            {
                if (sumM != 0)
                {
                    clusterResult.get(targetType).set(key, k,newMeasure.get(k) / sumM);
                }
                else
                {
                    clusterResult.get(targetType).set(key, k, 0.0);
                }
            }
        }

        //for attribute type
        for (int i = 0; i < attributeType.length; i++)
        {
            Relation curRelation = network.get(targetType, attributeType[i]);
            Set<Integer> curKeys = Global.entitySet.getByType(attributeType[i]).getKeys();
            for (int colID : curKeys)
            {
                ArrayList<Double> newMeasure = new ArrayList<Double>(K+1);
                double sum = 0;
                for(int k=0; k<K+1; k++)
                {
                    newMeasure.add(k, 0.0);
                }

                HashMap<Integer, Double> curCol = curRelation.getCol(colID);
                if (curCol == null)
                {
                    continue;
                }
                else
                {
                	for (int key: curCol.keySet()) {
                        ArrayList<Double> targetMeasure = clusterResult.get(targetType).get(key);
                        for (int k = 0; k < K + 1; k++)
                        {
                            newMeasure.set(k,newMeasure.get(k) + targetMeasure.get(k));
                            sum += targetMeasure.get(k);
                        }
                    }
                    if(sum == 0)
                    {
                        clusterResult.get(attributeType[i]).set(colID, newMeasure);
                    }
                    else
                    {
                        for (int k = 0; k < K + 1; k++)
                        {
                            newMeasure.set(k,newMeasure.get(k) / sum);
                        }
                        clusterResult.get(attributeType[i]).set(colID, newMeasure);
                    }
                    
                }
            }
        }

        for (int i = 0; i < attributeType.length; i++)
        {
            Set<Integer> keys = Global.entitySet.getByType(attributeType[i]).getKeys();
            for (int k = 0; k < K; k++)
            {
                writeToFile(outfile[i]+"."+k, attributeType[i], keys, (curTypeRankVecVec[k]).get(attributeType[i]));
            }

            writeToFile(outfile_clus[i],clusterResult.get(attributeType[i]),attributeType[i]);
        }
        for (int k = 0; k < K; k++)
        {
            writeToFile(outfile[3]  + "." + k, targetType, targetKeys, (curTypeRankVecVec[k]).get(targetType));
        }
        writeToFile (outfile_clus[3], clusterResult.get(targetType),targetType);


        //calculate important nodes within cluster, and hubs for current level of network

        TypeEntropy typeEntropy = clusterResult.getEntropy(K);
        String [] outfile_rep =  {"conf.rep", "author.rep", "term.rep", "paper.rep"};
        String [] outfile_hub =  {"conf.hub", "author.hub", "term.hub", "paper.hub"};
        //first, important nodes in each cluster for each type, sorting by P(o|k)/e^ entropy(k|o)

        
        for (int i = 0; i < attributeType.length; i++)
        {
            for(int k=0; k<K; k++)
            {
                ArrayList<IDScore> curAttriImportanceAL = new ArrayList<IDScore>();
                Set<Integer> curAttriKeys = Global.entitySet.getByType(attributeType[i]).getKeys();
                HashMap<Integer, Double> entropyHT = typeEntropy.get(attributeType[i]);
                RankVec curRankVec = curTypeRankVecVec[k].get(attributeType[i]);
                for (int attriID :curAttriKeys)
                {
                    IDScore ID_score = new IDScore();
                    ID_score.ID = attriID;
                    if (entropyHT.containsKey(attriID))
                    {
                        ID_score.rankscore = curRankVec.get(attriID) / Math.pow(20, entropyHT.get(attriID))*clusterResult.get(attributeType[i]).get(attriID,k);
                        curAttriImportanceAL.add(ID_score);
                    }
                }
                writeToFile(outfile_rep[i] + "." + k, curAttriImportanceAL, attributeType[i]);
            }           

        }


        //second, hub for current level of network, sorting by P(o)*e^entropy(k|o)
        for (int i = 0; i < attributeType.length; i++)
        {
            for (int k = 0; k < K; k++)
            {
                ArrayList<IDScore> curAttriHubAL = new ArrayList<IDScore>();
                Set<Integer> curAttriKeys = Global.entitySet.getByType(attributeType[i]).getKeys();
                HashMap<Integer, Double> entropyHT = typeEntropy.get(attributeType[i]);
                RankVec curRankVec = curTypeRankVecVec[k].get(attributeType[i]);
                for (int attriID: curAttriKeys)
                {
                    IDScore ID_score = new IDScore();
                    ID_score.ID = attriID;
                    if (entropyHT.containsKey(attriID))
                    {
                        ID_score.rankscore = curRankVec.get(attriID) *entropyHT.get(attriID);
                        curAttriHubAL.add(ID_score);
                    }
                }
                writeToFile(outfile_hub[i] + "." + k, curAttriHubAL, attributeType[i]);
            }           
        }
    }

    public static int getMaxIndex(ArrayList<Double> al)
    {
        double max = Double.MIN_VALUE;
        int ind = 0;
        for (int k = 0; k < al.size(); k++)
        {
            if (max < al.get(k))
            {
                ind = k;
                max = al.get(k);
            }
        }
        return ind;
    }

    public static void writeToFile(String outfile, String type, Set<Integer> keys, RankVec rankVec) throws Throwable
    {
    	outfile=Global.outDir+"/"+outfile;
    	BufferedWriter out = new BufferedWriter(new FileWriter(new File(outfile)));
        ArrayList<IDScore> sortIDAL = rankVec.sort();
        for (int i = 0; i < sortIDAL.size();i++ )
        {
            int ID = sortIDAL.get(i).ID;
            out.write(ID + "\t" + Global.entitySet.getByType(type).getNameByID(ID) + ":\t");
            //for (int k = 0; k < K; k++)
            {
                double rank = rankVec.get(ID);
                out.write(rank + "\t");
            }
            out.write("\n");
        }
        out.close();
    }

    public static void writeToFile(String outfile, ClusterIndicator clusIndicator,String type) throws Throwable
    {
    	outfile=Global.outDir+"/"+outfile;
    	BufferedWriter out = new BufferedWriter(new FileWriter(new File(outfile)));
        Set<Integer> keys = clusIndicator.getKeys();
        for (int key : keys)
        {
            ArrayList<Double> curMeasure = clusIndicator.get(key);
            out.write(key + "\t" + Global.entitySet.getByType(type).getNameByID(key) + "\t");
            for (int k = 0; k < curMeasure.size(); k++)
                out.write(curMeasure.get(k) + "\t");
            out.write("\n");
        }
        out.close();
    }

    public static void writeToFile(String outfile, ArrayList<IDScore> al,String type) throws Throwable
    {
    	outfile=Global.outDir+"/"+outfile;
    	BufferedWriter out = new BufferedWriter(new FileWriter(new File(outfile)));
    	Collections.sort(al,new RankComp());
        for (int i = 0; i < al.size(); i++)
        {
            IDScore curIDScore = al.get(i);
            String name = Global.entitySet.getByType(type).getNameByID(curIDScore.ID);
            out.write(curIDScore.ID + "\t" + name+ "\t" + curIDScore.rankscore+"\n");
        }
        out.close();
    }

    
    public static RankVec Ranking_Iter_Authority2(RelationSet network, String targetType, String cur_attributeType, String []attributeType, TypeMemberShip typeMembership,  double lambda_D, TypeRankVec typeRankVec)
    {
        Relation paperAttriRelation = network.get(targetType, cur_attributeType);
        RankVec attriVec = new RankVec();
        attriVec.copy(typeRankVec.get(cur_attributeType));

        RankVec targetVec = new RankVec();
        targetVec.copy(typeRankVec.get(targetType));

        Set<Integer> attributeKeys = Global.entitySet.getByType(cur_attributeType).getKeys();
        Set<Integer> targetKeys = Global.entitySet.getByType(targetType).getKeys();

        RankVec newTargetVec = new RankVec();
        for (int targetID : targetKeys)
        {
            HashMap<Integer, Double> curRow = paperAttriRelation.getRow(targetID);
            double score = 0;
            if (curRow == null)
            {
                newTargetVec.set(targetID, 0.);
            }
            else
            {
                for (int attriID: curRow.keySet())
                {
                    score += (double)curRow.get(attriID) * attriVec.get(attriID);
                }
                newTargetVec.set(targetID, score);
            }
        }
        newTargetVec.normalization();

        double nonZero = 0;
        for (int targetID : targetKeys)
        {
            if (typeMembership.get(targetType).containsKey(targetID))
            {
                nonZero++;
            }
        }

        RankVec newAttriVec = new RankVec();
        for (int attriID :attributeKeys)
        {
        	HashMap<Integer, Double> curCol = paperAttriRelation.getCol(attriID);
            double score = 0;
            if (curCol == null)
            {
                newAttriVec.set(attriID, 0.);
                continue;
            }
            else
            {
                for (int targetID: curCol.keySet())
                {
                    score += (double)curCol.get(targetID) * targetVec.get(targetID)*(double)typeMembership.get(targetType).get(targetID);
                }
            }
            newAttriVec.set(attriID, score);
        }

        newAttriVec.normalization();
        return attriVec;
    }

    public static RankVec Ranking_Iter_Authority3(RelationSet network, String targetType, String cur_attributeType, String[] attributeType, TypeMemberShip typeMembership, TypeRankVec typeRankVec)
    {
        String authorType = "author";
        Relation paperAttriRelation = network.get(targetType, cur_attributeType);
        Relation paperAuthorRelation = network.get(targetType, authorType);
        RankVec attriVec = new RankVec();
        attriVec.copy(typeRankVec.get(cur_attributeType));
        
        RankVec authorVec = new RankVec();
        authorVec.copy(typeRankVec.get(authorType));

        Set<Integer> attributeKeys = Global.entitySet.getByType(cur_attributeType).getKeys();
        Set<Integer> targetKeys = Global.entitySet.getByType(targetType).getKeys();

        Set<Integer> authorKeys = Global.entitySet.getByType(authorType).getKeys();

        RankVec newAuthorVec = new RankVec();
        double nonZero = 0;
        for (int authorID : authorKeys)
        {
        	HashMap<Integer, Double> curCol = paperAuthorRelation.getCol(authorID);
            double score = 0;
           
            if (curCol == null)
            {
                newAuthorVec.set(authorID, 0.);
            }
            else
            {
                boolean isZero = true;
                for (int targetID : curCol.keySet())
                {
                	HashMap<Integer, Double> curRow = paperAttriRelation.getRow(targetID);
                    if (typeMembership.get(targetType).get(targetID) != 0)
                    {
                        isZero = false;
                    }
                    if (curRow == null)
                        continue;
                    for (int attriID : curRow.keySet())
                    {
                        score += (double)curRow.get(attriID) * attriVec.get(attriID) *typeMembership.get(targetType).get(targetID);
                    }
                }
                if (!isZero)
                    nonZero++;
                newAuthorVec.set(authorID, score);
            }
        }
        newAuthorVec.normalization();

        RankVec newAttriVec = new RankVec();
        for (int attriID : attributeKeys)
        {
        	HashMap<Integer, Double> curCol = paperAttriRelation.getCol(attriID);
            double score = 0;
            if (curCol == null)
            {
                newAttriVec.set(attriID, 0.);
                continue;
            }
            else
            {
                for (int targetID : curCol.keySet())
                {
                    //int outlink = (int)target_outlink[targetID];
                	HashMap<Integer, Double> curRow = paperAuthorRelation.getRow(targetID);
                    if(curRow == null)
                    {
                        continue;
                    }
                    for (int authorID : curRow.keySet())
                    {
                        score += (double)curCol.get(targetID) * authorVec.get(authorID) * (double)typeMembership.get(targetType).get(targetID);
                    }
                }
            }
            newAttriVec.set(attriID, score);
        }

        newAttriVec.normalization();

        Ranking_Iter_Authority2(network, targetType, cur_attributeType, attributeType, typeMembership, 0.9, typeRankVec);

        for (int attriID : attributeKeys)
        {
            double score = attriVec.get(attriID);
            //double score = (double)1 / attriCount;
            attriVec.set(attriID, score);
        }

        //attriVec.copy(newAttriVec);
        attriVec.normalization();
        for (int attriID :attributeKeys)
            newAttriVec.set(attriID,  0);
        double sum = 0;
        for(int targetID : targetKeys)
        {
        	HashMap<Integer, Double> curRow = paperAttriRelation.getRow(targetID);
            
            if (curRow == null)
                continue;
            else
            {
                for (int attriID : curRow.keySet())
                {
                    double score = newAttriVec.get(attriID);
                    double memberScore = typeMembership.get(targetType).get(targetID);
                    newAttriVec.set(attriID, score + curRow.get(attriID) * memberScore);
                    sum += curRow.get(attriID) * memberScore;
                }
            }
        }

        for(int attriID :authorKeys)
        {
            double score = newAttriVec.get(attriID);
            newAttriVec.set(attriID, score / sum);
        }
        newAttriVec.normalization();
        for(int attriID : attributeKeys)
        {
            double score = attriVec.get(attriID);
            //double score = (double)1 / attriCount;
            attriVec.set(attriID, Global.lambda_P* score + (1-Global.lambda_P)*newAttriVec.get(attriID));
        }
        return attriVec;
    }

    
    
    /**
     * Corresponds to the simple ranking scheme to compute the ranking distribution for attribute objects as mentioned in section 4.4 of paper
     * This is used to obtain ranking for terms. Rank of a term depends on papers in which it appears. Each paper contributes weight proportional to number of authors and conferences it is related to.
     * @param network
     * @param targetType
     * @param cur_attributeType
     * @param attributeType
     * @param typeMembership
     * @param priorRank
     * @param lambda_P
     * @param typeRankVec
     * @return
     */
    public static RankVec Ranking_Iter_Count(RelationSet network, String targetType, String cur_attributeType, String []attributeType, TypeMemberShip typeMembership, TypeRankVec priorRank, double lambda, TypeRankVec typeRankVec)
    {
        Relation paperTermRelation = network.get(targetType, cur_attributeType);
        RankVec attriVec = typeRankVec.get(cur_attributeType);

        Set<Integer> attributeKeys = Global.entitySet.getByType(cur_attributeType).getKeys();

        RankVec[] attributeRV = new RankVec[attributeType.length - 1];
        for (int i = 0; i < attributeType.length - 1; i++)
        {
            attributeRV[i] = typeRankVec.get(attributeType[i]);
        }
        
        for(int attriID:attributeKeys)
        {
            HashMap<Integer, Double> curCol = paperTermRelation.getCol(attriID);
            if (curCol == null)
            {
                attriVec.set(attriID, 0);
                continue;
            }

            double score = 0;
            for (int targetID: curCol.keySet()) {
                double targetrank = 1;
                for (int i = 0; i < attributeType.length - 1; i++)  //change the quality of a paper
                {
                    Relation curRelation = network.get(targetType,attributeType[i]);
                    targetrank *= curRelation.getRowAverage(targetID, attributeRV[i]);
                }
                score += curCol.get(targetID) * typeMembership.get(targetType).get(targetID)*targetrank ;
            }
            attriVec.set(attriID, score);
        }

        attriVec.normalization();
        if (priorRank != null && priorRank.containsType(cur_attributeType))
        {
            for (int colID: attributeKeys)
            {
                double score = attriVec.get(colID);
                attriVec.set(colID, (1 - lambda) * score + lambda * priorRank.get(cur_attributeType,colID));
            }
        }
        return attriVec;
    }
    
    @SuppressWarnings("unchecked")
	public static TypeRankVec Ranking_Iter_PGRank(RelationSet network, String targetType, String[] attributeType, TypeMemberShip typeMembership, TypeRankVec priorRank, TypeRankVec typeRankVec)
    {
        //for attribute types
        TypeRankVec newTypeRankVec = new TypeRankVec();
        RankVec[] curAttri_RankVec = new RankVec[attributeType.length - 1];
        Set<Integer>[] attriKeys = new Set[attributeType.length - 1];
        Relation[] relation = new Relation[attributeType.length - 1];
        HashMap<Integer, Integer> [] attri_outlink = new HashMap [attributeType.length-1];

        //old value
        for (int i = 0; i < attributeType.length - 1; i++)
        {
            curAttri_RankVec[i] = new RankVec();
            curAttri_RankVec[i].copy(typeRankVec.get(attributeType[i]));
            attriKeys[i] = Global.entitySet.getByType(attributeType[i]).getKeys();
            relation[i] = network.get(targetType, attributeType[i]);

            attri_outlink[i] = new HashMap<Integer, Integer>();
            for(Integer attriID: attriKeys[i])
            {
                int outlink = relation[i].getCol(attriID).size();
                attri_outlink[i].put(attriID, outlink);
            }
        }

        
        for (int i = 0; i < attributeType.length - 1; i++)
        {
            //suppose C
            for(int attriID:attriKeys[i])
            {
                //CP
                HashMap<Integer, Double> colHT = relation[i].getCol(attriID);
                if (colHT == null)
                {
                    curAttri_RankVec[i].set(attriID, 0.0);
                    continue;
                }
                double score_attriID = 0;
                for (int targetID: colHT.keySet()) {
                    //PA
                    HashMap<Integer, Double> colRowHT = relation[1 - i].getRow(targetID);
                    //PC
                    HashMap<Integer, Double> colSelfHT = relation[i].getRow(targetID);
                    double score = 0;
                    if (colRowHT == null)
                    {
                        continue;
                    }
                    //paper's score = (sum of (author's score / outlink))/conf number of paper
                    for (int attriID_other : colRowHT.keySet()) {
                        int outlink = attri_outlink[1-i].get(attriID_other);
                        score += colRowHT.get(attriID_other) * curAttri_RankVec[1 - i].get(attriID_other)/outlink * typeMembership.get(targetType).get(targetID);
                    }
                    score = score / colSelfHT.size(); //for each paper, how many links to conf and authors?
                    score_attriID += score;
                }
                //if (attributeType[i] == "conf")
                //{
                //    curAttri_RankVec[i][attriID] = score_attriID/colHT.Count;
                //}
                //else
                {
                    curAttri_RankVec[i].set(attriID, score_attriID);
                }
            }
            curAttri_RankVec[i].normalization();

            newTypeRankVec.set(attributeType[i], curAttri_RankVec[i]);
        }

        return newTypeRankVec;

    }
}

