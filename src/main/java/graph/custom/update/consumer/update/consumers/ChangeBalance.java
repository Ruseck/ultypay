package graph.custom.update.consumer.update.consumers;

import graph.custom.update.consumer.update.SConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import uibot.Context;
import uibot.ContextUtil;

import java.math.BigDecimal;

public class ChangeBalance implements SConsumer<Update> {

    private long sum;
    private Context context;

    public ChangeBalance(Context context, long sum) {
        this.sum = sum;
        this.context = context;
    }

    public ChangeBalance(Context context) {
        this.context = context;
    }

    @Override
    public void accept(Update update) {
        ContextUtil.INST.getPlayer(context, update)
                .upBalance(BigDecimal.valueOf(Long.parseLong(update.getMessage().getText())));
    }
}
