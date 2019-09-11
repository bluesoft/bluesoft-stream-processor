package br.com.bluesoft.streamprocessor.instruction;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import br.com.bluesoft.streamprocessor.Data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CollectTest {

    @Test
    public void mapOnCollect() {
        // Arranje
        Data expectedData = new Data();
        Collect collect = new Collect(d -> expectedData);

        // Act
        Data dataCollected = collect.collect(new Data());

        // Assert
        assertEquals(expectedData, dataCollected);
    }

    @Test
    public void onHandleCollectAll() {
        // Arranje
        AtomicBoolean collected = new AtomicBoolean();

        Collect collect = new Collect(data -> {
            collected.set(true);
            return data;
        });
        collect.setChain(collect);

        // Act
        collect.handle(new Object());

        // Assert
        assertTrue(collected.get());
    }
}
