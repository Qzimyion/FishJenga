package com.qzimyion;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FishJenga implements ModInitializer {

	public static final String MOD_ID = "fishjenga";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Block SALMON_JENGA = new FishJengaBlock(FabricBlockSettings.create().strength(0.2f).sounds(BlockSoundGroup.SLIME));
	public static final Block COD_JENGA = new FishJengaBlock(FabricBlockSettings.copyOf(SALMON_JENGA));
	public static final Block TROPICAL_JENGA = new FishJengaBlock(FabricBlockSettings.copyOf(SALMON_JENGA));


	@Override
	public void onInitialize() {

		//Jenga blocks
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "salmon_jenga"), SALMON_JENGA);
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "cod_jenga"), COD_JENGA);
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "tropical_fish_jenga"), TROPICAL_JENGA);
	}
}