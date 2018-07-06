
public class CNode {

	CNode next;
	CNode behind;
	String value;
	
	public CNode(){
		next=null;
		behind=null;
		value="";
	}
	public CNode(String v){
		next=null;
		behind=null;
		value=v;
	}
}
