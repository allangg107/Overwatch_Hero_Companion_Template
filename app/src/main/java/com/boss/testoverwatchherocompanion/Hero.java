package com.boss.testoverwatchherocompanion;

public class Hero
{
    public String heroName;
    public int heroDifficulty;
    public String heroStrengths;
    public String heroWeaknesses;
    public int heroHealth;
    public int heroArmor;
    public int heroShield;
    public String heroLeftClickDamage;
    public String heroRightClickDamage;
    public String heroSpeed;
    public String heroPassiveName;
    public String heroPassive;
    public String heroAbility1Name;
    public String heroAbility1;
    public String heroAbility2Name;
    public String heroAbility2;
    public String heroUltimateName;
    public String heroUltimate;
    public String heroTipsAndTricks;
    public String heroClass;
    public int heroImage;

    public String heroHealthArmorShieldToString()
    {
        String retVal = "Health: " + heroHealth;
        if(heroArmor != 0) retVal += ", Armor: " + heroArmor;
        if(heroShield != 0) retVal += ", Shield: " + heroShield;
        if(heroName.equals("D.Va")) retVal = "Health(Mech): 400, Armor(Mech): 200, Health(Hero): 150";

            return retVal;
    }

    public int getTotalHeroHealthArmorShield()
    {
        return heroHealth + heroArmor + heroShield;
    }
}
