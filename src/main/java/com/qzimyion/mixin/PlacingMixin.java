package com.qzimyion.mixin;

import com.qzimyion.FishJenga;
import net.minecraft.block.Blocks;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
@Debug(export = true)
public class PlacingMixin {


    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = {"stringValue=salmon"}, ordinal = 0)),
            at = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", ordinal = 0))
    private static Item SalmonRedirect(Item.Settings settings) {
        return new AliasedBlockItem(FishJenga.SALMON_JENGA, (new Item.Settings()).food(FoodComponents.SALMON));
    }

    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = {"stringValue=cod"}, ordinal = 0)),
            at = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", ordinal = 0))
    private static Item CodRedirect(Item.Settings settings) {
        return new AliasedBlockItem(FishJenga.COD_JENGA, (new Item.Settings()).food(FoodComponents.COD));
    }

    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = {"stringValue=tropical_fish"}, ordinal = 0)),
            at = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", ordinal = 0))
    private static Item TFishRedirect(Item.Settings settings) { //Trans rights >:3
        return new AliasedBlockItem(FishJenga.TROPICAL_JENGA, (new Item.Settings()).food(FoodComponents.TROPICAL_FISH));
    }
}
