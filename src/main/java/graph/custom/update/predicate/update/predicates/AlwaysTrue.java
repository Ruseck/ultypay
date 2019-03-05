package graph.custom.update.predicate.update.predicates;

import graph.custom.update.predicate.update.SPredicate;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.function.Predicate;

public class AlwaysTrue implements SPredicate<Update> {

    @Override
    public boolean test(Update update) {
        return update.hasMessage();
    }
}
