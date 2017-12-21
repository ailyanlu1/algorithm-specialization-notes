import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Tankc on 2017/12/21.
 */
public class QuickSort {
    public static int compCount=0;
    public static int ChoosePivot(ArrayList<Integer> array, int left, int right,int choose){
        int pivot;
        int pivot_index;
        int tmp;
        switch (choose){
            case 0:break;
            case 1:tmp=array.get(left);array.set(left,array.get(right));array.set(right,tmp);break;
            case 2:int mid =array.get((left+right)/2);int mid_index=(left+right)/2;
            if(array.get(left)>mid){
                if(mid>array.get(right)){
                    tmp=array.get(left);
                    array.set(left,mid);
                    array.set(mid_index,tmp);
                }else if(array.get(left)>array.get(right)){
                    tmp=array.get(left);
                    array.set(left,array.get(right));
                    array.set(right,tmp);
                }
            }else{
                if(array.get(left)<array.get(right)){
                    if(mid>array.get(right)){
                        tmp=array.get(left);
                        array.set(left,array.get(right));
                        array.set(right,tmp);
                    }else{
                        tmp=array.get(left);
                        array.set(left,mid);
                        array.set(mid_index,tmp);
                    }
                }
            }
            break;
        }
        pivot=array.get(left);
        pivot_index=left;
        int i=left+1;
        for(int j=left+1;j<=right;j++){
            if(array.get(j)<pivot){
                tmp=array.get(i);
                array.set(i,array.get(j));
                array.set(j,tmp);
                i++;
            }
        }
        array.set(pivot_index,array.get(i-1));
        array.set(i-1,pivot);
        return i-1;
    }
    public static void quicksort(ArrayList<Integer> array,int left, int right,int choose){
        if(left>=right)
            return;
        compCount+=(right-left);
        int pivot_index=ChoosePivot(array,left,right,choose);
        quicksort(array,left,pivot_index-1,choose);
        quicksort(array,pivot_index+1,right,choose);
    }
    public static void main(String[] args){
        BufferedReader br;
        String line;
        ArrayList<Integer> list=new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader("Data/QuickSort.txt"));
            try {
                while((line=br.readLine())!=null){
                    list.add(Integer.parseInt(line));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        quicksort(list,0,list.size()-1,2);
        System.out.println(QuickSort.compCount);
    }
}
