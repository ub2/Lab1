package lab1;

import java.util.ArrayList;
import java.util.Scanner;

class MyThread implements Runnable {
	private Graph G;
	private String presentWord;
	private StringBuffer path = new StringBuffer();
	private static int autoProcess;
	private static boolean finish = false;
	Thread t;
	private boolean stop = false;
	private Thread others;
	private boolean terminate = false;
	
	public Thread getThread(){
		return t;
	}
	
	
	public MyThread(Graph G, String presentWord) {
		// TODO Auto-generated constructor stub
		this.G = G;
		this.presentWord = presentWord;
		autoProcess = 1;
		t = new Thread(this,"secondThread");
		System.out.println(t);
        t.start();	
	}
	
	public MyThread(boolean stop, Thread others) {
		// TODO Auto-generated constructor stub
		this.stop = stop;
		this.others = others;
		autoProcess = 0;
		t = new Thread(this,"thirdThread");
		System.out.println(t);
        t.start();	
	}
	
	public String getPath(){
		return path.toString();
	}
	
	 @Override  
	 public void run() {  
		 if(stop == true){
			 String s = null;
			 Scanner in = new Scanner(System.in);
			 //while(!terminate){
			 while(true){
				 s = in.nextLine();
				 if(s.isEmpty()){
					// if(finish == false)
						 finish = true;
					 //else
					//	 finish = false;
					 break;
				 }
			 }
		 }
		 else{
			 ArrayList<Edge> edges = new ArrayList<>();
			   String nextWord = null;
			   Edge tempEdge = null;
			   //presentWord = "civilizations";
			   path.append(presentWord);
			   while(!finish||autoProcess == 1) { 
		    	   nextWord = mainWindow.moveOneStep(G, presentWord, path.toString());
		    	   if(G.getNodeList().get(presentWord)!=null&&nextWord!=null){
		    		   tempEdge = G.getNodeList().get(presentWord).get(nextWord);
			    	   presentWord = nextWord;
			    	   path.append("->" + presentWord);
			    	   System.out.println(path.toString());
			    	   if(!edges.contains(tempEdge))
			    		   edges.add(tempEdge);
			    	   else{
			    		   //terminate = true;
			    		   break;
			    	   }
		    	   }
		    	   else{
		    		   //terminate = true;
		    		   break;
		    	   }
		            try {  
		            	if(autoProcess == 0){		            		
		            		Thread.sleep(2000);
		            	}
		            	else{   
		            		Scanner in = new Scanner(System.in);
		            		String s = in.nextLine();
		   				 	while(!s.isEmpty()){
		   				 		s = in.nextLine();
		   				 	};
		            	}
		            } catch (InterruptedException e) {  
		            	e.printStackTrace();  
		            }  
		       }  
		 }
	          
	}  
};