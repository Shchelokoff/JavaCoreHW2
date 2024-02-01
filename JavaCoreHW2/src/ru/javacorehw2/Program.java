package ru.javacorehw2;

import java.util.Random;
import java.util.Scanner;

public class Program {

    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static final char DOT_EMPTY = '*';
    private static final int WIN_COUNT = 3;

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    private static char[][] field;
    private static int fieldSizeX;
    private static int fieldSizeY;

    /**
     * Точка входа в приложение
     */
    public static void main(String[] args) {
        while (true) {
            initialize();
            printField();
            while (true) {
                humanTurn();
                printField();
                if (checkGameState(DOT_HUMAN, "Вы победили!"))
                    break;
                aiTurn();
                printField();
                if (checkGameState(DOT_AI, "Победил компьютер!"))
                    break;
            }
            System.out.print("Желаете сыграть еще раз? (Y - да): ");
            if (!scanner.next().equalsIgnoreCase("Y"))
                break;
        }
    }

    /**
     * Инициализация игрового поля
     */
    static void initialize() {
        fieldSizeY = 6;
        fieldSizeX = 6;

        field = new char[fieldSizeY][fieldSizeX];
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                field[y][x] = DOT_EMPTY;
            }
        }
    }

    /**
     * Печать текущего состояния игрового поля
     */
    private static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeX; i++) {
            System.out.print("-" + (i + 1));
        }
        System.out.println("-");

        for (int y = 0; y < fieldSizeY; y++) {
            System.out.print(y + 1 + "|");
            for (int x = 0; x < fieldSizeX; x++) {
                System.out.print(field[y][x] + "|");
            }
            System.out.println();
        }

        for (int i = 0; i < fieldSizeX * 2 + 2; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Ход человека
     */
    static void humanTurn() {
        int x;
        int y;

        do {
            System.out.print("Введите координаты хода X и Y (от 1 до 3)\nчерез пробел: ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));

        field[y][x] = DOT_HUMAN;
    }

    /**
     * Ход компьютера
     */
    static void aiTurn() {
        int x;
        int y;

        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));

        field[y][x] = DOT_AI;
    }

    /**
     * Проверка, является ли ячейка игрового поля пустой
     */
    static boolean isCellEmpty(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }

    /**
     * Проверка доступности ячейки игрового поля
     */
    static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }


    /**
     * Метод проверки состояния игры
     */
    static boolean checkGameState(char dot, String s) {
        if (check1(dot, WIN_COUNT)||check2(dot, WIN_COUNT)||check3(dot, WIN_COUNT)||check4(dot, WIN_COUNT)) {
            System.out.println(s);
            return true; // Игра закончилась
        }
        if (checkDraw()) {
            System.out.println("Ничья!");
            return true; // Игра закончилась
        }
        return false; // Игра продолжается
    }

    /**
     * Проверка на ничью
     */
    static boolean checkDraw() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(x, y))
                    return false;
            }
        }
        return true;
    }

    /**
     * Проверка победы игрока по горизонтали
     */

    static boolean check1(char dot, int WIN_COUNT) {
        for (int i = 0; i < fieldSizeY; i++) {
            int counter = 0;
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j] == dot) counter++;
                if (counter == WIN_COUNT) return true;
            }
        }
        return false;
    }
    /**
     * Проверка победы игрока по вертикали
     */
    static boolean check2(char dot, int WIN_COUNT) {
        for (int j = 0; j < fieldSizeY; j++) {
            int counter = 0;
            for (int i = 0; i < fieldSizeX; i++) {
                if (field[i][j] == dot) counter++;
                if (counter == WIN_COUNT) return true;
            }
        }
        return false;
    }
    /**
     * Проверка победы игрока по главной диагонали
     */
    static boolean check3(char dot, int WIN_COUNT) {
        for (int k = 0; k < fieldSizeY + fieldSizeX - 1; k++) {
            int counter = 0;
            for (int i = Math.min(k, fieldSizeY - 1); i >= 0 && k - i < fieldSizeX; i--) {
                int j = k - i;
                if (field[i][j] == dot) counter++;
                if (counter == WIN_COUNT) return true;
            }
        }
        return false;
    }
    /**
     * Проверка победы игрока по побочной диагонали
     */
    static boolean check4(char dot, int WIN_COUNT) {
        for (int k = 0; k < fieldSizeY + fieldSizeX - 1; k++) {
            int counter = 0;
            for (int i = Math.max(0, k - fieldSizeY + 1); i < fieldSizeY && k - i >= 0; i++) {
                int j = k - i;
                if (field[i][j] == dot) counter++;
                if (counter == WIN_COUNT) return true;
            }
        }
        return false;
    }
}