function topleft() {
    return getString("pctl_artist") + " - " + getString("pctl_album") + " - " + getString("pctl_title") + "\n" +
        getString("pctl_status") + " " + getString("pctl_player") + " " + getString("pctl_position") + "/" + getString("pctl_duration") + " - " + getNumber("pctl_volume") + "%";
}