package me.GuitarXpress.NoTeleportDamage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	Main plugin;

	private List<UUID> teleportedPlayers = new ArrayList<UUID>();

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		System.out.println("NoTeleportDamage enabled.");
	}

	@Override
	public void onDisable() {
		System.out.println("NoTeleportDamage disabled.");
	}

	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		Player p = (Player) event.getPlayer();

		if (!teleportedPlayers.contains(p.getUniqueId()))
			teleportedPlayers.add(p.getUniqueId());
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player))
			return;

		if (!(event.getCause().equals(DamageCause.FALL)))
			return;

		Player player = (Player) event.getEntity();

		if (teleportedPlayers.contains(player.getUniqueId())) {
			teleportedPlayers.remove(player.getUniqueId());
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = (Player) event.getPlayer();

		if (!(teleportedPlayers.contains(player.getUniqueId())))
			return;

		if ((player.getLocation().clone().subtract(0, 1, 0).getBlock().getType().equals(Material.AIR)))
			return;

		teleportedPlayers.remove(player.getUniqueId());

	}

}
