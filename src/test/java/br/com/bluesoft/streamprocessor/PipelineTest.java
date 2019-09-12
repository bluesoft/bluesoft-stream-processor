package br.com.bluesoft.streamprocessor;

import org.junit.Test;

import java.util.Collection;
import java.util.Optional;

import br.com.bluesoft.streamprocessor.instruction.InstructionStub;

import static org.junit.Assert.assertEquals;

public class PipelineTest {

    @Test
    public void handleObject() {
        // Arranje
        Pipeline pipeline = new Pipeline().pipe(new InstructionStub());
        Object object = new Object();

        // Act
        Optional<Data> data = pipeline.handle(object);

        // Assert
        Collection<Object> collected = data.get().getAll();
        assertEquals(1, collected.size());
        assertEquals(object, collected.iterator().next());
    }
}
