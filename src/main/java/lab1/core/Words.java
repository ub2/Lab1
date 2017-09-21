//package lab1.core;
//
//public class Words {
//	private String word1;
//	private String word2;
//	private String originWord = null;
//	private enum postfix {ing, ied, ies, es, s}
//	public Words(String word1, String word2) {
//		this.word1 = word1;
//		this.word2 = word2;
//	}
//	
//	public Words (){}
//	
//	public void setWord1(String word1){
//		this.word1 = word1;
//	}
//	
//	public void setWord2(String word2) {
//		this.word2 = word2;
//	}
//	
//	public String getOriginWord(){
//		return originWord;
//	}
//	
////	test whether two words share the same meaning
//	public boolean isSame(){
//		int len1 = word1.length()-1;
//		int len2 = word2.length()-1;
//		String s1 = null,s2 = null;
//		boolean tmp = false;//len1 not change?
//		if(len1>len2)
//			len1 = len2;
//		else
//			tmp = true;
//		s1 = word1.substring(0, len1);
//		s2 = word2.substring(0, len1);
//		while(!s1.equals(s2)&&len1>0){
//			s1 = s1.substring(0, --len1);
//			s2 = s2.substring(0, len1);
//		}
//		if(len1 == 0)
//			return false;
//		String post = null;
//		if(tmp){
//			post = word1.substring(len1);
//			originWord = word1;
//		}
//		else
//			originWord = word2;
//		tmp = false;
//		for(postfix a : postfix.values()){
//			if(post!=null&&post.equals(a.toString()))
//				tmp = true;
//		}
//		return tmp;
//	}
//
//}
