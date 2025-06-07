package software.openex.gate.binary.order;

import software.openex.gate.binary.BinaryRepresentation;

import java.lang.foreign.Arena;

/**
 * @author Alireza Pourtaghi
 */
public final class OrderBinaryRepresentation extends BinaryRepresentation<Order> {
    private final Order order;

    public OrderBinaryRepresentation(final Order order) {
        super(order.size());
        this.order = order;
    }

    public OrderBinaryRepresentation(final Arena arena, final Order order) {
        super(arena, order.size());
        this.order = order;
    }

    @Override
    protected int id() {
        return order.representationId();
    }

    @Override
    protected void encodeRecord() {
        try {
            putLong(order.getId());
            putLong(order.getTs());
            putString(order.getSymbol());
            putString(order.getQuantity());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
