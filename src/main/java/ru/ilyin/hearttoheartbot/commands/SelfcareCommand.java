package ru.ilyin.hearttoheartbot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SelfcareCommand implements TelegramCommand {
    private final Map<String, ChatClient> promptToChat;
    private final Map<Long, String> awaitingCommand;
    @Override
    public String execute(Long chatId) {
        awaitingCommand.put(chatId, myCommandName().substring(1));
        ChatClient client = promptToChat.get(myCommandName().substring(1));
        return client.prompt()
                .user(
                        u ->
                                u.text("Задай на водящие вопросы, чтобы понять мое состояние")
                )
                .call()
                .content();

    }

    @Override
    public String myCommandName() {
        return "/selfcare";
    }
}
