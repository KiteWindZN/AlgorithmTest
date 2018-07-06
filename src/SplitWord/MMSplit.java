package SplitWord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MMSplit {

	Map<String, String> map = new HashMap<String, String>();

	List<String> list1 = new ArrayList<String>();
	List<String> list2 = new ArrayList<String>();
	String line = "";
	int Forward_Single = 0;
	int Backward_Single = 0;

	public String getLine() {
		return line;
	}

	public void setLine() {
		this.line = myReader();
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(String fileName) throws IOException {
		BuiltDic(fileName);
	}

	public List<String> getList1() {
		return list1;
	}

	public void setList1(List<String> list1) {
		this.list1 = list1;
	}

	public List<String> getList2() {
		return list2;
	}

	public void setList2(List<String> list2) {
		this.list2 = list2;
	}

	public int getForward_Single() {
		return Forward_Single;
	}

	public int getBackward_Single() {
		return Backward_Single;
	}

	public void BuiltDic(String fileName) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(fileName));
		String line = bf.readLine();
		while (line != null) {
			String[] strS = line.split(" ");
			for (int i = 0; i < strS.length; i++) {
				map.put(strS[i], "TRUE");
			}
			line = bf.readLine();
		}
		bf.close();

	}

	public String myReader() {
		Scanner sc = new Scanner(System.in);
		line = sc.nextLine();
		sc.close();
		return line;
	}

	public void mySplitForward(String line) {

		while (line.length() > 0) {
			int flag = 0;
			int tmpIndex = 0;
			if (line.length() >= 6)
				tmpIndex = 6;
			else
				tmpIndex = line.length();
			while (flag == 0) {
				String tmpStr = line.substring(0, tmpIndex);
				if (map.containsKey(tmpStr) || tmpIndex == 1) {
					list1.add(tmpStr);
					if (tmpStr.length() == 1)
						Forward_Single++;
					line = line.substring(tmpIndex, line.length());
					flag = 1;
				}
				tmpIndex--;
			}
		}

	}

	public void mySplitBackward(String line) {

		while (line.length() > 0) {
			int flag = 0;
			int tmpIndex = 0;
			if (line.length() >= 6) {
				tmpIndex = line.length() - 6;
			} else
				tmpIndex = 0;
			while (flag == 0) {
				String tmpStr = line.substring(tmpIndex, line.length());

				if (map.containsKey(tmpStr) || tmpIndex == line.length() - 1) {
					if (tmpStr.length() == 1) {
						Backward_Single++;
					}
					// list2.add(tmpStr);
					list2.add(0, tmpStr);
					line = line.substring(0, tmpIndex);
					flag = 1;
				}
				tmpIndex++;

			}
		}
	}

	public boolean isListEqual() {
		if (list1.size() != list2.size())
			return false;
		int len = list1.size();
		for (int i = 0; i < len; i++) {
			if (!list1.get(i).equals(list2.get(i))) {
				return false;
			}
		}
		return true;
	}

    public List<String> ChooseRes(){
    	if(list1.size()<list2.size())
    		return list1;
    	else if(list1.size()>list2.size())
    		return list2;
    	if(isListEqual()||Forward_Single<Backward_Single){
    		return list1;
    	}
    	return list2;
    }
	public void myPrintln(List<String> list) {
		if(list==null){
			System.out.println("error, list.size() is 0!");
			return;
		}
		System.out.print(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			System.out.print(" " + list.get(i));
		}
		System.out.println();
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		MMSplit mmSplit = new MMSplit();
		String line = mmSplit.myReader();
		mmSplit.BuiltDic("Dic.txt");
		mmSplit.mySplitForward(line);
		mmSplit.mySplitBackward(line);
		mmSplit.myPrintln(mmSplit.list1);
		mmSplit.myPrintln(mmSplit.list2);
		List<String> list=mmSplit.ChooseRes();
		mmSplit.myPrintln(list);

	}

}
