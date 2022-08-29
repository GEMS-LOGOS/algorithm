# 29 그래프의 너비 우선 탐색

---

- 그래프 탐색 알고리즘
    - 깊이 우선 탐색
    - **너비 우선 탐색**
- 너비 우선 탐색
    - 개념 : 시작점에서 가까운 정점부터 순서대로 방문하는 탐색 알고리즘
    - 구현
        
        ```cpp
        vector<vector<int>> adj;
        
        void bfs (int start, vector<int>& distance, vector<int>& parent) {
        	distance = vector<int>(adj.size(), -1);
        	parent = vector<int>(adj.size(), -1);
        
        	queue<int> q;
        	distance[start] = 0;
        	parent[start] = start;
        
        	q.push(start);
        	while(!q.empty()) {
        		int here = q.front();
        		q.pop();
        		for (int i=0; i<adj[here].size(); ++i) {
        			int there = adj[here][i];
        			if (distance[there] == -1) {
        				q.push(there)
        				distance[there] = distance[here] + 1
        				parent[there] = here;
        			}
        		}
        	}
        }
        
        vector<int> shortestPath (int v, const vector<int>& parent) {
        	vector<int> path(1, v);
        	while(parent[v] != v) {
        		v = parent[v];
        		path.push_back(v);
        	}
        	reverse(path.begine(), path.end());
        	return path;
        }
        ```
        
    - 시간복잡도
        - 인접리스트 : O(V+E)
        - 인접행렬 : O(V**2)
- 너비 우선 탐색 스패닝 트리
    - 너비 우선 탐색에서 새 정점을 발견하는 데 사용한 간선들을 모은 트리
    - 시작점으로부터 다른 모든 정점까지의 최단 경로를 찾을 수 있음
- 최단 경로 전략
    - 너비 우선 탐색 개선
        - 양방향 탐색
            - 시작 정점에서 시작하는 정방향 탐색과 목표 정점에서 시작해서 거꾸로 올라오는 역방향 탐색을 동시에 수행하며, 가운데서 둘이 만나면 탐색 종료
        - 깊어지는 탐색
            - 깊이 제한을 정한 후 이 제한보다 짧은 경로가 존재하는지 깊이 우선 탐색으로 확인, 성공 시 반환, 실패 시 깊이 제한을 늘려 다시 시도
    - 탐색 방법 선택 (상태 공간에서 최단 경로를 찾는 경우)
        1. 너비 우선 탐색 최우선 고려
            1. 직관적, 구현 간단
            2. (깊이가 너무 깊은 경우) 메모리 사용량 확인 필요
        2. 탐색의 깊이가 정해져 있고, 메모리와 시간이 부족한 경우, 양방향 탐색 고려
            1. 역방향으로 움직이기 쉬워야함
        3. 두 탐색이 너무 메모리를 많이 사용하거나 느린 경우, 깊어지는 탐색 사용 고려
    - 상태 객체 구현 주의사항
        - 상태에 대한 여러 연산을 가능한 효율적으로 구현
        - 가능한 적은 메모리 사용

---

- 문제 : Sorting Game (SORTGAME, 중)
- 문제 : 어린이날 (CHILDRENDAY, 상)
- 문제 : 하노이의 탑 (HANOI4B, 중)
    - 풀이
        - 그래프 너비 우선 탐색
        - 상태 표현 → 정수 (각 원반의 위치를 정수 하나로 표현)
        - 양방향 탐색
    - 코드
        
        ```python
        from queue import Queue
        
        max_disc = 12
        
        def get(state, index):
            return (state >> (index * 2)) & 3
        
        def set(state, index, value):
            return (state & ~(3 << (index * 2))) | (value << (index * 2))
        
        def sgn(x):
            if not x:
                return 0
        
            if x > 0:
                return 1
            else:
                return -1
        
        def incr(x):
            if x < 0:
                return x - 1
            else:
                return x + 1
        
        def bfs(discs, begin, end):
            if begin == end:
                return 0
        
            q = Queue()
            q.put(begin)
        
            c = [-1 for i in range(1 << (max_disc * 2))]
            c[begin] = 0
        
            while not q.empty():
                here = q.get()
        
                top = [-1, -1, -1, -1]
                for i in range(discs - 1, -1, -1):
                    top[get(here, i)] = i
        
                for i in range(4):
                    if top[i] != -1:
                        for j in range(4):
                            if i != j and (top[j] == -1 or top[j] > top[i]):
                                there = set(here, top[i], j)
        
                                if c[there] != -1:
                                    continue
        
                                c[there] = c[here] + 1
        
                                if there == end:
                                    return c[there]
        
                                q.put(there)
            return -1
        
        def bidir(discs, begin, end):
            if begin == end:
                return 0
        
            q = Queue()
            q.put(begin)
            q.put(end)
        
            c = [0 for i in range(1 << (max_disc * 2))]
            c[begin] = 1
            c[end] = -1
        
            while not q.empty():
                here = q.get()
        
                top = [-1, -1, -1, -1]
                for i in range(discs - 1, -1, -1):
                    top[get(here, i)] = i
        
                for i in range(4):
                    if top[i] != -1:
                        for j in range(4):
                            if i != j and (top[j] == -1 or top[j] > top[i]):
                                there = set(here, top[i], j)
        
                                if c[there] == 0:
                                    c[there] = incr(c[here])
                                    q.put(there)
                                elif sgn(c[there]) != sgn(c[here]):
                                    rs = abs(c[there])
                                    rs2 = abs(c[here])
                                    rs3 = rs + rs2 - 1
                                    return rs3
        
            return -1
        
        if __name__ == "__main__":
            result1 = bidir(5, 0b1010011100, 0b1111111111)
            result2 = bidir(3, 0b100010, 0b111111)
            result3 = bidir(10, 0b11110000100101101011, 0b11111111111111111111)
            print(result1)
            print(result2)
            print(result3)
        ```
        

---
