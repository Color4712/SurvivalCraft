# 🌴 Survival Craft

**Survival Craft** is our team’s first game — a survival experience across wild and mysterious islands.
Players will explore, survive, craft, and evolve while facing various challenges.
*Note: the name is temporary and may change later.*

## 🎮 About the Game

Your journey begins on a remote island...
Each island is unique, filled with danger, secrets, and opportunities to grow.

## ⚙️ Planned Features

- 🏝️ Multiple islands (possibly procedurally generated)
- 🔨 Crafting, building, and resource management
- 📈 Character progression and skill upgrades
- 🌡️ Survival mechanics: hunger, weather, tools, threats
- 🧭 Exploration, hidden elements, and adventure

## 🚧 Development Status

The project is currently in **early development** and the first playable slice is **alpha 0.0.1 (Java)**.
We are actively working on core systems and mechanics.

### 🧪 Alpha 0.0.1 Scope
- Text-based vertical slice implemented in Java (same language as Minecraft)
- Basic player stats (hunger, energy) and a minimal inventory cap
- Sample island with gatherable resources and a simple crafting decision tree
- Deterministic two-day survival loop to demo pacing and logging

### ▶️ Running the alpha build
1. Make sure you have **Java 17+** and **Gradle 8.7+** installed.
2. Build and run the console slice:
   ```bash
   gradle run
   ```
3. Run the unit tests:
   ```bash
   gradle test
   ```

### 🚀 Publishing alpha 0.0.1 to GitHub Releases
1. Ensure your branch is up to date on GitHub.
2. Create and push the tag for the alpha release:
   ```bash
   git tag v0.0.1-alpha
   git push origin v0.0.1-alpha
   ```
3. The **Release Alpha** GitHub Actions workflow will:
   - Build and test the project via Gradle
   - Package the JAR artifact from `build/libs/`
   - Publish a pre-release on GitHub Releases named after the tag (e.g., `v0.0.1-alpha`)

## 👥 About the Team

We are a small, passionate team united by the idea of creating a deep and atmospheric survival game.
We build openly, collaboratively, and with a lot of heart.

## 📬 Feedback & Contributions

We welcome your ideas, feedback, and suggestions.
Feel free to open issues or join discussions here on GitHub.

## 📢 Stay Updated

We’ll be posting regular updates — follow the project to stay in the loop!

---

🧑‍💻 Developed with ❤️ by the **Survival Craft** team
