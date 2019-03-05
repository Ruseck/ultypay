package graph.custom.update.consumer.update.consumers;

import graph.custom.update.consumer.update.SConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import uibot.Context;

public class ChangeAndSend implements SConsumer<Update> {

    private Context context;
    private String id;
    private String text;

    public ChangeAndSend(Context context, String id, String text) {
        this.context = context;
        this.id = id;
        this.text = text;
    }

    @Override
    public void accept(Update update) {
        new ChangeNode(context, id).accept(update);
        new SendMessage(context, text).accept(update);
    }
}
