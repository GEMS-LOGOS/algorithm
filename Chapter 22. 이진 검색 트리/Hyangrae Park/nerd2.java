import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {

    static TreeMap<Integer, Integer> map;

    static boolean isDominated(int p, int q) {
        int higher;
        if (map.higherKey(p) == null) {
            return false;
        } else {
            higher = map.higherKey(p);
        }
        return q < map.get(higher);
    }

    static void removeDominated(int p, int q) {
        while (true) {
            int lower;
            if (map.lowerKey(p) == null) {
                return;
            } else {
                lower = map.lowerKey(p);
            }

            if (q > map.get(lower)) {
                map.remove(lower);
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            int N = Integer.parseInt(br.readLine());
            map = new TreeMap<>();
            int sum = 0;
            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int p = Integer.parseInt(st.nextToken());
                int q = Integer.parseInt(st.nextToken());

                map.put(p, q);
                if (isDominated(p, q)) {
                    map.remove(p);
                } else {
                    removeDominated(p, q);
                }
                sum += map.size();
            }
            System.out.println(sum);
        }

    }
}
