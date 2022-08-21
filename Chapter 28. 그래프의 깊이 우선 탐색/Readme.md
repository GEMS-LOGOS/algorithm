## 그래프의 깊이 우선 탐색

- 그래프의 탐색 알고리즘
  - 트리의 순회와 같이 그래프의 모든 정점들을 특정한 순서에 따라 방문하는 알고리즘들.
  - 널리 사용되는 알고리즘 두 개
    - 너비 우선 탐색 (BFS)
    - 깊이 우선 탐색 (DFS)
- 깊이 우선 탐색 (Depth-first search, DFS)
  - 그래프의 모든 정점을 발견하는 가장 단순하고 고전적인 방법
  - 현재 정점과 인접한 간선들을 하나씩 검사하다가, 아직 방문하지 않은 정점으로 향하는 간선이 있다면 그 간선을 무조건 따라가는 것.
  - 갈 곳이 없는 막힌 정점에 도달하면 포기하고, 마지막에 따라왔던 간선을 따라 뒤로 돌아감.
  - 마지막에 따라왔던 간선을 따라 뒤로 돌아가는 방법을 재귀 호출 활용하여 구현하면 간편.
  - 시간 복잡도는 O(|V| + |E|)

### 감시 카메라 설치
- 루트 없는 트리
  - 어떤 연결된 그래프가 루트 없는 트리인지 확인하기 위해서는 다음 속성 중 하나라도 성립하는지 확인하면 됨.
    - 정확히 V-1개의 간선이 있음
    - 사이클이 존재하지 않음
    - 두 정점 사이를 연결하는 단순 경로가 정확히 하나 있음
```c
#define FORI(num) for(int i=0;i<num;i++)
const int NOT_WATCH=0;
const int WATCH=1;
const int INSTALL=2;
const int MAX_V=1000;
typedef struct Gallery{
    vector<int> connected;
    int status;
    int visited=false;
}gallery[MAX_V];
int camera=0;
int dfs(int start){
    gallery[start].visited=true;
    FORI(gallery[start]){

    }
}


```