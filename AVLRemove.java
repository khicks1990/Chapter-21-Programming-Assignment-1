import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

// AVLTree class represents the AVL tree data structure
class AVLTree {
    // AVLNode class represents a node in the AVL tree
    class AVLNode {
        int value;              // Value stored in this node
        AVLNode left, right;    // Left and right subtree children
        int height;             // Height of subtree rooted at this node

        public AVLNode(int value) {
            this(value, null, null); // Calls the other constructor with no children
        }

        // Constructor with left and right child nodes
        public AVLNode(int val, AVLNode left1, AVLNode right1) {
            value = val;
            left = left1;
            right = right1;
            height = 0; // Initial height
        }

        // Resets the height of the node based on its children's heights
        void resetHeight() {
            int leftHeight = AVLTree.getHeight(left);
            int rightHeight = AVLTree.getHeight(right);
            height = 1 + Math.max(leftHeight, rightHeight); // Height = 1 + max height of children
        }
    }

    private AVLNode root = null; // Root of the AVL tree

    // Static method to get the height of a tree
    static int getHeight(AVLNode tree) {
        if (tree == null) return -1; // Height of an empty tree is -1
        else return tree.height;      // Return the height of the node
    }

    // Method to add a value to the AVL tree
    public boolean add(int x) {
        root = add(root, x); // Reassigning the root after insertion
        return true; // Always return true for successful addition
    }

    // Method to get a visual representation of the tree
    public JComponent getView() {
        return BTreeDisplay.createBTreeDisplay(root); // Returns the graphical display of the tree
    }

    // Method to check if the tree is empty
    public boolean isEmpty() {
        return root == null; // Return true if root is null
    }

    // Private method to add a value to the tree recursively
    private AVLNode add(AVLNode bTree, int x) {
        if (bTree == null)
            return new AVLNode(x); // Create a new node if we've reached a leaf

        // Recur down the tree to find the correct position
        if (x < bTree.value)
            bTree.left = add(bTree.left, x); // Add to the left subtree
        else
            bTree.right = add(bTree.right, x); // Add to the right subtree

        // Update the height of the node
        bTree.resetHeight();

        // Balance the tree if necessary
        int leftHeight = getHeight(bTree.left);
        int rightHeight = getHeight(bTree.right);
        if (Math.abs(leftHeight - rightHeight) == 2)
            return balance(bTree); // Rebalance if the tree is unbalanced
        else
            return bTree; // Return the (possibly updated) node
    }

    // Inner class to hold the result of a removal operation
    private class RemovalResult {
        AVLNode node; // The removed node
        AVLNode tree; // The remaining tree after removal

        RemovalResult(AVLNode node, AVLNode tree) {
            this.node = node; // Assign the removed node
            this.tree = tree; // Assign the remaining tree
        }
    }

    // Method to remove a value from the AVL tree
    public boolean remove(int x) {
        RemovalResult result = remove(root, x); // Call the private remove method
        if (result == null)
            return false; // Return false if the value was not found
        else {
            root = result.tree; // Set the new root
            return true; // Return true if the value was successfully removed
        }
    }

    // Private method to remove a value recursively
    private RemovalResult remove(AVLNode bTree, int x) {
        // Base case: if the tree is empty
        if (bTree == null) return null;

        // If the value to remove is less than the current node's value
        if (x < bTree.value) {
            // Recursively remove from the left subtree, If not found, return null

            // Update the left child of the current node
            // Update the height of the current node

            // Balance the tree and return the result
        }
        // If the value to remove is greater than the current node's value
        if (x > bTree.value) {
            // Recursively remove from the right subtree, If not found, return null

            // Update the right child of the current node
            // Update the height of the current node

            // Balance the tree and return the result
        }

        // If the current node contains the value to be removed
        // Case 1: Node is a leaf
        if (bTree.right == null && bTree.left == null)
            // Return the node and null tree

        // Case 2: Node has two children
        if (bTree.right != null && bTree.left != null) {
            // Find the largest node in the left subtree
            // replace the current node
            // Attach the left subtree
            // Attach the right subtree

            // Update the height of the new root
            // Balance the new root

            // Clear the current node's children
            // Return the current node and new root
        }

        // Case 3: Node has one child
        AVLNode node = bTree; // The node to be removed
        AVLNode tree; // The remaining tree
        if (bTree.left != null)
            // If left child exists, use it
        else
            // Otherwise, use the right child

        node.left = null; // Clear the current node's children
        node.right = null;
        return new RemovalResult(node, tree); // Return the removed node and remaining tree
    }

