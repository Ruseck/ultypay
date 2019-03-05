package graph.custom.update;

import graph.Action;
import graph.Node;
import uibot.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Listeners implements Serializable {

    public Listeners(Context context) {
        this.context = context;
    }

    private Context context;
    private List<Node<Context>> listeners = new ArrayList<>();

    public void add(Node<Context> listener) {
        listeners.add(listener);
    }

    private boolean isStopped;

    public void stop() {
        isStopped = true;
    }

    public void start() {
        isStopped = false;
        Runnable run = () -> {
            while (!isStopped) {

                listeners.forEach(node -> {
                    List<Action<Context>> executed = node.getActions().stream()
                            .filter(a -> a.getPredicate().test(context))
                            .peek(a -> a.getConsumerList().forEach(consumer -> consumer.accept(context)))
                            .collect(Collectors.toList());
                    node.getActions().removeAll(executed);
                });

                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        Thread thread = new Thread(run);
        thread.start();
    }
}
