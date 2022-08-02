package committee.nova.anonymouspotion.item;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class TintedGlassBottleItem extends GlassBottleItem {
    public TintedGlassBottleItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        List<AreaEffectCloudEntity> list = world.getEntitiesByClass(AreaEffectCloudEntity.class, user.getBoundingBox().expand(2.0D),
                (entity) -> entity != null && entity.isAlive() && entity.getOwner() instanceof EnderDragonEntity);
        ItemStack itemStack = user.getStackInHand(hand);
        if (!list.isEmpty()) {
            AreaEffectCloudEntity areaEffectCloudEntity = list.get(0);
            areaEffectCloudEntity.setRadius(areaEffectCloudEntity.getRadius() - 0.5F);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            world.emitGameEvent(user, GameEvent.FLUID_PICKUP, user.getPos());
            return TypedActionResult.success(this.fill(itemStack, user, new ItemStack(ItemInit.TINTED_DRAGON_BREATH)), world.isClient());
        } else {
            BlockHitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
            if (hitResult.getType() == HitResult.Type.MISS) {
                return TypedActionResult.pass(itemStack);
            } else {
                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockPos blockPos = hitResult.getBlockPos();
                    if (!world.canPlayerModifyAt(user, blockPos)) {
                        return TypedActionResult.pass(itemStack);
                    }
                    if (world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
                        world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                        world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
                        return TypedActionResult.success(this.fill(itemStack, user, PotionUtil.setPotion(new ItemStack(ItemInit.TINTED_POTION), Potions.WATER)), world.isClient());
                    }
                }
                return TypedActionResult.pass(itemStack);
            }
        }
    }
}
