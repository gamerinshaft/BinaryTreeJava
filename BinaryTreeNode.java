class BinaryTreeNode {
  Object data;
  BinaryTreeNode up;
  BinaryTreeNode down;

  BinaryTreeNode( Object obj, BinaryTreeNode u, BinaryTreeNode d ) {
    data = obj;
    up = u;
    down = d;
  }
}