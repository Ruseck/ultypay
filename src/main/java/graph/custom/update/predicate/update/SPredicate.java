package graph.custom.update.predicate.update;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.function.Predicate;

public interface SPredicate<T> extends Serializable, Predicate<T> {
}
