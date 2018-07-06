package GeneticAlgorithm;

/*
 * 遗传算法模拟机器人Roby收集易拉罐，
 * 
 * Roby动作：   0 向北移动；1 向东移动；2 向南移动；3 向西移动；4 随机移动；5 不动；6 收集罐子
 * 地图状态：    空 --> 0 有罐子 --> 1 墙壁 --> 2
 * 得分规则：    
 *         1. 如果当前格子中有罐子而且清理了，加 10 分。 
 *         2. 如果当前格子中没有罐子却执行清理的动作，扣 1 分。
 *         3. 如果撞到了墙，扣 5 分，并且弹回原来的格子。
 * 
 */
public class RobyFindCan {

	public static int mapWidth = 50;
	public static int mapHeight = 50;
	public static float canRate = 0.4f;
	int[][] map = new int[50][50];

	public void initMap() {
		// 先假设map上所有位置都为空
		for (int i = 0; i < mapHeight; i++) {
			for (int j = 0; j < mapWidth; j++)
				map[i][j] = 0;
		}
		// 设置四周的墙壁
		for (int i = 0; i < mapHeight; i++) {
			map[i][0] = 2;
			map[i][mapWidth - 1] = 2;
		}

		// 按照一定概率放置易拉罐
		for (int i = 1; i < mapHeight - 1; i++) {
			for (int j = 1; j < mapWidth - 1; j++) {
				double tmpRate = Math.random();
				if (tmpRate < canRate)
					map[i][j] = 1;
			}
		}
	}

	/*
	 * 监测东南西北四个方向上的移动是否合法
	 */
	public boolean meetWall(int[] p, int move) {
		boolean res = true;
		switch (move) {
		case 1:// 北
			if (p[0] - 1 == 0)
				return true;
			else {
				p[0] -= 1;
				res = false;
			}
			break;
		case 2:// 东
			if (p[1] + 1 == mapWidth - 1)
				return true;
			else {
				p[1] += 1;
				res = false;
			}
			break;
		case 3:// 南
			if (p[0] + 1 == mapHeight - 1)
				return true;
			else {
				p[0] += 1;
				res = false;
			}
			break;
		case 4:// 西
			if (p[1] - 1 == 0)
				return true;
			else {
				p[1] -= 1;
				res = false;
			}
			break;
		default:
			System.out.println("无效移动");
			break;
		}

		return res;
	}

	/*
	 * 生成200个原始基因组
	 */
	public String[] createGenetics() {
		String[] resGenes = new String[200];
		for (int i = 0; i < 200; i++) {
			StringBuilder sbd = new StringBuilder();
			for (int j = 0; j < 243; j++) {
				int tmp = (int) (Math.random() * 7);
				sbd.append(tmp);
			}
			resGenes[i] = sbd.toString();
		}
		return resGenes;
	}

	/*
	 * 获得一个位置周围的情况
	 */
	public String getLocalString(int x, int y) {
		StringBuilder sbd = new StringBuilder();
		// 顺序：北东南西中
		sbd.append(map[x - 1][y]);
		sbd.append(map[x][y + 1]);
		sbd.append(map[x + 1][y]);
		sbd.append(map[x][y - 1]);
		sbd.append(map[x][y]);
		return sbd.toString();
	}

	/*
	 * 将3进制字符串转化为10进制int
	 */
	public int str2Int(String str) {
		int res = 0;
		for (int i = 0; i < str.length(); i++) {
			res = res * 3 + (str.charAt(i) - '0');
		}
		return res;
	}

	public float Solve(String genetic) {
		int sumScore = 0;
		int[] p = new int[2];

		//对于任何一个基因，在100个地图上走200步
		for (int i = 0; i < 100; i++) {
			initMap();
			p[0] = 1;
			p[1] = 1;
			int oneTimeScore = 0;
			for (int j = 0; j < 200; j++) {//走200步
				String str = getLocalString(p[0], p[1]);
				// System.out.println(str);
				int nums = str2Int(str);
				char step = genetic.charAt(nums);
				switch (step) {
				case '0':// 北
					if (meetWall(p, 1))
						oneTimeScore -= 5;
					break;
				case '1':// 东
					if (meetWall(p, 2))
						oneTimeScore -= 5;
					break;
				case '2':// 南
					if (meetWall(p, 3))
						oneTimeScore -= 5;
					break;
				case '3':// 西
					if (meetWall(p, 4))
						oneTimeScore -= 5;
					break;
				case '4':// 随机
					int tempStep = (int) (Math.random() * 4 + 1);
					if (meetWall(p, tempStep))
						oneTimeScore -= 5;
					break;
				case '5':// 不动
					break;
				case '6':// 捡易拉罐
					if (map[p[0]][p[1]] == 1){
						oneTimeScore += 10;
						map[p[0]][p[1]]=0;
					}
					else
						oneTimeScore -= 1;
					break;
				default:
					System.out.println("步骤出错");
					break;
				}
			}
			sumScore += oneTimeScore;
		}
		return (float) (sumScore / 100.0 + 1000);
	}

