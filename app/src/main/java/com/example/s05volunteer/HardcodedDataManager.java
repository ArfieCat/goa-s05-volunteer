package com.example.s05volunteer;

import java.util.ArrayList;

// Singleton for generating and holding the data so it doesn't clog the Activities
public final class HardcodedDataManager {
    private static final HardcodedDataManager INSTANCE = new HardcodedDataManager();
    ArrayList<VolunteerOpportunity> data;

    // Disallow public instantiation
    private HardcodedDataManager() {
        generateData();
    }

    public static HardcodedDataManager getInstance() {
        return INSTANCE;
    }

    private void generateData() {
        data = new ArrayList<>();
        data.add(new VolunteerOpportunity(
                "ABC Food Bank",
                "ABC Food Bank provides free meals anyone in need in the community.",
                "We rely heavily on volunteers to help us achieve this.",
                "3:00PM - 5:00PM, every Friday",
                "123 ABC Street, Richmond, BC",
                "abcfoodbank@email.com",
                R.drawable.logo1,
                R.drawable.org1
        ));
        data.add(new VolunteerOpportunity(
                "DEF Run for Health",
                "DEF Run for Health raises money for medical research.",
                "Join our 10k run and support this meaningful cause.",
                "10:00AM, Thursday, December 16",
                "456 DEF Street, Richmond, BC",
                "defrunforhealth@email.com",
                R.drawable.logo2,
                R.drawable.org2
        ));
        data.add(new VolunteerOpportunity(
                "GHI Homes",
                "GHI Homes helps build homes for families in poverty-stricken locations worldwide.",
                "Join us for a summer or support us with your donations.",
                "July - August 2022",
                "789 Rue GHI, Quebec, QC",
                "ghihomes@email.com",
                R.drawable.logo3,
                R.drawable.org3
        ));
    }
}
