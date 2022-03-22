package swan.dev.myrecipebook;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

import java.util.List;

@Dao
public interface MainDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    @Insert(onConflict = REPLACE)
    void insert(Ingredient ingredient);

    //Delete query
    @Delete
    void delete(MainData mainData);

    //Delete query
    @Delete
    void delete(Ingredient ingredient);

    //Delete all query
    @Delete
    void reset(List<MainData> mainData);

    //Update query
    @Query("UPDATE table_name SET text = :sText WHERE ID = :sID")
    void update(int sID,String sText);

    //Update query
    @Query("UPDATE table_name SET text = :sText, steps = :stText ,ingredients = :iText  WHERE ID = :sID")
    void update(int sID,String sText, String iText, String stText);

    //Get all data query
    @Query("SELECT * FROM table_name")
    List<MainData> getAll();

    //Get all shopping list
    @Query("SELECT * FROM shopping_list")
    List<Ingredient> getShoppingList();

    //Get a dataObject
    @Query("SELECT * FROM table_name WHERE ID = :id")
    MainData getData(int id);
}
