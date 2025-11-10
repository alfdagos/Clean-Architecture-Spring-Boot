package it.alf.cleana.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseEntity {
    private final List<Object> domainEvents = new ArrayList<>();

    protected void addDomainEvent(Object event) {
        domainEvents.add(event);
    }

    public List<Object> getDomainEvents() {
        return List.copyOf(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }
}
