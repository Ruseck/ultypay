package graph.custom.update.consumer.update.consumers;

import graph.Node;
import graph.NodeBuilder;
import graph.custom.update.consumer.update.SConsumer;
import graph.custom.update.predicate.update.predicates.Equals;
import graph.custom.update.predicate.update.predicates.NotEnoughCoins;
import org.telegram.telegrambots.meta.api.objects.Update;
import training.Training;
import uibot.Context;
import uibot.ContextUtil;

public class CreateAndChangeConfirmPayNode implements SConsumer<Update> {

    private Context context;
    private Training training;

    public CreateAndChangeConfirmPayNode(Context context, Training training) {
        this.context = context;
        this.training = training;
    }

    @Override
    public void accept(Update update) {
        NodeBuilder<Update> builder = new NodeBuilder<>();
        long trainingPrice = training.getCostPerPerson().longValue();
        Node<Update> train = builder.createNodes(ContextUtil.INST.getUniqNodedId(context, update))
                .addAction(
                        new Equals("Отмена"),
                        new ChangeAndSend(context, "3", "Ну, нет так нет."))
                .addAction(new NotEnoughCoins(context, trainingPrice + ""),
                        new ChangeAndSend(context, "3", "Не достаточно денег, проверь баланс!"))
                .addAction(new Equals("Да"),
                        new ChangeAndSend(context, "3", "Окей, оплата проведена!"),
                        new AddUserToTraining(context, training, trainingPrice))
                .addAction(new NotEnoughCoins(context, trainingPrice * 2 + ""),
                        new ChangeAndSend(context, "3", "Не достаточно денег, проверь баланс!"))
                .addAction(new Equals("За себя и +1"),
                        new ChangeAndSend(context, "3", "Окей, оплата проведена!"),
                        new AddUserToTraining(context, training, trainingPrice * 2))
                .addAction(new NotEnoughCoins(context, trainingPrice * 3 + ""),
                        new ChangeAndSend(context, "3", "Не достаточно денег, проверь баланс!"))
                .addAction(new Equals("За себя и +2"),
                        new ChangeAndSend(context, "3", "Окей, оплата проведена!"),
                        new AddUserToTraining(context, training, trainingPrice * 3))
                .build();
        ContextUtil.INST.getPlayer(context, update).getNodes().put(train.getId(), train);
        ContextUtil.INST.getPlayer(context, update).setCurrent(train);
    }
}
