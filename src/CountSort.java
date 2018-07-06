import java.util.Scanner;

public class CountSort {
    public void mySort(int[] nums){
    	int max=nums[0];
    	for(int i=1;i<nums.length;i++){
    		if(max<nums[i])
    			max=nums[i];
    	}
    	int[] ballot=new int[max+1];
    	int[] bucket=new int[nums.length+1];
    	
    	for(int i=0;i<nums.length;i++){
    		ballot[nums[i]]++;
    	}
    	for(int i=1;i<ballot.length;i++){
    		ballot[i]+=ballot[i-1];
    	}
    	for(int i=0;i<nums.length;i++){
    		int j=ballot[nums[i]];
    		while(bucket[j]!=0)
    			j--;
    		bucket[j]=nums[i];
    	}
    	System.out.print(bucket[1]);
    	for(int i=2;i<bucket.length;i++){
    		System.out.print(" "+bucket[i]);
    	}
    }  
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		@SuppressWarnings("resource")
		Scanner sc=new Scanner(System.in);
		CountSort myObj=new CountSort();
		String line=sc.nextLine();
		line.substring(0, 0);
		String[] nums=line.split(" ");
		int[] numbers=new int[nums.length];
		for(int i=0;i<nums.length;i++){
			numbers[i]=Integer.parseInt(nums[i]);
		}
		myObj.mySort(numbers);
	}

}
