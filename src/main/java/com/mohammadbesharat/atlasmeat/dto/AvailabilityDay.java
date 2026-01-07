package com.mohammadbesharat.atlasmeat.dto;

import java.time.LocalDate;

public class AvailabilityDay {
    
    public LocalDate date;
    public boolean open;
    public int maxOrders;
    public long booked;
    public long remaining;

    public AvailabilityDay(LocalDate date, boolean open, int maxOrders, long booked){
        this.date = date;
        this.open = open;
        this.maxOrders = maxOrders;
        this.booked = booked;
        this.remaining = Math.max(0, maxOrders - booked);
    }

    public LocalDate getDate(){
        return date;
    }

    public boolean isOpen(){
        return open;
    }

    public int getMaxOrders(){
        return maxOrders;
    }

    public long getBooked(){
        return booked;
    }
}
