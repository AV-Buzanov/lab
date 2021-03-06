package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.ReorderServerCommand;
import org.example.enums.SortStatus;
import org.example.model.Message;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ReorderCommand extends AbstractCommand {

    @Override
    public String command() {
        return "reorder";
    }

    @Override
    public String description() {
        return "Отсортировать коллекцию в порядке, обратном нынешнему";
    }

    @Override
    public AbstractServerCommand serverCommand() {
        return new ReorderServerCommand();
    }

    @Override
    public Queue<Message> execute(String[] args) {
        return new LinkedList<>(Arrays.asList(new Message(serverCommand())));
    }
}
