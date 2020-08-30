# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/) and this project adheres to [Semantic Versioning](http://semver.org/).

## [Unreleased] - yyyy-mm-dd

### Changed

- Refactor old project classes via game dev patterns implemetation.

## [0.2.3-alpha.2] - 2020-08-30

### Fixed

- The render order for units on screen.

## [0.2.2-alpha.2] - 2020-08-26

### Changed

- Add split server into internal and dedicated services for running the game multiplayer:
  - server and clients separately;
  - main client (with server) and clients. 

### Fixed

- The concurrent units access.

## [0.1.1-alpha.2] - 2020-08-23

### Fixed

- The fish eye effect during the walls render.

## [0.1.0-alpha.2] - 2020-08-15

### Added

- The dedicated server and client based on dockets for multiplayer matches in features.

## [0.1.0-alpha] - 2020-08-09

### Added
- Initial public pre-release of the plain pld retro shooter which should be regarded as an MVP:
    - This pre-release contain one scene with the general game engine opportunity example;
    - The player could moving, shooting and kill the enemies;
    - This pre-release consists of bad code for quick MVP results.