import static org.junit.Assert.*;

import org.junit.Test;


public class MergeSortTest {

    @Test
    public void testInversionsCount() {
        
        String[] alpha = new String[] {"A", "B", "C", "D", "E"};
        long count = MergeSort.sort(alpha);
        
        assertEquals(0, count);
        
        alpha = new String[] {"2", "4", "1", "3", "5"};
        count = MergeSort.sort(alpha);
        
        assertEquals(3, count);

    }

}
