package ru.lexender.project.server.handler.command;

import ru.lexender.project.server.handler.builder.list.PersonBuilder;
import ru.lexender.project.server.handler.builder.list.StudyGroupBuilder;
import ru.lexender.project.server.handler.command.list.Add;
import ru.lexender.project.server.handler.command.list.AddIfMin;
import ru.lexender.project.server.handler.command.list.Clear;
import ru.lexender.project.server.handler.command.list.CountGreaterThanGroupAdmin;
import ru.lexender.project.server.handler.command.list.Exit;
import ru.lexender.project.server.handler.command.list.FilterStartsWithName;
import ru.lexender.project.server.handler.command.list.Help;
import ru.lexender.project.server.handler.command.list.History;
import ru.lexender.project.server.handler.command.list.Info;
import ru.lexender.project.server.handler.command.list.PrintFieldAscendingSemesterEnum;
import ru.lexender.project.server.handler.command.list.RemoveById;
import ru.lexender.project.server.handler.command.list.RemoveGreater;
import ru.lexender.project.server.handler.command.list.Save;
import ru.lexender.project.server.handler.command.list.Show;
import ru.lexender.project.server.handler.command.list.UpdateId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccessLevel {

    public static final List<Command> ALL = new ArrayList<>(Arrays.asList(
            new Help(),
            new Info(),
            new Show(),
            new Exit(),
            new History(),
            new Add(new StudyGroupBuilder()),
            new Clear(),
            new UpdateId(new StudyGroupBuilder()),
            new RemoveById(),
            new AddIfMin(new StudyGroupBuilder()),
            new RemoveGreater(new StudyGroupBuilder()),
            new FilterStartsWithName(),
            new CountGreaterThanGroupAdmin(new PersonBuilder()),
            new PrintFieldAscendingSemesterEnum(),
            new Save()
    ));

    public static final List<Command> GUEST = new ArrayList<>(Arrays.asList(
            ALL.get(0),
            ALL.get(1),
            ALL.get(2),
            ALL.get(3),
            ALL.get(4),
            ALL.get(5),
            ALL.get(6),
            ALL.get(7),
            ALL.get(8),
            ALL.get(9),
            ALL.get(10),
            ALL.get(11),
            ALL.get(12),
            ALL.get(13)
        ));
}
