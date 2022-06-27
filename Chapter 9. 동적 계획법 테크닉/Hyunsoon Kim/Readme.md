# 09 동적 계획법 테크닉

---

- [1] 최적화 문제의 실제 답 계산하기
    - 최적화 문제 답 계산하기
        - 재귀 호출의 각 단계에서 최적해를 만들었던 선택을 별도의 배열에 저장해둡니다.
        - 별도의 재귀 함수를 이용해 이 선택을 따라가며 각 선택지를 저장하거나 출력합니다.
- [2] k번째 답 계산하기
    - k번째 답 계산하기 레시피
        - 답들을 사전순서대로 만들며 경우의 수를 세는 완전 탐색 알고리즘을 설계하고, 메모이제이션을 적용해 경우의 수를 세는 동적 계획법 알고리즘으로 바꿉니다.
        - 모든 답들을 사전순으로 생성하며 skip개를 건너뛰고 첫 번째 답을 반환하는 재귀 호출 함수를 구현합니다. 재귀 함수는 각 조각들에 들어갈 수 있는 값을 하나씩 고려하면서 이 값을 선택했을 때 만들어지는 답의 수 M과 건너 뛰어야 할 답의 수 skip을 비교합니다.
            - M ≤ skip : M개의 답은 모두 우리가 원하는 답보다 앞에 있으므로, 이들을 건너뜁니다. 대신 skip을 M만큼 줄입니다.
            - M ≥ skip : M개의 답 중에 우리가 원하는 답이 있으므로, 이 값을 선택합니다. M개의 답 중에 skip개를 건너뛴 것이 우리가 원하는 답입니다. 이 값을 답에 추가하고 재귀 호출로 답의 나머지 부분을 만듭니다.
            
    - 예제 : 모스 부호 사전 (MORSE, 중)
        
        ```cpp
        string kth(int n, int m, int skip) {
        	if(n == 0) return string(m, 'o');
        	if(skip < bino[n+m-1][n-1])
        		return '-' + kth(n-1, m, skip);
        	return 'o' + kth(n, m-1, skip - bino[n+m-1][n-1]);
        }
        ```
        
- [3] 정수 이외의 입력에 대한 메모이제이션
    - 연관 배열 사용하기 ex) STL map
    - 일대일 대응 함수 작성하기
        - 입력이 불린 값의 배열인 경우 : 길이 n 2진수 처리
        - 입력이 순열인 경우 : 사전순 계산 함수 활용
        - 입력의 범위가 좁을 경우 : n 자리 k 진수 처리
- [4] 조합 게임
    - 조합 게임이란 두 사람의 참가자가 하는 게임 ex) 체스, 오목, 바둑
        - 조합 게임 해결 : 게임의 상태가 주어졌을 때 완벽한 한 수를 찾아내는 알고리즘을 만드는 것, 게임 트리 활용
- [5] 반복적 동적 계획법
    - 부분 문제 간의 의존성을 파악하기 쉬울 경우 → 반복문 활용 → 반복적 동적 계획법
    - 슬라이딩 윈도
        - 사용하는 데이터 전체를 메모리에 유지하는 것이 아니라 필요한 부분만을 저장하는 기법
        - 반복적 동적 계획법에서 공간 복잡도를 줄이는데 사용
    - 선형 변환 형태의 점화식을 행렬을 이용해 풀 때에도 활용
    - 반복적 동적 계획법 vs 재귀적 동적 계획법
        - 재귀적 동적 계획법
            - 장점
                - 좀더 직관적인 코드를 짤 수 있다
                - 부분 문제 간의 의존 관계나 계산 순서에 대해 고민할 필요가 없다
                - 전체 부분 문제 중 일부의 답만 필요할 경우 더 빠르게 동작한다
            - 단점
                - 슬라이딩 윈도 기법을 쓸 수 없다
                - 스택 오버플로를 조심해야 한다
        - 반복적 동적 계획법
            - 장점
                - 구현이 대개 더 짧다
                - 재귀 호출에 필요한 부하가 없기 때문에 조금 더 빠르게 동작한다
                - 슬라이딩 윈도 기법을 쓸 수 있다
            - 단점
                - 구현이 좀더 비직관적이다
                - 부분 문제 간의 의존 관계를 고려해 계산되는 순서를 고민해야 한다

---

- [1] 문제 : 여행 짐 싸기 (PACKING, 중)
    - 풀이 메모
        - 메모이제이션 :  cache[100] -> cache[100][0,1] -> **cache[100][1001]**
    - 코드 (예제 동작 확인, 답안 코드 확인 → 풀이 맞는듯, 근데 알고스팟 답안 제출 시 오답?)
        
        ```c
        #define _CRT_SECURE_NO_WARNINGS
        #define MAX(a, b) a >= b ? a : b
        
        #include <stdio.h>
        #include <string.h>
        
        int n, w;
        char item[100][20];
        int v[100],l[100] ;
        int cache[1001][100];
        char arr[100][20];
        int arr_cnt;
        
        int cal(int weight, int idx) {
        	if (idx == n) return 0;
        
        	if (cache[weight][idx] != -1) return cache[weight][idx];
        
        	if (weight >= v[idx])
        		return cache[weight][idx] = MAX(cal(weight - v[idx], idx + 1) + l[idx], cal(weight, idx + 1));
        	else
        		return cache[weight][idx] = cal(weight, idx + 1);
        }
        void get(int weight, int idx) {
        	if (idx == n) return;
        
        	if (cal(weight, idx) == cal(weight, idx + 1))
        		get(weight, idx + 1);
        	else {
        		strcpy(arr[arr_cnt++], item[idx]);
        		get(weight - v[idx], idx + 1);
        	}
        }
        
        int main() {
        	int c;
        	scanf("%d", &c);
        	while (c--) {
        		scanf("%d %d", &n, &w);
        		for (int i = 0; i < n; i++) {
        			scanf("%s %d %d", item[i], &v[i], &l[i]);
        			memset(cache, -1, 100 * 1001 * sizeof(int));
        			arr_cnt = 0;
        		}
        		int result = cal(w, 0);
        		get(w, 0);
        		printf("%d %d\n", result, arr_cnt);
        		for (int i = 0; i < arr_cnt; i++)
        			printf("%s\n", arr[i]);
        	}
        	return 0;
        }
        ```
        
- [1] 문제 : 광학 문자 인식 (OCR, 상)
- [2] 문제 : k번째 최대 증가 부분 수열 (KLIS, 상)
- [2] 문제 : 드래곤 커브 (DRAGON, 중)
- [3] 문제 : 웨브바짐 (ZIMBABWE, 상)
- [3] 문제 : 실험 데이터 복구하기 (RESTORE, 중)
- [4] 문제 : 숫자 게임 (NUMBERGAME,  하)
- [4] 문제 : 블록 게임 (BLOCKGAME, 중)
- [5] 문제 : 회전초밥 (SUSHI, 중)
- [5] 문제 : 지니어스 (GENIUS, 중)

---
