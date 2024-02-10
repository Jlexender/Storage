package ru.lexender.project;

import ru.lexender.project.file.enviroment.EnvironmentVariable;

public class Main {
    public static void main(String[] args) {
        try {
            EnvironmentVariable variable = new EnvironmentVariable("LAB");
            ConsoleApp application = new ConsoleApp(variable);
            application.run();
        } catch (NullPointerException exception) {
            System.out.println("Please add 'LAB' environment variable");
        }
    }
}