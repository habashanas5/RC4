package testing;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Testing {

    private static final int S_ARRAY_SIZE = 256;

    public static byte[] initialize(byte[] key) {
        byte[] sArray = new byte[S_ARRAY_SIZE];
        byte[] keyArray = new byte[S_ARRAY_SIZE];

        for (int i = 0; i < S_ARRAY_SIZE; i++) {
            sArray[i] = (byte) i;
            keyArray[i] = key[i % key.length];
        }

        int j = 0;
        for (int i = 0; i < S_ARRAY_SIZE; i++) {
            j = (j + (sArray[i] & 0xFF) + (keyArray[i] & 0xFF)) & 0xFF;
            byte temp = sArray[i];
            sArray[i] = sArray[j];
            sArray[j] = temp;
        }
        return sArray;
    }

    public static byte[] encrypt(byte[] plaintext, byte[] key) {
        byte[] ciphertext = new byte[plaintext.length];
        byte[] sArray = initialize(key);

        int i = 0;
        int j = 0;
        int k = 0;
        while (k < plaintext.length) {
            i = ((i + 1) & 0xFF) % 256;
            j = ((j + (sArray[i] & 0xFF)) & 0xFF) % 256;
            
            byte temp = sArray[i];
            sArray[i] = sArray[j];
            sArray[j] = temp;
            int t = (((sArray[i] & 0xFF) + (sArray[j] & 0xFF)) & 0xFF) % 256;
            ciphertext[k] = (byte) (plaintext[k] ^ sArray[t]);
            k++;
        }
        return ciphertext;
    }

    public static byte[] decrypt(byte[] ciphertext, byte[] key) {
        return encrypt(ciphertext, key);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String plaintext = "";
        String key = "";

        System.out.print("Please enter plaintext: ");
        plaintext = in.nextLine();

        System.out.print("Please enter key: ");
        key = in.nextLine();

        System.out.println();

        byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);

        byte[] ciphertext = encrypt(plaintextBytes, keyBytes);
        System.out.println("The ciphertext is: "
                + new String(ciphertext, StandardCharsets.UTF_8));

        byte[] decryptedText = decrypt(ciphertext, keyBytes);
        System.out.println("Decrypted text is: "
                + new String(decryptedText, StandardCharsets.UTF_8));
    }
}
