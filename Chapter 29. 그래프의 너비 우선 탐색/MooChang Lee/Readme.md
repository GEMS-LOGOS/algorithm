## 그래프의 너비 우선 탐색

- 그래프의 너비 탐색 알고리즘
  - 시작점에서 제일 가까운 정점부터 순서대로 방문하는 알고리즘
  - 순서
    - 시작점
    - 처음 보는 정점을 발견 시 방문 예정으로 기록 후 별도의 위치에 저장
    - 인접한 정점을 모두 검사 후 저장한 목록에서 다음 정점을 꺼내서 방문
    - 반복
  - 시간 복잡도는 O(|V| + |E|)
  - 발견과 방문이 같지 않음
  - 모든 정점은 다음 순서를 거침
    - 발견되지 않은 상태
    - 발견되었지만 방문되지 않은 상태
    - 발견되고 방문된 상태

### 하노이의 탑
```c
const int MAX_DISCS = 12;
int get(int state,int index){
    return (state>> (index*2)) & 3;
}
int set(int state,int index, int value){
    return (state & ~(3 << (index * 2)>>)) | (value << (index * 2));
}

int c[1<<(MAX_DISCS*2)];

int bfs(int discs, int begin, int end){
    if(begin == end) return 0;
    queue<int> q;
    memset(c,-1,sizeof(c));
    q.push(begin);
    c[begin]=0;
    while(!q.empty()){
        int here = q.front();
        q.pop();

        int top[4]={-1,-1,-1,-1};
        for(int i=discs-1;i>=0;--i)
            top[get(here,i)] = i;
        for(int i=0;i<4;++i)
            if(top[i] != -1)
                for(int j=0;j<4;++j)
                    if(i!=j && (top[j] == -1 || top[j]>top[i])){
                        int there = set(here,top[i],j);
                        if(c[there]!=-1) continue;
                        c[there] = c[here] +1;
                        if(there == end) return c[there];
                        q.push(there);
                    }
    }
    return -1;
}
```