/**
  Programming Challenge 21-1.
  AVL Trees.
  This program adds the remove operation to the AVL Tree program in the text.
*/

import java.util.*;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


class AVLTree
{
   /**
      AVLNode Represents a node in an AVL tree
   */

    class AVLNode 
    {      
        int value;              // Value stored in this node.
        AVLNode left, right;    // Left and right subtree  children.
        int height;             // Height of subtree rooted at this node.

        public AVLNode(int value)
        { 
            // Call the other (sister) constructor.
            this(value, null, null);
        }
        public AVLNode(int val, AVLNode left1, AVLNode right1)
        {
            value = val;
            left = left1;
            right = right1;
            height = 0;
        }

        /**
           The resetHeight methods recomputes height 
           if the left or right subtrees have changed.
        */

        void resetHeight()
        {
          int leftHeight = AVLTree.getHeight(left);
          int rightHeight = AVLTree.getHeight(right);      
          height = 1 + Math.max(leftHeight, rightHeight);
        }    
     } 

    /** 
      The BTreeDisplay class can graphically 
       display a binary tree
    */


    /**
       The getHeight method computes the 
       height of an AVL tree.
       @param tree An AVL tree.
    */
    static int getHeight(AVLNode tree)
    {
      if (tree == null) return -1; 
      else return tree.height;       
    }

    /**
      The add method adds a value to this AVL tree.
      @param x The value to add.
      @return true. 
    */

    public boolean add(int x)
    {
       root = add(root, x);   
       return true;
    }

    /**
       The getView method creates and returns a 
       a graphical view of the binary tree.       
    */

    public Parent getView()
    {
        return BTreeDisplay.createBTreeDisplay(root);
    }

    /**
       The isEmpty method checks if this AVL tree is empty.
       @return true if the tree is empty, false otherwise.
    */

    public boolean isEmpty() 
    {
        return root == null;
    }

    private AVLNode root = null;      // Root of this AVL tree   
    /**
       The add method adds a value to an AVL tree.
       @return The root of the  augmented AVL tree.
    */

    private AVLNode add(AVLNode bTree, int x)
    {        
        if (bTree == null)
            return new AVLNode(x);        
        if (x < bTree.value)       
            bTree.left = add(bTree.left, x);       
        else       
            bTree.right = add(bTree.right, x);

        bTree.resetHeight();

        // Compute heights of the left and right subtrees
        int leftHeight = getHeight(bTree.left);
        int rightHeight = getHeight(bTree.right);       
        if (Math.abs(leftHeight - rightHeight) == 2)
           return balance(bTree);
        else       
           return bTree;

    }

    /**
       This class represents the result of removing a 
       a node from a binary tree.
    */
    private class RemovalResult
    {
        AVLNode node; // Removed node
        AVLNode tree; // Remaining tree
        RemovalResult(AVLNode node, AVLNode tree)
        {
            this.node = node;
            this.tree = tree;
        }
    }


    /**
       Remove a value from this AVL tree.
       @param x The value to remove.
       @return true if x was successfully removed, false otherwise.
    */

    public boolean remove(int x)
    {
        RemovalResult result = remove(root, x);
        if (result == null)
            return false;
        else 
        {
            root = result.tree;
            return true;
        }        
    }

     /**
       This remove method removes a value a value
       from an AVL tree and returns the 
       removed node and the remaining tree wrapped 
       in a RemovalResult object.
       @param bTree The AVL tree to remove from.
       @param x The value to be removed.
       @return null if x is not found in bTree.
    */


    private RemovalResult remove(AVLNode bTree, int x)
    {
       if (bTree == null) return null;  
       if (x < bTree.value)
       {
           // Remove x from the left subtree
           // Assume remaining tree comes back already balanced.


           // Reset the height and rebalance
         
       }
       if (x > bTree.value) 
       {
           // Remove x from the right subtree
           // Assume remaining tree comes back already balanced.

           // Reset the height and rebalance
         
       }
       // x is in this root node      
       // Is it a leaf? 

        

       // Does the node have two children?
       if (bTree.right != null && bTree.left != null)
       {
          // Remove largest node in left subtree and 
          // make it the root of the remaining tree


          // Reset height of newRoot and rebalance


          // Prepare the result to be returned
            
       }
      // The node has one child
                
    }   

