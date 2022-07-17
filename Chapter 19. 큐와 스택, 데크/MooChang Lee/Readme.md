# Chapter 19. 큐와 스택, 데크

1. 큐 (queue)
    - 한쪽 끝에서 자료를 넣고 반대 쪽 끝에서 자료를 꺼낼 수 있는 선형 자료 구조. (FIFO, First In First Out)
    - 놀이공원 및 음식점에서 선 줄, 할 일의 목록 등
2. 스택 (stack)
    - 한쪽 끝에서만 자료를 넣고 뺄 수 있는 선형 자료 구조. (LIFO, Last in First Out)
    - 함수 호출 문맥 관리
3. 데크 (dequeue)
    - 양쪽 끝에서 자료들을 넣고 뺄 수 있는 섢평 자료 구조.

* 대부분의 표준 라이브러리에서 제공하는 자료 구조이기 때문에 이들을 직접 구현할 이유는 없다. 소중한 대회 시간을 이를 구현하는데 낭비하지 말 것.

- 조세푸스 문제
```c
#include <stdio.h>
#include <queue>

#define FORI(MAX) for(int i=0;i<MAX;i++)

using namespace std;
void josephus(int n,int k){
    queue<int> people;
    FORI(n){
        people.push(n+1);
    }    
    while(people.size()!=2){
        people.pop();
        FORI(k){
            people.push(people.front());
            people.pop();
        }
    }
    cout << people.front() << " " << people.back() << endl;

}
```

- 외계 신호 분석
```c
#include <stdio.h>
#include <queue>

#define FORI(MAX) for(int i=0;i<MAX;i++)

using namespace std;
void signal(){

}
```