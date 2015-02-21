import java.util.ArrayList;

public class KnnNodeGroup {
	ArrayList<KnnNode> nodeList;
	String label;
	
	public KnnNodeGroup() {
		super();
		nodeList = new ArrayList<KnnNode>();
	}

	public KnnNodeGroup(String label) {
		super();
		this.label = label;
		nodeList = new ArrayList<KnnNode>();
	}

	public KnnNodeGroup(ArrayList<KnnNode> nodeList, String label) {
		super();
		this.nodeList = nodeList;
		this.label = label;
	}
	
	public ArrayList<KnnNode> getNodeList() {
		return nodeList;
	}

	public void setNodeList(ArrayList<KnnNode> nodeList) {
		this.nodeList = nodeList;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	//get the distance from one node to a group node list
	public ArrayList<Double> getDistanceToGroup (KnnNode node){
		ArrayList<Double> distanceResult = new ArrayList<Double>();
		
		for(KnnNode temp : nodeList){
			double distance = temp.getNodeDistance(node);
			distanceResult.add(distance);
		}
		return distanceResult;
	}
	
	//
	public ArrayList<KnnNode> getDistanceToGroupReturnNode(KnnNode node){

		for(KnnNode temp : nodeList){
			node.getNodeDistance(temp);
		}
		
		return nodeList;
	
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<nodeList.size();i++){
			sb.append("label:" + label + "features: ");
			for(int j=0;j<nodeList.get(i).getFeatures().length;j++){
				sb.append(nodeList.get(i).getFeatures()[j]+" "+"dist:"+nodeList.get(i).getDist());
			}
			sb.append("\n");
		}
		return sb.toString(); 
	}
}
