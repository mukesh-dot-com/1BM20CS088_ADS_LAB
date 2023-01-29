import java.util.*;

class Tree2_3<S extends Comparable<S>> {
    private class node {
        private S leftKey, rightKey;
        private node leftChild, middleChild, rightChild;

        private node() {
            leftChild = middleChild = rightChild = null;
            leftKey = rightKey = null;
        }

        private node(S leftKey, S rightKey) {
            this.leftKey = leftKey;
            this.rightKey = rightKey;
            leftChild = middleChild = rightChild = null;
        }

        private node(S leftKey, S rightKey, node leftChild, node midChild) {
            this.leftKey = leftKey;
            this.rightKey = rightKey;
            this.leftChild = leftChild;
            this.middleChild = midChild;
        }
        private void setLeftKey(S value) {
            this.leftKey = value;
        }

        private void setRightKey(S value) {
            this.rightKey = value;
        }

        private void setLeftChild(node child) {
            this.leftChild = child;
        }

        private void setMidChild(node child) {
            this.middleChild = child;
        }

        private void setRightChild(node child) {
            this.rightChild = child;
        }

        private S getLeftKey() {
            return leftKey;
        }

        private S getRightKey() {
            return rightKey;
        }

        private node getLeftChild() {
            return leftChild;
        }

        private node getMidChild() {
            return middleChild;
        }

        private node getRightChild() {
            return rightChild;
        }
        private boolean isNodeLeaf() {
            return leftChild == null && middleChild == null && rightChild == null;
        }
        private boolean is2Node() {
            return rightKey == null;
        }
        private boolean is3Node() {
            return rightKey != null; 
        }

        private S replaceWithMaxKey() { 
            S maxKey;
            if (!isNodeLeaf()) { 
                if (getRightKey() != null)
                    maxKey = rightChild.replaceWithMaxKey(); 
                else
                    maxKey = middleChild.replaceWithMaxKey(); 
            } else {
                if (getRightKey() == null) {
                    maxKey = getLeftKey();
                    setLeftKey(null);
                } else {
                    maxKey = getRightKey();
                    setRightKey(null);
                }
            }
            if (!isBalanced())
                rebalance(); 
            return maxKey;
        }

        private S replaceWithMinKey() {
            S minKey;
            if (!isNodeLeaf()) {
                minKey = leftChild.replaceWithMinKey();
            }
            else { 
                minKey = leftKey;
                leftKey = null;
                if (rightKey != null) { 
                    leftKey = rightKey;
                    rightKey = null;
                }
            }
            if (!isBalanced())
                rebalance();
            return minKey;
        }

        private boolean isBalanced() {
            boolean isBalanced = false;
            if (isNodeLeaf())
                isBalanced = true; 
            else if (leftChild.getLeftKey() != null && middleChild.getLeftKey() != null) {
                if (rightKey != null) {
                    if (rightChild.getLeftKey() != null) {
                        isBalanced = true;
                    }
                } else {
                    isBalanced = true;
                }
            }
            return isBalanced;
        }

        private void rebalance() {
            while (!isBalanced()) {
                if (getLeftChild().getLeftKey() == null) {
                    getLeftChild().setLeftKey(getLeftKey());
                    setLeftKey(getMidChild().getLeftKey());
                    if (getMidChild().getRightKey() != null) {
                        getMidChild().setLeftKey(getMidChild().getRightKey());
                        getMidChild().setRightKey(null);
                    }
                    else {
                        getMidChild().setLeftKey(null);
                    }
                } else if (getMidChild().getLeftKey() == null) {
                    if (getRightKey() == null) {
                        if (getLeftChild().getLeftKey() != null && getLeftChild().getRightKey() == null
                                && getMidChild().getLeftKey() == null) {
                            setRightKey(getLeftKey());
                            setLeftKey(getLeftChild().getLeftKey());
                            setLeftChild(null);
                            setMidChild(null);
                            setRightChild(null);
                        } else {
                            getMidChild().setLeftKey(getLeftKey());
                            if (getLeftChild().getRightKey() == null) {
                                setLeftKey(getLeftChild().getLeftKey());
                                getLeftChild().setLeftKey(null);
                            } else {
                                setLeftKey(getLeftChild().getRightKey());
                                getLeftChild().setRightKey(null);
                            }
                            if (getLeftChild().getLeftKey() == null && getMidChild().getLeftKey() == null) {
                                setLeftChild(null);
                                setMidChild(null);
                                setRightChild(null);
                            }
                        }
                    } else {
                        getMidChild().setLeftKey(getRightKey());
                        setRightKey(getRightChild().getLeftKey());
                        if (getRightChild().getRightKey() != null) {
                            getRightChild().setLeftKey(getRightChild().getRightKey());
                            getRightChild().setRightKey(null);
                        } else {
                            getRightChild().setLeftKey(null);
                        }
                    }
                } else if (getRightKey() != null && getRightChild().getLeftKey() == null) {
                    if (getMidChild().getRightKey() != null) {
                        getRightChild().setLeftKey(getRightKey());
                        setRightKey(getMidChild().getRightKey());
                        getMidChild().setRightKey(null);
                    } else {
                        getMidChild().setRightKey(getRightKey());
                        setRightKey(null);
                    }
                }
            }
        }
    }

