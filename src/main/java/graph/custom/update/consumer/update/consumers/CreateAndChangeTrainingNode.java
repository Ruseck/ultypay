package graph.custom.update.consumer.update.consumers;

import graph.Node;
import graph.NodeBuilder;
import graph.custom.update.consumer.update.SConsumer;
import graph.custom.update.predicate.update.predicates.Equals;
import org.telegram.telegrambots.meta.api.objects.Update;
import training.Training;
import uibot.Context;
import uibot.ContextUtil;

public class CreateAndChangeTrainingNode implements SConsumer<Update> {
    Context context;
    Training training;

    public CreateAndChangeTrainingNode(Context context, Training training) {
        this.context = context;
        this.training = training;
    }

    @Override
    public void accept(Update update) {
        NodeBuilder<Update> builder = new NodeBuilder<>();
        Node<Update> train = builder.createNodes(ContextUtil.INST.getUniqNodedId(context, update))
                .addAction(new Equals("Оплатить"),
                        new CreateAndChangeConfirmPayNode(context, training),
                        new SendMessage(context, "Стоимость с человека " + training.getCostPerPerson() + " гривен. Подтверждаешь оплату?"))
                .addAction(new Equals("Список оплативших"),
                        new SendMessage(context, () -> training.listOfPlayers()))
                .addAction(new Equals("Назад"),
                        new ChangeNode(context, "3"),
                        new SendMessage(context, "Что-то ещё?"))
                .build();
        ContextUtil.INST.getPlayer(context, update).getNodes().put(train.getId(), train);
        ContextUtil.INST.getPlayer(context, update).setCurrent(train);
    }
}
