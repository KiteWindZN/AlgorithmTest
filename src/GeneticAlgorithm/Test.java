package GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public void crossGenes() {
		List<List<Integer>> genes=new ArrayList<List<Integer>>();
		List<Integer> list1=new ArrayList<Integer>();
		List<Integer> list2=new ArrayList<Integer>();
		list1.add(1);
		list1.add(2);
		list1.add(3);
		list1.add(4);
		list1.add(5);
		list1.add(6);
		list1.add(7);
		list1.add(1);
		list2.add(7);
		list2.add(6);
		list2.add(5);
		list2.add(2);
		list2.add(3);
		list2.add(1);
		list2.add(4);
		list2.add(7);
		genes.add(list1);
		genes.add(list2);
		
		for (int i = 0, len = genes.size(); i < len; i++) {

			int tempIndex1 = (int) (Math.random() * len);
			int tempIndex2 = (int) (Math.random() * len);
			while (tempIndex2 == tempIndex1) {
				tempIndex2 = (int) (Math.random() * len);
			}
			List<Integer> gene1 = new ArrayList<Integer>();
			List<Integer> gene2 = new ArrayList<Integer>();

			for (int j = 0; j < genes.get(tempIndex1).size(); j++) {
				gene1.add(genes.get(tempIndex1).get(j));
				gene2.add(genes.get(tempIndex2).get(j));
			}
			if (tempIndex1 > tempIndex2) {
				int tmp = tempIndex1;
				tempIndex1 = tempIndex2;
				tempIndex2 = tmp;
			}
			genes.remove(tempIndex1);
			genes.remove(tempIndex2 - 1);
			double curRate = Math.random();
			float crossRate=1;
			if (curRate < crossRate) {
				int crossPoint = (int) (Math.random() * gene1.size());
				crossPoint=5;

				List<Integer> tmpList1 = new ArrayList<Integer>();
				List<Integer> tmpList2 = new ArrayList<Integer>();

				for (int j = 0; j < crossPoint; j++) {
					tmpList1.add(gene1.get(j));
					tmpList2.add(gene2.get(j));
				}

				for (int j = crossPoint; j < gene1.size() - 1; j++) {
					tmpList1.add(gene2.get(j));
					tmpList2.add(gene1.get(j));
				}
				tmpList1.add(gene1.get(gene1.size() - 1));
				tmpList2.add(gene2.get(gene2.size() - 1));

				int myIndex1 = 1;
				int myIndex2 = 1;
				while (myIndex1 < (tmpList1.size() - 1) && myIndex2 < (tmpList2.size() - 1)) {
					int num1 = tmpList1.get(myIndex1);
					while (tmpList1.indexOf(num1) == tmpList1.lastIndexOf(num1) && myIndex1 < (tmpList1.size() - 1)) {
						num1 = tmpList1.get(myIndex1);
						myIndex1++;
						num1 = tmpList1.get(myIndex1);
					}
					int num2 = tmpList2.get(myIndex2);
					while (tmpList2.indexOf(num2) == tmpList2.lastIndexOf(num2) && myIndex2 < (tmpList2.size() - 1)) {
						myIndex2++;
						num2 = tmpList2.get(myIndex2);
					}
					if (myIndex1 < (tmpList1.size() - 1) && (myIndex2 < (tmpList2.size() - 1))) {
						int num3 = tmpList1.get(myIndex1);
						int num4 = tmpList2.get(myIndex2);

						tmpList1.set(myIndex1, num4);
						tmpList2.set(myIndex2, num3);
					}
					myIndex1++;
					myIndex2++;
				}

				// System.out.println(myIndex1);
				if (!isValid(tmpList1)) {
					System.out.println("error!!");
					printList(tmpList1);
					System.out.println("error!!");
					System.out.println("error!!");
				}
				if (!isValid(tmpList2)) {
					System.out.println("error!!");
					printList(tmpList2);
					System.out.println("error!!");
					System.out.println("error!!");
				}
				genes.add(tmpList1);
				genes.add(tmpList2);
				tmpList1 = null;
				tmpList2 = null;

			} else {
				genes.add(gene1);
				genes.add(gene2);
			}
		}
	}

	public boolean isValid(List<Integer> list) {
		int len = list.size();
		int flag = 0;
		for (int i = 1; i < len - 1; i++) {
			int num = list.get(i);
			if (list.indexOf(num) != list.lastIndexOf(num)) {
				flag++;
				System.out.println(
						"two internal nodes are the same , error: " + list.indexOf(num) + "  " + list.lastIndexOf(num));
			}
		}
		int num1 = list.get(0);
		int num2 = list.get(len - 1);
		if (num1 != num2) {
			flag++;
			System.out.println("start != end , error: " + list.get(0) + " " + list.get(list.size() - 1));

		}
		if (flag == 0)
			return true;
		return false;
	}

	public void printList(List<Integer> list) {
		System.out.print(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			System.out.print(" -> (" + i + ")" + list.get(i));
			// if ((i + 1) % 10 == 0)
			// System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Test myObj=new Test();
		myObj.crossGenes();
	}

}
