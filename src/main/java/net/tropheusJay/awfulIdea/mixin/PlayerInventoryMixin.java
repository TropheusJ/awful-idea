package net.tropheusJay.awfulIdea.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
	@Shadow
	@Final
	public DefaultedList<ItemStack> main;
	@Shadow
	@Final
	public DefaultedList<ItemStack> armor;
	@Shadow
	@Final
	@Mutable
	public DefaultedList<ItemStack> offHand = DefaultedList.ofSize(1, ItemStack.EMPTY);
	@Shadow
	@Final
	@Mutable
	private List<DefaultedList<ItemStack>> combinedInventory = ImmutableList.of(main, armor, offHand);
	
	@Inject(at = @At("RETURN"), method = "<init>")
	private void PlayerInventory(PlayerEntity player, CallbackInfo ci) {
		offHand = DefaultedList.ofSize(2, ItemStack.EMPTY);
		combinedInventory = ImmutableList.of(main, armor, offHand);
	}
}
