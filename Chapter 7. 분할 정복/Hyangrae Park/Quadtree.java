import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int head = 0;
    static String input;

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int max = Integer.parseInt(bufferedReader.readLine());

        for (int i = 0; i < max; i++) {
            head = 0;
            input = bufferedReader.readLine();

            System.out.println(reverse());
        }
    }

    private static String reverse() {
        if (input.charAt(head) != 'x') {
            return input.charAt(head++) + ""; // 리턴 값을 스트링으로 캐스팅하기 위한 empty string
        }
        head++;
        String[] divided = new String[4];
        divided[0] = reverse(); // 좌측 상단 뒤집기
        divided[1] = reverse(); // 우측 상단 뒤집기
        divided[2] = reverse(); // 좌측 하단 뒤집기
        divided[3] = reverse(); // 우측 하단 뒤집기

        return "x" + divided[2] + divided[3] + divided[0] + divided[1]; // 상하 위치 변경
    }
}
