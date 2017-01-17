import sun.misc.Unsafe;
import java.lang.reflect.Field;

public class MemoryAllocationHelper {

    private static Unsafe unsafe;
    static {
        try {
            Field singleoneInstanceField = Unsafe.class.getDeclaredField("theUnsafe");
            singleoneInstanceField.setAccessible(true);
            unsafe = (Unsafe) singleoneInstanceField.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static Object allocate(Class<?> classToAllocate) {
        try {
            return classToAllocate.cast(unsafe.allocateInstance(classToAllocate));
        }catch(InstantiationException e) {
            return null;
        }
    }
}
