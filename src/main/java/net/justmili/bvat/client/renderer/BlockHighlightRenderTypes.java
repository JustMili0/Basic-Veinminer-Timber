package net.justmili.bvat.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;

import java.util.OptionalDouble;

public class BlockHighlightRenderTypes {
    public static final RenderType DEFAULT_LINE;
    public static final RenderType THICK_LINE;
    public static final double THICK_LINE_WIDTH = 4.0;

    static {
        DEFAULT_LINE = RenderType.lines();
        THICK_LINE = RenderType.create("thick", DefaultVertexFormat.POSITION_COLOR_NORMAL, VertexFormat.Mode.LINES, 1536, false, false,
            RenderType.CompositeState.builder()
                .setShaderState(RenderType.RENDERTYPE_LINES_SHADER)
                .setLineState(new RenderType.LineStateShard(OptionalDouble.of(THICK_LINE_WIDTH)))
                .setLayeringState(RenderType.VIEW_OFFSET_Z_LAYERING)
                .setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY)
                .setOutputState(RenderType.ITEM_ENTITY_TARGET)
                .setWriteMaskState(RenderType.COLOR_DEPTH_WRITE)
                .setCullState(RenderType.NO_CULL)
                .createCompositeState(false)
        );
    }
}