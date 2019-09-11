package br.com.bluesoft.streamprocessor;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DataTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getSemObjetoColetado() {
        // Arrange
        Data data = new Data();

        // Act
        Object element = data.get(Object.class);

        // Assert
        assertNull(element);
    }

    @Test
    public void getComUmObjetoColetado() {
        // Arrange
        Data data = new Data();
        Object object = new Object();
        data.add(object);

        // Act
        Object element = data.get(Object.class);

        // Assert
        assertEquals( object, element);
    }

    @Test
    public void getComMaisDeUmObjetoColetado() {
        // Arrange
        Data data = new Data();
        data.add(new Object());
        data.add(new Object());

        // Assert
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Utilize o método getList. Existem 2 objetos para o tipo " + Object.class);

        // Act
        Object element = data.get(Object.class);

    }
}
