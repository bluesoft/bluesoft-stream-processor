package br.com.bluesoft.streamprocessor.instruction;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import br.com.bluesoft.streamprocessor.Data;

public class Instructions {

    public static Instruction map(Function<Data, Data> mapper) {
        return new Map(mapper);
    }

    public static Instruction chain(Instruction... instructions) {
        return chain(Arrays.asList(instructions));
    }

    public static Instruction chain(List<? extends Instruction> instructions) {
        Instruction chain = instructions.get(0);

        instructions
            .stream()
            .peek(instruction -> instruction.setChain(chain))
            .reduce(
                (instruction1, instruction2) -> {
                    instruction1.setNext(instruction2);
                    return instruction2;
                });

        return chain;
    }
}
