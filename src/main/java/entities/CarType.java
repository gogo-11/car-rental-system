package entities;

public enum CarType {
    SEDAN("Sedan"),
    SUV("SUV"),
    HATCHBACK("Hatchback"),
    WAGON("Station wagon"),
    PICKUP("Pickup truck"),
    COUPE("Coupe"),
    CONVERTIBLE("Convertible");

    CarType(String typeName) {
        this.typeName = typeName;
    }
    private final String typeName;

    public String getTypeName() {
        return typeName;
    }
}
