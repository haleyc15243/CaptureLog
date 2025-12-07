# CaptureLog - Developer Growth Tracking System

A flexible daily note and capture system built with Kotlin Multiplatform.

## Project Overview

CaptureLog is a mobile-first app for tracking professional development through:
- Daily note system with customizable capture templates
- Flexible field types for different use cases
- Action queue for prioritized learning/tasks
- Full-text search and tagging

**Timeline:** 6-8 weeks (KMP from the start)
**Purpose:** Portfolio project + personal productivity tool

## Directory Structure

```
CaptureLog-Project/
├── README.md (this file)
├── docs/
│   ├── 01-Project-Overview.md
│   ├── 02-Architecture.md
│   ├── 03-Data-Models.md
│   ├── 04-Implementation-Guide.md
│   └── 05-Development-Roadmap.md
├── design/
│   ├── system-visual.svg
│   └── wireframes.md (placeholder)
├── architecture/
│   ├── kmp-structure.md
│   ├── database-schema.md
│   └── dependency-injection.md
└── references/
    ├── rss-feeds.md
    └── learning-resources.md
```

## Tech Stack

### Multiplatform (commonMain)
- Kotlin 2.1.x
- SQLDelight (database)
- Koin (dependency injection)
- kotlinx.datetime (date/time)
- kotlinx.serialization (JSON)
- Coroutines + Flow
- Ktor (if RSS integration added later)

### Android (androidMain)
- Jetpack Compose
- Material 3
- Navigation Compose

### iOS (iosMain)
- SwiftUI or Compose Multiplatform
- (TBD based on preference)

## Quick Start

See `docs/04-Implementation-Guide.md` for step-by-step setup instructions.

## Key Features

1. **Template-Based Captures** - Flexible system for different capture types
2. **Daily Notes** - Main hub for organizing daily captures
3. **Action Queue** - Prioritized list of items to try/learn/do
4. **Search & Filter** - Full-text search across all data
5. **Tag Management** - Organize and categorize captures

## Why This Project?

- **Real Problem:** You'll actually use it daily
- **Modern Stack:** Showcases KMP, SQLDelight, Koin, Compose
- **Flexible Architecture:** Template system demonstrates extensibility
- **Portfolio Quality:** Production-ready code with tests
- **Learning Opportunity:** RSS integration, background sync (future)

## Next Steps

1. Review `docs/01-Project-Overview.md` for high-level design
2. Check `docs/02-Architecture.md` for system architecture
3. Follow `docs/04-Implementation-Guide.md` to start building
4. Reference `docs/05-Development-Roadmap.md` for milestones

---

Built with ❤️ and Kotlin
