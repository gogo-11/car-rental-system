package entities;

public enum CarType {
    SEDAN("Sedan"),
    SUV("SUV"),
    HATCHBACK("Hatchback"),
    WAGON("Wagon "),
    PICKUP_TRUCK("Pickup truck");

    CarType(String typeName) {
        this.typeName = typeName;
    }
    private final String typeName;

    public String getTypeName() {
        return typeName;
    }
}
