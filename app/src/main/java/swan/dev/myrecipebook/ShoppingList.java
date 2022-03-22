package swan.dev.myrecipebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import swan.dev.myrecipebook.R;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList extends AppCompatActivity {
    //Initalize variable
    RecyclerView recyclerView;
    TextView textView;

    List<Ingredient> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    IngredientAdapter iadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        //Assign Variable
        textView = findViewById(R.id.shoppingList_Text);
        recyclerView = findViewById(R.id.shopping_list_view);

        //Create DataBase
        database = RoomDB.getInstance(getApplicationContext());
        dataList = database.mainDao().getShoppingList();

        //Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(ShoppingList.this);
        //Set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //Initialize adapter
        iadapter = new IngredientAdapter(ShoppingList.this,dataList);
        //Set adapter
        recyclerView.setAdapter(this.iadapter);
    }
}