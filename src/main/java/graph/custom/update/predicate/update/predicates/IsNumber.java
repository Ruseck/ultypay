package graph.custom.update.predicate.update.predicates;

import graph.custom.update.predicate.update.SPredicate;
import org.telegram.telegrambots.meta.api.objects.Update;
import uibot.ContextUtil;


public class IsNumber implements SPredicate<Update> {
    @Override
    public boolean test(Update update) {
        try {
            return ContextUtil.INST.getText(update)
                    .map(Double::new).isPresent();
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
