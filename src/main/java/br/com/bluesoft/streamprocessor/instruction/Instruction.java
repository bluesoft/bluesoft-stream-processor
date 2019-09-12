package br.com.bluesoft.streamprocessor.instruction;

import java.util.Optional;

import br.com.bluesoft.streamprocessor.Data;

public abstract class Instruction {

    private Instruction chain;
    private Instruction next;

    void setChain(Instruction chain) {
        this.chain = chain;
    }

    protected void handleNext(Object object) {
        getNext().ifPresent(instruction -> instruction.handle(object));
    }

    protected void collectAll() {
        requireChain();
        chain.collect(new Data());
    }

    protected void clearAll() {
        requireChain();
        clearNext(chain);
    }

    protected void collectNext(Data data) {
        getNext().ifPresent(instruction -> instruction.collect(data));
    }

    private void clearNext(Instruction instruction) {
        instruction.clear();
        getNext().ifPresent(this::clearNext);
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

    public abstract void clear();
}
