import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;


/**
 * Faster implementation.
 * Using sort on each change in largest3 array.
 */
public class LargestTripleProducts {

    


    /**
     * Uses a priority queue.
     */
    static int[] findMaxProduct0(int[] arr) {

        // **** sanity check(s) ****
        if (arr == null) return null;
        if (arr.length == 0) return new int[0];
        if (arr.length == 1) return new int[] {-1};
        if (arr.length == 2) return new int[] {-1, -1};

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
     * Edited previous implementation.
     * Uses array of thee largest values.
     */
    static int[] findMaxProduct1(int[] arr) {

        // **** sanity check(s) ****
        if (arr == null) return null;
        if (arr.length == 0) return new int[0];
        if (arr.length == 1) return new int[] {-1};
        if (arr.length == 2) return new int[] {-1, -1};

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

            // **** generate the product of the three largest values ****
            prods[i] = largest[0] * largest[1] * largest[2];
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

        // **** return the top three values ****
        return largest;
    }


    /**
     * Single sort of three elements.
     */
    static int[] findMaxProduct2(int[] arr) {

        // **** sanity check(s) ****
        if (arr == null) return null;
        if (arr.length == 0) return new int[] {};
        if (arr.length == 1) return new int[] { -1 };
        if (arr.length == 2) return new int[] { -1, -1 };

        // **** initialization ****
        int[] prods = new int[arr.length];
        prods[0]    = prods[1] = -1;
        int prod    = arr[0] * arr[1] * arr[2];
        prods[2]    = prod;

        int[] tops  = new int[]{ arr[0], arr[1], arr[2]};

        // **** sort tops array - O(3 log(3)) ****
        Arrays.sort(tops);

        // **** process array - O(n) ****
        for (var i = 3; i < arr.length; i++) {

            // **** skip this arr entry ****
            for ( ; i < arr.length && arr[i] < tops[0]; i++)
                prods[i] = prods[i - 1];

            // **** check if done processing arr ****
            if (i >= arr.length) break;

            // **** update all three entries ****
            if (arr[i] > tops[2]) {
                tops[0] = tops[1];
                tops[1] = tops[2];
                tops[2] = arr[i];
            }

            // **** update two entries ****
            else if (arr[i] > tops[1]) {
                tops[0] = tops[1];
                tops[1] = arr[i];
            }

            // **** update one entry ****
            else if (arr[i] > tops[0]) {
                tops[0] = arr[i];
            }

            // **** compute and save product ****
            prods[i] = tops[0] * tops[1] * tops[2];
        }

        // **** return array of max products ****
        return prods;
    }


    /**
     * Contributed by Brent Boyer.
     * 
     * Multiple three element sorts.
     */
    static int[] findMaxProduct3(int[] arr) {

        // **** sanity check(s) ****
        if (arr == null) return null;
        if (arr.length == 0) return new int[0];
        if (arr.length == 1) return new int[] {-1};
        if (arr.length == 2) return new int[] {-1, -1};
    
        // **** ****
        int[] result    = new int[arr.length];
        result[0]       = -1;
        result[1]       = -1;
        result[2]       = arr[0] * arr[1] * arr[2];

        int[] largest3  = new int[3];
        largest3[0]     = arr[0];
        largest3[1]     = arr[1];
        largest3[2]     = arr[2];
        
        // **** sort largest array - O(3 log(3)) ****
        Arrays.sort(largest3);

        // **** ****
        int productLast = result[2];

        // **** for top performance, since 3 is such a small number, 
        //      use inline the code to insert arr[i] into its proper 
        //      slot and drop the original largest3[0] ****
        for (int i = 3; i < arr.length; i++) {

            // **** sort and compute product ****
            if (arr[i] > largest3[0]) {

                // **** replace smallest ****
                largest3[0] = arr[i];

                // **** sort largest array - O(3 log(3)) ****
                Arrays.sort(largest3);

                // **** compute product ****
                productLast = largest3[0] * largest3[1] * largest3[2];
            }

            // **** store product ****
            result[i] = productLast;
        }

        // **** return products ****
        return result;
    } 


    /**
     * Test scaffolding
     * 
     * !!!! NOT PART OF SOLUTION !!!!
     * 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        // **** declare arr ****
        int[] arr = null;

        // **** open buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // **** read and split strings with integers ****
        String[] strs = br.readLine().trim().split(",");

        // **** close buffered reader ****
        br.close();
        
        // **** check if we need to generate our own array of values ****
        if (strs.length == 1 && strs[0].equals("-2")) {

            // **** ****
            Random rand = new Random();

            // **** generate n ****
            int n = rand.nextInt(100000);

            // ???? ????
            System.out.println("main <<<               n: " + n);

            // **** create arr ****
            arr = new int[n];

            // **** populate arr ****
            for (var i = 0; i < n; i++)
                arr[i] = rand.nextInt(1000);
        } else {

            // **** create integer arr from string values ****
            arr = Arrays.stream(strs)
                            .mapToInt(Integer::parseInt)
                            .toArray();
        }

        // **** short arrays ****
        if (arr.length < 16) {
            System.out.println("main <<<     arr: " + Arrays.toString(arr));

            // **** find the max products and display result (take 1) ****
            System.out.println("main <<< findMaxProduct0: " + Arrays.toString(findMaxProduct0(arr)));

            // **** find the max products and display result (take 2) ****
            System.out.println("main <<< findMaxProduct1: " + Arrays.toString(findMaxProduct1(arr)));

            // **** find the max products and display result (take 3) ****
            System.out.println("main <<< findMaxProduct2: " + Arrays.toString(findMaxProduct2(arr)));

            // **** find the max products and display result (take 4) ****
            System.out.println("main <<< findMaxProduct3: " + Arrays.toString(findMaxProduct3(arr)));
        } else {

            // **** ****
            for (var i = 0; i < 4; i++) {

                // **** start timer ****
                long start = System.currentTimeMillis();

                // **** call function of interest ****
                switch (i) {
                    case 0:
                        findMaxProduct0(arr);
                    break;

                    case 1:
                        findMaxProduct1(arr);
                    break;

                    case 2:
                        findMaxProduct2(arr);
                    break;

                    case 3:
                        findMaxProduct3(arr);
                    break;

                    default:
                        System.err.println("main <<< UNEXPECTED i: " + i);
                        System.exit(-1);
                    break;
                }

                // **** stop timer ****
                long end = System.currentTimeMillis();

                // **** display execution time ****
                System.out.println("main <<< findMaxProduct" + i + ": " + (end - start) + " ms");
            }
        }



    }
}