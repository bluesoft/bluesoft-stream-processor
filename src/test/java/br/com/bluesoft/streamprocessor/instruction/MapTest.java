package br.com.bluesoft.streamprocessor.instruction;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import br.com.bluesoft.streamprocessor.Data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MapTest {

    @Test
    public void handleNext() {
        // Arranje
        InstructionStub nextInstruction = new InstructionStub();

        Map map = new Map(data -> data);
        map.setNext(nextInstruction);

        Object object = new Object();

        // Act
        map.handle(object);

        // Assert
        assertEquals(object, nextInstruction.getHandleParam());
    }

    @Test
    public void collectNext() {
        // Arranje
        InstructionStub nextInstruction = new InstructionStub();

        Map map = new Map(data -> data);
        map.setNext(nextInstruction);

        Data data = new Data();

        // Act
        map.collect(data);

        // Assert
        assertEquals(data, nextInstruction.getCollectParam());
    }

    @Test
    public void mapOnCollect() {
        // Arranje
        AtomicBoolean collected = new AtomicBoolean();
        Map map = new Map(d -> {
            collected.set(true);
            return d;
        });

        // Act
        map.collect(new Data());

        // Assert
        assertTrue(collected.get());
    }
}
