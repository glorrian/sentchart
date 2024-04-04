package dev.sentchart.io;

import java.io.IOException;

/**
 * Signals that a file with this permission is not supported
 *
 * <p> This exception will be thrown by the {@link SignalPointsDataBufferReader} constructor when
 * a file with specified extension does not support. All supported extensions recorded in {@link SignalPointsDataBufferReader#FILE_FORMATS}
 *
 */
public class UnsupportedFileFormatException extends IOException {
    @java.io.Serial
    private static final long serialVersionUID = 8272104340014252102L;

    public UnsupportedFileFormatException() {
        super();
    }


    public UnsupportedFileFormatException(String s) {
        super(s);
    }
}