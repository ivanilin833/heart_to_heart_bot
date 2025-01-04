package ru.ilyin.hearttoheartbot.models;

import java.util.ArrayDeque;
import java.util.Deque;

public class ChatContext {
    private final StringBuilder chatHistory = new StringBuilder();
    private final Deque<String> messageQueue = new ArrayDeque<>();
    private final int TOKEN_LIMIT = 4096;

    public String getChatHistory() {
        return chatHistory.toString();
    }
    public void appendChatHistory(String msg) {
        // Добавляем новое сообщение в очередь
        messageQueue.addLast(msg);
        chatHistory.append(msg);

        // Проверяем размер токенов
        while (countTokens(chatHistory.toString()) > TOKEN_LIMIT) {
            // Удаляем старейшее сообщение, если лимит превышен
            String oldestMessage = messageQueue.pollFirst();
            if (oldestMessage != null) {
                chatHistory.delete(0, oldestMessage.length());
            }
        }
    }

    // todo вынести метод в отдельный интерфейс и убрать магическое число
    private int countTokens(String text) {
        // Эмуляция подсчёта токенов (на основе слов и символов)
        return text.split("\\s+").length + (int) Math.ceil((double) text.length() / 4);
    }
}
