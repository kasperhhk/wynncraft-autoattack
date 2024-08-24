package com.fraye.autoattack;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

import com.mojang.brigadier.arguments.FloatArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;

public class FrayesWynncraftAutoattackerClient implements ClientModInitializer {
	public static final FrayeAutoAttackConfig CONFIG = FrayeAutoAttackConfig.createAndLoad();

	@Override
	public void onInitializeClient() {
		Settings.ticksPerAttack = CONFIG.ticksPerAttackSetting();

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(literal("frayeauto")
				.then(literal("set")
					.then(argument("value", FloatArgumentType.floatArg())
						.executes(context -> {
							final float value = FloatArgumentType.getFloat(context, "value");
							final int ticksPerAttack = value < 0.05 ? 1 : (int)((1f / value) * Settings.tickRate);
							Settings.ticksPerAttack = ticksPerAttack;
							CONFIG.ticksPerAttackSetting(Settings.ticksPerAttack);
							context.getSource().sendFeedback(Text.literal("Updated attacks per second to %s, ticks per attack %s".formatted(value, ticksPerAttack)));
							return 1;
						})
					)
				)
				.then(literal("get")
					.executes(context -> {
						final int ticksPerAttack = Settings.ticksPerAttack;
						final float secondsPerAttack = (float)Settings.ticksPerAttack / (float)Settings.tickRate;
						final float aps = 1 / secondsPerAttack;
						context.getSource().sendFeedback(Text.literal("Attacks per second is set to %s, ticks per attack %s".formatted(aps, ticksPerAttack)));
						return 0;
					})
				)
			);
		});
	}
}