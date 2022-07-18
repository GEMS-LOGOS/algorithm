# Chapter 19. 큐와 스택, 데크

1. 큐 (queue)
    - 한쪽 끝에서 자료를 넣고 반대 쪽 끝에서 자료를 꺼낼 수 있는 선형 자료 구조. (FIFO, First In First Out)
    - 놀이공원 및 음식점에서 선 줄, 할 일의 목록 등
2. 스택 (stack)
    - 한쪽 끝에서만 자료를 넣고 뺄 수 있는 선형 자료 구조. (LIFO, Last in First Out)
    - 함수 호출 문맥 관리
3. 데크 (dequeue)
    - 양쪽 끝에서 자료들을 넣고 뺄 수 있는 섢평 자료 구조.

* 대부분의 표준 라이브러리에서 제공하는 자료 구조이기 때문에 이들을 직접 구현할 이유는 없다. 소중한 대회 시간을 이를 구현하는데 낭비하지 말 것.

- 조세푸스 문제
```c
#include <stdio.h>
#include <queue>

#define FORI(MAX) for(int i=0;i<MAX;i++)

using namespace std;
void josephus(int n,int k){
    queue<int> people;
    FORI(n){
        people.push(n+1);
    }    
    while(people.size()!=2){
        people.pop();
        FORI(k){
            people.push(people.front());
            people.pop();
        }
    }
    cout << people.front() << " " << people.back() << endl;

}
```

- Indexed Priority Queue
```c
#include <stdio.h>
#define FORI(MAX) for(int i=0;i<MAX;i++)

struct Element {
	int index, value;
	bool operator<(const Element &o) const {
		return value == o.value ? index < o.index : value < o.value;
	}
};


struct IndexedPriorityQueue {
	Element *arr; // 모든 원소의 배열
	int *position; // 모든 원소의 배열에서 index 값으로 해당 원소를 찾기 위한 배열
	int size;

	IndexedPriorityQueue(int max) : size(0)
	{
		arr = new Element[max + 1];
		position = new int[max + 1];
	}

	~IndexedPriorityQueue()
	{
		delete[] arr;
		delete[] position;
	}

	void push(int i, int value) // i 인덱스를 가진 원소 생성, position[i]에는 i라는 인덱스를 가진 원소의 arr 상 위치를 나타냄.
	{
		++size;
		arr[size] = { i, value };
		position[i] = size;
		up(size);
	}

	void change(int i, int value) // i 인덱스에 있는 원소 내부 value를 입력받은 value로 바꾼 후 이전 값보다 작아질 경우 down, 아닐 경우 up
	{
		int cur = position[i];
		int k = arr[cur].value;
		arr[cur].value = value;
		if (k < value)
			down(cur);
		else
			up(cur);
	}

	void up(int cur)
	{
		while (cur > 1)
		{
			if (arr[cur].value >= arr[cur >> 1].value)
				break;
			swap(arr[cur], arr[cur >> 1]);
			position[arr[cur].i] = cur;
			cur >>= 1;
		}
		position[arr[cur].i] = cur;
	}

	void down(int cur)
	{
		while ((cur << 1) <= size)
		{
			int max;
			if ((cur << 1) == size || (arr[cur << 1].value < arr[(cur << 1) + 1].value))
				max = (cur << 1);
			else
				max = (cur << 1) + 1;
			if (arr[cur].value <= arr[max].value)
				break;
			swap(arr[cur], arr[max]);
			position[arr[cur].i] = cur;
			cur = max;
		}
		position[arr[cur].i] = cur;
	}

	int pop()
	{
		int ret = arr[1].i;
		arr[1] = arr[size--];
		position[arr[1].i] = 1;
		down(1);
		return ret;
	}

	void del(int i)
	{
		int cur = position[i];
		int k = arr[cur].value;
		arr[cur] = arr[size--];
		position[arr[cur].i] = cur;
		if (arr[cur].value > k)
			down(cur);
		else
			up(cur);
	}
};


```

- [H2335] 생존열차

