public class Node<T> {
    private T key;
    private Node<T> left;
    private Node<T> right;
    private int height;

    public Node(T key) {
        this.key = key;
        this.height = 1;
    }

    public void updateHeight() {
        int leftHeight = left != null ? left.getHeight() : 0;
        int rightHeight = right != null ? right.getHeight() : 0;
        height = Math.max(leftHeight, rightHeight) + 1;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


}