    /**
       The removeLargest method removes the largest
       node from an AVL tree.
       @param bTree: The AVL tree.
       @return  The result of removing the largest node.
    */
    private RemovalResult removeLargest(AVLNode bTree)
    {
        if (bTree == null) return null;        
        if (bTree.right == null)
        {
            // Root is the largest node
            // Left subtree is remaining tree, should already be balanced.
            AVLNode tree = bTree.left;
            bTree.left = null;
            return new RemovalResult(bTree, tree);            
        }
        else
        {
           // Remove the largest node from the right subtree
            RemovalResult remResult = removeLargest(bTree.right);
            bTree.right = remResult.tree;

            // Reset the height of the subtree rooted at bTree
            // and rebalance
            bTree.resetHeight();            
            remResult.tree = balance(bTree);        
            return remResult;            
        }
    }    

    /**
       The balance method rebalances an AVL tree. 
       @param bTree The AVL tree needing to be balanced.
       @return The balanced AVL tree.      
    */

    private AVLNode balance(AVLNode bTree)
    {
        int rHeight = getHeight(bTree.right);
        int lHeight = getHeight(bTree.left);

        // Check if already balanced
        if (Math.abs(rHeight - lHeight) <= 1)
            return bTree;

        // Needs to be balanced        
        if (rHeight > lHeight)
        {
            AVLNode rightChild = bTree.right;
            int rrHeight = getHeight(rightChild.right);
            int rlHeight = getHeight(rightChild.left);           
            if (rrHeight > rlHeight)           
                return rrBalance(bTree);            
            else            
            {
                bTree =  rlBalance(bTree);

                // Following is necessary rrHeight == rlHeight
                bTree.left = balance(bTree.left);
                bTree.right = balance(bTree.right);
                bTree.resetHeight();
                return bTree;
            }            
        }
        else
        {
            AVLNode leftChild = bTree.left;
            int llHeight = getHeight(leftChild.left);
            int lrHeight = getHeight(leftChild.right);           
            if (llHeight > lrHeight)
                return llBalance(bTree);
            else
            {
                bTree =  lrBalance(bTree);  

                // Following is necessary llHeight == lrHeight
                bTree.left = balance(bTree.left);
                bTree.right = balance(bTree.right);
                bTree.resetHeight();
                return bTree;
            }
        }        
    }

    /** 
       The rrBlance method corrects an RR imbalance.
       @param bTree The AVL tree wih an RR imbalance.
       @return The balanced AVL tree.       
    */

    private AVLNode rrBalance(AVLNode bTree)
    {       
        AVLNode rightChild = bTree.right;
        AVLNode rightLeftChild = rightChild.left;
        rightChild.left = bTree;
        bTree.right = rightLeftChild;
        bTree.resetHeight();
        rightChild.resetHeight();
        return rightChild;
    }

    /**
       The rlBalance method corrects an RL imbalance.
       @parame bTree The AVL tree with an RL imbalance.
       @return The balanced AVL tree.
    */

    private AVLNode rlBalance(AVLNode bTree)
    {
        AVLNode root = bTree;
        AVLNode rNode = root.right;
        AVLNode rlNode = rNode.left;
        AVLNode rlrTree = rlNode.right;
        AVLNode rllTree = rlNode.left;

        // Build the restructured tree
        rNode.left = rlrTree;
        root.right = rllTree;
        rlNode.left = root;
        rlNode.right = rNode;

        // Adjust heights
        rNode.resetHeight();
        root.resetHeight();
        rlNode.resetHeight();

        return rlNode;        
    }

    /**
       The llBalance method corrects an LL imbalance.
       @param bTree The AVL tree with an LL imbalance.
       @return The balanced AVL tree.
    */

    private AVLNode llBalance(AVLNode bTree)
    {
        AVLNode leftChild = bTree.left;
        AVLNode lrTree = leftChild.right;
        leftChild.right = bTree;
        bTree.left = lrTree;
        bTree.resetHeight();
        leftChild.resetHeight();
        return leftChild;        
    }

    /**
       The lrBalance method corrects an LR imbalance.
       @param bTree The AVL tree with an LR imbalance.
       @return The balanced AVL tree.
    */
    private AVLNode lrBalance(AVLNode bTree)
    {
        AVLNode root = bTree;
        AVLNode lNode = root.left;
        AVLNode lrNode = lNode.right;
        AVLNode lrlTree = lrNode.left;
        AVLNode lrrTree = lrNode.right;

        // Build the restructured tree
        lNode.right = lrlTree;
        root.left = lrrTree;
        lrNode.left = lNode;
        lrNode.right = root;

        // Adjust heights
        lNode.resetHeight();
        root.resetHeight();
        lrNode.resetHeight();

        return lrNode;        
    }    
}

