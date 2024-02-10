package ru.lexender.project;

import ru.lexender.project.file.enviroment.EnvironmentVariable;

public class Main {
    public static void main(String[] args) {
        EnvironmentVariable variable = new EnvironmentVariable("LAB");
        ConsoleApp application = new ConsoleApp(variable);
        application.run();
        
        // add group 15 17 40 4 EVENING_CLASSES FIRST dvk 80 GREEN BLACK ITALY
    }
}