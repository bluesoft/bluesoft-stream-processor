package br.com.bluesoft.streamprocessor.instruction;

import java.util.function.Function;

import br.com.bluesoft.streamprocessor.Data;

public class Collect extends Instruction {

    private Function<Data, Data> mapper;

    Collect(Function<Data, Data> mapper) {
        this.mapper = mapper;
    }

    @Override
    public void handle(Object object) {
        collectAll();
    }

    @Override
    public void collect(Data data) {
        collectNext(mapper.apply(data));
    }

    @Override
    public void clear() {
    }
}
