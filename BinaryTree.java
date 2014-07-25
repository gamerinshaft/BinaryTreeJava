import java.io.*;
import java.util.*;
import java.awt.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.*;
class BinaryTree {
  BinaryTreeNode root;

  BinaryTree(Object data) {
    root = new BinaryTreeNode(data, null, null);
  }

  void printLn(){
    System.out.println(root.data);
  }

  public static void main(String args[]) {
    BinaryTree tree = new BinaryTree(args[0]);
    tree.printLn();
  }

}