package br.com.bluesoft.streamprocessor.instruction;

import java.util.Optional;

import br.com.bluesoft.streamprocessor.Data;

public abstract class Instruction {

    private Instruction chain;
    private Instruction next;

    protected void handleNext(Object object) {
        getNext().ifPresent(instruction -> instruction.handle(object));
    }

    protected void collectAll() {
        requireChain();
        chain.collect(new Data());
    }

    protected void collectNext(Data data) {
        getNext().ifPresent(instruction -> instruction.collect(data));
    }

    void setChain(Instruction chain) {
        this.chain = chain;
    }

    private Optional<Instruction> getNext() {
        return Optional.ofNullable(next);
    }

    void setNext(Instruction next) {
        this.next = next;
    }

    private void requireChain() {
        if (chain == null) {
            throw new IllegalStateException(
                String.format("[chain = null] for instruction %s", this.getClass().getName()));
        }
    }

    public abstract void handle(Object object);

    public abstract void collect(Data data);
}
