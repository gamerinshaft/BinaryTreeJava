import java.io.*;
import java.util.*;
import java.awt.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.*;
class BinaryTree {
  BinaryTreeNode root;
  String map;

  BinaryTree(Object data) {
    root = new BinaryTreeNode(data, null, null);
  }

//---------------------------[showTree]------------------------------------------

  String showTree(){
    map = root.data + "(root)";
    mappingTree("",0,root);
    return map;
  }

  void mappingTree(String route, int count, BinaryTreeNode tree){
    if(tree.up !=null){
      mappingTreeUp(route,count,tree.up);
      mappingTree(route + "1",count + 1, tree.up);
    }
    if(tree.down !=null){
      mappingTreeDown(route,count,tree.down);
      mappingTree(route + "0",count + 1, tree.down);
    }
  }

  void mappingTreeUp(String route, int count, BinaryTreeNode tree){
    if(!tree.data.equals("")){
      map += "\n";
      for(int i = 0;i< count;i++){
        char ch1 = route.charAt(i);
        if(ch1=='1'){
          map += "|      ";
        }else{
          map += "       ";
        }
      }
      map += "├──────" + tree.data;
    }
  }

  void mappingTreeDown(String route, int count, BinaryTreeNode tree){
    if(!tree.data.equals("")){
      map += "\n";
      for(int i = 0; i < count; i++){
        char ch1 = route.charAt(i);
        if(ch1=='1'){
          map += "|      ";
        }else{
          map += "       ";
        }
      }
      map += "└──────" + tree.data;
    }
  }
//--------------------------[insertTree]--------------------------------------

  void insertTree(){
    BinaryTreeNode pastTree;
    pastTree = root;
    insertUp("", pastTree);
    insertDown("",pastTree);
  }

  void insertUp(String bread, BinaryTreeNode pastTree){
    BufferedReader input = new BufferedReader (new InputStreamReader (System.in));
    try{
      if(pastTree.up == null || pastTree.up.data.equals("")){
        BinaryTreeNode newUpTree;
        System.out.println("parent:" + bread + pastTree.data);
        System.out.println("up");
        Object up = input.readLine( );
        newUpTree = new BinaryTreeNode(up, null, null);
        pastTree.up = newUpTree;
      }else{
        bread += pastTree.data + " > ";
        insertUp(bread, pastTree.up);
        insertDown(bread, pastTree.up);
      }
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  void insertDown(String bread, BinaryTreeNode pastTree){
    BufferedReader input = new BufferedReader (new InputStreamReader (System.in));
    try{
      if(pastTree.down == null || pastTree.down.data.equals("")){
        BinaryTreeNode newDownTree;
        System.out.println("parent:" + bread + pastTree.data);
        System.out.println("down");
        Object down = input.readLine( );
        newDownTree = new BinaryTreeNode(down, null, null);
        pastTree.down = newDownTree;
      }else{
        bread += pastTree.data + " > ";
        insertUp(bread, pastTree.down);
        insertDown(bread, pastTree.down);
      }
    }catch(IOException e){
      e.printStackTrace();
    }
  }
//---------------------------------[DeleteTree]-------------------------------------------
//------------------------------------[main]----------------------------------------------

  public static void main(String args[]) {
    boolean isContinue = true;
    String value;
    BinaryTree tree = new BinaryTree(args[0]);
    System.out.println("-------------------------------------------------------------------");
    System.out.println("show : ツリーを表示");
    System.out.println("add  : ノード追加モード");
    System.out.println("del  : そのノード以下のブランチを削除");
    System.out.println("end  : プログラムを終了");
    System.out.println("※add画面で値を入力せずにEnterを押すと、ブランチはそこでとまります。");
    System.out.println("-------------------------------------------------------------------");

    while(isContinue){
      try{
        BufferedReader input;
        System.out.println("option(add/show/del/end)");
        input = new BufferedReader (new InputStreamReader (System.in));
        value = input.readLine( );
        if(value.equals("end")){
          isContinue = false;
        }else if(value.equals("") || value.indexOf(" ") >= 0 || value.indexOf("　") >= 0){
          System.out.println("空白文字は含まないで下さい");
        }else if(value.equals("show")){
          System.out.println("treeを表示します");
          System.out.println("-------------------------------------------------------------------");
          System.out.println(tree.showTree());
          System.out.println("-------------------------------------------------------------------");
        }else if(value.equals("add")){
          System.out.println("treeを追加します");
          tree.insertTree();
        }else if(value.equals("del")){
          System.out.println("削除するノードをrootから / で区切って入力してください");
          System.out.println("-------------------------------------------------------------------");
          System.out.println(tree.showTree());
          System.out.println("-------------------------------------------------------------------");
        }
      }catch(IOException e){
        e.printStackTrace();
      }
    }
  }

}