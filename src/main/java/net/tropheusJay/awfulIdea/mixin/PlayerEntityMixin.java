package net.tropheusJay.awfulIdea.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tropheusJay.awfulIdea.AwfulIdea;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	@Shadow
	@Final
	private PlayerInventory inventory;
	
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Inject(at = @At("TAIL"), method = "equipStack(Lnet/minecraft/entity/EquipmentSlot;Lnet/minecraft/item/ItemStack;)V")
	private void equipStack(EquipmentSlot slot, ItemStack stack, CallbackInfo ci) {
		if (slot == AwfulIdea.THIRD_HAND_EQUIPMENT_SLOT) {
			onEquipStack(stack);
			inventory.offHand.set(1, stack);
		}
	}
	
	@Inject(at = @At("HEAD"), method = "getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;", cancellable = true)
	private void getEquippedStack(EquipmentSlot slot, CallbackInfoReturnable<ItemStack> cir) {
		if (slot == AwfulIdea.THIRD_HAND_EQUIPMENT_SLOT) {
			cir.setReturnValue(inventory.offHand.get(1));
		}
	}
}
