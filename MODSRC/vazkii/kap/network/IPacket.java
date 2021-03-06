/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Kings and Peasants Mod.
 *
 * Kings and Peasants is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [13 Jul 2013, 12:06:53 (GMT)]
 */
package vazkii.kap.network;

import java.io.Serializable;

import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IPacket extends Serializable {

	@SideOnly(Side.CLIENT)
	public void handle_client(INetworkManager manager, Player player);

	public void handle_server(INetworkManager manager, Player player);
}
