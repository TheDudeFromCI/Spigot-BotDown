package net.whg.minigames.stonequarry;

import net.whg.match.Minigame;

public class StoneQuarry implements Minigame {

    @Override
    public String getName() {
        return "StoneQuarry";
    }

    @Override
    public int getPlayerCount() {
        return 2;
    }
}
