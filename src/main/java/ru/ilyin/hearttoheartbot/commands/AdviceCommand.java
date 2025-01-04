package ru.ilyin.hearttoheartbot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
@RequiredArgsConstructor
public class AdviceCommand implements TelegramCommand {
    private final Map<Long, String> awaitingCommand;
    @Override
    public String execute(Long chatId) {
        awaitingCommand.put(chatId, myCommandName().substring(1));
        return "Опиши пожалуйста свою ситуацию и я постараюсь дать тебе полезный совет";
    }

    @Override
    public String myCommandName() {
        return "/advice";
    }
}
