package com.paulomiotto.banking;

import java.util.Objects;

public class Customer {
    private final String id;
    private final String name;

    public Customer(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Customer id cannot be null/blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be null/blank");
        }
        this.id = id;
        this.name = name;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Customer{id='%s', name='%s'}".formatted(id, name);
    }
}
