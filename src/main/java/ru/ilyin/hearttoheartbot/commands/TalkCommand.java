package ru.ilyin.hearttoheartbot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class TalkCommand implements TelegramCommand {
    private final Map<Long, String> awaitingCommand;
    @Override
    public String execute(Long chatId) {
        awaitingCommand.put(chatId, myCommandName().substring(1));
        return "О чем ты хочешь со мной поговорить?";
    }

    @Override
    public String myCommandName() {
        return "/talk";
    }
}
