package ru.lexender.project.console.receiver;

import java.util.Optional;
import java.util.Scanner;

public class ConsoleReceiver implements IReceive {
    public String receive() {
        Scanner scanner = new Scanner(System.in);
        Optional<String> line = Optional.ofNullable(scanner.nextLine());
        return line.orElse("");
    }
}
