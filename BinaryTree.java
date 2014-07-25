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

  String printTree(){
    String s = root.data + "";
    return s;
  }

  public static void main(String args[]) {
    boolean isContinue = true;
    String value;
    BinaryTree tree = new BinaryTree(args[0]);

    System.out.println("終了したい場合は end と入力して下さい。");

    while(isContinue){
      try{
        BufferedReader input;
        System.out.println("input:");
        input = new BufferedReader (new InputStreamReader (System.in));
        value = input.readLine( );
        if(value.equals("end")){
          isContinue = false;
        }else if(value.equals("") || value.indexOf(" ") >= 0 || value.indexOf("　") >= 0){
          System.out.println("空白文字は含まないで下さい");
        }else{
          // s.insert(value);
          System.out.println(tree.printTree());
        }
      }catch(IOException e){
        e.printStackTrace();
      }
    }
  }

}