package serialization.srsf;

import java.util.ArrayList;
import java.util.List;

/**
 * Used in conjunction with LazyResolver to act as a stand-in,
 * or a promise to provide, an object at a later time than instantiation,
 * upon request.
 * @param <T> The class of the object to lazily provide
 * @see LazyResolver
 */
public class Lazy<T>
{
    /**
     * The resolver for this instance of Lazy
     */
    private final LazyResolver<T> resolver;
    /**
     * The cached object if it has already been instantiated
     */
    private T value;

    /**
     * Keeps track if the object has been instantiated
     */
    private boolean valueCreated = false;

    /**
     * Instantiates the Lazy with the given resolver that will provide the object upon request.
     * @param resolver The resolver that will provide the object.
     */
    public Lazy(LazyResolver<T> resolver) {
        this.resolver = resolver;
    }

    /**
     * Resolves, and gets the value requested. If the value has already been instantiated, it will
     * return the cached value. There is only ever one instance of a created value for a given Lazy-wrapped instance
     * of the same value.
     * @return
     */
    public T getValue() {
        if(isValueCreated()) return value; //check if the value is created
        this.value = resolver.resolve(); //resolve the value
        this.valueCreated = true; //indicate the value has been created
        return value;
    }

    /**
     * Returns whether or not the value has been created
     * @return Whether or not the value has been created
     */
    public boolean isValueCreated() {
        return this.valueCreated;
    }

    /**
     * A helper method that wraps already instantiated value into a Lazy object
     * @param eagerValue The already instantiated value
     * @param <T> The class of the given value
     * @return A lazy-wrapped instance of the given value.
     */
    public static <T> Lazy<T> asLazy(final T eagerValue) {
        return new Lazy<>(new LazyResolver<T>() {
            @Override
            public T resolve() {
                return eagerValue;
            }
        });
    }

    /**
     * A helper method that wraps a list of objects into a list of
     * lazy-wrapped versions of the object
     * @param eagerList The list of objects
     * @param <E> The class of the objects
     * @return A list of the lazy-wrapped versions of the given object
     */
    public static <E> List<Lazy<E>> asLazyList(final List<E> eagerList) {
        List<Lazy<E>> eList = new ArrayList<>();
        for (E element : eagerList) {
            eList.add(Lazy.asLazy(element));
        }
        return eList;
    }

    /**
     * A helper method to evauluate all the elements of a list of lazy objects
     * @param lazyList The list of lazy-wrapped objects
     * @param <E> The class of the objects
     * @return A list of the fully instantiated, unwrapped objects.
     */
    public static <E> List<E> asList(final List<Lazy<E>> lazyList) {
        List<E> eList = new ArrayList<>();
        for (Lazy<E> element : lazyList) {
            eList.add(element.getValue());
        }
        return eList;
    }

}
