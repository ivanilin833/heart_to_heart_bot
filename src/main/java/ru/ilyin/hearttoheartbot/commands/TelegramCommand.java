package ru.ilyin.hearttoheartbot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ilyin.hearttoheartbot.registries.CommandRegistry;

public interface TelegramCommand {
    String execute(Long chatId);
    @Autowired
    default void registryMe(CommandRegistry registry) {
        registry.registerCommand(myCommandName(), this);
    }
    String myCommandName();
}
