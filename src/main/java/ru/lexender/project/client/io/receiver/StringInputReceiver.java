package ru.lexender.project.client.io.receiver;

import ru.lexender.project.client.io.Input;
import ru.lexender.project.client.io.StringInput;

import java.util.Optional;
import java.util.Scanner;

public class StringInputReceiver implements IReceive {
    public Input receive() {
        Scanner scanner = new Scanner(System.in);
        Optional<String> line = Optional.ofNullable(scanner.nextLine());
        return new StringInput(line.orElse(""));
    }
}