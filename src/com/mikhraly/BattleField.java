package com.mikhraly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BattleField {
    private String[][] field = {
        {"  ", "А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "К"},
        {" 1", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
        {" 2", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
        {" 3", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
        {" 4", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
        {" 5", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
        {" 6", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
        {" 7", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
        {" 8", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
        {" 9", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
        {"10", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "}
    };

    private static int countFreeShip4 = 1;
    private static int countFreeShip3 = 2;
    private static int countFreeShip2 = 3;
    private static int countFreeShip1 = 4;

    public static int getCountFreeShip4() {
        return countFreeShip4;
    }

    public static int getCountFreeShip3() {
        return countFreeShip3;
    }

    public static int getCountFreeShip2() {
        return countFreeShip2;
    }

    public static int getCountFreeShip1() {
        return countFreeShip1;
    }

    public void printField() {
        for (int str = 0; str < 11; str++) {
            for (int col = 0; col < 11; col++) {
                System.out.print(field[str][col] + "  ");
            }
            System.out.println();
        }
    }

    public void setShip(String shipCoordinates) throws IOException {
        String[] shipPoints = shipCoordinates.split(" ");
        int shipSize = shipPoints.length;

        for (String point : shipPoints) {
            int countEquals = 0;
            for (int i = 0; i < shipSize; i++) {
                if (point.equals(shipPoints[i]))
                    countEquals++;
            }
            if (countEquals > 1)
                throw new IOException("Наличие повторяющихся координат");
        }

        int[] str = new int[shipSize];
        int[] col = new int[shipSize];

        try {
            for (int i = 0; i < shipSize; i++) {
                str[i] = convertStringToNumber(shipPoints[i].split(",")[1]);
                col[i] = convertColumnToNumber(shipPoints[i].split(",")[0]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IOException("Неверный формат координат");
        }

        if (shipSize == 0)
            throw new IOException("Введены пустые данные");
        else if (shipSize > 4)
            throw new IOException("Слишком много координат");

        int strCountEquals = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[0] == str[i])
                strCountEquals++;
        }
        int colCountEquals = 0;
        for (int i = 0; i < col.length; i++) {
            if (col[0] == col[i])
                colCountEquals++;
        }
        if (strCountEquals != str.length && colCountEquals != col.length)
            throw new IOException("Координаты не на одной линии");

        if (shipSize > 1) {
            if (strCountEquals == str.length) {
                arrangeForRise(col);
                for (int i = 0; i < col.length - 1; i++) {
                    if ((col[i+1] - col[i]) > 1)
                        throw new IOException("Координаты введены с разрывом");
                }
            }
            if (colCountEquals == col.length) {
                arrangeForRise(str);
                for (int i = 0; i < str.length - 1; i++) {
                    if ((str[i+1] - str[i]) > 1)
                        throw new IOException("Координаты введены с разрывом");
                }
            }
        }

        if (isFreeHalo(str, col)) {
            switch (shipSize) {
                case 1 -> {
                    if (countFreeShip1 > 0) countFreeShip1--;
                    else throw new IOException("Нет свободных кораблей этого типа");}
                case 2 -> {
                    if (countFreeShip2 > 0) countFreeShip2--;
                    else throw new IOException("Нет свободных кораблей этого типа");}
                case 3 -> {
                    if (countFreeShip3 > 0) countFreeShip3--;
                    else throw new IOException("Нет свободных кораблей этого типа");}
                case 4 -> {
                    if (countFreeShip4 > 0) countFreeShip4--;
                    else throw new IOException("Нет свободных кораблей этого типа");}
                default -> {}
            }
            for (int i = 0; i < shipSize; i++)
                field[str[i]][col[i]] = "O";
        }
        else throw new IOException("Нет места для корабля по заданным координатам");
    }

    private int convertStringToNumber(String string) throws IOException {
        switch (string) {
            case "1" -> {return 1;}
            case "2" -> {return 2;}
            case "3" -> {return 3;}
            case "4" -> {return 4;}
            case "5" -> {return 5;}
            case "6" -> {return 6;}
            case "7" -> {return 7;}
            case "8" -> {return 8;}
            case "9" -> {return 9;}
            case "10" -> {return 10;}
            default -> throw new IOException("Введены неверные координаты");
        }
    }

    private int convertColumnToNumber(String column) throws IOException {
        switch (column) {
            case "А", "а" -> {return 1;}
            case "Б", "б" -> {return 2;}
            case "В", "в" -> {return 3;}
            case "Г", "г" -> {return 4;}
            case "Д", "д" -> {return 5;}
            case "Е", "е" -> {return 6;}
            case "Ж", "ж" -> {return 7;}
            case "З", "з" -> {return 8;}
            case "И", "и" -> {return 9;}
            case "К", "к" -> {return 10;}
            default -> throw new IOException("Введены неверные координаты");
        }
    }

    private boolean isFreeHalo(int[] shipStr, int[] shipCol) {
        if (shipStr.length != shipCol.length || shipStr.length == 0)
            throw new IllegalArgumentException();

        for (int i = 0; i < shipStr.length; i++) {
            int currentStr = shipStr[i];
            int currentCol = shipCol[i];

            for (int s = currentStr-1; s <= currentStr+1; s++) {
                for (int c = currentCol-1; c <= currentCol+1; c++) {
                    if (s > 0 && s < 11 && c > 0 && c < 11 && !isShipContainCoordinates(s, c, shipStr, shipCol)) {
                        if (!field[s][c].equals(" "))
                            return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean isShipContainCoordinates(int x, int y, int[] xArr, int[] yArr) {
        for (int i = 0; i < xArr.length; i++) {
            if (x == xArr[i] && y == yArr[i])
                return true;
        }
        return false;
    }

    private void arrangeForRise(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int k = i; k < array.length; k++) {
                if (array[k] < array[i]) {
                    int temp = array[i];
                    array[i] = array[k];
                    array[k] = temp;
                }
            }
        }
    }
}

