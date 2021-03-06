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

//---------------------------[showPlaneTree]------------------------------------------

  String showPlaneTree(){
    map = root.data + "(root)";
    mappingPlaneTree("",0,root);
    return map;
  }

  void mappingPlaneTree(String route, int count, BinaryTreeNode tree){
    if(tree.up !=null){
      mappingTreeUp(route,count,tree.up);
      mappingPlaneTree(route + "1",count + 1, tree.up);
    }
    if(tree.down !=null){
      mappingTreeDown(route,count,tree.down);
      mappingPlaneTree(route + "0",count + 1, tree.down);
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


//-----------------------------------[rename]-------------------------------------------
  void renameNode(){
    System.out.println("ノードの位置をrootから / で区切って指定すると、そのノードのデータを変更する事が出来ます。");
    try{
      BufferedReader input;
      input = new BufferedReader (new InputStreamReader (System.in));
      String value = input.readLine( );
      String[] valueAry = value.split("/");
      String state = "";
      BinaryTreeNode tree = root;
      BinaryTreeNode pareTree = root;
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

      System.out.println(tree.data + "を何に書き換えますか？");
      value = input.readLine( );
      if(tree == pareTree){
         tree.data = value;
      }else{
        if(state.equals("up")){
          if(pareTree.down.data.equals(value)){
            System.out.println("もう一方と同じ値にはできません");
          }else{
            tree.data = value;
            System.out.println("書き換えました");
          }
        }else if(state.equals("down")){
          if(pareTree.up.data.equals(value)){
            System.out.println("もう一方と同じ値にはできません");
          }else{
            tree.data = value;
            System.out.println("書き換えました");
          }
        }
      }


    }catch(IOException e){
      e.printStackTrace();
    }
  }

//------------------------------------[save]----------------------------------------------
  void saveData(){
    try{
      System.out.println("保存する形式を data か　plane かから選んで下さい。");
      BufferedReader input;
      input = new BufferedReader (new InputStreamReader (System.in));
      String value = input.readLine( );
      String data;
      if(value.equals("data")){
        data = showDataTree();
      }else{
        data = showPlaneTree();
      }


      System.out.println("既に存在するファイルを保存先に指定して下さい。");
      value = input.readLine( );
      File file = new File(value);

      if (checkBeforeWritefile(file)){
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        pw.println(data);
        pw.close();
        System.out.println("保存しました。");
        System.out.println("-------------------------------------------------------------------");
      }else{
        System.out.println("ファイルに書き込めません");
      }
    }catch(IOException e){
      System.out.println(e);
    }
  }

  private static boolean checkBeforeWritefile(File file){
    if (file.exists()){
      if (file.isFile() && file.canWrite()){
        return true;
      }
    }

    return false;
  }
//------------------------------------[Data]----------------------------------------------
  String showDataTree(){
    map = "" + root.data;
    mappingDataTree("",0,root);
    return map;
  }

  void mappingDataTree(String route, int count, BinaryTreeNode tree){
    String memo = route;
    if(tree.up !=null){
      route = memo;
      route += "+";
      mappingDataTreeUp(route,count,tree.up);
      mappingDataTree(route,count + 1, tree.up);
    }
    if(tree.down !=null){
      route = memo;
      route += "-";
      mappingDataTreeDown(route,count,tree.down);
      mappingDataTree(route,count + 1, tree.down);
    }
  }

  void mappingDataTreeUp(String route, int count, BinaryTreeNode tree){
    if(!tree.data.equals("")){
      map += "\n";
      map += route + tree.data;
    }
  }

  void mappingDataTreeDown(String route, int count, BinaryTreeNode tree){
    if(!tree.data.equals("")){
      map += "\n";
      map += route + tree.data;
    }
  }
//------------------------------------[load]----------------------------------------------
  void loadData(){
    try{
      BufferedReader br;
      FileReader fr;
      BufferedReader input;
      input = new BufferedReader (new InputStreamReader (System.in));
      System.out.println("読込むファイルを指定して下さい");
      String fileName = input.readLine( );
      fr = new FileReader(fileName);
      br = new BufferedReader(fr);
      String str;
      int beforeLength = -1;
      int length;
      BinaryTreeNode pareTree;
      BinaryTreeNode tree = new BinaryTreeNode("", null, null);
      BinaryTreeNode origin = new BinaryTreeNode(root.data, null, null);
      String state = "root";
      while (br.ready()) {
        str = br.readLine();
        String[] strAry = str.split("");
        length = strAry.length - 1;
        pareTree = origin;
        for(int i = 0; i <= length; i++){
          tree = new BinaryTreeNode("", null, null);
          if(strAry[i].equals("+")){
            if(pareTree.up == null){
              pareTree.up = tree;
            }else{
              pareTree = pareTree.up;
            }
            state = "up";
          }else if(strAry[i].equals("-")){
            if(pareTree.down == null){
              pareTree.down = tree;
            }else{
              pareTree = pareTree.down;
            }
            state = "down";
          }else{

            str = "";
            for(int j = i; j <= length; j++){
              str += strAry[j];
            }
            if(state.equals("root")){
              pareTree.data = str;
              break;
            }else if(state.equals("up")){
              pareTree.up.data = str;
              break;
            }else if(state.equals("down")){
              pareTree.down.data = str;
              break;
            }
          }
        }
        state = "";
      }
      root = origin;
      fr.close();
      System.out.println("ファイルを読込みました");
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
    System.out.println("add    : ノード追加モード");
    System.out.println("ADD    : 直接追加モード");
    System.out.println("rename : 指定したノードのデータを変更");
    System.out.println("show   : ツリーを表示");
    System.out.println("cp     : そのノード以下のブランチをコピー");
    System.out.println("del    : そのノード以下のブランチを削除");
    System.out.println("save   : プログラムをファイルに書き出し");
    System.out.println("load   : プログラムからバイナリツリーを読み込み");
    System.out.println("end    : プログラムを終了");
    System.out.println("※add画面で値を入力せずにEnterを押すと、ブランチはそこでとまります。");
    System.out.println("-------------------------------------------------------------------");

    while(isContinue){
      try{
        BufferedReader input;
        System.out.println("option(add/ADD/rename/show/cp/del/save/load/end)");
        input = new BufferedReader (new InputStreamReader (System.in));
        value = input.readLine( );
        if(value.equals("end")){
          isContinue = false;
        }else if(value.equals("") || value.indexOf(" ") >= 0 || value.indexOf("　") >= 0){
          System.out.println("空白文字は含まないで下さい");
        }else if(value.equals("show")){
          System.out.println("treeを表示します");
          System.out.println("-------------------------------------------------------------------");
          System.out.println(tree.showPlaneTree());
          System.out.println("-------------------------------------------------------------------");
        }else if(value.equals("add")){
          System.out.println("treeを追加します");
          tree.insertTree();
        }else if(value.equals("del")){
          System.out.println("-------------------------------------------------------------------");
          System.out.println(tree.showPlaneTree());
          System.out.println("-------------------------------------------------------------------");
          tree.deleteNode();
        }else if(value.equals("ADD")){
          System.out.println("-------------------------------------------------------------------");
          tree.addNodeDirect();
          System.out.println("-------------------------------------------------------------------");
        }else if(value.equals("cp")){
          System.out.println("-------------------------------------------------------------------");
          System.out.println(tree.showPlaneTree());
          System.out.println("-------------------------------------------------------------------");
          tree.copyNode();
        }else if(value.equals("rename")){
          System.out.println("-------------------------------------------------------------------");
          System.out.println(tree.showPlaneTree());
          System.out.println("-------------------------------------------------------------------");
          tree.renameNode();
        }else if(value.equals("save")){
          System.out.println("-------------------------------------------------------------------");
          System.out.println(tree.showPlaneTree());
          System.out.println("-------------------------------------------------------------------");
          tree.saveData();
        }else if(value.equals("load")){
          System.out.println("-------------------------------------------------------------------");
          tree.loadData();
          System.out.println("-------------------------------------------------------------------");
        }
      }catch(IOException e){
        e.printStackTrace();
      }
    }
  }

}