    private node root;
    private int elements;
    private int isRootGreater = 1, isRootLesser = -1;
    private boolean IsLastElementInsertedCorrectly;

    public Tree2_3() {
        root = new node();
        elements = 0; 
    }

    public int getSize() {
        return elements;
    }

    public boolean isEmpty() {
        if (root == null)
            return true;
        if (root.getLeftKey() == null)
            return true;
        return false; 
    }

    public int getLevel() {
        int level = 0;
        node tempNode = root;
        while (tempNode != null) {
            tempNode = root.getLeftChild(); 
            level++;
        }
        return level;
    }

    public boolean insert(S value) {
        IsLastElementInsertedCorrectly = false;
        this.elements++;
        if (root == null || root.getLeftKey() == null) {
            if (root == null)
                root = new node();
            root.setLeftKey(value);
            IsLastElementInsertedCorrectly = true;
        } else 
        {
            node newRoot = insertR(root, value); 
            if (newRoot != null)
                root = newRoot;
        }
        if (!IsLastElementInsertedCorrectly)
            this.elements--;
        return IsLastElementInsertedCorrectly;
    }

    private node insertR(node current, S element) {
        node newParent = null;
        if (!current.isNodeLeaf()) {
            node childPushedUp = null;
            if (current.leftKey.compareTo(element) == 0
                    || (current.is3Node() && current.rightKey.compareTo(element) == 0)) {
            }
            else if (current.leftKey.compareTo(element) == isRootGreater) {
                childPushedUp = insertR(current.leftChild, element);
                if (childPushedUp != null) { 
                    if (current.is2Node()) {
                        current.rightKey = current.leftKey; 
                        current.leftKey = childPushedUp.leftKey;
                        current.rightChild = current.middleChild;
                        current.middleChild = childPushedUp.middleChild;
                        current.leftChild = childPushedUp.leftChild;
                    } else { 
                        node rightSubtreeCopy = new node(current.rightKey, null, current.middleChild,
                                current.rightChild);
                        newParent = new node(current.leftKey, null, childPushedUp, rightSubtreeCopy);
                    }
                }
            } else if (current.is2Node()
                    || (current.is3Node() && current.rightKey.compareTo(element) == isRootGreater)) {
                childPushedUp = insertR(current.middleChild, element);
                if (childPushedUp != null) {
                    if (current.is2Node()) {
                        current.rightKey = childPushedUp.leftKey;
                        current.rightChild = childPushedUp.middleChild;
                        current.middleChild = childPushedUp.leftChild;
                    } else {
                        node left = new node(current.leftKey, null, current.leftChild, childPushedUp.leftChild);
                        node mid = new node(current.rightKey, null, childPushedUp.middleChild, current.rightChild);
                        newParent = new node(childPushedUp.leftKey, null, left, mid);
                    }
                }
            } else if (current.is3Node() && current.rightKey.compareTo(element) == isRootLesser) {
                childPushedUp = insertR(current.rightChild, element);
                if (childPushedUp != null) {
                    node leftCopy = new node(current.leftKey, null, current.leftChild, current.middleChild);
                    newParent = new node(current.rightKey, null, leftCopy, childPushedUp);
                }
            }
        } else { 
            IsLastElementInsertedCorrectly = true;
            if (current.leftKey.compareTo(element) == 0
                    || (current.is3Node() && current.rightKey.compareTo(element) == 0)) {
                IsLastElementInsertedCorrectly = false;
            } else if (current.is2Node()) {
                if (current.leftKey.compareTo(element) == isRootGreater) {
                    current.rightKey = current.leftKey;
                    current.leftKey = element;
                }
                else if (current.leftKey.compareTo(element) == isRootLesser)
                    current.rightKey = element;
            }
            else
                newParent = split(current, element);
        }
        return newParent;
    }

    private node split(node current, S value) {
        System.out.println("");
        System.out.println("splitting called for the element " + value);
        node newParent = null;
        if (current.leftKey.compareTo(value) == isRootGreater) {
            node left = new node(value, null);
            node right = new node(current.rightKey, null);
            newParent = new node(current.leftKey, null, left, right);
        }
        else if (current.leftKey.compareTo(value) == isRootLesser) {
            if (current.rightKey.compareTo(value) == isRootGreater) {

                node left = new node(current.leftKey, null);
                node right = new node(current.rightKey, null);
                newParent = new node(value, null, left, right);

            } else { 
                node left = new node(current.leftKey, null);
                node right = new node(value, null);
                newParent = new node(current.rightKey, null, left, right);
            }
        }
        return newParent;
    }

    public S search(S value) {
        return searchRec(root, value);
    }

