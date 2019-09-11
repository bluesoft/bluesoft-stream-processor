package br.com.bluesoft.streamprocessor.instruction;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class InstructionsTest {

    @Test
    public void criaChain() {
        // Arranje
        Instruction instruction1 = mock(Instruction.class);
        Instruction instruction2 = mock(Instruction.class);

        List<Instruction> instructions = Arrays.asList(instruction1, instruction2);

        // Act
        Instruction chain = Instructions.chain(instructions);

        // Assert
        assertEquals(chain, instruction1);

        verify(instruction1).setChain(instruction1);
        verify(instruction2).setChain(instruction1);

        verify(instruction1).setNext(instruction2);
    }

}
