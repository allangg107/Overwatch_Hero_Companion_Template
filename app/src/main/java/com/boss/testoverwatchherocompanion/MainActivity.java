package com.boss.testoverwatchherocompanion;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    public final List<Hero> ALL_HEROES_LIST = HeroManager.getAllHeroes();
    private List<Hero> sortedAndFilteredHeroesList = HeroManager.getAllHeroes();

    private boolean isDisplayingHeroInfo = false;
    private boolean isDisplayingOptions = false;

    private boolean isSortedByHealth = false;
    private boolean isSortedByDefault = true;

    private boolean isFilteredForTanks = false;
    private boolean isFilteredForDamage = false;
    private boolean isFilteredForSupport = false;
    private boolean isFilteredForDefault = true;

    RecyclerView heroListView;
    GridLayoutManager gridLayoutManager;
    RecyclerViewAdapter recyclerViewAdapter;

    //keeps track of the first visible child and it's vertical offset in pixels in the recycler view
    //these values are modified in the recycler view's adapter
    //the purpose is to store the current scrollbar location
    int firstVisibleItem = 0;
    int firstVisibleItemOffset = 0;

    private String randomHeroButtonText = "random hero";

    //CHANGE NEEDED
    //comment code

    //IDEA
    //possibility to change color under options
    //if able to change under options, that setting should be saved
    //needs to change the theme color / application color

    //IDEA
    //add give feedback button

    //IDEA
    //sort and filter buttons should be properly centered

    //IDEA
    //add a notes page to explain how to use app, ex: "Help" button under options menu

    //IDEA
    //when sorting by health, give preference to armor/shield when total health is equivalent

    //IDEA
    //add button to see visual display of hero health bars, dps, etc.

    //IDEA
    //after selecting random hero button, option to jump to that hero's info page

    //IDEA
    //add same random button effect as window's version


    //this is the method called when the app starts (think of it as the main() method)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initializeAppStart();
    }

    //initialize anything that needs to be initialized when the app first starts here
    private void initializeAppStart()
    {
        displayRecyclerHeroView();
    }

    //method that will be called when the back button is pressed
    //displays the main grid of heroes
    public void backButton(View view)
    {
        displayRecyclerHeroView();
    }

    //sets the content view to display the information of the hero passed in
    public void displayHeroInformation(Hero hero)
    {
        setContentView(R.layout.hero_information);

        ImageView imageView = findViewById(R.id.heroInfoBackgroundImage);
        imageView.setImageResource(hero.heroImage);

        TextView textView = findViewById(R.id.heroNameTextView);
        textView.setText(hero.heroName);
        textView = findViewById(R.id.heroDifficultyTextView);
        textView.setText(("Difficulty: " + hero.heroDifficulty));
        textView = findViewById(R.id.strengthTextView);
        textView.setText(hero.heroStrengths);
        textView = findViewById(R.id.weaknessTextView);
        textView.setText(hero.heroWeaknesses);
        textView = findViewById(R.id.heroHealthArmorShieldTextView);
        textView.setText(hero.heroHealthArmorShieldToString());
        textView = findViewById(R.id.heroLeftClickDamageTextView);
        textView.setText(hero.heroLeftClickDamage);
        textView = findViewById(R.id.heroRightClickDamageTextView);
        textView.setText(hero.heroRightClickDamage);
        textView = findViewById(R.id.heroSpeedTextView);
        textView.setText(hero.heroSpeed);
        textView = findViewById(R.id.heroPassiveNameTextView);
        textView.setText("Passive: " + hero.heroPassiveName);
        textView = findViewById(R.id.heroPassiveTextView);
        if(hero.heroPassive.equals("")) textView.setVisibility(View.GONE);
        textView.setText(hero.heroPassive);
        textView = findViewById(R.id.heroAbility1NameTextView);
        textView.setText("Ability 1: " + hero.heroAbility1Name);
        textView = findViewById(R.id.heroAbility1TextView);
        textView.setText(hero.heroAbility1);
        textView = findViewById(R.id.heroAbility2NameTextView);
        textView.setText("Ability 2: " + hero.heroAbility2Name);
        textView = findViewById(R.id.heroAbility2TextView);
        textView.setText(hero.heroAbility2);
        textView = findViewById(R.id.heroUltimateNameTextView);
        textView.setText("Ultimate: " + hero.heroUltimateName);
        textView = findViewById(R.id.heroUltimateTextView);
        textView.setText(hero.heroUltimate);
        textView = findViewById(R.id.heroTipsAndTricksTextView);
        textView.setText(hero.heroTipsAndTricks);

        isDisplayingHeroInfo = true;
    }

    //sets the content view to display the main grid of heroes
    public void displayRecyclerHeroView()
    {
        setContentView(R.layout.hero_recycler_view);

        heroListView = findViewById(R.id.mainView);

        gridLayoutManager = new GridLayoutManager(this,3,GridLayoutManager.VERTICAL, false);
        recyclerViewAdapter = new RecyclerViewAdapter(this, sortedAndFilteredHeroesList, this);

        gridLayoutManager.scrollToPositionWithOffset(firstVisibleItem, firstVisibleItemOffset);

        heroListView.setLayoutManager(gridLayoutManager);
        heroListView.setAdapter(recyclerViewAdapter);

        Button randomHeroButton = findViewById(R.id.randomHeroButton);
        randomHeroButton.setText(randomHeroButtonText);

        isDisplayingHeroInfo = false;
        isDisplayingOptions = false;
    }

    //overrides the phone's navigation back button
    @Override
    public void onBackPressed()
    {
        if(isDisplayingHeroInfo) backButton(heroListView);
        else if(isDisplayingOptions) findViewById(R.id.optionsParentLayout).setVisibility(View.GONE);
        else super.onBackPressed();
    }

    //method called when the random hero button is pressed
    public void randomHeroButton_OnClick(View view)
    {
        int randomNum = ThreadLocalRandom.current().nextInt(0, ALL_HEROES_LIST.size()-1);

        String randomHero = "";
        randomHero = ALL_HEROES_LIST.get(randomNum).heroName;

        //changes the button's text to the random hero
        Button b1 = findViewById(R.id.randomHeroButton);
        b1.setText(randomHero);

        randomHeroButtonText = randomHero;
    }

    //method called when the options button is pressed
    public void optionsButton_OnClick(View view)
    {
        if(isDisplayingOptions == false)
        {
            findViewById(R.id.optionsParentLayout).setVisibility(View.VISIBLE);
            showSelectedSortAndFilter();
            isDisplayingOptions = true;
        }
        else
        {
            findViewById(R.id.optionsParentLayout).setVisibility(View.GONE);
            showSelectedSortAndFilter();
            isDisplayingOptions = false;
        }
    }

    //changes the option buttons' background based on which have been selected
    private void showSelectedSortAndFilter()
    {
        Button healthSB = findViewById(R.id.healthSortButton);
        Button defaultSB = findViewById(R.id.defaultSortButton);

        if(isSortedByHealth)
        {
            healthSB.setBackgroundColor(getResources().getColor(R.color.white));
            healthSB.setTextColor(getResources().getColor(R.color.light_orange));

            defaultSB.setBackgroundColor(getResources().getColor(R.color.light_orange));
            defaultSB.setTextColor(getResources().getColor(R.color.white));
        }
        else if(isSortedByDefault)
        {
            defaultSB.setBackgroundColor(getResources().getColor(R.color.white));
            defaultSB.setTextColor(getResources().getColor(R.color.light_orange));

            healthSB.setBackgroundColor(getResources().getColor(R.color.light_orange));
            healthSB.setTextColor(getResources().getColor(R.color.white));
        }

        Button tankFB = findViewById(R.id.tankFilterButton);
        Button damageFB = findViewById(R.id.damageFilterButton);
        Button supportFB = findViewById(R.id.supportFilterButton);
        Button defaultFB = findViewById(R.id.defaultFilterButton);

        if(isFilteredForTanks)
        {
            tankFB.setBackgroundColor(getResources().getColor(R.color.white));
            tankFB.setTextColor(getResources().getColor(R.color.light_orange));

            damageFB.setBackgroundColor(getResources().getColor(R.color.light_orange));
            damageFB.setTextColor(getResources().getColor(R.color.white));

            supportFB.setBackgroundColor(getResources().getColor(R.color.light_orange));
            supportFB.setTextColor(getResources().getColor(R.color.white));

            defaultFB.setBackgroundColor(getResources().getColor(R.color.light_orange));
            defaultFB.setTextColor(getResources().getColor(R.color.white));
        }
        else if(isFilteredForDamage)
        {
            damageFB.setBackgroundColor(getResources().getColor(R.color.white));
            damageFB.setTextColor(getResources().getColor(R.color.light_orange));

            tankFB.setBackgroundColor(getResources().getColor(R.color.light_orange));
            tankFB.setTextColor(getResources().getColor(R.color.white));

            supportFB.setBackgroundColor(getResources().getColor(R.color.light_orange));
            supportFB.setTextColor(getResources().getColor(R.color.white));

            defaultFB.setBackgroundColor(getResources().getColor(R.color.light_orange));
            defaultFB.setTextColor(getResources().getColor(R.color.white));
        }
        else if(isFilteredForSupport)
        {
            supportFB.setBackgroundColor(getResources().getColor(R.color.white));
            supportFB.setTextColor(getResources().getColor(R.color.light_orange));

            damageFB.setBackgroundColor(getResources().getColor(R.color.light_orange));
            damageFB.setTextColor(getResources().getColor(R.color.white));

            tankFB.setBackgroundColor(getResources().getColor(R.color.light_orange));
            tankFB.setTextColor(getResources().getColor(R.color.white));

            defaultFB.setBackgroundColor(getResources().getColor(R.color.light_orange));
            defaultFB.setTextColor(getResources().getColor(R.color.white));
        }
        else if(isFilteredForDefault)
        {
            defaultFB.setBackgroundColor(getResources().getColor(R.color.white));
            defaultFB.setTextColor(getResources().getColor(R.color.light_orange));

            damageFB.setBackgroundColor(getResources().getColor(R.color.light_orange));
            damageFB.setTextColor(getResources().getColor(R.color.white));

            supportFB.setBackgroundColor(getResources().getColor(R.color.light_orange));
            supportFB.setTextColor(getResources().getColor(R.color.white));

            tankFB.setBackgroundColor(getResources().getColor(R.color.light_orange));
            tankFB.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public void sortHeroesByHealth(View view)
    {
        sortByHealth();
    }

    public void sortHeroesByDefault(View view)
    {
        sortByDefault();
    }

    private void sortByHealth()
    {
        int numHeroes = sortedAndFilteredHeroesList.size();

        Hero temp;

        for(int i = 1; i < numHeroes; i++)
        {
            temp = sortedAndFilteredHeroesList.get(i);

            int k = i - 1;
            for(; k >= 0 && sortedAndFilteredHeroesList.get(k).getTotalHeroHealthArmorShield() < temp.getTotalHeroHealthArmorShield(); k--)
            {
                sortedAndFilteredHeroesList.remove(k+1);
                sortedAndFilteredHeroesList.add(k+1, sortedAndFilteredHeroesList.get(k));
            }
            sortedAndFilteredHeroesList.remove(k+1);
            sortedAndFilteredHeroesList.add(k+1, temp);
        }

        setSortedBy("health");
        displayRecyclerHeroView();
    }

    private void sortByDefault()
    {
        if(isFilteredForTanks) sortedAndFilteredHeroesList = HeroManager.getClassHeroes("tank");
        if(isFilteredForDamage) sortedAndFilteredHeroesList = HeroManager.getClassHeroes("damage");
        if(isFilteredForSupport) sortedAndFilteredHeroesList = HeroManager.getClassHeroes("support");
        if(isFilteredForDefault) sortedAndFilteredHeroesList = HeroManager.getAllHeroes();

        setSortedBy("default");
        displayRecyclerHeroView();
    }

    //sets the SortedBy boolean variables
    private void setSortedBy(String sortedBy)
    {
        if(sortedBy.equals("health"))
        {
            isSortedByHealth = true;
            isSortedByDefault = false;
        }
        else if(sortedBy.equals("default"))
        {
            isSortedByHealth = false;
            isSortedByDefault = true;
        }
    }

    public void filterForTankHeroes(View view)
    {
        filterForTank();
    }

    public void filterForDamageHeroes(View view)
    {
        filterForDamage();
    }

    public void filterForSupportHeroes(View view)
    {
        filterForSupport();
    }

    public void filterForDefaultHeroes(View view)
    {
        filterForDefault();
    }

    private void filterForTank()
    {
        sortedAndFilteredHeroesList = HeroManager.getClassHeroes("tank");
        setFilteredBy("tank");
        sortByCurrentSort();
    }

    private void filterForDamage()
    {
        sortedAndFilteredHeroesList = HeroManager.getClassHeroes("damage");
        setFilteredBy("damage");
        sortByCurrentSort();
    }

    private void filterForSupport()
    {
        sortedAndFilteredHeroesList = HeroManager.getClassHeroes("support");
        setFilteredBy("support");
        sortByCurrentSort();
    }

    private void filterForDefault()
    {
        sortedAndFilteredHeroesList = HeroManager.getAllHeroes();
        setFilteredBy("default");
        sortByCurrentSort();
    }

    //sets the FilteredBy boolean variables
    private void setFilteredBy(String filteredBy)
    {
        if(filteredBy.equals("tank"))
        {
            isFilteredForTanks = true;
            isFilteredForDamage = false;
            isFilteredForSupport = false;
            isFilteredForDefault = false;
        }
        else if(filteredBy.equals("damage"))
        {
            isFilteredForTanks = false;
            isFilteredForDamage = true;
            isFilteredForSupport = false;
            isFilteredForDefault = false;
        }
        else if(filteredBy.equals("support"))
        {
            isFilteredForTanks = false;
            isFilteredForDamage = false;
            isFilteredForSupport = true;
            isFilteredForDefault = false;
        }
        else if(filteredBy.equals("default"))
        {
            isFilteredForTanks = false;
            isFilteredForDamage = false;
            isFilteredForSupport = false;
            isFilteredForDefault = true;
        }
    }

    //sorts the list by the current sort
    //method to be called after filtering has been changed
    private void sortByCurrentSort()
    {
        if(isSortedByHealth) sortByHealth();
        else if(isSortedByDefault) sortByDefault();
    }
}