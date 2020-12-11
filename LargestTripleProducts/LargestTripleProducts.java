import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;


/**
 * 
 */
public class LargestTripleProducts {


    /**
     * 
     */
    static int[] findMaxProduct(int[] arr) {

        // **** sanity check(s) ****
        if (arr.length == 1)
            return new int[] { -1 };

        if (arr.length == 2)
            return new int[] { -1, -1 };

        // **** initialization ****
        int[] prods = new int[arr.length];
        prods[0]    = prods[1] = -1;
        int prod    = arr[0] * arr[1] * arr[2];
        prods[2]    = prod;

        // **** priority heap (implemented with a priority queue) ****
        PriorityQueue<Integer> maxVals = new PriorityQueue<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer x, Integer y) {
                return Integer.compare(x, y);
            }
        });
        maxVals.add(arr[0]);
        maxVals.add(arr[1]);
        maxVals.add(arr[2]);

        // **** traverse array O(n) ****
        for (int i = 3; i < arr.length; i++) {

            // **** update max values (if needed) ****
            if (arr[i] > maxVals.peek()) {

                // **** remove from head of the queue (smallest value) ****
                maxVals.remove();

                // **** add new highest value ****
                maxVals.add(arr[i]);

                // **** compute the max product ****
                Integer[] tmp = (Integer[]) maxVals.toArray(new Integer[0]);
                prod = tmp[0] * tmp[1] * tmp[2];
            }

            // **** set the max product in the array ****
            prods[i] = prod;
        }

        // **** return array of max products ****
        return prods;
    }


    /**
     * Test scaffolding
     * 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        // **** open buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // **** read and split strings with integers ****
        String[] strs = br.readLine().trim().split(",");

        // **** close buffered reader ****
        br.close();
        
        // **** create integer array with string values ****
        int[] arr = Arrays.stream(strs).mapToInt(Integer::parseInt).toArray();

        // ???? ????
        System.out.println("main <<< arr: " + Arrays.toString(arr));

        // **** find the max products and display result ****
        System.out.println("main <<< output: " + Arrays.toString(findMaxProduct(arr)));
    }
}