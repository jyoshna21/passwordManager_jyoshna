package JYOSHNAintern;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class passworkManager {
	public static String generatePassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }

    // Method to generate a secret key
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    // Method to encrypt data
    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // Method to decrypt data
    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedData);
    }

    // Method to save the encrypted password to a file
    public static void savePassword(String filename, String encryptedPassword) throws IOException {
        FileWriter writer = new FileWriter(new File(filename));
        writer.write(encryptedPassword);
        writer.close();
    }

    // Main method
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter the length of the password:");
            int length = scanner.nextInt();

            String password = generatePassword(length);
            System.out.println("Generated Password: " + password);

            SecretKey key = generateKey();
            String encryptedPassword = encrypt(password, key);
            System.out.println("Encrypted Password: " + encryptedPassword);

            System.out.println("Enter the filename to save the password:");
            scanner.nextLine(); // Consume the newline
            String filename = scanner.nextLine();

            savePassword(filename, encryptedPassword);
            System.out.println("Password saved to " + filename);

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
