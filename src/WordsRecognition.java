import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordsRecognition {

	int[][][] charS = new int[26][53][53];
	List<String> list = new ArrayList<String>();

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

	public String pre_process(String word) {
		String str = "";
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) >= 'a' && word.charAt(i) <= 'z') {
				str = str + word.charAt(i);
			}
		}
		return str;
	}

	public void buildFA() {
		for (int i = 0; i < list.size(); i++) {

			int tmp = list.get(i).charAt(0) - 'a';
			if (list.get(i).length() == 1) {
				charS[0][0][tmp * 2 + 2] = 1;
				continue;
			} else
				charS[0][0][tmp * 2 + 1] = 1;
			for (int j = 0; j < list.get(i).length() - 2; j++) {
				int tmp1 = list.get(i).charAt(j) - 'a';
				int tmp2 = list.get(i).charAt(j + 1) - 'a';
				charS[j + 1][tmp1 * 2 + 1][tmp2 * 2 + 1] = 1;
			}

			int tmp1 = list.get(i).length() - 2;
			int tmp2 = list.get(i).length() - 1;
			tmp1 = list.get(i).charAt(tmp1) - 'a';
			tmp2 = list.get(i).charAt(tmp2) - 'a';

			if ((tmp1 * 2 + 1) >= 55 || (tmp2 * 2 + 2) >= 55) {
				System.out.println(list.get(i));
				System.out.println(tmp1 + "  " + tmp2);
			}
			charS[list.get(i).length() - 1][tmp1 * 2 + 1][tmp2 * 2 + 2] = 1;
		}
	}

	public boolean myRecognition(String word) {
		if (word.length() == 0) {
			return false;
		}
		int tmp = word.charAt(0) - 'a';
		if (word.length() == 1) {
			if (charS[0][0][tmp * 2 + 2] == 1)
				return true;
			return false;
		}
		if (charS[0][0][tmp * 2 + 1] == 1) {
			for (int i = 0; i < word.length() - 2; i++) {
				int tmp1 = word.charAt(i) - 'a';
				int tmp2 = word.charAt(i + 1) - 'a';
				if (charS[i + 1][tmp1 * 2 + 1][tmp2 * 2 + 1] == 0)
					return false;
			}

			int tmp1 = word.length() - 2;
			int tmp2 = word.length() - 1;
			tmp1 = word.charAt(tmp1) - 'a';
			tmp2 = word.charAt(tmp2) - 'a';

			if (charS[word.length() - 1][tmp1 * 2 + 1][tmp2 * 2 + 2] == 0)
				return false;
		} else {
			return false;
		}
		return true;
	}

	public void myTest(List<String> listW) {
		for (int i = 0; i < listW.size(); i++) {
			if (!myRecognition(listW.get(i))) {
				System.out.println("Wrong words: " + listW.get(i));
			}
		}
	}

	public void getListW(List<String> listW) {
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		String[] resS = line.split(" ");
		for (int i = 0; i < resS.length; i++) {
			resS[i] = resS[i].toLowerCase();
			resS[i] = pre_process(resS[i]);
			if (resS[i].length() > 0)
				listW.add(resS[i]);
		}
		sc.close();
	}

	public void WriteCharS() throws IOException{
	   PrintWriter pf=new PrintWriter(new BufferedWriter(new FileWriter("CharS.txt")));
	   for(int i=0;i<26;i++){
		   for(int j=0;j<53;j++){
			  // System.out.print(charS[i][j][0]);
			   pf.print(charS[i][j][0]);
			   for(int h=1;h<53;h++){
			//	   System.out.print(" "+charS[i][j][h]);
				   pf.print(" "+charS[i][j][h]);
			   }
			 //  System.out.println();
			   pf.println();
		   }
	   }
	   pf.close();
	}
	
	public void getCharS() throws IOException{
		BufferedReader bf=new BufferedReader(new FileReader("CharS.txt"));
		for(int i=0;i<26;i++){
			for(int j=0;j<53;j++){
				String line=bf.readLine();
				if(line==null)
				{
					System.out.println("get CharS error!");
					return;
				}
				String[] nums=line.split(" ");
				for(int h=0;h<53;h++){
					
					charS[i][j][h]=Integer.parseInt(nums[h]);
				}
			}
		}
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		WordsRecognition obj = new WordsRecognition();
		List<String> listW = new ArrayList<String>();

		Scanner sc = new Scanner(System.in);
		System.out.println("choose: 1. create FA again; 2. use the exist FA");
		int choose = sc.nextInt();
		sc.nextLine();
		if (choose == 1) {
			obj.myRead();
			obj.buildFA();
		}else{
			obj.getCharS();
		}
		String line = sc.nextLine();
		String[] resS = line.split(" ");
		for (int i = 0; i < resS.length; i++) {
			resS[i] = resS[i].toLowerCase();
			resS[i] = obj.pre_process(resS[i]);
			if (resS[i].length() > 0)
				listW.add(resS[i]);
		}
		
		
		obj.myTest(listW);
		obj.WriteCharS();
	}

}
