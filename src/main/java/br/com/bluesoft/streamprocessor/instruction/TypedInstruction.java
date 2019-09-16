package br.com.bluesoft.streamprocessor.instruction;

public abstract class TypedInstruction<T> extends Instruction {

    private final Class<T> type;

    public TypedInstruction(Class<T> type) {
        this.type = type;
    }

    @Override
    public void handle(Object object) {
        if (type.equals(object.getClass())) {
            handleTyped(type.cast(object));
        }

        handleNext(object);
    }

    protected abstract void handleTyped(T t);
}
