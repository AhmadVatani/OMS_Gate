package software.openex.gate.binary;

import java.lang.foreign.MemorySegment;
import java.util.List;

import static java.lang.foreign.ValueLayout.*;
import static java.nio.ByteOrder.BIG_ENDIAN;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * A binary representable base interface including utility methods.
 *
 * @author Alireza Pourtaghi
 */
public interface BinaryRepresentable {
    OfByte BYTE = JAVA_BYTE.withOrder(BIG_ENDIAN);
    OfShort SHORT = JAVA_SHORT_UNALIGNED.withOrder(BIG_ENDIAN);
    OfInt INT = JAVA_INT_UNALIGNED.withOrder(BIG_ENDIAN);
    OfLong LONG = JAVA_LONG_UNALIGNED.withOrder(BIG_ENDIAN);

    // Representation's header size
    int RHS = 1 + 1 + 4 + 4;

    // Representation's version
    byte VR1 = 0b00000001;

    // Flags; 8 flags can be used in a single byte
    byte FGS = 0b00000000;

    // Line feed
    byte LFD = 0x0A;

    // Carriage Return
    byte CRT = 0x0D;

    static byte version(final MemorySegment segment) {
        return segment.get(BYTE, 0);
    }

    static byte flags(final MemorySegment segment) {
        return segment.get(BYTE, 1);
    }

    static int id(final MemorySegment segment) {
        return segment.get(INT, 2);
    }

    static int size(final MemorySegment segment) {
        return segment.get(INT, 6);
    }

    default void setCompressed(final MemorySegment segment) {
        segment.set(BYTE, 1, (byte) (flags(segment) | 0b00000001));
    }

    default boolean isCompressed(final MemorySegment segment) {
        return (flags(segment) & 0b00000001) == 0b00000001;
    }

    static int representationSize(final String value) {
        return 4 + value.getBytes(UTF_8).length + 1;
    }

    static int representationSize(final byte[] value) {
        return 4 + value.length;
    }

    static <T> int representationSize(final List<BinaryRepresentation<T>> values) {
        var size = 0;
        for (final var br : values) {
            size += (int) br.representationSize();
        }

        return 4 + size;
    }
}
