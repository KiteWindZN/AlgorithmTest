package GeneticAlgorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * 利用遗传算法实现TSP
 **/

public class GeneticTSP {
	public static float crossRate = 0.5f;
	public static float evidenceRate = 0.1f;
	public static int iterate = 1000;
	public static int geneticNum = 200;
	public static int cityNum = 150;
	public float[][] graph; //= new float[cityNum][cityNum];

	/*
	 * 初始化地图
	 **/
	public void initGraph() throws IOException {
		/*for (int i = 0; i < cityNum; i++) {
			for (int j = 0; j <= i; j++) {
				if (i == j)
					graph[i][j] = 0;
				else {
					float distance = (float) (Math.random() * 150);
					graph[i][j] = distance;
					graph[j][i] = distance;
				}
			}
		}*/
		
		BufferedReader bf=new BufferedReader(new FileReader("cities.txt"));
		String line=bf.readLine();
		cityNum=Integer.parseInt(line);
		graph=new float[cityNum][cityNum];
		int[] x=new int[cityNum];
		int[] y=new int[cityNum];
		for(int i=0;i<cityNum;i++){
			line=bf.readLine();
			String[] nums=line.split(" ");
			x[i]=Integer.parseInt(nums[1]);
			y[i]=Integer.parseInt(nums[2]);
		}
		
		for(int i=0;i<cityNum;i++){
			for(int j=0;j<=i;j++){
				if(i==j)
					graph[i][j]=0;
				else{
					float dis=(float) (Math.pow(x[i]-x[j], 2)+Math.pow(y[i]-y[j], 2));
					dis=(float) Math.sqrt(dis)/100.0f;
					graph[i][j]=dis;
					graph[j][i]=dis;
				}
			}
		}
	}

	/*
	 * 创建基因的过程
	 **/
	public List<List<Integer>> createGenes() {
		List<List<Integer>> genes = new ArrayList<List<Integer>>();
		for (int i = 0; i < geneticNum; i++) {
			List<Integer> list = new ArrayList<Integer>();
			List<Integer> gene = new ArrayList<Integer>();
			for (int j = 0; j < cityNum; j++) {
				int nextCity = (int) (Math.random() * cityNum);
				while (list.contains(nextCity)) {
					nextCity = (int) (Math.random() * cityNum);
				}
				gene.add(nextCity);
				list.add(nextCity);
			}
			gene.add(list.get(0));// 回到原点
			genes.add(gene);
		}
		return genes;
	}

	/*
	 * 计算路径的值
	 **/
	public float calculateCost(List<Integer> gene) {
		float cost = 0;
		for (int i = 0, len = gene.size(); i < len - 1; i++) {
			int start = gene.get(i);
			int end = gene.get(i + 1);
			cost += graph[start][end];
		}
		return cost;
	}

	/*
	 * 选择过程
	 **/
	public List<List<Integer>> chooseGenes(List<List<Integer>> genes, float[] costs) {
		List<List<Integer>> resGenes = new ArrayList<List<Integer>>();
		for (int i = 0, len = genes.size(); i < len; i++) {
			float minCost = Integer.MAX_VALUE;
			List<Integer> tempGene = new ArrayList();
			for (int j = 0; j < 10; j++) {
				int tempIndex = (int) (Math.random() * len);
				if (minCost > costs[tempIndex]) {
					minCost = costs[tempIndex];
					tempGene = genes.get(tempIndex);
				}
			}
			resGenes.add(tempGene);
		}
		return resGenes;
	}

	/*
	 * 交叉过程
	 **/
	public void crossGenes(List<List<Integer>> genes) {
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
			if (curRate < crossRate) {
				int crossPoint = (int) (Math.random() * gene1.size());// 交叉的时候避开起始点，很关键！！！！
				if (crossPoint == 0)
					crossPoint++;
				if (crossPoint == gene1.size())
					crossPoint--;
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

				/*
				 * // System.out.println(myIndex1); if(!isValid(tmpList1)){
				 * System.out.println("error!!"); printList(tmpList1);
				 * System.out.println("error!!"); System.out.println("error!!");
				 * } if(!isValid(tmpList2)){ System.out.println("error!!");
				 * printList(tmpList2); System.out.println("error!!");
				 * System.out.println("error!!"); }
				 */
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

	/*
	 * 变异过程
	 **/
	public void evidenceGenes(List<List<Integer>> genes) {
		for (int i = 0, len = genes.size(); i < len; i++) {
			List<Integer> gene = genes.get(i);
			double curRate = Math.random();
			if (curRate < evidenceRate) {
				int evidencePoint1 = (int) (Math.random() * gene.size());
				int evidencePoint2 = (int) (Math.random() * gene.size());
				while (evidencePoint1 == 0 || evidencePoint1 == gene.size() - 1)
					evidencePoint1 = (int) (Math.random() * gene.size());

				while (evidencePoint2 == 0 || evidencePoint2 == gene.size() - 1)
					evidencePoint2 = (int) (Math.random() * gene.size());

				int change1 = gene.get(evidencePoint1);
				int change2 = gene.get(evidencePoint2);

				gene.add(evidencePoint1, change2);
				gene.remove(evidencePoint1 + 1);
				gene.add(evidencePoint2, change1);
				gene.remove(evidencePoint2 + 1);
			}
			genes.add(i, gene);
			genes.remove(i + 1);
		}
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

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		GeneticTSP geneticTSP = new GeneticTSP();
		geneticTSP.initGraph();
		List<List<Integer>> genes = geneticTSP.createGenes();

		for (int i = 0; i < 500; i++) {
			float[] costs = new float[genes.size()];
			float minCost = Integer.MAX_VALUE;
			List<Integer> path = new ArrayList<Integer>();
			// System.out.println("第" + i + "次迭代的cost为：");
			for (int j = 0; j < costs.length; j++) {
				costs[j] = geneticTSP.calculateCost(genes.get(j));
				// System.out.print(costs[j] + " ");
				if (minCost > costs[j]) {
					minCost = costs[j];
					path = genes.get(j);
				}

				// if ((j + 1) % 10 == 0)
				// System.out.println();
			}

			System.out.println("第" + i + "次迭代的path为：");
			geneticTSP.printList(path);
			geneticTSP.isValid(path);
			System.out.println("第" + i + "次迭代的最小值为：" + minCost);

			genes = geneticTSP.chooseGenes(genes, costs);
			geneticTSP.crossGenes(genes);
			geneticTSP.evidenceGenes(genes);
		}
	}

}
