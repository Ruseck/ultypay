package graph.custom.update.consumer.update.consumers;

import graph.custom.update.consumer.update.SConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import training.Player;
import training.Training;
import uibot.Context;
import uibot.ContextUtil;

import java.math.BigDecimal;

public class AddUserToTraining implements SConsumer<Update> {

    private Context context;
    private Training training;
    private long sum;

    public AddUserToTraining(Context context, Training training, long sum) {
        this.sum = sum;
        this.context = context;
        this.training = training;
    }

    @Override
    public void accept(Update update) {
        Player player = ContextUtil.INST.getPlayer(context, update);
        player.upBalance(BigDecimal.valueOf(-sum));
        training.getPayed().compute(player, (k, v) -> (v == null) ? BigDecimal.valueOf(sum) : BigDecimal.valueOf(sum).add(v));
    }
}
