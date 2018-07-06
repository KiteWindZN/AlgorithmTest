package AntAlgorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ACO {

	int cityNum;
	float[][] tao;
	float[][] dis;
	Ant[] ant;
	int antNum;
	float[] bestTour;
	float bestLength;
	public void init(String fileName,int antNum) throws IOException{
		this.antNum=antNum;
		ant=new Ant[antNum];
		
		BufferedReader bf=new BufferedReader(new FileReader(fileName));
		String line=bf.readLine();
		cityNum=Integer.parseInt(line);
		dis=new float[cityNum][cityNum];
		tao=new float[cityNum][cityNum];
		int[] x=new int[cityNum];
		int[] y=new int[cityNum];
		for(int i=0;i<cityNum;i++){
			line=bf.readLine();
			String[] nums=line.split(" ");
			x[i]=Integer.parseInt(nums[1]);
			y[i]=Integer.parseInt(nums[2]);
		}
		
		//calculate dis
		for(int i=0;i<cityNum;i++){
			for(int j=0;j<=i;j++){
				if(j==i)
					dis[i][j]=0f;
				else{
					double tmpDis=Math.pow((x[i]-x[j]),2)+Math.pow(y[i]-y[j], 2);
					tmpDis=Math.sqrt(tmpDis)/100;
					dis[i][j]=(float) tmpDis;
					dis[j][i]=(float) tmpDis;
				}
			}
		}
		
		// 初始化信息素
		for(int i=0;i<cityNum;i++){
			for(int j=0;j<cityNum;j++){
				tao[i][j]=0.5f;
			}
		}
		
		bestTour=new float[cityNum+1];
		bestLength=Integer.MAX_VALUE;
		//放置蚂蚁
		for(int i=0;i<antNum;i++){
			ant[i]=new Ant();
			ant[i].randomSelectCity(cityNum);
		}
	}
	
	public void run(int maxGen){
		for(int i=0;i<maxGen;i++){
			System.out.println("di "+i+" dai kai shi");
			for(int j=0;j<antNum;j++){
				for(int h=1;h<cityNum;h++){
					ant[j].selectNextCity(h, tao, dis);
				}
				ant[j].calculateTourLength(dis);
				if(bestLength>ant[j].tourLength){
					bestLength=ant[j].tourLength;
					System.out.println("第 "+i+"代发现的新的更好的解为： "+bestLength);
					
				}
			}
			updateTao();
			//放置蚂蚁
			for(int j=0;j<antNum;j++){
				ant[j].randomSelectCity(cityNum);
			}
		}
	}
	
	public void updateTao(){
		//信息素挥发
		for(int i=0;i<cityNum;i++){
			for(int j=0;j<cityNum;j++){
				tao[i][j]*=0.5;
			}
		}
		//信息素增加
		for(int i=0;i<antNum;i++){
			for(int j=0;j<cityNum;j++){
				float tmpTao=1000.0f/ant[i].tourLength;
				//System.out.println(ant[i].tour[j]+" "+ant[i].tour[j+1]);
				tao[ant[i].tour[j]][ant[i].tour[j+1]]+=tmpTao;
			}
		}
	}

	public void getBestLength(){
		System.out.println("the best length is : "+bestLength);
	}
}
