package ru.ilyin.hearttoheartbot.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "bot.properties")
public class PromptProperties {

    private Map<String, String> prompts;

    public Map<String, String> getPrompts() {
        return prompts;
    }

    public void setPrompts(Map<String, String> prompts) {
        this.prompts = prompts;
    }
}

