import java.util.*;
public class question1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter size of Set A: ");
        int nA = sc.nextInt();
        Set<Integer> A = new HashSet<>();
        System.out.println("Enter elements of Set A:");
        for (int i = 0; i < nA; i++) A.add(sc.nextInt());
        System.out.print("Enter size of Set B: ");
        int nB = sc.nextInt();
        Set<Integer> B = new HashSet<>();
        System.out.println("Enter elements of Set B:");
        for (int i = 0; i < nB; i++) B.add(sc.nextInt());
        System.out.print("Enter size of Set C: ");
        int nC = sc.nextInt();
        Set<Integer> C = new HashSet<>();
        System.out.println("Enter elements of Set C:");
        for (int i = 0; i < nC; i++) C.add(sc.nextInt());

        Set<Integer> AB = new HashSet<>(A); AB.retainAll(B);
        Set<Integer> BC = new HashSet<>(B); BC.retainAll(C);
        Set<Integer> AC = new HashSet<>(A); AC.retainAll(C);
        Set<Integer> ABC = new HashSet<>(AB); ABC.retainAll(C);

        
        int unionSize = A.size() + B.size() + C.size()
                - AB.size() - BC.size() - AC.size()
                + ABC.size();

        System.out.println("\nUnion size (A U B U C): " + unionSize);

        System.out.println("\nPower Set of A:");
        List<Integer> listA = new ArrayList<>(A);
        int powerSize = 1 << listA.size();
        for (int i = 0; i < powerSize; i++) {
            System.out.print("{ ");
            for (int j = 0; j < listA.size(); j++) {
                if ((i & (1 << j)) != 0)
                    System.out.print(listA.get(j) + " ");
            }
            System.out.println("}");
        }
    }
}
