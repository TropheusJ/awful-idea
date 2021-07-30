package net.tropheusJay.awfulIdea;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.entity.EquipmentSlot;
import org.objectweb.asm.Type;

public class HandExtender implements Runnable {
	@Override
	public void run() {
		ClassTinkerers.enumBuilder("net.minecraft.util.Hand").addEnum("THIRD_HAND").build();
		ClassTinkerers.enumBuilder("net.minecraft.entity.EquipmentSlot",
				Type.getType(EquipmentSlot.Type.class),
				int.class,
				int.class,
				String.class).addEnum("THIRD_HAND", () -> new Object[]{EquipmentSlot.Type.HAND, 2, 6, "thirdhand"}).build();
	}
}
