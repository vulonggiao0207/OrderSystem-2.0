package com.giao.ordersystem;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Long on 5/26/2016.
 */
public class InitData{
    private static Context context;

    public InitData(Context context)
    {
        this.context=context;
    }
    public void InitDatabase()
    {
        TableDAO tableDAO= new TableDAO(this.context);
        CategoryDAO categoryDAO= new CategoryDAO(this.context);
        DishDAO dishDAO= new DishDAO(this.context);
        //Insert default Table
        String []tableArr= {"Table 01","Table 02","Table 03","Table 04","Table 05","Table 06","Table 07","Table 08",
                "Table 09","Table 10","Table 11","Table 12","Table 13","Table 14","Table 15","Table 16","Table 17",
                "Table 18","Table 19","Table 20","Table 21","Table 22","Table 23","Table 24","Table 25"};
        for(int i=0;i<25;i++)
        {
            tableDAO.open();
            tableDAO.create(tableArr[i]);
            tableDAO.close();
        }
        //Insert default Category
        String []categoryArr={"Entrée","Soup","Noodle/Rice","Salad/Vegetarian","Chicken/Duck","Pork","Beef","Prawn","Seafood","Red wine","White Wine",
                "Beer/Soft Drink","Spirit/Cocktails","Coffee/Dessert","Banquet","Special Request"};
        for(int i =0;i<14;i++)
        {
            categoryDAO.open();
            categoryDAO.create(categoryArr[i]);
            categoryDAO.close();
        }
        /////////////////////////////////////ENTREE////////////////////////////////////////////////////
        String []categoryName={"Entrée","Entrée","Entrée","Entrée","Entrée","Entrée","Entrée","Entrée","Entrée","Entrée","Entrée","Entrée","Entrée","Entrée","Entrée","Entrée","Entrée","Entrée"};
        String []dishName={"Crispy Pancake","Vegetarian Crispy Pancake","Spring Rolls",
                "Vegan Spring Rolls","Fresh Prawn Roll","Vegetarian Fresh Roll",
                "Little Rice Cakes","Stuffed Mussels","Stuffed mushroom" ,
                "Grilled Pork Balls Entrée",
                "Grilled Pork Skewerd Entrée","Grilled Beef Roll Entrée","Tender Beef Cubes",
                "Stuffed Chicken Wing Entrée", "Duck cumquat and ginger sauce",
                "Crispy Quail", "Soft Shell Crab"};
        String []dishPrice={"14.5","13.0","9.6","9.0","9.6","9.0","13.5","14.5","14","14.5","14.5","14.5","14.5","17.5","18","11.5","11.5"};
        String []dishDecription={
                "Authentic specialty pancake filled with prawns, pork, bean sprouts and served with salad, pickles and fish sauce. The light and crispy pancake makes this a repeat favourite",
                "Authentic specialty pancake filled with vegetables and bean sprouts and served with salad, pickles and fish sauce. The light and crispy pancake makes this a repeat favourite",
                "Acclaimed, unique Bay Tinh creation, containing quality meat freshly minced, a special mix of black fungus and ingredients, encased in light, crispy pastry.",
                "Acclaimed, unique Bay Tinh creation, containing quality mixed vegetables, black fungus and ingredients, encased in light, crispy pastry.",
                "Lectuce, mint, pork and prawn wrapped between rice paper.",
                "Vegetables, tofu and basil wrapped between rice paper",
                "This traditional Southern dish is rarely served in Australia. It required a skilful technique to create the velvety texture and creamy taste. ",
                "Large mussels stuffed with prawns in oyster sauce, or sweet & sour sauce with chilli.",
                "Steamed dried Shiitake mushrooms stuffed with prawn paste, served with Asian cabbage and oyster sauce.",
                "Chef’s specialty. Marinated pork balls wrapped in lettuce with special rice noodle cakes, pickles, mint and Bay Tinh’s special sauce.",
                "Marinated pork belly wrapped in lettuce with special rice noodle cakes, pickles, mint and Bay Tinh’s special sauce",
                "Fish cake stick wrapped by beef skewed with sliced onion.",
                "Selected yearling beef, marinated with the Chef’s special recipe and flash seared in high flame to seal in the flavour and juices; served with classic pepper, salt and lemon juice.",
                "De-boned wing, stuffed with quality minced pork, black fungus and vermicelli. Twice cooked to a golden brown, with plum sauce and sesame seeds.",
                "Harry’s new luscious sous vide cooked duck with tangy Asian cumquat and ginger sauce to complement and contrast the richness of the duck. ",
                "Marinated in herbs and spices, twice cooked to golden brown, served on shredded salad with classic salt, pepper and lemon juice.",
                "Delicately seasoned, in a very light crispy batter, served with classic lemon, salt and pepper dipping sauce."};
        for(int i=0;i<16;i++)
        {
            dishDAO.open();
            dishDAO.create(categoryName[i],dishName[i],dishPrice[i],dishDecription[i],"0");
            dishDAO.close();
        }
        /////////////////////////////////////SOUP/////////////////////////////////////////////////////////
        String []categoryName1={"Soup","Soup","Soup","Soup","Soup","Soup","Soup","Soup","Soup","Soup","Soup"};
        String []dishName1={"Sweet and Sour Prawn Soup","Sweet and Sour Chicken Soup", "Sweet and Sour Tofu Soup","Chicken & Sweet Corn Soup","Crab & Asparagus Soup"
                ,"Rice Noodle Soup – Hu Tieu My Tho","Combination Steamboat ","Seafood Steamboat ", "Vegetarian Steamboat",
                "Flat rice noodle beef soup","Flat rice noodle chicken soup"};
        String []dishPrice1={"9.0","9.0","8.5","9.0","9.0","9.0","36.0","36.0","36.0","14.0","14.0"};
        String []dishDecription1={"A specialty of Southern Vietnam. Soured with tamarind, fresh tomato, and pineapple; lifted by a wetland herb (unique to Vietnamese food), and finished with okra for colour and texture",
        "A specialty of Southern Vietnam. Soured with tamarind, fresh tomato, and pineapple; lifted by a wetland herb (unique to Vietnamese food), and finished with okra for colour and texture",
        "A specialty of Southern Vietnam. Soured with tamarind, fresh tomato, and pineapple; lifted by a wetland herb (unique to Vietnamese food), and finished with okra for colour and texture",
        "Popular traditional soup with fresh crab and asparagus",
        "Succulent chunks of crab meat in a tasty asparagus soup",
        "From My Tho Province, this special rice noodle soup is topped with prawns, calamari, chicken and pork.",
        "One of the most popular dishes in Vietnamese dining. Cooked in a delicious broth at your table with fresh meats, seafood and vegetables. It is shared by four people as an entrée",
        "One of the most popular dishes in Vietnamese dining. Cooked in a delicious broth at your table with fresh seafood and vegetables. It is shared by four people as an entrée",
        "One of the most popular dishes in Vietnamese dining. Cooked in a delicious broth at your table with fresh tofu and vegetables. It is shared by four people as an entrée",
        "Traditional beef soup with bean sprout and basil",
        "Traditional chicken soup with bean sprout and basil"};
        for(int i=0;i<11;i++)
        {
            dishDAO.open();
            dishDAO.create(categoryName1[i],dishName1[i],dishPrice1[i],dishDecription1[i],"0");
            dishDAO.close();
        }
        /////////////////////////////////////////NOODLE/RICE//////////////////////////////
        String []categoryName2={"Noodle/Rice","Noodle/Rice","Noodle/Rice","Noodle/Rice","Noodle/Rice","Noodle/Rice","Noodle/Rice","Noodle/Rice"};
        String []dishName2={"Crispy Fried Egge Noodle","Soft Egg Noodle", "Rice Noodle","Fried Rice",
                "Mimosa Rice","Garlic Rice (Small)","Garlic Rice (Large)","Steamed Rice"};
        String []dishPrice2={"22.5","22.5","22.5","13.5","10.0","6.5","9.0","2.5"};
        String []dishDecription2={"Crispy Fried Egg Noodles smothered with your choice of vegetables, meat and seafood, or vegetables and seafood, or vegetables only. Chilli optional.",
        "Soft Egg Noodles braised with your choice of vegetables, meat and seafood, or vegetables and seafood, or vegetables only. Chilli optional.",
        "Rice Noodles braised with your choice of vegetables, meat and seafood, or vegetables and seafood, or vegetables only. Chilli optional.",
        "With prawns, pork sausage, egg and peas",
        "Stir-fried rice with chicken, a touch of butter and pepper","","",""};
        for(int i=0;i<8;i++)
        {
            dishDAO.open();
            dishDAO.create(categoryName2[i],dishName2[i],dishPrice2[i],dishDecription2[i],"0");
            dishDAO.close();
        }
        /////////////////////////////////////////////SALAD/VEGETARIAN////////////////////////////////////////////////////////////////////////
        String []categoryName3={"Salad/Vegetarian","Salad/Vegetarian","Salad/Vegetarian","Salad/Vegetarian","Salad/Vegetarian","Salad/Vegetarian","Salad/Vegetarian","Salad/Vegetarian","Salad/Vegetarian","Salad/Vegetarian"};
        String []dishName3={"Duck Salad","Vegetarin Duck Salad","Beef Salad","Papaya Salad","Vegetarian Salad","Bonfire Totu",
                "Tofu sautéed in your choice of tasty sauce (optional chilli)","Salt and Pepper Tofu","Asian Broccoli","Asian Broccoli with Garlic Sauce"};
        String []dishPrice3={"12.0","12.0","15.0","13.80","13.5","26.0","17.9","18.5","12.6","13.5"};
        String []dishDescription3={"Harry’s signature duck salad. Twice cooked with aromatic spices; dressed with lime juice, kaffir lime and special Vietnamese ingredients, served with lightly pickle salad in lettuce leaves, accompanied by prawn crackers. ",
        "Harry’s signature duck salad, with tofu based substitute. Cooked with aromatic spices; dressed with lime juice, kaffir lime and special Vietnamese ingredients, served with lightly pickle salad in lettuce leaves, accompanied by prawn crackers. ",
        "Harry’s new succulent sous vide cooked beef, with green apple and pickle salad, prawn crackers and fish sauce. Serves up to 4 people. ",
        "Sliced prawns and pork, with lightly papaya salad, prawn crackers and fish sauce. Serves up to 4 ",
        "Delicious tofu with lightly pickled salad, herbs, and served with crushed peanuts and soy sauce. ",
        "Tofu, shitake mushroom and onion, cooked in a pot at the table and served with rice paper, lettuce, herbs, pickles and Harry’s hoisin sauce. ",
        "Tomato Sauce, Lemongrass Tofu, Curry Sauce,Sate Sauce ,Mixed Vegetables,Salt & Pepper Tofu",
        "Salt and pepper with lime sauce",
        "Stir-fried with Harry’s special sauce.",
        "Stir-fried with garlic sauce. " };
        for(int i=0;i<10;i++)
        {
            dishDAO.open();
            dishDAO.create(categoryName3[i],dishName3[i],dishPrice3[i],dishDescription3[i],"0");
            dishDAO.close();
        }
        ///////////////////////////////////CHICKEN/DUCK//////////////////////////////////////////////////////////
        String []categoryName4={"Chicken/Duck","Chicken/Duck","Chicken/Duck","Chicken/Duck","Chicken/Duck","Chicken/Duck","Chicken/Duck","Chicken/Duck","Chicken/Duck","Chicken/Duck","Chicken/Duck","Chicken/Duck","Chicken/Duck","Chicken/Duck","Chicken/Duck"};
        String []dishName4={"Braised Duck with Peas","Ballotine of Chicken (quarter)","Ballotine of Chicken (half)","Red curry Chicken"
        ,"Crispy roaste Chicken","Curry Chicken","Sate Chicken","Sweet and sour Chiken",
        "Ginger Chicken","Lemongrass Chicken","Chicken with Cashew nuts","Chicken with Mixed Vegetables",
        "Chicken with Snow Peas","Bonfire Chicken"};
        String []dishPrice4={"23.8","18.5","28.5","20.5","20.5","20.8","20.8","20.8","20.8","20.8","20.8","20.8","20.8","26.0"};
        String []dishDescription4={"Twice cooked with aromatic spices, braised with mixed vegetables and peas.",
        "The chicken is deboned in a very precise way, stuffed with a delicious mixture including minced pork and fragrant spices. The Chicken is then slow cooked to develop the delicious flavour and tender texture before baking. Served with a rich plum sauce and sesame. ",
                "The chicken is deboned in a very precise way, stuffed with a delicious mixture including minced pork and fragrant spices. The Chicken is then slow cooked to develop the delicious flavour and tender texture before baking. Served with a rich plum sauce and sesame. ",
                "Aromatic casserole of chicken in tasty Vietnamese style red curry and lemongrass. ",
                "Half chicken, twice cooked with aromatic spices, served with classic salt, pepper and lemon dipping sauce. ",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "Premium meat or seafood cooked at your table in a pot with caramelized onion, infused with the flavours of coriander and peanut, served with rice paper, lettuce, herbs, pickles and Harry’s special anchovy sauce."};
        for(int i=0;i<14;i++)
        {
            dishDAO.open();
            dishDAO.create(categoryName4[i],dishName4[i],dishPrice4[i],dishDescription4[i],"0");
            dishDAO.close();
        }
        /////////////////////////////////PORK///////////////////////////////////////////////////
        String []categoryName5={"Pork","Pork","Pork","Pork","Pork","Pork","Pork","Pork"};
        String []dishName5={"Grilled Pork Chop","Curry Pork","Sate Pork",
        "Sweet and Sour Pork","Caramelized Pork","Stuffed Tofu",
        "Grilled Pork Skewer","Grilled Pork Balls"};
        String []dishPrice5={"22.5","20.5","20.5","20.5","20.5","20.5","26.6","22.5"};
        String []dishDescription5={"Marinated pork slices with Harry’s unique recipe, grilled and served on a salad bed",
                "",
                "",
                "",
                "",
                "House’s specialty – Golden fried tofu stuffed with premium minced pork, black fungus and vermicelli, served house made tomato sauce. ",
                "Succulent pork slices marinated with Harry’s unique recipe, grilled on a skewer. This taste sensation is presented in lettuce with special rice noodle cakes, pickles, mint and Bay Tinh’s special sauce. ",
                "Chef’s specialty. Marinated pork balls wrapped in lettuce with special rice noodle cakes, pickles, mint and Bay Tinh’s special sauce." };
        for(int i=0;i<8;i++)
        {
            dishDAO.open();
            dishDAO.create(categoryName5[i],dishName5[i],dishPrice5[i],dishDescription5[i],"0");
            dishDAO.close();
        }
        //////////////////////////////BEEF/////////////////////////////////////////////////
        String []categoryName6={"Beef","Beef","Beef","Beef","Beef","Beef","Beef"};
        String []dishName6={"Curry Beef","Sate Beef","Ginger Beef","Sweet and Sour Beef",
        "Beef with Mixed Vegetables","Beef with Snow Peas","Bonfire Beef"};
        String []dishPrice6={"21.7","21.7","21.7","21.7","21.7","21.7","28.0"};
        String []dishDescription6={"","","","","",""
        ,"Premium meat or seafood cooked at your table in a pot with caramelized onion, infused with the flavours of coriander and peanut, served with rice paper, lettuce, herbs, pickles and Harry’s special anchovy sauce."};
        for(int i=0;i<7;i++)
        {
            dishDAO.open();
            dishDAO.create(categoryName6[i],dishName6[i],dishPrice6[i],dishDescription6[i],"0");
            dishDAO.close();
        }
        ///////////////////////////////PRAWN////////////////////////////////////////////////
        String []categoryName7={"Prawn","Prawn","Prawn","Prawn","Prawn","Prawn","Prawn","Prawn","Prawn","Prawn"};
        String []dishName7={"King Prawn with Salt","King Prawn with Red Sauce","Prawn with Mixed Vegetables","Sate Prawn",
        "Curry Prawn","Garlic Prawn","King Prawn wrapped Sugar Cane","King Prawn wih Crispy Rice","Bonfire Chicken and Prawn","Bonfire Beef and Prawn"};
        String []dishPrice7={"26.5","22.5","22.5","22.5","22.5","22.5","26.5","8.0","28.0","28.0"};
        String []dishDescription7={"Fresh king prawns, deep fried in the shell, and served with classic salt, pepper and lemon sauce. ",
        "Marinated and braised in red sauce with herbs and spices",
        "",
        "",
        "",
        "",
        "Chef’s specialty. Marinated prawn paste, wrapped around sugar cane, served in lettuce, with special rice noodle cakes, pickles, mint and Bay Tinh’s special sauce. ",
        "Deep fried King prawn covered by crispy young rice, come with lime sauce",
        "Premium meat or seafood cooked at your table in a pot with caramelized onion, infused with the flavours of coriander and peanut, served with rice paper, lettuce, herbs, pickles and Harry’s special anchovy sauce.",
        "Premium meat or seafood cooked at your table in a pot with caramelized onion, infused with the flavours of coriander and peanut, served with rice paper, lettuce, herbs, pickles and Harry’s special anchovy sauce."};
        for(int i=0;i<10;i++)
        {
            dishDAO.open();
            dishDAO.create(categoryName7[i],dishName7[i],dishPrice7[i],dishDescription7[i],"0");
            dishDAO.close();
        }
        /////////////////////////////////SEAFOOD////////////////////////////////////////////////////
        String []categoryName8={"Seafood","Seafood","Seafood","Seafood","Seafood","Seafood","Seafood"};
        String []dishName8={"Salt and Pepper Calamari (Main)","Stuffed Calamari","Scallop with Shallots",
        "Caramelized Fish","Deep fried Snapper","Steamed snapper","Combination Seafood"};
        String []dishPrice8={"20.9","22.5","22.5","24.5","35.0","35.0","25.5"};
        String []dishDescription8={"Arguably the best in Sydney! Deep-fried to a taste sensation, served with a classic salt, pepper and lemon dipping sauce, or tamarind dipping sauce. ",
        "Two whole steamed calamari stuffed with special mixed pork paste, with your choice of sauce – oyster, tamarind sweet & sour or curry. ",
                "Stir-fried with soy sauce and onion. ",
                "Salmon cutlet slow-cooked in a clay pot filled with a rich caramelized sauce, escorted by thin slices of marinated pork – a must for those wanting to experience the traditional flavours of a Vietnamese dish. ",
                "Fresh Snapper served with Ginger or Tamarind Sauce.Flash fried for crispy skin and moist flesh.",
                "Steamed fresh Snapper served with Oyster sauce",
                "King prawns, calamari and fish stir fried with green vegetables and oyster sauce. "};
        for(int i=0;i<7;i++)
        {
            dishDAO.open();
            dishDAO.create(categoryName8[i],dishName8[i],dishPrice8[i],dishDescription8[i],"0");
            dishDAO.close();
        }
    }

}
