package br.com.bluesoft.streamprocessor.instruction;

import org.junit.Test;

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
}
