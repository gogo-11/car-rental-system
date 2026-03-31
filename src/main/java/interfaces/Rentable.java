package interfaces;

/**
 * Interface that marks objects that can be rented
 *
 * All classes implementing this interface should make sure it's possible to be rent
 * by specifying the name of the renter
 */
public interface Rentable {
    /**
     * Rents the object.
     *
     * @param  renterId  the ID of the person renting the object
     */
    void rent(String renterId);

    /**
     * Returns the object from rental
     */
    void returnFromRental();
}
