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
        boolean isContinue = true;
        while(isContinue){
          System.out.println("parent:" + bread + pastTree.data);
          System.out.println("up");
          Object up = input.readLine( );
          if(!up.equals("")){
            if(pastTree.down != null && up.equals(pastTree.down.data + "")){
              System.out.println("もう一方と同じ値は入力できません");
            }else{
              isContinue = false;
            }
          }else{
            isContinue = false;
          }
          newUpTree = new BinaryTreeNode(up, null, null);
          pastTree.up = newUpTree;
        }
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
        boolean isContinue = true;
        while(isContinue){
          System.out.println("parent:" + bread + pastTree.data);
          System.out.println("down");
          Object down = input.readLine( );
          if(!down.equals("")){
            if(pastTree.up != null && down.equals(pastTree.up.data + "")){
              System.out.println("もう一方と同じ値は入力できません");
            }else{
              isContinue = false;
            }
          }else{
            isContinue = false;
          }
          newDownTree = new BinaryTreeNode(down, null, null);
          pastTree.down = newDownTree;
        }
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
  void deleteNode(){
    System.out.println("削除するノードをrootから / で区切って入力してください");
    try{
      BufferedReader input;
      input = new BufferedReader (new InputStreamReader (System.in));
      String value = input.readLine( );
      String[] valueAry = value.split("/");
      BinaryTreeNode tree = root;
      BinaryTreeNode pareTree = root;
      String state = "";
      if(valueAry.length == 1){
        System.out.println("rootは削除できません。");
        System.out.println("-------------------------------------------------------------------");
      }else{
        for(int i = 1; i < valueAry.length; i++){
          if(tree.up.data.equals(valueAry[i])){
            pareTree = tree;
            tree = tree.up;
            state = "up";
          }else if(tree.down.data.equals(valueAry[i])){
            pareTree = tree;
            tree = tree.down;
            state = "down";
          }
        }
        if(state == "down"){
          pareTree.down = null;
        }else if(state == "up"){
          pareTree.up = null;
        }
        System.out.println("削除しました。");
        System.out.println("-------------------------------------------------------------------");
      }
    }catch(IOException e){
      e.printStackTrace();
    }
  }

//--------------------------------------[ADD]---------------------------------------------
  void addNodeDirect(){
    System.out.println("追加したいノードの位置をrootから / で区切って入力してください");
    try{
      BufferedReader input;
      input = new BufferedReader (new InputStreamReader (System.in));
      String value = input.readLine( );
      String[] hoge = value.split("/");
      BinaryTreeNode tree = root;
      BinaryTreeNode pareTree = root;
      String state = "";
      if(hoge.length == 1){
        System.out.println("rootは削除できません。");
        System.out.println("-------------------------------------------------------------------");
      }else{
        for(int i = 1; i < hoge.length; i++){
          if(tree.up.data.equals(hoge[i])){
            pareTree = tree;
            tree = tree.up;
            state = "up";
          }else if(tree.down.data.equals(hoge[i])){
            pareTree = tree;
            tree = tree.down;
            state = "down";
          }
        }
        if(state == "down"){
          pareTree.down = null;
        }else if(state == "up"){
          pareTree.up = null;
        }
        System.out.println("削除しました。");
        System.out.println("-------------------------------------------------------------------");
      }
    }catch(IOException e){
      e.printStackTrace();
    }
  }

//------------------------------------[main]----------------------------------------------

  public static void main(String args[]) {
    boolean isContinue = true;
    String value;
    BinaryTree tree = new BinaryTree(args[0]);
    System.out.println("-------------------------------------------------------------------");
    System.out.println("show   : ツリーを表示");
    System.out.println("add    : ノード追加モード");
    System.out.println("ADD : 直接追加モード");
    System.out.println("del    : そのノード以下のブランチを削除");
    System.out.println("end    : プログラムを終了");
    System.out.println("※add画面で値を入力せずにEnterを押すと、ブランチはそこでとまります。");
    System.out.println("-------------------------------------------------------------------");

    while(isContinue){
      try{
        BufferedReader input;
        System.out.println("option(add/ADD/show/del/end)");
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
          System.out.println("-------------------------------------------------------------------");
          System.out.println(tree.showTree());
          System.out.println("-------------------------------------------------------------------");
          tree.deleteNode();
        }else if(value.equals("ADD")){
          System.out.println("-------------------------------------------------------------------");
          System.out.println("ADDするよ");
          System.out.println("-------------------------------------------------------------------");
        }
      }catch(IOException e){
        e.printStackTrace();
      }
    }
  }

}