package graph.custom.update.consumer.update.consumers;

import graph.Action;
import graph.custom.update.consumer.update.SConsumer;
import graph.custom.update.predicate.update.predicates.AdminEquals;
import graph.custom.update.predicate.update.predicates.Equals;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import training.Player;
import uibot.Context;
import uibot.ContextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SendMessage implements SConsumer<Update> {

    private String text;
    private Context context;

    public SendMessage(Context context, String text) {
        this.text = text;
        this.context = context;
    }

    public SendMessage(Context context, Supplier<String> text) {
        this.text = text.get();
        this.context = context;
    }

    @Override
    public void accept(Update update) {
        org.telegram.telegrambots.meta.api.methods.send.SendMessage sendMessage =
                new org.telegram.telegrambots.meta.api.methods.send.SendMessage();
        sendMessage
                .setChatId(update.getMessage().getChatId())
                .setText(text);
        Player player = ContextUtil.INST.getPlayer(context, update);
        ArrayList<String> words = player
                .getCurrent()
                .getActions().stream()
                .map(Action<Update>::getPredicate)
                .filter(p -> p instanceof Equals)
                .filter(p -> !(!player.isAdmin() && p instanceof AdminEquals))
                .map(p -> (Equals) p)
                .map(Equals::getText)
                .collect(Collectors.toCollection(ArrayList::new));

        Optional.of(createKeyboard(words))
                .ifPresent(sendMessage::setReplyMarkup);

        try {
            context.getBot().execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private ReplyKeyboardMarkup createKeyboard(ArrayList<String> words) {
        if (words.isEmpty()) return new ReplyKeyboardMarkup();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        for (String temp : words) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(temp));
            keyboard.add(keyboardRow);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}
