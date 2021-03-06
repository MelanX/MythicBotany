package mythicbotany.data.custom;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import mythicbotany.MythicBotany;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

public class FloatingFlowerModelBuilder extends BlockModelBuilder {

    private ResourceLocation flower = null;

    public FloatingFlowerModelBuilder(ResourceLocation outputLocation, ExistingFileHelper existingFileHelper) {
        super(outputLocation, existingFileHelper);
    }

    public static FloatingFlowerModelBuilder create(BlockModelProvider provider, String path) {
        Preconditions.checkNotNull(path, "Path must not be null");
        ResourceLocation outputLoc = new ResourceLocation(MythicBotany.MODID, "block/" + path);
        return (FloatingFlowerModelBuilder) provider.generatedModels.computeIfAbsent(outputLoc, rl -> new FloatingFlowerModelBuilder(rl, provider.existingFileHelper));
    }

    public FloatingFlowerModelBuilder flower(ResourceLocation flower) {
        if (flower.getPath().startsWith("block/")) {
            this.flower = flower;
        } else {
            this.flower = new ResourceLocation(flower.getNamespace(), "block/" + flower.getPath());
        }
        return this;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = super.toJson();
        json.addProperty("loader", "botania:floating_flower");
        if (flower != null) {
            JsonObject flowerObj = new JsonObject();
            flowerObj.addProperty("parent", flower.toString());
            json.add("flower", flowerObj);
        }
        return json;
    }
}
