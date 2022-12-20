package com.cinema.cinema.utils;

//TODO
public class QrCodeGenerator {

    private static int counter = 0;

    public static String generateQr(){
        counter++;
        return "qr-code-" + counter;
    }
}
