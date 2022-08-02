package committee.nova.anonymouspotion.item;

import committee.nova.anonymouspotion.AnonymousPotion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;

public class ItemInit {
    public static final Text UNKNOWN = Text.translatable("tooltip.anonymouspotion.unknown").formatted(Formatting.YELLOW);

    public static final TintedGlassBottleItem TINTED_GLASS_BOTTLE = new TintedGlassBottleItem(new Item.Settings().group(ItemGroup.BREWING));
    public static final TintedPotionItem TINTED_POTION = new TintedPotionItem(new Item.Settings().maxCount(1).group(ItemGroup.BREWING));
    public static final TintedSplashPotionItem TINTED_SPLASH_POTION = new TintedSplashPotionItem(new Item.Settings().maxCount(1).group(ItemGroup.BREWING));
    public static final TintedLingeringPotionItem TINTED_LINGERING_POTION = new TintedLingeringPotionItem(new Item.Settings().maxCount(1).group(ItemGroup.BREWING));
    public static final Item TINTED_DRAGON_BREATH = new SimpleTooltipItem(new Item.Settings().recipeRemainder(TINTED_GLASS_BOTTLE).group(ItemGroup.BREWING),
            UNKNOWN);

    public static void register() {
        Registry.register(Registry.ITEM, AnonymousPotion.id("tinted_glass_bottle"), TINTED_GLASS_BOTTLE);
        Registry.register(Registry.ITEM, AnonymousPotion.id("tinted_potion"), TINTED_POTION);
        Registry.register(Registry.ITEM, AnonymousPotion.id("tinted_splash_potion"), TINTED_SPLASH_POTION);
        Registry.register(Registry.ITEM, AnonymousPotion.id("tinted_lingering_potion"), TINTED_LINGERING_POTION);
        Registry.register(Registry.ITEM, AnonymousPotion.id("tinted_dragon_breath"), TINTED_DRAGON_BREATH);
    }
}
