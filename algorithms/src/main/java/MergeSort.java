import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class MergeSort {

    private MergeSort() {        
    }

    // stably merge a[lo..mid] with a[mid+1..hi] using aux[lo..hi]
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi, Counts counts) {

        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k]; 
        }

        // merge back to a[]
        int i = lo, j = mid+1;
        int inversions = 0;
        
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)              a[k] = aux[j++];  // this copying is unneccessary
            else if (j > hi)               a[k] = aux[i++];
            else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
                inversions += mid - i + 1;
            }
            else                           a[k] = aux[i++];
        }
        counts.inversionsCount += inversions;
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static long sort(Comparable[] a) {
        int N = a.length;
        
        Counts counts = new Counts();
        Comparable[] aux = new Comparable[N];

        for (int n = 1; n < N; n = n+n) {
            for (int i = 0; i < N-n; i += n+n) {
                int lo = i;
                int m  = i+n-1;
                int hi = Math.min(i+n+n-1, N-1);
                merge(a, aux, lo, m, hi, counts);
            }
        }
        assert isSorted(a);
        return counts.inversionsCount;
    }

    private static class Counts  {
        private long inversionsCount = 0;
    }
    
  /***********************************************************************
    *  Helper sorting functions
    ***********************************************************************/
    
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }

   // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


   /***********************************************************************
    *  Check if array is sorted - useful for debugging
    ***********************************************************************/
    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    /**
     * Reads in a sequence of strings from standard input; bottom-up
     * mergesorts them; and prints them to standard output in ascending order. 
     */
    public static void main(String[] args) throws Exception {
        
        File file = new File("/home/edison/ws-algorithms/algorithms/stanford/IntegerArray.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        List<Integer> lines = new ArrayList<Integer>();
        
        while ((line = br.readLine()) != null) {
            lines.add(Integer.valueOf(line));
        }
        br.close();

        Comparable[] comp = new Comparable[lines.size()];
        long inversionsCount = MergeSort.sort(lines.toArray(comp));
        System.out.println("inversionsCount = " + inversionsCount);
    }
}
