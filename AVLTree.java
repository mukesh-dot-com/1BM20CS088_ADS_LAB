import java.util.Scanner;  
  
// create Node class to design the structure of the AVL Tree Node  
class Node  
{      
    int element;  
    int h;  //for height  
    Node leftChild;  
    Node rightChild;  
      
    //default constructor to create null node  
    public Node()  
    {  
        leftChild = null;  
        rightChild = null;  
        element = 0;  
        h = 0;  
    }  
    // parameterized constructor  
    public Node(int element)  
    {  
        leftChild = null;  
        rightChild = null;  
        this.element = element;  
        h = 0;  
    }       
}  
  
// create class ConstructAVLTree for constructing AVL Tree  
class ConstructAVLTree  
{  
    Node rootNode;       
  
    //Constructor to set null value to the rootNode  
    public ConstructAVLTree()  
    {  
        rootNode = null;  
    }  
     
    // create insertElement() to insert element to to the AVL Tree  
    public void insertElement(int element)  
    {  
        rootNode = insertElement(element, rootNode);  
    }  
      
    //create getHeight() method to get the height of the AVL Tree  
    private int getHeight(Node node )  
    {  
        return node == null ? -1 : node.h;  
    }  
      
    //create maxNode() method to get the maximum height from left and right node  
    private int getMaxHeight(int leftNodeHeight, int rightNodeHeight)  
    {  
    return leftNodeHeight > rightNodeHeight ? leftNodeHeight : rightNodeHeight;  
    }  
      
      
    //create insertElement() method to insert data in the AVL Tree recursively   
    private Node insertElement(int element, Node node)  
    {  
        //check whether the node is null or not  
        if (node == null)  
            node = new Node(element);  
        //insert a node in case when the given element is lesser than the element of the root node  
        else if (element < node.element)  
        {  
            node.leftChild = insertElement( element, node.leftChild );  
            if( getHeight( node.leftChild ) - getHeight( node.rightChild ) == 2 )  
                if( element < node.leftChild.element )  
                    node = rotateWithLeftChild( node );  
                else  
                    node = doubleWithLeftChild( node );  
        }  
        else if( element > node.element )  
        {  
            node.rightChild = insertElement( element, node.rightChild );  
            if( getHeight( node.rightChild ) - getHeight( node.leftChild ) == 2 )  
                if( element > node.rightChild.element)  
                    node = rotateWithRightChild( node );  
                else  
                    node = doubleWithRightChild( node );  
        }  
        else  
            ;  // if the element is already present in the tree, we will do nothing   
        node.h = getMaxHeight( getHeight( node.leftChild ), getHeight( node.rightChild ) ) + 1;  
          
        return node;  
          
    }  
      
    // creating rotateWithLeftChild() method to perform rotation of binary tree node with left child        
    private Node rotateWithLeftChild(Node node2)  
    {  
        Node node1 = node2.leftChild;  
        node2.leftChild = node1.rightChild;  
        node1.rightChild = node2;  
        node2.h = getMaxHeight( getHeight( node2.leftChild ), getHeight( node2.rightChild ) ) + 1;  
        node1.h = getMaxHeight( getHeight( node1.leftChild ), node2.h ) + 1;  
        return node1;  
    }  
  
    // creating rotateWithRightChild() method to perform rotation of binary tree node with right child        
    private Node rotateWithRightChild(Node node1)  
    {  
        Node node2 = node1.rightChild;  
        node1.rightChild = node2.leftChild;  
        node2.leftChild = node1;  
        node1.h = getMaxHeight( getHeight( node1.leftChild ), getHeight( node1.rightChild ) ) + 1;  
        node2.h = getMaxHeight( getHeight( node2.rightChild ), node1.h ) + 1;  
        return node2;  
    }  
  
