package ru.ilyin.hearttoheartbot.bots;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ilyin.hearttoheartbot.conf.BotProperties;
import ru.ilyin.hearttoheartbot.handlers.MessageHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final BotProperties botProperties;
    private final MessageHandler messageHandler;
    private final Map<Long, String> awaitingCommand;
    private final Map<String, ChatClient> promptToChat;


    public TelegramBot(BotProperties botProperties, MessageHandler messageHandler,
                       Map<Long, String> awaitingCommand, Map<String, ChatClient> promptToChat) {
        super(botProperties.getBotToken());
        this.botProperties = botProperties;
        this.messageHandler = messageHandler;
        this.awaitingCommand = awaitingCommand;
        this.promptToChat = promptToChat;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            User from = update.getMessage().getFrom();
            Long chatId = update.getMessage().getChatId();
            String message = update.getMessage().getText();
            // я определил что это команда или верну дефолт
            if (message.contains("/")) {
                // теперь мне надо определить какая это команда
                String response = messageHandler.handleMessage(chatId, message);
                if (!response.isEmpty()) {
                    sendMessage(chatId, response, from, message);
                }
            } else {
                String state = awaitingCommand.getOrDefault(chatId, null);
                if (state == null || state.isEmpty()) {
                    String response = messageHandler.handleMessage(chatId, "/help");
                    sendMessage(chatId, response, from, message);
                    return;
                }
                ChatClient client = promptToChat.get(state);
                String content = client.prompt()
                        .user(
                                u ->
                                        u.text(message)
                        )
                        .call()
                        .content();
                sendMessage(chatId, content, from, message);
            }
        }
    }

    @PostConstruct
    public void setBotCommands() {
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand("/start", "🏁 Начало вашего пути к внутреннему спокойствию"));
        commands.add(new BotCommand("/talk", "🗨️ Поделитесь своими чувствами — я здесь, чтобы слушать"));
        commands.add(new BotCommand("/advice", "💡 Умные рекомендации для сложных ситуаций"));
        commands.add(new BotCommand("/exercise", "🧘‍♀️ Расслабьтесь и восстановите силы с помощью практик"));
        commands.add(new BotCommand("/selfcare", "🌿 Простые способы позаботиться о себе"));
        commands.add(new BotCommand("/stop", "❌ Остановим разговор, когда вы будете готовы"));

        try {
            execute(
                    new SetMyCommands(
                            commands,
                            new BotCommandScopeDefault(),
                            null
                    )
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



    @Override
    public String getBotUsername() {
        return botProperties.getBotUsername();
    }

    private void sendMessage(Long chatId, String text, User user, String message) {
        try {
            log.info("{} отправил {} и получил ответ {}", user, message, text);
            execute(new SendMessage(String.valueOf(chatId), text));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
