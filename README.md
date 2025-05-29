# Cashbook GUI Application

This project is a graphical user interface (GUI) implementation of a cashbook application. It allows users to manage their cashbook entries, including adding receipts and payments, and exporting the data to CSV format.

## Project Structure

```
CashbookGUIApp
├── src
│   ├── CashbookApp.java        # Main class to initialize the application
│   ├── CashbookGUI.java        # GUI implementation for user interaction
│   ├── Entry.java              # Class representing a cashbook entry
│   └── utils
│       └── CSVUtils.java       # Utility methods for CSV file operations
├── .gitignore                  # Specifies files to be ignored by Git
└── README.md                   # Documentation for the project
```

## Setup Instructions

1. **Clone the Repository**: 
   ```
   git clone <repository-url>
   ```

2. **Navigate to the Project Directory**:
   ```
   cd CashbookGUIApp
   ```

3. **Compile the Source Code**:
   Use your preferred Java IDE or command line to compile the Java files in the `src` directory.

4. **Run the Application**:
   Execute the `CashbookApp.java` file to start the application.

## Usage Guidelines

- Upon launching the application, users will be presented with a GUI to add cashbook entries.
- Users can input details for receipts and payments, including serial number, date, particulars, ledger folio, discounts, cash, and bank amounts.
- The application provides an option to export all entries to a CSV file for further analysis or record-keeping.

## Additional Information

- Ensure that you have Java Development Kit (JDK) installed on your machine to run the application.
- For any issues or feature requests, please open an issue in the repository.