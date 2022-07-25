package com.mikhraly;

import java.io.IOException;

public class SeaBattle {
    public static void main(String[] args) {
        BattleField battleField = new BattleField();
        battleField.printField();

        //while (true) {
            try {
                battleField.setShip("А,6 Б,6 В,6");
                battleField.setShip("К,10");
                battleField.setShip("Е,3 е,4 е,5 е,6");
                battleField.setShip("к,1 к,2 к,3");
                battleField.setShip("а,1 а,2 а,3");


                battleField.printField();
            } catch (IOException e) {
                System.out.print("Ошибка.\t" + e.getMessage() + "\n");
            }

        //}

    }
}
