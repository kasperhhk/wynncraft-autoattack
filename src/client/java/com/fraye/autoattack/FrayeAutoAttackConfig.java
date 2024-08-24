package com.fraye.autoattack;

import blue.endless.jankson.Jankson;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.util.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class FrayeAutoAttackConfig extends ConfigWrapper<com.fraye.autoattack.ConfigModel> {

    public final Keys keys = new Keys();

    private final Option<java.lang.Integer> ticksPerAttackSetting = this.optionForKey(this.keys.ticksPerAttackSetting);

    private FrayeAutoAttackConfig() {
        super(com.fraye.autoattack.ConfigModel.class);
    }

    private FrayeAutoAttackConfig(Consumer<Jankson.Builder> janksonBuilder) {
        super(com.fraye.autoattack.ConfigModel.class, janksonBuilder);
    }

    public static FrayeAutoAttackConfig createAndLoad() {
        var wrapper = new FrayeAutoAttackConfig();
        wrapper.load();
        return wrapper;
    }

    public static FrayeAutoAttackConfig createAndLoad(Consumer<Jankson.Builder> janksonBuilder) {
        var wrapper = new FrayeAutoAttackConfig(janksonBuilder);
        wrapper.load();
        return wrapper;
    }

    public int ticksPerAttackSetting() {
        return ticksPerAttackSetting.value();
    }

    public void ticksPerAttackSetting(int value) {
        ticksPerAttackSetting.set(value);
    }


    public static class Keys {
        public final Option.Key ticksPerAttackSetting = new Option.Key("ticksPerAttackSetting");
    }
}

