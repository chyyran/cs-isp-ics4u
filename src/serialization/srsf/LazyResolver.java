package serialization.srsf;

public interface LazyResolver<E>
{
    E resolve();

    LazyResolver NULL_RESOLVER = new LazyResolver() {
        @Override
        public Object resolve() {
            return null;
        }
    };
}
