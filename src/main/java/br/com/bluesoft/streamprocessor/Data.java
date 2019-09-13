package br.com.bluesoft.streamprocessor;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Data {

    private final Map<Object, List<Object>> dataMap = new LinkedHashMap<>();

    public Data(Object... objects) {
        addAll(Arrays.asList(objects));
    }

    public void add(Object object) {
        if(object != null) {
            put(object.getClass(), object);
        }
    }

    public void addAll(Collection<?> objects) {
        if (objects != null) {
            objects.forEach(this::add);
        }
    }

    public <T> T get(Object key) {
        List<Object> objects = dataMap.get(key);

        if(CollectionUtils.isEmpty(objects)) {
            return null;
        } else if(objects.size() > 1) {
            String msg = String.format("Use getList instead. There are %d objects of type %s",
                                       objects.size(),
                                       key);
            throw new IllegalArgumentException(msg);
        }

        return (T) objects.get(0);
    }

    public Collection<Object> getAll() {
        return dataMap
            .values()
            .stream()
            .flatMap(List::stream)
            .collect(Collectors.toList());
    }

    public <T> List<T> getList(Object key) {
        return (List<T>) dataMap.get(key);
    }

    public void put(Object key, Object value) {
        Validate.notNull(key, "A key é obrigatória");

        if (value != null) {
            dataMap.computeIfAbsent(key, k -> new ArrayList<>());
            dataMap.get(key).add(value);
        }
    }

    @Override
    public String toString() {
        return dataMap.toString();
    }
}
