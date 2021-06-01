package com.mycompany.app;

import java.util.concurrent.atomic.AtomicInteger;

public class Peers {
    private static final AtomicInteger COUNTER = new AtomicInteger();

    private final int id;
    private String FirstName;
    private String LastName;

    public Peers(String FirstName, String LastName) {
        this.id=COUNTER.getAndIncrement();
        this.FirstName=FirstName;
        this.LastName=LastName;
    }

    public Peers() {
        this.id=COUNTER.getAndIncrement();
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public int getId() {
        return id;
    }

    public void setFirstName(String FirstName) {
        this.FirstName= FirstName;
    }

    public void setLastName(String LastName) {
        this.LastName= LastName;
    }
}