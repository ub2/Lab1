package lab1;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class mainWindow {
	private static String fileName;
	private static final int infinite = 1000;
	//private String filePath;
	
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		System.out.println("Choose the input mode: 0 for file, 1 for console");
		int option = in.nextInt();
		if(option == 0){
			System.out.println("Input file name(suffixed included):");
			do{
				fileName = in.next();
			}while(!fileName.contains(".txt")&&!fileName.contains(".bin"));
		}
		Graph G = createDirectedGraph(fileName);
		
		//showDirectedGraph(G);
		
		//test shortest path
		String w1, w2;
		//while(true){
		w1 = in.next(); w2 = in.next();
		String path = calcShortestPath(G,w1,w2);
		System.out.println(path);
		String[] paths = path.split("\\|\\|");
		int color = 3;
		for(String aString : paths){
			String[] singlePath = aString.split("<-");
			G.getNodes().get(G.getNodeSet().get(singlePath[0])).setColor(1);
			G.getNodes().get(G.getNodeSet().get(singlePath[singlePath.length-1])).setColor(2);
			for(int i = 0;i<singlePath.length -1 ;i++){
				G.getNodeList().get(singlePath[i+1]).get(singlePath[i]).setColor(color);
			}
			showDirectedGraph(G);
		}
		//}
		
//		//test random walk
//		for(int i =0;i<20;i++){
//		System.out.println(randomWalk(G));
//		}
//		
//		//test create new text
//		String newText;
//		newText = "Seek to explore new and exciting synergies";
//		newText = generateNewText(G, newText);
//		System.out.println(newText);
//		
//		//test found bridge word
//		String word1, word2;
//		word1 = in.next(); word2 = in.next();
//		String statement = queryBridgeWords(G, word1, word2);
//		if(!statement.contains("!")){//modify the sentence
//			String[] words = statement.split(", ");
//			StringBuffer tmp = new StringBuffer();
//			for(int i = 0;i<words.length-2;i++){
//				tmp.append(words[i] + ", ");
//			}
//			tmp.append(((words.length>1)?"and ":" ") + words[words.length-1] + ".");
//			statement = "The bridge words from \"" + word1 + "\" to \"" + word2 + "\" " + ((words.length>1)?"are:":"is:") + tmp.toString();
//		}
//		System.out.println(statement);
//		in.close();
	}

	//find bridge word in the sentence
	public static String queryBridgeWords(Graph G, String w1, String w2){
		ArrayList<String> result = new ArrayList<String>();
		String s;
		if(G.getNodeSet().containsKey(w1)&&G.getNodeSet().containsKey(w2)){
			HashMap<String, Edge> e = G.getNodeList().get(w1);
			for(String i : e.keySet()){
				if(G.getNodeList().get(i)!=null&&G.getNodeList().get(i).containsKey(w2)){
					result.add(i);
				}
			}
			StringBuffer tmp = new StringBuffer();

			if (result.size()==0)
				s = "No bridge words from \"" + w1 + "\" and \"" + w2 + "\"!";
			else{
				//for(int i = 0;i<result.size()-1;i++){
				for(int i = 0;i<result.size();i++){
					tmp.append(result.get(i) + ", ");
				}
				//tmp.append("and " + result.get(result.size() - 1) + ".");
				//s = "The bridge words from \"" + w1 + "\" to \"" + w2 + "\" are:" + tmp.toString();
				s = tmp.toString();
			}
		}
		else if(G.getNodeSet().containsKey(w2)){
			s = "No \"" + w1 + "\" in the graph!";
		}
		else if(G.getNodeSet().containsKey(w1)){
			s = "No \"" + w2 + "\" in the graph!";
		}
		else
			s = "No \"" + w1 + "\" and \"" + w2 + "\" in the graph!";
		return s;
		 
	 }
	
	//create new sentence based on the bridge words
	public static String generateNewText(Graph G, String inputText){
		String[] words = inputText.split("[^a-zA-Z]+");
		String temp = null;
		String[] bridgeWords;
		StringBuffer newSentence = new StringBuffer();
		Random rand = new Random();
		for(int i = 0;i<words.length-1;i++){//traverse the new text
			temp = queryBridgeWords(G, words[i], words[i+1]);//find bridge words
			newSentence.append(words[i] + " ");
			if(!temp.contains("!")){//if there is any bridge word exists
				bridgeWords = temp.split(", ");
				newSentence.append(bridgeWords[rand.nextInt(bridgeWords.length)] + " ");
			}
		}
		newSentence.append(words[words.length-1]);
		return newSentence.toString();
	}
	
	//generate new graph
	public static Graph createDirectedGraph(String filename){
		String temp = null;
		if(filename == null){//read from console
			System.out.println("Input the sentence:");
			Scanner in = new Scanner(System.in);
			temp = in.nextLine();
			in.close();
		}
		else{//read from file
			File file = new File(filename);
	        BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new FileReader(file));
	            if ((temp = reader.readLine()) == null) {
	               System.out.print("File is empty!");
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
		}
        //create graph
		String[] words = temp.split("[^a-zA-Z]+");
		//establish the Graph
		Graph G = new Graph();
		//transform the words to lower case & add words
		for(int i =0;i<words.length;i++){
			words[i] = words[i].toLowerCase();
			G.addNode(words[i]);
		}
		//add edge
		for(int i = 0;i<words.length -1;i++){
			G.addEdge(words[i], words[i+1]);
		}
		return G;
	}

	//create the graph based on the data
	public static void showDirectedGraph(Graph G){
		List<String> color = Arrays.asList("black", "blue", "red","green","yellow");
		GraphViz gv = new GraphViz();
		gv.addln(gv.start_graph());
		Map<String,HashMap<String, Edge>> g = G.getNodeList(); 
//		HashMap<String,Integer> e=new HashMap<String,Integer>();
//		e.put("a", 1);e.put("b", 2);e.put("c", 1);e.put("d",3);e.put("m", 0);
//		g.put("a", e);
//		g.put("c", e);
		for (String i : g.keySet()) {
			HashMap<String, Edge> k=g.get(i);
			for (String j : k.keySet()) {
				gv.addln(i+"->"+j+"[color="+color.get(k.get(j).getColor())+
						",label=\""+ k.get(j).getWeight()+"\"];");
			}
		}
		gv.addln(gv.end_graph());
		System.out.println(gv.getDotSource());
		String type = "png";
		File out = new File("out." + type);
		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
	}
	
	//search a path randomly
	public static String randomWalk(Graph G){
		//define some variable and initialize them at the same time
		Random random = new Random();
		HashMap<String, Edge> map;
		//ArrayList <String> tempList;
		String[] tempList;
		StringBuffer path = new StringBuffer();
		//generate a word randomly
		String tempPath = G.getNodes().get(random.nextInt(G.getVertexNum())).getWord();//get first word
		String nextWord = tempPath;
		boolean stop = false;//control the stop by key event
		while(tempPath!=null && path.indexOf(tempPath) ==-1 && !stop){
			path.append(tempPath);//add path
			map = G.getNodeList().get(nextWord);
			if(map == null) break; //if there is no road then exit
			tempList = map.keySet().toArray(new String[0]);
			nextWord = (tempList.length>=1)?tempList[random.nextInt(tempList.length)]:null;
			tempPath = (nextWord==null)?null:("->" + nextWord);//record the path
		}
		return path.toString();
	}

	public static String calcShortestPath(Graph G, String word1, String word2){
		int[] minDistance = new int[G.getVertexNum()];
		int word1Sub = -1, word2Sub = -1;
		Set<Integer> mark = new HashSet<Integer>();
		int[] path = new int[G.getVertexNum()];
		HashMap<String, Edge> tempMap = G.getNodeList().get(word1);
		String tempWord;
		if(tempMap == null)//the word has no connection with others
			return null;
		//initialize all the variable
		for(int i = 0;i < G.getVertexNum();i++){
			tempWord = G.getNodes().get(i).getWord();
			if(tempMap.containsKey(tempWord))
				minDistance[i] = tempMap.get(tempWord).getWeight();
			else if(!tempWord.equals(word1))
				minDistance[i] = infinite;
			else
				word1Sub = i;
			if(tempWord.equals(word2))
				word2Sub = i;
			path[i] = -1;
			mark.add(i);
		}
		mark.remove(word1Sub);
		for(int i =0;i<G.getVertexNum()-1;i++){//traverse all the vertex and calculate the minimal distance
			//find minimal distance 
			int min = infinite;
			int sub = -1;
			for(int a : mark){
				if(min > minDistance[a]){
					sub = a;
					min = minDistance[a];
				}
			}
			if(sub == -1)
				break;
			//remove minimal edge 
			mark.remove(sub);
			//update the distance
			HashMap<String, Edge> localMap = G.getNodeList().get(G.getNodes().get(sub).getWord());
			if(localMap!=null){
				for(int a : mark){
					String tWord = G.getNodes().get(a).getWord();
					int pastDistance = minDistance[a];
					int newDistance = (localMap.containsKey(tWord))?(localMap.get(tWord).getWeight()+min):(infinite+1);
					if(newDistance < pastDistance){
						minDistance[a] = newDistance;
						path[a] = sub;  
					}
				}
			}
		}
//		//get path(find one path)
//		StringBuffer result = new StringBuffer();
//		int subscript = word2Sub;
//		boolean find = false;
//		while(subscript != -1){
//			result.append(G.getNodes().get(subscript).getWord() + "<-");
//			subscript = path[subscript];
//			if(subscript == word2Sub)
//				find = true;
//		}
//		result.append(word1);
//		return (find)?result.toString():null;
		Set<Integer> destination = new HashSet<Integer>();
		StringBuffer result = new StringBuffer();
		if(word2 == null){
			for(int i = 0;i<G.getVertexNum();i++){
				if(i!=word1Sub)
					destination.add(i);
			}
		}
		else
			destination.add(word2Sub);
		int subscript;
		StringBuffer tmp = new StringBuffer();
		for(int a : destination){
			if(a == -1)
				continue;
			subscript = a;
			tmp.setLength(0);
			while(subscript != -1){
				tmp.append(G.getNodes().get(subscript).getWord() + "<-");
				subscript = path[subscript];
			}
			if(a != -1){
				result.append(tmp + word1);
				result.append("||");
			}
		}
		return (destination.isEmpty())?null:result.toString();
	}
}
