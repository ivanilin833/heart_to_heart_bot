package ru.ilyin.hearttoheartbot.conf;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BotProperties {
    @Value("${bot.properties.username}")
    private String botUsername;
    @Value("${bot.properties.token}")
    private String botToken;
}