    private S searchRec(node current, S value) {
        S found = null;
        if (current != null) {
            if (current.leftKey != null && current.leftKey.equals(value))
                found = current.leftKey;
            else {
                if (current.rightKey != null && current.rightKey.equals(value))
                    found = current.rightKey;
                else {
                    if (current.leftKey.compareTo(value) == isRootGreater) {
                        found = searchRec(current.leftChild, value);
                    } else if (current.rightChild == null || current.rightKey.compareTo(value) == isRootGreater) {
                        found = searchRec(current.middleChild, value);
                    } else if (current.rightKey.compareTo(value) == isRootLesser) {
                        found = searchRec(current.rightChild, value);
                    } else
                        return null; 
                }
            }
        }
        return found;
    }

    public boolean delete(S value) {
        boolean deleted;
        this.elements--;
        deleted = deleteRec(root, value); 
        root.rebalance();
        if (root.getLeftKey() == null)
            root = null; 
        if (!deleted)
            this.elements++;
        return deleted;
    }

    private boolean deleteRec(node current, S value) {
        boolean deleted = true;
        if (current == null)
            deleted = false;
        else {
            if (!current.getLeftKey().equals(value)) {
                if (current.getRightKey() == null || current.getRightKey().compareTo(value) == isRootGreater) {
                    if (current.getLeftKey().compareTo(value) == isRootGreater) {
                        deleted = deleteRec(current.leftChild, value);
                    } else { 
                        deleted = deleteRec(current.middleChild, value);
                    }
                } else {
                    if (!current.getRightKey().equals(value)) {
                        deleted = deleteRec(current.rightChild, value);
                    } else { 
                        if (current.isNodeLeaf())
                            current.setRightKey(null);
                        else { 
                            S replacement = current.getRightChild().replaceWithMinKey();
                            current.setRightKey(replacement);
                        }
                    }
                }
            }
            else {
                if (current.isNodeLeaf()) { 
                    if (current.getRightKey() != null) {
                        current.setLeftKey(current.getRightKey());
                        current.setRightKey(null);
                    } else { 
                        current.setLeftKey(null); 
                        return true;
                    }
                } else { 
                    S replacement = current.getLeftChild().replaceWithMaxKey();
                    current.setLeftKey(replacement);
                }
            }
        }
        if (current != null && !current.isBalanced()) {
            current.rebalance();
        } else if (current != null && !current.isNodeLeaf()) {
            boolean balanced = false;
            while (!balanced) {
                if (current.getRightChild() == null) {
                    if (current.getLeftChild().isNodeLeaf() && !current.getMidChild().isNodeLeaf()) {
                        S replacement = current.getMidChild().replaceWithMinKey();
                        S readdition = current.getLeftKey();
                        current.setLeftKey(replacement);
                        insert(readdition);
                    } else if (!current.getLeftChild().isNodeLeaf() && current.getMidChild().isNodeLeaf()) {
                        if (current.getRightKey() == null) {
                            S replacement = current.getLeftChild().replaceWithMaxKey();
                            S readdition = current.getLeftKey();
                            current.setLeftKey(replacement);
                            insert(readdition);
                        }
                    }
                }
                if (current.getRightChild() != null) {
                    if (current.getMidChild().isNodeLeaf() && !current.getRightChild().isNodeLeaf()) {

                        current.getRightChild().rebalance();
                    }
                    if (current.getMidChild().isNodeLeaf() && !current.getRightChild().isNodeLeaf()) {
                        S replacement = current.getRightChild().replaceWithMinKey();
                        S readdition = current.getRightKey();
                        current.setRightKey(replacement);
                        insert(readdition);
                    } else
                        balanced = true;
                }
                if (current.isBalanced())
                    balanced = true;
            }
        }
        return deleted;
    }

    public void inOrderDisplay() {
        if (!isEmpty()) {
            System.out.println("In Order Display of the 2-3 Tree:");
            inOrderRec(root);
        } else
            System.out.println("The tree is empty.");
    }
    private void inOrderRec(node current) {
        if (current != null) {
            if (current.isNodeLeaf()) {
                System.out.print(current.getLeftKey() + " ");
                if (current.getRightKey() != null)
                    System.out.print(current.getRightKey() + " ");
            } else {
                inOrderRec(current.getLeftChild());
                System.out.print(current.getLeftKey() + " ");
                inOrderRec(current.getMidChild());
                if (current.getRightKey() != null) {
                    if (!current.isNodeLeaf())
                        System.out.print(current.getRightKey() + " ");
                    inOrderRec(current.getRightChild());
                }
            }
        }
    }
}

class Main {
    public static void main(String[] args) {
        Tree2_3<Integer> tree = new Tree2_3<>();
        int opt;
        Scanner sc = new Scanner(System.in);
            System.out.println("\nEnter number of elements to be inserted");
            opt=sc.nextInt();
            while(opt!=0){
                tree.insert(sc.nextInt());
                opt--;
            }
            System.out.println("\nInorder is");
            tree.inOrderDisplay();
            System.out.println("\nEnter the element to be deleted");
            tree.delete(sc.nextInt());
            System.out.println("\nInorder is");
            tree.inOrderDisplay();
        sc.close();
    }
}
