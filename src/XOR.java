import java.text.DecimalFormat;
import java.util.ArrayList;

public class XOR {
	public String replaceSpace(StringBuffer str) {
    	String str1=str.toString();
        String str2="";
        for(int i=0;i<str1.length();i++){
            if(str1.charAt(i)==' '){
                str2+="%20";
            }
            else{
                str2+=str1.charAt(i);
            }
        }
        return str2;
    }
	
	class ListNode {
		        int val;
		        ListNode next = null;
		
		        ListNode(int val) {
		            this.val = val;
		        }
		        
		     
		   }
	
	 public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
	        ArrayList<Integer> list=new ArrayList<Integer>();
	        ListNode p=listNode;
	        ListNode s=listNode;
	        ListNode q=p.next;
	        while(q!=null){
	            s.next=q.next;
	            q.next=p;
	            p=q;
	            q=s.next;
	        }
	        
	        while(p!=null){
	            list.add(p.val);
	            p=p.next;
	        }
	        return list;
	    }
	 
	 public void myPrint(){
		 ListNode listNode=new ListNode(0);
		 ListNode n=new ListNode(1);
		 listNode.next=n;
		 n.next=new ListNode(2);
		 n.next.next=new ListNode(3);
		// listNode.next=new ListNode(4);
		 ArrayList<Integer> list=printListFromTailToHead(listNode);
		 for(int i=0;i<list.size();i++){
			 System.out.println(list.get(i));
		 }
	 }
	 
	 class TreeNode {
		      int val;
		      TreeNode left;
		      TreeNode right;
		      TreeNode(int x) { val = x; }
		  }
	 
	 public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
	        TreeNode t=new TreeNode(pre[0]);
	        int index =pre[0];
	        int l_len=0;
	        int r_len=0;
	        for(int i=0;i<in.length;i++){
	            if(in[i]==index){
	                l_len=i;
	                r_len=in.length-i-1;
	                break;
	            }
	        }
	        if(l_len==0)
	            t.left=null;
	        else{
	            int[] pre1=new int[l_len];
	            int[] in1=new int[l_len];
	            for(int i=0;i<l_len;i++){
	                pre1[i]=pre[i+1];
	                in1[i]=in[i];
	            }
	            t.left=reConstructBinaryTree(pre1,in1);
	        }
	        if(r_len==0){
	            t.right=null;
	        }else{
	            int[] pre2=new int[r_len];
	            int[] in2=new int[r_len];
	            for(int i=l_len+1;i<in.length;i++){
	                pre2[i-l_len-1]=pre[i];
	                in2[i-l_len-1]=in[i];
	            }
	            t.right=reConstructBinaryTree(pre2,in2);
	        }
	        return t;
	    }
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 DecimalFormat formater = new DecimalFormat();
		 formater.setMinimumFractionDigits(5);
		 System.out.println(formater.format(1.23232143));
	}

}
