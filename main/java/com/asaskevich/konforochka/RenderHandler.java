package com.asaskevich.konforochka;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RenderHandler {
	private Minecraft mc;
	private int width;
	private int height;
	private Field curBlockDamage;
	private boolean isRendering;
	private long lastChanging;
	private ResourceLocation iconSheet = new ResourceLocation("minecraft:textures/gui/icons.png");
	private Entity pointedEntity;

	public RenderHandler(Minecraft mc, boolean render) {
		this.mc = mc;
		this.width = mc.displayWidth;
		this.height = mc.displayHeight;
		this.curBlockDamage = null;
		this.isRendering = render;
		try {
			for (Field field : PlayerControllerMP.class.getDeclaredFields())
				if (field.getName().equals("field_78770_f") || field.getName().equals("curBlockDamageMP")) {
					this.curBlockDamage = PlayerControllerMP.class.getDeclaredField(field.getName());
					curBlockDamage.setAccessible(true);
				}
		} catch (Exception exception) {
			System.err.println(exception);
		}
	}

	@SubscribeEvent
	public void renderGameOverlay(RenderGameOverlayEvent event) {
		try {
			if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
				MovingObjectPosition mop = mc.renderViewEntity.rayTrace(200, 2.0F);
				this.getEntityLookingAt(2.0F);
				if (mop != null && isRendering) {
					Block blockLookingAt = mc.theWorld.getBlock(mop.blockX, mop.blockY, mop.blockZ);
					// /////////////
					if (curBlockDamage != null) {
						float damage = curBlockDamage.getFloat(Minecraft.getMinecraft().playerController);
						if (!Block.isEqualTo(blockLookingAt, Blocks.air) && damage > 0) {
							ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
							FontRenderer fontRender = mc.fontRenderer;
							width = res.getScaledWidth();
							height = res.getScaledHeight();
							mc.entityRenderer.setupOverlayRendering();
							String text = String.format("%.0f%%", damage * 100);
							int x = width / 2 + 2;
							int y = height / 2 + 2;
							int color = 0xFFFFFF;
							fontRender.drawStringWithShadow(text, x, y, color);
						}
					}
				}
				if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null && isRendering) {
					Entity target = mc.objectMouseOver.entityHit;
					if (target instanceof EntityLiving) {
						EntityLiving entity = (EntityLiving) target;
						ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
						FontRenderer fontRender = mc.fontRenderer;
						width = res.getScaledWidth();
						height = res.getScaledHeight();
						mc.entityRenderer.setupOverlayRendering();
						int color = 0xFFFFFF;
						int x = width / 2 + 2;
						int y = height / 2 - 2 - fontRender.FONT_HEIGHT;
						String text = (int) entity.getHealth() + "x";
						fontRender.drawStringWithShadow(text, x, y, color);
						mc.getTextureManager().bindTexture(iconSheet);
						GL11.glPushMatrix();
						GL11.glEnable(GL11.GL_BLEND);
						mc.getTextureManager().bindTexture(iconSheet);
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						GL11.glScalef(fontRender.FONT_HEIGHT / 8, fontRender.FONT_HEIGHT / 8, fontRender.FONT_HEIGHT / 8);
						mc.ingameGUI.drawTexturedModalRect(x + 2 + fontRender.getStringWidth(text), y, 52, 0, 8, 8);
						GL11.glDisable(GL11.GL_BLEND);
						GL11.glPopMatrix();
					}
					if (target instanceof EntityItem) {
						EntityItem item = (EntityItem) target;
						ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
						FontRenderer fontRender = mc.fontRenderer;
						width = res.getScaledWidth();
						height = res.getScaledHeight();
						mc.entityRenderer.setupOverlayRendering();
						int color = 0xFFFFFF;
						String text = (int) item.getEntityItem().stackSize + "x " + item.getEntityItem().getDisplayName();
						int x = width / 2 - 2 - fontRender.getStringWidth(text);
						int y = height / 2 - 2 - fontRender.FONT_HEIGHT;
						fontRender.drawStringWithShadow(text, x, y, color);
					}
				}
				if (System.currentTimeMillis() - lastChanging <= 2000) {
					ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
					FontRenderer fontRender = mc.fontRenderer;
					width = res.getScaledWidth();
					height = res.getScaledHeight();
					mc.entityRenderer.setupOverlayRendering();
					int x = 4;
					int y = 4;
					int color = 0xFFFFFF;
					fontRender.drawStringWithShadow("Smart Cursor: " + (isRendering ? "on" : "off"), x, y, color);
				}
			}
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	public void setRender(boolean flag) {
		this.isRendering = flag;
	}

	public void invertRender() {
		this.lastChanging = System.currentTimeMillis();
		this.isRendering = !this.isRendering;
	}

	public boolean getRender() {
		return this.isRendering;
	}

	public void getEntityLookingAt(float par1) {
		if (this.mc.renderViewEntity != null) {
			if (this.mc.theWorld != null) {
				this.mc.pointedEntity = null;
				double d0 = (double) this.mc.playerController.getBlockReachDistance();
				this.mc.objectMouseOver = this.mc.renderViewEntity.rayTrace(d0, par1);
				double d1 = d0;
				Vec3 vec3 = this.mc.renderViewEntity.getPosition(par1);

				if (this.mc.playerController.extendedReach()) {
					d0 = 6.0D;
					d1 = 6.0D;
				} else {
					if (d0 > 3.0D) {
						d1 = 3.0D;
					}

					d0 = d1;
				}

				if (this.mc.objectMouseOver != null) {
					d1 = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
				}

				Vec3 vec31 = this.mc.renderViewEntity.getLook(par1);
				Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
				this.pointedEntity = null;
				Vec3 vec33 = null;
				float f1 = 1.0F;
				List list = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(
						this.mc.renderViewEntity,
						this.mc.renderViewEntity.boundingBox.addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand(
								(double) f1, (double) f1, (double) f1));
				double d2 = d1;

				for (int i = 0; i < list.size(); ++i) {
					Entity entity = (Entity) list.get(i);

					float f2 = entity.getCollisionBorderSize();
					AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double) f2, (double) f2, (double) f2);
					MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

					if (axisalignedbb.isVecInside(vec3)) {
						if (0.0D < d2 || d2 == 0.0D) {
							this.pointedEntity = entity;
							vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
							d2 = 0.0D;
						}
					} else if (movingobjectposition != null) {
						double d3 = vec3.distanceTo(movingobjectposition.hitVec);

						if (d3 < d2 || d2 == 0.0D) {
							if (entity == this.mc.renderViewEntity.ridingEntity && !entity.canRiderInteract()) {
								if (d2 == 0.0D) {
									this.pointedEntity = entity;
									vec33 = movingobjectposition.hitVec;
								}
							} else {
								this.pointedEntity = entity;
								vec33 = movingobjectposition.hitVec;
								d2 = d3;
							}
						}
					}

				}

				if (this.pointedEntity != null && (d2 < d1 || this.mc.objectMouseOver == null)) {
					this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, vec33);
					this.mc.pointedEntity = this.pointedEntity;
				}
			}
		}

	}
}
