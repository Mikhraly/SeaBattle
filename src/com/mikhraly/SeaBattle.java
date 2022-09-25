package com.mikhraly;

import java.io.IOException;
import java.util.*;

public class SeaBattle {
    public static void main(String[] args) {
        System.out.println("\n\t--- ������� ��� ---\n");

        BattleField field1 = new BattleField();
        field1.printField();
        Scanner scanner = new Scanner(System.in);

        while (field1.getCountShip1() + field1.getCountShip2() + field1.getCountShip3() + field1.getCountShip4() != 0) {
            try {
                System.out.println("\n������ �����, ����� �������. ������ �����, �� ������! " +
                        "�������: \"�1\" ��� \"�1 �2\" � �.�.");
                System.out.print("�������� ��������: " +
                        "��������������� - " + field1.getCountShip4() + ", " +
                        "������������ - " + field1.getCountShip3() + ", " +
                        "������������ - " + field1.getCountShip2() + ", " +
                        "������������ - " + field1.getCountShip1() +
                        "\n--> ");
                field1.setShip(scanner.nextLine());
                field1.printField();

            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        for (int i = 0; i < 10; i++) System.out.println("\t\t\t|||\n");


        BattleField field2 = new BattleField();
        field2.printField();

        while (field2.getCountShip1() + field2.getCountShip2() + field2.getCountShip3() + field2.getCountShip4() != 0) {
            try {
                System.out.println("\n������ �����, ����� �������. ������ �����, �� ������! " +
                        "�������: \"�1\" ��� \"�1 �2\" � �.�.");
                System.out.print("�������� ��������: " +
                        "��������������� - " + field2.getCountShip4() + ", " +
                        "������������ - " + field2.getCountShip3() + ", " +
                        "������������ - " + field2.getCountShip2() + ", " +
                        "������������ - " + field2.getCountShip1() +
                        "\n--> ");
                field2.setShip(scanner.nextLine());
                field2.printField();

            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        for (int i = 0; i < 10; i++) System.out.println("\t\t\t|||\n");


        Random random = new Random();
        int player = random.nextInt(1,3);
        if (player == 1) {
            System.out.println("�������� ����� 1");
        } else if (player == 2) {
            System.out.println("�������� ����� 2");
        }

        while (player != 0) {
            if (player == 1) {
                field2.printFieldMask();

                int fireResult;
                while (true) {
                    System.out.print("\n����� �� ���������� --> ");
                    String coordinate = scanner.nextLine();
                    try {
                        fireResult = field2.fireShip(coordinate);
                        break;
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("������ �����. �������� ���...");
                    }
                }

                if (fireResult == 1) {
                    System.out.println("������ ����� ����������");
                } else if (fireResult == -1) {
                    System.out.println("������!!! ������ ����� �������!");
                    player = 0;
                } else {
                    System.out.println("��� ��������� �� ������� ������");
                    player = 2;
                }
            }

            if (player == 2) {
                field1.printFieldMask();

                int fireResult;
                while (true) {
                    System.out.print("\n����� �� ���������� --> ");
                    String coordinate = scanner.nextLine();
                    try {
                        fireResult = field1.fireShip(coordinate);
                        break;
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("������ �����. �������� ���...");
                    }
                }

                if (fireResult == 1) {
                    System.out.println("������ ����� ����������");
                } else if (fireResult == -1) {
                    System.out.println("������!!! ������ ����� �������!");
                    player = 0;
                } else {
                    System.out.println("��� ��������� � ������� ������");
                    player = 1;
                }
            }
        }

        scanner.close();
    }

}
