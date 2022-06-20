# 08 동적 계획법

---

1. 도입
    - 동적 계획법
        - 프로그래밍 대회에서 가장 자주 출현하는 디자인 패러다임
        - 분할 정복과 비교
            - 공통 : 문제를 작은 문제들로 나누어 해결
            - 차이 : 두 개 이상의 문제를 푸는 작은 문제 (중복되는 부분 문제) 존재
        - 장점 : 중복 계산 제거 → 속도 향상 (계산 결과 저장 메모리(캐시) 사용)
        - 예시 : 이항 계수 계산
    - 메모이제이션
        - 결과 저장 및 재활용하는 최적화 기법, 동적 계획법에 활용
        - 조건 : 참조적 투명성 함수 (입력이 고정되어 있을 때 그 결과가 항상 같은 함수)
        - 구현 패턴 : 기저 사례 처리 → 캐시 확인 (초기화 필수) → 캐시 O : 활용 / 캐시 X : 계산
        - 시간 복잡도 : (대략적) 존재하는 부분 문제의 수 X 한 부분 문제를 풀 때 필요한 반복문
    - 예제 : 외발 뛰기 (JUMPGAME, 하)
        - 완전 탐색
            
            ```cpp
            int n, board[100][100];
            
            bool jump(int y, int x) {
            	if (y >=n || x >= n) return false;
            	if (y == n-1 && x == n-1) return true;
            
            	int jumpSize = board[y][x];
            	return jump(y + jumpSize, x) || jump(y, x + jumpSize);
            }
            ```
            
        - 동적 계획법
            
            ```cpp
            int n, board[100][100];
            int cache[100][100]; // set to -1
            
            bool jump(int y, int x) {
            	if (y >=n || x >= n) return false;
            	if (y == n-1 && x == n-1) return true;
            
            	int& ret = cache[y][x];
            	if(ret != -1) return ret;
            
            	int jumpSize = board[y][x];
            	return ret = jump(y + jumpSize, x) || jump(y, x + jumpSize);
            }
            ```
            
    - 동적 계획법 레시피
        - 주어진 문제를 완전 탐색을 이용해 해결합니다.
        - 중복된 부분 문제를 한 번만 계산하도록 메모이제이션을 적용합니다.
    - 반복적 동적 계획법
        - 재귀 호출을 이용하지 않은 동적 계획법
2. 전통적 최적화 문제들
    - 예제 : 삼각형 위의 최대 경로 (TRIANGLEPATH, 하)
        
        ```cpp
        int n, triangle[100][100];
        int cache[100][100];
        
        int path(int y, int x) {
        	if (y == n-1) return triangle[y][x];
        
        	int& ret = cache[y][x];
        	if (ret != -1) return ret;
        
        	return ret = max(path(y + 1, x), path(y, x + 1)) + triangle[y][x];
        }
        ```
        
    - 이론적 배경 : 최적 부분 구조
        - 어떤 문제와 분할 방식에 성립하는 조건
        - 각 부분 문제의 최적해만 있으면 전체 문제의 최적해를 쉽게 얻어낼 수 있을 경우, 이 조건이 성립
        - 동적 계획법 알고리즘을 적용하기 위해 아주 중요한 조건
    - 예제 : 최대 증가 부분 수열 (LIS, 하)
        - 완전 탐색
            
            ```cpp
            int lis (const vector<int>& A) {
            	if (A.empty()) return 0;
            	int ret = 0;
            	for (int i = 0; i < A.size(); ++i) {
            		vector<int> B;
            		for (int j = i+1; j < A.size(); ++j)
            			if (A[i] < A[j])
            				B.push_back(A[j]);
            		ret = max(ret, 1 + lis(B);
            	}
            	return ret;
            }
            ```
            
        - 동적 계획법
            
            ```cpp
            int n;
            int cache[100], S[100];
            int lis2 (int start) {
            	int& ret = cache[start];
            	if (ret != -1) return ret;
            	ret = 1;
            	for (int next = start+1, next < n; ++next)
            		if (S[start] < S[next])
            			ret = max(ret, list(next) + 1);
            	return ret;
            }정
            ```
            
        - 동적 계획법 (개선 : 시작 위치 고정)
            
            ```cpp
            int n;
            int cache[101], S[100];
            int lis3 (int start) {
            	int& ret = cache[start+1];
            	if (ret != -1) return ret;
            	ret = 1;
            	for (int next = start+1, next < n; ++next)
            		if (start == -1 || S[start] < S[next])
            			ret = max(ret, list(next) + 1);
            	return ret;
            }
            ```
            
    - 최적화 문제 동적 계획법 레시피
        - 모든 답을 만들어 보고 그중 최적해의 점수를 반환하는 완전 탐색 알고리즘을 설계합니다.
        - 전체 답의 점수를 반환하는 것이 아니라, 앞으로 남은 선택들에 해당하는 점수만을 반환하도록 부분문제 정의를 바꿉니다.
        - 재귀 호출의 입력에 이전의 선택에 관련된 정보가 있다면 꼭 필요한 것만 남기고 줄입니다. 문제에 최적 부분 구조가 성립할 경우에는 이전 선택에 관련된 정보를 완전히 없앨 수도 있습니다. 여기서 우리의 목표는 가능한 한 중복되는 부분 문제를 많이 만드는 것입니다. 입력의 종류가 줄어들면 줄어들 수 록 더 많은 부분 문제가 중복되고, 따라서 메모이제이션을 최대한도로 활용할 수 있지요.
        - 입력이 배열이거나 문자열인 경우 가능하다면 적절한 변환을 통해 메모이제이션을 할 수 있도록 합니다.
        - 메모이제이션을 적용합니다.
