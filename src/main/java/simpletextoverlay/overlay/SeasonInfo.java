package simpletextoverlay.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;

import net.minecraft.resources.ResourceLocation;
import sereneseasons.api.season.Season;
import sereneseasons.config.BiomeConfig;
import sereneseasons.api.season.Season.SubSeason;
import sereneseasons.api.season.SeasonHelper;

import simpletextoverlay.SimpleTextOverlay;
import simpletextoverlay.util.Alignment;
import simpletextoverlay.util.ColorHelper;
import simpletextoverlay.util.FontHelper;

import java.util.Objects;


public class SeasonInfo extends Info {

    public SeasonInfo(String label, int lineNum) {
        super(label, lineNum);
    }

    @Override
    public void renderText(PoseStack matrix, Minecraft mc, BlockPos pos, int scaledWidth, int scaledHeight) {

//        ResourceLocation SPRING = new ResourceLocation(SimpleTextOverlay.MODID, "textures/seasons/spring.png");
//        ResourceLocation SUMMER = new ResourceLocation(SimpleTextOverlay.MODID, "textures/seasons/summer.png");
//        ResourceLocation AUTUMN = new ResourceLocation(SimpleTextOverlay.MODID, "textures/seasons/autumn.png");
//        ResourceLocation WINTER = new ResourceLocation(SimpleTextOverlay.MODID, "textures/seasons/winter.png");
//        ResourceLocation DRY = new ResourceLocation(SimpleTextOverlay.MODID, "textures/seasons/dry.png");
//        ResourceLocation WET = new ResourceLocation(SimpleTextOverlay.MODID, "textures/seasons/wet.png");


        if (BiomeConfig.enablesSeasonalEffects(Objects.requireNonNull(mc.level).getBiome(pos))) {
            Season season = SeasonHelper.getSeasonState(mc.level).getSeason();
            SubSeason subSeason = SeasonHelper.getSeasonState(mc.level).getSubSeason();
            ResourceLocation seasonIcon = new ResourceLocation(SimpleTextOverlay.MODID, "textures/seasons/" + season.toString().toLowerCase() + ".png");

            if (BiomeConfig.enablesSeasonalEffects(mc.level.getBiome(pos))) {
                TranslatableComponent seasonName = new TranslatableComponent("desc." + SimpleTextOverlay.MODID + "." + subSeason.name().toLowerCase());
                int x = Alignment.getX(scaledWidth, mc.font.width(super.label) + mc.font.width(seasonName));
                int y = Alignment.getY(scaledHeight, super.lineNum, mc.font.lineHeight);
                FontHelper.draw(mc, matrix, seasonName, x, y, ColorHelper.getSeasonColor(subSeason));

                // Draw season icons
                int iconX = x - 10;
                int iconY = y;
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, seasonIcon);
                RenderSystem.enableBlend();
                GuiComponent.blit(matrix, iconX, iconY, 0, 0, 9, 9, 9, 9);
            }


        }
    }

}
