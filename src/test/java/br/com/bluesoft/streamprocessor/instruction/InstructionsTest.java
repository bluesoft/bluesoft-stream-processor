package br.com.bluesoft.streamprocessor.instruction;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InstructionsTest {

    @Test
    public void chain() {
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

    @Test
    public void chainSingleInstruction() {
        // Arranje
        InstructionStub instruction = new InstructionStub();
        Instruction chain = Instructions.chain(instruction);

        // Act

        // Requeries the chain to clear all
        chain.clearAll();

        // Assert
        assertTrue(instruction.isClear());
    }
}
