package com.mikhraly;

import java.io.IOException;

public class SeaBattle {
    public static void main(String[] args) {
        BattleField battleField = new BattleField();
        battleField.printField();

        //while (true) {
            try {
                battleField.setShip("�,6 �,6 �,6");
                battleField.setShip("�,10");
                battleField.setShip("�,3 �,4 �,5 �,6");
                battleField.setShip("�,1 �,2 �,3");
                battleField.setShip("�,1 �,2 �,3");


                battleField.printField();
            } catch (IOException e) {
                System.out.print("������.\t" + e.getMessage() + "\n");
            }

        //}

    }
}
