package com.dheeraj.user.registration.util;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dheeraj on 04/03/18.
 */
public class AllFiles {
    public static void main(String[] args) {
        File imageFile = new File("/Users/dheeraj/imageSample");

        ITesseract instance = new Tesseract();


        List<File> files = Arrays.asList(imageFile.listFiles());
        for (File file : files) {
            System.out.println(file);
            try {

                String result = instance.doOCR(imageFile);
                System.out.println(result);
            } catch (Exception e) {
//                System.err.println(e.getMessage());

            }


            System.out.println("===============");
        }

    }
}
