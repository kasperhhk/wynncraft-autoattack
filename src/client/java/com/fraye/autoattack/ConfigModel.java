package com.fraye.autoattack;

import io.wispforest.owo.config.annotation.Config;

@Config(name = "frayeautoattack-config", wrapperName = "FrayeAutoAttackConfig")
public class ConfigModel {
    public int ticksPerAttackSetting = 2;
}