```c
#define CUSTOM 1000000

#define CUST_IN_TRAIN   10000

#define TRAIN_N 10

#define JOB_N   1000

#define MAX_SIZE CUST_IN_TRAIN
 
struct CUST {
    int idx;
    int id;
    int job;
    int point;
    int trainIdx;
    int heapIdxMax;
    int heapIdxMin;
 
    int n;
 
}custPool[CUSTOM];
 
int custCnt;
int custNum;
int custInTrain;
int jobN;
int trainN;

struct NODE {
    int id;
    NODE* prev;
    NODE* next;
}nodePool[CUSTOM + (JOB_N*2)];
 
int nodeCnt;
 
struct SET {
    NODE* head;
    NODE* tail;
 
}setPool[JOB_N];
 
struct HeapMax{
 
    CUST* heap[MAX_SIZE];
    int heapSize = 0;
 
    bool comp(CUST* a, CUST* b)
    {
        if (a->point > b->point) return true;
        else if (a->point == b->point && a->id < b->id) return true;
        else return false;
    }
 
    void up(int current)
    {
        while (current > 0 && comp(heap[current], heap[(current - 1) / 2]))
        {
            CUST* temp = heap[(current - 1) / 2];
            heap[(current - 1) / 2] = heap[current];
            heap[current] = temp;
            heap[(current - 1) / 2]->heapIdxMax = (current - 1) / 2;
            heap[current]->heapIdxMax = current;
            current = (current - 1) / 2;
        }
        // pq heappush up 부분
 
    }
    int heapPush(CUST* value)
    {
        heap[heapSize] = value;
        heap[heapSize]->heapIdxMax = heapSize;
 
        int current = heapSize;
 
        up(current);
 
        heapSize = heapSize + 1;
 
        return 1;
    }
 
    void down(int current)
    {
        while (current * 2 + 1 < heapSize)
        {
            int child;
            if (current * 2 + 2 == heapSize)
            {
                child = current * 2 + 1;
            }
            else
            {
                child = comp(heap[current * 2 + 1], heap[current * 2 + 2]) ? current * 2 + 1 : current * 2 + 2;
            }
 
            if (comp(heap[current], heap[child]))
            {
                break;
            }
 
            CUST* temp = heap[current];
            heap[current] = heap[child];
            heap[child] = temp;
            heap[current]->heapIdxMax = current;
            heap[child]->heapIdxMax = child;
 
            current = child;
        }
 
    }
 
    int heapPop(CUST *&value)
    {
        if (heapSize <= 0)
        {
            return -1;
        }
 
        *&value = heap[0];
        heapSize = heapSize - 1;
 
        heap[0] = heap[heapSize];
        heap[0]->heapIdxMax = 0;
 
        int current = 0;
        down(current);
          
        return 1;
    }
 
    void update(int current)
    {
        up(current);
        down(current);
    }
 
    void del(int current)
    {
        heap[current] = heap[--heapSize];
        heap[current]->heapIdxMax = current;
 
        up(current);
        down(current);
 
    }
 
}PQMax[TRAIN_N];
 
struct HeapMin {
 
    CUST* heap[MAX_SIZE];
    int heapSize = 0;
 
    bool comp(CUST* a, CUST* b)
    {
        if (a->point < b->point) return true;
        else if (a->point == b->point && a->id > b->id) return true;
        else return false;
    }
 
    void up(int current)
    {
        while (current > 0 && comp(heap[current], heap[(current - 1) / 2]))
        {
            CUST* temp = heap[(current - 1) / 2];
            heap[(current - 1) / 2] = heap[current];
            heap[current] = temp;
            heap[(current - 1) / 2]->heapIdxMin = (current - 1) / 2;
            heap[current]->heapIdxMin = current;
            current = (current - 1) / 2;
        }
 
    }
    int heapPush(CUST* value)
    {
        heap[heapSize] = value;
        heap[heapSize]->heapIdxMin = heapSize;
 
        int current = heapSize;
 
        up(current);
 
        heapSize = heapSize + 1;
 
        return 1;
    }
 
    void down(int current)
    {
        while (current * 2 + 1 < heapSize)
        {
            int child;
            if (current * 2 + 2 == heapSize)
            {
                child = current * 2 + 1;
            }
            else
            {
                child = comp(heap[current * 2 + 1], heap[current * 2 + 2]) ? current * 2 + 1 : current * 2 + 2;
            }
 
            if (comp(heap[current], heap[child]))
            {
                break;
            }
 
            CUST* temp = heap[current];
            heap[current] = heap[child];
            heap[child] = temp;
            heap[current]->heapIdxMin = current;
            heap[child]->heapIdxMin = child;
 
            current = child;
        }
 
    }
 
    int heapPop(CUST *&value)
    {
        if (heapSize <= 0)
        {
            return -1;
        }
 
        *&value = heap[0];
        heapSize = heapSize - 1;
 
        heap[0] = heap[heapSize];
        heap[0]->heapIdxMin = 0;
 
        int current = 0;
        down(current);
 
        return 1;
    }
 
    void update(int current)
    {
        up(current);
        down(current);
    }
 
    void del(int current)
    {
        heap[current] = heap[--heapSize];
        heap[current]->heapIdxMin = current;
 
        up(current);
        down(current);
 
    }
 
}PQMin[TRAIN_N];
 
void addNode(NODE* n, NODE* t)
{
    n->prev = t->prev;
    t->prev = n;
    n->next = n->prev->next;
    n->prev->next = n;
}
 
void init(int N, int M, int J, int mPoint[], int mJobID[])
{
    custCnt = 0;
    nodeCnt = 0;
    custNum = N;
    custInTrain = M;
    jobN = J;
    trainN = custNum / custInTrain;
 
    for (register int i = 0; i < TRAIN_N; i++)
    {
        PQMax[i].heapSize = 0;
        PQMin[i].heapSize = 0;
    }
 
    for (register int i = 0; i < JOB_N; i++)
    {
        setPool[i].head = &nodePool[nodeCnt++];
        setPool[i].tail = &nodePool[nodeCnt++];
        setPool[i].head->next = setPool[i].tail;
        setPool[i].head->prev = 0;
        setPool[i].tail->prev = setPool[i].head;
        setPool[i].tail->next = 0;
    }
 
    for (register int i = 0; i < custNum; i++)
    {
        CUST* node = &custPool[i];
        node->id = i;
        node->idx = i;
        node->job = mJobID[i];
        node->point = mPoint[i];
        node->trainIdx = i / custInTrain;
 
        PQMax[node->trainIdx].heapPush(node);
        PQMin[node->trainIdx].heapPush(node);
 
        NODE* n = &nodePool[nodeCnt++];
        n->id = node->id;
        addNode(n, setPool[node->job].tail);
    }
}
 
void destroy()
{
 
}
 
int update(int mID, int mPoint)
{
    CUST* node = &custPool[mID];
 
    node->point += mPoint;
 
    PQMax[node->trainIdx].update(node->heapIdxMax);
    PQMin[node->trainIdx].update(node->heapIdxMin);
 
    return node->point;
}
 
int updateByJob(int mJobID, int mPoint)
{
    int totalPoint = 0;
 
    for (register NODE* iter = setPool[mJobID].head->next; iter != setPool[mJobID].tail; iter = iter->next)
    {
        CUST* node = &custPool[iter->id];
        node->point += mPoint;
 
        PQMax[node->trainIdx].update(node->heapIdxMax);
        PQMin[node->trainIdx].update(node->heapIdxMin);
 
        totalPoint += node->point;
    }
    return totalPoint;
}
 
int move(int mNum)
{
    int totalPoint = 0;
    int max[10][5];
    int min[10][5];
    int maxCnt[10];
    int minCnt[10];
 
    for (register int i = 0; i < 10; i++)
    {
        maxCnt[i] = 0;
        minCnt[i] = 0;
    }
 
    for (register int i = 0; i < trainN; i++)
    {
        if (i > 0)
        {
            for (register int j = 0; j < mNum; j++)
            {
                CUST* node;
                PQMax[i].heapPop(node);
                PQMin[i].del(node->heapIdxMin);
                max[i][maxCnt[i]++] = node->id;
                node->trainIdx = i - 1;
                totalPoint += node->point;
            }
        }
 
        if (i < trainN-1)
        {
            for (register int j = 0; j < mNum; j++)
            {
                CUST* node;
                PQMin[i].heapPop(node);
                PQMax[i].del(node->heapIdxMax);
 
                min[i][minCnt[i]++] = node->id;
                node->trainIdx = i + 1;
                totalPoint += node->point;
            }
        }
 
    }
 
    for (register int i = 1; i < trainN; i++)
    {
        for (register int j = 0; j < mNum; j++)
        {
            CUST* node = &custPool[max[i][j]];
            PQMax[i - 1].heapPush(node);
            PQMin[i - 1].heapPush(node);
 
        }
    }
 
    for (register int i = 0; i < trainN-1; i++)
    {
        for (register int j = 0; j < mNum; j++)
        {
            CUST* node = &custPool[min[i][j]];
            PQMax[i + 1].heapPush(node);
            PQMin[i + 1].heapPush(node);
        }
    }
 
 
    return totalPoint;
}
```