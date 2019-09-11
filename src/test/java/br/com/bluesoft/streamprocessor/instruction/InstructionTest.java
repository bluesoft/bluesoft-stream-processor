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

        InstructionStub instruction2 = new InstructionStub();

        InstructionStub instruction1 = new InstructionStub();
        instruction1.setNext(instruction2);

        // Act
        instruction1.handleNext(registro);

        // Assert
        assertEquals(registro, instruction1.getHandleNextParam());
        assertEquals(registro, instruction2.getHandleParam());
    }

    @Test
    public void collectAll() {
        // Arranje
        InstructionStub instruction2 = new InstructionStub();

        InstructionStub instruction1 = new InstructionStub();
        instruction1.setNext(instruction2);

        instruction2.setChain(instruction1);

        // Act
        instruction2.collectAll();

        // Assert
        assertNotNull(instruction1.getCollectParam());
        assertNotNull(instruction2.getCollectParam());
    }

    @Test
    public void onCollectValidateIfReturnsData() {
        // Arranje
        InstructionStub instruction2 = new InstructionStub() {
            @Override
            public Data collect(Data data) {
                return null;
            }
        };

        InstructionStub instruction1 = new InstructionStub();
        instruction1.setNext(instruction2);

        instruction2.setChain(instruction1);

        // Assert
        expectedException.expectMessage("Collected data cannot be null");
        expectedException.expect(NullPointerException.class);

        // Act
        instruction2.collectAll();
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
        public Data collect(Data data) {
            return collectParam = data;
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
