# 18 선형 자료 구조

---

- 동적 배열
    - 배열의 문제를 해결하기 위해 등장
        - 배열의 문제 : 처음에 배열을 선언할 때 배열의 크기를 지정해야하며, 그 이상의 자료를 넣을 수 없다 (동적 배열은 자료의 개수가 변함에 따라 크기가 변경됨)
    - 언어 차원에서 지원되는 기능이 아니라 배열을 이용해 만들어 낸 별도의 자료 구조
    - 배열의 특성을 이어받음
        - 원소들은 메모리의 연속된 위치에 저장된다
        - 주어진 위치의 원소를 반환하거나 변경하는 동작을 O(1)에 할 수 있다
    - 추가적인 특성
        - 배열의 크기를 변경하는 resize() 연산이 가능하며, 이 동작을 수행하는 데는 배열의 크기 N에 비례하는 시간이 소요
        - 주어진 원소를 배열의 맨 끝에 추가하고 크기를 1 증가 시키는 append() 연산을 지원하며, 이 동작을 수행하는 데 상수 시간이 소요
    - 동적 배열 구현 아이디어 : 배열 재할당
    - append() 연산을 상수 시간에 할 수 있는 방법 : 재할당 시 현재 원소 개수의 비례해서 진행
- 연결 리스트
    - 배열의 문제를 해결하기 위해 등장
        - 배열의 문제 : 원소들의 순서를 유지하면서 임의의 위치에 원소를 삽입하거나, 삭제하는 것은 원소들을 옮겨야하기 때문에 시간이 오래걸린다 (연결 리스트는 상수 시간에 가능)
    - 연결 리스트 응용 연산
        - 잘라 붙이기 연산
        - 삭제했던 원소 돌려놓기
- 동적 배열과 연결 리스트의 비교
    - 가장 큰 차이점 : 삽입과 삭제, 임의의 원소에 접근하는데 걸리는 시간
        - 삽입, 삭제 X, 추가가 배열 끝에서만 발생하는 경우 → 동적 배열 사용
            - 임의의 원소에 대한 접근이 빠르고, 원소들의 공간 지역성으로 캐시 효율 증가
        - 반대 → 연결 리스트 사용
    - 시간 복잡도 비교
        
        
        | 작업 | 동적 배열 | 연결 리스트 |
        | --- | --- | --- |
        | 이전 원소/ 다음 원소 찾기 | O(1) | O(1) |
        | 맨 뒤에 원소 추가/삭제하기 | O(1) | O(1) |
        | 맨 뒤 이외의 위치에 원소 추가/삭제하기 | O(n) | O(1) |
        | 임의의 위치의 원소 찾기 | O(1) | O(n) |
        | 크기 구하기 | O(1) | O(n) or O(1) |

---

- 문제 : 조세푸스 문제 (JOSEPHUS, 하)
    - 코드
        
        ```c
        #define _CRT_SECURE_NO_WARNINGS
        
        #include <stdio.h>
        #include <stdlib.h>
        
        int n, k;
        struct node* list;
        
        struct node {
        	int n;
        	struct node * prev;
        	struct node * next;
        };
        
        void init() {
        	struct node* temp_now = malloc(sizeof(struct node));
        	struct node* temp_prev = NULL;
        
        	list = temp_now;
        
        	for (int i = 0; i < n; i++) {
        		temp_now->n = i + 1;
        		if (i == (n - 1)) {
        			temp_now->prev = temp_prev;
        			temp_now->next = list;
        			temp_now->next->prev = temp_now;
        		}
        		else {
        			temp_now->prev = temp_prev;
        			temp_now->next = malloc(sizeof(struct node));
        			temp_prev = temp_now;
        			temp_now = temp_now->next;
        		}
        	}
        }
        
        void cal() {
        	struct node* temp_now = list;
        	struct node* temp_prev = list->prev;
        	struct node* temp_next = list->next;
        
        	for (int i = 0; i < n - 2; i++) {
        		temp_prev->next = temp_next;
        		temp_next->prev = temp_prev;
        		free(temp_now);
        
        		temp_now = temp_next;
        		for (int j = 0; j < k - 1; j++) {
        			temp_now = temp_now->next;
        		}
        		temp_prev = temp_now->prev;
        		temp_next = temp_now->next;
        	}
        
        	printf("%d %d\n", temp_now->n, temp_now->next->n);
         }
        
        int main() {
        	int c;
        	scanf("%d", &c);
        	while (c--) {
        		scanf("%d %d", &n, &k);
        		init();
        		cal();
        	}
        	return 0;
        }
        ```
        

---
