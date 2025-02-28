package com.mdhp.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageUtils {

    public static boolean isValidImage(String base64String) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);

        // Check if the image is JPG (starts with FF D8 and ends with FF D9)
        if (decodedBytes.length > 2 && decodedBytes[0] == (byte) 0xFF && decodedBytes[1] == (byte) 0xD8
                && decodedBytes[decodedBytes.length - 2] == (byte) 0xFF && decodedBytes[decodedBytes.length - 1] == (byte) 0xD9) {
            return true;
        }

        // Check if the image is PNG (starts with 89 50 4E 47 0D 0A 1A 0A)
        if (decodedBytes.length > 8 && decodedBytes[0] == (byte) 0x89 && decodedBytes[1] == (byte) 0x50
                && decodedBytes[2] == (byte) 0x4E && decodedBytes[3] == (byte) 0x47 && decodedBytes[4] == (byte) 0x0D
                && decodedBytes[5] == (byte) 0x0A && decodedBytes[6] == (byte) 0x1A && decodedBytes[7] == (byte) 0x0A) {
            return true;
        }

        return false;
    }

    public static void saveImage(String base64String, String filePath) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);

        // Create the output file (in this case, in the resources/images/ directory)
        File outputFile = new File(filePath);
        File parentDir = outputFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs(); // Create the directories if they don't exist
        }

        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(decodedBytes); // Write the byte array to the file
        }

        System.out.println("File saved at: " + filePath);
    }
}

