package committee.nova.anonymouspotion.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TintedPotionItem extends PotionItem {
    public TintedPotionItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;
        if (playerEntity instanceof ServerPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);
        }
        if (!world.isClient) {
            List<StatusEffectInstance> list = PotionUtil.getPotionEffects(stack);
            for (StatusEffectInstance statusEffectInstance : list) {
                if (statusEffectInstance.getEffectType().isInstant()) {
                    statusEffectInstance.getEffectType().applyInstantEffect(playerEntity,
                            playerEntity,
                            user,
                            statusEffectInstance.getAmplifier(),
                            1.0D);
                } else {
                    user.addStatusEffect(new StatusEffectInstance(statusEffectInstance));
                }
            }
        }
        if (playerEntity != null) {
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!playerEntity.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        if (playerEntity == null || !playerEntity.getAbilities().creativeMode) {
            if (stack.isEmpty()) {
                return new ItemStack(ItemInit.TINTED_GLASS_BOTTLE);
            }
            if (playerEntity != null) {
                playerEntity.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        user.emitGameEvent(GameEvent.DRINK);
        return stack;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity playerEntity = context.getPlayer();
        ItemStack itemStack = context.getStack();
        BlockState blockState = world.getBlockState(blockPos);
        if (context.getSide() != Direction.DOWN
                && blockState.isIn(BlockTags.CONVERTABLE_TO_MUD)
                && PotionUtil.getPotion(itemStack) == Potions.WATER) {
            world.playSound(null,
                    blockPos,
                    SoundEvents.ENTITY_GENERIC_SPLASH,
                    SoundCategory.PLAYERS,
                    1.0F,
                    1.0F);
            playerEntity.setStackInHand(context.getHand(),
                    ItemUsage.exchangeStack(itemStack, playerEntity, new ItemStack(ItemInit.TINTED_GLASS_BOTTLE)));
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
            if (!world.isClient) {
                ServerWorld serverWorld = (ServerWorld) world;
                for(int i = 0; i < 5; ++i) {
                    serverWorld.spawnParticles(ParticleTypes.SPLASH,
                            (double)blockPos.getX() + world.random.nextDouble(),
                            blockPos.getY() + 1,
                            (double)blockPos.getZ() + world.random.nextDouble(),
                            1,
                            0.0D,
                            0.0D,
                            0.0D,
                            1.0D);
                }
            }
            world.playSound(null, blockPos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(null, GameEvent.FLUID_PLACE, blockPos);
            world.setBlockState(blockPos, Blocks.MUD.getDefaultState());
            return ActionResult.success(world.isClient);
        } else {
            return ActionResult.PASS;
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return "item.anonymouspotion.tinted_potion";
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
