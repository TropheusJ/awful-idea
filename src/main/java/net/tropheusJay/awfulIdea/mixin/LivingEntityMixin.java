package net.tropheusJay.awfulIdea.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.tropheusJay.awfulIdea.AwfulIdea;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.tropheusJay.awfulIdea.AwfulIdea.THIRD_HAND;
import static net.tropheusJay.awfulIdea.AwfulIdea.THIRD_HAND_EQUIPMENT_SLOT;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Shadow
	@Final
	@Mutable
	private DefaultedList<ItemStack> equippedHand = DefaultedList.ofSize(2, ItemStack.EMPTY);
	
	@Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);
	
	@Shadow public abstract void equipStack(EquipmentSlot slot, ItemStack stack);
	
	@Inject(at = @At("RETURN"), method = "<init>")
	private void LivingEntity(EntityType<? extends LivingEntity> entityType, World world, CallbackInfo ci) {
		equippedHand = DefaultedList.ofSize(3, ItemStack.EMPTY);
	}
	
	@Inject(at = @At("HEAD"), method = "getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;", cancellable = true)
	private void getStackInHand(Hand hand, CallbackInfoReturnable<ItemStack> cir) {
		if (hand == THIRD_HAND) {
			cir.setReturnValue(getEquippedStack(THIRD_HAND_EQUIPMENT_SLOT));
		}
	}
	
	@Inject(at = @At("HEAD"), method = "setStackInHand(Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;)V", cancellable = true)
	private void setStackInHand(Hand hand, ItemStack stack, CallbackInfo ci) {
		if (hand == THIRD_HAND) {
			equipStack(THIRD_HAND_EQUIPMENT_SLOT, stack);
			ci.cancel();
		}
	}
}
