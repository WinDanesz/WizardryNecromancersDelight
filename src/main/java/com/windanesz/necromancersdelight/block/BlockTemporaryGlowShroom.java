package com.windanesz.necromancersdelight.block;

import com.Fishmod.mod_LavaCow.blocks.BlockGlowShroom;
import electroblob.wizardry.tileentity.TileEntityTimer;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@Mod.EventBusSubscriber
public class BlockTemporaryGlowShroom extends Block implements ITileEntityProvider {
	protected static final AxisAlignedBB MUSHROOM_AABB = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.4000000059604645D, 0.699999988079071D);

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return MUSHROOM_AABB;
	}

	public BlockTemporaryGlowShroom(){
		super(Material.PLANTS);
		this.blockSoundType = SoundType.PLANT;
		this.setLightLevel(1.0F);
		this.setTickRandomly(true);
	}

	// Replaces getRenderBlockPass
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer(){
		return BlockRenderLayer.TRANSLUCENT;
	}

	// See BlockTransportationStone for what all these do
	@Override public boolean isFullCube(IBlockState state){ return false; }
	@Override public boolean isBlockNormalCube(IBlockState state){ return false; }
	@Override public boolean isNormalCube(IBlockState state){ return false; }
	@Override public boolean isOpaqueCube(IBlockState state){ return false; }

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(16) == 0) {
			BlockGlowShroom.spawnParticles(worldIn, pos);
		}

		super.randomDisplayTick(stateIn, worldIn, pos, rand);
	}
	@Override
	public boolean hasTileEntity(IBlockState state){
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata){
		return new TileEntityTimer(1200);
	}

	@Override
	public int quantityDropped(Random par1Random){
		return 0;
	}

	@SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side){

		IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
		Block block = iblockstate.getBlock();

		return block == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}
}
