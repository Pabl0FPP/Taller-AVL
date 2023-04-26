import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        AVL<Integer> avl = new AVL<>();

        String msg="";

        int n = scan.nextInt(); // Número de operaciones

        int type; // Tipo de operación

        int element; // Elemento a insertar

        for(int i = 0; i < n; i++) {
            type = scan.nextInt();
            element = scan.nextInt();
            if(type == 1) {
                avl.add(element);
                msg=msg+avl.printLevels();
            } else if (type == 2){
                avl.delete(element);
                msg=msg+avl.printLevels();
            } else {
                System.out.print("Invalid option selected.");
            }
        }
        System.out.println(msg);
    }
}