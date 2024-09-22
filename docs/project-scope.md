# Project scope
The following list states the current goals and can act as a general roadmap
without guarantee of a timeline.

The scope should ensure a common understanding of developers and users about
what this projects aims to be.

Note that project does not necessarily mean one single library artifact.

## Goals
- Provide an assertj integration for baseline testing and follow it's general feel
- Provide easy integration with common format assertion libraries
- Provide sensible defaults that work for most projects and extensibility to overwrite all
- Handle basic storage aspects (e.g. file system handling, path management)
- Enable extended storage logic (e.g. extension points to implement remote storage)
- Handle support workflow integrations (e.g. extended reporting, hooks to integrate workflow systems)

## Maybe-Goals
- Remote storage
- Ease custom setups
- Ease serialization & deserialization aspects, and support different providers (e.g. gson vs jackson)

## Non-Goals
- We do not aim to provide a unified api over various format-specific assertion libraries
- We do not aim to provide format-specific assertion libraries, if valid alternatives exist

