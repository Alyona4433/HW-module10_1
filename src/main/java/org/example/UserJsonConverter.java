package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserJsonConverter {

    public static void main(String[] args) {
        List<User> users = readUsersFromFile("file.txt");
        writeUsersToJsonFile(users, "user.json");
    }

    public static List<User> readUsersFromFile(String fileName) {
        List<User> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String headerLine = reader.readLine();
            String[] headers = headerLine.split(" ");

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(" ");
                User user = new User();
                for (int i = 0; i < headers.length; i++) {
                    if (i < values.length) {
                        if (headers[i].equals("name")) {
                            user.setName(values[i]);
                        } else if (headers[i].equals("age")) {
                            user.setAge(Integer.parseInt(values[i]));
                        }
                    }
                }
                users.add(user);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return users;
    }

    public static void writeUsersToJsonFile(List<User> users, String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (FileWriter writer = new FileWriter(fileName)) {
            objectMapper.writeValue(writer, users);
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }
}
