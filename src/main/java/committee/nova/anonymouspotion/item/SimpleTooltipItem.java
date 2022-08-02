package committee.nova.anonymouspotion.item;

import com.google.common.collect.Lists;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SimpleTooltipItem extends Item {
    public final List<Text> tooltipList;

    public SimpleTooltipItem(Settings settings, List<Text> tooltipList) {
        super(settings);
        this.tooltipList = tooltipList;
    }

    public SimpleTooltipItem(Settings settings, Text... tooltips) {
        super(settings);
        this.tooltipList = Lists.newArrayList(tooltips);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.addAll(tooltipList);
    }
}
