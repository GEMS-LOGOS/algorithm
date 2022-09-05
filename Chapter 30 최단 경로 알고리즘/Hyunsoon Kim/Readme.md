# 30 최단 경로 알고리즘

---

- 최단 경로 문제
    - 주어진 그래프에서 주어진 두 정점을 연결하는 가장 짧은 경로 길이 찾는 문제
    - 그래프 응용 문제 중 가장 유용하고 널리 사용
    - 가중치
        - X → 너비 우선 탐색
        - O → (아래 알고리즘)
    - 이슈
        - 음수 간선
            - 음수 사이클 존재 가능 → 음의 무한대로 발산 가능 → 최단 경로 문제 정의 X
        - 단일 시작점과 모든 쌍 알고리즘
        - 방향 그래프와 무방향 그래프
- 다익스트라의 최단 경로 알고리즘
    - 단일 시작점 최단 경로 알고리즘
    - 우선순위 큐를 사용하는 너비 우선 탐색
    - 음수 간선이 있는 그래프에서 동작 X
    - 구현
        - 너비 우선 탐색
        - 우선순위 큐
        - C++ 우선순위 큐 사용 : 첫번째 원소 기준 정렬, “-” 적용 후 작을수록 높은 우선순위 부여
        - 최단 거리 갱신 시 큐에 중복 추가 + 향후 크기 비교 후 무시
        
        ```cpp
        int V;
        vector<pair<int,int>> adj[MAX_V];
        vector<int> dijkstra(int src) {
        	vector<int> dist(V, INF);
        	dist[src] = 0;
        	priority_queue<pair<int,int>> pq;
        	pq.push(make_pair(0, src));
        	while(!pq.empty()) {
        		int cost = -pq.top().first;
        		int here = pq.top().second;
        		pq.pop();
        		if(dist[here]<cost) continue;
        		for(int i=0; i<adj[here].size(); ++i){
        			int there = adj[here][i].first;
        			int nextDist = cost + adj[here][i].second;
        			if(dist[there]>nextDist) {
        				dist[there] = nextDist;
        				pq.push(make_pair(-nextDist,there);
        			}
        		}
        	}
        	return dist;
        }
        ```
        
    - 시간 복잡도 : O(|E|lg|V|)
    - 실제 경로 : 너비 우선 탐색과 같이 스패닝 트리 활용하여 계산
- 벨만-포드의 최단 경로 알고리즘
    - 단일 시작점 최단 경로 알고리즘
    - 음수 간선 그래프 적용 가능
        - 음수 사이클 존재 시 확인 가능
    - 개념
        - 각 정점까지 가는 최단 거리 상한을 적당히 예측한 뒤 예측 값과 실제 값 사이의 오차를 반복적으로 줄여가는 방식으로 동작
    - 구현
        
        ```cpp
        int V;
        vector<pair<int,int>> adj[MAX_V];
        vector<int> bellamnFord(int src){
        	vector<int> upper(V,INF);
        	upper[src]=0;
        	bool updated;
        	for(int iter=0; iter<V; ++iter){
        		updated = false;
        		for(int here=0; here<V; ++here)
        			for(int i=0; i<adj[here].size(); ++i){
        				int there = adj[here][i].first;
        				int cost = adj[here][i].second;
        				if(upper[there]>upper[here] + cost){
        					upper[there] = upper[here] + cost;
        					updated = true;
        				}
        			}
        		if(!updated) break;
        	}
        	if(updated) upper.clear();
        	return upper;
        }
        ```
        
    - 시간 복잡도 : O(|V||E|)
    - 실제 경로 : 너비 우선 탐색과 같이 스패닝 트리 활용하여 계산
