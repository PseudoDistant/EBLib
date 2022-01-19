package net.blacklab.lib.minecraft.vector;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

@SuppressWarnings("ALL")
public class VectorUtil {

	/**
	 * do1:当たり判定のチェック
	 * do2:常時ブロク判定、透過判定も当たり判定も無視。
	 */
	public static boolean canBlockBeSeen(Entity entity,int pX, int pY, int pZ, boolean toTop, boolean do1, boolean do2) {
		// ブロックの可視判定
		World world = entity.getWorld();
		BlockPos pos = new BlockPos(pX, pY, pZ);
		BlockState state = world.getBlockState(pos);
		Block lblock = state.getBlock();
		if (lblock == null) {
			return false;
		}
//		lblock.setBlockBoundsBasedOnState(world, new BlockPos(pX, pY, pZ));
		Box boundingBox = state.getCollisionShape(world, pos).getBoundingBox();
		
		Vec3d vec3do = new Vec3d(entity.getX(), entity.getY() + entity.getEyeHeight(entity.getPose()), entity.getZ());
		Vec3d vec3dt = new Vec3d(pX + 0.5D, pY + ((boundingBox.maxY + boundingBox.minY) * (toTop ? 0.9D : 0.5D)), pZ + 0.5D);
		HitResult movingobjectposition = world.raycastBlock(vec3do, vec3dt, pos, state.getCollisionShape(world, pos), state);
		
		if (movingobjectposition != null && movingobjectposition.getType() == HitResult.Type.BLOCK) {
			// 接触ブロックが指定したものならば
			if (movingobjectposition.getPos().getX() == pX &&
					movingobjectposition.getPos().getY() == pY &&
					movingobjectposition.getPos().getZ() == pZ) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 基本的にcanBlockBeSeenに同じ。違いは足元基準で「通れるか」を判断するもの
	 */
	public static boolean canMoveThrough(Entity pEntity, double fixHeight, double pX, double pY, double pZ, boolean toTop, boolean do1, boolean do2){
		World world = pEntity.getWorld();
		BlockPos pos = new BlockPos(pX, pY, pZ);
		BlockState state = world.getBlockState(pos);
		Block lblock = pEntity.getWorld().getBlockState(new BlockPos(pX, pY, pZ)).getBlock();
		if (lblock == null) {
			return false;
		}
//		lblock.setBlockBoundsBasedOnState(world, new BlockPos(pX, pY, pZ));
		Box boundingBox = state.getCollisionShape(world, pos).getBoundingBox();
		
		Vec3d vec3do = new Vec3d(pEntity.getX(), pEntity.getY()+fixHeight, pEntity.getZ());
		Vec3d vec3dt = new Vec3d(pX, pY, pZ);
		HitResult movingobjectposition = pEntity.getWorld().raycastBlock(vec3do, vec3dt, new BlockPos(pX, pY, pZ), state.getCollisionShape(world, pos), state);
		
		if (movingobjectposition != null && movingobjectposition.getType() == HitResult.Type.BLOCK) {
			if (movingobjectposition.getPos().getX() == (int)pX &&
					movingobjectposition.getPos().getY() == (int)pY &&
					movingobjectposition.getPos().getZ() == (int)pZ) {
				return true;
			}
			return false;
		}
		return true;
	}

}
