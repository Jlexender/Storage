package ru.lexender.project;

import ru.lexender.project.file.variable.EnvironmentVariable;

public class Main {
    public static void main(String[] args) {
        String variableName = "LAB";

        try {
            EnvironmentVariable variable = new EnvironmentVariable(variableName);
            ConsoleApp application = new ConsoleApp(variable);
            application.run();
        } catch (NullPointerException exception) {
            System.out.printf("Please add '%s' environment variable\n", variableName);
        }
    }
}