package br.com.bluesoft.streamprocessor.instruction;

import org.junit.Test;

import br.com.bluesoft.streamprocessor.Data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
    public void ignoreNotExpectedType() {
        // Arranje
        TypedInstructionStub instruction = new TypedInstructionStub(String.class);

        // Act
        instruction.handle(new Object());

        // Assert
        assertNull(instruction.getHandleTypedParam());
    }

    public static class TypedInstructionStub extends TypedInstruction<String> {

        private String handleTypedParam;

        public TypedInstructionStub(Class<String> type) {
            super(type);
        }

        @Override
        protected void handleTyped(String string) {
            this.handleTypedParam = string;
        }

        @Override
        public void collect(Data data) {
            throw new UnsupportedOperationException();
        }

        public String getHandleTypedParam() {
            return handleTypedParam;
        }
    }
}
