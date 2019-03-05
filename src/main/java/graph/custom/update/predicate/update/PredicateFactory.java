package graph.custom.update.predicate.update;

import graph.custom.update.predicate.update.predicates.AdminEquals;
import graph.custom.update.predicate.update.predicates.AlwaysTrue;
import graph.custom.update.predicate.update.predicates.Equals;
import graph.custom.update.predicate.update.predicates.IsNumber;
import org.telegram.telegrambots.meta.api.objects.Update;
import uibot.Context;

import java.util.HashMap;
import java.util.function.Predicate;

public enum PredicateFactory {
    INST;

    public SPredicate<Update> create(HashMap<String, String> data, Context context) {
        String type = data.get("type");
        switch (type) {
            case "always-true":
                return new AlwaysTrue();
            case "equals":
                return new Equals(data.get("text"));
            case "admin-equals":
                return new AdminEquals(data.get("text"), context);
            case "is-number":
                return new IsNumber();
            default:
                throw new IllegalStateException();
        }
    }
}
