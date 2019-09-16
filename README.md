# Bluesoft Stream Processor

A pipeline of instructions that can be used to process a stream of data.
The pipeline sends the same data throught all pipes which in turn pass data through a chain of instructions.
Each instruction is responsible to process the data and decide whether to pass on to the next.
You can create any kind of instruction that suits your domain building your own stream processing language.

![alt tag](https://user-images.githubusercontent.com/9370679/64796647-66327f80-d556-11e9-9694-dd26cac77ad8.png)

The main reason behind this framework is to abstract this kind of structure:
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
Into something like this:
```java

pipeline
    .pipe(
        Instructions
            .groupBy(Header.class)
            .groupBy(Block.class)
            .join(LineA.class)
            .join(LineB.class)
            .map(data -> {
                Header header = data.get(Header.class);
                Block block = data.get(Block.class);
                List<LineA> as = data.getList(LineA.class);
                LineB lineB data.get(LineB.class);

                return new Data(convert(header, block, as, lineB));
            })
    )
```

# Usage

## Instruction

An instruction is the framework's building block, every instruction can keep state to be collected later and decide weather to pass data on to the next instruction.
The state can be cleared by the instruction itself or throught the clearAll method issuing a clear call to all instructions on the same pipe.

```java

public class Map extends Instruction {

    private Function<Data, Data> mapper;

    Map(Function<Data, Data> mapper) {
        this.mapper = mapper;
    }

    @Override
    public void handle(Object object) {
        handleNext(object);
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

## Typed instruction

You can have an instruction that only handles a especific type.
By default typed instructions always pass data on to the next instruction.

```java
public class Store extends TypedInstruction<Record> {

    private Record record;

    public Store(Class<Record> type) {
        super(type);
    }

    @Override
    protected void handleTyped(Record record) {
        this.record = record;
    }

    @Override
    public void collect(Data data) {
        data.add(record);
        collectNext(data);
    }

    @Override
    public void clear() {
        record = null;
    }
}

```

## Pipeline

The pipeline is the entry point, it will try to collect data at every call to the handle method.

```java

Pipeline pipeline = new Pipeline()
    .pipe(instructions)
    .pipe(instructions2);

stream
    .flatMap(o -> {
        Optional<Data> data = pipeline.handle(o);
        return (data.isPresent())
               ? data.get().getAll().stream()
               : Stream.empty();
    });
    
```

# Example
Imagine you want to import a csv file containing products of every supermarket in the country which can have different kinds of packaging.

**File Structure**
- File header  
- Supermarket  
- Product  
- Pack

**File example**
```csv
header; 2019/01/01  
supermarket; Wallmart  
product; coke  
pack; 12; $10,00  
pack; 1; $1,00  
product; water  
pack; 12; $8,00  
supermarket; Target  
product; smartphone  
pack; 1; $1.000,00  
```
**Code**

Assuming that each row has already been converted to a POJO, our code could look like this:

```java

pipeline
    .pipe(
        new GroupBy<>(Header.class),
        new GroupBy<>(Supermarket.class),
        new GroupBy<>(Product.class),
        new Join<>(Pack.class),
        new Map(data -> {
            Header header = data.get(Header.class);
            Supermarket supermarket = data.get(Supermarket.class);
            Product product data.get(Product.class);
            List<Pack> packs = data.getList(Pack.class);

            return new Data(convert(header, supermarket, product, packs));
        })
    );
```

The Join instruction will store data of the same type and clear itself on collect.
```java
public class Join extends TypedInstruction<T> {

    private List<T> objects = new ArrayList<>();

    public Join(Class<T> type) {
        super(type);
    }

    @Override
    protected void handleTyped(T object) {
        objects.add(object);
    }

    @Override
    public void collect(Data data) {
        data.addAll(objects);
        clear();
        collectNext(data);
    }

    @Override
    public void clear() {
        elements.clear();
    }
}

```

The group by instruction will collect all data and clear the pipe at every change.
```java
public class GroupBy extends TypedInstruction<T> {

    private T object = new ArrayList<>();

    public Join(Class<T> type) {
        super(type);
    }

    @Override
    protected void handleTyped(T object) {
        if(isNewGroup()) {  
            collecAll();
            clearAll();
        }
        
        this.object = object;
    }

    @Override
    public void collect(Data data) {
        data.add(object);
        collectNext(data);
    }

    @Override
    public void clear() {
        object = null;
    }
    
    private boolean isNewGroup() {
        return this.object != null
    }
}

```
