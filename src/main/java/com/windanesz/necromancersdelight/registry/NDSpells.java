package com.windanesz.necromancersdelight.registry;

import com.Fishmod.mod_LavaCow.entities.EntityBanshee;
import com.Fishmod.mod_LavaCow.entities.EntityZombieMushroom;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityGhostBomb;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityHolyGrenade;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntitySonicBomb;
import com.Fishmod.mod_LavaCow.entities.tameable.EntitySalamander;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.windanesz.necromancersdelight.NecromancersDelight;
import com.windanesz.necromancersdelight.entity.living.EntityVespaMinion;
import com.windanesz.necromancersdelight.entity.projectile.EntityFrostBomb;
import com.windanesz.necromancersdelight.entity.projectile.EntityStinkBomb;
import com.windanesz.necromancersdelight.handler.NDEventHandler;
import com.windanesz.necromancersdelight.spell.Corrode;
import com.windanesz.necromancersdelight.spell.GlowShroom;
import com.windanesz.necromancersdelight.spell.SummonElementalLeech;
import com.windanesz.necromancersdelight.spell.SummonManaLeech;
import com.windanesz.necromancersdelight.spell.TrialOfMushrooms;
import com.windanesz.wizardryutils.spell.SpellDynamicConjuration;
import com.windanesz.wizardryutils.spell.SpellDynamicMinion;
import electroblob.wizardry.data.WizardData;
import electroblob.wizardry.item.ItemArtefact;
import electroblob.wizardry.item.SpellActions;
import electroblob.wizardry.registry.WizardryEnchantments;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.spell.SpellMinion;
import electroblob.wizardry.spell.SpellProjectile;
import electroblob.wizardry.spell.SpellThrowable;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@ObjectHolder(NecromancersDelight.MODID)
@EventBusSubscriber
public final class NDSpells {

	public static final Spell bound_frozen_dagger = placeholder();
	public static final Spell bound_bone_sword = placeholder();
	public static final Spell holy_hand_grenade = placeholder();
	public static final Spell ghost_bomb = placeholder();
	public static final Spell wail_of_the_banshee = placeholder();
	public static final Spell summon_vespa = placeholder();
	public static final Spell conjure_banshee = placeholder();
	public static final Spell conjure_mycosis = placeholder();
	public static final Spell summon_salamander = placeholder();
	public static final Spell glowshroom = placeholder();
	public static final Spell corrode = placeholder();
	public static final Spell trial_of_mushrooms = placeholder();
	public static final Spell forsaken_shield = placeholder();
    public static final Spell bound_spectral_dagger = placeholder();
    public static final Spell stink_bomb = placeholder();
    public static final Spell frost_bomb = placeholder();
    public static final Spell summon_mana_leech = placeholder();
    public static final Spell summon_elemental_leech = placeholder();

	private NDSpells() {} // no instances

	@Nonnull
	@SuppressWarnings("ConstantConditions")
	private static <T> T placeholder() { return null; }

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Spell> event) {

		IForgeRegistry<Spell> registry = event.getRegistry();

		registry.register(new SpellDynamicConjuration(NecromancersDelight.MODID, "bound_frozen_dagger", FishItems.FROZEN_DAGGER) {
			@Override
			protected ItemStack addItemExtras(EntityPlayer caster, ItemStack stack, SpellModifiers modifiers) {
				if (caster != null && ItemArtefact.isArtefactActive(caster, NDItems.ring_frozen_dagger)) {
					stack.addEnchantment(WizardryEnchantments.freezing_weapon, 1);

					WizardData.get(caster).setImbuementDuration(WizardryEnchantments.freezing_weapon, (getProperty(ITEM_LIFETIME).intValue()));
				}
				return stack;
			}
		});
		registry.register(new SpellDynamicConjuration(NecromancersDelight.MODID, "bound_bone_sword", FishItems.BONESWORD));
		registry.register(new SpellThrowable<>(NecromancersDelight.MODID, "holy_hand_grenade", EntityHolyGrenade::new).npcSelector((e, o) -> o).soundValues(0.5f, 0.4f, 0.2f));
		registry.register(new SpellThrowable<>(NecromancersDelight.MODID, "ghost_bomb", EntityGhostBomb::new).npcSelector((e, o) -> o).soundValues(0.5f, 0.4f, 0.2f));
		registry.register(new SpellThrowable<>(NecromancersDelight.MODID, "wail_of_the_banshee", EntitySonicBomb::new).npcSelector((e, o) -> o).soundValues(0.5f, 0.4f, 0.2f));
		registry.register(new SpellMinion<>(NecromancersDelight.MODID, "summon_vespa", EntityVespaMinion::new));
		registry.register(new SpellDynamicMinion<>(NecromancersDelight.MODID, "conjure_banshee", EntityBanshee::new));
		registry.register(new SpellDynamicMinion<>(NecromancersDelight.MODID, "conjure_mycosis", EntityZombieMushroom::new));
		registry.register(new SpellDynamicMinion<EntitySalamander>(NecromancersDelight.MODID, "summon_salamander", EntitySalamander::new) {
			@Override
			protected void addMinionExtras(EntitySalamander minion, BlockPos pos,
					@Nullable EntityLivingBase caster, SpellModifiers modifiers, int alreadySpawned) {
				if (caster instanceof EntityPlayer) { minion.setTamedBy((EntityPlayer) caster); } else if (caster != null) {
					minion.setTamed(true);
					minion.setOwnerId(caster.getUniqueID());
				}
			}
		});
		registry.register(new GlowShroom(NecromancersDelight.MODID, "glowshroom"));
		registry.register(new Corrode(NecromancersDelight.MODID, "corrode", SpellActions.POINT, false));
		registry.register(new TrialOfMushrooms(NecromancersDelight.MODID, "trial_of_mushrooms", SpellActions.IMBUE, false));
		registry.register(new SpellDynamicConjuration(NecromancersDelight.MODID, "bound_spectral_dagger", FishItems.SPECTRAL_DAGGER));
		registry.register(new SpellDynamicConjuration(NecromancersDelight.MODID, "forsaken_shield", FishItems.BONESWORD) {
			@Override
			protected ItemStack addItemExtras(EntityPlayer caster, ItemStack shield, SpellModifiers modifiers) { return NDEventHandler.getForsakenShield(); }
		});
		registry.register(new SpellProjectile<>(NecromancersDelight.MODID, "stink_bomb", EntityStinkBomb::new).addProperties(Spell.DIRECT_DAMAGE, Spell.EFFECT_RADIUS, Spell.DIRECT_EFFECT_DURATION, Spell.DIRECT_EFFECT_STRENGTH, Spell.SPLASH_DAMAGE, Spell.SPLASH_EFFECT_DURATION, Spell.SPLASH_EFFECT_STRENGTH).soundValues(0.5f, 0.4f, 0.2f));
		registry.register(new SpellProjectile<>(NecromancersDelight.MODID, "frost_bomb", EntityFrostBomb::new).addProperties(Spell.DIRECT_DAMAGE, Spell.EFFECT_RADIUS, Spell.DIRECT_EFFECT_DURATION, Spell.DIRECT_EFFECT_STRENGTH, Spell.SPLASH_DAMAGE, Spell.SPLASH_EFFECT_DURATION, Spell.SPLASH_EFFECT_STRENGTH).soundValues(0.5f, 0.4f, 0.2f));
        registry.register(new SummonManaLeech());
        registry.register(new SummonElementalLeech());
	}
}
