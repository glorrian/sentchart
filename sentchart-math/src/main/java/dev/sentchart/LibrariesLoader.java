package dev.sentchart;

import dev.sentchart.jni.LibraryUtils;
import dev.sentchart.math.NativeMath;

public class LibrariesLoader {
    public static void loadAll() {
        LibraryUtils.loadFromResource(NativeMath.class, "native_math");
    }
}
