# Airport Concurrency Simulation

A Java-based multi-threaded simulation engine designed to model real-time airport traffic control scheduling, runway access queues, and terminal gate coordination. 

This project implements advanced concurrent programming patterns to manage independent, moving entities safely without causing operational resource conflicts or data race conditions.

## 🚀 Key Features
* **Multi-Threaded Flight Execution:** Simulates multiple aircraft operating simultaneously as distinct, independent threads.
* **Runway & Gate Synchronization:** Coordinates aircraft landing sequences and gate allocations safely using explicit thread barriers and protection mechanisms.
* **Real-Time Live Event Logging:** Tracks aircraft state changes and scheduling intervals sequentially via clean terminal tracking streams.

## 🛠️ Tech Stack
* **Language:** Java (JDK 17+)
* **Build Architecture:** Maven (`pom.xml`)
* **Core Mechanics:** Concurrency, Multi-threading, Thread Pools, Synchronization Primitives

## 💻 How to Run Locally

1. Clone this repository to your machine:
   ```bash
   git clone [https://github.com/AbdelrahmanMohamed7/airport-concurrency-simulation.git](https://github.com/AbdelrahmanMohamed7/airport-concurrency-simulation.git)
   
