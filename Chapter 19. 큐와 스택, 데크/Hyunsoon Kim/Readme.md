# 19 큐와 스택, 데크

---

- 큐와 스택, 데크
    - 공통점 : 일렬로 늘어선 같은 형태의 자료들을 저장
    - 차이점 : 자료를 넣고 뺄 수 있는 방향
- 큐
    - 한쪽 끝에서 자료를 넣고 반대 쪽 끝에서 자료를 꺼낼 수 있다
    - 선입선출 (FIFO, First In First Out)
- 스택
    - 한쪽 끝에서만 자료를 넣고 뺄 수 있다
    - 후입선출 (LIFO, Last In First Out)
- 데크
    - 양쪽 끝에서 자료들을 넣고 뺄 수 있다
    - 스택과 큐를 구현하는데 사용 가능
- 큐와 스택, 데크의 구현
    - 연결 리스트
    - 동적 배열 (head, tail 활용)
    - 표준 라이브러리

---

- 문제 : 짝이 맞지 않는 괄호 (BRACKETS2, 하)
    - 풀이
        - 스택 사용
    - 코드
        
        ```c
        #define _CRT_SECURE_NO_WARNINGS
        
        #include <stdio.h>
        
        char str[10000];
        
        void cal() {
        	char stack[10000] = { 0, };
        	int str_idx = 0;
        	int stack_idx = 0;
        
        	while (str[str_idx] != '\0') {
        		if (str[str_idx] == '(' || str[str_idx] == '{' || str[str_idx] == '[') {
        			stack[stack_idx++] = str[str_idx++];
        		}
        		else {
        			if (stack_idx == 0) {
        				printf("NO\n");
        				return;
        			} else if (str[str_idx] == ')') {
        				if (stack[stack_idx - 1] == '(') {
        					str_idx++;
        					stack_idx--;
        				}
        				else {
        					printf("NO\n");
        					return;
        				}
        			} else if (str[str_idx] == '}') {
        				if (stack[stack_idx - 1] == '{') {
        					str_idx++;
        					stack_idx--;
        				}
        				else {
        					printf("NO\n");
        					return;
        				}
        			} else if (str[str_idx] == ']') {
        				if (stack[stack_idx - 1] == '[') {
        					str_idx++;
        					stack_idx--;
        				}
        				else {
        					printf("NO\n");
        					return;
        				}
        			}
        		}
        	}
        	
        	if (stack_idx == 0) printf("YES\n");
        	else printf("NO\n");
        	return;
        }
        
        int main() {
        	int c;
        	scanf("%d", &c);
        	while (c--) {
        		scanf("%s", str);
        		cal();
        	}
        	return 0;
        }
        ```
        
- 문제 : 외계 신호 분석 (ITES, 중)

---
