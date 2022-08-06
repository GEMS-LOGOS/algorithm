## 우선순위 큐와 힙
- 우선순위 큐
  - 순서대로 기다리고 있는 자료들을 저장하는 자료 구조
  - 가장 Priority가 높은 자료가 가장 먼저 꺼내진다.
  - 이러한 우선순위 큐를 구현하는 방법 중 대표적인 것이 힙(heap)이라는 트리
- 힙(heap)
  - 특정한 규칙을 만족하도록 구성된 이진 트리
    - 힙의 대소 관계 규칙
      - 부모 노드가 가진 원소는 항상 자식 노드가 가진 원소 이상
      - 왼쪽 자식과 오른쪽 자식이 갖는 원소의 크기는 제한하지 않음
    - 트리 높이를 일정하게 유지하기 위해 트리 구조 제약
      - 마지막 레벨을 제외한 모든 레벨에 노드가 꽉 차 있어야 한다.
      - 마지막 레벨에 노드가 있을 때는 항상 가장 왼쪽부터 순서대로 채워져 있어야 한다.
  - 이진 검색 트리와 다른 점
    - 중위 검색 결과가 정렬되어 있을 것이라는 제약이 없음.
  - push_heap() 또는 up()
    - 시간복잡도 : O(lgN)
```c
void push_heap(vector<int>& heap, int newValue){
    heap.push_back(newValue);
    int idx = heap.size() - 1;
    while(idx > 0 && heap[(idx-1)/2] < heap[idx]){
        swap(heap[idx],heap[(idx-1)/2]);
        idx = (idx-1)/2;
    }
}
```
  - pop_heap() 또는 down()
    - 시간복잡도 : O(lgN)
```c
int pop_heap(vector<int>& heap){
    int result = heap[0];
    heap[0] = heap.back();
    heap.pop_back();
    int here=0;
    while(true){
        int left = here * 2 + 1 , right = here * 2 + 2;
        if(left >= heap.size()) break;
        int next = here;
        if(heap[next] < heap[left])
            next = left;
        if(right < heap.size() && heap[next]<heap[right])
            next = right;
        if(next == here) break;
        swap(heap[here],heap[next]);
        here=next;
    }
}
```

### 문제 : 변화하는 중간 값 (문제 ID : RUNNINGMEDIAN, 난이도: 하)
한 수열의 중간 값(median)은 이 수열을 정렬했을 때 가운데 오는 값입니다. 예를 들어 {3,1,5,4,2}를 정렬했을 때 가운데 오는 값은 3이지요. 수열의 길이가 짝수일 때는 가운데 있는 두 값 중 보다 작은 것을 수열의 중간 값이라고 정의하도록 합시다. 
한 수열의 중간 값은 수열에 새로운 수가 추가될 때마다 바뀔 수 있습니다. 텅 빈 수열에서 시작패서 각 수가 추가될 때마다 중간 값을 계산하는 프로그램을 작성하네요. 예를 들어 3,1,5,4,2 순서대로 숫자가 추가될 경우 수열의 중간 값은 3,1,3,3,3 순서로 변화합니다.

```c
void push_heap(vector<int>& heap,int newValue){
    heap.push_back(newValue);
    int idx = heap.size() -1;
    while(idx >0 && heap[(idx-1)/2] > heap[idx]){
        swap(heap[(idx-1)/2]>heap[idx]);
        idx = (idx-1)/2;
    }
}


int runningMedian(int n,int a,int b){
    vector<int> heap;

}
```