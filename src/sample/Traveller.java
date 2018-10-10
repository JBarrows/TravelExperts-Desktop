package sample;

public class Traveller {

    private String fullName;
    private int headCount;

    public void set(String firstName, String lastName, int headCount) {
        this.fullName = firstName + " " + lastName;
        this.headCount = headCount;
    }

    @Override
    public String toString() {
        return fullName + ( headCount > 1 ? ( " +" + (headCount - 1) ) : "" );
    }

    public int getHeadCount() {
        return headCount;
    }
}
