package ru.lexender.project.server.handler.command;

public enum CommandStatus {
    SUCCESS,
    IN_QUEUE,
    WAITING_FOR_ARGUMENT,
    FAIL;
}
