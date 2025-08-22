# Log Parser Application

[![Java](https://img.shields.io/badge/Java-11+-orange.svg)](https://openjdk.java.net/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

A robust, extensible Java application for parsing and analyzing various types of log files. Built with clean architecture principles, this tool automatically classifies logs into different categories (APM, Application, Request) and generates comprehensive aggregated reports in JSON format.

## 🚀 Features

### **Multi-Format Log Support**
- **APM Logs**: Performance metrics analysis (min, max, median, average)
- **Application Logs**: Severity-based categorization and counting
- **Request Logs**: HTTP response time percentiles and status code analysis

### **Smart Log Classification**
- Automatic log type detection and routing
- Extensible parser architecture for new log formats
- Intelligent field validation and error handling

### **Comprehensive Output**
- JSON-based aggregated reports
- Separate output files for each log category
- Configurable aggregation strategies

## 🏗️ Architecture

The application follows clean architecture principles with clear separation of concerns:

```
src/main/LogParser/
├── Aggregators/          # Data aggregation logic
├── IO/                   # File handling operations
├── Models/               # Data structures
├── Parsers/              # Log parsing strategies
├── Utils/                # Validation and utility functions
└── LogParserApplication.java  # Main application entry point
```

### **Design Patterns**
- **Factory Pattern**: For creating parsers and aggregators
- **Strategy Pattern**: For different parsing and aggregation strategies
- **Singleton Pattern**: For file handler management

## 📋 Prerequisites

- **Java 11** or higher
- **Maven 3.6** or higher
- **Git** for version control

## 🛠️ Installation

1. **Clone the repository**
   ```bash
   git clone <your-repository-url>
   cd LogParserApplication
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

## 🚀 Usage

### **Basic Usage**
```bash
java -cp target/classes LogParserApplication --file <input-file.txt>
```

### **Example Commands**
```bash
# Parse a log file
java -cp target/classes LogParserApplication --file input.txt

# Parse with absolute path
java -cp target/classes LogParserApplication --file /path/to/logs.txt
```

### **Input File Format**
The application supports various log formats. Here are examples:

**APM Logs:**
```
2024-01-15 10:30:15 APM [PERFORMANCE] ResponseTime=150ms MemoryUsage=512MB
```

**Application Logs:**
```
2024-01-15 10:30:16 APP [INFO] User login successful userId=12345
```

**Request Logs:**
```
2024-01-15 10:30:17 REQ [HTTP] GET /api/users StatusCode=200 ResponseTime=45ms
```

## 📊 Output

The application generates three JSON output files:

- **`apm.json`**: Performance metrics aggregation
- **`application.json`**: Log severity distribution
- **`request.json`**: HTTP request statistics

### **Sample Output Structure**
```json
{
  "logType": "APM",
  "totalLogs": 100,
  "metrics": {
    "responseTime": {
      "min": 45,
      "max": 500,
      "median": 120,
      "average": 125.5
    }
  }
}
```

## 🧪 Testing

Run the test suite to ensure everything works correctly:

```bash
mvn test
```

The project includes comprehensive unit tests for:
- Log parsers
- Aggregators
- File handlers
- Main application logic

## 🔧 Configuration

### **Maven Configuration**
The project uses Maven for dependency management with the following key dependencies:
- **Gson 2.8.9**: JSON processing
- **Jackson 2.14.0**: Advanced JSON operations
- **JUnit Jupiter 5.9.3**: Testing framework

### **Java Version**
Configured for Java 11+ compatibility in `pom.xml`.

## 📁 Project Structure

```
LogParserApplication/
├── src/
│   ├── main/
│   │   └── LogParser/
│   │       ├── Aggregators/      # Data aggregation
│   │       ├── IO/               # File operations
│   │       ├── Models/            # Data structures
│   │       ├── Parsers/           # Log parsing
│   │       └── Utils/             # Utilities
│   └── test/                      # Test files
├── pom.xml                        # Maven configuration
├── README.md                      # This file
└── input.txt                      # Sample input file
```

## 🚀 Extending the Application

### **Adding New Log Types**
1. Create a new parser implementing `LogParser` interface
2. Create a corresponding aggregator implementing `LogAggregator` interface
3. Update the factory classes to include the new implementations

### **Custom Aggregation Strategies**
Implement the `LogAggregator` interface to create custom aggregation logic for specific use cases.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### **Code Style Guidelines**
- Follow Java naming conventions
- Use meaningful variable and method names
- Add comprehensive comments for complex logic
- Ensure all tests pass before submitting

## 🐛 Troubleshooting

### **Common Issues**

**File not found error:**
- Ensure the input file path is correct
- Check file permissions
- Verify the file exists in the specified location

**Parsing errors:**
- Check log format compliance
- Verify all required fields are present
- Review the log validation rules

**Memory issues with large files:**
- Consider processing files in chunks
- Monitor JVM memory settings
- Use streaming approaches for very large files

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**CMPE 202 Individual Project**
- **Course**: CMPE 202
- **Institution**: [Your Institution]
- **Semester**: [Current Semester]

## 🙏 Acknowledgments

- Built with modern Java best practices
- Inspired by real-world log analysis challenges
- Designed for educational and practical use

---

**⭐ Star this repository if you find it helpful!**

For questions or support, please open an issue in the repository.
