package informedSearch;
/*
 * Akanksha Shukla
 * Unity ID: apshukla
 */

public class MinPriorityQueue {

	Node[] nodes = new Node[100];
	int heapSize = 0;

	public void heapify(int i) {
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		int smallest = 10000;
		Node temp;
		if (l <= heapSize && nodes[l].getPathCost() < nodes[i].getPathCost())
			smallest = l;
		else
			smallest = i;
		if (r <= heapSize && nodes[r].getPathCost() < nodes[i].getPathCost())
			smallest = r;
		if (smallest != i) {
			temp = nodes[smallest];
			nodes[smallest] = nodes[i];
			nodes[i] = temp;
			heapify((i)/2);
		}

	}

	public void insert(Node A) {
		nodes[heapSize] = new Node();
		nodes[heapSize].setCity(A.getCity());
		nodes[heapSize].setParent(A.getParent());
		nodes[heapSize].setPathCost(A.getPathCost());
		nodes[heapSize].setTotalCost(A.getTotalCost());
		heapify((heapSize-1)/2);
		heapSize = heapSize + 1;

	}

	public Node qMin() {
		return nodes[0];
	}

	public Node getMin() {
		if (heapSize < 0) {
			System.out.println("Queue Underflow");
			return nodes[-1];
		}
		Node min = nodes[0];
		nodes[heapSize] = null;
		nodes[0] = nodes[heapSize - 1];
		heapSize--;
		heapify(0);
		return min;
	}
	
	public void print()
	{
		for(int i = 0; i< heapSize; i++)
		{
			System.out.print(nodes[i].getCity().getName()+" ");
			System.out.println(nodes[i].getPathCost());
		}
	}
	
	public boolean contains(City c)
	{
		for(int i = 0; i< heapSize; i++)
		{
			if(nodes[i].getCity().getName().equals(c.getName()))
				return true;
		}
		return false;
	}
	
	public double getPathCost(City c)
	{ int i=0;
		for(i = 0; i< heapSize; i++)
		{
			if(nodes[i].getCity().getName().equals(c.getName()))
				return nodes[i].getPathCost();;
		}
		return nodes[-1].getPathCost();
	}
	
	public boolean empty()
	{
		if(heapSize == 0)
			return true;
		else
			return false;
	}
}
