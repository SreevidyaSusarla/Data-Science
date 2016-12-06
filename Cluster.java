 import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
 
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
 
public class Cluster {
 
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
 
		return inputReader;
	}
 
	public static void main(String[] args) throws Exception {
		SimpleKMeans kmeans = new SimpleKMeans();
 
		kmeans.setSeed(10);
 
		kmeans.setPreserveInstancesOrder(true);
		kmeans.setNumClusters(5);
 
		//BufferedReader datafile = readDataFile("C:/Users/susar/Desktop/Teams.arff"); 
		BufferedReader datafile = readDataFile("C:/Users/susar/Desktop/storm.arff");
		Instances data = new Instances(datafile);
 
 
		kmeans.buildClusterer(data);
 
		
		int[] assignments = kmeans.getAssignments();
 
		int i=0;
		int cust0=0,cust1=0,cust2=0,cust3=0,cust4=0;
		for(int clusterNum : assignments) {
		    
		    if(clusterNum==0)
		    {
		    	cust0++;
		    }
		    else if(clusterNum==1)
		    {
		    	cust1++;
		    }
		    else if(clusterNum==2)
		    {
		    	cust2++;
		    }
		    else if(clusterNum==3)
		    {
		    	cust3++;
		    }
		    else 
		    {
		    	cust4++;
		    }
		   // i++;
		}
		System.out.println("Cluster 0:"+cust0);
		System.out.println("Cluster 1:"+cust1);
		System.out.println("Cluster 2:"+cust2);
		System.out.println("Cluster 3:"+cust3);
		System.out.println("Cluster 4:"+cust4);
		
	}
}
