# Bluesoft Stream Processor

A pipeline of instructions that can be used to process a stream of data.
The pipeline sends the same data throught all pipes which in turn pass data through a chain of instructions.
Each instruction is responsible to process the data and decide whether to pass on to the next.
You can create any kind of instruction that suits your domain.

![alt tag](https://user-images.githubusercontent.com/9370679/64796647-66327f80-d556-11e9-9694-dd26cac77ad8.png)

The main reason behind this framework is to abstract this kind of structure
```java

Header header;
Block block;
List<LineA> as = new ArrayList();
List<Object> objects = new ArrayList();

for (Object line : lines) {
    if (isHeader(line)) {
        header = (Header)line;
    } else if (isBlock(line)) {
        block = (Block)line;
    } else if (isLineA(line)) {
        as.add((LineA)line);
    } else if (isLineB(line)) {
        LineB lineB = (LineB)line;
        
        objects.add(convert(header, block, as, lineB));
        
        as.clear();
    }
}
```
Into something like this
```java

pipeline
    .pipe(
        Instructions
            .groupBy(Header.class)
            .groupBy(Block.class)
            .join(LineA.class)
            .join(LineB.class)
            .when(LineB.class)
            .collect(data -> {
                Header header = data.get(Header.class);
                Block block = data.get(Block.class);
                List<LineA> as = data.getList(LineA.class);
                LineB lineB data.get(LineB.class);

                Object object = convert(header, block, as, lineB);

                return new Data(object);
            })
    )
```

# Usage

## Instruction

Each instruction can handle data and have it's data collected and state cleared.

```java

public class Collect extends Instruction {

    private Function<Data, Data> mapper;

    Collect(Function<Data, Data> mapper) {
        this.mapper = mapper;
    }

    @Override
    public void handle(Object object) {
        collectAll();
    }

    @Override
    public void collect(Data data) {
        collectNext(mapper.apply(data));
    }

    @Override
    public void clear() {
    }
}
```

### Typed instruction

You can have an instruction that only handle a especific type

```java
public class Join extends TypedInstruction<Record> {

    private List<Record> elements = new ArrayList<>();

    public Join(Class<Record> type) {
        super(type);
    }

    @Override
    protected void handleTyped(Record record) {
        handleNext(record);
    }

    @Override
    public void collect(Data data) {
        data.addAll(elements);
        collectNext(data);
    }

    @Override
    public void clear() {
        elements.clear();
    }
}

```

## Pipeline

The pipeline will try to collect data at every call to the handle method

```java

Pipeline pipeline = new Pipeline()
    .pipe(instructions)
    .pipe(instructions);

stream
    .flatMap(o -> {
        Optional<Data> data = pipeline.handle(o);
        return (data.isPresent())
               ? data.get().getAll().stream()
               : Stream.empty();
    });
    
```
