package ca.bcit.myworld;

import java.util.ArrayList;

public class Country {
    private String _continent;
    private String _description;
    private String _name;
    private int _imageResourceId;

    public static final String[] continents = {
            "Africa", "Asia", "Europe", "North America", "South America"
    };

    public static final Country[] northAmericaCountries = {
            new Country("North America", "Canada", "Canada is a country in the northern part of North America. Its ten provinces and three territories extend from the Atlantic to the Pacific and northward into the Arctic Ocean, covering 9.98 million square kilometres (3.85 million square miles), making it the world's second-largest country by total area and the fourth-largest country by land area. Canada's southern border with the United States is the world's longest bi-national land border. The majority of the country has a cold or severely cold winter climate, but southerly areas are warm in summer. Canada is sparsely populated, the majority of its land territory being dominated by forest and tundra and the Rocky Mountains. It is highly urbanized with 82 per cent of the 35.15 million people concentrated in large and medium-sized cities, many near the southern border. Its capital is Ottawa, and its largest metropolitan areas are Toronto, Montreal and Vancouver.", R.drawable.canada),

            new Country("North America", "USA", "The United States of America (USA), commonly known as the United States (U.S.) or America is a federal republic composed of 50 states, a federal district, five major self-governing territories, and various possessions. Forty-eight states and the federal district are contiguous and located in North America between Canada and Mexico. The state of Alaska is in the northwest corner of North America, bordered by Canada to the east and across the Bering Strait from Russia to the west. The state of Hawaii is an archipelago in the mid-Pacific Ocean. The U.S. territories are scattered about the Pacific Ocean and the Caribbean Sea, stretching across nine official time zones. The extremely diverse geography, climate and wildlife of the United States make it one of the world's 17 megadiverse countries.", R.drawable.usa),

            new Country("North America", "Mexico", "Mexico , officially the United Mexican States (Spanish: Estados Unidos Mexicanos,  listen (help�info)), is a federal republic in the southern portion of North America. It is bordered to the north by the United States; to the south and west by the Pacific Ocean; to the southeast by Guatemala, Belize, and the Caribbean Sea; and to the east by the Gulf of Mexico. Covering almost two million square kilometers (over 760,000 sq mi),Mexico is the sixth largest country in the Americas by total area and the 13th largest independent nation in the world.\n" +
                    "With an estimated population of over 120 million, Mexico is the eleventh most populous country and the most populous Spanish-speaking country in the world while being the second most populous country in Latin America. Mexico is a federation comprising 31 states and a special federal entity that is also its capital and most populous city. Other metropolises include Guadalajara, Le�n, Monterrey, Puebla, Toluca, and Tijuana.", R.drawable.mexico),

            new Country("South America", "Argentina", "Argentina is a country located mostly in the southern half of South America. Sharing the bulk of the Southern Cone with Chile to the west, the country is also bordered by Bolivia and Paraguay to the north, Brazil to the northeast, Uruguay and the South Atlantic Ocean to the east, and the Drake Passage to the south.", R.drawable.argentina),

            new Country("South America", "Brazil", "Brazil is the largest country in both South America and Latin America. At 8.5 million square kilometers (3.2 million square miles) and with over 211 million people, Brazil is the world's fifth-largest country by area and the sixth most populous.", R.drawable.brazil),

            new Country("Asia", "China", "China, officially the People's Republic of China (PRC), is the world's most populous country, with a population of around 1.4 billion in 2019. Covering approximately 9.6 million square kilometers (3.7 million mi2), it is the world's third or fourth-largest country by area.", R.drawable.china),

            new Country("Africa", "Djibouti", "Djibouti is a country located in the Horn of Africa in Africa. It is bordered by Somalia in the south, Ethiopia in the south and west, Eritrea in the north, and the Red Sea and the Gulf of Aden in the east. Across the Gulf of Aden lies Yemen. The country has a total area of 23,200 km2 (8,958 sq mi). The Republic of Djibouti is predominantly inhabited by two ethnic groups, the Somali and the Afar people, with the former comprising the majority of the population.", R.drawable.djibouti),

            new Country("Africa", "Ethiopia", "Ethiopia is a landlocked country in Horn of Africa. It shares borders with Eritrea to the north, Djibouti to the northeast, Somalia to the east, Kenya to the south, South Sudan to the west and Sudan to the northwest. With over 109 million inhabitants as of 2019, Ethiopia is the 12th most populous country in the world, the second most populous nation on the African continent (after Nigeria), and most populous landlocked country in the world.", R.drawable.ethiopia),

            new Country("Europe", "France", "France is a country consisting of metropolitan France in Western Europe and several overseas regions and territories. The metropolitan area of France extends from the Rhine to the Atlantic Ocean and from the Mediterranean Sea to the English Channel and the North Sea. It borders Belgium, Luxembourg and Germany to the northeast, Switzerland, Monaco and Italy to the east and Andorra and Spain to the south.", R.drawable.france),

            new Country("Europe", "Germany", "Germany is a country in Central and Western Europe. Covering an area of 357,022 square kilometres (137,847 sq mi), it lies between the Baltic and North seas to the north, and the Alps to the south. It borders Denmark to the north, Poland and the Czech Republic to the east, Austria and Switzerland to the south, and France, Luxembourg, Belgium and the Netherlands to the west.", R.drawable.germany),

            new Country("Africa", "Ghana", "Ghana is a country along the Gulf of Guinea and the Atlantic Ocean, in the subregion of West Africa. Spanning a land mass of 238,535 km2 (92,099 sq mi), Ghana is bordered by the Ivory Coast in the west, Burkina Faso in the north, Togo in the east, and the Gulf of Guinea and Atlantic Ocean in the south. Ghana means 'Warrior King' in the Soninke language.", R.drawable.ghana),

            new Country("Europe", "Italy", "Italy is a country consisting of a peninsula delimited by the Alps and surrounded by several islands. Italy is located in south-central Europe, and is considered part of western Europe. A unitary parliamentary republic with Rome as its capital, the country covers a total area of 301,340 km2 (116,350 sq mi) and shares land borders with France, Switzerland, Austria, Slovenia, and the enclaved microstates of Vatican City and San Marino.", R.drawable.italy),

            new Country("Africa", "Morocco", "Morocco  is a country located in the Maghreb region of North Africa. It overlooks the Mediterranean Sea to the north and the Atlantic Ocean to the west, with land borders with Algeria to the east and Western Sahara (status disputed) to the south.", R.drawable.morocco),

            new Country("Africa", "Nigeria", "Nigeria is a sovereign country in West Africa bordering Niger in the north, Chad in the northeast, Cameroon in the east, and Benin in the west. Its southern coast is on the Gulf of Guinea in the Atlantic Ocean. Nigeria is a federal republic comprising 36 states and the Federal Capital Territory, where the capital, Abuja, is located. Lagos is the most populous city in the country and the African continent, as well as one of the largest metropolitan areas in the world.", R.drawable.nigeria),

            new Country("South America", "Peru", "Peru is a country in western South America. It is bordered in the north by Ecuador and Colombia, in the east by Brazil, in the southeast by Bolivia, in the south by Chile, and in the west by the Pacific Ocean. Peru is a megadiverse country with habitats ranging from the arid plains of the Pacific coastal region in the west to the peaks of the Andes mountains vertically extending from the north to the southeast of the country to the tropical Amazon Basin rainforest in the east with the Amazon river. At 1.28 million km2 (0.5 million mi2), Peru is the 19th largest country in the world, and the third largest in South America.", R.drawable.peru),

            new Country("Asia", "Philippines", "Philippines is an archipelagic country in Southeast Asia. Situated in the western Pacific Ocean, it consists of about 7,641 islands that are broadly categorized under three main geographical divisions from north to south: Luzon, Visayas, Mindanao. The capital city of the Philippines is Manila and the most populous city is Quezon City, both within the single urban area of Metro Manila. Bounded by the South China Sea on the west, the Philippine Sea on the east and the Celebes Sea on the southwest, the Philippines shares maritime borders with Taiwan to the north, Japan to the northeast, Palau to the east, Indonesia to the south, Malaysia and Brunei to the southwest, Vietnam to the west, and China to the northwest.", R.drawable.philippines),

            new Country("Europe", "Spain", "Spain is a country in Southwestern Europe with some pockets of territory across the Strait of Gibraltar and the Atlantic Ocean. Its continental European territory is situated on the Iberian Peninsula. Its territory also includes two archipelagos: the Canary Islands off the coast of North Africa, and the Balearic Islands in the Mediterranean Sea. The African enclaves of Ceuta, Melilla, and Pe��n de V�lez de la Gomera, makes Spain the only European country to have a physical border with an African country (Morocco).", R.drawable.spain),

            new Country("Asia", "Thailand", "Thailand  is a country in Southeast Asia. Located at the centre of the Indochinese Peninsula, it is composed of 76 provinces, and covers an area of 513,120 square kilometres (198,120 sq mi), and a population of over 66 million people. Thailand is the world's 50th-largest country by land area, and the 22nd-most-populous country in the world. The capital and largest city is Bangkok, a special administrative area. Thailand is bordered to the north by Myanmar and Laos, to the east by Laos and Cambodia, to the south by the Gulf of Thailand and Malaysia, and to the west by the Andaman Sea and the southern extremity of Myanmar. Its maritime boundaries include Vietnam in the Gulf of Thailand to the southeast, and Indonesia and India on the Andaman Sea to the southwest.", R.drawable.thailand),

    };

    // Each country has a name, description and an image resource
    private Country(String continent, String name, String description, int imageResourceId) {
        _continent = continent;
        _name = name;
        _description = description;
        _imageResourceId = imageResourceId;
    }

    public static Country[] getCountriesByContinent(String continent) {
        ArrayList<Country> countries = new ArrayList<Country>();
        for (int i = 0; i < northAmericaCountries.length; i++) {
            Country c = northAmericaCountries[i];
            if (continent.toLowerCase().trim().equals(c.getContinent().toLowerCase())) {
                countries.add(c);
            }
        }
        return countries.toArray(new Country[]{});
    }

    public static Country getCountryByName(String countryName) {
        Country result = null;
        for (int i = 0; i < northAmericaCountries.length; i++) {
            Country c = northAmericaCountries[i];
            if (countryName.toLowerCase().trim().equals(c.getName().toLowerCase())) {
                result = c;
                break;
            }
        }
        return result;
    }

    public String getName() {
        return _name;
    }

    public String getContinent() {
        return _continent;
    }

    public String getDescription() {
        return _description;
    }

    public int getImageResourceId() {
        return _imageResourceId;
    }

    public String toString() {
        return _name;
    }
}
