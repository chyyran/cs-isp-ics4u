package serialization.srsf;

/**
 * A factory that creates and returns an object at a later time than instantiation.
 * @param <T> The class of the resolving object.
 */
public interface LazyResolver<T>
{
    /**
     * The factory method that creates and instantiates the requested object
     * @return
     */
    T resolve();

    /**
     * A static instance of LazyResolver that always resolves a null value.
     */
    LazyResolver NULL_RESOLVER = new LazyResolver() {
        @Override
        public Object resolve() {
            return null;
        }
    };
}
