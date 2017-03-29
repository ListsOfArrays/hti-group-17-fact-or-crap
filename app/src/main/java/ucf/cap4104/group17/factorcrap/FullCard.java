package ucf.cap4104.group17.factorcrap;

import java.util.ArrayList;

/**
 * Created by Jacob on 3/23/2017.
 */

public class FullCard implements CardDescription {
    public static ArrayList<FullCard> getCards() {
        // initialise list to correct size to start with.
        ArrayList<FullCard> fullCardList = new ArrayList<>(64);
        fullCardList.add(new FullCard("When a male penguin falls in love with female penguin, he searches the entire beach to find the perfect pebble to present to her.", true));
        fullCardList.add(new FullCard("New Zealand will deny people residency visa’s if they too high of a BMI and there are cases where people have been rejected because of their weight.", true));
        fullCardList.add(new FullCard("Whenever a pregnant women suffers from organ damage like heart attack, the fetus sends stem cells to the organ helping it to repair.", true));
        fullCardList.add(new FullCard("It is illegal to climb trees in Oshawa, a town in Ontario, Canada.", true));
        fullCardList.add(new FullCard("Brown eyes are blue underneath, and you can actually get a surgery to turn brown eyes blue.", true));
        fullCardList.add(new FullCard("When you blush, the lining of your stomach also turns red.", true));
        fullCardList.add(new FullCard("A bolt of lightning is six times hotter than the sun.", true));
        fullCardList.add(new FullCard("When a person cries and the first drop of tears come from the right eye, its happiness. if it from left eye, it’s pain.", true));
        fullCardList.add(new FullCard("Only 2% of Earth population naturally has green eyes.", true));
        fullCardList.add(new FullCard("Having bridesmaids in a wedding wasn’t originally for moral support. They were intended to confuse evil spirits or those who wished to harm the bride.", true));
        fullCardList.add(new FullCard("Mount Everest is pronounced as Eve-rest, not Ever-est , as it is named after George Everest.", true));
        fullCardList.add(new FullCard("All pandas in the world are on loan from China.", true));
        fullCardList.add(new FullCard("When howling together, no two wolves will howl on the same note, instead, they harmonize to create the illusion that there are more of them than there actually are.", true));
        fullCardList.add(new FullCard("Your nose can remember 50,000 different scents.", true));
        fullCardList.add(new FullCard("India’s “Go Air” airline only hires female flight attendants because they are lighter, so they save up to US$500,000 per year in fuel.", true));
        fullCardList.add(new FullCard("At room temperature, the average air molecule travels at the speed of a rifle bullet.", true));
        fullCardList.add(new FullCard("The most common color for highlighters is yellow because it doesn’t leave a shadow on the page when photocopied.", true));
        fullCardList.add(new FullCard("The Bermuda Triangle has as many ship and plane disappearances as any other region of the ocean.", true));
        fullCardList.add(new FullCard("Dolphins recognize and admire themselves in mirrors.", true));
        fullCardList.add(new FullCard("The eye makes movements 50 times every second.", true));
        fullCardList.add(new FullCard("An ant’s sense of smell is stronger than a dog’s.", true));
        fullCardList.add(new FullCard("A day on Venus lasts longer than a year, it is 243 Earth days.", true));
        fullCardList.add(new FullCard("A snail breathes through its foot.", true));
        fullCardList.add(new FullCard("Polar bear fur is transparent, not white.", true));
        fullCardList.add(new FullCard("Cat kidneys are so efficient they can rehydrate by drinking seawater.", true));
        fullCardList.add(new FullCard("During a car crash, 40 % of drivers never even hit the brakes.", true));
        fullCardList.add(new FullCard("When the moon is directly overhead, you weigh slightly less.", true));
        fullCardList.add(new FullCard("Strawberries contain more vitamin C than oranges.", true));
        fullCardList.add(new FullCard("Banana milkshake is the perfect cure for hangover.", true));
        fullCardList.add(new FullCard("90% of U.S. media(TV, news, radio) is owned by 6 companies.", true));
        fullCardList.add(new FullCard("Goldfish don’t have stomachs.", true));
        fullCardList.add(new FullCard("Melting ice and icebergs make frizzing noise called “bergy selter”.", true));
        fullCardList.add(new FullCard("Some tumors can grow hair, bones and teeth.", true));
        fullCardList.add(new FullCard("Taking a quick nap after learning can help strengthen your memory.", true));
        fullCardList.add(new FullCard("Having friends from other cultures makes you more creative, study found.", true));
        fullCardList.add(new FullCard("Shark pregnancies last up to 4 years.", true));
        fullCardList.add(new FullCard("A full moon is nine times brighter than a half moon.", true));
        fullCardList.add(new FullCard("Smiling actually boosts your immune system.", true));
        fullCardList.add(new FullCard("Dogs and elephants are the only animals that understand pointing.", true));
        fullCardList.add(new FullCard("The main exporter of Brazil nuts is not Brazil. It’s Bolivia.", true));
        fullCardList.add(new FullCard("At rest, your brain one fifth of a calorie per minute.", true));
        fullCardList.add(new FullCard("A single tree can absorb more than 10 pounds of CO2 each year.", true));
        fullCardList.add(new FullCard("Amazon River once flowed in the opposite direction, from east to west.", true));
        fullCardList.add(new FullCard("A scientific study on peanuts in bars found traces of over 100 unique specimens of urine.", false));
        fullCardList.add(new FullCard("Elevators have killed or can kill when their cable snapped.", false));
        fullCardList.add(new FullCard("You can’t fold a piece of paper in half more than 7 times.", false));
        fullCardList.add(new FullCard("Elephants are the only mammal that can’t jump.", false));
        fullCardList.add(new FullCard("One dog year is equal to seven human years.", false));
        fullCardList.add(new FullCard("If someone wrongly advertises goods for the wrong price, they have to sell it to you at that price.", false));
        fullCardList.add(new FullCard("NASA invented the DustBuster.", false));
        fullCardList.add(new FullCard("Polar Bears are left handed.", false));
        fullCardList.add(new FullCard("No two countries with McDonald’s franchises have ever gone to war.", false));
        fullCardList.add(new FullCard("The Great Wall of China is the only manmade structure visible from space.", false));
        fullCardList.add(new FullCard("Mount Everest is the tallest mountain in the world.", false));
        fullCardList.add(new FullCard("Body heat dissipates mainly through the head.", false));
        fullCardList.add(new FullCard("Glass is a slow-moving liquid.", false));
        fullCardList.add(new FullCard("Mother birds will abandon babies if you touch them.", false));
        fullCardList.add(new FullCard("Different parts of your tongue detect different tastes.", false));
        fullCardList.add(new FullCard("People thought the world was flat before Columbus.", false));
        fullCardList.add(new FullCard("Deoxygenated blood is blue.", false));
        fullCardList.add(new FullCard("Chameleons change color to blend in with surroundings.", false));
        fullCardList.add(new FullCard("Humans have only five senses.", false));
        return fullCardList;
    }

    private final String description;
    private final boolean truth;

    private FullCard(String description, boolean truth) {
        this.description = description;
        this.truth = truth;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public boolean getTruthValue() {
        return truth;
    }
}
