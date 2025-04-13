package edu.grinnell.csc207.lootgenerator;

import java.util.Random;
import java.util.List;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LootGenerator {
    /** The path to the dataset (either the small or large set). */
    private static final String DATA_SET = "data/large";
    
    public static class Monster {
        
        public final String name;
        
        public Monster (String monsterName) {
            name = monsterName;
        }
    }
    
    public static class TreasureClass {
        
        public final String treasure;
        
        public TreasureClass (String loot) {
            treasure = loot;
        }
    }
    
    public static class BaseItem {
        
        public final String item;
        
        public BaseItem (String armor) {
            item = armor;
        }
    }
    
    public static class BaseStats {
        
        public final String defense;
        
        public BaseStats (int ac) {
            defense = "Defense: " + ac;
        }
    }
    
    public static class Affixes {
        
        public final String prefix;
        public final String suffix;
        public final String prefixMod;
        public final String suffixMod;
        
        public Affixes (String pre, String post, String preMod, String postMod) {
            prefix = pre;
            suffix = post;
            prefixMod = preMod;
            suffixMod = postMod;
        }
    }
    
    /**
     * Generates a list of all the monster available to fight.
     * 
     * @return a list of monsters
     * @throws FileNotFoundException if the file which contains the monster
     * names is not found
     * @throws IOException if the line in the file cannot be read
     */
    public static List<Monster> generateMonsterList() throws FileNotFoundException, IOException {
        List<Monster> list = new LinkedList();
        String file = DATA_SET + "/monstats.txt";
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line = buffer.readLine();
        String[] arr;
        String monsterName;
        Monster monster;
        while (line != null) {
            arr = line.split("\t");
            monsterName = arr[0];
            monster = new Monster(monsterName);
            list.add(monster);
            line = buffer.readLine();
        }
        return list;
    }
    
    /**
     * Picks a monster at random to fight
     * 
     * @return the monster chosen
     * @throws IOException from generateMonsterList()
     */
    public static Monster pickMonster() throws IOException {
        List<Monster> list = generateMonsterList();
        int rand = new Random().nextInt(list.size());
        Monster monster = list.get(rand);
        return monster;
    }
    
    /**
     * Finds the treasure class associated with the given monster and selects it
     * 
     * @param monster the monster whose treasure class we are looking for
     * @return the corresponding treasure class
     * @throws FileNotFoundException if the file that contains the treasure
     * classes is not found
     * @throws IOException if the line in the file cannot be read
     */
    public static TreasureClass fetchTreasureClass(Monster monster) throws FileNotFoundException, IOException {
        String file = DATA_SET + "/monstats.txt";
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line = buffer.readLine();
        while (!line.startsWith(monster.name)) {
            line = buffer.readLine();
        }
        String[] arr = line.split("\t");
        TreasureClass treasure = new TreasureClass(arr[arr.length - 1]);
        return treasure;
    }

    /**
     * Checks if the given string is a treasure class
     * 
     * @param str a string found as part of a treasure class
     * @return whether or not the string is a treasure class
     * @throws FileNotFoundException if the file containing treasure classes
     * cannot be found
     * @throws IOException if the line in the file cannot be read
     */
    public static boolean isTreasureClass(String str) throws FileNotFoundException, IOException {
        String file = DATA_SET + "/TreasureClassEx.txt";
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line = buffer.readLine();
        while (line != null) {
            if (line.startsWith(str)) {
                return true;
            }
            line = buffer.readLine();
        }
        return false;
    }
    
    /**
     * Generates a dropped item that is part of the given treasure class
     * 
     * @param treasure the treasure class that corresponds to the item
     * @return the item dropped by the monster
     * @throws IOException if the line in the file cannot be read
     */
    public static BaseItem generateBaseItem(TreasureClass treasure) throws IOException {
        String file = DATA_SET + "/TreasureClassEx.txt";
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line = buffer.readLine();
        while (!line.startsWith(treasure.treasure)) {
            line = buffer.readLine();
        }
        String[] arr = line.split("\t");
        int rand = new Random().nextInt(3);
        String loot = arr[rand + 1];
        if (isTreasureClass(loot)) {
            return generateBaseItem(new TreasureClass(loot));
        } else {
            BaseItem item = new BaseItem(loot);
            return item;
        }
    }
    
    /**
     * Generates the stats that the dropped item has
     * 
     * @param item the item dropped
     * @return the defense stat of the item
     * @throws FileNotFoundException if the file containing the armor stats
     * cannot be read
     * @throws IOException if the line in the file cannot be read
     */
    public static BaseStats generateBaseStats(BaseItem item) throws FileNotFoundException, IOException {
        String file = DATA_SET + "/armor.txt";
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line = buffer.readLine();
        while (!line.startsWith(item.item)) {
            line = buffer.readLine();
        }
        String[] arr = line.split("\t");
        int min = Integer.parseInt(arr[1]);
        int max = Integer.parseInt(arr[2]);
        int rand = new Random().nextInt(max - min + 1) + min;
        BaseStats stats = new BaseStats(rand);
        return stats;
    }
    
    /**
     * Generates a list of the available prefixes
     * 
     * @return a list of strings
     * @throws FileNotFoundException if the file being read from cannot be found
     * @throws IOException if the line in the file cannot be read
     */
    public static List<String> generatePrefixList() throws FileNotFoundException, IOException {
        List<String> list = new LinkedList();
        String file = DATA_SET + "/MagicPrefix.txt";
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line = buffer.readLine();
        String[] arr;
        String prefix;
        while (line != null) {
            arr = line.split("\t");
            prefix = arr[0];
            list.add(prefix);
            line = buffer.readLine();
        }
        return list;
    }

    /**
     * Generates a list of available suffixes
     * 
     * @return a list of strings
     * @throws FileNotFoundException if the file being read from cannot be found
     * @throws IOException if the line in the file cannot be read
     */
    public static List<String> generateSuffixList() throws FileNotFoundException, IOException {
        List<String> list = new LinkedList();
        String file = DATA_SET + "/MagicSuffix.txt";
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line = buffer.readLine();
        String[] arr;
        String suffix;
        while (line != null) {
            arr = line.split("\t");
            suffix = arr[0];
            list.add(suffix);
            line = buffer.readLine();
        }
        return list;
    }
    
    /**
     * Generates affixes at random alongside their modifiers
     * 
     * @return the affixes of the item
     * @throws IOException from generatePrefixList() and generateSuffixList()
     */
    public static Affixes generateAffix() throws IOException {
        int rand = new Random().nextInt(2);
        String prefix;
        String suffix;
        String addPreStats;
        String addPostStats;
        if (rand == 0) {
            List<String> prefixList = generatePrefixList();
            int preRand = new Random().nextInt(prefixList.size());
            prefix = prefixList.get(preRand);
            addPreStats = addStatsPrefix(prefix);
        } else {
            prefix = "";
            addPreStats = "";
        }
        rand = new Random().nextInt(2);
        if (rand == 0) {
            List<String> suffixList = generateSuffixList();
            int postRand = new Random().nextInt(suffixList.size());
            suffix = suffixList.get(postRand);
            addPostStats = addStatsSuffix(suffix);
        } else {
            suffix = "";
            addPostStats = "";
        }
        Affixes affix = new Affixes(prefix, suffix, addPreStats, addPostStats);
        return affix;
    }
    
    /**
     * Produces a string conveying the stats of given by the prefix
     * 
     * @param prefix the prefix of the item
     * @return the additional stats of the item
     * @throws FileNotFoundException if the file containing the prefix's stats 
     * cannot be found
     * @throws IOException if the line in the file cannot be read
     */
    public static String addStatsPrefix(String prefix) throws FileNotFoundException, IOException {
        String file = DATA_SET + "/MagicPrefix.txt";
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line = buffer.readLine();
        while (!line.startsWith(prefix)) {
            line = buffer.readLine();
        }
        String[] arr = line.split("\t");
        String mod = arr[1];
        int min = Integer.parseInt(arr[2]);
        int max = Integer.parseInt(arr[3]);
        int rand = new Random().nextInt(max - min + 1) + min;
        return rand + " " + mod;
    }
    
    /**
     * Produces a string conveying the stats of given by the suffix
     * 
     * @param suffix the suffix of the item
     * @return the additional stats of the item
     * @throws FileNotFoundException if the file containing the suffix's stats 
     * cannot be found
     * @throws IOException if the line in the file cannot be read
     */
    public static String addStatsSuffix(String suffix) throws FileNotFoundException, IOException {
        String file = DATA_SET + "/MagicSuffix.txt";
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line = buffer.readLine();
        while (!line.startsWith(suffix)) {
            line = buffer.readLine();
        }
        String[] arr = line.split("\t");
        String mod = arr[1];
        int min = Integer.parseInt(arr[2]);
        int max = Integer.parseInt(arr[3]);
        int rand = new Random().nextInt(max - min + 1) + min;
        return rand + " " + mod;
    }

    /**
     * Prints out the item according to its affixes
     * 
     * @param item the item dropped
     * @param affix the titles of the item
     */
    public static void affixPrinting(BaseItem item, Affixes affix) {
        if (!affix.prefix.equals("") && !affix.suffix.equals("")) {
            System.out.println(affix.prefix + " " + item.item + " " + affix.suffix);
        } else if (!affix.prefix.equals("")) {
            System.out.println(affix.prefix + " " + item.item);
        } else if (!affix.suffix.equals("")) {
            System.out.println(item.item + " " + affix.suffix);
        } else {
            System.out.println(item.item);
        }
    }
    
    /**
     * Prints out the dialogue as you slay the given monster
     * 
     * @param monster the monster slain
     * @param treasure the treasure class of the monster
     * @param item what the monster dropped
     * @param stats the stats of the item
     * @param affix the titles of the item
     */
    public static void printing(Monster monster, TreasureClass treasure,
            BaseItem item, BaseStats stats, Affixes affix) {
        System.out.println();
        System.out.println("Fighting " + monster.name + "...");
        System.out.println("You have slain " + monster.name + "!");
        System.out.println(monster.name + " dropped:");
        System.out.println();
        affixPrinting(item, affix);
        System.out.println(stats.defense);
        if (!affix.prefixMod.equals("")) {
            System.out.println(affix.prefixMod);
        }
        if (!affix.suffixMod.equals("")) {
            System.out.println(affix.suffixMod);
        }
    }
    
    /**
     * Takes input from the scanner to determine if the program should continue
     * or quit
     * 
     * @return whether or not the program should continue
     */
    public static boolean continueOrQuit() {
        boolean running = true;
        boolean asking;
        Scanner scan;
        String answer;
        System.out.println();
        System.out.println("Fight again [y/n]?");
        scan = new Scanner(System.in);
        asking = true;
        while (asking) {
            answer = scan.next();
            if (answer.substring(0, 1).equalsIgnoreCase("y")) {
                asking = false;
            } else if (answer.substring(0, 1).equalsIgnoreCase("n")) {
                running = false;
                asking = false;
            } else {
                System.out.println("Incorrect input. Please try again.");
            }
        }
        return running;
    }

    /**
     * Generates the various implements and puts them together
     * 
     * @param args the commands given in the command line
     * @throws IOException from the various generating functions
     */
    public static void main(String[] args) throws IOException {
        boolean running = true;
        Monster monster;
        TreasureClass treasure;
        BaseItem item;
        BaseStats stats;
        Affixes affix;
        System.out.println("This program kills monsters and generates loot!");
        while (running) {
            monster = pickMonster();
            treasure = fetchTreasureClass(monster);
            item = generateBaseItem(treasure);
            stats = generateBaseStats(item);
            affix = generateAffix();
            printing(monster, treasure, item, stats, affix);
            running = continueOrQuit();
        }
        System.exit(0);
    }
}
