package ru.ilyin.hearttoheartbot.commands;

import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements TelegramCommand {
    @Override
    public String execute(Long chatId) {
        return """
                🌟 Доступные команды:
                
                    /talk — "Если что-то тревожит или хочется просто выговориться, напиши мне. Я готов слушать и быть рядом."
                
                    /advice — "Не знаешь, как поступить? Расскажи, и я постараюсь подсказать что-то полезное и заботливое."
                
                    /exercise — "Хочешь почувствовать немного спокойствия? Я покажу простые упражнения, которые помогут расслабиться."
                
                    /selfcare — "Иногда важно уделить время себе. Я подскажу идеи, как можно поднять настроение или почувствовать себя лучше."
                
                    /stop — "Если захочешь закончить разговор, просто дай знать. Я всегда буду здесь, если снова понадоблюсь."
                """;
    }

    @Override
    public String myCommandName() {
        return "/help";
    }
}
