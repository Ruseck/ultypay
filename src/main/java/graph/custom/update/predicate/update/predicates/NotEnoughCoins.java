package graph.custom.update.predicate.update.predicates;

import graph.custom.update.predicate.update.SPredicate;
import org.telegram.telegrambots.meta.api.objects.Update;
import uibot.Context;
import uibot.ContextUtil;

import java.math.BigDecimal;

public class NotEnoughCoins implements SPredicate<Update> {
    public NotEnoughCoins(Context context, String coin) {
        this.context = context;
        this.coin = coin;
    }

    private Context context;
    private String coin;

    @Override
    public boolean test(Update update) {
        return ContextUtil.INST.getPlayer(context, update)
                .getBalance().compareTo(BigDecimal.valueOf(Long.parseLong(coin))) == -1L;
    }
}
