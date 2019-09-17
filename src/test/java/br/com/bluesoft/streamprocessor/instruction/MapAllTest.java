package br.com.bluesoft.streamprocessor.instruction;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import br.com.bluesoft.streamprocessor.Data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MapAllTest {

    @Test
    public void handleNext() {
        // Arranje
        InstructionStub nextInstruction = new InstructionStub();

        MapAll mapAll = new MapAll(data -> data);
        mapAll.setNext(nextInstruction);

        Object object = new Object();

        // Act
        mapAll.handle(object);

        // Assert
        assertEquals(object, nextInstruction.getHandleParam());
    }

    @Test
    public void collectNext() {
        // Arranje
        InstructionStub nextInstruction = new InstructionStub();

        MapAll mapAll = new MapAll(data -> data);
        mapAll.setNext(nextInstruction);

        Data data = new Data();

        // Act
        mapAll.collect(data);

        // Assert
        assertEquals(data, nextInstruction.getCollectParam());
    }

    @Test
    public void mapOnCollect() {
        // Arranje
        AtomicBoolean collected = new AtomicBoolean();
        MapAll mapAll = new MapAll(d -> {
            collected.set(true);
            return d;
        });

        // Act
        mapAll.collect(new Data());

        // Assert
        assertTrue(collected.get());
    }
}
