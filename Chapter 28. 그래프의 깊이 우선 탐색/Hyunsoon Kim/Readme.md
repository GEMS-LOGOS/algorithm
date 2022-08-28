# 28 그래프의 깊이 우선 탐색

---

- 그래프 탐색 알고리즘
    - 깊이 우선 탐색
    - 너비 우선 탐색
- 깊이 우선 탐색 (depth-first search, DFS)
    - 재귀호출을 활용하여 구현 가능
    - 연결되지 않은 그래프 적용 시 주의
    - 시간복잡도
        - 인접 리스트 : O(V+E)
        - 인접 행렬 : O(V**2)
    - 활용
        - 두 정점이 서로 연결되어 있는지 확인
        - 연결된 부분집합의 개수 확인
        - 위상 정렬
- 오일러 서킷
    - 그래프의 모든 간선을 정확히 한 번씩 지나서 시작점으로 돌아오는 경로를 찾는 문제
    - 깊이 우선 탐색을 활용하여 구현
    - 오일러 트레일
        - 시작점과 끝점이 다른 오일러 서킷
        - 오일러 서킷을 활용하여 해결
- 깊이 우선 탐색 스패닝 트리
    - 깊이 우선 탐색 시 사용된 간선의 모습
    - 간선 종류 : 트리 간선, 순방향 간선, 역방향 간선, 교차 간선
    - 활용
        - 위상 정렬 정당성 증명
        - 사이클 존재 여부 확인
- 활용
    - 절단점 찾기 알고리즘
    - 다리 찾기
    - 강결합 컴포넌트 분리

---

- 문제 : 고대어 사전 (DICTIONARY, 하)
- 문제 : 단어 제한 끝말잇기 (WORDCHAIN, 하)
- 문제 : 감시 카메라 설치 (GALLERY, 중)
    - 풀이
        - 그래프의 지배 집합 찾기 문제(NP-완전문제)
            
            → 사이클 X → 루트 없는 트리 → 트리의 최소 지배 집합 문제
            
    - 코드
        
        ```python
        from enum import Enum
        class Status(Enum):
            UNWATCHED = 0
            WATCHED = 1
            INSTALLED = 2
        
        installed = visited = adj = None
        
        def dfs(v):
            global adj, visited, installed
        
            visited[v] = True
            children = [0, 0, 0]
        
            if len(adj) > v:
                for cv in adj[v]:
                    if not visited[cv]:
                        children[dfs(cv)] += 1
        
            if children[Status.UNWATCHED.value]:
                installed += 1
                return Status.INSTALLED.value
            if children[Status.INSTALLED.value]:
                return Status.WATCHED.value
            return Status.UNWATCHED.value
        
        def install_camera(size, array):
            global adj, visited, installed
        
            adj = array
            visited = [False] * size
            installed = 0
        
            for v in range(size):
                if not visited[v] and dfs(v) == Status.UNWATCHED.value:
                    installed += 1
            
            return installed
        
        def test():
            test_cases = [
                { "size": 6, "array": [[1, 4], [0, 2, 3], [1, 5], [1], [0], [2]] },
                { "size": 4, "array": [[1], [0], [3], [2]] },
                { "size": 1000, "array": [[0], [1]] }
            ]
            
            for test_case in test_cases:
                print(install_camera(test_case["size"], test_case["array"]))
        
        test()
        ```
        
- 문제 : 회의실 배정 (MEETINGROOM, 상)

---
