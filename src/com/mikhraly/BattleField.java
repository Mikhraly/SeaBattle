package com.mikhraly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BattleField {
    private String[][] field = {
            {"  ", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�"},
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
            {"  ", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�"},
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
        // ������������� ��������� ������ ��������� (�������� "A,5 �,5") � List �������� ("5,1" "5,2")
        List<String> ship = convertCoordinate(shipCoordinate);

        checkCoordinates(ship);

        if (isFreeHalo(ship)) {
            switch (ship.size()) {
                case 1 -> {
                    if (countShip1 > 0) {
                        countShip1--;
                        ships1.add(new Ship(ship));
                    }
                    else throw new IOException("��� ��������� �������� ����� ����");}
                case 2 -> {
                    if (countShip2 > 0) {
                        countShip2--;
                        ships2.add(new Ship(ship));
                    }
                    else throw new IOException("��� ��������� �������� ����� ����");}
                case 3 -> {
                    if (countShip3 > 0) {
                        countShip3--;
                        ships3.add(new Ship(ship));
                    }
                    else throw new IOException("��� ��������� �������� ����� ����");}
                case 4 -> {
                    if (countShip4 > 0) {
                        countShip4--;
                        ships4.add(new Ship(ship));
                    }
                    else throw new IOException("��� ��������� �������� ����� ����");}
                default -> {}
            }
            for (String coordinate : ship)
                field[Integer.parseInt(coordinate.split(",")[0])]
                        [Integer.parseInt(coordinate.split(",")[1])] = "O";
        }
        else throw new IOException("��� ����� ��� ������� �� �������� �����������");
    }

    private void checkCoordinates(List<String> ship) throws IOException {
        checkCoordinateCorrect(ship);
        checkCoordinateRepeat(ship);

        if (ship.size() == 0)
            throw new IOException("������� ������ ������");
        else if (ship.size() > 4)
            throw new IOException("������� ����� ���������");

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
                throw new IOException("������� ������������� ���������");
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
                throw new IOException("�������� ������ ���������");
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
            throw new IOException("���������� �� �� ����� �����");
    }

    private String convertColumnWordToNumber(String column) throws IOException {
        switch (column) {
            case "�", "�" -> {return "1";}
            case "�", "�" -> {return "2";}
            case "�", "�" -> {return "3";}
            case "�", "�" -> {return "4";}
            case "�", "�" -> {return "5";}
            case "�", "�" -> {return "6";}
            case "�", "�" -> {return "7";}
            case "�", "�" -> {return "8";}
            case "�", "�" -> {return "9";}
            case "�", "�" -> {return "10";}
            default -> throw new IOException("������� �������� ����������");
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
        // ��������� ��� ���������� ������� - ������������� ��� �����������
        int countEquals = 0;
        for (int i = 0; i < ship.size()-1; i++) {
            if (ship.get(0).split(",")[0].equals(ship.get(i+1).split(",")[0]))
                countEquals++;
        }

        if (countEquals != 0) { // �������������� �������
            // ����������� �� �����������
            for (int i = 0; i < ship.size()-1; i++) {
                for (int k = i+1; k < ship.size(); k++) {
                    if (Integer.parseInt(ship.get(k).split(",")[1]) <
                            Integer.parseInt(ship.get(i).split(",")[1]))
                        Collections.swap(ship, k, i);
                }
            }
            // ��������� �� �������
            for (int i = 0; i < ship.size()-1; i++) {
                if ((Integer.parseInt(ship.get(i+1).split(",")[1]) -
                        Integer.parseInt(ship.get(i).split(",")[1])) > 1)
                    throw new IOException("���������� ������� � ��������");
            }

        } else { // ������������ �������
            // ����������� �� �����������
            for (int i = 0; i < ship.size()-1; i++) {
                for (int k = i+1; k < ship.size(); k++) {
                    if (Integer.parseInt(ship.get(k).split(",")[0]) <
                            Integer.parseInt(ship.get(i).split(",")[0]))
                        Collections.swap(ship, k, i);
                }
            }
            // ��������� �� �������
            for (int i = 0; i < ship.size()-1; i++) {
                if ((Integer.parseInt(ship.get(i+1).split(",")[0]) -
                        Integer.parseInt(ship.get(i).split(",")[0])) > 1)
                    throw new IOException("���������� ������� � ��������");
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
                System.out.println("������");
                return false;
            }
            case "X" -> {
                System.out.println("������. ���� ��� ���� ��������!");
                return false;
            }
            case "-" -> {
                System.out.println("������. ��� ��� � ��� ����!");
                return false;
            }
            default -> {
                System.out.println("������! ����������� �������� � ����!");
                return false;
            }
        }
    }

    private void removeCoordinateFromShipList(String coordinate, List<Ship> ships) {
        for (int i = 0; i < ships.size(); i++) {
            ships.get(i).getShipCoordinate().remove(coordinate);
            if (ships.get(i).getShipCoordinate().size() == 0) {
                System.out.println("������ �������!");
                for (String halo : ships.get(i).getHaloCoordinate()) {
                    fieldMask[Integer.parseInt(halo.split(",")[0])]
                            [Integer.parseInt(halo.split(",")[1])] = "-";
                }
            } else {
                System.out.println("���������!");
            }
        }
    }


    private class Ship {
        private List<String> shipCoordinate;
        private List<String> haloCoordinate;

        // ��������� �������� ����������� ����������
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

