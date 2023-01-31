package simpletextoverlay.overlay.botwmcs;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
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
        // String numberDays = String.format(Locale.ENGLISH, "%d", Math.max(Objects.requireNonNull(mc.level).getDayTime() / 24000, 1));
        Integer money = 0;
        String moneyHave = String.format(new TranslatableComponent("desc.simpletextoverlay.botwmcs.moneyHave").getString(), money);

        int x = Alignment.getX(scaledWidth, mc.font.width(super.label + moneyHave));
        int y = Alignment.getY(scaledHeight, super.lineNum, mc.font.lineHeight);

        FontHelper.draw(mc, matrix, super.label, x, y, OverlayConfig.labelColor().getRGB());

        x = x + mc.font.width(super.label);

        FontHelper.draw(mc, matrix, moneyHave, x, y, OverlayConfig.daysColor().getRGB());
    }

}
