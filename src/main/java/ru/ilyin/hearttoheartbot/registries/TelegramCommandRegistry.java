package ru.ilyin.hearttoheartbot.registries;

import org.springframework.stereotype.Component;
import ru.ilyin.hearttoheartbot.commands.HelpCommand;
import ru.ilyin.hearttoheartbot.commands.TelegramCommand;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TelegramCommandRegistry implements CommandRegistry {
    private final Map<String, TelegramCommand> commands = new ConcurrentHashMap<>();

    @Override
    public void registerCommand(String commandName, TelegramCommand command) {
        commands.put(commandName, command);
    }

    @Override
    public TelegramCommand getCommand(String commandName) {
        return commands.getOrDefault(commandName, new HelpCommand());
    }
}
