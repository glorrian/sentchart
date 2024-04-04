package dev.sentchart.io;

import java.io.IOException;
import java.util.List;

/**
 * <p>The {@link DataBufferReader} interface reflects working with data using a buffer.
 * The idea of this interface is to conveniently process both large and small amounts of data that are stored in external memory.
 * It is possible to validate the data of their files and multithreaded processing and validation</p>
 */
public interface DataBufferReader<T> {
    /**
     * <p>The method is designed for relatively small data in order
     * to read all the data and conveniently work with them as a single collection.</p>
     * @return A list of tuples with data, the size of the tuple depends on the data format and can contain from 1 to 10 elements
     */
    List<T> readAll() throws IOException;

    /**
     * <p>The method is intended for sequential reading of data "line by line".
     * It is convenient for working with big data, for example,
     * for sequential calculations, where past and future values do not affect</p>
     * @return Data tuple, the size of the tuple depends on the data format and can contain from 1 to 10 elements
     */
    T nextLine() throws IOException;

    /**
     * <p>Returns the buffer pointer to the beginning of the data</p>
     */
    void reset() throws IOException;

    /**
     * <p>Closes the connection of the buffer with the data, after one call, the buffer object becomes unusable</p>
     */
    void close() throws IOException;
}
