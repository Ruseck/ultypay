package graph.custom.update.predicate.update.predicates;

import org.telegram.telegrambots.meta.api.objects.Update;
import uibot.Context;
import uibot.ContextUtil;

public class AdminEquals extends Equals {
    private Context context;

    public AdminEquals(String text, Context context) {
        super(text);
        this.context = context;
    }

    @Override
    public boolean test(Update update) {
        if (!ContextUtil.INST.getPlayer(context, update).isAdmin()) {
            return false;
        }
        return super.test(update);
    }
}
