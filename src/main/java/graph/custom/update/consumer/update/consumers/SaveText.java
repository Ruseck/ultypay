package graph.custom.update.consumer.update.consumers;

import graph.custom.update.consumer.update.SConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import uibot.Context;
import uibot.ContextUtil;

public class SaveText implements SConsumer<Update> {

    private String text;
    private Context context;

    public SaveText(Context context, String text) {
        this.text = text;
        this.context = context;
    }

    @Override
    public void accept(Update update) {
        ContextUtil.INST
                .getPlayer(context, update)
                .getCash()
                .put(text, ContextUtil.INST.getText(update).orElse(""));
    }

}
