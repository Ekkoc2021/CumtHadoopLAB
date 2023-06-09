package com.ekko.test;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomNumberGenerator {
    public static void main(String[] args) {
        Set<Integer> randomNumbers = generateRandomNumbers(1, 10000000, 1000);
        writeToFile(randomNumbers, "Lab4\\int1.txt");
    }

    public static Set<Integer> generateRandomNumbers(int min, int max, int count) {
        Set<Integer> numbers = new HashSet<>();
        Random random = new Random();

        while (numbers.size() < count) {
            int number = random.nextInt(max - min + 1) + min;
            numbers.add(number);
        }

        return numbers;
    }

    public static void writeToFile(Set<Integer> numbers, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int number : numbers) {
                writer.write(Integer.toString(number));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}