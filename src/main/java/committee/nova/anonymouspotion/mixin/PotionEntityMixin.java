package committee.nova.anonymouspotion.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.LingeringPotionItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotionEntity.class)
public abstract class PotionEntityMixin extends ThrownItemEntity implements FlyingItemEntity {
    public PotionEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyReturnValue(method = "isLingering", at = @At("RETURN"))
    private boolean correctIsLingering(boolean original) {
        return this.getStack().getItem() instanceof LingeringPotionItem;
    }
}