    // Method to remove the largest node from a subtree
    private RemovalResult removeLargest(AVLNode bTree) {
        if (bTree == null) return null; // Base case: empty tree
        if (bTree.right == null) {
            // If the right child is null, this is the largest node
            AVLNode tree = bTree.left; // The remaining tree is the left subtree
            bTree.left = null; // Clear the current node's children
            return new RemovalResult(bTree, tree); // Return the removed node and the remaining tree
        } else {
            RemovalResult remResult = removeLargest(bTree.right); // Recur to find the largest
            bTree.right = remResult.tree; // Update the right child
            bTree.resetHeight(); // Update height
            remResult.tree = balance(bTree); // Balance the tree
            return remResult; // Return the result with the updated tree
        }
    }

    // Method to balance the AVL tree
    private AVLNode balance(AVLNode bTree) {
        int rHeight = getHeight(bTree.right);
        int lHeight = getHeight(bTree.left);

        // If the tree is balanced
        if (Math.abs(rHeight - lHeight) <= 1)
            return bTree;

        // If the right subtree is taller
        if (rHeight > lHeight) {
            AVLNode rightChild = bTree.right;
            int rrHeight = getHeight(rightChild.right);
            int rlHeight = getHeight(rightChild.left);
            if (rrHeight > rlHeight)
                return rrBalance(bTree); // Perform RR rotation
            else {
                bTree = rlBalance(bTree); // Perform RL rotation
                bTree.left = balance(bTree.left);
                bTree.right = balance(bTree.right);
                bTree.resetHeight();
                return bTree;
            }
        } else { // If the left subtree is taller
            AVLNode leftChild = bTree.left;
            int llHeight = getHeight(leftChild.left);
            int lrHeight = getHeight(leftChild.right);
            if (llHeight > lrHeight)
                return llBalance(bTree); // Perform LL rotation
            else {
                bTree = lrBalance(bTree); // Perform LR rotation
                bTree.left = balance(bTree.left);
                bTree.right = balance(bTree.right);
                bTree.resetHeight();
                return bTree;
            }
        }
    }

    // Right-Right (RR) rotation
    private AVLNode rrBalance(AVLNode bTree) {
        AVLNode rightChild = bTree.right;
        AVLNode rightLeftChild = rightChild.left;
        rightChild.left = bTree; // Rotate right
        bTree.right = rightLeftChild; // Adjust right child
        bTree.resetHeight();
        rightChild.resetHeight();
        return rightChild; // Return new root
    }

    // Right-Left (RL) rotation
    private AVLNode rlBalance(AVLNode bTree) {
        AVLNode root = bTree;
        AVLNode rNode = root.right;
        AVLNode rlNode = rNode.left;
        AVLNode rlrTree = rlNode.right;
        AVLNode rllTree = rlNode.left;

        // Rotate
        rNode.left = rlrTree;
        root.right = rllTree;
        rlNode.left = root;
        rlNode.right = rNode;

        // Update heights
        rNode.resetHeight();
        root.resetHeight();
        rlNode.resetHeight();

        return rlNode; // Return new root
    }

    // Left-Left (LL) rotation
    private AVLNode llBalance(AVLNode bTree) {
        AVLNode leftChild = bTree.left;
        AVLNode lrTree = leftChild.right;
        leftChild.right = bTree; // Rotate left
        bTree.left = lrTree; // Adjust left child
        bTree.resetHeight();
        leftChild.resetHeight();
        return leftChild; // Return new root
    }

    // Left-Right (LR) rotation
    private AVLNode lrBalance(AVLNode bTree) {
        AVLNode root = bTree;
        AVLNode lNode = root.left;
        AVLNode lrNode = lNode.right;
        AVLNode lrlTree = lrNode.left;
        AVLNode lrrTree = lrNode.right;

        // Rotate
        lNode.right = lrlTree;
        root.left = lrrTree;
        lrNode.left = lNode;
        lrNode.right = root;

        // Update heights
        lNode.resetHeight();
        root.resetHeight();
        lrNode.resetHeight();

        return lrNode; // Return new root
    }
}

