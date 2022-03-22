package swan.dev.myrecipebook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import swan.dev.myrecipebook.R;

public class ViewRecipe extends AppCompatActivity {

    private RoomDB database;
    private MainData mainData;

    private TextView description;
    private TextView ingredients;
    private TextView steps;

    ListView listView;
    String[] ingredientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        //Create DataBase
        database = RoomDB.getInstance(getApplicationContext());

        //Get the Intent and set the Recipe Id
        Intent getIntent = getIntent();
        String recipe_idS = getIntent.getStringExtra("RECIPE_ID");
        int recipe_id = Integer.parseInt(recipe_idS);

        //Create the mainData Object
        mainData = database.mainDao().getData(recipe_id);

        //Populate the TextViews
        description = findViewById(R.id.dText);
        ingredients = findViewById(R.id.iText);
        steps = findViewById(R.id.sText);

        description.setText(mainData.getText());
        ingredients.setText("Ingredients");
        steps.setText(mainData.getSteps());

        ingredientList = mainData.getIngredient().split("\\r\\n|[\\n\\x0B\\x0C\\r\\u0085\\u2028\\u2029]");

        //Find listview
        listView = findViewById(R.id.listView);
        // now create an adapter class

        MyAdapter adapter = new MyAdapter(this, ingredientList);
        listView.setAdapter(adapter);

        // now set item click on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //add String to DB
                Toast.makeText(ViewRecipe.this, ingredientList[position], Toast.LENGTH_SHORT).show();

                Ingredient ingredient = new Ingredient(ingredientList[position]);
                database.mainDao().insert(ingredient);
            }
        });

    }

    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String rIngredientList[];

        MyAdapter (Context c, String ingredientList[]){
            super(c, R.layout.row,ingredientList);// CHECK THIS CHECK THIS CHECK THIS
            this.context = c;
            this.rIngredientList = ingredientList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.img_add);
            TextView ingredient = row.findViewById(R.id.row_text);

            //Set Resources
            ingredient.setText(rIngredientList[position]);


            return row;
        }
    }
}