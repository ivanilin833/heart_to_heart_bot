package ru.ilyin.hearttoheartbot.registries;

import ru.ilyin.hearttoheartbot.commands.TelegramCommand;

public interface CommandRegistry {
    void registerCommand(String commandName, TelegramCommand command);
    TelegramCommand getCommand(String commandName);
}
