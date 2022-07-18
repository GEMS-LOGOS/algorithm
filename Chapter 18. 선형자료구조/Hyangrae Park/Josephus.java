import java.util.LinkedList;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    int t;
    Scanner sc = new Scanner(System.in);
    t = sc.nextInt();
    
    for (int i = 0; i < t; i++) {
      int n = sc.nextInt();
      int k = sc.nextInt();
      LinkedList<Integer> arr = new LinkedList<>();
      for (int j = 1; j <=n; j++) {
        arr.add(j);
      }
      int dir = 0;
      arr.remove(dir);
      
      while(arr.size() > 2) {
        dir = dir+k-1;
        while (dir<=arr.size()) {
          dir = dir-arr.size();
        }
        arr.remove(dir);
      }
      System.out.println(arr.get(0)+" "+arr.get(1));
    }
  }
}
