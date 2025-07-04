package software.openex.gate.binary.base;

import static software.openex.gate.binary.BinaryRepresentable.representationSize;

/**
 * @author Alireza Pourtaghi
 */
public final class ErrorMessage {
    private final String code;
    private final String message;

    public ErrorMessage(final String code, final String message) {
        this.code = code == null ? "" : code;
        this.message = message == null ? "" : message;
    }

    public int size() {
        return representationSize(code) + representationSize(message);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
