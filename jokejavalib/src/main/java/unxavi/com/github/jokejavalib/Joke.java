package unxavi.com.github.jokejavalib;

import java.util.Random;

public class Joke {

    private static String jokes[] = {
            "Knock, knock. Who’s there? very long pause… Java.",
            "A programmer puts two glasses on his bedside table before going to sleep. A full one, in case he gets thirsty, and an empty one, in case he doesn't.",
            "There's no place like 127.0.0.1",
            "A son asked his father(a programmer) why the sun rises in the east, and sets in the west. His response? It works, don't touch!",
            " How many programmers does it take to change a light bulb?..... None, it is a hardware problem.",

    };

    public static String getRandomJoke(){
        Random random = new Random();
        int randomJokePosition = random.nextInt(jokes.length);
        return jokes[randomJokePosition];
    }
}
