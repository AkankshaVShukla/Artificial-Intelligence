package informedSearch;
/*
 * Akanksha Shukla
 * Unity ID: apshukla
 */

public class GreedySearch {

	SearchUSA s;
	List exploredPath = new List();
	List visited = new List();
	MinPriorityQueue queue = new MinPriorityQueue();
	SearchCity obj = new SearchCity();
	double solCost = 0;

	public void search(String src, String dest, City[] cities, double[][] searchSpace) {
		s = new SearchUSA();
		int srcIndex = obj.searchCity(cities, src);
		int destIndex = obj.searchCity(cities, dest);
		int currentIndex = srcIndex;
		Node currNode = new Node();
		Node destNode = new Node();
		destNode.setCity(cities[destIndex]);
		currNode.setCity(cities[srcIndex]);		
		currNode.setPathCost(s.calculateHeuristics(currNode, destNode));
		currNode.setTotalCost(0);
		currNode.setParent(null);
		queue.insert(currNode);

		while (!queue.empty()) {
			currNode = queue.getMin();
			solCost = currNode.getTotalCost();
			exploredPath.insert(currNode);
			if (currNode.getCity().getName().equals(dest)) {	
				System.out.println("Nodes expanded: ");
				exploredPath.show();
				showPath(currNode);
				break;
			}

			currentIndex = obj.searchCity(cities, currNode.getCity().getName());
			for (int j = 0; j < searchSpace[currentIndex].length; j++) {
				if (searchSpace[currentIndex][j] != 0) {
					Node child = new Node();
					child.setCity(cities[j]);
					child.setTotalCost(currNode.getTotalCost()+searchSpace[currentIndex][j]);
					double cost = s.calculateHeuristics(child, destNode);
					child.setPathCost(cost);
					child.setParent(currNode);
					if(!exploredPath.contains(child)){
						queue.insert(child);
					}
				}
			}
		}

	}

	public void showPath(Node dest) {
		Node n;
		List path = new List();
		for (n = dest; n.getParent() != null; n = n.getParent()) {
			path.insert(n);
		}
		path.insert(n);
		System.out.println("List of nodes in solution path");
		path.show();
		System.out.println("Total Solution cost is: "+ solCost);
	}
}