	/*
	 * 计算权重数组
	 */
	public float[] getWeight(float[] scores) {
		float sumScore = 0f;
		float[] weight = new float[scores.length];
		for (int i = 0, len = scores.length; i < len; i++) {
			sumScore += scores[i];
		}
		for (int i = 0, len = scores.length; i < len; i++) {
			weight[i] = scores[i] / sumScore;
		}
		return weight;
	}

	/*
	 * 选择过程
	 */
	public String[] chooseGenes(float[] weight, String[] genetics) {
		String[] resGenetics = new String[weight.length];
		for (int i = 0, len = weight.length; i < len; i++) {
			float maxRate = 0;
			String str = "";
			for (int j = 0; j < 10; j++) {
				int tempIndex = (int) (Math.random() * len);
				if (maxRate < weight[tempIndex]) {
					maxRate = weight[tempIndex];
					str = genetics[tempIndex];
				}
			}
			resGenetics[i] = str;
		}
		return resGenetics;
	}

	/*
	 * 交叉过程
	 */
	public void crossGenes(String[] genetics) {
		float crossRate = 0.5f;
		
		for (int i = 0, len = genetics.length ; i < len/2; i++) {
			int tmpIndex1 = (int) (Math.random() * len);
			int tmpIndex2 = (int) (Math.random() * len);
			
			String genetic1 = genetics[tmpIndex1];
			String genetic2 = genetics[tmpIndex2];

			double curRate = Math.random();
			if (curRate > crossRate) {// 大于交叉概率时，两个基因交叉
				int crossPoint = (int) (Math.random() * len);// 随机生成交叉点
				String gene1S = genetic1.substring(0, crossPoint);
				String gene1E = genetic1.substring(crossPoint, len);
				String gene2S = genetic2.substring(0, crossPoint);
				String gene2E = genetic2.substring(crossPoint, len);
				genetic1 = gene1S + gene2E;
				genetic2 = gene2S + gene1E;
			}
			genetics[tmpIndex1] = genetic1;
			genetics[tmpIndex2] = genetic2;
		}
		
	}

	/*
	 * 变异过程
	 */
	public void evidenceGenes(String[] genetics) {
		float evidenceRate = 0.1f;
		for (int i = 0, len = genetics.length; i < len; i++) {
			String gene = genetics[i];
			double curRate = Math.random();
			if (curRate < evidenceRate) {// 小于变异概率，基因发生变异
				int tempIndex = (int) (Math.random() * gene.length());
				int theOne = (int) (Math.random() * 7);
				String geneS = gene.substring(0, tempIndex);
				String geneE = gene.substring(tempIndex + 1, gene.length());
				gene = geneS + theOne + geneE;
			}
			genetics[i] = gene;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RobyFindCan robyFindCan = new RobyFindCan();
		String[] genetics = robyFindCan.createGenetics();
		float[] scores = new float[200];
		System.out.println("开始遗传算法的过程：");
		for (int count = 0; count < 500; count++) {
			float maxScore=0;
			//System.out.println("第"+count+"轮的结果为：");
			for (int i = 0, len = genetics.length; i < len; i++) {
				scores[i] = robyFindCan.Solve(genetics[i]);
				//System.out.print(scores[i] + "  ");
				if(maxScore<scores[i])
					maxScore=scores[i];
				//if ((i + 1) % 10 == 0)
				//	System.out.println();
			}
			System.out.println("第"+count+"轮的最大值为："+maxScore);
			//System.out.println();
			float[] weight = new float[genetics.length];
			weight = robyFindCan.getWeight(scores);
			genetics = robyFindCan.chooseGenes(weight, genetics);
			robyFindCan.crossGenes(genetics);
			robyFindCan.evidenceGenes(genetics);

		}
	}

}
