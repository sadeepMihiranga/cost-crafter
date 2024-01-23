package com.cost.crafter;

import com.cost.crafter.menu.StartMenu;

public class Main {

    public static void main(String[] args) {
        try {
            new StartMenu().showStartMenu();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void consoleFunc() {
    }
}
