import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;


/**
 * Faster implementation.
 */
public class LargestTripleProducts {


    /**
     * Improved implementation.
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

        PriorityQueue<Integer> maxVals = new PriorityQueue<Integer>( (a,b) -> (a - b) );

        maxVals.add(arr[0]);
        maxVals.add(arr[1]);
        maxVals.add(arr[2]);

        // **** traverse array O(n) ****
        for (int i = 3; i < arr.length; i++) {

            // ???? ????
            System.out.println("findMaxProduct <<< maxVals: " + maxVals.toString());

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
     * Largest values in descending order.
     * 
     * Utility method.
     */
    static private int[] largestValues(int[] largest, int val) {

        // ???? ????
        // System.out.println("<<< val: " + val + " largest: " + Arrays.toString(largest));

        // **** update the top three values ****
        if (val > largest[0]) {
            largest[2] = largest[1];
            largest[1] = largest[0];
            largest[0] = val;
        }

        // **** update the top two values ****
        else if (val > largest[1]) {
            largest[2] = largest[1];
            largest[1] = val;
        }

        // **** update the top value ****
        else if (val > largest[2]) {
            largest[2] = val;
        }

        // ???? ????
        // System.out.println("<<< largest: " + Arrays.toString(largest));

        // **** return the top three values ****
        return largest;
    }


    /**
     * Edited previous implementation.
     */
    static int[] findMaxProduct1(int[] arr) {

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
        
        // **** compute the largest three values and return them in the array ****
        int[] largest = new int[] {Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
        for (int i = 0; i < 3; i++)
            largest = largestValues(largest, arr[i]);

        // **** traverse array multiplying the current 3 largest entries O(n) ****
        for (int i = 3; i < arr.length; i++) {

            // **** update the top three largest values ****
            largest = largestValues(largest, arr[i]);

            // **** generate the product of the three lasget values ****
            prods[i] = largest[0] * largest[1] * largest[2];
        }

        // **** return array of max products ****
        return prods;
    }


    /**
     * Test scaffolding
     * 
     * !!!! NOT PART OF SOLUTION !!!!
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

        // **** find the max products and display result (take 1) ****
        System.out.println("main <<<  output: " + Arrays.toString(findMaxProduct(arr)));

        // **** find the max products and display result (take 2) ****
        System.out.println("main <<< output1: " + Arrays.toString(findMaxProduct1(arr)));
    }
}