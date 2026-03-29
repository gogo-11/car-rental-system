# Car Rental System (Java Console Project)

This is a project for the Java Foundation course.  
The idea is to make a console app about a car rental management system.

## Done so far

I created the main classes and most of the service logic:

- `Car`, `Customer`, `Rental`
- `CarRentalService` + `CarRentalServiceImpl`
- enums for car type/status and rental status - `CarType`, `CarStatus`, `RentStatus`
- validators for input checks - `Validator`, `CarValidator`, `CustomerValidator`, `RentalValidator`

## Implemented functionality

At this stage, these methods are implemented in the service:

- Add car with generated UUID
- Add customer
- Edit car
- Remove car (`REMOVED` status)
- List all cars
- List available cars
- Find car by ID
- Search cars by model
- Search cars by status
- Rent car
- Return car

## Project structure

- `entities` -> domain classes (`Car`, `Customer`, `Rental`)
- `interfaces` -> interfaces (`Rentable`, `Searchable`, `CarRentalService`)
- `services` -> business logic (`CarRentalServiceImpl`)
- `services/validator` -> validation classes
- `app` -> app entry point (`CarRentalApp`)

## Build

```bash
mvn clean compile
```

## Current state

The base logic is mostly there, but the app is still not completely finished.

To be done:

- full console command flow in `CarRentalApp`
- CSV loading/saving logic
- startup load + save on exit

## Notes

- IDs are generated with UUID
- when car is removed, it is not deleted, just marked as `REMOVED`
- rentals are kept for history after return

## Personal note

I focused first on OOP structure and business logic, and next I will finish file handling and console interaction.
