# Bluesoft Stream Processor

A pipeline of instructions that can be used to process a stream of data.
The pipeline sends the same data throught all pipes which in turn pass data through a chain of instructions.
Each instruction is responsible to process the data and decide whether to pass on to the next.

The main reason behide this framework is to abstract this kind of structure
```java

Header header;
Block block;
List<LineA> as;

for (Object line : lines) {
    if (isHeader(line)) {
        header = (Header)line;
    } else if (isBlock(line)) {
        block = (Block)line;
    } else if (isLineA(line)) {
        as.add((LineA)line);
    } else if (isLineB(line)) {
        LineB lineB = line;
        Pojo pojo = convert(header, block, as, lineB);
        as.clear();
    }
}
```
Into samething like this
```java

pipeline
    .pipe(
        .groupBy(Header.class)
        .groupBy(Block.class)
        .join(LineA.class)
        .join(LineB.class)
        .collect(data -> {
            Header header = data.get(Header.class);
            Block block = data.get(Block.class);
            List<LineA> as = data.getList(LineA.class);
            LineB lineB data.get(LineB.class);
            
            Pojo pojo = convert(header, block, as, lineB);
            
            return new Data(pojo);
        })
    )
```

![alt tag](https://user-images.githubusercontent.com/9370679/64796647-66327f80-d556-11e9-9694-dd26cac77ad8.png)

# Example

## Instruction


```java



```

## Pipeline

```java

Pipeline pipeline = new Pipeline();

```

```java

stream
    .flatMap(o -> {
        Optional<Data> data = pipeline.handle(o);
        return (data.isPresent())
               ? data.get().getAll().stream()
               : Stream.empty();
    });
    
```
