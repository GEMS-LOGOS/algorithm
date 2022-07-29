# 22 이진 검색 트리

---

- 이진 검색 트리
    - 이진 트리 : 최대 2개의 자식 노드를 가짐 (left, right)
    - 이진 탐색 : 탐색 시간 O(lgN)
    - 모든 서브 트리에서 left child < parent < right child 성립 (정렬됨)
- 이진 검색 트리 vs 정렬된 배열
    - 순회, 검색 유사
    - 추가, 삭제 유리
        - 삭제 : 합치기 연산으로 구현
    - 추가 연산 예시
        - x보다 작은 원소 개수 찾기
        - k번째 원소 찾기
- 시간 복잡도 분석과 균형 잡힌 이진 검색 트리
    - 모든 연산이 루트에서 한 단계씩 트리를 내려가며 재귀 호출을 통해 수행
    - 최대 재귀 호출 횟수 → 트리의 높이 h
    - 모든 연산의 시간 복잡도 → O(h)
    - 트리가 기울어진 경우 → 이진 검색 트리 X, 연결 리스트 O
    - 균형 잡힌 이진 검색 트리 (balanced binary search tree)
        - 트리 높이 O(lgN) 유지
        - 예시) 레드 블랙 트리
- 균형 잡힌 이진 검색 트리 직접 구현하기 : 트립
    - 트립(treap) : AVL 트리나 레드 블랙 트리 등 보다 구현이 간단한 이진 검색 트리
    - 난수를 활용하여 트리 생성
        - 난수를 활용하여 우선순위 생성 → 우선순위 순서대로 트리 추가
        - 트리의 높이의 기대치가 O(N) 보다 낮음 → O(lgN)
    - 코드
        
        ```python
        from random import seed
        from random import randint
        
        seed(1)
        
        class Node:
            def __init__(self, value):
                self.value = value
                self.size = 1
                self.priority = randint(0, 100000)
                self.left = None
                self.right = None
        
            def set_left(self, left):
                self.left = left
                self.cal_size()
        
            def set_right(self, right):
                self.right = right
                self.cal_size()
        
            def cal_size(self):
                self.size = 1
                if (self.left != None):
                    self.size += self.left.size
                if (self.right != None):
                    self.size += self.right.size
        
        def split(root, value):
            if root == None:
                return None, None
            
            if root.value < value:
                left, right = split(root.right, value)
                root.set_right(left)
                return root, right
            else:
                left, right = split(root.left, value)
                root.set_left(right)
                return left, root
        
        def insert(root, node):
            if root == None:
                return node
                
            if root.priority < node.priority:
                left, right = split(root, node.value)
                node.set_left(left)
                node.set_right(right)
                return node
        
            if node.value < root.value:
                root.set_left(insert(root.left, node))
            else:
                root.set_right(insert(root.right, node))
            return root
        
        def merge(left, right):
            if left == None:
                return right
            if right == None:
                return left
            
            if left.priority < right.priority:
                right.set_left(merge(left, right.left))
                return right
            else:
                left.set_right(merge(right, left.right))
                return left
        
        def erase(root, value):
            if root == None:
                return root
        
            if root.value == value:
                new_root = merge(root.left, root.right)
                del root
                return new_root
        
            if value < root.value:
                root.set_left(erase(root.left, value))
            else:
                root.set_right(erase(root.right, value))
            return root
        
        def kth(root, k):
            left_size = 0
            if root.left != None:
                left_size = root.left.size
            
            if k <= left_size:
                return kth(root.left, k)
            elif k == left_size + 1:
                return root
            else:
                return kth(root.right, k - left_size - 1)
        
        def count_less_than(root, value):
            if root == None:
                return 0
        
            if root.value >= value:
                return count_less_than(root.left, value)
            else:
                left_size = 0 if root.left == None else root.left.size
                return left_size + 1 + count_less_than(root.right, value)
        
        if __name__ == '__main__':
            root = None
            values = [1,2,3,4,5,6,7,8,9,10]
        
            for value in values:
                root = insert(root, Node(value))
            print('insert test completed')
        
            # erase(root, 4)
            # print('erase test completed')
        
            # k = kth(root, 4)
            # print('kth test completed')
        
            # count = count_less_than(root, 4)
            # print('count_less_than test completed')
        ```
        

---

- 문제 : 너드인가, 너드가 아닌가? 2 (NERD2, 중)
- 문제 : 삽입 정렬 뒤집기 (INSERTION, 중)
    - 풀이
        - k번째 원소 찾기 + 빠른 삭제 → 이진 검색 트리
    - 코드
        
        ```python
        from random import seed
        from random import randint
        
        seed(1)
        
        class Node:
            def __init__(self, value):
                self.value = value
                self.size = 1
                self.priority = randint(0, 100000)
                self.left = None
                self.right = None
        
            def set_left(self, left):
                self.left = left
                self.cal_size()
        
            def set_right(self, right):
                self.right = right
                self.cal_size()
        
            def cal_size(self):
                self.size = 1
                if (self.left != None):
                    self.size += self.left.size
                if (self.right != None):
                    self.size += self.right.size
        
        def split(root, value):
            if root == None:
                return None, None
            
            if root.value < value:
                left, right = split(root.right, value)
                root.set_right(left)
                return root, right
            else:
                left, right = split(root.left, value)
                root.set_left(right)
                return left, root
        
        def insert(root, node):
            if root == None:
                return node
                
            if root.priority < node.priority:
                left, right = split(root, node.value)
                node.set_left(left)
                node.set_right(right)
                return node
        
            if node.value < root.value:
                root.set_left(insert(root.left, node))
            else:
                root.set_right(insert(root.right, node))
            return root
        
        def merge(left, right):
            if left == None:
                return right
            if right == None:
                return left
            
            if left.priority < right.priority:
                right.set_left(merge(left, right.left))
                return right
            else:
                left.set_right(merge(right, left.right))
                return left
        
        def erase(root, value):
            if root == None:
                return root
        
            if root.value == value:
                new_root = merge(root.left, root.right)
                del root
                return new_root
        
            if value < root.value:
                root.set_left(erase(root.left, value))
            else:
                root.set_right(erase(root.right, value))
            return root
        
        def kth(root, k):
            left_size = 0
            if root.left != None:
                left_size = root.left.size
            
            if k <= left_size:
                return kth(root.left, k)
            elif k == left_size + 1:
                return root
            else:
                return kth(root.right, k - left_size - 1)
        
        def count_less_than(root, value):
            if root == None:
                return 0
        
            if root.value >= value:
                return count_less_than(root.left, value)
            else:
                left_size = 0 if root.left == None else root.left.size
                return left_size + 1 + count_less_than(root.right, value)
        
        def get_original_array(move_array):
            original_array_size = len(move_array)
            bst = get_binary_search_tree(original_array_size)
        
            original_array = []
            for i in range(original_array_size):
                # calculate k
                array_size = original_array_size - i
                move_size = move_array[original_array_size - 1 - i]
                k = array_size - move_size
        
                # find and remove kth node
                kth_node = kth(bst, k)
                kth_value = kth_node.value
                bst = erase(bst, kth_value)
        
                original_array.insert(0, kth_value)
            return original_array
        
        def get_binary_search_tree(tree_size):
            # make "binary search tree" using "treap"
            root = None
            for number in range(tree_size):
                root = insert(root, Node(number + 1))
            return root
        
        if __name__ == '__main__':
            test_case = [[0, 1, 1, 2, 3], [0, 1, 2, 3]]
            for move_array in test_case:
                original_array = get_original_array(move_array)
                for number in original_array:
                    print(number, end=' ')
                print()
        ```
        

---
