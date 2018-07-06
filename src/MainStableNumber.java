import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainStableNumber {

	List<List<Integer>> list = new ArrayList<List<Integer>>();
	List<List<Integer>> list1 = new ArrayList<List<Integer>>();

	int N = 0;

	public void myRead() {
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		N = Integer.parseInt(line);
		for (int i = 0; i <= N; i++) {
			List<Integer> tmpList = new ArrayList<Integer>();
			list.add(tmpList);
		}
		List<Integer> tmpList1 = new ArrayList<Integer>();
		list1.add(tmpList1);
		for (int i = 1; i <= N; i++) {
			line = sc.nextLine();
			String[] nums = line.split(" ");
			int n = Integer.parseInt(nums[0]);
			List<Integer> tmpList = new ArrayList<Integer>();
			for (int j = 1; j < nums.length; j++) {
				int tmp = Integer.parseInt(nums[j]);
				list.get(tmp).add(i);
				tmpList.add(tmp);
			}
			list1.add(tmpList);
		}
	}

	public void mySolve() {
		int[] flag = new int[N + 1];
		int[] visited = new int[N + 1];

		int res = 0;
		if (list.get(0).size() == 1) {
			res = 1;
		}
		List<Integer> queue = new ArrayList<Integer>();
		queue.add(0);
		flag[0] = 1;
		while (queue.size() != 0) {
			int tmp = queue.get(0);
			queue.remove(0);
			for (int i = 0; i < list.get(tmp).size(); i++) {
				int tmp1 = list.get(tmp).get(i);
				int flag1 = 1;
				for (int h = 0; h < list1.get(tmp1).size(); h++) {
					int child = list1.get(tmp1).get(i);
					if (flag[child] == 0)
						flag1 = 0;

				}
				if (flag1 == 1) {
					queue.add(tmp1);
					flag[tmp1] = 1;
				}
			}

		}
	}

	public int mySolve1() {
		String[] value = new String[N + 1];
		
		int[] res=new int[N+1];
		int myRes=list.get(0).size();
		List<Integer> leaf = new ArrayList<Integer>();
		value[0]="";
		for (int i = 1; i <= N; i++) {
			if (list.get(i).size() == 0) {
				leaf.add(i);
			}
			value[i]=""+i;
		}
	
		while (leaf.size() != 0) {
			int tmp = leaf.get(0);
			int tmp1=0;
			for (int i = 0; i < list1.get(tmp).size(); i++) {
				tmp1=list1.get(tmp).get(i);
				String str = value[tmp1];
				if (tmp1==0) {
					str += value[tmp];
				}else{
					str=mergeStr(str,value[tmp]);
				}
				value[tmp1] = str;
				for(int j=0;j<list.get(tmp1).size();j++){
					if(list.get(tmp1).get(j)==tmp){
						list.get(tmp1).remove(j);
						if(list.get(tmp1).size()==0)
							leaf.add(tmp1);
						break;
					}
				}
			}
			leaf.remove(0);
		}
		
		for(int i=0;i<value[0].length();i++){
			int tmp=value[0].charAt(i)-'0';
			res[tmp]++;
		}
		
		for(int i=0;i<=N;i++){
			if(res[i]>1)
				myRes++;
		}
		return myRes;
	}
	
	public String mergeStr(String str1,String str2){
		String str=str1;
		for(int i=0;i< str2.length();i++){
			if(!str.contains(str2.charAt(i)+""))
				str+=str2.charAt(i);
		}
		return str;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MainStableNumber obj=new MainStableNumber();
		obj.myRead();
		int res=obj.mySolve1();
		System.out.println(res);
	}

}
