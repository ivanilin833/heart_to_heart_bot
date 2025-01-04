package ru.ilyin.hearttoheartbot.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilyin.hearttoheartbot.commands.TelegramCommand;
import ru.ilyin.hearttoheartbot.registries.CommandRegistry;

@Component
@RequiredArgsConstructor
public class MessageHandler {
    private final CommandRegistry commandRegistry;

    public String handleMessage(Long chatId, String message) {
        TelegramCommand command = commandRegistry.getCommand(message);
        return command.execute(chatId);
    }
}
