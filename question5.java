import java.util.*;
public class question5{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter size of domain (|A| = n): ");
        int n = sc.nextInt();
        System.out.print("Enter size of codomain (|B| = m): ");
        int m = sc.nextInt();
        Map<Integer, Integer> map = new HashMap<>();
        System.out.println("Enter number of mappings (a b) meaning f(a) = b:");
        int pairs = sc.nextInt();
        boolean wellDefined = true;
        for (int i = 0; i < pairs; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            if (map.containsKey(a) && map.get(a) != b) {
                wellDefined = false;}
            map.put(a, b);}
        if (!wellDefined) {
            System.out.println("=> Function is NOT well-defined (same a maps to two different b's).");
            return;
        } else {System.out.println("=> Function is well-defined.");}
        Set<Integer> images = new HashSet<>(map.values());
        boolean injective = (images.size() == map.size());
        boolean surjective = (images.size() == m);
        System.out.println("Injective: " + injective);
        System.out.println("Surjective: " + surjective);
        if (injective && surjective)
            System.out.println("=> f is Bijective.");
        else if (injective)
            System.out.println("=> f is Injective but not Surjective.");
        else if (surjective)
            System.out.println("=> f is Surjective but not Injective.");
        else
            System.out.println("=> f is neither Injective nor Surjective.");
        if (n > m) {
            System.out.println("\nSince |A| > |B|, by Pigeonhole Principle:");
            findCollision(map);}}
    static void findCollision(Map<Integer, Integer> map) {
        Map<Integer, Integer> seen = new HashMap<>();
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            int a = e.getKey(), b = e.getValue();
            if (seen.containsKey(b)) {
                System.out.println("Collision found: a1 = " + seen.get(b) +
                                   ", a2 = " + a + " → f(a1) = f(a2) = " + b);
                return;
            } else seen.put(b, a);}System.out.println("No collision found (unexpected).");}}
