## 검색 트리(search tree)
- 자료를 일정한 상태로 정렬해 둔 채로 저장
- 원소의 추가 삭제 및 특정 원소의 존재 여부 확인 가능
- 가장 흔하게 사용되는 것은 이진 검색 트리(binary search tree) 및 그 변종들
## 이진 트리
- 각 노드가 왼쪽과 오른쪽, 최대 두 개의 자식 노드만을 가질 수 있는 트리
- 이를 이용해, 왼쪽에는 해당 노드보다 작은 값의 노드를, 오른쪽에는 해당 노드보다 큰 값의 노드를 배정
### 순회
- 중위 순회하면 크기 순으로 정렬된 원소의 목록을 얻을 수 있음.
### 검색
- 이진 탐색과 비슷한 속도로 자료를 찾을 수 있음
### 조작
- 원소 추가는 쉽다
- 원소 삭제의 경우
  - '합치기' 연산 구현
    - t 노드의 하위 노드를 합친 후 t 노드를 필두로 하는 서브 노드와 바꿔치기
## 레드-블랙 트리
- Each node is either red or black. (노드는 레드 혹은 블랙 중의 하나이다.)
- The root is black. (루트 노드는 블랙이다.)
- All leaves (NIL) are black. (모든 리프 노드(NIL)들은 블랙이다.)
- If a node is red, then both its children are black. (레드 노드의 자식 노드들은 언제나 블랙이다.)
- Every path from a given node to any of its descendant NIL nodes contains the same number of black nodes. (어떤 노드로부터 시작되어 리프 노드에 도달하는 모든 경로에는 모두 같은 개수의 블랙 노드가 있다.)
### 구현
```c
// C 언어에서의 RBTNode의 구조
typedef struct tagRBTNode {
	struct tagRBTNode* Parent;
	struct tagRBTNode* Left;
	struct tagRBTNode* Right;

	enum {RED, BLACK} Color;
	int Data
}RBTNode;

extern RBTNode* Nil;

void RBT_RotateRight(RBTNode** Root, RBTNode* Parent) {

	RBTNode* LeftChild = Parent->Left;
	Parent->Left = LeftChild->Right; // 왼쪽 자식 노드의 오른쪽 자식 노드를 부모 노드의 왼쪽 자식에 등록

	if (LeftChild->Right != Nil) {
		LeftChild->Right->Parent = Parent; // 등록한 왼쪽 자식의 부모 노드를 바꾼다.
	}

	LeftChild->Parent = Parent->Parent; // 부모노드 교체

	if (Parent->Parent == NULL) { // 부모가 NULL 이라면 루트 노드이다. 그러므로 왼쪽 노드를 루트 노드로 한다.
		(*Root) = LeftChild;
	}
	else {
		if (Parent == Parent->Parent->Left) { // 부모노드가 할아버지 노드의 왼쪽 자식이였다면
			Parent->Parent->Left = LeftChild; // 왼쪽 자식으로
		} 
		else { // 오른쪽 자식이였다면
			Parent->Parent->Right = LeftChild; // 오른쪽 자식으로
		}

		// 마무리
		LeftChild->Right = Parent; 
		Parent->Parent = LeftChild;
	}
}

void RBT_RotateLeft(RBTNode** Root, RBTNode* Parent) {

	RBTNode* RightChild = Parent->Right;
	Parent->Right = RightChild->Left; 

	if (RightChild->Left != Nil) {
		RightChild->Left->Parent = Parent; 
	}

	RightChild->Parent = Parent->Parent; 

	if (Parent->Parent == NULL) { 
		(*Root) = RightChild;
	}
	else {
		if (Parent == Parent->Parent->Right) { 
			Parent->Parent->Right = RightChild;
		}
		else {
			Parent->Parent->Left = RightChild;
		}

		RightChild->Left = Parent;
		Parent->Parent = RightChild;
	}
}

void RBT_InsertNodeHelper(RBTNode** Tree, RBTNode* NewNode) {
	if ((*Tree) == NULL) { // 루트 노드가 널이면 새로운 노드가 루트 노드
		(*Tree) = NewNode;
	}

	if ((*Tree)->Data < NewNode->Data) { //새로운 노드의 데이터가 더 크면
		if ((*Tree)->Right == Nil) { // 오른쪽 비어 있으면
			(*Tree)->Right = NewNode; // 삽입
			NewNode->Parent = (*Tree); // 부모 노드가 루트 노드
		}
		else {
			RBT_InsertNodeHelper(&(*Tree)->Right, NewNode); // 오른쪽으로 재귀하여 빈 노드를 찾아 간다. 
		}
	}

	if ((*Tree)->Data > NewNode->Data) { //새로운 노드의 데이터가 더 작으면
		if ((*Tree)->Left == Nil) { // 왼쪽 비어 있으면
			(*Tree)->Left = NewNode; // 삽입
			NewNode->Parent = (*Tree); // 부모 노드가 루트 노드
		}
		else {
			RBT_InsertNodeHelper(&(*Tree)->Left, NewNode); // 오른쪽으로 재귀하여 빈 노드를 찾아 간다. 
		}
	}
}

void RBT_InsertNode(RBTNode** Tree, RBTNode* NewNode) {
	RBT_InsertNodeHelper(Tree, NewNode);

	NewNode->Color = RED;
	NewNode->Left = Nil;
	NewNode->Right = Nil;

	RBT_REbuildAfterInsert(Tree, NewNode); // 인서트 후에 리빌딩을 통하여 레드블랙트리의 규칙을 유지한다.
}

void RBT_RebuildAfterInsert(RBTNode** Root, RBTNode* X) {
	while (X != (*Root) && X->Parent->Color == RED) { // 규칙을 위반하고 있는 동안 계속 반복한다.
		if (X->Parent == X->Parent->Parent->Left) { // 부모노드가 할아버지 노드의 왼쪽 자식인 경우
			RBTNode* Uncle = X->Parent->Parent->Right;
			if (Uncle->Color == RED) { // 1.삼촌 노드가 빨간색인 경우
				X->Parent->Color = BLACK; 
				Uncle->Color = BLACK;
				X->Parent->Parent->Color = RED;
				X = X->Parent->Parent;
			}
			else { // 삼촌 노드가 검은색인 경우
				if (X == X->Parent->Right) { // 오른쪽 자식인 경우
					X = X->Parent;
					RBT_RotateLeft(Root, X); // 왼쪽으로 회전 시킨다.
				}
				X->Parent->Color = BLACK;
				X->Parent->Parent->Color = RED;
				RBT_RotateRight(Root, X->Parent->Parent);
			}
		}
		else { // 부모노드가 할아버지 노드의 오른쪽 자신인 경우는 위의 왼쪽 자식인 경우에서 좌/우만 바꿔준다.
			RBTNode* Uncle = X->Parent->Parent->Left;
			if (Uncle->Color == RED) { // 1.삼촌 노드가 빨간색인 경우
				X->Parent->Color = BLACK;
				Uncle->Color = BLACK;
				X->Parent->Parent->Color = RED;
				X = X->Parent->Parent;
			}
			else { // 삼촌 노드가 검은색인 경우
				if (X == X->Parent->Left) { // 왼쪽 자식인 경우
					X = X->Parent;
					RBT_RotateRight(Root, X); // 오른쪽으로 회전 시킨다.
				}
				X->Parent->Color = BLACK;
				X->Parent->Parent->Color = RED;
				RBT_RotateLeft(Root, X->Parent->Parent);
			}
		}
	}

	(*Root)->Color = BLACK;
}

RBTNode* RBT_SearchNode(RBTNode* Tree, int Target) {
	if (Tree == Nil)
		return NULL;

	if (Tree->Data > Target) {
		return RBT_SearchNode(Tree->Left, Target);
	}
	else if(Tree->Data < Target){
		return RBT_SearchNode(Tree->Right, Target);
	}
	else {
		return Tree;
	}
}

RBTNode* RBT_SearchMinNode(RBTNode* Tree) {
	if (Tree == Nil) {
		return Nil;
	}

	if (Tree->Left == Nil) {
		return Tree;
	}
	else {
		return RBT_SearchMinNode(Tree->Left);
	}
		
}

void RBT_RebuildAfterRemove(RBTNode** Root, RBTNode* Successor) {

	RBTNode* Sibling = NULL;

	while (Successor->Parent != NULL && Successor->Color == BLACK) { // 이중흑색이 루트 까지 가거나 빨간색 노드에게 넘어가면 반복 종료
		
		if (Successor == Successor->Parent->Left) { // 이중흑색 노드가 왼쪽인 경우
			Sibling = Successor->Parent->Right;
			// 1. 형제가 빨간색인 경우
			if (Sibling->Color == RED) {
				Sibling->Color = BLACK;
				Successor->Parent->Color = RED;
				RBT_RotateLeft(Root, Successor->Parent);
			}
			//2. 형제가 검은색인 경우
			else {
				// 2-1. 형제의 양쪽 자식이 모두 검은색인 경우
				if (Sibling->Left->Color == BLACK && Sibling->Right->Color == BLACK) {
					Sibling->Color = RED;
					Successor = Successor->Parent;
				}
				// 2-2. 왼쪽 자식이 빨간색인 경우
				else {
					if (Sibling->Left->Color == RED) {
						Sibling->Left->Color = BLACK;
						Sibling->Color = RED;

						RBT_RotateRight(Root, Sibling);
						Sibling = Successor->Parent->Right;
					}
					// 2-3. 오른쪽 자식이 빨간색인 경우
					Sibling->Color = Successor->Parent->Color;
					Successor->Parent->Color = BLACK;
					Sibling->Right->Color = BLACK;
					RBT_RotateLeft(Root, Successor->Parent);
					Successor = (*Root);
				}
			}
		}
		else { // 이중흑색 노드가 오른쪽 자식일때
			Sibling = Successor->Parent->Left;
			// 1. 형제가 빨간색인 경우
			if (Sibling->Color == RED) {
				Sibling->Color = BLACK;
				Successor->Parent->Color = RED;
				RBT_RotateRight(Root, Successor->Parent);
			}
			//2. 형제가 검은색인 경우
			else {
				// 2-1. 형제의 양쪽 자식이 모두 검은색인 경우
				if (Sibling->Right->Color == BLACK && Sibling->Left->Color == BLACK) {
					Sibling->Color = RED;
					Successor = Successor->Parent;
				}
				// 2-2. 오른쪽 자식이 빨간색인 경우
				else {
					if (Sibling->Right->Color == RED) {
						Sibling->Right->Color = BLACK;
						Sibling->Color = RED;

						RBT_RotateLeft(Root, Sibling);
						Sibling = Successor->Parent->Left;
					}
					// 2-3. 오른쪽 자식이 빨간색인 경우
					Sibling->Color = Successor->Parent->Color;
					Successor->Parent->Color = BLACK;
					Sibling->Right->Color = BLACK;
					RBT_RotateRight(Root, Successor->Parent);
					Successor = (*Root);
				}
			}
		}
	}
}

RBTNode* RBT_RemoveNode(RBTNode** Root, int Data) {
	RBTNode* Removed = NULL;
	RBTNode* Successor = NULL;
	RBTNode* Target = RBT_SearchNode((*Root), Data);

	if (Target == NULL) {
		return NULL;
	}

	if (Target->Left == Nil || Target->Right == Nil) {
		Removed = Target;
	}
	else {
		Removed = RBT_SearchMinNode(Target->Right);
		Target->Data = Removed->Data;
	}

	if (Removed->Left != Nil) {
		Successor = Removed->Left;
	}
	else {
		Successor = Removed->Right;
	}

	Successor->Parent = Removed->Parent;

	if (Removed->Parent == NULL) {
		(*Root) = Successor;
	}
	else {
		if (Removed == Removed->Parent->Left) {
			Removed->Parent->Left = Successor;
		}
		else {
			Removed->Parent->Right = Successor;
		}
	}

	if (Removed->Color == BLACK) // 삭제한 노드가 검은색인 경우 이중흑색이기 때문에 처리를 위해 리빌딩 함수 호출
		RBT_RebuildAfterRemove(Root, Successor);

	return Removed;
}
``` 