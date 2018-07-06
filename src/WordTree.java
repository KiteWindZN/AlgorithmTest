import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordTree {

	List<String> list = new ArrayList<String>();

	class TNode {
		TNode left;
		TNode right;
		int value;
		char chracter;

		TNode(char ch) {
			this.left = null;
			this.right = null;
			value = 0;
			chracter = ch;
		}
	}

	public void myRead() throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader("Harry_Potter_and_Chamber_of_Secrets.txt"));
		String line = bf.readLine();
		while (line != null) {

			String[] strs = line.split(" ");

			for (int i = 0; i < strs.length; i++) {
				strs[i] = strs[i].toLowerCase();
				String[] resS = strs[i].split("--");
				for (int j = 0; j < resS.length; j++) {
					resS[j] = pre_process(resS[j]);
					if (resS[j].length() > 0) {

						list.add(resS[j]);
					}
				}
			}
			line = bf.readLine();
		}
	}

	public TNode Build_WordTree() {
		TNode tree = new TNode('#');
		for (int i = 0; i < list.size(); i++) {
			String str = list.get(i);
			TNode tmpT = tree;
			int j = 0;
			for (j = 0; j < str.length(); j++) {
				TNode t = tmpT.left;
				int flag = 0;
				if (t == null) {
					char ch = str.charAt(j);
					TNode newT = new TNode(ch);
					t = newT;
					tmpT.left = t;
					tmpT = tmpT.left;

				} else {
					if (t.chracter == str.charAt(j)) {
						tmpT = t;
					} else {
						while (t.right != null) {
							if (t.chracter == str.charAt(j)) {
								flag = 1;
								break;
							}
							if (t.right.chracter == str.charAt(j)) {
								flag = 2;
								break;
							}
							t = t.right;
						}
						if (flag == 1) {
							tmpT = t;
						} else if (flag == 2) {
							tmpT = t.right;
						} else {
							char ch = str.charAt(j);
							TNode newT = new TNode(ch);
							t.right = newT;
							tmpT = t.right;
						}
					}
				}
				if (j == str.length() - 1)
					tmpT.value = 1;
			}
		}
		return tree;
	}

	public String pre_process(String word) {
		String str = "";
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) >= 'a' && word.charAt(i) <= 'z') {
				str = str + word.charAt(i);
			}
		}
		return str;
	}

	public boolean isValid(String str,TNode tree){
		TNode t=tree.left;
		int i=0;
		for(;i<str.length();i++){
			int flag=0;
			while(t!=null){
				if(t.chracter==str.charAt(i)){
					
					flag=1;
					break;
				}
				t=t.right;
			}
			if(flag==0)
				return false;
			if(i==str.length()-1){
				if(t.value==0)
					return false;
				else return true;
			}
			 t=t.left;
		}
		if(i<str.length())
			return false;
		return true;
	}
	public void myTest(String[] strS){
		TNode tree=Build_WordTree();
		for(int i=0;i<strS.length;i++){
			String str=strS[i];
			str=pre_process(str.toLowerCase());
			if(!isValid(str,tree)){
				System.out.println(str+ " : word is wrong");
			}
		}
		System.out.println("Success");
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		WordTree obj = new WordTree();
		obj.myRead();
		
		Scanner sc=new Scanner(System.in);
		
		String line=sc.nextLine();
		String[] strS=line.split(" ");
		obj.myTest(strS);
	}

}
