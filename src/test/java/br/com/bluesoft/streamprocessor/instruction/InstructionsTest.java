package br.com.bluesoft.streamprocessor.instruction;

import org.junit.Test;

import br.com.bluesoft.streamprocessor.Data;

import static org.junit.Assert.assertEquals;

public class InstructionsTest {

    @Test
    public void criaChain() {
        // Arranje
        InstructionStub firstInstruction = new InstructionStub();
        InstructionStub lasteInstruction = new InstructionStub();
        Object object = new Object();

        // Act
        Instruction chain = Instructions.chain(firstInstruction, lasteInstruction);
        chain.handle(object);

        // Assert
        assertEquals(chain, firstInstruction);
        assertEquals(object, firstInstruction.getHandleParam());
        assertEquals(object, lasteInstruction.getHandleParam());
    }

    private static class InstructionStub extends Instruction {

        private Object handleParam;

        @Override
        public void handle(Object object) {
            handleParam = object;
            handleNext(object);
        }

        @Override
        public void collect(Data data) {
            throw new UnsupportedOperationException();
        }

        public Object getHandleParam() {
            return handleParam;
        }
    }
}
