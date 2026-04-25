package com.mohammadbesharat.atlasmeat.config;

import com.mohammadbesharat.atlasmeat.order.domain.AnimalType;
import com.mohammadbesharat.atlasmeat.order.domain.Cut;
import com.mohammadbesharat.atlasmeat.order.repo.CutRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DevDataSeeder implements CommandLineRunner {

    private final CutRepository cutRepository;
    public DevDataSeeder(CutRepository cutRepository){
        this.cutRepository = cutRepository;
    }

    @Override
    public void run(String... args){
        //if cuts are already seeded, dont dupe
        if(cutRepository.count() > 0) return;

        //seed cuts based on real cut sheet from atlas meat
        //seed cuts for beef
        cutRepository.save(new Cut(AnimalType.BEEF, "CHUCK_ROAST", "Chuck Roast"));
        cutRepository.save(new Cut(AnimalType.BEEF, "CHUCK_STEAK", "Chuck Steak"));
        cutRepository.save(new Cut(AnimalType.BEEF, "ARM_ROAST", "Arm Roast"));
        cutRepository.save(new Cut(AnimalType.BEEF, "ARM_STEAK", "Arm Steak"));
        cutRepository.save(new Cut(AnimalType.BEEF, "PRIME_RIB", "Prime Rib"));
        cutRepository.save(new Cut(AnimalType.BEEF, "RIBEYE", "Ribeye"));
        cutRepository.save(new Cut(AnimalType.BEEF, "RIB_STEAKS", "Rib Steaks"));
        cutRepository.save(new Cut(AnimalType.BEEF, "TBONE", "T-Bone"));
        cutRepository.save(new Cut(AnimalType.BEEF, "NEW_YORK", "New York"));
        cutRepository.save(new Cut(AnimalType.BEEF, "SIRLOIN_ROAST", "Sirloin Roast"));
        cutRepository.save(new Cut(AnimalType.BEEF, "SIRLOIN_STEAK", "Sirloin Steak"));
        cutRepository.save(new Cut(AnimalType.BEEF, "TIP_ROAST", "Tip Roast"));
        cutRepository.save(new Cut(AnimalType.BEEF, "TIP_STEAK", "Tip Steak"));
        cutRepository.save(new Cut(AnimalType.BEEF, "TOP_ROUND_ROAST", "Top Round Roast"));
        cutRepository.save(new Cut(AnimalType.BEEF, "TOP_ROUND_STEAK", "Top Round Steak"));
        cutRepository.save(new Cut(AnimalType.BEEF, "BOTTOM_ROUND_ROAST", "Bottom Round Roast"));
        cutRepository.save(new Cut(AnimalType.BEEF, "BOTTOM_ROUND_STEAK", "Bottom Round Steak"));
        cutRepository.save(new Cut(AnimalType.BEEF, "TENDERLOIN_ROAST", "Tenderloin Roast"));
        cutRepository.save(new Cut(AnimalType.BEEF, "TENDERLOIN_STEAK", "Tenderloin Steak"));
        cutRepository.save(new Cut(AnimalType.BEEF, "FLANK_STEAK", "Flank Steak"));
        cutRepository.save(new Cut(AnimalType.BEEF, "BRISKET", "Brisket"));
        cutRepository.save(new Cut(AnimalType.BEEF, "EYE_OF_ROUND_ROAST", "Eye Of Round Roast"));
        cutRepository.save(new Cut(AnimalType.BEEF, "STEW_MEAT", "Stew Meat"));
        cutRepository.save(new Cut(AnimalType.BEEF, "SKIRT_STEAK", "Skirt Steak"));
        cutRepository.save(new Cut(AnimalType.BEEF, "SHANKS", "Shanks"));
        cutRepository.save(new Cut(AnimalType.BEEF, "SHORT_RIB", "Short Rib"));
        cutRepository.save(new Cut(AnimalType.BEEF, "TONGUE", "Tongue"));
        cutRepository.save(new Cut(AnimalType.BEEF, "CHEEK_MEAT", "Cheek Meat"));
        cutRepository.save(new Cut(AnimalType.BEEF, "HEART", "Heart"));
        cutRepository.save(new Cut(AnimalType.BEEF, "LIVER", "Liver"));
        cutRepository.save(new Cut(AnimalType.BEEF, "KIDNEY", "Kidney"));
        cutRepository.save(new Cut(AnimalType.BEEF, "NECK_BONES", "Neck Bones"));
        cutRepository.save(new Cut(AnimalType.BEEF, "DOG_BONES", "Dog Bones"));
        cutRepository.save(new Cut(AnimalType.BEEF, "MORROW", "Morrow"));
        cutRepository.save(new Cut(AnimalType.BEEF, "KNUCKLE", "KNUCKLE"));
        cutRepository.save(new Cut(AnimalType.BEEF, "OXTAIL", "Oxtail"));
        cutRepository.save(new Cut(AnimalType.BEEF, "FAT", "Fat"));
        cutRepository.save(new Cut(AnimalType.BEEF, "BURGER", "Burger"));
        cutRepository.save(new Cut(AnimalType.BEEF, "BURGER_PATTIES", "Burger Patties"));

        //seed cuts for hog
        cutRepository.save(new Cut(AnimalType.HOG, "SHOULDER_ROAST", "Shoulder Roast"));
        cutRepository.save(new Cut(AnimalType.HOG, "SHOULDER_STEAK", "Shoulder Steak"));
        cutRepository.save(new Cut(AnimalType.HOG, "PICNIC_ROAST", "Picnic Roast"));
        cutRepository.save(new Cut(AnimalType.HOG, "PICNIC_STEAK", "Picnic Steak"));
        cutRepository.save(new Cut(AnimalType.HOG, "LOIN", "Loin"));
        cutRepository.save(new Cut(AnimalType.HOG, "BABY_BACK_RIBS", "Baby Back Ribs"));
        cutRepository.save(new Cut(AnimalType.HOG, "SPARE_RIBS", "Spare Ribs"));
        cutRepository.save(new Cut(AnimalType.HOG, "HAM", "Ham"));
        cutRepository.save(new Cut(AnimalType.HOG, "BACON", "Bacon"));
        cutRepository.save(new Cut(AnimalType.HOG, "NECK_BONES", "Neck Bones"));
        cutRepository.save(new Cut(AnimalType.HOG, "BRATWURST", "Bratwurst"));
        cutRepository.save(new Cut(AnimalType.HOG, "BREAKFAST_SAUSAGE", "Breakfast Sausage"));
        cutRepository.save(new Cut(AnimalType.HOG, "ITALIAN_SAUSAGE", "Italian Sausage"));
        cutRepository.save(new Cut(AnimalType.HOG, "GERMAN_SAUSAGE", "German Sausage"));
        cutRepository.save(new Cut(AnimalType.HOG, "CHORIZO", "Chorizo"));

        //seed cuts for lamb
        cutRepository.save(new Cut(AnimalType.LAMB, "LEG_ROAST", "Leg Roast"));
        cutRepository.save(new Cut(AnimalType.LAMB, "LEG_STEAK", "Leg STEAK"));
        cutRepository.save(new Cut(AnimalType.LAMB, "SHOULDER_ROAST", "Shoulder Roast"));
        cutRepository.save(new Cut(AnimalType.LAMB, "SHOULDER_STEAK", "Shoulder Steak"));
        cutRepository.save(new Cut(AnimalType.LAMB, "CHOPS", "Chops"));
        cutRepository.save(new Cut(AnimalType.LAMB, "RACK", "Rack of Lamb"));
        cutRepository.save(new Cut(AnimalType.LAMB, "RIB", "Rib"));
        cutRepository.save(new Cut(AnimalType.LAMB, "SHANK", "Lamb Shank"));
        cutRepository.save(new Cut(AnimalType.LAMB, "STEW_MEAT", "Stew Meat"));
        cutRepository.save(new Cut(AnimalType.LAMB, "GROUND", "Ground"));
        cutRepository.save(new Cut(AnimalType.LAMB, "LIVER", "Liver"));
        cutRepository.save(new Cut(AnimalType.LAMB, "HEART", "Heart"));
        cutRepository.save(new Cut(AnimalType.LAMB, "KIDNEYS", "Kidneys"));

        //seed cuts for goat
        cutRepository.save(new Cut(AnimalType.GOAT, "LEG_ROAST", "Leg Roast"));
        cutRepository.save(new Cut(AnimalType.GOAT, "LEG_STEAK", "Leg STEAK"));
        cutRepository.save(new Cut(AnimalType.GOAT, "SHOULDER_ROAST", "Shoulder Roast"));
        cutRepository.save(new Cut(AnimalType.GOAT, "SHOULDER_STEAK", "Shoulder Steak"));
        cutRepository.save(new Cut(AnimalType.GOAT, "CHOPS", "Chops"));
        cutRepository.save(new Cut(AnimalType.GOAT, "RACK", "Rack of Lamb"));
        cutRepository.save(new Cut(AnimalType.GOAT, "RIB", "Rib"));
        cutRepository.save(new Cut(AnimalType.GOAT, "SHANK", "Lamb Shank"));
        cutRepository.save(new Cut(AnimalType.GOAT, "STEW_MEAT", "Stew Meat"));
        cutRepository.save(new Cut(AnimalType.GOAT, "GROUND", "Ground"));
        cutRepository.save(new Cut(AnimalType.GOAT, "LIVER", "Liver"));
        cutRepository.save(new Cut(AnimalType.GOAT, "HEART", "Heart"));
        cutRepository.save(new Cut(AnimalType.GOAT, "KIDNEYS", "Kidneys"));


    }
}
