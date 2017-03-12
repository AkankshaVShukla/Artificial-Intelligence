package informedSearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SearchUSA {

	public static void main(String args[]) throws Exception {
		String line;
		String[] pieces;
		int i = 0;
		City[] cities = new City[112];

		try {
			BufferedReader br = new BufferedReader(new FileReader("src\\informedSearch\\usroads.txt"));
			while ((line = br.readLine()) != null) {
				if (line.startsWith("city")) {
					pieces = line.split("\\(");
					pieces = pieces[1].split("\\)");
					pieces = pieces[0].split(",");
					cities[i] = new City(pieces[0], Double.parseDouble(pieces[1]), Double.parseDouble(pieces[2]));
					i++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		SearchCity cd = new SearchCity();

		double[][] searchSpace = cd.readDistances(cities);
		String type = "greedy";//args[0];
		String src = "boston";//args[1];
		String dest = "raleigh";//args[2];
		
		if(type == null || src == null || dest == null){
			System.out.println("Invalid Arguments");
			return;
		}
				 
		if (type.equals("uniform")) {
			UniformSearch u = new UniformSearch();
			u.search(src, dest, cities, searchSpace);
		} else if (type.equals("greedy")) {
			GreedySearch g = new GreedySearch();
			g.search(src, dest, cities, searchSpace);
		} else if (type.equals("astar")) {
			Astar a = new Astar();
			a.search(src, dest, cities, searchSpace);
		}

	}
	public double calculateHeuristics(Node nodeSrc, Node nodeDest) {
		double term1 = 69.5 * (nodeSrc.getCity().getLatitude() - nodeDest.getCity().getLatitude());
		double term2 = 69.5 * Math.cos((nodeSrc.getCity().getLatitude() + nodeDest.getCity().getLatitude())/360 * Math.PI);
		double term3 = nodeSrc.getCity().getLongitude() - nodeDest.getCity().getLongitude();
		return Math.sqrt(Math.pow(term1, 2)+Math.pow(term2*term3, 2));
	}
}
class Node {
	double pathCost;
	double totalCost;
	City city = new City();
	Node parent;
	
	public double getPathCost() {
		return pathCost;
	}
	public void setPathCost(double pathCost) {
		this.pathCost = pathCost;
	}

	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
}
class City {
	private String name;
	private double latitude;
	private double longitude;

	City() {
		name = "";
		latitude = 0.0;
		longitude = 0.0;
	}

	City(String city, double latitude, double longitude) {
		name = city;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}


class SearchCity {
	double[][] searchCitySpace;
	public int searchCity(City[] cities, String c) {
		int index = -1;
		for (int i = 0; i < cities.length; i++) {
			if (cities[i].getName().equals(c)) {
				index = i;
				break;
			}
		}
		return index;
	}

	public double[][] readDistances(City[] cities) throws Exception {
		String line;
		String[] pieces;
		searchCitySpace = new double[cities.length][cities.length];
		BufferedReader br = new BufferedReader(new FileReader("src\\informedSearch\\usroads.txt"));

		try {
			while ((line = br.readLine()) != null) {
				if (line.startsWith("road")) {
					pieces = line.split("\\(");
					pieces = pieces[1].split("\\)");
					pieces = pieces[0].split(",");
					searchCitySpace[searchCity(cities, pieces[0].trim())][searchCity(cities,
							pieces[1].trim())] = Double.parseDouble(pieces[2].trim());
					searchCitySpace[searchCity(cities, pieces[1].trim())][searchCity(cities,
							pieces[0].trim())] = Double.parseDouble(pieces[2].trim());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return searchCitySpace;
	}
}

class List {

	Node[] n = new Node[200];
	int size = 0;

	public void insert(Node node) {
		n[size++] = node;
	}

	public boolean contains(Node node)
	{
		for(int i=0; i<size; i++)
		{
			if(n[i].getCity().getName().equals(node.getCity().getName()))
				return true;
		}
		return false;
	}
	
	public Node search(Node node)
	{
		for(int i=0; i<size; i++)
		{
			if(n[i].getCity().getName().equals(node.getCity().getName()))
				return n[i];
		}
		return n[-1];
	}
	
	public void show() {
		int i=0;
		while(n[i] != null){
			System.out.print(n[i++].getCity().getName()+",");
		}
		System.out.println();
		System.out.println("Number of nodes: "+i);
	}
}