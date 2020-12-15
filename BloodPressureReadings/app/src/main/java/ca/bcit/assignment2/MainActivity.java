package ca.bcit.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    static final String FIREBASE_REF_PATH = "BloodPressureReadings";

    Spinner familyMemberSpinner;
    EditText systolicEditText;
    EditText diastolicEditText;
    Button addButton;
    Button reportButton;
    ListView readingsListView;

    DatabaseReference databaseRef;
    ArrayList<BloodPressureReading> allBloodPressureReadings = new ArrayList<>();
    ArrayList<BloodPressureReading> displayedBloodPressureReadings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.title_name);

        familyMemberSpinner = findViewById(R.id.familyMemberSpinner);
        systolicEditText = findViewById(R.id.systolicEditText);
        diastolicEditText = findViewById(R.id.diastolicEditText);
        addButton = findViewById(R.id.addButton);
        reportButton = findViewById(R.id.reportButton);
        readingsListView = findViewById(R.id.readingsListView);

        databaseRef = FirebaseDatabase.getInstance().getReference(FIREBASE_REF_PATH);

        addButton.setOnClickListener(v -> addReading());
        reportButton.setOnClickListener(v -> showReport());

        // long press to edit/delete an item from the list
        readingsListView.setOnItemLongClickListener((parent, view, position, id) -> {
            BloodPressureReading reading = displayedBloodPressureReadings.get(position);
            showUpdateDialog(reading);
            return false;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        familyMemberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                displayReadings();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // fetches readings from firebase
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allBloodPressureReadings.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BloodPressureReading reading = snapshot.getValue(BloodPressureReading.class);
                    reading.setId(snapshot.getKey());
                    allBloodPressureReadings.add(reading);
                }
                displayReadings();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("--- FIREBASE ---", "Failed to read value.", databaseError.toException());
            }
        });
    }

    private void displayReadings() {
        displayedBloodPressureReadings.clear();
        String familyMember = familyMemberSpinner.getSelectedItem().toString();
        for (BloodPressureReading reading : allBloodPressureReadings) {
            if (familyMember.equals(reading.getFamilyMember())) {
                displayedBloodPressureReadings.add(reading);
            }
        }

        ReadingsAdapter adapter = new ReadingsAdapter(MainActivity.this, displayedBloodPressureReadings);
        readingsListView.setAdapter(adapter);
    }

    private void addReading() {
        String systolicText = systolicEditText.getText().toString().trim();
        String diastolicText = diastolicEditText.getText().toString().trim();

        if (TextUtils.isEmpty(systolicText)) {
            Toast.makeText(this, R.string.missing_systolic_message, Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(diastolicText)) {
            Toast.makeText(this, R.string.missing_diastolic_message, Toast.LENGTH_LONG).show();
            return;
        }

        try {
            String familyMember = familyMemberSpinner.getSelectedItem().toString();
            int systolic = Integer.parseInt(systolicText);
            int diastolic = Integer.parseInt(diastolicText);
            String recordId = databaseRef.push().getKey();
            BloodPressureReading bloodPressureReading = new BloodPressureReading(recordId, familyMember, systolic, diastolic);
            if (bloodPressureReading.getCondition() == BloodPressureReading.Condition.HYPERTENSIVE) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.warning)
                        .setMessage(R.string.hypertensive_warning_message)
                        .setPositiveButton(android.R.string.ok, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            Task setValueTask = databaseRef.child(recordId).setValue(bloodPressureReading);

            setValueTask.addOnSuccessListener(listener -> {
                Toast.makeText(MainActivity.this,
                        R.string.reading_added_message, Toast.LENGTH_LONG).show();

                systolicEditText.setText("");
                diastolicEditText.setText("");
            });

            setValueTask.addOnFailureListener(listener -> Toast.makeText(MainActivity.this,
                    getResources().getText(R.string.general_error_message) + "\n" + listener.toString(),
                    Toast.LENGTH_SHORT).show());
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.invalid_input_message, Toast.LENGTH_LONG).show();
        }
    }

    private void updateReading(String id, EditText systolicEditText, EditText diastolicEditText, AlertDialog alertDialog) {
        String systolicText = systolicEditText.getText().toString().trim();
        String diastolicText = diastolicEditText.getText().toString().trim();

        if (TextUtils.isEmpty(systolicText)) {
            systolicEditText.setError(getResources().getText(R.string.missing_systolic_message));
            return;
        } else if (TextUtils.isEmpty(diastolicText)) {
            diastolicEditText.setError(getResources().getText(R.string.missing_diastolic_message));
            return;
        }

        try {
            String familyMember = familyMemberSpinner.getSelectedItem().toString();
            int systolic = Integer.parseInt(systolicText);
            int diastolic = Integer.parseInt(diastolicText);
            DatabaseReference dbRef = databaseRef.child(id);
            BloodPressureReading bloodPressureReading = new BloodPressureReading(id, familyMember, systolic, diastolic);
            Task setValueTask = dbRef.setValue(bloodPressureReading);

            setValueTask.addOnSuccessListener(listener -> {
                Toast.makeText(MainActivity.this,
                        R.string.reading_updated_message, Toast.LENGTH_LONG).show();

                systolicEditText.setText("");
                diastolicEditText.setText("");
            });

            setValueTask.addOnFailureListener(listener -> Toast.makeText(MainActivity.this,
                    getResources().getText(R.string.general_error_message) + "\n" + listener.toString(),
                    Toast.LENGTH_SHORT).show());

            alertDialog.dismiss();
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.invalid_input_message, Toast.LENGTH_LONG).show();
        }
    }

    private void deleteReading(String id) {
        DatabaseReference dbRef = databaseRef.child(id);
        Task setRemoveTask = dbRef.removeValue();

        setRemoveTask.addOnSuccessListener(listener -> {
            Toast.makeText(MainActivity.this,
                    R.string.reading_deleted_message, Toast.LENGTH_LONG).show();

            systolicEditText.setText("");
            diastolicEditText.setText("");
        });

        setRemoveTask.addOnFailureListener(listener -> Toast.makeText(MainActivity.this,
                getResources().getText(R.string.general_error_message) + "\n" + listener.toString(),
                Toast.LENGTH_SHORT).show());
    }

    private void showUpdateDialog(BloodPressureReading reading) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View dialogView = getLayoutInflater().inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText systolicEditText = dialogView.findViewById(R.id.systolicEditText);
        final EditText diastolicEditText = dialogView.findViewById(R.id.diastolicEditText);
        final Button updateButton = dialogView.findViewById(R.id.updateButton);
        final Button deleteButton = dialogView.findViewById(R.id.deleteButton);

        systolicEditText.setText(Integer.toString((int) reading.getSystolic()));
        diastolicEditText.setText(Integer.toString((int) reading.getDiastolic()));

        dialogBuilder.setTitle(R.string.update_dialog_title);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        updateButton.setOnClickListener(v -> updateReading(reading.getId(), systolicEditText, diastolicEditText, alertDialog));
        deleteButton.setOnClickListener(v -> {
            deleteReading(reading.getId());
            alertDialog.dismiss();
        });
    }

    private Pair<Double, Double> getAverageSystolicDiastolic(List<BloodPressureReading> readings, String member) {
        List<BloodPressureReading> filteredReadings = readings.stream()
                .filter(x -> x.getFamilyMember().equals(member))
                .collect(Collectors.toList());

        double fatherSystolic = filteredReadings.isEmpty() ? -1 :
                filteredReadings.stream().mapToDouble(BloodPressureReading::getSystolic).sum() / filteredReadings.size();
        double fatherDiastolic = filteredReadings.isEmpty() ? -1 :
                filteredReadings.stream().mapToDouble(BloodPressureReading::getDiastolic).sum() / filteredReadings.size();

        return new Pair<>(fatherSystolic, fatherDiastolic);
    }

    private void showReport() {
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        List<BloodPressureReading> readingsByMonth = allBloodPressureReadings.stream().filter(x -> {
            calendar.setTime(x.getRecordedDate());
            return calendar.get(Calendar.MONTH) == currentMonth;
        }).collect(Collectors.toList());

        String[] members = getResources().getStringArray(R.array.familyMember);
        Pair<Double, Double> fatherData = getAverageSystolicDiastolic(readingsByMonth, members[0]);
        Pair<Double, Double> motherData = getAverageSystolicDiastolic(readingsByMonth, members[1]);
        Pair<Double, Double> grandmaData = getAverageSystolicDiastolic(readingsByMonth, members[2]);
        Pair<Double, Double> grandpaData = getAverageSystolicDiastolic(readingsByMonth, members[3]);

        Intent i = new Intent(this, ReportActivity.class);
        i.putExtra(ReportActivity.FATHER_SYSTOLIC_KEY, fatherData.first);
        i.putExtra(ReportActivity.FATHER_DIASTOLIC_KEY, fatherData.second);
        i.putExtra(ReportActivity.MOTHER_SYSTOLIC_KEY, motherData.first);
        i.putExtra(ReportActivity.MOTHER_DIASTOLIC_KEY, motherData.second);
        i.putExtra(ReportActivity.GRANDPA_SYSTOLIC_KEY, grandpaData.first);
        i.putExtra(ReportActivity.GRANDPA_DIASTOLIC_KEY, grandpaData.second);
        i.putExtra(ReportActivity.GRANDMA_SYSTOLIC_KEY, grandmaData.first);
        i.putExtra(ReportActivity.GRANDMA_DIASTOLIC_KEY, grandmaData.second);
        startActivity(i);
    }
}