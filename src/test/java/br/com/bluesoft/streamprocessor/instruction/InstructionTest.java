package br.com.bluesoft.streamprocessor.instruction;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class InstructionTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void chain() {
        // Arranje
        InstructionStub firstInstruction = new InstructionStub();
        InstructionStub lasteInstruction = new InstructionStub();
        Object object = new Object();

        // Act
        Instruction chain = Instruction.chain(firstInstruction, lasteInstruction);
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
        Instruction chain = Instruction.chain(instruction);

        // Act

        // Requeries the chain to clear all
        chain.clearAll();

        // Assert
        assertTrue(instruction.isClear());
    }

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
    public void clearAll() {
        // Arranje
        InstructionStub lastInstruction = new InstructionStub();

        InstructionStub firstInstruction = new InstructionStub();
        firstInstruction.setNext(lastInstruction);

        lastInstruction.setChain(firstInstruction);

        // Act
        lastInstruction.clearAll();

        // Assert
        assertTrue(firstInstruction.isClear());
        assertTrue(lastInstruction.isClear());
    }

    @Test
    public void clearAllNext() {
        // Arranje
        InstructionStub lastInstruction = new InstructionStub();

        InstructionStub middleInstruction = new InstructionStub();
        middleInstruction.setNext(lastInstruction);

        InstructionStub firstInstruction = new InstructionStub();
        firstInstruction.setNext(middleInstruction);

        // Act
        firstInstruction.clearAllNext();

        // Assert
        assertFalse(firstInstruction.isClear());
        assertTrue(middleInstruction.isClear());
        assertTrue(lastInstruction.isClear());
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
}
