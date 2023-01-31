package simpletextoverlay.overlay.botwmcs;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Decoder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import simpletextoverlay.SimpleTextOverlay;
import simpletextoverlay.config.OverlayConfig;
import simpletextoverlay.overlay.Info;
import simpletextoverlay.util.Alignment;
import simpletextoverlay.util.FontHelper;


public class MoneyInfo extends Info {
    public MoneyInfo(String label, int lineNum) {
        super(label, lineNum);
    }

    @Override
    public void renderText(PoseStack matrix, Minecraft mc, BlockPos pos, int scaledWidth, int scaledHeight) {
        // Text draw
        double money = 0.0;
        String moneyHave = String.format(new TranslatableComponent("desc.simpletextoverlay.botwmcs.moneyHave").getString(), money);

        int x = Alignment.getX(scaledWidth, mc.font.width(super.label + moneyHave));
        int y = Alignment.getY(scaledHeight, super.lineNum, mc.font.lineHeight);

//        FontHelper.draw(mc, matrix, super.label, x, y, OverlayConfig.labelColor().getRGB());

        x = x + mc.font.width(super.label);

        FontHelper.draw(mc, matrix, moneyHave, x, y, OverlayConfig.moneyColor().getRGB());

        // Image draw
        ResourceLocation MONEY_ICON;
        int iconX = x - 10;
        int iconY = y;
        MONEY_ICON = new ResourceLocation("simpletextoverlay", "textures/botwmcs/gold_coin.png");
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, MONEY_ICON);
        RenderSystem.enableBlend();
        GuiComponent.blit(matrix, iconX, iconY, 0, 0, 9, 9, 9, 9);



    }

}
