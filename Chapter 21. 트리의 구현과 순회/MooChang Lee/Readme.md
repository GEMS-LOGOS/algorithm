# 21 트리의 구현과 순회

---

# 트리
- 하나의 상위 개념에서 가지를 쳐서 뻗어나가는 구조
- 주로 탐색형 자료 구조로 쓰임 -> 이진 검색 트리
# 정의 및 용어
## 구성 요소
- 노드(node)와 간선(edge) 
- 연결된 노드중 상위 노드 : 부모
- 부모 노드가 같은 두 노드 : 형제
- 부모 포함 상위 노드 : 선조
- 자식 노드 포함 모든 하위 노드 : 자손
- 최상위 하나의 노드 : 루트
- 자손이 없는 노드 : 리프
### 속성
- 어떤 노드에 닿기까지 루트로부터의 길이 : 깊이
- 제일 긴 길이 : 높이
- 어떤 노드 t와 그 자손들로 구성된 트리 : 서브트리
### 표현
- 구조체/객체 표현하여 포인터로 연결하기
```c
struct TreeNode {
    string label;
    TreeNode* parent;
    vector<TreeNode*> children;
}
```
# 트리의 순회
## 순회하여 모든 값 출력
```c
void printLabels(TreeNode* root){
    cout << root-> label << endl;
    for(int i=0;i<root->children.size();i++)
        printLabels(root->children(i));
}
```
## 순회 활용 트리의 높이 계산
```c
void height(TreeNode* root){
    int h=0;
    for(int i=0;i<root->children.size();i++)
        h = max(h,1+height(root->children(i)));
    return h;
}
```
---
