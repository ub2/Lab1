package lab1;

public class Node {
	private String word;
	private int color;
	
	public Node(){
		word = null;
		color = 0;
	}
	
	public Node(String word){
		this.word = word;
		color = 0;
	}
	
	public String getWord(){
		return word;
	}
	
	public void setWord(String word){
		this.word = word;
	}
	
	public int getColor(){
		return color;
	}
	
	public void setColor(int color){
		this.color = color;
	}
}
