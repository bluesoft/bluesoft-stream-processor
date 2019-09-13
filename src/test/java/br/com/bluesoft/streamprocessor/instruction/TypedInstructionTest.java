package br.com.bluesoft.streamprocessor.instruction;

import org.junit.Test;

import br.com.bluesoft.streamprocessor.Data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TypedInstructionTest {

    @Test
    public void handleExpectedType() {
        // Arranje
        TypedInstructionStub instruction = new TypedInstructionStub(String.class);
        String expected = "expected";

        // Act
        instruction.handle(expected);

        // Assert
        assertEquals(expected, instruction.getHandleTypedParam());
    }

    @Test
    public void passOnNotExpectedType() {
        // Arranje
        TypedInstructionStub instruction = new TypedInstructionStub(String.class);

        // Act
        instruction.handle(new Object());

        // Assert
        assertNotNull(instruction.getHandleNextParam());
    }

    public static class TypedInstructionStub extends TypedInstruction<String> {

        private String handleTypedParam;
        private Object handleNextParam;

        public TypedInstructionStub(Class<String> type) {
            super(type);
        }

        @Override
        protected void handleNext(Object object) {
            this.handleNextParam = object;
            super.handleNext(object);
        }

        @Override
        protected void handleTyped(String string) {
            this.handleTypedParam = string;
        }

        @Override
        public void collect(Data data) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        public String getHandleTypedParam() {
            return handleTypedParam;
        }

        public Object getHandleNextParam() {
            return handleNextParam;
        }
    }
}
