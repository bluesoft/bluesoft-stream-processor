package br.com.bluesoft.streamprocessor.instruction;

import org.apache.commons.lang3.Validate;

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
        collectNext(chain, new Data());
    }

    void setChain(Instruction chain) {
        this.chain = chain;
    }

    void setNext(Instruction next) {
        this.next = next;
    }

    private Optional<Instruction> getNext() {
        return Optional.ofNullable(next);
    }

    private void collectNext(Data data) {
        getNext().ifPresent(instruction -> collectNext(instruction, data));
    }

    private void collectNext(Instruction instruction,
                             Data data) {

        data = instruction.collect(data);
        Validate.notNull(data, "Collected data cannot be null");
        instruction.collectNext(data);
    }

    private void requireChain() {
        if (chain == null) {
            throw new IllegalStateException(
                String.format("[chain = null] for instruction %s", this.getClass().getName()));
        }
    }

    public abstract void handle(Object object);

    public abstract Data collect(Data data);
}