- 플로이드의 모든 쌍 최단 거리 알고리즘
    - 모든 쌍 간의 최단 거리 알고리즘
    - 개념 : 경유점, 경로가 특정점을 경유하는 경우와 그렇지 않은 경우를 나눠서 최단 거리 계산
    - 구현
        
        ```cpp
        int V;
        int adj[MAX_V][MAX_V];
        int via[MAX_V][MAX_V];
        void floyd2(){
        	for(int i=0; i<V; ++i) adj[i][j]=0;
        	memset(via,-1,sizeof(via));
        	for(int k=0; k<V; ++k)
        		for(int i=0; i<V; ++i)
        			for(int j=0; j<V; ++j)
        				if(adj[i][j]>adj[i][k]+adj[k][j]){
        					via[i][j]=k;
        					adj[i][j]=adj[i][k]+adj[k][j];
        				}
        }
        void reconstruct(int u, int v, vector<int>& path){
        	if(via[u][v]==-1){
        		path.push_back(u);
        		if(u!=v) path.push_back(v);
        	}
        	else{
        		int w = via[u][v];
        		reconstruct(u,w,path);
        		path.pop_back();
        		reconstruct(w,v,path);
        	}
        }
        ```
        
    - 시간 복잡도 : O(|V|**3)
    - 공간 복잡도 : O(|V|**2)
    - 실제 경로 계산 : 두 정점 사이의 최단 경로 갱신 시 저장 후 활용
    - 활용 : 가중치 없는 그래프에서 각 정점 간의 도달 가능성 여부 계산

---

- 문제 : 신호 라우팅 (ROUTING, 하)
- 문제 : 소방차 (FIRETRUCKS, 중)
    - 풀이
        - ~~다익스트라~~
            - ~~불난 곳에서 시작~~
            - ~~소방서에서 시작~~
        - ~~플로이드~~
        - 다익스트라 + 트릭 (시작 노드 추가 및 연결)
    - 구현
        
        ```python
        from queue import PriorityQueue
        
        def dijkstra(src):
            adj = (
                [
                    [{"node": 1, "edge": 3}, {"node": 5, "edge": 9}],
                    [{"node": 0, "edge": 3}, {"node": 2, "edge": 6}, {"node": 7, "edge": 3}],
                    [{"node": 1, "edge": 6}, {"node": 3, "edge": 4}, {"node": 4, "edge": 2}],
                    [{"node": 8, "edge": 0}, {"node": 2, "edge": 4}, {"node": 4, "edge": 7}],
                    [
                        {"node": 2, "edge": 2},
                        {"node": 3, "edge": 7},
                        {"node": 5, "edge": 5},
                        {"node": 6, "edge": 1},
                    ],
                    [
                        {"node": 8, "edge": 0},
                        {"node": 0, "edge": 9},
                        {"node": 7, "edge": 5},
                        {"node": 4, "edge": 5},
                        {"node": 6, "edge": 3},
                    ],
                    [{"node": 5, "edge": 3}, {"node": 7, "edge": 3}, {"node": 4, "edge": 1}],
                    [{"node": 1, "edge": 3}, {"node": 5, "edge": 5}, {"node": 6, "edge": 3}],
                    [{"node": 3, "edge": 0}, {"node": 5, "edge": 0}],
                ],
            )
        
            dist = [999 for i in range(9)]
            dist[src] = 0
            que = PriorityQueue()
            que.put([0, src])
        
            while not que.empty():
                q = que.get()
                cost = -q[0]
                here = q[1]
                if dist[here] < cost:
                    continue
                for i in adj[0][here]:
                    there = i["node"]
                    nextDist = i["edge"] + cost
                    if dist[there] > nextDist:
                        dist[there] = nextDist
                        que.put([-nextDist, there])
            return dist
        
        if __name__ == "__main__":
            dist = dijkstra(8)
            answer = dist[1] + dist[2] + dist[4]
            print(answer)
        ```
        
- 문제 : 철인 N 종 경기 (NTHLON, 상)
- 문제 : 시간여행 (TIMETRIP, 중)
- 문제 : 음주 운전 단속 (DRUNKEN, 중)
- 문제 : 선거 공약 (PROMISES, 중)

---
