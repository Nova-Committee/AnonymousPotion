package committee.nova.anonymouspotion.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.screen.BrewingStandScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BrewingStandScreenHandler.PotionSlot.class)
public class PotionSlotMixin {
    @ModifyReturnValue(method = "matches", at = @At("RETURN"))
    private static boolean correctMatches(boolean original, ItemStack stack) {
        return stack.getItem() instanceof PotionItem || stack.getItem() instanceof GlassBottleItem;
    }
}