    //create doubleWithLeftChild() method to perform double rotation of binary tree node. This method first rotate the left child with its right child, and after that, node3 with the new left child  
    private Node doubleWithLeftChild(Node node3)  
    {  
        node3.leftChild = rotateWithRightChild( node3.leftChild );  
        return rotateWithLeftChild( node3 );  
    }  
  
    //create doubleWithRightChild() method to perform double rotation of binary tree node. This method first rotate the right child with its left child and after that node1 with the new right child  
    private Node doubleWithRightChild(Node node1)  
    {  
        node1.rightChild = rotateWithLeftChild( node1.rightChild );  
        return rotateWithRightChild( node1 );  
    }      

    // create inorderTraversal() method for traversing AVL Tree in in-order form  
    public void inorderTraversal()  
    {  
        inorderTraversal(rootNode);  
    }  
    private void inorderTraversal(Node head)  
    {  
        if (head != null)  
        {  
            inorderTraversal(head.leftChild);  
            System.out.print(head.element+" ");  
            inorderTraversal(head.rightChild);  
        }  
    }  

    public void preorderTraversal()  
    {  
        preorderTraversal(rootNode);  
    }  
    private void preorderTraversal(Node head)  
    {  
        if (head != null)  
        {  
            System.out.print(head.element+" ");  
            preorderTraversal(head.leftChild);               
            preorderTraversal(head.rightChild);  
        }  
    }
    Node minValueNode(Node node)
	{
	    Node temp;
        for(temp=node;temp.leftChild!=null;temp=temp.leftChild);

		return temp;
	}
    int getBalance(Node N)
	{
		if (N == null)
			return 0;
		return getHeight(N.leftChild) - getHeight(N.rightChild);
	}
    
    Node deleteNode(Node root, int key)
	{
		if (root == null)
			return root;
		if (key < root.element)
			root.leftChild = deleteNode(root.leftChild, key);
		else if (key > root.element)
			root.rightChild = deleteNode(root.rightChild, key);
		else
		{
			if ((root.leftChild == null) || (root.rightChild == null))
			{
				Node temp = null;
				if (temp == root.leftChild)
					temp = root.rightChild;
				else
					temp = root.leftChild;

				if (temp == null)
				{
					temp = root;
					root = null;
				}
				else 
					root = temp;
			}
			else
			{
				Node temp = minValueNode(root.rightChild);
				root.element = temp.element;
				root.rightChild = deleteNode(root.rightChild, temp.element);
			}
		}
		if (root == null)
			return root;
		root.h = Math.max(getHeight(root.leftChild), getHeight(root.rightChild)) + 1;
    	        int balance = getBalance(root);
		if (balance > 1 && getBalance(root.leftChild) >= 0)
			return rotateWithRightChild(root);
		if (balance > 1 && getBalance(root.leftChild) < 0)
		{
			root.leftChild = rotateWithLeftChild(root.leftChild);
			return rotateWithRightChild(root);
		}
		if (balance < -1 && getBalance(root.rightChild) <= 0)
			return rotateWithLeftChild(root);
		if (balance < -1 && getBalance(root.rightChild) > 0)
		{
			root.rightChild = rotateWithRightChild(root.rightChild);
			return rotateWithLeftChild(root);
		}
		return root;
	}
}  

public class AVLTree
{  
    public static void main(String[] args)  
    {              
        Scanner sc = new Scanner(System.in);  
        ConstructAVLTree obj = new ConstructAVLTree();   
        int i = 0;
        while(i<5)     
        {   
            obj.insertElement(sc.nextInt());    
            i++;
        }                   
        System.out.println("\nDisplay AVL Tree in Pre order");  
        obj.preorderTraversal();  

        System.out.println("\nDisplay AVL Tree in In order");  
        obj.inorderTraversal();  

        System.out.println("\nEnter the node to be deleted");
        obj.deleteNode(obj.rootNode, sc.nextInt());

        System.out.println("\nDisplay AVL Tree in In order");  
        obj.inorderTraversal(); 
        obj.preorderTraversal(); 
 
                    System.exit(0);   
            }                       
    }  
