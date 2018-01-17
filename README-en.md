- [简体中文](README.md)
- **English**

<p align="center">
<img src="images/logo.png" alt="MoonLake" />
</p>

<p align="center">
<a href="https://travis-ci.org/McMoonLakeDev/MoonLake"><img src="https://travis-ci.org/McMoonLakeDev/MoonLake.svg?branch=v2.0-alpha-kotlin-travis" alt="Travis CI" /></a>
<a href="https://codebeat.co/projects/github-com-mcmoonlakedev-moonlake-v2-0-alpha-kotlin"><img src="https://codebeat.co/badges/71de9e97-982a-4630-a501-07e6c7c35d94" alt="Codebeat" /></a>
<a href="https://gitter.im/McMoonLakeDev/MoonLake"><img src="https://badges.gitter.im/McMoonLakeDev/MoonLake.svg" alt="Gitter" /></a>
<a href="https://github.com/McMoonLakeDev/MoonLake"><img src="https://badges.frapsoft.com/os/v1/open-source.svg?v=102" alt="OpenSource" /></a>
<a href="http://www.gnu.org/licenses/gpl-3.0"><img src="https://badges.frapsoft.com/os/gpl/gpl.svg?v=102" alt="GPLv3" /></a>
</p>

## Introduction

- This branch will be the content of the future `v2.0` version. For the old content, Please view the [Override](https://github.com/McMoonLakeDev/MoonLake/tree/override) branch.

## API

- [x] Reflection
- [x] Version
- [x] Region
- [x] Service
- [x] Event
- [x] Packet
- [ ] Command Annotation
- [x] Cached
- [x] Particle
- [x] NBT Component
- [x] Anvil Window
- [x] Chat Component
- [x] Depend Plugin
- [x] Item Builder
- [x] MoonLake Player

## Support

Version: `1.8.x` ~ `1.12.x`

- [x] [Bukkit](https://bukkit.org)
- [x] [Spigot](https://spigotmc.org)
- [x] PaperSpigot ([Mirror](https://yivesmirror.com/downloads/paperspigot))
- [x] [PaperClip](https://ci.destroystokyo.com/job/Paper/)

## Install

You can download a successful build file in the [GitHub Releases](https://github.com/McMoonLakeDev/MoonLake/releases).

#### Next

1. Shutdown the server
2. Place the `MoonLakePlugin.jar` file in the `plugins` folder
3. Run the server
4. In the `plugins/MoonLake/config.yml` configuration file custom function
5. Reload the server
6. Successfully installed

## Building

MoonLake uses `Maven` to manage project dependencies.

#### Requirements

- JDK 8
- Maven 3.3.x
- Git

> Build of Craftbukkit and Bukkit for local maven repository. See [BuildTools](https://www.spigotmc.org/wiki/buildtools/) Tools for more information.

Then run:

```sh
git clone https://github.com/McMoonLakeDev/MoonLake.git
cd MoonLake
mvn clean install
```

You can find the output file in the `target` directory of the corresponding module.

## Feedback

- For bugs, questions and discussions please use the [GitHub Issues](https://github.com/McMoonLakeDev/MoonLake/issues).

## License

    Copyright (C) 2016-Present The MoonLake (mcmoonlake@hotmail.com)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
