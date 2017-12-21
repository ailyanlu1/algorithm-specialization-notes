import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by Tankc on 2017/12/14.
 */
public class CountInversions {

    public static long mergeSort(int[] array, int array_size){
        int[] temp=new int[array_size];
        return _mergeSort(array,temp,0,array_size-1);
    }

    public static long _mergeSort(int[] array,int[] temp,int left,int right){
        int mid;
        //一定不能设置为int类型，因为统计出来的数量超过Integer.MAX了，所以需要使用long型
        long inversion_count=0;
        if(right>left){
            mid=(right+left)/2;
            inversion_count=_mergeSort(array,temp,left,mid);
            inversion_count+=_mergeSort(array,temp,mid+1,right);
            inversion_count+=merge(array,temp,left,mid+1,right);
        }
        return inversion_count;
    }

    public static long merge(int[] array,int[] temp,int left,int mid,int right){
        int i,j,k;
        long inversion_count=0;
        i=left;
        j=mid;
        k=left;
        while((i<=mid-1)&&(j<=right)){
            if(array[i]<=array[j]){
                temp[k++]=array[i++];
            }else{
                temp[k++]=array[j++];
                inversion_count+=(mid-i);
            }
        }
        while(i<=mid-1){
            temp[k++]=array[i++];
        }
        while(j<=right){
            temp[k++]=array[j++];
        }
        for(i=left;i<=right;i++){
            array[i]=temp[i];
        }
        return inversion_count;
    }

    public static void main(String[] args){
        try {
            BufferedReader br=new BufferedReader(new FileReader("Data/IntegerArray.txt"));
            String line="";
            int[] array=new int[100000];
            int i=0;
            while((line=br.readLine())!=null){
                array[i++]=Integer.parseInt(line);
            }
            System.out.println(mergeSort(array,array.length));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
