public class Gallery {
	
	// 노드 수
	static int G;
	// 간선 수
	static int H;
	// 인접리스트
	static ArrayList<Integer>[] adjList;
	static int[] discovered;
	static int count;
	static int installed;
	static final int UNWATCHED = 0;
	static final int WATCHED = 1;
	static final int INSTALLED = 2;

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		System.setIn(new FileInputStream("D:\\sample_input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine().trim());
		
		for(int tc=1; tc<=T; tc++){
			StringTokenizer st = new StringTokenizer(br.readLine());
			G = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			
			adjList = new ArrayList[G];
			discovered = new int[G];
			
			for(int i=0; i<G; i++){
				adjList[i] = new ArrayList<Integer>();
			}
			
			for(int i=0; i<H; i++){
				
				st = new StringTokenizer(br.readLine());
				int sv = Integer.parseInt(st.nextToken());
				int ev = Integer.parseInt(st.nextToken());
				
				adjList[sv].add(ev);
				adjList[ev].add(sv);
				
			}
			
			count = 1;
			dfsAll();
			
			bw.write(installed + "\n");
			bw.flush();
			
		}
		
		bw.close();

	}
	
	
	public static void dfsAll(){
		installed = 0;
		for(int i = 0; i < G; i++){
			if(discovered[i] == 0 && dfs(i) == UNWATCHED){
				++installed;
			}
		}
	}
	
	public static int dfs(int here){
		
		discovered[here] = count++;
		int[] children = {0,0,0};
		
		for(int i = 0; i < adjList[here].size(); i++){
			int there = adjList[here].get(i);
				if(discovered[there] == 0){
					++children[dfs(there)];
				}
		}
		
		// 자손 노드 중 감시되지 않는 노드가 있을 경우 카메라를 설치한다.
		if(children[UNWATCHED] > 0){
			++installed;
			return INSTALLED;
		}
		
		// 자손 노드 중 카메라가 설치된 노드가 있을 경우 설치할 필요가 없다.
		if(children[INSTALLED] > 0){
			return WATCHED;
		}
		return UNWATCHED;
	}
}
