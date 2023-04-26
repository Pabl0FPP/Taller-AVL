import java.util.LinkedList;
import java.util.Queue;

    public class AVL<T extends Comparable<T>> {

        private Node<T> root;

        public AVL() {
            root = null;
        }

        public void add(Node<T> node) {
            if (root == null) {
                root = node;
                System.out.println(root.getKey());
            } else {
                add(root, node);
            }
        }

        public void add(T key) {
            root = add(root, new Node<T>(key));
        }

        private Node<T> add(Node<T> current, Node<T> node) {
            if (current == null) {
                return node;
            }

            if (node.getKey().compareTo(current.getKey()) < 0) {
                current.setLeft(add(current.getLeft(), node));
            } else if (node.getKey().compareTo(current.getKey()) > 0) {
                current.setRight(add(current.getRight(), node));
            } else {
                // duplicate key, do nothing
                return current;
            }

            current.updateHeight();
            int balanceFactor = getBalanceFactor(current);

            // left-left case
            if (balanceFactor > 1 && node.getKey().compareTo(current.getLeft().getKey()) < 0) {
                return rotateRight(current);
            }

            // right-right case
            if (balanceFactor < -1 && node.getKey().compareTo(current.getRight().getKey()) > 0) {
                return rotateLeft(current);
            }

            // left-right case
            if (balanceFactor > 1 && node.getKey().compareTo(current.getLeft().getKey()) > 0) {
                current.setLeft(rotateLeft(current.getLeft()));
                return rotateRight(current);
            }

            // right-left case
            if (balanceFactor < -1 && node.getKey().compareTo(current.getRight().getKey()) < 0) {
                current.setRight(rotateRight(current.getRight()));
                return rotateLeft(current);
            }

            return current;
        }


        private int getBalanceFactor(Node<T> node) {
            int leftHeight = node.getLeft() != null ? node.getLeft().getHeight() : 0;
            int rightHeight = node.getRight() != null ? node.getRight().getHeight() : 0;
            return leftHeight - rightHeight;
        }

        private Node<T> rotateLeft(Node<T> node) {
            Node<T> newRoot = node.getRight();
            Node<T> leftSubtree = newRoot.getLeft();

            newRoot.setLeft(node);
            node.setRight(leftSubtree);

            node.updateHeight();
            newRoot.updateHeight();

            return newRoot;
        }

        private Node<T> rotateRight(Node<T> node) {
            Node<T> newRoot = node.getLeft();
            Node<T> rightSubtree = newRoot.getRight();

            newRoot.setRight(node);
            node.setLeft(rightSubtree);

            node.updateHeight();
            newRoot.updateHeight();

            return newRoot;
        }

        public Node<T> getMin() {
            return getMin(root);
        }

        private Node<T> getMin(Node<T> current) {
            if (current.getLeft() == null) {
                return current;
            }
            return getMin(current.getLeft());
        }

        public void delete(T goal) {
            delete(null, root, goal);
        }

        private void delete(Node<T> parent, Node<T> current, T goal) {
            if (current == null) {
                return;
            }
            //  Encontramos al nodo
            if (goal.equals(current.getKey())) {

                //  Es un nodo hoja
                if (current.getRight() == null && current.getLeft() == null) {
                    if (current.getLeft() == current) {
                        parent.setLeft(null);
                    } else {
                        parent.setRight(null);
                    }
                }

                //eliminar un nodo que tiene un hijo derecho
                else if (current.getRight() != null && current.getLeft() == null) {
                    if (parent.getLeft() == current) {
                        parent.setRight(current.getRight());
                    } else {
                        parent.setRight(current.getRight());
                    }

                    // Delete a node with a left child
                } else if (current.getRight() == null && current.getLeft() != null) {
                    if (parent.getLeft() == current) {
                        parent.setLeft(current.getLeft());
                    } else {
                        parent.setRight(current.getLeft());
                    }

                    // Delete a node with 2 children
                } else if (current.getRight() != null && current.getLeft() != null) {
                    Node<T> successor = getMin(current.getRight());
                    // Overwrite the key and values
                    current.setKey(successor.getKey());
                    // If it has values, do current.setValue(successor.getValue())
                    delete(current, current.getRight(), successor.getKey());
                }

            } else if (goal.compareTo(current.getKey()) < 0) {
                delete(current, current.getLeft(), goal);
            } else if (goal.compareTo(current.getKey()) > 0) {
                delete(current, current.getRight(), goal);
            }
        }

        public String printLevels() {
            if (root == null) {
                return "";
            }
            String msg = "";
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                for (int i = 0; i < levelSize; i++) {
                    Node node = queue.poll();
                    msg = msg + (node.getKey() + " ");
                    if (node.getLeft() != null) {
                        queue.add(node.getLeft());
                    }
                    if (node.getRight() != null) {
                        queue.add(node.getRight());
                    }
                }

            }
            msg = msg + "\n";
            return msg;
        }

    }
