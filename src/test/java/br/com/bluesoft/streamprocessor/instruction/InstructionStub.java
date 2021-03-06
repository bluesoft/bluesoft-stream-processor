package br.com.bluesoft.streamprocessor.instruction;

import br.com.bluesoft.streamprocessor.Data;

public class InstructionStub extends Instruction {

    private Object handleNextParam;
    private Object handleParam;
    private Data collectParam;
    private boolean clear;

    @Override
    protected void handleNext(Object object) {
        handleNextParam = object;
        super.handleNext(object);
    }

    @Override
    public void handle(Object object) {
        handleParam = object;
        handleNext(object);
    }

    @Override
    public void collect(Data data) {
        collectParam = data;
        data.add(handleParam);
        collectNext(data);
    }

    @Override
    public void clear() {
        clear = true;
    }

    public Object getHandleNextParam() {
        return handleNextParam;
    }

    public Object getHandleParam() {
        return handleParam;
    }

    public Data getCollectParam() {
        return collectParam;
    }

    public boolean isClear() {
        return clear;
    }
}
