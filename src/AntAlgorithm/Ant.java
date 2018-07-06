package AntAlgorithm;

import java.util.Random;

public class Ant {

	int cities;
	int[] unvisited;
	int[] tour;
	int tourLength;
	
	/*
	 * 初始化一些变量，并且选出放置蚂蚁的城市
	 * */
	public void randomSelectCity(int cityNum){
		this.cities=cityNum;
		this.tour=new int[this.cities+1];
		this.unvisited=new int[this.cities];
		
		for(int i=0;i<cityNum;i++){
			this.unvisited[i]=1;
			this.tour[i]=-1;
		}
		this.tour[cityNum]=-1;
		long r1=System.currentTimeMillis();
		Random rand=new Random(r1);
		
		int firstCity=(int) (Math.random()*cityNum);
		//System.out.println("first city: "+firstCity);
		
		this.tour[0]=firstCity;
		this.unvisited[firstCity]=0;
	}
	
	/*
	 * 确定蚂蚁下一步（第index步）的移动方向
	 * tao为代表信息素的数组
	 * dis为城市之间的距离
	 * */
	public void selectNextCity(int index,float[][] tao,float[][] dis){
		float sumTao=0;
		int currentCity=tour[index-1];
		float[] p=new float[this.cities];
		
		for(int i=0;i<this.cities;i++){
			if(unvisited[i]==1){
				sumTao+=tao[currentCity][i]/dis[i][currentCity];
			}
		}
		for(int i=0;i<this.cities;i++){
			if(unvisited[i]==0){
				p[i]=0;
			}else if(unvisited[i]==1){
				p[i]=(float) (tao[currentCity][i]/dis[i][currentCity]/sumTao);
			}
		}
		
		//轮盘转选出下一个城市
		
		float rate=(float) Math.random();
		float rateSum=0;
		int selectCity=-1;
		
		for(int i=0;i<cities;i++){
			rateSum+=p[i];
			if(rateSum>rate){
				selectCity=i;
				break;
			}
			
		}
		if(selectCity==-1){
			//System.out.println("select next city error");
			int tmpI=0;
			float tmpRate=0;
			
			for(int i=0;i<10;i++){
				tmpI=(int) (Math.random()*unvisited.length);
			    while(unvisited[tmpI]==0)
				    tmpI=(int) (Math.random()*unvisited.length);
			    if(tmpRate<p[tmpI]){
			    	selectCity=tmpI;
			    	tmpRate=p[tmpI];
			    }
			}
		}
		//System.out.println("next city: "+selectCity);
		tour[index]=selectCity;
		unvisited[selectCity]=0;
	}
	
	/*
	 * 计算路径的长度
	 * */
	public void calculateTourLength(float[][] dis){
		this.tourLength=0;
		tour[cities]=tour[0];
		for(int i=0;i<tour.length-1;i++){
			this.tourLength+=dis[tour[i]][tour[i+1]];
		}
	}
}
