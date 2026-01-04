
# XSON

**Module**: Data Exchange and Interoperability  
**Supervisor**: Prof. El Habib NFAOUI  
**Program**: Master WISD 2025-2026

------------------------------------------------------------------------

## Description
XSON is a JavaFX application developed with Maven for managing user accounts, files, and archives. The application provides advanced features for bidirectional conversion between JSON and XML formats, with implementations using external APIs (Jackson, org.json) and custom implementations without external dependencies.

## Main Features

### 🔄 JSON ↔ XML Conversion
The application offers sophisticated utilities for data conversion:

#### With API (External Libraries)
- **JsonToXml**: Uses `org.json` and Jackson to convert JSON to XML with automatic formatting and indentation.
- **XmlToJson**: Uses `Jackson XmlMapper` to convert XML to JSON with pretty-printing.

#### Without API (Custom Implementation)
- **JsonToXml**: Manual JSON parser with recursive XML generation and formatting.
- **XmlToJson**: Custom DOM XML parser with conversion to JSON structure.

### 👤 Account and User Management
- Account creation and authentication
- User profile management with roles (guest/admin)
- Advanced security with BCrypt password hashing
- User data validation

### 📁 File and Archive Management
- Upload and storage of XML/JSON files
- Automatic management of file pairs (JSON ↔ XML)
- Organization into archives with user associations
- Search and filter by modification date
- Full file metadata (name, type, date)

### 🖥️ JavaFX User Interface
- Main dashboard with dual-panel editor (JSON/XML)
- Real-time conversion between formats
- Archive and file management
- Intuitive interface with custom themes

## Technologies Used
- **Java 21** - Programming language
- **JavaFX 21** - User interface
- **MySQL** - Database
- **Maven** - Dependency management
- **Jackson** - JSON/XML serialization
- **org.json** - JSON manipulation
- **Jakarta JSON** - Standard JSON API
- **JDBC** - Database connection

## Installation

### Prerequisites
- Java 21 or higher
- Maven 3.x
- MySQL Server

### Database Configuration
1. Create a MySQL database named `changeData`
2. Update the connection settings in `src/main/resources/application.properties`:
   ```properties
   db.driver=com.mysql.cj.jdbc.Driver
   db.url=jdbc:mysql://localhost:3306/changeData?useSSL=false&serverTimezone=UTC
   db.user=your_username
   db.password=your_password
   ```

### Installation and Execution
1. Clone the repository:
   ```bash
   git clone https://github.com/Kwimoad/XSON.git
   cd application
   ```

2. Compile the project:
   ```bash
   mvn clean compile
   ```

3. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="Main"
   ```

## Usage

### JSON/XML Conversion
```java
// With API
import org.utils.withAPI.JsonToXml;
String xml = JsonToXml.convertJsonToXmlPretty(jsonString, "root");

// Without API
import org.utils.withoutAPI.JsonToXml;
String xml = JsonToXml.convertJsonToXmlPretty(jsonString, "root");
```

### Graphical Interface
1. Launch the application
2. Log in or create an account
3. Use the dual-panel editor to:
   - Enter JSON and convert to XML
   - Enter XML and convert to JSON
   - Save converted files
4. Manage your archives in the dedicated tab

## Architecture
```
src/main/java/org/
├── controller/      # Business controllers (File, Account, etc.)
├── dto/             # Data Transfer Objects (User, FileInformation, etc.)
├── Models/          # Repositories and data access
├── service/         # Business services (Authentication)
├── utils/           # Conversion and validation utilities
│   ├── withAPI/     # Conversions using external libraries
│   └── withoutAPI/  # Custom conversions
├── views/           # JavaFX user interfaces
│   ├── style/       # UI styles and constants
│   └── *.java
├── security/        # Security and password management (BCrypt)
├── database/        # Database connection and management
├── resource/        # Application resources (ResourceDB)
└── Main.java        # Application entry point
```

## Demo Video
For a complete demonstration of JSON/XML conversion features and the user interface, watch our explanatory video available on Drive.

## Contributors
- Aouad Abdelkarim

## License
This project is licensed under MIT. See the LICENSE file for more details.
