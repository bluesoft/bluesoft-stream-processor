package br.com.bluesoft.streamprocessor.instruction;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.com.bluesoft.streamprocessor.Data;

public abstract class Instruction {

    private Instruction chain;
    private Instruction next;

    public static Instruction chain(Instruction... instructions) {
        return chain(Arrays.asList(instructions));
    }

    public static Instruction chain(List<? extends Instruction> instructions) {
        Instruction chain = instructions.get(0);

        if(instructions.size() == 1) {
            chain.setChain(chain);
            return chain;
        }

        instructions
            .stream()
            .peek(instruction -> instruction.setChain(chain))
            .reduce(
                (instruction1, instruction2) -> {
                    instruction1.setNext(instruction2);
                    return instruction2;
                });

        return chain;
    }

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
        clearAll(chain);
    }

    protected void clearAllNext() {
        getNext().ifPresent(this::clearAllNext);
    }

    protected void collectNext(Data data) {
        getNext().ifPresent(instruction -> instruction.collect(data));
    }

    private void clearAll(Instruction instruction) {
        instruction.clear();
        instruction.getNext().ifPresent(this::clearAll);
    }

    private void clearAllNext(Instruction instruction) {
        instruction.clear();
        instruction.clearAllNext();
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
