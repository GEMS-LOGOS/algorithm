import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class ITES {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int C = Integer.parseInt(br.readLine());
		while (C-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int K = Integer.parseInt(st.nextToken());
			int N = Integer.parseInt(st.nextToken());

			long A = 1983;
			Queue<Integer> q = new LinkedList<>();
			int sum = 0;
			int count = 0;
			for (int i = 1; i <= N; i++) {
				int signal = (int) (A % 10000 + 1);
				q.add(signal);
				sum += signal;
				while (sum > K) {
					sum -= q.poll();
				}
				if (sum == K) {
					count++;
				}
				A = (long) ((A * 214013 + 2531011) % Math.pow(2, 32));
			}
			System.out.println(count);
		}
	}
}
