[![progress-banner](https://backend.codecrafters.io/progress/shell/eb3ebe86-ed97-4aea-bb51-c81237eae9c0)](https://app.codecrafters.io/users/codecrafters-bot?r=2qF)

# Build Your Own Shell

This project is a custom implementation of a POSIX-compliant shell built in Java. It interprets shell commands, runs external programs, and supports built-in commands like `cd`, `pwd`, `echo`, and more. The shell also includes features like command parsing, redirection, and autocompletion.

This project is part of the ["Build Your Own Shell" Challenge](https://app.codecrafters.io/courses/shell/overview) by Codecrafters. It is a great way to learn about how shells work internally and practice advanced Java programming concepts.


## 📑 Table of Contents

- [Build Your Own Shell](#build-your-own-shell)
  - [📑 Table of Contents](#-table-of-contents)
  - [📌 What This Project Does](#-what-this-project-does)
  - [⚙️ How to Set Up and Run](#️-how-to-set-up-and-run)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Usage](#usage)
  - [✨ Key Features](#-key-features)
  - [🔍 How It Works Internally](#-how-it-works-internally)
  - [🧰 Technologies Used](#-technologies-used)
  - [📁 Folder and File Structure](#-folder-and-file-structure)
  - [💡 Challenges \& Lessons Learned](#-challenges--lessons-learned)
    - [Challenges](#challenges)
    - [Lessons Learned](#lessons-learned)
  - [🛠️ Why I Built This Project](#️-why-i-built-this-project)


## 📌 What This Project Does

This project implements a shell that:

- Accepts user input to execute commands.
- Supports built-in commands like `cd`, `pwd`, `echo`, and `exit`.
- Runs external programs by searching for executables in the system's `PATH`.
- Handles input/output redirection (e.g., `>`, `>>`, `2>`).
- Provides autocompletion for commands and executables.

The purpose of this project is to understand how shells work and practice building a complex system from scratch.


## ⚙️ How to Set Up and Run

### Prerequisites

- **Java 23**: Ensure you have Java 23 installed.
- **Maven**: Install Maven for building the project.
- **Linux Environment**: The shell uses POSIX features, so it works best on Linux.

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Md-Talim/codecrafters-shell-java.git
   cd codecrafters-shell-java
   ```

2. Build the project using Maven:
   ```bash
   mvn clean package
   ```

3. Run the shell:
   ```bash
   ./your_program.sh
   ```

### Usage

- You'll see a prompt like `$`.
- Enter commands like `pwd`, `echo Hello`, or `ls`.
- Use `Ctrl+D` or `exit` command to exit the shell.


## ✨ Key Features

- **Built-in commands**
  Supports common commands like:
  - `cd`: Change directory
  - `pwd`: Print working directory
  - `echo`: Print text
  - `exit`: Exit the shell
  - `type`: Show whether a command is built-in or external

- **External command support**
  If a command isn’t built-in, the shell searches the system `PATH` and runs it using Java’s `ProcessBuilder`.

- **I/O redirection**
  Redirect output and errors using:
  - `>` (overwrite)
  - `>>` (append)
  - `2>` (redirect stderr)

- **Autocompletion**
  Press `Tab` to auto-complete command names or see suggestions.

- **Error handling**
  - Displays clear error messages when commands fail or are used incorrectly
  - Handles missing files, bad arguments, and permission issues gracefully
  - Prevents the shell from crashing due to unexpected input or runtime errors

- **Raw terminal mode**
  Switches the terminal to raw mode to capture user input character by character—enabling advanced input behavior like autocompletion.


## 🔍 How It Works Internally

Here's a simplified look at how the shell is structured under the hood:

| Component              | Role                                                                      |
| ---------------------- | ------------------------------------------------------------------------- |
| `Shell.java`           | The main loop that reads input, parses commands, and executes them.       |
| `Parser.java`          | Breaks the input line into tokens, handles redirection and quoting.       |
| `CommandRegistry`      | Registers and manages built-in commands.                                  |
| `ExternalCommand.java` | Executes non-built-in programs via `ProcessBuilder`.                      |
| `Redirection.java`     | Handles output and error redirection to files.                            |
| `AutoCompleter.java`   | Provides suggestions for commands using `Tab`.                            |
| `Termios.java`         | Enables raw terminal mode via JNA to control low-level terminal settings. |


## 🧰 Technologies Used

- **Java**: The primary programming language.
- **Maven**: For building and managing dependencies.
- **JNA (Java Native Access)**: For interacting with low-level terminal settings.
- **POSIX**: For shell behavior and command execution.


## 📁 Folder and File Structure

```
src/
  main/
    java/
      Main.java                # Entry point of the program
      shell/
        Shell.java             # Core shell logic
        command/               # Built-in commands
        environment/           # Environment management
        io/                    # Input/output utilities
        parser/                # Command parsing
        process/               # External command execution
        terminal/              # Terminal control
        autocomplete/          # Autocompletion logic
```

**Key files:**
- `Main.java`: Starts the shell and handles user input.
- `Shell.java`: Manages commands and execution.
- `Parser.java`: Parses user input into commands and arguments.
- `SystemProcessExecutor.java`: Executes external commands.


## 💡 Challenges & Lessons Learned

### Challenges

1. **Command Parsing**:
   - Handling quotes, escape characters, and redirection operators was tricky.

2. **Redirection**:
   - Managing file output and error streams required careful design.

3. **Autocompletion**:
   - Implementing a responsive and accurate autocompletion system was challenging.

4. **Terminal Control**:
   - Disabling echo and enabling raw mode required low-level terminal manipulation.

### Lessons Learned

- Gained a deep understanding of how shells work.
- Improved skills in Java, especially working with `ProcessBuilder` and file I/O.
- Learned about terminal control using JNA and POSIX standards.


## 🛠️ Why I Built This Project

I built this project to:

- Practice advanced Java programming concepts.
- Learn how Bash/Zsh-like shells work under the hood
- Practice writing clean, modular, and testable Java code
- Solve a real-world systems-level problem from scratch

This project helped me grow as a backend/systems engineer and is one of my favorite learning experiences.
