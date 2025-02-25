## **How to Run the Application**

### **1. Install Docker**
To run the application, you need Docker installed on your system. Follow the official Docker installation guide:

- [Install Docker](https://www.docker.com/get-started)

After installation, verify Docker is working:
```bash
docker --version
```

---

### **2. Run the Application**
1. Clone the repository:
   ```bash
   git clone https://github.com/IRomanchuk06/volunteer_platform
   cd volunteer_platform
   ```

2. Make the `run_app.sh` script executable:
   ```bash
   chmod +x scripts/run_app.sh
   ```

3. Start the application:
   ```bash
   ./scripts/run_app.sh
   ```

   To see detailed logs, use the `--verbose` or `-v` flag:
   ```bash
   ./scripts/run_app.sh --verbose
   ```
