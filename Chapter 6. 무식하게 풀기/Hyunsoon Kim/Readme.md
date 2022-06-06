# 06 무식하게 풀기

- 무식하게 풀기 (brute-force) → 완전 탐색 (exhaustive search) → 반복문과 재귀호출
- 재귀호출
    - 상황에 따라 반복문에 비해 간결하고 유연하게 코드를 작성할 수 있도록 도와준다.
    - 기저 사례 (더이상 쪼개지지 않는 최소한의 작업) 처리 필요 및 항상 활용 필요
    - 반복문 vs 재귀호출
    ex) n 개중 m개의 조합을 찾는 경우
        
        ```cpp
        // 반복문
        for (int i=0; i<n; ++i)
        	for (int j=i+1; j<n; ++j)
        		for (int k=j+1; k<n; ++k)
        			for (int l=k+1; l<n; ++l)
        				cout << i << " " << j << " " << k << " " << l << endl;
        ```
        
        (전위 vs 후위 연산자 : [https://doo9713.tistory.com/7](https://doo9713.tistory.com/7))
        
        ```cpp
        // 재귀호출
        void pick(int n, vector<int>& picked, int toPick) {
        	if (toPick==0) { printPicked(picked); return; }
        	int smallest = picked.empty() ? 0 : picked.back()+1;
        	for (int next=smallest; next<n; ++next) {
        		picked.push_back(next);
        		pick(n, picked, toPick-1);
        		picked.pop_back();
        	}
        }
        ```
        
- 시간 복잡도 = 모든 경우의 수
- 완전 탐색 방법
    - 완전 탐색은 존재하는 모든 답을 하나씩 검사하므로, 걸리는 시간은 가능한 답의 수에 정확히 비례합니다. 최대 크기의 입력을 가정했을 때 답의 개수를 계산하고 이들을 모두 제한 시간 안에 생성할 수 있을지를 가늠합니다. 만약 시간 안에 계산할 수 없다면 다른 방법을 적용합니다.
    - 가능한 모든 답의 후보를 만드는 과정을 여러 개의 선택으로 나눕니다. 각 선택은 답의 후보를 만드는 과정의 한 조각이 됩니다.
    - 그중 하나의 조각을 선택해 답의 일부를 만들고, 나머지 답을 재귀 호출을 통해 완성합니다.
    - 조각이 하나밖에 남지 않은 경우, 혹은 하나도 남지 않은 경우에는 답을 생성했으므로, 이것을 기저 사례로 선택해 처리합니다.
- 문제와 부분 문제
- 최적화 문제
    - 답이 여러 개이고, 그 중 가장 좋은 답을 찾아 내는 문제
    - 가장 기초적이고 직관적인 방법으로 완전 탐색 활용
    ex) 여행하는 외판원 문제
- 많이 등장하는 완전 탐색 유형
    - 모든 순열 만들기 N이 10 이상인 경우, 다른 방법 사용
    - 모든 조합 만들기
    - 2의 n제곱가지 경우의 수 만들기 n비트 정수 활용

---

- 문제 : 소풍 (PICNIC, 하)
    - 코드
        
        ```c
        #include <stdio.h>
        
        int count(int n, int arr[10][10], int select[10]);
        
        int main() {
          int c;
          scanf("%d", &c);
        
          while(c--) {
            int n, m;
            scanf("%d %d", &n, &m);
            
            int arr[10][10] = {0};
            for (int i=0; i<m; i++) {
              int a, b;
              scanf("%d %d", &a, &b);
              arr[a][b] = arr[b][a] = 1;
            }
        
            int select[10] = {0};
            int cnt = count(n, arr, select);
            printf("%d\n", cnt);
          }
        
          return 0;
        }
        
        int count(int n, int arr[10][10], int select[10]) {
          int min = -1;
          for(int i=0; i<n; i++) {
            if(select[i]==0) {
              min = i;
              break;
            }
          }
        
          if (min == -1) {
            return 1;
          }
        
          int ret = 0;
          for (int i=min+1; i<n; i++) {
            if (select[i]==0 && arr[min][i]==1) {
              select[min] = select[i] = 1;
              ret += count(n, arr, select);
              select[min] = select[i] = 0;
            }
          }
        
          return ret;
        }
        ```
        
    - 정리
        - 경우의 수, 재귀 호출
        - 중복 제거 ← 순서 강제
        - 답의 상한, 모든 경우의 수, 1000 이하 → 완전 탐색
- 문제 : 게임판 덮기 (BOARDCOVER, 하)
- 문제 : 시계 맞추기 (CLOCKSYNC, 중)
