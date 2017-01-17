package serialization.srsf;

/**
 * Created by Ronny on 2017-01-12.
 */
public class Lazy<E>
{
    private final LazyResolver<E> resolver;
    private E value;
    public Lazy(LazyResolver<E> resolver) {
        this.resolver = resolver;
    }

    public E getValue() {
        if(value != null) return value;
        this.value = resolver.resolve();
        return value;
    }
}