// BTreeDisplay class to create a visual representation of the AVL tree
class BTreeDisplay {
    static JComponent createBTreeDisplay(AVLTree.AVLNode tree) {
        if (tree == null) return new JPanel(); // Return an empty panel for null tree

        JTextField valueField = new JTextField(Integer.toString(tree.value));
        valueField.setEditable(false); // Node value field is not editable
        valueField.setHorizontalAlignment(JTextField.CENTER);
        valueField.setPreferredSize(new Dimension(50, 25)); // Fixed size for node values

        JPanel valuePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        valuePanel.add(valueField); // Center the node value

        if (tree.left == null && tree.right == null) {
            return valuePanel; // Return leaf node display
        }

        JPanel nodePanel = new JPanel(new BorderLayout());
        nodePanel.add(valuePanel, BorderLayout.NORTH); // Add the value at the top

        JPanel childrenPanel = new JPanel();
        childrenPanel.setLayout(new BoxLayout(childrenPanel, BoxLayout.X_AXIS)); // Horizontal layout for children
        // Create the display for left and right children recursively
        childrenPanel.add(createBTreeDisplay(tree.left));
        childrenPanel.add(Box.createHorizontalGlue()); // Spacer
        childrenPanel.add(createBTreeDisplay(tree.right));

        nodePanel.add(childrenPanel, BorderLayout.CENTER); // Add children to the center
        return nodePanel; // Return the complete node display
    }
}

// Main class to create the GUI for AVL Tree operations
public class AVLRemove extends JFrame {
    private AVLTree avlTree = new AVLTree(); // Instance of the AVLTree
    private JTextField cmdResultTextField; // Field to display command results
    private JTextField cmdTextField; // Field for user command input
    private JPanel treePanel; // Panel to display the AVL tree

    public AVLRemove() {
        setTitle("AVL Trees");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 600); // Set the size of the window

        // Command help in NORTH
        JTextArea cmdTextArea = new JTextArea();
        cmdTextArea.setText("Available commands are:\nadd element\nremove element");
        cmdTextArea.setEditable(false); // Make it read-only
        add(new JScrollPane(cmdTextArea), BorderLayout.NORTH); // Add scrollable command help

        // Tree display in CENTER
        treePanel = new JPanel(new BorderLayout());
        add(treePanel, BorderLayout.CENTER); // Add the tree panel to the center

        // Command input and result in SOUTH
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding

        // Panel to display command result
        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        resultPanel.add(new JLabel("Command Result: "));
        cmdResultTextField = new JTextField(20); // Text field for command results
        cmdResultTextField.setEditable(false); // Make it read-only
        resultPanel.add(cmdResultTextField);

        // Panel for command input
        JPanel cmdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cmdPanel.add(new JLabel("Command: "));
        cmdTextField = new JTextField(20); // Text field for user command input
        cmdTextField.addActionListener(new CmdTextListener()); // Add action listener
        cmdPanel.add(cmdTextField);

        // Add panels to southPanel
        southPanel.add(resultPanel);
        southPanel.add(cmdPanel);

        // Add the south panel to the main frame
        add(southPanel, BorderLayout.SOUTH);
    }

    // Inner class to handle command input
    private class CmdTextListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String cmdStr = cmdTextField.getText(); // Get the command
            Scanner sc = new Scanner(cmdStr);
            if (!sc.hasNext()) return; // Check if input is empty
            String cmd = sc.next(); // Read the command
            if (cmd.equals("add")) {
                if (!sc.hasNextInt()) return; // Check for integer input
                int value = sc.nextInt(); // Get the value to add
                avlTree.add(value); // Add the value to the tree
                updateTreeDisplay(); // Update tree display
                cmdResultTextField.setText(" "); // Clear command result
            } else if (cmd.equals("remove")) {
                if (!sc.hasNextInt()) return; // Check for integer input
                int value = sc.nextInt(); // Get the value to remove
                boolean removed = avlTree.remove(value); // Attempt to remove the value
                if (removed) {
                    updateTreeDisplay(); // Update tree display
                    cmdResultTextField.setText(Integer.toString(value)); // Show removed value
                } else {
                    cmdResultTextField.setText("Value not found"); // Show error message
                }
            }
            cmdTextField.setText(""); // Clear the command input field
        }
    }

    // Method to update the tree display in the GUI
    private void updateTreeDisplay() {
        treePanel.removeAll(); // Clear previous tree display
        JComponent view = avlTree.getView(); // Get the current tree display
        treePanel.add(view, BorderLayout.CENTER); // Add the new tree display
        treePanel.revalidate(); // Refresh the panel
        treePanel.repaint(); // Repaint the panel to reflect changes
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AVLRemove frame = new AVLRemove();
            frame.setVisible(true); // Show the frame
        });
    }
}