// q. https://www.hackerrank.com/challenges/candies/problem?isFullScreen=true&h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=dynamic-programming

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'candies' function below.
     *
     * The function is expected to return a LONG_INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER_ARRAY arr
     */

    public static long candies(int n, List<Integer> arr) {
    // Write your code here
        long sum = 0;
        int[] dp_up = new int[arr.size()];
        int[] dp_down = new int[arr.size()];
        dp_up[0] = 1;
        dp_down[arr.size() - 1] = 1;
        
        for(int i=1; i<arr.size(); i++){
            dp_up[i] = (arr.get(i - 1) < arr.get(i)) ? (dp_up[i-1] + 1) : 1;
        }
        for(int i=0; i<arr.size(); i++){
            System.out.print(dp_up[i] + " ");
        }
        System.out.println("");
        for(int i=arr.size() - 2; i>=0; i--){
            int t = (arr.get(i + 1) < arr.get(i)) ? (dp_down[i+1] + 1) : 1;
            dp_down[i] = Math.max(dp_down[i], t);
        }
        for(int i=0; i<arr.size(); i++){
            System.out.print(dp_down[i] + " ");
        }
        
        for(int i=0; i<arr.size(); i++) {
            sum += Math.max(dp_up[i], dp_down[i]);
        }
        
        return sum;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> arr = IntStream.range(0, n).mapToObj(i -> {
            try {
                return bufferedReader.readLine().replaceAll("\\s+$", "");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(toList());

        long result = Result.candies(n, arr);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
