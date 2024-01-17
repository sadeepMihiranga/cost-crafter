package com.cost.crafter.menu;

import java.io.BufferedReader;
import java.io.IOException;

public class BaseMenuHandler {

    protected void exit() {
        System.out.println("Thank you !");
        System.exit(0);
    }

    protected int checkSelectedOption(BufferedReader br, int defaultInvalidOption) throws IOException {
        try {
            return Integer.parseInt(br.readLine());
        } catch (NumberFormatException ex) {
            return defaultInvalidOption;
        }
    }
}
