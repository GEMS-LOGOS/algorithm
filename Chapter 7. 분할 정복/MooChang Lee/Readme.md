# Chapter 7. 분할 정복
## 1. 정의
 - 하나의 문제를 작은 여러개의 문제로 쪼갠 후 재귀적으로 각 문제를 해결한 후 이를 다시 합쳐서 원래 문제를 해결하는 방식
  . 분할된 문제는 기존 문제와 성격이 동일하며 단순히 입력 크기만 작아진 것이다.
  . 분할된 문제는 독립적이다
## 2. 예시 
### 2.1 Median algorithm
 - Input : A list of numbers S; an integer k
 - Output : The kth smallest element of S
 - 리스트 S를 v라는 숫자에 의해 3 가지 카테고리로 쪼갤 수 있음.
  . SL : v보다 작은 숫자들의 집합
  . Sv : v랑 같은 숫자들의 집합 (duplicates)
  . SR : v보다 큰 숫자들의 집합
 - 일반화한다면 다음과 같음
  . selection(S,k) = selection(SL,k) if ( k < SL )
  . selection(S,k) = v if (SL < k <= SL + Sv)
  . selection(S,k) = selection(SR,k-SL-Sv) if ( k > SL + Sv )

### 2.2 Matrix multiplication (by Volker Strassen)
 - 자세한 내용은 https://ko.wikipedia.org/wiki/%EC%8A%88%ED%8A%B8%EB%9D%BC%EC%84%BC_%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98 참고