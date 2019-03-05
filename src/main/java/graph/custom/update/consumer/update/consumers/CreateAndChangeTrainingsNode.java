package graph.custom.update.consumer.update.consumers;

import graph.Node;
import graph.NodeBuilder;
import graph.custom.update.consumer.update.SConsumer;
import graph.custom.update.predicate.update.predicates.Equals;
import org.telegram.telegrambots.meta.api.objects.Update;
import training.Training;
import uibot.Context;
import uibot.ContextUtil;

import java.util.LinkedList;

public class CreateAndChangeTrainingsNode implements SConsumer<Update> {
    Context context;

    public CreateAndChangeTrainingsNode(Context context) {
        this.context = context;
    }

    @Override
    public void accept(Update update) {
        NodeBuilder<Update> builder = new NodeBuilder<>();
        builder.createNodes(ContextUtil.INST.getUniqNodedId(context, update));
        for (Training training : context.getTrainings()) {
            builder
                    .addAction()
                    .addPredicate(new Equals(training.shortInfo()))
                    .addConsumer(new CreateAndChangeTrainingNode(context, training))
                    .addConsumer(new SendMessage(context, training.fullInfo()));
        }
        Node<Update> train = builder.build();
        ContextUtil.INST.getPlayer(context, update).getNodes().put(train.getId(), train);
        ContextUtil.INST.getPlayer(context, update).setCurrent(train);
    }
}
