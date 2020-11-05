package ca.bcit.firebasetodolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText taskEditText;
    EditText nameEditText;
    DatePicker dueDatePicker;
    CheckBox doneCheckBox;
    Button addTodoButton;
    ListView todoListView;
    DatabaseReference todoDatabase;

    List<ToDoItem> toDoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskEditText = findViewById(R.id.taskEditText);
        nameEditText = findViewById(R.id.nameEditText);
        dueDatePicker = findViewById(R.id.dueDatePicker);
        doneCheckBox = findViewById(R.id.doneCheckBox);
        addTodoButton = findViewById(R.id.addTodoButton);
        todoListView = findViewById(R.id.todoListView);
        todoDatabase = FirebaseDatabase.getInstance().getReference("todos");

        addTodoButton.setOnClickListener(view -> addTodo());

        toDoItems = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        todoDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                toDoItems.clear();
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    ToDoItem student = studentSnapshot.getValue(ToDoItem.class);
                    toDoItems.add(student);
                }

                ToDoListAdapter adapter = new ToDoListAdapter(MainActivity.this, toDoItems);
                todoListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void addTodo() {
        String task = taskEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        Date dueDate = getDateFromDatePicker(dueDatePicker);
        boolean done = doneCheckBox.isChecked();

        if (TextUtils.isEmpty(task)) {
            Toast.makeText(this, "You must enter a task.", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "You must enter a name.", Toast.LENGTH_LONG).show();
            return;
        }

        String id = todoDatabase.push().getKey();
        ToDoItem item = new ToDoItem(task, name, dueDate, done);
        Task setValueTask = todoDatabase.child(id).setValue(item);

        setValueTask.addOnSuccessListener(listener -> {
            Toast.makeText(MainActivity.this,"Student added.",Toast.LENGTH_LONG).show();

            taskEditText.setText("");
            nameEditText.setText("");
            doneCheckBox.setChecked(false);
        });

        setValueTask.addOnFailureListener(listener -> Toast.makeText(MainActivity.this,
                "something went wrong.\n" + listener.toString(),
                Toast.LENGTH_SHORT).show());
    }

    private Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}