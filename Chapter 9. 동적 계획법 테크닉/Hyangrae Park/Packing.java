import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static String[] name;
    static int val[], vol[];
    static int cache[][], N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int tc = Integer.parseInt(br.readLine());

        for(int i =0 ; i < tc ; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N= Integer.parseInt(st.nextToken());
            int MAX_VOL = Integer.parseInt(st.nextToken());

            name=  new String[N];
            val = new int[N];
            vol = new int[N];

            cache = new int[1001][101];
            for(int c [] : cache)
                Arrays.fill(c, -1);

            for(int j= 0 ; j < N ; j++) {
                st = new StringTokenizer(br.readLine());

                name[j] = st.nextToken();
                vol[j] = Integer.parseInt(st.nextToken());
                val[j] = Integer.parseInt(st.nextToken());
            }

            List<String> list=  new ArrayList<>();
            int result = pack(MAX_VOL,0);
            reconstruct(MAX_VOL,0,list);

            System.out.println(result + " " + list.size());
            for(String n : list)
                System.out.println(n);

        }

    }
    private static int pack(int capacity, int cur) {
        if(cur == N)
            return 0;

        if(cache[capacity][cur] != -1)
            return cache[capacity][cur];

        int ret = (cache[capacity][cur] == -1 ? 0 : cache[capacity][cur]);

        ret = pack(capacity, cur+1);

        //현재 물건을 고르는 경우
        if(vol[cur] <= capacity)
            ret = Math.max(ret,pack(capacity-vol[cur], cur+1) + val[cur]);

        return cache[capacity][cur] = ret;

    }

    private static void reconstruct(int capacity, int item, List<String> list) {
        if(item == N)
            return;

        if(pack(capacity, item) == pack(capacity,item+1))
            reconstruct(capacity,item+1,list);
        else {
            list.add(name[item]);
            reconstruct(capacity-vol[item],item+1,list);
        }
    }
}
