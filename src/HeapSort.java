import java.util.Scanner;

public class HeapSort {

	public void mySort(int[] nums){
		int len=nums.length;
		for(int i=(len-1)/2;i>=0;i--){
			adjustHeap(nums,i,len);
		}
		for(int i=len-1;i>0;i--){
			mySwap(nums,0,i);
			len--;
			adjustHeap(nums,0,len);
		}
	}
	public void adjustHeap(int[] nums,int begin,int len){
		int tmp=nums[begin];
		for(int i=begin*2+1;i<len;i=i*2+1){
			
			if((i+1<len)&&nums[i]<nums[i+1]){
				i=i+1;
			}
			if(tmp<nums[i]){
				nums[begin]=nums[i];
				begin=i;
			}else break;
			
		}
		nums[begin]=tmp;
	}
	public void mySwap(int[] nums,int i,int j){
		int tmp=nums[i];
		nums[i]=nums[j];
		nums[j]=tmp;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc=new Scanner(System.in);
		HeapSort myObj=new HeapSort();
		
		String line=sc.nextLine();
		String[] nums=line.split(" ");
		int[] numbers=new int[nums.length];
		for(int i=0;i<nums.length;i++){
			numbers[i]=Integer.parseInt(nums[i]);
		}
		myObj.mySort(numbers);
		System.out.print(numbers[0]);
		for(int i=1;i<numbers.length;i++){
			System.out.print(" "+numbers[i]);
		}
		System.out.println();
	}
}
