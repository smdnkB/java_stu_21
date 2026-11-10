package com.liu.moudel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * This program decrypts and extracts strings from 'a' and 'aa' files * based on the logic from the provided decompiled Java code.
 */
public class Decode {
    /**
     * Main method to run the decryption process. * @param args Command line arguments: path to file 'a' and path to file 'aa'.
     */
    public static void main(String[] args) {

        String fileAPath = "D:\\object\\Go\\dreamNas\\a";
        String fileAAPath =  "D:\\object\\Go\\dreamNas\\aa";
        try {
            // 1. Read and decrypt 'aa' file content.
            // The decryption is a simple XOR with the key 0x24.
            byte[] decryptedAA = decryptAA(fileAAPath);
            System.out.println("Successfully decrypted 'aa' file.");
            // 2. Read and process 'a' index file to extract strings.
            List<String> words = processA(fileAPath, decryptedAA);
            System.out.println("Successfully extracted " + words.size() + " words.");
            // 3. Print the decrypted words.
            System.out.println("\n--- Decrypted Words ---");
            for (String word : words) {
                System.out.println(word);
            }
        } catch (IOException e) {
            System.err.println("An error occurred:");
            e.printStackTrace();
        }
    }

    /**
     * Reads the 'aa' file and decrypts its content by XORing each byte with 0x24. * @param path Path to the 'aa' file. * @return A byte array containing the decrypted content. * @throws IOException if an I/O error occurs.
     */
    private static byte[] decryptAA(String path) throws IOException {
        File file = new File(path);
        byte[] fileContent = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            int bytesRead = fis.read(fileContent);
            if (bytesRead != file.length()) {
                throw new IOException("Could not read the entire file: " + path);
            }
        }
        for (int i = 0; i < fileContent.length; i++) {
            fileContent[i] = (byte) (fileContent[i] ^ 0x24);
        }
        return fileContent;
    }

    /**
     * Processes the index file 'a' to find offsets and lengths, * and extracts the corresponding strings from the decrypted 'aa' data. * @param path Path to the 'a' file. * @param decryptedAA The byte array of decrypted 'aa' content. * @return A list of extracted strings. * @throws IOException if an I/O error occurs.
     */
    private static List<String> processA(String path, byte[] decryptedAA) throws IOException {
        List<String> words = new ArrayList<>();
        byte[] indexFileBytes;
        File fileA = new File(path);
        try (FileInputStream fis = new FileInputStream(fileA)) {
            indexFileBytes = new byte[(int) fileA.length()];
            int bytesRead = fis.read(indexFileBytes);
            if (bytesRead != fileA.length()) {
                throw new IOException("Could not read the entire file: " + path);
            }
        }
        // First 4 bytes of 'a' is the number of entries (big-endian).
        ByteBuffer bb = ByteBuffer.wrap(indexFileBytes, 0, 4);
        bb.order(ByteOrder.BIG_ENDIAN);
        int numEntries = bb.getInt();
        // The rest of the relevant data is an array of 8-byte entries.
        int indexDataLength = numEntries * 8;
        if (indexFileBytes.length < 4 + indexDataLength) {
            throw new IOException("Index file 'a' is smaller than expected.");
        }
        byte[] indexData = new byte[indexDataLength];
        System.arraycopy(indexFileBytes, 4, indexData, 0, indexDataLength);
        int currentStringOffset = 0;
        for (int i = 0; i < numEntries; i++) {
            int entryBaseOffset = i * 8;
            // The length of the string is stored at the 8th byte (index 7) of the entry,
            // with an obfuscation of -10.
            int length = (indexData[entryBaseOffset + 7]) - 10;
            if (length > 0) {
                try {
                    if (currentStringOffset + length <= decryptedAA.length) {
                        String word = new String(decryptedAA, currentStringOffset, length, StandardCharsets.UTF_8);
                        words.add(word);
                    } else {
                        System.err.println("Error: trying to read beyond the bounds of decrypted 'aa' data.");
                        System.err.println("Entry " + i + ": offset=" + currentStringOffset + ", length=" + length + ", aa_data_size=" + decryptedAA.length);
                    }
                } catch (Exception e) {
                    System.err.println("Error creating string at offset " + currentStringOffset + " with length " + length);
                }
            }
            // The original code increments the offset by length + 1, suggesting a separator byte.
            currentStringOffset += length + 1;
        }
        return words;
    }
}