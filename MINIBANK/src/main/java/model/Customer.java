package model;

import java.util.Objects;

/**
 * Customer model for MiniBank with Address and Cloneable support.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 02, 03 & 06
 */
public class Customer implements Cloneable, java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private static long customerCounter = 100;

    private final String customerId;
    private String name;
    private String email;
    private String mobile;
    private Address address;

    public static class Address implements Cloneable, java.io.Serializable {
        private static final long serialVersionUID = 1L;
        private String line;
        private String city;
        private String pincode;

        public Address(String line, String city, String pincode) {
            this.line = line;
            this.city = city;
            this.pincode = pincode;
        }

        public String getLine() { return line; }
        public void setLine(String line) { this.line = line; }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getPincode() { return pincode; }
        public void setPincode(String pincode) { this.pincode = pincode; }

        @Override
        public String toString() {
            return line + ", " + city + " (" + pincode + ")";
        }

        @Override
        public Address clone() {
            try {
                return (Address) super.clone();
            } catch (CloneNotSupportedException e) {
                return new Address(line, city, pincode);
            }
        }
    }

    private static synchronized String generateCustomerId() {
        customerCounter++;
        return "CUST" + customerCounter;
    }

    public Customer(String name, String email, String mobile) {
        this.customerId = generateCustomerId();
        this.name = (name != null) ? name.trim() : "Unknown";
        this.email = (email != null) ? email.trim() : "";
        this.mobile = (mobile != null) ? mobile.trim() : "";
    }

    public Customer(String name, String email, String mobile, Address address) {
        this(name, email, mobile);
        this.address = address;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
    public Address getAddress() { return address; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public void setAddress(Address address) { this.address = address; }

    @Override
    public Customer clone() {
        try {
            Customer cloned = (Customer) super.clone();
            if (this.address != null) {
                // Deep-copy the nested Address so changes to the clone do not affect the original Customer
                cloned.address = this.address.clone();
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            Customer copy = new Customer(this.name, this.email, this.mobile);
            if (this.address != null) {
                copy.address = this.address.clone();
            }
            return copy;
        }
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%s, name='%s', email='%s', mobile='%s', address=%s]",
                customerId, name, email, mobile, address != null ? address : "Not Provided");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Customer other)) return false;
        return Objects.equals(this.customerId, other.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }
}
