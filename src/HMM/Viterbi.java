package HMM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Viterbi {
	String[] states={"Sunny","Rainy"};
	String[] description={"walk","shop","clean"};
	String[] observe={"walk","shop","clean"};
	Map<String,Integer> map=new HashMap<String,Integer>();
	Map<Integer,String> mapW=new HashMap<Integer,String>();
	float[] start_probability={(float) 0.4,(float) 0.6};
	float[][] transition_probability={{(float) 0.6,(float) 0.4},{(float) 0.3,(float) 0.7}};
	float[][] emission_probability={{(float) 0.6,(float) 0.3,(float) 0.1},{(float) 0.1,(float) 0.4,(float) 0.5}};

	class Day{
		double probability=0;
		List<Integer> list=new ArrayList<Integer>();
	}
	
	public Viterbi(){
		init_map();
	}
	
	public void init_map(){
		for(int i=0;i<description.length;i++){
			map.put(description[i],i);
		}
		for(int i=0;i<states.length;i++){
			mapW.put(i,states[i]);
		}
	}
	
	
	public Day Solve(){
		int len=observe.length;
		Day[] days=new Day[len*2];
		for(int i=0;i<days.length;i++){
			days[i]=new Day();
		}
		for(int i=0;i<len;i++){
			int index2=map.get(observe[i]);
			if(i==0){
				days[i*2].probability=start_probability[0]*emission_probability[0][index2];
				days[i*2+1].probability=start_probability[1]*emission_probability[1][index2];
				days[i*2].list.add(0);
				days[i*2+1].list.add(1);
			}else{
				double pro1=days[i*2-1].probability*transition_probability[1][0]*emission_probability[0][index2];
				double pro2=days[i*2-2].probability*transition_probability[0][0]*emission_probability[0][index2];
				double pro3=days[i*2-1].probability*transition_probability[1][1]*emission_probability[1][index2];
				double pro4=days[i*2-2].probability*transition_probability[0][1]*emission_probability[1][index2];
				
				if(pro1<pro2){
					for(int j=0;j<days[i*2-2].list.size();j++){
						int tmp=days[i*2-2].list.get(j);
						days[i*2].list.add(tmp);
					}
					days[i*2].list.add(0);
					days[i*2].probability=pro2;
				}else{
					for(int j=0;j<days[i*2-1].list.size();j++){
						int tmp=days[i*2-1].list.get(j);
						days[i*2].list.add(tmp);
					}
					days[i*2].list.add(0);
					days[i*2].probability=pro1;
				}
				if(pro3<pro4){
					for(int j=0;j<days[i*2-2].list.size();j++){
						int tmp=days[i*2-2].list.get(j);
						days[i*2+1].list.add(tmp);
					}
					days[i*2+1].list.add(1);
					days[i*2+1].probability=pro4;
				}else{
					for(int j=0;j<days[i*2-1].list.size();j++){
						int tmp=days[i*2-1].list.get(j);
						days[i*2+1].list.add(tmp);
					}
					days[i*2+1].list.add(1);
					days[i*2+1].probability=pro3;
				}
			}
					
		}
		if(days[len*2-2].probability>days[len*2-1].probability){
			return days[len*2-2];
		}
		return days[len*2-1];
	}
	
	public void myPrintln(Day day){
		System.out.println("the probability is : "+day.probability);
		System.out.print(mapW.get(day.list.get(0)));
		for(int i=1;i<day.list.size();i++){
			int index=day.list.get(i);
			System.out.print(" "+mapW.get(index));
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Viterbi myViterbi=new Viterbi();
		Day lastDay=myViterbi.Solve();
		myViterbi.myPrintln(lastDay);
	}

}
