package com.fraye.autoattack.mixin.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.fraye.autoattack.Settings;

@Mixin(MinecraftClient.class)
public class ExampleClientMixin {
	private static int ticksSinceLastAttack = 0;

	@Inject(at = @At("HEAD"), method = "run")
	private void init(CallbackInfo info) {
		// This code is injected into the start of MinecraftClient.run()V
		
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			if (client.options.attackKey.isPressed()) {
				ticksSinceLastAttack++;
				if (ticksSinceLastAttack >= Settings.ticksPerAttack) {
					ticksSinceLastAttack = 0;
					client.player.swingHand(Hand.MAIN_HAND);
				}
			}
		});
	}
}