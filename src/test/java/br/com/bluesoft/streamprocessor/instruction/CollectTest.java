package br.com.bluesoft.streamprocessor.instruction;

public class CollectTest {

//    @Test
//    public void executaCollectAllSeForOTipoEsperado() {
//        // Arranje
//        Collect collect = spy(new Collect(Long.class, data -> {
//        }));
//        doNothing().when(collect).collectAll();
//
//        Long object = 1L;
//
//        // Act
//        collect.handle(object);
//
//        // Assert
//        verify(collect).collectAll();
//    }
//
//    @Test
//    public void naoExecutaCollectAllSeNaoForOTipoEsperado() {
//        // Arranje
//        Collect collect = spy(new Collect(Long.class, data -> {
//        }));
//        doNothing().when(collect).collectAll();
//
//        String object = "1";
//
//        // Act
//        collect.handle(object);
//
//        // Assert
//        verify(collect, never()).collectAll();
//    }
//
//    @Test
//    public void executaACallbackAoColletar() {
//        // Arranje
//        AtomicBoolean executed = new AtomicBoolean();
//        Collect collect = new Collect(Long.class, data -> executed.set(true));
//
//        // Act
//        collect.collect(new Data());
//
//        // Assert
//        assertTrue(executed.get());
//    }
}
