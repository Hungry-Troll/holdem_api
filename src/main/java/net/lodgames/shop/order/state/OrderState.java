package net.lodgames.shop.order.state;

public interface OrderState<E> {
    void paid(E e);
    void cancel(E e);
    void refund(E e);
}
