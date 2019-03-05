package graph;

import graph.custom.update.consumer.update.SConsumer;
import graph.custom.update.predicate.update.SPredicate;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Action<T> implements Serializable {

    private SPredicate<T> predicate;
    private List<SConsumer<T>> consumerList = new LinkedList<>();

    public SPredicate<T> getPredicate() {
        return predicate;
    }

    public void setPredicate(SPredicate<T> predicate) {
        this.predicate = predicate;
    }

    public List<SConsumer<T>> getConsumerList() {
        return consumerList;
    }

    public void execute(T obj) {
        consumerList.forEach(consumer -> consumer.accept(obj));
    }

}
