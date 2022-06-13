# 07 분할 정복

---

- 분할 정복이란
    - 가장 유명한 알고리즘 디자인 패러다임
    - 주어진 문제를 둘 이상의 부분 문제로 나눈 뒤 각 문제에 대한 답을 재귀 호출을 이용해 계산하고, 각 부분 문제의 답으로부터 전체 문제의 답을 계산한다.
- 분할 정복 vs 일반적인 재귀 호출
    - 분할 정복 : 거의 같은 크기로 나누기
    - 일반적인 재귀 호출 : 한 조각과 나머지 전체로 나누기
- 분할 정복을 사용하는 알고리즘의 세 가지 구성 요소
    - 문제를 더 작은 문제로 분할하는 과정 (divide)
    - 각 문제에 대해 구한 답을 원해 문제에 대한 답으로 병합하는 과정 (merge)
    - 더이상 답을 분할하지 않고 곧장 풀 수 있는 매우 작은 문제 (base case)
- 분할 정복을 사용하기 위한 문제의 특성
    - 문제를 둘 이상의 부분 문제로 나누는 자연스러운 방법이 있다.
    - 부분 문제의 답을 조합해 원래 문제의 답을 계산하는 효율적인 방법이 있다.
- 분할 정복의 장점
    - 같은 작업을 더 빠르게 처리한다.

---

- 예제 : 수열의 빠른 합과 행렬의 빠른 제곱
    - 같은 문제라도 어떻게 분할하느냐에 따라 시간 복잡도의 차이가 커진다.
    여러 번 중복되어 계산되면서 시간을 소모하는 부분 문제들이 존재하기 때문에
- 예제 : 병합 정렬과 퀵 정렬
    - 같은 아이디어라도 어떤 식으로 분할하느냐에 따라 다른 알고리즘이 될 수 있다.
    시간이 많이 걸리는 작업을 분할 단계에서 하느냐 병합 단계에서 하느냐의 차이
- 예제 : 카라츠바의 빠른 곱셈 알고리즘
    - 곱셈 시간복잡도 개선 : O(n^2) → O(n^lg3) n의 크기가 작을 경우에는 O(n^2) 사용
        
        곱셈 수를 4번 → 3번으로 감소
        

---

- 문제 : 쿼드 트리 뒤집기 (QUADTREE, 하)
    
    ```cpp
    #include<iostream>
    #include<string>
    
    using namespace std;
    
    string reverse(string::iterator& it) {
    	char head = *it;
    	++it;
    
    	if (head == 'b' || head == 'w')
    		return string(1, head);
    
    	string upperLeft = reverse(it);
    	string upperRight = reverse(it);
    	string lowerLeft = reverse(it);
    	string lowerRight = reverse(it);
    
    	return string("x") + lowerLeft + lowerRight + upperLeft + upperRight;
    }
    
    int main() {
    	int cases;
    	cin >> cases;
    	while (cases--) {
    		string str;
    		cin >> str;
    		string::iterator it = str.begin();
    		cout << reverse(it) << endl;
    	}
    	return 0;
    }
    ```
    
- 문제 : 울타리 잘라내기 (FENCE, 중)
- 문제 : 팬미팅 (FANMEETING, 상)

---
