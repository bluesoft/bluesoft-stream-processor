package br.com.bluesoft.streamprocessor.instruction;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import br.com.bluesoft.streamprocessor.Data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InstructionTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void handleObjectOnNextInstruction() {
        // Arranje
        Long registro = 1L;

        InstructionStub lastInstruction = new InstructionStub();

        InstructionStub firstInstruction = new InstructionStub();
        firstInstruction.setNext(lastInstruction);

        // Act
        firstInstruction.handleNext(registro);

        // Assert
        assertEquals(registro, firstInstruction.getHandleNextParam());
        assertEquals(registro, lastInstruction.getHandleParam());
    }

    @Test
    public void collectAll() {
        // Arranje
        InstructionStub lastInstruction = new InstructionStub();

        InstructionStub firstInstruction = new InstructionStub();
        firstInstruction.setNext(lastInstruction);

        lastInstruction.setChain(firstInstruction);

        // Act
        lastInstruction.collectAll();

        // Assert
        assertNotNull(firstInstruction.getCollectParam());
    }

    @Test
    public void onCollectAllValidateIfInstructionHasReferenceToTheChain() {
        // Arranje
        InstructionStub instruction = new InstructionStub();

        // Assert
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("[chain = null] for instruction " + InstructionStub.class.getName());

        // Act
        instruction.collectAll();
    }

    private static class InstructionStub extends Instruction {

        private Object handleNextParam;
        private Object handleParam;
        private Data collectParam;

        @Override
        protected void handleNext(Object object) {
            handleNextParam = object;
            super.handleNext(object);
        }

        @Override
        public void handle(Object object) {
            handleParam = object;
        }

        @Override
        public void collect(Data data) {
            collectParam = data;
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
    }
}
