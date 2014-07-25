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


  String showTree(){
    String s;
    s = root.data + "";
    s += "\n├──────【" + root.up.data + "】";
    s += "\n│";
    s += "\n└──────【" + root.down.data + "】";
    return s;
  }

  void insertTree(){
    BinaryTreeNode pastTree;
    pastTree = root;
    if(pastTree.up == null){
      insertUp(pastTree);
    }
    if(pastTree.down == null){
      insertDown(pastTree);
    }
  }
  void insertUp(BinaryTreeNode pastTree){
    BufferedReader input = new BufferedReader (new InputStreamReader (System.in));
    try{
      BinaryTreeNode newUpTree;
      System.out.println("parent:" + pastTree.data);
      System.out.println("up");
      Object up = input.readLine( );
      newUpTree = new BinaryTreeNode(up, null, null);
      pastTree.up = newUpTree;
    }catch(IOException e){
      e.printStackTrace();
    }
  }
  void insertDown(BinaryTreeNode pastTree){
    BufferedReader input = new BufferedReader (new InputStreamReader (System.in));
    try{
      BinaryTreeNode newDownTree;
      System.out.println("parent:" + pastTree.data);
      System.out.println("down");
      Object down = input.readLine( );
      newDownTree = new BinaryTreeNode(down, null, null);
      pastTree.down = newDownTree;
    }catch(IOException e){
      e.printStackTrace();
    }
  }
//----------------------------------------------------------------------
  // void insert(Object data) {
  //   newTree = new Cell(data);
  //   Cell pastCell = header;
  //   while(isNextCellExist(pastCell)){
  //     if(isNextCellDataInsertData(pastCell, data)){
  //       if(isNextCellLast(pastCell)){
  //         break;
  //       }else{
  //         pastCell.next = pastCell.next.next;
  //       }
  //     }
  //     pastCell = pastCell.next;
  //   }
  //   pastCell.next = newCell;
  // }

  // private boolean isNextCellExist(Cell cell){
  //   return cell.next != null;
  // }

  // private boolean isNextCellDataInsertData(Cell cell, Object data){
  //   return cell.next.data.equals(data);
  // }

  // private boolean isNextCellLast(Cell cell){
  //   return cell.next.next == null;
  // }
//----------------------------------------------------------------------



  public static void main(String args[]) {
    boolean isContinue = true;
    String value;
    BinaryTree tree = new BinaryTree(args[0]);

    System.out.println("終了したい場合は end と入力して下さい。");

    while(isContinue){
      try{
        BufferedReader input;
        System.out.println("option(add/show)");
        input = new BufferedReader (new InputStreamReader (System.in));
        value = input.readLine( );
        if(value.equals("end")){
          isContinue = false;
        }else if(value.equals("") || value.indexOf(" ") >= 0 || value.indexOf("　") >= 0){
          System.out.println("空白文字は含まないで下さい");
        }else if(value.equals("show")){
          System.out.println("treeを表示します");
          System.out.println(tree.showTree());
        }else if(value.equals("add")){
          System.out.println("treeを追加します");
          tree.insertTree();
        }
      }catch(IOException e){
        e.printStackTrace();
      }
    }
  }

}