package informedSearch;
/*
 * Akanksha Shukla
 * Unity ID: apshukla
 */

public class UniformSearch {

	List exploredPath = new List();
	List visited = new List();
	MinPriorityQueue queue = new MinPriorityQueue();
	SearchCity sc = new SearchCity();

	public void search(String src, String dest, City[] cities, double[][] searchCitySpace) {

		int srcIndex = sc.searchCity(cities, src);
		int currentIndex = srcIndex;
		Node currentNode = new Node();
		Node destinationNode = new Node();
		currentNode.setCity(cities[srcIndex]);
		currentNode.setPathCost(0);
		currentNode.setParent(null);
		queue.insert(currentNode);
		boolean found = false;
		while (!queue.empty()) {
			exploredPath.insert(currentNode);
			currentNode = queue.getMin();
			if (currentNode.getCity().getName().equals(dest)) {
				{
					System.out.println("Total Solution cost is: "+ currentNode.getTotalCost());
					destinationNode = currentNode;
					found = true;
					break;
				}
			}

			currentIndex = sc.searchCity(cities, currentNode.getCity().getName());
			for (int j = 0; j < searchCitySpace[currentIndex].length; j++) {
				if (searchCitySpace[currentIndex][j] != 0) {
					Node child = new Node();
					double cost = searchCitySpace[currentIndex][j];

					child.setCity(cities[j]);
					child.setPathCost(currentNode.pathCost + cost);
					child.setTotalCost(currentNode.pathCost + cost);

					if (!queue.contains(child.getCity()) && (!exploredPath.contains(child))) {
						child.setParent(currentNode);
						queue.insert(child);
					}

					else if (queue.contains(child.getCity())) {
						if ((queue.getPathCost(child.getCity()) > child.getPathCost())) {
							child.setParent(currentNode);
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
			showPath(destinationNode);
		}
	}

	public void showPath(Node dest) {
		List path = new List();
		Node n;
		for (n = dest; n.getParent() != null; n = n.getParent()) {
			path.insert(n);
		}
		path.insert(n);
		System.out.println("List of nodes in solution path");
		path.show();
	}
}
