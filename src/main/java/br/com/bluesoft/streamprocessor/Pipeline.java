package br.com.bluesoft.streamprocessor;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import br.com.bluesoft.streamprocessor.instruction.Instruction;
import br.com.bluesoft.streamprocessor.instruction.Instructions;

public class Pipeline {

    private List<Instruction> pipeline = new ArrayList<>();
    private Data collected;

    public Pipeline pipe(Instruction... instructions) {
        End endInstruction = new End(data -> collected = data);

        Pipe pipe = new Pipe(ArrayUtils.addAll(instructions, endInstruction));
        pipeline.add(pipe);

        return this;
    }

    public Optional<Data> handle(Object object) {
        pipeline.forEach(pipe -> pipe.handle(object));

        Data c = collected;
        collected = null;

        return Optional.ofNullable(c);
    }

    private static class End extends Instruction {

        private Consumer<Data> collector;

        public End(Consumer<Data> collector) {
            this.collector = collector;
        }

        @Override
        public void handle(Object object) {
            collectAll();
        }

        @Override
        public void collect(Data data) {
            collector.accept(data);
        }
    }

    private static class Pipe extends Instruction {

        private Instruction instruction;

        public Pipe(Instruction... instructions) {
            this.instruction = Instructions.chain(instructions);
        }

        @Override
        public void handle(Object object) {
            instruction.handle(object);
        }

        @Override
        public void collect(Data data) {
            throw new UnsupportedOperationException();
        }
    }
}
