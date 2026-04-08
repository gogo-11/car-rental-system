# Car Rental System (Java Console Project)

This is my Java Foundation course project.  
The target is to build a console app for managing a car rental system

## What is implemented

- Core entities: `Car`, `Customer`, `Rental`
- Service layer: `CarRentalService` + `CarRentalServiceImpl`
- Validators: `Validator`, `CarValidator`, `CustomerValidator`, `RentalValidator`
- CSV storage:
  - readers: `CarStorageReader`, `CustomerStorageReader`, `RentalStorageReader`
  - writers: `CarStorageWriter`, `CustomerStorageWriter`, `RentalStorageWriter`
- How the Application Works:
  - `CarRentalApp` (startup loading)
  - `CommandHandler` (command execution)
  - `ExceptionHandler` (handles runtime/user errors)

## Main features

- Add car (UUID is auto-generated)
- Add customer (UUID is auto-generated, unique email check)
- Find customer ID by email (`Get my customer id`)
- Edit car
- Remove car (no actual delete, only `REMOVED` status)
- List all cars
- List available cars
- Search cars by ID, model, and status
- Rent car
- Return car
- Save and exit (writes current data to CSV files)

## Console commands

These are the main commands supported in the menu:

- `Add Car`
- `Add Customer`
- `Get my customer id`
- `Rent Car`
- `Return Car`
- `Edit Car <carId>`
- `List Cars`
- `List Available Cars`
- `Search by model <model>`
- `Search by status <status>`
- `Search by ID <carId>`
- `Remove <carId>`
- `Help`
- `Save and Exit`

## Data files

The app reads and writes these files:

- `src/main/resources/database/cars.csv`
- `src/main/resources/database/customers.csv`
- `src/main/resources/database/rentals.csv`

On launch all the data from these files is loaded into service.

When `Save and Exit` is executed, current data in the memory is written back to them

## Project structure

- `src/main/java/app` - app startup + command handling + console I/O
- `src/main/java/entities` - entity classes and enums
- `src/main/java/services` - business logic implementation
- `src/main/java/services/validator` - validations
- `src/main/java/interfaces` - core interfaces (`Identifiable`, `Rentable`, `Searchable`)
- `src/main/java/interfaces/service` - service interfaces
- `src/main/java/interfaces/storage` - storage interfaces
- `src/main/java/storage/reader` - CSV files readers
- `src/main/java/storage/writer` - CSV files writers

## How to run

1. Compile:

```bash
mvn clean compile
```

2. Run:

```bash
java -cp target/classes app.CarRentalApp
```

## Current status

The application works properly and all around for cars, customers, and rentals with CSV files management.
