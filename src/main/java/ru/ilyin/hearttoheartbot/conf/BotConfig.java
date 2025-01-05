package ru.ilyin.hearttoheartbot.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.ilyin.hearttoheartbot.bots.TelegramBot;
import ru.ilyin.hearttoheartbot.handlers.MessageHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.DEFAULT_CHAT_MEMORY_CONVERSATION_ID;

@Slf4j
@Configuration
public class BotConfig {


    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramBot telegramBot) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(telegramBot);
        return botsApi;
    }

    @Bean
    public Supplier<ChatMemory> chatMemorySupplier() {
        return InMemoryChatMemory::new;
    }

    @Bean
    public Map<String, ChatClient> promptToChat(ChatClient.Builder builder, PromptProperties promptProperties) {
        Map<String, ChatClient> chatClientMap = new ConcurrentHashMap<>();

        promptProperties.getPrompts().forEach((key, value) -> {
            ChatClient client = builder
                    .defaultSystem(key)
                    .defaultAdvisors(
                            new MessageChatMemoryAdvisor(chatMemorySupplier().get(), DEFAULT_CHAT_MEMORY_CONVERSATION_ID, 5)
                    )
                    .build();
            chatClientMap.put(key, client);
        });
        return chatClientMap;
    }

    @Bean
    public Map<Long, String> awaitingCommand() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public TelegramBot telegramBot(BotProperties botProperties, MessageHandler messageHandler, Map<String, ChatClient> promptToChat) {
        return new TelegramBot(botProperties, messageHandler, awaitingCommand(), promptToChat);
    }


}
