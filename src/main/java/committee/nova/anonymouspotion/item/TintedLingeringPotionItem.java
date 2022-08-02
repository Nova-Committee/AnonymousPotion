package committee.nova.anonymouspotion.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.LingeringPotionItem;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TintedLingeringPotionItem extends LingeringPotionItem {
    public TintedLingeringPotionItem(Settings settings) {
        super(settings);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return "item.anonymouspotion.tinted_lingering_potion";
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.hasEnchantments();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(ItemInit.UNKNOWN);
    }
}