3. 경우의 수와 확률
    - 오버플로에 유의하기 → 모듈라 연산
    - 예제 : 타일링 방법의 수 세기 (TILING2, 하)
        
        ```cpp
        const int MOD = 1000000007;
        int cache[101];
        int tiling(int width) {
        	if (width <= 1) return 1;
        	int & ret = cache[width];
        	if (ret != -1) return ret;
        	return ret = (tiling(width-2) + tiling(width-1)) % MOD
        }
        ```
        
    - 예제 : 삼각형 위의 최대 경로 개수 세기 (TRIPATHCNT, 중)
        
        ```cpp
        int countCache[100][100];
        int count(int y, int x) {
        	if (y == -1) return 1;
        	int& ret = countCache[y][x];
        	if (ret != -1) return ret;
        	ret = 0;
        	if (path2(y+1, x+1) >= path2(y+1, x)) ret += count(y+1, x+1);
        	if (path2(y+1, x+1) <= path2(y+1, x)) ret += count(y+1, x);
        	return ret;
        }
        ```
        
    - 예제 : 우물을 기어오르는 달팽이
        
        ```cpp
        int n, m;
        int cache[MAX_N][2*MAX_N+1];
        int climb(int days, int climbed) {
        	if (days ==m) return climbed >= n ? 1 : 0;
        	int& ret = cache[days][climbed];
        	if (ret != -1) return ret;
        	return ret = climb(days+1, climbed+1) + climbed(days+1, climbed+2);
        }
        ```
        
    - 예제 : 장마가 찾아왔다 (SNAIL, 하)
        
        ```cpp
        int n, m;
        int cache[MAX_N][2*MAX_N+1];
        int climb(int days, int climbed) {
        	if (days ==m) return climbed >= n ? 1 : 0;
        	int& ret = cache[days][climbed];
        	if (ret != -1) return ret;
        	return ret = 0.75 * climb(days+1, climbed+1) + 0.25 * climbed(days+1, climbed+2);
        }
        ```
        
    - 경우의 수 계산하기 레시피
        - 모든 답을 직접 만들어서 세어보는 완전 탐색 알고리즘을 설계합니다. 이때 경우의 수를 제대로 세기 위해서는 재귀 호출의 각 단계에서 고르는 각 선택지에 다음과 같은 속성이 성립해야 합니다.
            - 모든 경우는 이 선택지들에 포함됨
            - 어떤 경우도 두 개 이상의 선택지에 포함되지 않음
        - 최적화 문제를 해결할 때처럼 이전의 조각에서 결정한 요소들에 대한 입력을 없애거나 변형해서 줄입니다. 재귀 함수는 앞으로 남아 있는 조각들을 고르는 경우의 수만을 반환해야 합니다.
        - 메모이제이션을 적용합니다.

---

- [1] 문제: 와일드카드 (WILDCARD, 중)
    - 코드
        
        ```c
        #define _CRT_SECURE_NO_WARNINGS
        
        #define N 100
        #define INIT -1
        
        #include <stdio.h>
        #include <string.h>
        
        char W[N], S[N];
        int WLen, SLen;
        int cache[N+1][N+1];
        
        int matchMemoized(int w, int s) {
        	if (cache[w][s] != -1) return cache[w][s];
        	if (w < WLen && s < SLen && (W[w] == '?' || W[w] == S[s]))
        		return cache[w][s] = matchMemoized(w + 1, s + 1);
        	if (w == WLen) return cache[w][s] = (s == SLen);
        	if (W[w] == '*') {
        		if (matchMemoized(w + 1, s) || (s < SLen && matchMemoized(w, s + 1)))
        			return cache[w][s] = 1;
        	}
        	return 0;
        }
        
        int main() {
        	int c;
        	scanf("%d", &c);
        	while (c--) {
        		scanf("%s", W);
        		WLen = strlen(W);
        		int sc;
        		scanf("%d", &sc);
        		int cnt = 0;
        		char list[50][100];
        		while (sc--) {
        			scanf("%s", S);
        			SLen = strlen(S);
        			memset(cache, INIT, (N + 1) * (N + 1) * sizeof(int));
        			int result = matchMemoized(0, 0);
        			if (result) strcpy(list[cnt++], S);
        		}
        
        		char tmp[100];
        		for (int i = 0; i < cnt - 1; i++) {
        			for (int j = 0; j < cnt - 1 - i; j++) {
        				if (strcmp(list[j], list[j + 1]) > 0) {
        					strcpy(tmp, list[j]);
        					strcpy(list[j], list[j + 1]);
        					strcpy(list[j + 1], tmp);
        				}
        			}
        		}
        		for (int i = 0; i < cnt; i++) {
        			printf("%s\n", list[i]);
        		}
        	}
        	return 0;
        }
        
        ```
        
- [2] 문제 : 합친 LIS (JLIS, 하)
- [2] 문제 : 원주율 외우기 (PI, 하)
- [2] 문제 : Quantization (QUANTIZE, 중)
- [3] 문제 : 비대칭 타일링 (ASYMTILING, 하)
- [3] 문제 : 폴리오미노 (POLY, 중)
- [3] 문제 : 두니발 박사의 탈옥 (NUMB3RS, 중)

---
