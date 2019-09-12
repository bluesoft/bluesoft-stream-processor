package br.com.bluesoft.streamprocessor;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DataTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void addIgnoreNullValues() {
        // Arranje
        Data data = new Data();

        // Act
        data.add(null);

        // Assert
        assertEquals(0, data.getAll().size());
    }

    @Test
    public void addAllIgnoreNullValues() {
        // Arranje
        Data data = new Data();

        // Act
        data.addAll(null);

        // Assert
        assertEquals(0, data.getAll().size());
    }


    @Test
    public void getNothing() {
        // Arrange
        Data data = new Data();

        // Act
        Object element = data.get(Object.class);

        // Assert
        assertNull(element);
    }

    @Test
    public void getObject() {
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
    public void getCannotReturnMoreThanOne() {
        // Arrange
        Data data = new Data();
        data.add(new Object());
        data.add(new Object());

        // Assert
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Use getList instead. There are 2 objects of type " + Object.class);

        // Act
        Object element = data.get(Object.class);

    }

    @Test
    public void getAll() {
        // Arrange
        Data data = new Data();
        data.add(new Object());
        data.add(new Object());

        // Act
        Collection<Object> objects = data.getAll();

        // Assert
        assertEquals(2, objects.size());
    }

    @Test
    public void getList() {
        // Arrange
        Data data = new Data();
        data.add(new Object());
        data.add(new Object());
        data.add("");

        // Act
        Collection<Object> objects = data.getList(Object.class);

        // Assert
        assertEquals(2, objects.size());
    }
}
