package br.com.bluesoft.streamprocessor.instruction;

import org.junit.Test;

import br.com.bluesoft.streamprocessor.Data;

import static org.junit.Assert.assertEquals;

public class InstructionsTest {

    @Test
    public void criaChain() {
        // Arranje
        InstructionStub instruction1 = new InstructionStub();
        InstructionStub instruction2 = new InstructionStub();
        Object object = new Object();

        // Act
        Instruction chain = Instructions.chain(instruction1, instruction2);
        chain.handle(object);

        // Assert
        assertEquals(chain, instruction1);
        assertEquals(object, instruction1.getHandleParam());
        assertEquals(object, instruction2.getHandleParam());
    }

    private static class InstructionStub extends Instruction {

        private Object handleParam;

        @Override
        public void handle(Object object) {
            handleParam = object;
            handleNext(object);
        }

        @Override
        public Data collect(Data data) {
            throw new UnsupportedOperationException();
        }

        public Object getHandleParam() {
            return handleParam;
        }
    }
}
