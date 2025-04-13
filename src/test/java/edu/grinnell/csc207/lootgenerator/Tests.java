package edu.grinnell.csc207.lootgenerator;

import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Tests {
    
    private static final String DATA_SET = "data/large";
    
    @Test
    public void pickMonsterTest() throws IOException {
        String file = DATA_SET + "/monstats.txt";
        LootGenerator.Monster monster = LootGenerator.pickMonster();
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line = buffer.readLine();
        while (!line.startsWith(monster.name)) {
            line = buffer.readLine();
        }
        String[] arr = line.split("\t");
        assertEquals(monster.name, arr[0]);
    }
    
    @Test
    public void fetchTreasureClassTest() throws IOException {
        String file = DATA_SET + "/TreasureClassEx.txt";
        LootGenerator.Monster monster = LootGenerator.pickMonster();
        LootGenerator.TreasureClass treasure = LootGenerator.fetchTreasureClass(monster);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line = buffer.readLine();
        while (!line.startsWith(treasure.treasure)) {
            line = buffer.readLine();
        }
        String[] arr = line.split("\t");
        assertEquals(treasure.treasure, arr[0]);
    }
    
    @Test
    public void generateBaseItemTest() throws IOException {
        String file = DATA_SET + "/armor.txt";
        LootGenerator.Monster monster = LootGenerator.pickMonster();
        LootGenerator.TreasureClass treasure = LootGenerator.fetchTreasureClass(monster);
        LootGenerator.BaseItem item = LootGenerator.generateBaseItem(treasure);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line = buffer.readLine();
        while (!line.startsWith(item.item)) {
            line = buffer.readLine();
        }
        String[] arr = line.split("\t");
        assertEquals(item.item, arr[0]);
    }
    
    @Test
    public void generateBaseStatsTest() throws IOException {
        String file = DATA_SET + "/armor.txt";
        LootGenerator.Monster monster = LootGenerator.pickMonster();
        LootGenerator.TreasureClass treasure = LootGenerator.fetchTreasureClass(monster);
        LootGenerator.BaseItem item = LootGenerator.generateBaseItem(treasure);
        LootGenerator.BaseStats stats = LootGenerator.generateBaseStats(item);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line = buffer.readLine();
        while (!line.startsWith(item.item)) {
            line = buffer.readLine();
        }
        String[] arr = line.split("\t");
        String[] num = stats.defense.split(" ");
        assertTrue(Integer.parseInt(num[1]) >= Integer.parseInt(arr[1]));
        assertTrue(Integer.parseInt(num[1]) <= Integer.parseInt(arr[2]));
    }
    
    @Test
    public void generatePrefixTest() throws IOException {
        LootGenerator.Affixes affix = LootGenerator.generateAffix();
        if (!affix.prefix.equals("")) {
            String file = DATA_SET + "/MagicPrefix.txt";
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            String line = buffer.readLine();
            while (!line.startsWith(affix.prefix)) {
                line = buffer.readLine();
            }
            String[] arr = line.split("\t");
            String[] num = affix.prefixMod.split(" ");
            assertTrue(affix.prefixMod.endsWith(arr[1]));
            assertTrue(Integer.parseInt(num[0]) >= Integer.parseInt(arr[2]));
            assertTrue(Integer.parseInt(num[0]) <= Integer.parseInt(arr[3]));
        } else {
            assertEquals(affix.prefix, affix.prefixMod);
        }
    }
    
    @Test
    public void generateSuffixTest() throws IOException {
        LootGenerator.Affixes affix = LootGenerator.generateAffix();
        if (!affix.suffix.equals("")) {
            String file = DATA_SET + "/MagicSuffix.txt";
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            String line = buffer.readLine();
            while (!line.startsWith(affix.suffix)) {
                line = buffer.readLine();
            }
            String[] arr = line.split("\t");
            String[] num = affix.suffixMod.split(" ");
            assertTrue(affix.suffixMod.endsWith(arr[1]));
            assertTrue(Integer.parseInt(num[0]) >= Integer.parseInt(arr[2]));
            assertTrue(Integer.parseInt(num[0]) <= Integer.parseInt(arr[3]));
        } else {
            assertEquals(affix.suffix, affix.suffixMod);
        }
    }
}
