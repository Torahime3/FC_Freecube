package fr.torahime.freecube.controllers.loaders;

import fr.torahime.freecube.models.game.GamePlayer;
import fr.torahime.freecube.services.gameplayers.GamePlayerService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class GamePlayerLoader {

    public static void loadPlayerData(Player player){

        GamePlayerService gamePlayerService = new GamePlayerService();
        GamePlayer gp = gamePlayerService.get(player.getUniqueId().toString());

        try {

            if(gp == null){
                //Create gameplayer
                GamePlayer.addGamePlayer(player.getUniqueId());
                gamePlayerService.create(GamePlayer.getPlayer(player.getUniqueId()));
            } else {
                //Load gameplayer
                GamePlayer.addGamePlayer(player.getUniqueId(), gp);
                gp.initializeGamePlayer();
                PlotLoader.loadPlotFromGamePlayer(gp, player);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
