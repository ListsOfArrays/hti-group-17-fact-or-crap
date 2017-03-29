package ucf.cap4104.group17.factorcrap;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Jacob on 3/23/2017.
 */

public class Card {
    public static ArrayList<Card> getCards() {
        // initialise list to correct size to start with.
        ArrayList<Card> cardList = new ArrayList<>(64);
        cardList.add(new Card("When a male penguin falls in love with female penguin, he searches the entire beach to find the perfect pebble to present to her.", true));
        cardList.add(new Card("New Zealand will deny people residency visa’s if they too high of a BMI and there are cases where people have been rejected because of their weight.", true));
        cardList.add(new Card("Whenever a pregnant women suffers from organ damage like heart attack, the fetus sends stem cells to the organ helping it to repair.", true));
        cardList.add(new Card("It is illegal to climb trees in Oshawa, a town in Ontario, Canada.", true));
        cardList.add(new Card("Brown eyes are blue underneath, and you can actually get a surgery to turn brown eyes blue.", true));
        cardList.add(new Card("When you blush, the lining of your stomach also turns red.", true));
        cardList.add(new Card("A bolt of lightning is six times hotter than the sun.", true));
        cardList.add(new Card("When a person cries and the first drop of tears come from the right eye, its happiness. if it from left eye, it’s pain.", true));
        cardList.add(new Card("Only 2% of Earth population naturally has green eyes.", true));
        cardList.add(new Card("Having bridesmaids in a wedding wasn’t originally for moral support. They were intended to confuse evil spirits or those who wished to harm the bride.", true));
        cardList.add(new Card("Mount Everest is pronounced as Eve-rest, not Ever-est , as it is named after George Everest.", true));
        cardList.add(new Card("All pandas in the world are on loan from China.", true));
        cardList.add(new Card("When howling together, no two wolves will howl on the same note, instead, they harmonize to create the illusion that there are more of them than there actually are.", true));
        cardList.add(new Card("Your nose can remember 50,000 different scents.", true));
        cardList.add(new Card("India’s “Go Air” airline only hires female flight attendants because they are lighter, so they save up to US$500,000 per year in fuel.", true));
        cardList.add(new Card("At room temperature, the average air molecule travels at the speed of a rifle bullet.", true));
        cardList.add(new Card("The most common color for highlighters is yellow because it doesn’t leave a shadow on the page when photocopied.", true));
        cardList.add(new Card("The Bermuda Triangle has as many ship and plane disappearances as any other region of the ocean.", true));
        cardList.add(new Card("Dolphins recognize and admire themselves in mirrors.", true));
        cardList.add(new Card("The eye makes movements 50 times every second.", true));
        cardList.add(new Card("An ant’s sense of smell is stronger than a dog’s.", true));
        cardList.add(new Card("A day on Venus lasts longer than a year, it is 243 Earth days.", true));
        cardList.add(new Card("A snail breathes through its foot.", true));
        cardList.add(new Card("Polar bear fur is transparent, not white.", true));
        cardList.add(new Card("Cat kidneys are so efficient they can rehydrate by drinking seawater.", true));
        cardList.add(new Card("During a car crash, 40 % of drivers never even hit the brakes.", true));
        cardList.add(new Card("When the moon is directly overhead, you weigh slightly less.", true));
        cardList.add(new Card("Strawberries contain more vitamin C than oranges.", true));
        cardList.add(new Card("Banana milkshake is the perfect cure for hangover.", true));
        cardList.add(new Card("90% of U.S. media(TV, news, radio) is owned by 6 companies.", true));
        cardList.add(new Card("Goldfish don’t have stomachs.", true));
        cardList.add(new Card("Melting ice and icebergs make frizzing noise called “bergy selter”.", true));
        cardList.add(new Card("Some tumors can grow hair, bones and teeth.", true));
        cardList.add(new Card("Taking a quick nap after learning can help strengthen your memory.", true));
        cardList.add(new Card("Having friends from other cultures makes you more creative, study found.", true));
        cardList.add(new Card("Shark pregnancies last up to 4 years.", true));
        cardList.add(new Card("A full moon is nine times brighter than a half moon.", true));
        cardList.add(new Card("Smiling actually boosts your immune system.", true));
        cardList.add(new Card("Dogs and elephants are the only animals that understand pointing.", true));
        cardList.add(new Card("The main exporter of Brazil nuts is not Brazil. It’s Bolivia.", true));
        cardList.add(new Card("At rest, your brain one fifth of a calorie per minute.", true));
        cardList.add(new Card("A single tree can absorb more than 10 pounds of CO2 each year.", true));
        cardList.add(new Card("Amazon River once flowed in the opposite direction, from east to west.", true));
        cardList.add(new Card("A scientific study on peanuts in bars found traces of over 100 unique specimens of urine.", false));
        cardList.add(new Card("Elevators have killed or can kill when their cable snapped.", false));
        cardList.add(new Card("You can’t fold a piece of paper in half more than 7 times.", false));
        cardList.add(new Card("Elephants are the only mammal that can’t jump.", false));
        cardList.add(new Card("One dog year is equal to seven human years.", false));
        cardList.add(new Card("If someone wrongly advertises goods for the wrong price, they have to sell it to you at that price.", false));
        cardList.add(new Card("NASA invented the DustBuster.", false));
        cardList.add(new Card("Polar Bears are left handed.", false));
        cardList.add(new Card("No two countries with McDonald’s franchises have ever gone to war.", false));
        cardList.add(new Card("The Great Wall of China is the only manmade structure visible from space.", false));
        cardList.add(new Card("Mount Everest is the tallest mountain in the world.", false));
        cardList.add(new Card("Body heat dissipates mainly through the head.", false));
        cardList.add(new Card("Glass is a slow-moving liquid.", false));
        cardList.add(new Card("Mother birds will abandon babies if you touch them.", false));
        cardList.add(new Card("Different parts of your tongue detect different tastes.", false));
        cardList.add(new Card("People thought the world was flat before Columbus.", false));
        cardList.add(new Card("Deoxygenated blood is blue.", false));
        cardList.add(new Card("Chameleons change color to blend in with surroundings.", false));
        cardList.add(new Card("Humans have only five senses.", false));
        return cardList;
    }

    private final String description;
    private final boolean truth;

    private Card(String description, boolean truth) {
        this.description = description;
        this.truth = truth;
    }

    public String getDescription() {
        return description;
    }

    public boolean getTruthValue() {
        return truth;
    }
}
