# 23 우선순위 큐와 힙

---

- 우선순위 큐
    - 우선순위가 가장 높은 자료를 가장 먼저 꺼내는 자료 구조
    - 대표적으로 힙을 활용하여 구현
- 힙
    - 최대 원소를 가능한 빠르게 찾을 수 있는 방법
    - 조건
        - 부모 노드가 가진 원소는 항상 자식 노드가 가진 원소 이상이어야 한다.
        - 마지막 레벨을 제외한 모든 레벨에 노드가 꽉 차 있어야 한다.
        - 마지막 레벨에 노드가 있을 때는 항상 가장 왼쪽부터 순서대로 채워져 있어야 한다.
- 배열을 활용한 힙의 구현
    - 간단하게 구현 가능
    - 삽입
        - 마지막 위치에 새로운 원소를 추가
        - 트리 상에서 부모와 비교해가면서 위치 결정
            - 부모 보다 크면 교체
            - 부모 보다 작을 때까지 진행
    - 최대 원소 꺼내기
        - 루트에서 최대 원소 꺼내기
        - 마지막 원소를 루트로 이동
        - 트리 상에서 왼쪽과 오른쪽 자식과 비교해가면서 위치 결정
            - 자식보다 클 때까지 진행
            - 왼쪽, 오른쪽 자식 중 큰 자식과 변경
    - 관련된 추가 연산
        - 힙 생성 연산
        - 힙 정렬 * : O(NlgN)
        - 힙에 있는 원소 중 하나 증가 시키기

---

- 문제 : 변화하는 중간 값 (RUNNINGMEDIAN, 하)
    - 풀이
        - 중앙값을 기준으로 최대힙과 최소힙을 사용
    - 코드
        
        ```python
        from queue import PriorityQueue
        
        def get_median_array(array):
            medians = []
        
            half_front = PriorityQueue() # max_heap, use negative number
            half_back = PriorityQueue() # min_heap
        
            for number in array:
                # put number
                if half_front.qsize() == half_back.qsize():
                    half_front.put(number * -1)
                else:
                    half_back.put(number)
        
                # check and change number
                if not half_front.empty() and not half_back.empty() and not (half_front.queue[0] * -1 < half_back.queue[0]):
                    half_front_max_number = half_front.get() * (-1)
                    half_back_min_number = half_back.get()
                    half_front.put(half_back_min_number * -1)
                    half_back.put(half_front_max_number)
                
                median = half_front.queue[0] * -1
                medians.append(median)
        
            return medians
        
        if __name__ == '__main__':
            test_case = [[3,1,5,4,2]]
            for array in test_case:
                median_array = get_median_array(array)
                for median in median_array:
                    print(median, end=' ')
                print()
        ```
        

---
