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

public class AccessLevel {

    public static final Command[] ALL = {
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
    };

    public static final Command[] GUEST = {
            ALL[0],
            ALL[1],
            ALL[2],
            ALL[3],
            ALL[4],
            ALL[5],
            ALL[6],
            ALL[7],
            ALL[8],
            ALL[9],
            ALL[10],
            ALL[11],
            ALL[12],
            ALL[13],
    };
}
