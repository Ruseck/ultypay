package graph.custom.update.consumer.update.consumers;

import graph.Node;
import graph.custom.update.consumer.update.SConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import training.Player;
import uibot.Context;
import uibot.ContextUtil;

public class ChangeNode implements SConsumer<Update> {

    private long id;
    private Context context;

    public ChangeNode(Context context, String id) {
        this.id = Long.parseLong(id);
        this.context = context;
    }

    @Override
    public void accept(Update update) {
        Player player = ContextUtil.INST.getPlayer(context, update);
        Node<Update> next = player.getNodes().get(id);
        player.setCurrent(next);
    }
}
