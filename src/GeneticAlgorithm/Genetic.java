package GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;

/*
 * author: zhangnan createed on 2018-05-30,finished on 2018-05-31
 * 利用遗传算法
 * 求解函数 f(x) = x + 10*sin(5*x) + 7*cos(4*x) 在区间[0,9]的最大值 
 */

public class Genetic {
	int[] limitX = new int[2];
	int xMax = 0;
	float diff = 0f;
	float e = 0.0001f;
	int bitsPower = 1;

	/*
	 * 适应度函数
	 */
	public float valueFunction(float numF) {
		float returnFloat = 0f;
		returnFloat = (float) (numF + 10 * Math.sin(5 * numF) + 7 * Math.cos(4 * numF));
		return returnFloat;
	}

	/*
	 * 根据区间计算出需要的参数值
	 */
	public void init() {
		limitX[0] = 0;
		limitX[1] = 9;
		xMax = limitX[0];
		if (xMax < limitX[1])
			xMax = limitX[1];
		int range = Math.abs(limitX[0] - limitX[1]);
		int splitNum = (int) (range / e);

		while (Math.pow(2, bitsPower) < splitNum) {
			bitsPower++;
		}
		e = (float) (range / (Math.pow(2, bitsPower)));
		diff = range - xMax;
	}

	/*
	 * 十进制转换为二进制
	 */
	public String transform2two(int num) {
		String str = "";
		while (num > 0) {
			int tmp = num % 2;
			num = num / 2;
			str = tmp + str;
		}
		return str;
	}

	/*
	 * 二进制转换为十进制
	 */
	public int transform2ten(String str) {
		int returnInt = 0;
		for (int i = 0, len = str.length(); i < len; i++) {
			returnInt = returnInt * 2 + str.charAt(i) - '0';
		}
		return returnInt;
	}

	/*
	 * 将基因解码为区间内的数值
	 */
	public float decode(int numF) {
		float decodeNum = numF * e - diff;
		return decodeNum;
	}

	/*
	 * 根据基因的长度，随机生成基因
	 */
	public String createGenetic(int bitsPower) {
		StringBuilder sbd = new StringBuilder();
		for (int i = 0; i < bitsPower; i++) {
			double tmpF = Math.random();
			char ch = tmpF > 0.5F ? '1' : '0';
			sbd.append(ch);
		}
		return sbd.toString();
	}

	public float[] calF(int[] nums) {
		float[] f = new float[nums.length];
		for (int i = 0, len = nums.length; i < len; i++) {
			float myDecode = decode(nums[i]);
			// System.out.println(myDecode);
			f[i] = valueFunction(myDecode);
		}

		return f;
	}

	/*
	 * 计算权重大小
	 */
	public float[] calWeight(float[] f) {

		float[] weight = new float[f.length];
		float sumFloat = 0f;
		for (int i = 0, len = f.length; i < len; i++) {
			sumFloat += f[i];
		}
		for (int i = 0, len = f.length; i < len; i++) {
			weight[i] = f[i] / sumFloat;
		}
		return weight;
	}

	/*
	 * 根据权重大小选择基因
	 */
	public int chooseNums(float[] weight) {
		int index = 0;
		float maxWeight = 0f;
		for (int i = 0; i < 10; i++) {
			int myRand = (int) (Math.random() * weight.length);
			if (weight[myRand] > maxWeight) {
				maxWeight = weight[myRand];
				index = myRand;
			}
		}
		return index;
	}

	/*
	 * 创建种子基因，nums为种子基因数组
	 */

	public String[] createGenetics(int totalGenetics) {
		String[] genetics = new String[totalGenetics];
		for (int i = 0; i < totalGenetics; i++) {
			genetics[i] = createGenetic(bitsPower);
		}
		return genetics;
	}

