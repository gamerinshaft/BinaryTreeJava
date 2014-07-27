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
      System.out.println("sita");
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
    System.out.println("ノードの位置をrootから / で区切って指定すると、そのノード以下の各末端にノードを追加する事が出来ます。");
    try{
      BufferedReader input;
      input = new BufferedReader (new InputStreamReader (System.in));
      String value = input.readLine( );
      String[] valueAry = value.split("/");
      String bread = "";
      BinaryTreeNode tree = root;
      BinaryTreeNode pareTree = root;
      String state = "";
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
        bread += pareTree.data + " > ";
      }
      insertUp(bread, tree);
      insertDown(bread, tree);
    }catch(IOException e){
      e.printStackTrace();
    }
  }

//--------------------------------------[cp]---------------------------------------------
  void copyNode(){
    System.out.println("コピーしたいノードの位置をrootから / で区切って指定して下さい。");
    try{
      BufferedReader input;
      input = new BufferedReader (new InputStreamReader (System.in));
      String value1 = input.readLine( );
      String[] valueAry1 = value1.split("/");
      String oribread = "";
      String cpbread = "";
      BinaryTreeNode originTree = root;
      BinaryTreeNode copyTree = root;
      String state = "";

      for(int i = 1; i < valueAry1.length; i++){
        if(originTree.up.data.equals(valueAry1[i])){
          originTree = originTree.up;
        }else if(originTree.down.data.equals(valueAry1[i])){
          originTree = originTree.down;
        }
      }

      System.out.println("コピーノード先の位置をrootから / で区切って指定して下さい。");
      String value2 = input.readLine( );
      String[] valueAry2 = value2.split("/");
      System.out.println("上につけたいなら up 下につけたいなら down を入力して下さい");
      state = input.readLine( );

      for(int i = 1; i < valueAry2.length; i++){
        System.out.println(valueAry2[i]);
      }
      for(int i = 1; i < valueAry2.length; i++){
        System.out.println(i + "回目");
        if(copyTree.up != null){
          if(copyTree.up.data.equals(valueAry2[i])){
            copyTree = copyTree.up;
          }
        }
        if(copyTree.down != null){
          if(copyTree.down.data.equals(valueAry2[i])){
            copyTree = copyTree.down;
          }
        }
      }

      if(state.equals("up")){
        if(copyTree.down != null){
          if(!originTree.data.equals(copyTree.down.data)){
            copyTree.up = new BinaryTreeNode("dummy",null,null);
            copyTreeUp(copyTree.up, originTree);
            copyTree.up = copyTree.up.up;
          }else{
           System.out.println("コピー元の値がコピー先のもう一方のノードと同じ値です。");
          }
        }else{
          copyTree.up = new BinaryTreeNode("dummy",null,null);
          copyTreeUp(copyTree.up, originTree);
          copyTree.up = copyTree.up.up;
        }
      }else if(state.equals("down")){
        if(copyTree.up != null){
          if(!originTree.data.equals(copyTree.up.data)){
            copyTree.down = new BinaryTreeNode("dummy",null,null);
            copyTreeDown(copyTree.down, originTree);
            copyTree.down = copyTree.down.down;
          }else{
            System.out.println("コピー元の値がコピー先のもう一方のノードと同じ値です。");
          }
        }else{
          copyTree.down = new BinaryTreeNode("dummy",null,null);
          copyTreeDown(copyTree.down, originTree);
          copyTree.down = copyTree.down.down;
        }
      }else{
        System.out.println("入力された文字が up, down ではないです");
      }

    }catch(IOException e){
      e.printStackTrace();
    }
  }

  void copyTreeUp(BinaryTreeNode copyTree, BinaryTreeNode originTree){
    copyTree.up = new BinaryTreeNode(originTree.data, null, null);
    if(originTree.up != null){
      if(originTree.up.data.equals("dummy")){
      }else{
        copyTreeUp(copyTree.up, originTree.up);
      }
    }
    if(originTree.down != null){
      if(originTree.down.data.equals("dummy")){
      }else{
        copyTreeDown(copyTree.up, originTree.down);
      }
    }
  }

  void copyTreeDown(BinaryTreeNode copyTree, BinaryTreeNode originTree){
    copyTree.down = new BinaryTreeNode(originTree.data, null, null);
    if(originTree.up != null){
      if(originTree.up.data.equals("dummy")){
      }else{
        copyTreeUp(copyTree.down, originTree.up);
      }
    }
    if(originTree.down != null){
      if(originTree.down.data.equals("dummy")){
      }else{
        copyTreeDown(copyTree.down, originTree.down);
      }
    }
  }


//------------------------------------[main]----------------------------------------------

  public static void main(String args[]) {
    boolean isContinue = true;
    String value;
    BinaryTree tree = new BinaryTree(args[0]);
    System.out.println("-------------------------------------------------------------------");
    System.out.println("add    : ノード追加モード");
    System.out.println("ADD    : 直接追加モード");
    System.out.println("show   : ツリーを表示");
    System.out.println("cp     : そのノード以下のブランチをコピー");
    System.out.println("del    : そのノード以下のブランチを削除");
    System.out.println("end    : プログラムを終了");
    System.out.println("※add画面で値を入力せずにEnterを押すと、ブランチはそこでとまります。");
    System.out.println("-------------------------------------------------------------------");

    while(isContinue){
      try{
        BufferedReader input;
        System.out.println("option(add/ADD/show/cp/del/end)");
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
          tree.addNodeDirect();
          System.out.println("-------------------------------------------------------------------");
        }else if(value.equals("cp")){
          System.out.println("-------------------------------------------------------------------");
          System.out.println(tree.showTree());
          System.out.println("-------------------------------------------------------------------");
          tree.copyNode();
        }
      }catch(IOException e){
        e.printStackTrace();
      }
    }
  }

}