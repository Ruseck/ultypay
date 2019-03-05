package graph.custom.update.predicate.update.predicates;

import graph.custom.update.predicate.update.SPredicate;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uibot.ContextUtil;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Predicate;

public class Equals implements SPredicate<Update> {

    public String getText() {
        return text;
    }

    private String text;

    public Equals(String text) {
        this.text = text;
    }

    @Override
    public boolean test(Update update) {
        return ContextUtil.INST.getText(update)
                .filter(mtext -> mtext.equals(text))
                .isPresent();
    }
}
