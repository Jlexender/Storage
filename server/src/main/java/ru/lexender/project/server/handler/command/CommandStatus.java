package ru.lexender.project.server.handler.command;

public enum CommandStatus {
    SUCCESS,
    NOT_USED,
    IN_PROCESS,
    WAITING_FOR_ARGUMENT,
    FAIL;
}
