package informedSearch;
/*
 * Akanksha Shukla
 * Unity ID: apshukla
 */

public class Astar {
	MinPriorityQueue queue = new MinPriorityQueue();
	SearchCity sc = new SearchCity();
	SearchUSA s = new SearchUSA();
	List exploredPath = new List();
	double solcost =0;

	public void search(String src, String dest, City[] cities, double[][] searchCitySpace) {
		int srcIndex = sc.searchCity(cities, src);
		int destIndex = sc.searchCity(cities, dest);
		int currIndex = srcIndex;
		boolean found = false;
		Node currNode = new Node();
		Node destNode = new Node();
		destNode.setCity(cities[destIndex]);
		currNode.setCity(cities[srcIndex]);
		currNode.setPathCost(s.calculateHeuristics(currNode, destNode));
		currNode.setParent(null);
		currNode.setTotalCost(0);
		queue.insert(currNode);
		
		while (!queue.empty()) {
			currNode = queue.getMin();
			solcost = currNode.getTotalCost();
			exploredPath.insert(currNode);
			if (currNode.getCity().getName().equals(dest)) {				
				found = true;
				destNode = currNode;
				break;
			}
			
			currIndex = sc.searchCity(cities, currNode.getCity().getName());
			for (int j = 0; j < searchCitySpace[currIndex].length; j++) {
				if (searchCitySpace[currIndex][j] != 0) {
					Node child = new Node();
					double cost = searchCitySpace[currIndex][j];
					child.setCity(cities[j]);
					double totalCost = (solcost + cost) + s.calculateHeuristics(child, destNode);
					child.setPathCost(totalCost);
					child.setTotalCost(currNode.getTotalCost()+cost);

					if (!queue.contains(child.getCity()) && (!exploredPath.contains(child))) {
						child.setParent(currNode);
						queue.insert(child);
					}

					else if (queue.contains(child.getCity())) {
						if ((queue.getPathCost(child.getCity()) > child.getPathCost())) {
							child.setParent(currNode);
							queue.insert(child);
						}
					}
				}
			}
		}
		if (found == true) {
			System.out.println("Nodes expanded: ");
			exploredPath.show();
			System.out.println("Solution path:");
			showPath(destNode);
		}
	}

	public void showPath(Node dest) {
		List path = new List();
		Node n;
		double cost = dest.getTotalCost();
		for (n = dest; n.getParent() != null; n = n.getParent()) {
			path.insert(n);
		}
		path.insert(n);		
		System.out.println("List of nodes in solution path");
		path.show();
		System.out.println("Total Solution cost is: "+ cost);
	}
}
