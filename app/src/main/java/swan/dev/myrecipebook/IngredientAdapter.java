package swan.dev.myrecipebook;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import swan.dev.myrecipebook.R;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder>{
    //Initialize variable
    private List<Ingredient> dataList;
    private Activity context;
    private RoomDB database;


    //Create constructor
    public IngredientAdapter(Activity context,List<Ingredient> dataList){
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_shopping, parent,false);
        return new IngredientAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
        //Initialize Ingredient data
        Ingredient ingredient = dataList.get(position);
        //Initialize database
        database = RoomDB.getInstance(context);
        //Set text on text view
        holder.textView.setText(ingredient.getText());


        holder.btDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Initialize Ingredient data
                Ingredient i = dataList.get(holder.getBindingAdapterPosition());
                //Delete text from database
                database.mainDao().delete(i);
                //Notify when data is deleted
                int position = holder.getBindingAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //Initialize variable
        TextView textView;
        ImageView btDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            textView = itemView.findViewById(R.id.shoppingList_Text);
            btDelete = itemView.findViewById(R.id.bt_delete_text);
        }
    }
}
