# Study Alert

A Java desktop app for macOS that cuts down on social media time by nagging me to study during scheduled study times.

## MVP

Java desktop program for Mac that alerts you more and more annoyingly during scheduled study times until you click the Start studying button, then it runs a focus timer.

- Study schedule: reads the study times from a settings file
- Background check: checks the clock every minute to see if it's study time and if a session has been started
- Escalation: each ignored alert gets more annoying, from a banner notification to an alarm noise to a pop-up to a spoken voice, repeating until you respond
- Start studying: silences all alerts, resets the alert level, and starts a 25-minute timer
- Rechecking: once the timer ends, the app resumes checking and alerts you if you're still in a study block
- Quit: closes the app at any time

## Eisenhower Matrix

| | Urgent | Not Urgent |
|---|---|---|
| Important | MacAlerts class<br>Study time check with escalation<br>Start studying button that stops alerts and runs a timer | Social media detection<br>Full-screen nag with an escape hatch<br>Auto-start when the user logs in |
| Not Important | Use built-in macOS audio<br>Edit settings in a file, not a settings screen<br>Have IntelliJ generate the Maven project | Fancy UI and themes<br>Website blocking using the hosts file<br>iPhone app, sync across devices |

## Future Plans

- Social media detection
- Full-screen nag with an escape hatch
- Auto-start at login

## Tech Stack

Java, Swing, Maven, JUnit 5, IntelliJ IDEA, Git/GitHub, macOS commands (`osascript`, `say`, `afplay`), and launchd.

## Build Plan

1. Project setup
2. Mac alert helper
3. Study timer
4. Study schedule
5. Escalation
6. Full-screen alert
7. Social media detection
8. Stats
9. Auto-start

## System Diagram

![System Diagram](docs/CS193_Proj.drawio-2.png)

