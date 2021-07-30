package net.tropheusJay.awfulIdea;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class AwfulIdea implements ModInitializer {
	public static Hand THIRD_HAND;
	public static EquipmentSlot THIRD_HAND_EQUIPMENT_SLOT;
	public static final String ID = "awful-idea";
	public static final Identifier THIRD_HAND_SWAP_PACKET = new Identifier(ID, "third_hand_swap_packet");
	@Override
	public void onInitialize() {
		THIRD_HAND = ClassTinkerers.getEnum(Hand.class, "THIRD_HAND");
		THIRD_HAND_EQUIPMENT_SLOT = ClassTinkerers.getEnum(EquipmentSlot.class, "THIRD_HAND");
		System.out.println(THIRD_HAND_EQUIPMENT_SLOT.getName());
		ServerPlayNetworking.registerGlobalReceiver(THIRD_HAND_SWAP_PACKET, (server, player, handler, buf, responseSender) -> {
			if (!player.isSpectator()) {
				ItemStack itemStack = player.getStackInHand(THIRD_HAND);
				player.setStackInHand(THIRD_HAND, player.getStackInHand(Hand.MAIN_HAND));
				player.setStackInHand(Hand.MAIN_HAND, itemStack);
				player.clearActiveItem();
			}
		});
	}
}