/**
   This class builds the user interface and 
   supports user interaction.
*/

public class Main extends Application
{
    private AVLTree avlTree = new AVLTree();   
    private Label cmdResultLabel;
    private TextField cmdResultTextField;

    private Label cmdLabel;
    private TextField cmdTextField;   

    BorderPane rootPane = new BorderPane();    

    /*
      This references the JavaFX node that displays 
      a graphical representation of the AVL tree.
    */
    Parent view = null;

    @Override
    public void start(Stage stage) throws Exception
    {

        // cmd text and cmd Result label in Horizontal Box
        HBox resultHBox = new HBox(10);
        cmdResultLabel = new Label("Command Result: ");
        cmdResultTextField = new TextField();
        resultHBox.getChildren().addAll(cmdResultLabel, cmdResultTextField);
        cmdResultTextField.setEditable(false);       
        resultHBox.setPadding(new Insets(10, 0, 0, 0));

        // Leave center for binary tree view

        // cmd label and cmd text field in Horizontal Box
        cmdLabel = new Label("Command: ");
        cmdTextField = new TextField();
        HBox cmdHBox = new HBox(10);
        cmdHBox.getChildren().addAll(cmdLabel, cmdTextField);      

        // Stack the two horizontal boxes in a vertical box
        // and put them in the SOUTH region of the border layout
        // of the frame.
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(resultHBox, cmdHBox);


        // Use an un-editable text area for  available  cmd list (help)
        // and put it in NORTH region of the Border layout of the frame
       TextArea cmdTextArea = new TextArea();        
       String [] cmds = 
       {
            "Available commands are:",            
            "           add element",
            "           remove element"            
       };
       for (String s : cmds)
       {
           cmdTextArea.appendText(s + "\n");
       }     
       cmdTextArea.setEditable(false);

       rootPane.setTop(cmdTextArea);
       rootPane.setBottom(vBox);
       rootPane.setPadding(new Insets(10));
       // Usual Scene and Stage stuff
       Scene scene = new Scene(rootPane);
       stage.setScene(scene);
       stage.setTitle("AVL Trees");        
       stage.show();

        cmdTextField.setOnAction(new CmdTextListener());

    }

    public static void main(String [] args)
    {
        launch(args);
    }

    /**
       This class interprets the commands typed
       into the command text field and passes
       on to the appropriate method in the AVL tree.
    */
    public class CmdTextListener implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent evt)
        {
            String cmdStr = cmdTextField.getText();
            Scanner sc = new Scanner(cmdStr);
            String cmd = sc.next();
            if (cmd.equals("add"))
            {
                int value = sc.nextInt();
                avlTree.add(value);              
                view = avlTree.getView();            
                rootPane.setCenter(view);              
                cmdResultTextField.setText(" ");
            } 
            if (cmd.equals("remove"))
            {
                int value = sc.nextInt();
                avlTree.remove(value);                
                view = avlTree.getView();            
                rootPane.setCenter(view);              
                cmdResultTextField.setText(String.valueOf(value));              
            }
        }  
     }    

}


 class BTreeDisplay 
    {        
        static Parent createBTreeDisplay(AVLTree.AVLNode tree)
        {           
           if (tree == null) return new Label();
           // Each node's value goes into a text field
           TextField nodeTf = new TextField(String.valueOf(tree.value));
           nodeTf.setAlignment(Pos.CENTER);
           nodeTf.setPrefColumnCount(4);
           HBox nodeTfHBox = new HBox();
           nodeTfHBox.setAlignment(Pos.CENTER);
           nodeTfHBox.getChildren().add(nodeTf);
           if (tree.left == null && tree.right == null) return nodeTfHBox;

           // View for binary tree is a BorderPane with node value
           // at the top, left subtree in Left and right subtree in Right
           BorderPane view = new BorderPane();
           view.setTop(nodeTfHBox);
           BorderPane.setAlignment(nodeTf, Pos.CENTER);

           if (tree.left != null)
           {
           Parent  leftSubTreeView = createBTreeDisplay(tree.left);
           view.setLeft(leftSubTreeView);
           BorderPane.setAlignment(leftSubTreeView, Pos.TOP_LEFT);
           }

           view.setCenter(new Label("    ")); // Spacer

           if (tree.right != null)
           {
           Parent  rightSubTreeView = createBTreeDisplay(tree.right);
           view.setRight(rightSubTreeView);         
           BorderPane.setAlignment(rightSubTreeView, Pos.TOP_RIGHT);
           }

           return view;          
        }   
    }

