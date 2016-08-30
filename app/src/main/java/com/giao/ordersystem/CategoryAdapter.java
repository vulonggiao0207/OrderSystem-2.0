package com.giao.ordersystem;

/**
 * Created by Long on 2/12/2016.
 */
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<CategoryBO> data;
    private static LayoutInflater inflater = null;
    private TextView mText;
    private Button mButton;


    public CategoryAdapter(Context context, ArrayList<CategoryBO> data) {
        // TODO Auto-generated constructor stub

        this.context = context;
        this.data = new ArrayList<CategoryBO>(data);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public CategoryBO getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.listview_layout2, null, true);
        }
        CategoryBO temp = (CategoryBO) data.get(position);
        mText = (TextView) vi.findViewById(R.id.EditCategoryEditText);
        mButton = (Button) vi.findViewById(R.id.DeleteCategoryButton);
        mText.setText(temp.getCategoryName());
        mButton.setTag(position);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (Integer) v.getTag();
                CategoryBO category = data.get(index);
                data.remove(category);
                notifyDataSetChanged();
            }
        });
        return vi;
 /*       // TODO Auto-generated method stub
        final Holder holder= new Holder();
        final long mTextLostFocusTimestamp = -1;
        View vi;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.listview_layout2, null, true);
        }
        else {

            vi = convertView;
            holder.edittext = (EditText) vi.findViewById(R.id.EditCategoryEditText);
            mText=(EditText) vi.findViewById(R.id.EditCategoryEditText);
            holder.button = (Button) vi.findViewById(R.id.DeleteCategoryButton);
            CategoryBO temp = (CategoryBO) data.get(position);
            holder.edittext.setText(temp.getCategoryName());

            holder.button.setTag(temp.getCategoryName());
            holder.button.setTag(position);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = (Integer) v.getTag();
                    CategoryBO category = data.get(index);
                    data.remove(category);
                    notifyDataSetChanged();
                }
            });
            holder.edittext.setOnFocusChangeListener(this);
            vi.setTag(holder);
        }
        return vi;*/
    }
    //   public class Holder{EditText edittext; Button button;}

    //This Listener used to keep enable the edit mode of EditText
    /*If focus was on a different view, then that view loses focus
    2. the target gains focus
    3. soft keyboard pops up.
            4. this causes the target to lose focus
    5. the code detects this situation and calls target.requestFocus()
    6. the leftmost, topmost view gains focus, due to Android nonsense
    7. the leftmost view loses focus, due to requestFocus being called
    8. target finally gains focus*/
 /*   @Override
    public void onFocusChange(View v, boolean hasFocus) {
        long t = System.currentTimeMillis();
        long delta = t - focusTime;
        if (hasFocus) {     // gained focus
            if (delta > minDelta) {
                focusTime = t;
                focusTarget = v;
            }
        }
        else {              // lost focus
            if (delta <= minDelta  &&  v == focusTarget) {
                focusTarget.post(new Runnable() {   // reset focus to target
                    public void run() {
                        focusTarget.requestFocus();
                    }
                });
            }
        }
    }*/

}
