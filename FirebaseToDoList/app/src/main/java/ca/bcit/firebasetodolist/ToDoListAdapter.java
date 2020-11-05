package ca.bcit.firebasetodolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ToDoListAdapter extends ArrayAdapter<ToDoItem> {
    private final Context context;
    private final List<ToDoItem> toDoItems;

    public ToDoListAdapter(Context context, List<ToDoItem> toDoItems) {
        super(context, R.layout.list_layout, toDoItems);
        this.context = context;
        this.toDoItems = toDoItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_layout, null, true);
        }

        TextView text1 = convertView.findViewById(R.id.text1);
        TextView text2 = convertView.findViewById(R.id.text2);

        ToDoItem item = toDoItems.get(position);
        text1.setText(item.getTask() + " " + item.getWho());
        text2.setText(item.getFormattedDueDate() + " " + (item.isDone() ? "DONE" : "NOT DONE"));

        return convertView;
    }

}
