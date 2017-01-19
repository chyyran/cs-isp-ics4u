package serialization.srsf;

import java.util.ArrayList;
import java.util.List;

public class Lazy<T>
{
    private final LazyResolver<T> resolver;
    private T value;

    public Lazy(LazyResolver<T> resolver) {
        this.resolver = resolver;
    }

    public T getValue() {
        if(value != null) return value;
        this.value = resolver.resolve();
        return value;
    }

    public static <T> Lazy<T> asLazy(final T eagerValue) {
        return new Lazy<>(new LazyResolver<T>() {
            @Override
            public T resolve() {
                return eagerValue;
            }
        });
    }

    public static <E> List<Lazy<E>> asLazyList(final List<E> eagerList) {
        List<Lazy<E>> eList = new ArrayList<>();
        for (E element : eagerList) {
            eList.add(Lazy.asLazy(element));
        }
        return eList;
    }

    public static <E> List<E> getValues(final List<Lazy<E>> lazyList) {
        List<E> eagerList = new ArrayList<>();
        for (Lazy<E> element : lazyList) {
            eagerList.add(element.getValue());
        }
        return eagerList;
    }
}