	public int[] genetics2nums(String[] genetics) {
		int[] nums = new int[genetics.length];
		for (int i = 0, len = genetics.length; i < len; i++) {
			nums[i] = transform2ten(genetics[i]);
		}
		return nums;
	}

	
	//下面开始交叉
	public List<String> geneticCross(String[] genetics){
		List<String> returnList=new ArrayList<String>();
		List<Integer> list=new ArrayList<Integer>();
		int strLen=genetics[0].length();
		for(int i=0,len=genetics.length;i<len/4;i++){
			int index=(int) (Math.random()*len);
			while(list.contains(index)){
				index=(int) (Math.random()*len);
			}
			list.add(index);
			String tmpStr1=genetics[index];
			index=(int) (Math.random()*len);
			while(list.contains(index)){
				index=(int) (Math.random()*len);
			}
			list.add(index);
			String tmpStr2=genetics[index];
			
			double evidenceRate=Math.random();
			int evidenceCount1=0;
			int evidenceCount2=1;
			//evidenceRate>0.98 tmpStr1变异1位，evidenceRate>=0.99 tmpStr1变异2位
			
			if(evidenceRate>0.98&&evidenceRate<0.99){
				evidenceCount1=1;
			}else if(evidenceRate>=0.99){
				evidenceCount1=2;
			}
			
			////0.01<evidenceRate<0.02 tmpStr2变异1位，0<evidenceRate<=0.01 tmpStr2变异2位
			if(evidenceRate>0.01&&evidenceRate<0.02){
				evidenceCount2=1;
			}else if(evidenceRate<=0.01){
				evidenceCount1=2;
			}
			tmpStr1=geneticEvidence(tmpStr1,evidenceCount1);
			tmpStr2=geneticEvidence(tmpStr2,evidenceCount2);
			String str1S=tmpStr1.substring(0, strLen-2);
			String str1E=tmpStr1.substring(strLen-2, strLen);
			String str2S=tmpStr2.substring(0, strLen-2);
			String str2E=tmpStr2.substring(strLen-2, strLen);
			tmpStr1=str1S+str2E;
			tmpStr2=str2S+str1E;
			returnList.add(tmpStr1);
			returnList.add(tmpStr2);
		}
		return returnList;
	}
	
	//下面开始变异
	public String geneticEvidence(String genetic,int evidenceCount){
		for(int i=0;i<evidenceCount;i++){
			int changeIndex=(int) (Math.random()*genetic.length());
			String str1=genetic.substring(0, changeIndex);
			String str2=genetic.substring(changeIndex+1, genetic.length());
			char ch=genetic.charAt(changeIndex)=='0'?'1':'0';
			genetic=str1+ch+str2;
		}
		return genetic;
	}
	
	
	public static void main(String[] args) {
		Genetic genetic = new Genetic();

		genetic.init();
		String[] strNums = genetic.createGenetics(1000);

		System.out.println("开始遗传算法的计算：");
		//选择过程
		for (int count = 0; count < 3; count++) {
			float Max=0;
			int[] nums = genetic.genetics2nums(strNums);
			float[] f = genetic.calF(nums);
			float[] weight = genetic.calWeight(f);
			/*for (int i = 0, len = weight.length; i < len; i++) {
				System.out.print(strNums[i] + "  ");
				if ((i + 1) % 100 == 0)
					System.out.println();
			}*/
			System.out.println();
			System.out.println("本轮计算结果如下：");
			for (int i = 0, len = weight.length; i < len; i++) {
				System.out.print(f[i] + "  ");
				if(Max<f[i])
					Max=f[i];
				if ((i + 1) % 100 == 0)
					System.out.println();
			}
			System.out.println("此轮遗传的最大值为： "+Max);
			
			for (int i = 0, len = nums.length; i < len; i++) {
				int index = genetic.chooseNums(weight);
				String tempStr = genetic.transform2two(nums[index]);
				strNums[i] = tempStr;
			}
			
		}
		//交叉变异过程
		for(int count=0;count<6;count++){
			float Max=0;
			int[] nums=genetic.genetics2nums(strNums);
			float[] f=genetic.calF(nums);
			float[] weight=genetic.calWeight(f);
			for (int i = 0, len = strNums.length/2; i < len; i++) {
				int index = genetic.chooseNums(weight);
				String tempStr = genetic.transform2two(nums[index]);
				strNums[i] = tempStr;
			}
			List<String> tmpList=genetic.geneticCross(strNums);
			for(int i=0,len=tmpList.size();i<len;i++){
			    strNums[i+len]=tmpList.get(i);
			}
			nums=genetic.genetics2nums(strNums);
			f=genetic.calF(nums);
			weight=genetic.calWeight(f);
			System.out.println();
			System.out.println("本轮计算结果如下：");
			for (int i = 0, len = weight.length; i < len; i++) {
				System.out.print(f[i] + "  ");
				if(Max<f[i])
					Max=f[i];
				if ((i + 1) % 100 == 0)
					System.out.println();
			}
			System.out.println("此轮遗传的最大值为： "+Max);
			for (int i = 0, len = nums.length; i < len; i++) {
				int index = genetic.chooseNums(weight);
				String tempStr = genetic.transform2two(nums[index]);
				strNums[i] = tempStr;
			}
			
			
		}
		System.out.println();
		System.out.println("作者给出的最优解：  "+genetic.valueFunction(7.8569f));
	}

}
