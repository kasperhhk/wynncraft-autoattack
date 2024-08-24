package com.fraye.autoattack.mixin.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Hand;

import java.time.Instant;
import java.util.Date;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class ExampleClientMixin {
	@Inject(at = @At("HEAD"), method = "run")
	private void init(CallbackInfo info) {
		// This code is injected into the start of MinecraftClient.run()V
		float aps = 10;
		Instant lastAttack = Instant.now();
		
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			if (client.options.attackKey.isPressed()) {
				client.player.swingHand(Hand.MAIN_HAND);
			}
		});
	}
}