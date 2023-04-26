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
            root = delete(root, goal);
        }

        private Node<T> delete(Node<T> current, T goal) {
            if (current == null) {
                return null;
            }

            // Buscar el nodo deseado
            if (goal.compareTo(current.getKey()) < 0) {
                current.setLeft(delete(current.getLeft(), goal));
            } else if (goal.compareTo(current.getKey()) > 0) {
                current.setRight(delete(current.getRight(), goal));
            } else { // Encontrar el nodo deseado para eliminar
                // Eliminar un nodo hoja
                if (current.getLeft() == null && current.getRight() == null) {
                    current = null;
                }
                // Eliminar un nodo con un solo hijo
                else if (current.getLeft() == null || current.getRight() == null) {
                    if (current.getLeft() == null) {
                        current = current.getRight();
                    } else {
                        current = current.getLeft();
                    }
                }
                // Eliminar un nodo con dos hijos
                else {
                    Node<T> successor = getSuccessor(current);
                    current.setKey(successor.getKey());
                    current.setRight(delete(current.getRight(), successor.getKey()));
                }
            }

            // Reequilibrar el árbol
            if (current != null) {
                int balance = getBalanceFactor(current);
                if (balance > 1) {
                    if (getBalanceFactor(current.getLeft()) >= 0) {
                        current = rotateRight(current);
                    } else {
                        current.setLeft(rotateLeft(current.getLeft()));
                        current = rotateRight(current);
                    }
                } else if (balance < -1) {
                    if (getBalanceFactor(current.getRight()) <= 0) {
                        current = rotateLeft(current);
                    } else {
                        current.setRight(rotateRight(current.getRight()));
                        current = rotateLeft(current);
                    }
                }
            }

            return current;
        }

        private Node<T> getSuccessor(Node<T> current) {
            Node<T> successor = current.getRight();
            while (successor.getLeft() != null) {
                successor = successor.getLeft();
            }
            return successor;
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
