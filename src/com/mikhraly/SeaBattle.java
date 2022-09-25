package com.mikhraly;

import java.io.IOException;
import java.util.*;

public class SeaBattle {
    public static void main(String[] args) {
        System.out.println("\n\t--- Морской бой ---\n");

        BattleField field1 = new BattleField();
        field1.printField();
        Scanner scanner = new Scanner(System.in);

        while (field1.getCountShip1() + field1.getCountShip2() + field1.getCountShip3() + field1.getCountShip4() != 0) {
            try {
                System.out.println("\nПервый игрок, вводи корабли. Второй игрок, не смотри! " +
                        "Примеры: \"А1\" или \"г1 г2\" и т.д.");
                System.out.print("Осталось кораблей: " +
                        "четырехпалубных - " + field1.getCountShip4() + ", " +
                        "трехпалубных - " + field1.getCountShip3() + ", " +
                        "двухпалубных - " + field1.getCountShip2() + ", " +
                        "однопалубных - " + field1.getCountShip1() +
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
                System.out.println("\nВторой игрок, вводи корабли. Первый игрок, не смотри! " +
                        "Примеры: \"А1\" или \"г1 г2\" и т.д.");
                System.out.print("Осталось кораблей: " +
                        "четырехпалубных - " + field2.getCountShip4() + ", " +
                        "трехпалубных - " + field2.getCountShip3() + ", " +
                        "двухпалубных - " + field2.getCountShip2() + ", " +
                        "однопалубных - " + field2.getCountShip1() +
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
            System.out.println("Начинает игрок 1");
        } else if (player == 2) {
            System.out.println("Начинает игрок 2");
        }

        while (player != 0) {
            if (player == 1) {
                field2.printFieldMask();

                int fireResult;
                while (true) {
                    System.out.print("\nОгонь по координате --> ");
                    String coordinate = scanner.nextLine();
                    try {
                        fireResult = field2.fireShip(coordinate);
                        break;
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Ошибка ввода. Попробуй ещё...");
                    }
                }

                if (fireResult == 1) {
                    System.out.println("Первый игрок продолжает");
                } else if (fireResult == -1) {
                    System.out.println("ПОБЕДА!!! Первый игрок выиграл!");
                    player = 0;
                } else {
                    System.out.println("Ход переходит ко второму игроку");
                    player = 2;
                }
            }

            if (player == 2) {
                field1.printFieldMask();

                int fireResult;
                while (true) {
                    System.out.print("\nОгонь по координате --> ");
                    String coordinate = scanner.nextLine();
                    try {
                        fireResult = field1.fireShip(coordinate);
                        break;
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Ошибка ввода. Попробуй ещё...");
                    }
                }

                if (fireResult == 1) {
                    System.out.println("Второй игрок продолжает");
                } else if (fireResult == -1) {
                    System.out.println("ПОБЕДА!!! Второй игрок выиграл!");
                    player = 0;
                } else {
                    System.out.println("Ход переходит к первому игроку");
                    player = 1;
                }
            }
        }

        scanner.close();
    }

}
