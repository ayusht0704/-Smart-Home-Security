import java.util.*;
public class question7 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter 4-bit message (space-separated bits): ");
        int[] m = new int[4];
        for (int i = 0; i < 4; i++) m[i] = sc.nextInt();
        // Step 2: Encode using Hamming(7,4)
        int[] code = new int[7];
        code[2] = m[0];
        code[4] = m[1];
        code[5] = m[2];
        code[6] = m[3];
        // Parity bits (even parity)
        code[0] = code[2] ^ code[4] ^ code[6];
        code[1] = code[2] ^ code[5] ^ code[6];
        code[3] = code[4] ^ code[5] ^ code[6];
        System.out.print("Encoded 7-bit code: ");
        for (int bit : code) System.out.print(bit + " ");
        System.out.println();
        // Step 3: Receive (simulate transmission)
        System.out.print("Enter received 7-bit word: ");
        int[] r = new int[7];
        for (int i = 0; i < 7; i++) r[i] = sc.nextInt();
        // Step 4: Compute syndrome bits
        int s1 = r[0] ^ r[2] ^ r[4] ^ r[6];
        int s2 = r[1] ^ r[2] ^ r[5] ^ r[6];
        int s3 = r[3] ^ r[4] ^ r[5] ^ r[6];
        int errorPos = s1 + 2 * s2 + 4 * s3;
        System.out.println("Syndrome bits: " + s1 + " " + s2 + " " + s3);
        if (errorPos == 0)
            System.out.println("No error detected.");
        else {
            System.out.println("Error at bit position: " + errorPos);
            // Correct the error
            r[errorPos - 1] ^= 1;
        }
        // Step 5: Display corrected code and message
        System.out.print("Corrected code: ");
        for (int bit : r) System.out.print(bit + " ");
        System.out.println();

        System.out.print("Decoded 4-bit message: ");
        System.out.println(r[2] + " " + r[4] + " " + r[5] + " " + r[6]);
    }
}
