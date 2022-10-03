package connection;

import java.io.Closeable;

public interface SenderReceiver extends Closeable {
    public final int BUFFER_SIZE = 40000000;
}
