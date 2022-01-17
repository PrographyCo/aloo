package com.prography.sw.aloocustomer.model;

public class TimeLine {
    private String message;
    private OrderStatus status;

    public TimeLine(String message, OrderStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }



}
