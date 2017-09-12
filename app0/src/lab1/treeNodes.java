package lab1;

import java.util.ArrayList;

public class treeNodes{
	private int weight;
	private String word;
	private ArrayList<treeNodes> children = new ArrayList<treeNodes>();
	private treeNodes father = null;
	
	public treeNodes(treeNodes father, int weight, String word) {
		this.father = father;
		this.weight = weight;
		this.word = word;
	}
	public int getWeiht(){
		return weight;
	}
	public String getWord(){
		return word;
	}
	public ArrayList<treeNodes> getChildren(){
		return children;
	}
	public treeNodes getFather(){
		return father;
	}
}
