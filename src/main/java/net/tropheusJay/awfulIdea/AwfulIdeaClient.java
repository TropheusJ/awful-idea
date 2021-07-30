package net.tropheusJay.awfulIdea;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import static net.tropheusJay.awfulIdea.AwfulIdea.THIRD_HAND_SWAP_PACKET;

public class AwfulIdeaClient implements ClientModInitializer {
	private static KeyBinding keyBinding;
	
	@Override
	public void onInitializeClient() {
//		KeyBinding h = MinecraftClient.getInstance().options.keySwapHands;
		keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.awful-idea.swapThirdHand", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "key.categories.inventory"));
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (keyBinding.wasPressed()) {
				if (!client.player.isSpectator()) {
					ClientPlayNetworking.send(THIRD_HAND_SWAP_PACKET, PacketByteBufs.create());
				}
			}
		});
	}
}
