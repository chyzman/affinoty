package io.wispforest.affinity.datagen;

import io.wispforest.affinity.Affinity;
import io.wispforest.affinity.object.AffinityBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

import static io.wispforest.affinity.object.AffinityItems.*;

public class AffinityRecipesProvider extends FabricRecipeProvider {

    public AffinityRecipesProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, Blocks.FLOWERING_AZALEA)
                .input(AZALEA_FLOWERS)
                .input(Blocks.AZALEA)
                .criterion(hasItem(AZALEA_FLOWERS), conditionsFromItem(AZALEA_FLOWERS))
                .offerTo(exporter, craftingRecipe(Blocks.FLOWERING_AZALEA));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, SATIATING_POTION)
                .input(Items.COOKED_BEEF)
                .input(Items.GLASS_BOTTLE)
                .criterion(hasItem(Items.COOKED_BEEF), conditionsFromItem(Items.COOKED_BEEF))
                .offerTo(exporter, craftingRecipe(SATIATING_POTION));

        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(UNFIRED_CLAY_CUP), RecipeCategory.MISC, CLAY_CUP, .1f, 200)
                .criterion(hasItem(UNFIRED_CLAY_CUP), conditionsFromItem(UNFIRED_CLAY_CUP))
                .offerTo(exporter);

        offerBoatRecipe(exporter, AZALEA_BOAT, AffinityBlocks.AZALEA_PLANKS);
        offerChestBoatRecipe(exporter, AZALEA_CHEST_BOAT, AZALEA_BOAT);
        offerPlanksRecipe(exporter, AffinityBlocks.AZALEA_PLANKS, TagKey.of(RegistryKeys.ITEM, Affinity.id("azalea_logs")), 4);
        generateFamily(exporter, AffinityBlockFamilies.AZALEA);
    }

    private static Identifier craftingRecipe(ItemConvertible item) {
        final var itemId = Registries.ITEM.getId(item.asItem());
        return new Identifier(Affinity.MOD_ID, "crafting/" + itemId.getPath());
    }
}
