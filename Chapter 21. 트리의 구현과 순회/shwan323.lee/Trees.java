class Solution {
  boolean checkBST(Node root) {
      return check(root, 0, 10000);
  }

  boolean check(Node root, int min, int max){
      if(root.data <= min || root.data >= max)
          return false;

      boolean ret = true;
      if(root.left != null){
          ret = ret && check(root.left, min,  root.data);
      }

      if(root.right != null){
          ret = ret && check(root.right, root.data, max);    
      }

      return ret;
  }
}
