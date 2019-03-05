package graph.custom.update.consumer.update.consumers;

import graph.custom.update.consumer.update.SConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import training.Player;
import training.Training;
import uibot.Context;
import uibot.ContextUtil;


public class CancelTraining implements SConsumer<Update> {

    public CancelTraining(Context context) {
        this.context = context;
    }

    Context context;

    @Override
    public void accept(Update update) {
        int index = ContextUtil.INST.getText(update).map(Integer::parseInt).orElse(-1);
        Training training = context.getTrainings().get(index);
        training.getPayed().forEach(Player::upBalance);
        training.getPayed().forEach(Player::upBalance);
        String messageText = "Тренировка в " + training.shortInfo() + " к сожалению отменена(";
        training.getPayed().forEach((k, v) -> ContextUtil.INST.sendMessage(k.getUser().getId(), messageText, context.getBot()));
        context.getTrainings().remove(index);
    }
}
