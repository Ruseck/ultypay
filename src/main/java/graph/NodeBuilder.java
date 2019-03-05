package graph;

import graph.custom.update.consumer.update.SConsumer;
import graph.custom.update.predicate.update.SPredicate;

import java.util.Arrays;

public class NodeBuilder<T> {
    private Node<T> template;
    private Action<T> actionTemplate;

    public Node<T> getTemplate() {
        return template;
    }

    public void setTemplate(Node<T> template) {
        this.template = template;
    }

    public Action<T> getActionTemplate() {
        return actionTemplate;
    }

    public void setActionTemplate(Action<T> actionTemplate) {
        this.actionTemplate = actionTemplate;
    }

    public NodeBuilder<T> createNodes(long id) {
        template = new Node<>(id);
        return this;
    }

    public NodeBuilder<T> addAction() {
        actionTemplate = new Action<>();
        template.getActions().add(actionTemplate);
        return this;
    }


    public NodeBuilder<T> addAction(SPredicate<T> predicate, SConsumer<T>... actions) {
        addAction();
        addPredicate(predicate);
        Arrays.stream(actions).forEach(this::addConsumer);
        return this;
    }

    public NodeBuilder<T> addPredicate(SPredicate<T> predicate) {
        actionTemplate.setPredicate(predicate);
        return this;
    }

    public NodeBuilder<T> addConsumer(SConsumer<T> action) {
        actionTemplate.getConsumerList().add(action);
        return this;
    }

    public Node<T> build() {
        return template;
    }

}
