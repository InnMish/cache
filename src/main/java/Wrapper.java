package main.java;

public class Wrapper {

    private Object value;
    private long addTime;

    Wrapper(Object value, long addTime) {
        this.value = value;
        this.addTime = addTime;
    }

    long getAddTime() {
        return addTime;
    }

    void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Wrapper other = (Wrapper) obj;
        return this.value == other.value || (this.value != null && this.value.equals(other.value));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return 43 * hash + (this.value != null ? this.value.hashCode() : 0);
    }
}
