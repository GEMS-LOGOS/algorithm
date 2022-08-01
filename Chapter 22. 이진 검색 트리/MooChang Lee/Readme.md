## 검색 트리(search tree)
- 자료를 일정한 상태로 정렬해 둔 채로 저장
- 원소의 추가 삭제 및 특정 원소의 존재 여부 확인 가능
- 가장 흔하게 사용되는 것은 이진 검색 트리(binary search tree) 및 그 변종들
## 이진 트리
- 각 노드가 왼쪽과 오른쪽, 최대 두 개의 자식 노드만을 가질 수 있는 트리
- 이를 이용해, 왼쪽에는 해당 노드보다 작은 값의 노드를, 오른쪽에는 해당 노드보다 큰 값의 노드를 배정
### 순회
- 중위 순회하면 크기 순으로 정렬된 원소의 목록을 얻을 수 있음.
### 검색
- 이진 탐색과 비슷한 속도로 자료를 찾을 수 있음
### 조작
- 원소 추가는 쉽다
- 원소 삭제의 경우
  - '합치기' 연산 구현
    - t 노드의 하위 노드를 합친 후 t 노드를 필두로 하는 서브 노드와 바꿔치기
## 레드-블랙 트리
- Each node is either red or black. (노드는 레드 혹은 블랙 중의 하나이다.)
- The root is black. (루트 노드는 블랙이다.)
- All leaves (NIL) are black. (모든 리프 노드(NIL)들은 블랙이다.)
- If a node is red, then both its children are black. (레드 노드의 자식 노드들은 언제나 블랙이다.)
- Every path from a given node to any of its descendant NIL nodes contains the same number of black nodes. (어떤 노드로부터 시작되어 리프 노드에 도달하는 모든 경로에는 모두 같은 개수의 블랙 노드가 있다.)
### 구현
```c

``` 