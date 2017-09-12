package lab1;


public class Edge {
	private int color;
	private int weight;
	
	public Edge(){
		color = 1;
		weight = 0;
	}
	
	public void setWeight(int w){
		weight = w;
	}
	
	public int getWeight(){
		return weight;
	}
	
	public int getColor(){
		return color;
	}
	
	public void setColor(int c){
		color = c;
	}

}
