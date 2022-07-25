# 21 트리의 구현과 순회

---

- 트리의 사용 목적
    - 현실 세계의 개념을 추상화해 표현하는 자료 구조 (계층적 구조를 갖는 자료들을 표현)
    - 탐색형 자료 구조 ex) 이진 검색 트리
- 트리의 구성 요소 및 속성
    - 노드, 간선 / 부모, 형제, 선조, 자손 노드 / 루트, 리프 노드
    - 트리의 깊이, 높이
- 트리의 재귀적 속성 : 서브트리
- 트리의 표현 : 구조체/객체 표현, 포인터 연결
- 트리의 순회
    - 재귀적 속성 사용
    - 트리의 높이 계산

---

- 문제: 트리 순회 순서 변경 (TRAVERSAL, 하)
    - 풀이 : 트리의 재귀적 속성을 활용하여 서브트리를 구하고 활용
    - 코드
        
        ```python
        def printPostOrder(preorder, inorder):
        
            if len(preorder) == 0 :
                return
            
            root = preorder[0]
            root_index_in_inorder = inorder.index(root)
        
            left_subtree_preorder = preorder[1:root_index_in_inorder+1]
            right_subtree_preorder = preorder[root_index_in_inorder+1:]
        
            left_subtree_inorder = inorder[:root_index_in_inorder]
            right_subtree_inorder = inorder[root_index_in_inorder+1:]
        
            ### Post Order
            # left
            printPostOrder(left_subtree_preorder, left_subtree_inorder)
        
            # right
            printPostOrder(right_subtree_preorder, right_subtree_inorder)
        
            # root
            print(root)
        
        preorder = [27, 16, 9, 12, 54, 36, 72]
        inorder = [9, 12, 16, 27, 36, 54, 72]
        printPostOrder(preorder, inorder)
        ```
        
- 문제 : 요새 (FORTRESS, 중)

---
