/*
 * Akanksha Shukla
 * Unity ID: apshukla
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class SearchRomania {
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("roads.pl"));
		String a;
		List<String> cities = new ArrayList<String>();
		List<String> edges = new ArrayList<String>();
		while((a=br.readLine())!=null){
			edges.add(a);
			String[] pieces = a.split(",");
			if(!cities.contains(pieces[0]))
				cities.add(pieces[0]);
			if(!cities.contains(pieces[1]))
				cities.add(pieces[1]);
		}
		Graph g = new Graph(cities.size());
		for(String c: cities){
			g.addVertex(c);
		}
		for(String e: edges){
			String[] pieces = e.split(",");
			g.addEdge(pieces[0],pieces[1]);
		}

		//g.display();
		String searchType = args[0];
		String source = args[1];
		String dest = args[2];
		if(source != null && dest != null && searchType!= null){
			if(searchType.equalsIgnoreCase("DFS")){
				g.dfs(source, dest);
			}else if(searchType.equalsIgnoreCase("BFS")){
				g.bfs(source, dest);
			}else{
				System.out.println("Invalid SearchType");
			}
		}else{
			System.out.println("Invalid Arguments");
		}
	}	
}

class Vertex{
	public String label;
	public boolean visited;
	public Vertex(String city){
		label = city;
		visited = false;
	}
}

class Graph{
	private Vertex vertexList[];
	private int adjMatrix[][];
	private int vertexCount;
	//private Stack theStack;

	public Graph(int size){
		vertexList = new Vertex[size];
		adjMatrix = new int [size][size];
		vertexCount = 0;

		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
				adjMatrix[i][j] = 0;
		//theStack = new Stack();	
	}


	public void addVertex(String city){
		vertexList[vertexCount++] = new Vertex(city);
	}

	public void addEdge(String start, String end){
		int s = findIndex(start);
		int e = findIndex(end);
		if(s != -1 && e != -1) {
			adjMatrix[s][e] = 1;
			adjMatrix[e][s] = 1;
		}
	}

	public int findIndex(String city){
		for(int i=0;i<vertexList.length;i++){
			if(vertexList[i].label.equals(city)) {
				return i;
			}
		}
		return -1;
	}

	public void displayGraph(){
		int count =0;
		for(Vertex v: vertexList){
			System.out.println(count+ " " +v.label);
			count++;
		}

		for(int i=0;i<vertexList.length;i++){
			for(int j=0;j<vertexList.length;j++)
				System.out.print(adjMatrix[i][j]);
			System.out.println();
		}
	}

	public void dfs(String start_city,String end_city){
		int s = findIndex(start_city);
		int e = findIndex(end_city);
		List<String> path = new ArrayList<String>();
		Stack<Integer> stk = new Stack<Integer>();	
		vertexList[s].visited = true;
		path.add(vertexList[s].label);
		stk.push(s);
		while(!stk.isEmpty()){
			Integer a =(Integer) stk.peek();
			int v = getAdjUnvisitedVertex(a);
			if(v==-1)
				stk.pop();
			else if(v==e){
				vertexList[v].visited=true;
				path.add(vertexList[v].label);
				break;
			} else {
				vertexList[v].visited=true;
				path.add(vertexList[v].label);
				stk.push(v);
			}
		}

		for(int i=0;i<vertexCount;i++){
			vertexList[i].visited=false;
		}

		//print path
		System.out.println("DFS Path from "+start_city+" to "+end_city);
		for(String p: path){
			System.out.println(p+" ");
		}
		System.out.println("Length of the list is " + path.size());
	}

	public void bfs(String start_city,String end_city){
		int s = findIndex(start_city);
		int e = findIndex(end_city);
		List<String> path = new ArrayList<String>();
		Queue q = new LinkedList();

		vertexList[s].visited = true;
		path.add(vertexList[s].label);
		q.add(s);
		int v2;

		while(!q.isEmpty()) {
			int v1 = (Integer) q.remove();
			while((v2=getAdjUnvisitedVertex(v1))!= -1){
				vertexList[v2].visited = true;
				path.add(vertexList[v2].label);
				q.add(v2);
				if(v2==e){
					q.clear();
				}
			}
		}


		for(int i=0;i<vertexCount;i++){
			vertexList[i].visited=false;
		}

		//print path
		System.out.println("BFS Path from "+start_city+" to "+end_city);
		for(String p: path){
			System.out.println(p+" ");
		}
		System.out.println("Length of the list is " + path.size());
	}


	public int getAdjUnvisitedVertex(int v){
		for(int i=0;i<vertexList.length;i++) 
			if(adjMatrix[v][i]==1 && vertexList[i].visited==false)
				return i;
		return -1;
	}
}
