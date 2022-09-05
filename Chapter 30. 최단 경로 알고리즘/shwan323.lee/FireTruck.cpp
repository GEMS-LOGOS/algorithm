	void Dijkstra(int start_station){
    priority_queue<pair<int,int>, vector<pair<int,int>>, greater<pair<int,int>>> pq;
    pq.push({0,start_station});
    dist[start_station] = 0;

    int now_place, cost, next_place, next_cost;
    while(!pq.empty()){
      now_place = pq.top().second;
      cost = pq.top().first;
      pq.pop();

      if(dist[now_place] < cost) 
        continue;

      for(int i=0; i<adj[now_place].size() ; ++i){
        next_place = adj[now_place][i].second;

        if(is_station[next_place])  //다음 장소가 소방소라면 더 이상 탐색 x 
          continue;

        next_cost = cost + adj[now_place][i].first;

        if(next_cost < dist[next_place]){
          pq.push({next_cost, next_place});
          dist[next_place] = next_cost;
        }
      }
    }
}
 
