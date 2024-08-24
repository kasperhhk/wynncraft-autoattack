package com.fraye.autoattack;

import static net.minecraft.server.command.CommandManager.*;

import com.mojang.brigadier.arguments.FloatArgumentType;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.text.Text;

public class FrayesWynncraftAutoattackerClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			var main = literal("frayeauto");
			var set = main.then(literal("set"))
				.then(argument("value", FloatArgumentType.floatArg()))
				.executes(context -> {
					final float value = FloatArgumentType.getFloat(context, "value");
					final int ticksPerAttack = value < 0.05 ? 1 : (int)((1f / value) * Settings.tickRate);
					Settings.ticksPerAttack = ticksPerAttack;
					context.getSource().sendFeedback(() -> Text.literal("Updated attacks per second to %s, ticks per attack %s".formatted(value, ticksPerAttack)), false);
					return 1;
				});
			var get = main.then(literal("get"))
				.executes(context -> {
					final int ticksPerAttack = Settings.ticksPerAttack;
					final float secondsPerAttack = (float)Settings.ticksPerAttack / (float)Settings.tickRate;
					final float aps = 1 / secondsPerAttack;
					context.getSource().sendFeedback(() -> Text.literal("Attacks per second is set to %s, ticks per attack %s".formatted(aps, ticksPerAttack)), false);
					return 1;
				});

			dispatcher.register(set);
			dispatcher.register(get);
		});
	}
}