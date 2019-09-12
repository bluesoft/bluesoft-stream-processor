package br.com.bluesoft.streamprocessor.instruction;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import br.com.bluesoft.streamprocessor.Data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CollectTest {

    @Test
    public void collectNext() {
        // Arranje
        InstructionStub nextInstruction = new InstructionStub();

        Collect collect = new Collect(data -> data);
        collect.setNext(nextInstruction);

        Data data = new Data();

        // Act
        collect.collect(data);

        // Assert
        assertEquals(data, nextInstruction.getCollectParam());
    }

    @Test
    public void mapOnCollect() {
        // Arranje
        AtomicBoolean collected = new AtomicBoolean();
        Collect collect = new Collect(d -> {
            collected.set(true);
            return d;
        });

        // Act
        collect.collect(new Data());

        // Assert
        assertTrue(collected.get());
    }

    @Test
    public void onHandleCollectAll() {
        // Arranje
        AtomicBoolean collected = new AtomicBoolean();

        Collect collect = new Collect(data -> {
            collected.set(true);
            return data;
        });
        collect.setChain(collect);

        // Act
        collect.handle(new Object());

        // Assert
        assertTrue(collected.get());
    }

    private static class InstructionStub extends Instruction {

        private Data collectParam;

        @Override
        public void handle(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void collect(Data data) {
            this.collectParam = data;
        }

        public Data getCollectParam() {
            return collectParam;
        }
    }
}
