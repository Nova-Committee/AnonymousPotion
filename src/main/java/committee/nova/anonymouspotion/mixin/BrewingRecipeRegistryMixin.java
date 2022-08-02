package committee.nova.anonymouspotion.mixin;

import committee.nova.anonymouspotion.item.ItemInit;
import net.minecraft.item.Items;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingRecipeRegistry.class)
public abstract class BrewingRecipeRegistryMixin {
    @Inject(method = "registerDefaults", at = @At("RETURN"))
    private static void onRegisterDefaults(CallbackInfo ci) {
        BrewingRecipeRegistryAccessor.invokeRegisterPotionType(ItemInit.TINTED_POTION);
        BrewingRecipeRegistryAccessor.invokeRegisterPotionType(ItemInit.TINTED_SPLASH_POTION);
        BrewingRecipeRegistryAccessor.invokeRegisterPotionType(ItemInit.TINTED_LINGERING_POTION);
        BrewingRecipeRegistryAccessor.invokeRegisterItemRecipe(ItemInit.TINTED_POTION, Items.GUNPOWDER, ItemInit.TINTED_SPLASH_POTION);
        BrewingRecipeRegistryAccessor.invokeRegisterItemRecipe(Items.SPLASH_POTION, ItemInit.TINTED_DRAGON_BREATH, Items.LINGERING_POTION);
        BrewingRecipeRegistryAccessor.invokeRegisterItemRecipe(ItemInit.TINTED_SPLASH_POTION, Items.DRAGON_BREATH, ItemInit.TINTED_LINGERING_POTION);
        BrewingRecipeRegistryAccessor.invokeRegisterItemRecipe(ItemInit.TINTED_SPLASH_POTION, ItemInit.TINTED_DRAGON_BREATH, ItemInit.TINTED_LINGERING_POTION);
    }
}
