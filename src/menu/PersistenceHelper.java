import java.io.*;

/**
 * Saves stuff to a binary file.
 * Uses the object serialization API but doesn't output
 * text files.
 */
public class PersistenceHelper<T extends Serializable> {

    private String filename;
    public PersistenceHelper(String filename) {
        this.filename = filename;
    }

    public void save(T object) throws IOException{
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.filename));
        out.writeObject(object);
        out.flush();
        out.close();
    }

    public T load() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.filename));
        T result = (T)in.readObject();
        in.close();
        return result;
    }
}