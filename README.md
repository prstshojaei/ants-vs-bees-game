# Ants vs Some Bees — JavaFX Tower Defence

A desktop tower-defence game built in **Java** and **JavaFX**, where the player deploys a colony of ants to defend their Queen from waves of invading bees. Developed as a group project for the **MSc Software Engineering (CSYM026)** module at the **University of Northampton**, working from a provided legacy Java Swing starter engine and extending it into a complete, packaged, playable game.

> ⚠️ **This was a 3-person team project.** This repository (`github.com/prstshojaei/ants-vs-bees-game`) is my personal copy for portfolio purposes. The original team repository and full credits are linked below. My specific contributions are listed in the [My Contributions](#my-contributions) section.

---

## Gameplay

- Deploy 13+ ant types, each with distinct behaviour and food costs (Harvester, Thrower, Wall, Fire, Ninja, Hungry, Bodyguard, Scuba, Slow/Stun/Short/Long Thrower, and the Queen).
- Defend against standard, **Ghost** (50% dodge), and **Zombie** (area-of-effect) bees.
- Three difficulty levels with **seeded level generation** — players can share a seed to replay the exact same map and bee waves.
- User accounts with a persistent leaderboard, score tracking, and a timer.
- Pause / resume / reset, in-game settings (music toggle, game speed), help screen, and an end-game score screen.

## Tech Stack

- **Language:** Java
- **UI:** JavaFX (migrated from the original Java Swing engine)
- **Testing:** JUnit 5 (white-box unit testing)
- **Build / Packaging:** Maven, `jlink` / `jpackage` for native `.exe` and `.app` installers
- **CI/CD:** GitHub Actions (automated cross-platform builds)
- **Tools:** Git, GitHub, Jira (Scrum), Slack

## Architecture Overview

The game is built around a layered inheritance hierarchy that separates shared behaviour from specialist behaviour, with UI and file handling kept separate from the core game rules.

- **`Insect`** — abstract root class holding shared state (armour, place, status) and abstract `action()` / `leavePlace()` methods.
- **`Ant` / `Bee`** — abstract subclasses adding player-unit and enemy-specific logic.
- **Concrete units** — each ant and bee type overrides only what makes it unique (e.g. `FireAnt` overrides `reduceArmor()` to explode on death; `GhostBee` overrides it for evasion).
- **`Place` / `AntColony` / `Hive`** — model the tunnel grid as a linked list of nodes, with seeded random generation for water tiles and bee waves.
- **Design patterns:** Observer (JavaFX mouse/resize event system), Singleton (Queen Ant).

## Running the Game

The `main` branch of the original team repository contains full setup instructions and a downloadable Windows `.exe` release build. To run from source:

```bash
# Compile (point the classpath at your local JavaFX SDK)
javac -cp "lib/junit-platform-console-standalone-6.0.3.jar;<path-to-javafx-sdk>/lib/*" \
      -d out src/core/*.java src/ants/*.java src/tests/*.java

# Run
java -cp "out;<path-to-javafx-sdk>/lib/*" core.AntsVsSomeBees
```

## Running the Tests

```bash
java -jar lib/junit-platform-console-standalone-6.0.3.jar \
     -cp "out;<path-to-javafx-sdk>/lib/*" --scan-classpath
```

Tests follow an **arrange / act / assert** structure, with one behaviour per test method for easy diagnosis. JavaFX-dependent classes are tested headlessly using `Platform.startup()`.

---

## My Contributions

My work focused on the core game logic, the full user interface, and the test suite:

**Game logic & object model**
- The full insect inheritance hierarchy: the abstract `Insect` base class, the `Ant` and `Bee` abstract classes, and all concrete ant/bee subclasses (Thrower variants, Fire, Ninja, Hungry, Wall, Bodyguard, Scuba, Ghost & Zombie bees).
- The `Place`, `AntColony`, and `Hive` classes — the spatial grid, food system, deployment rules, and seeded wave generation.

**Design pattern**
- Implemented the **Observer pattern** in the mouse-event system (`handleClick`, `handleMouseOver`, and window-resize listeners) so the UI reacts to input and rescales accurately at any window size.

**UI & menu system**
- The complete menu system: `GameAppController` (central navigation hub) plus every screen — main menu, help, game screen, pause overlay, settings, game-over, and score screens.
- **User account system** (`UserManager`, `UserAccount`) with JSON persistence, the `SeedGenerator`, and the `ScoreManager` / `GameTimer` scoring systems.

**Testing**
- The **JUnit 5 white-box test suite**, written alongside development to verify internal logic paths and edge cases for each class.

## Team

A 3-person team. Other members contributed the JavaFX migration, graphics & audio systems, native packaging and CI/CD pipeline, the Scrum/project management, the difficulty system, and black-box testing documentation. Full per-section authorship is documented in the project report.

## Acknowledgements

- Built on a starter game engine provided by the University of Northampton for the CSYM026 module.
- Original team repository: `https://github.com/thedirge0matic/Group1-CSYM026-AntsVsBees`
