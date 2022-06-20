// https://www.hackerrank.com/challenges/abbr/problem?isFullScreen=true&h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=dynamic-programming

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

   static boolean isEqual(char a, char b){
        return a == b || Character.toUpperCase(a) == b;
    }

    static String abbreviation(String a, String b) {
        boolean[][] dp = new boolean[a.length()+1][b.length()+1];
        dp[0][0] = true;

        for(int i=1; i <= a.length(); i++){
            for(int j=0; j <= b.length(); j++){
                if(j > 0 && dp[i-1][j-1] && isEqual(a.charAt(i-1), b.charAt(j-1))){
                    dp[i][j] = true;
                }
                if(Character.isLowerCase(a.charAt(i-1)) && dp[i-1][j]){
                    dp[i][j] = true;
                }
                
            }
        }
        return dp[a.length()][b.length()] ? "YES" : "NO";
    }


}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                String a = bufferedReader.readLine();

                String b = bufferedReader.readLine();

                String result = Result.abbreviation(a, b);

                bufferedWriter.write(result);
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
