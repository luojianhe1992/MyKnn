import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Classifier {
	
	public static void main(String[] args) {
		String inputFileName = "IrisTrain.csv";
		String outputFileName = "IrisTest.csv";
		
		//read the training data and store in the ArrayList<KnnNode>
		ArrayList<KnnNode> trainingData = DataReader.parse(inputFileName);
		
		//read the testing data and store in the ArrayList<KnnNode>
		ArrayList<KnnNode> testingData = DataReader.parse(outputFileName);
		
		Set<String> labelSet = new HashSet<String>();
		
		for(KnnNode temp:trainingData){
			labelSet.add(temp.getLabel());		
		}
		
		int labelSize = labelSet.size();

		int default_k = 5;
		
		
		
		ArrayList<KnnNodeGroup> dataGroup = new ArrayList<KnnNodeGroup>();
		
		for(int i=0;i<labelSize;i++){
			dataGroup.add(new KnnNodeGroup());
		}
		
		String [] labelArray = new String[labelSize];
		
		int countLabel = 0;
		for(String str:labelSet){
			labelArray[countLabel] = str;
			countLabel++;
		}
		
		for(int i=0;i<labelSize;i++){
			dataGroup.get(i).setLabel(labelArray[i]);
		}
		
		for(KnnNode temp:trainingData){
			for(int i=0;i<labelSize;i++){
				if(temp.getLabel().equals(labelArray[i])){
					dataGroup.get(i).getNodeList().add(temp);
				}
			}
		}
		
//		System.out.println(dataGroup);
		
		ArrayList<ArrayList<ArrayList<Double>>> distanceList = new ArrayList<ArrayList<ArrayList<Double>>>(); 
		
		for(int i=0;i<testingData.size();i++){
			
			//new a ArrayList<ArrayList<Double>> to store the information for each testingData
			distanceList.add(new ArrayList<ArrayList<Double>>());
			
			for(int j=0;j<labelSize;j++){
				distanceList.get(i).add(new ArrayList<Double>());
			
				distanceList.get(i).set(j, dataGroup.get(j).getDistanceToGroup(testingData.get(i)));
			}
		}
		
//		System.out.println("****************************");
//		System.out.println(distanceList);
//		System.out.println("****************************");

//		System.out.println(trainingData);
		 
		
		int [] labelNum = new int [labelSize];
		
		for(int i=0;i<labelSize;i++){
			System.out.println(labelArray[i]);
		}
		
		//KNN算法
		for(KnnNode node:testingData){
			for(KnnNode train:trainingData){
				train.getNodeDistance(node);
			}
			ArrayList<KnnNode> tempList = sortArrayListReturn(trainingData, default_k);
			System.out.println(tempList);
			for(int i=0;i<tempList.size();i++){
				for(int j=0;j<labelSize;j++){
					if(tempList.get(i).getLabel().equals(labelArray[j])){
						labelNum[j]++;
					}
				}
			}
			int maxNum = 0;
			int maxIndex = -1;
			for(int i=0;i<labelSize;i++){
				if(maxNum < labelNum[i]){
					maxNum = labelNum[i];
					maxIndex = i;
				}
			}
			node.setLabel(labelArray[maxIndex]);
			
			//after using the value, reset the value to the original value
			for(int i=0;i<labelSize;i++){
				labelNum[i] = 0;
			}
		}
		System.out.println(testingData);
	}
	
	//sorting
	public static ArrayList<KnnNode> sortArrayListReturn(ArrayList<KnnNode> nodeList, int default_k){
		
		ArrayList<KnnNode> temp = nodeList;
		
		//sorting
		for(int i=0;i<temp.size();i++){
			for(int j=i + 1;j<temp.size();j++){
				if(temp.get(j).getDist() < temp.get(i).getDist()){
					KnnNode tempNode = new KnnNode();
					tempNode = temp.get(i);
					temp.set(i, temp.get(j));
					temp.set(j, tempNode);
				}
			}
		}
		ArrayList<KnnNode> result = new ArrayList<KnnNode>();
		for(int i=0;i<default_k;i++){
			result.add(temp.get(i));
		}
		return result;
	}
}
