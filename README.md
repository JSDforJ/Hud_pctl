# SpotiHUD Playerctl
Playerctl variables for Hudder: No setup required! Works with Spotify, YT, browsers, and more. Only works on Linux (for now).

### Requirements
- [Hudder](https://modrinth.com/mod/hudder)
- [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin)
- [Playerctl](https://pkgs.org/search/?q=playerctl)

### Variables
- ``pctl_player``, string, eg. spotify, browser, youtube, etc.
- ``pctl_title``, string
- ``pctl_artist``, string
- ``pctl_album``, string
- ``pctl_status``, string, either "Playing", "Paused", or "Stopped"
- ``pctl_position``, string, formatted as 0:00
- ``pctl_duration``, string, formatted as 0:00
- ``pctl_volume``, number, formatted as between ``0`` and ``100``
- ``pctl_volume_raw``, number, formatted as between ``0`` and ``1``
- ``pctl_position_raw``, number, in seconds
- ``pctl_duration_raw``, number, in seconds
##### All strings return N/A on error
##### All numbers return -1 on error
