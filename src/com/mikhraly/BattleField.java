package com.mikhraly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    private String[][] fieldMask = {
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

    private List<Ship> ships1 = new ArrayList<>();
    private List<Ship> ships2 = new ArrayList<>();
    private List<Ship> ships3 = new ArrayList<>();
    private List<Ship> ships4 = new ArrayList<>();

    public List<Ship> getShips1() {
        return ships1;
    }

    public List<Ship> getShips2() {
        return ships2;
    }

    public List<Ship> getShips3() {
        return ships3;
    }

    public List<Ship> getShips4() {
        return ships4;
    }

    public void printField() {
        System.out.println();
        for (int str = 0; str < 11; str++) {
            for (int col = 0; col < 11; col++) {
                System.out.print(field[str][col] + "  ");
            }
            System.out.println();
        }
    }

    public void printFieldMask() {
        System.out.println();
        for (int str = 0; str < 11; str++) {
            for (int col = 0; col < 11; col++) {
                System.out.print(fieldMask[str][col] + "  ");
            }
            System.out.println();
        }
    }

    private int countShip1 = 4;
    private int countShip2 = 3;
    private int countShip3 = 2;
    private int countShip4 = 1;

    public int getCountShip1() {
        return countShip1;
    }

    public int getCountShip2() {
        return countShip2;
    }

    public int getCountShip3() {
        return countShip3;
    }

    public int getCountShip4() {
        return countShip4;
    }


    public void setShip(String shipCoordinate) throws IOException {
        // Преобразовать введенную строку координат (например "A,5 Б,5") в List стрингов ("5,1" "5,2")
        List<String> ship = convertCoordinate(shipCoordinate);

        checkCoordinates(ship);

        if (isFreeHalo(ship)) {
            switch (ship.size()) {
                case 1 -> {
                    if (countShip1 > 0) {
                        countShip1--;
                        ships1.add(new Ship(ship));
                    }
                    else throw new IOException("Нет свободных кораблей этого типа");}
                case 2 -> {
                    if (countShip2 > 0) {
                        countShip2--;
                        ships2.add(new Ship(ship));
                    }
                    else throw new IOException("Нет свободных кораблей этого типа");}
                case 3 -> {
                    if (countShip3 > 0) {
                        countShip3--;
                        ships3.add(new Ship(ship));
                    }
                    else throw new IOException("Нет свободных кораблей этого типа");}
                case 4 -> {
                    if (countShip4 > 0) {
                        countShip4--;
                        ships4.add(new Ship(ship));
                    }
                    else throw new IOException("Нет свободных кораблей этого типа");}
                default -> {}
            }
            for (String coordinate : ship)
                field[Integer.parseInt(coordinate.split(",")[0])]
                        [Integer.parseInt(coordinate.split(",")[1])] = "O";
        }
        else throw new IOException("Нет места для корабля по заданным координатам");
    }

    private void checkCoordinates(List<String> ship) throws IOException {
        checkCoordinateCorrect(ship);
        checkCoordinateRepeat(ship);

        if (ship.size() == 0)
            throw new IOException("Введены пустые данные");
        else if (ship.size() > 4)
            throw new IOException("Слишком много координат");

        checkCoordinateLinear(ship);

        if (ship.size() > 1)
            checkCoordinatesWithoutSpace(ship);
    }

    private void checkCoordinateRepeat(List<String> ship) throws IOException {
        int countEquals = 0;
        for (String str1 : ship) {
            for (String str2 : ship) {
                if (str1.equals(str2))
                    countEquals++;
            }
            if (countEquals > 1)
                throw new IOException("Наличие повторяющихся координат");
            else
                countEquals = 0;
        }
    }

    private List<String> convertCoordinate(String shipCoordinate) throws IOException {
        List<String> coordinate = new ArrayList<>(Arrays.asList(shipCoordinate.split(" ")));
        List<String> converted = new ArrayList<>();
        for (String current : coordinate) {
            String str = current.split(",")[1];
            String col = convertColumnWordToNumber(current.split(",")[0]);
            converted.add(str + "," + col);
        }
        return converted;
    }

    private void checkCoordinateCorrect(List<String> shipCoordinate) throws IOException {
        for (String current : shipCoordinate) {
            if (Integer.parseInt(current.split(",")[1]) > 0 &&
                    Integer.parseInt(current.split(",")[1]) < 11 &&
                    Integer.parseInt(current.split(",")[0]) > 0 &&
                    Integer.parseInt(current.split(",")[0]) < 11)
                continue;
            else
                throw new IOException("Неверный формат координат");
        }
    }

    private void checkCoordinateLinear(List<String> shipCoordinate) throws IOException {
        int strCountEquals = 0;
        int colCountEquals = 0;
        for (int i = 0; i < shipCoordinate.size(); i++) {
            if (shipCoordinate.get(0).split(",")[0].equals(shipCoordinate.get(i).split(",")[0]))
                strCountEquals++;
            if (shipCoordinate.get(0).split(",")[1].equals(shipCoordinate.get(i).split(",")[1]))
                colCountEquals++;
        }
        if (strCountEquals != shipCoordinate.size() && colCountEquals != shipCoordinate.size())
            throw new IOException("Координаты не на одной линии");
    }

    private String convertColumnWordToNumber(String column) throws IOException {
        switch (column) {
            case "А", "а" -> {return "1";}
            case "Б", "б" -> {return "2";}
            case "В", "в" -> {return "3";}
            case "Г", "г" -> {return "4";}
            case "Д", "д" -> {return "5";}
            case "Е", "е" -> {return "6";}
            case "Ж", "ж" -> {return "7";}
            case "З", "з" -> {return "8";}
            case "И", "и" -> {return "9";}
            case "К", "к" -> {return "10";}
            default -> throw new IOException("Введены неверные координаты");
        }
    }

    private boolean isFreeHalo(List<String> ship) {
        for (String coordinate : ship) {
            int str = Integer.parseInt(coordinate.split(",")[0]);
            int col = Integer.parseInt(coordinate.split(",")[1]);

            for (int s = str-1; s <= str+1; s++) {
                for (int c = col-1; c <= col+1; c++) {
                    if (s > 0 && s < 11 && c > 0 && c < 11) {
                        if (
                                (!ship.contains(Integer.toString(s) + "," + Integer.toString(c)) &&
                                        !field[s][c].equals(" ")) || field[s][c].equals("O")
                        )
                            return false;
                    }
                }
            }
        }
        return true;
    }

    private void checkCoordinatesWithoutSpace(List<String> ship) throws IOException {
        // Проверить как расположен корабль - горизонтально или вертикально
        int countEquals = 0;
        for (int i = 0; i < ship.size()-1; i++) {
            if (ship.get(0).split(",")[0].equals(ship.get(i+1).split(",")[0]))
                countEquals++;
        }

        if (countEquals != 0) { // Горизонтальный корабль
            // Упорядочить по возрастанию
            for (int i = 0; i < ship.size()-1; i++) {
                for (int k = i+1; k < ship.size(); k++) {
                    if (Integer.parseInt(ship.get(k).split(",")[1]) <
                            Integer.parseInt(ship.get(i).split(",")[1]))
                        Collections.swap(ship, k, i);
                }
            }
            // Проверить на разрывы
            for (int i = 0; i < ship.size()-1; i++) {
                if ((Integer.parseInt(ship.get(i+1).split(",")[1]) -
                        Integer.parseInt(ship.get(i).split(",")[1])) > 1)
                    throw new IOException("Координаты введены с разрывом");
            }

        } else { // Вертикальный корабль
            // Упорядочить по возрастанию
            for (int i = 0; i < ship.size()-1; i++) {
                for (int k = i+1; k < ship.size(); k++) {
                    if (Integer.parseInt(ship.get(k).split(",")[0]) <
                            Integer.parseInt(ship.get(i).split(",")[0]))
                        Collections.swap(ship, k, i);
                }
            }
            // Проверить на разрывы
            for (int i = 0; i < ship.size()-1; i++) {
                if ((Integer.parseInt(ship.get(i+1).split(",")[0]) -
                        Integer.parseInt(ship.get(i).split(",")[0])) > 1)
                    throw new IOException("Координаты введены с разрывом");
            }
        }
    }

    public boolean fireShip(String coordinate) throws IOException {
        List<String> coordinateList = convertCoordinate(coordinate);
        checkCoordinates(coordinateList);
        coordinate = coordinateList.get(0);
        int str = Integer.parseInt(coordinate.split(",")[0]);
        int col = Integer.parseInt(coordinate.split(",")[1]);

        switch (field[str][col]) {
            case "O" -> {
                fieldMask[str][col] = "X";
                removeCoordinateFromShipList(coordinate, ships1);
                removeCoordinateFromShipList(coordinate, ships2);
                removeCoordinateFromShipList(coordinate, ships3);
                removeCoordinateFromShipList(coordinate, ships4);
                return true;
            }
            case " " -> {
                fieldMask[str][col] = "-";
                System.out.println("Промах");
                return false;
            }
            case "X" -> {
                System.out.println("Промах. Зона уже была поражена!");
                return false;
            }
            case "-" -> {
                System.out.println("Промах. Уже бил в эту зону!");
                return false;
            }
            default -> {
                System.out.println("Ошибка! Неизвестное значение в поле!");
                return false;
            }
        }
    }

    private void removeCoordinateFromShipList(String coordinate, List<Ship> ships) {
        for (int i = 0; i < ships.size(); i++) {
            ships.get(i).getShipCoordinate().remove(coordinate);
            if (ships.get(i).getShipCoordinate().size() == 0) {
                System.out.println("Утопил корабль!");
                for (String halo : ships.get(i).getHaloCoordinate()) {
                    fieldMask[Integer.parseInt(halo.split(",")[0])]
                            [Integer.parseInt(halo.split(",")[1])] = "-";
                }
            } else {
                System.out.println("Попадание!");
            }
        }
    }


    private class Ship {
        private List<String> shipCoordinate;
        private List<String> haloCoordinate;

        // Принимает заведомо проверенные координаты
        public Ship(List<String> shipCoordinate) {
            this.shipCoordinate = shipCoordinate;
            this.haloCoordinate = calculateHaloCoordinate(shipCoordinate);
        }

        public List<String> getShipCoordinate() {
            return shipCoordinate;
        }

        public List<String> getHaloCoordinate() {
            return haloCoordinate;
        }

        private List<String> calculateHaloCoordinate(List<String> shipCoordinate) {
            List<String> haloCoordinate = new ArrayList<>();

            for (String coordinate : shipCoordinate) {
                int string = Integer.parseInt(coordinate.split(",")[0]);
                int column = Integer.parseInt(coordinate.split(",")[1]);

                for (int s = string-1; s <= string+1; s++) {
                    for (int c = column-1; c <= column+1; c++) {
                        if (s > 0 && s < 11 && c > 0 && c < 11 && !shipCoordinate.contains(s + "," + c))
                            haloCoordinate.add(s + "," + c);
                    }
                }
            }
            return haloCoordinate;
        }

    }
}

