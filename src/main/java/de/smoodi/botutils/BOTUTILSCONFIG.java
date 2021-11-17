package de.smoodi.botutils;

import java.util.Random;

public class BOTUTILSCONFIG {

    private static String botPrefix = "~";
    private static String[] randomTitleHelps = {"Uuuh, it's dangerous to go alone... Take this info.",
            "Did not expect to see you so soon..",
            "There's something wrong here... I can feel it.",
            "HELP, HELP, HELP - SOMEONE NEEDS ASSISTANCE!",
            ":thinking:",
            "42 ... Wait that's not what you were asking for?",
            "Help page"};

    public BOTUTILSCONFIG(String m_botPrefix) {
        botPrefix = m_botPrefix;
    }

    public BOTUTILSCONFIG(String m_botPrefix, String[] m_randomTitleHelps) {
        botPrefix = m_botPrefix;
        randomTitleHelps = m_randomTitleHelps;
    }

    public static String getBotPrefix() {
        return botPrefix;
    }

    public static String[] getAllRandomMessages() {
        return randomTitleHelps;
    }

    public static String getRandomMessage() {
        return randomTitleHelps[new Random().nextInt(randomTitleHelps.length)];
    }
}
