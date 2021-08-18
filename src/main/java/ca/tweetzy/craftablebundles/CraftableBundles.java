package ca.tweetzy.craftablebundles;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * The current file has been created by Kiran Hart
 * Date Created: August 18 2021
 * Time Created: 3:18 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class CraftableBundles extends JavaPlugin  {

    private final HashMap<Character, Material> recipeItems = new HashMap<>();

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        getConfig().getStringList("recipe_items").forEach(line -> {
            String[] split = line.split(",");
            this.recipeItems.put(split[0].charAt(0), Material.valueOf(split[1].toUpperCase()));
        });

        NamespacedKey key = new NamespacedKey(this, "mc_bundle");
        ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.BUNDLE, getConfig().getInt("crafting_amount", 1)));

        recipe.shape(getConfig().getStringList("recipe").toArray(new String[0]));

        this.recipeItems.forEach(recipe::setIngredient);

        Bukkit.addRecipe(recipe);

        new Metrics(this, 12479);
    }
}
