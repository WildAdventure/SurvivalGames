/*
 * Copyright (c) 2020, Wild Adventure
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 * 4. Redistribution of this software in source or binary forms shall be free
 *    of all charges or fees to the recipient of this software.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.gmail.filoghost.survivalgames.listener;

import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

import com.gmail.filoghost.survivalgames.SurvivalGames;
import com.gmail.filoghost.survivalgames.generation.ChestFiller;
import com.gmail.filoghost.survivalgames.player.Status;

public class ChestListener implements Listener {

	@EventHandler (ignoreCancelled = true)
	public void onOpen(InventoryOpenEvent event) {
		
		InventoryHolder holder = event.getInventory().getHolder();
		
		if (holder instanceof Chest) {
			Chest chest = (Chest) event.getInventory().getHolder();
			ChestFiller.fillChestIfNeeded(chest, false);
			
		} else if (holder instanceof DoubleChest) {
			DoubleChest doubleChest = (DoubleChest) event.getInventory().getHolder();
			ChestFiller.fillChestIfNeeded((Chest) doubleChest.getLeftSide(), false);
			ChestFiller.fillChestIfNeeded((Chest) doubleChest.getRightSide(), false);
		}
		
		switch (event.getInventory().getType()) {
			case ANVIL:
			case BEACON:
			case DISPENSER:
				if (SurvivalGames.getHGamer((Player) event.getPlayer()).getStatus() == Status.TRIBUTE) {
					event.setCancelled(true);
				}
				break;
				
			default:
				// Autorizza
				break;
		}
	}

}
