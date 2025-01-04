package ru.ilyin.hearttoheartbot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class StopCommand implements TelegramCommand {
    private final Map<String, ChatClient> promptToChat;
    private final Map<Long, String> awaitingCommand;
    @Override
    public String execute(Long chatId) {
        awaitingCommand.put(chatId, "");
        ChatClient client = promptToChat.get(myCommandName().substring(1));
        return client.prompt()
                .user(
                        u ->
                                u.text("Пока")
                )
                .call()
                .content();

    }

    @Override
    public String myCommandName() {
        return "/stop";
    }
}
