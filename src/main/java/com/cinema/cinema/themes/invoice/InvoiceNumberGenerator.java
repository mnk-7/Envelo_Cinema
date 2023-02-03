package com.cinema.cinema.themes.invoice;

//TODO
public class InvoiceNumberGenerator {

    private static int counter = 0;

    public static String generateNumber() {
        counter++;
        return "inv-" + counter;
    }

}
