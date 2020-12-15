package ca.bcit.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportActivity extends AppCompatActivity {

    static final String FATHER_SYSTOLIC_KEY = "fatherSystolic";
    static final String FATHER_DIASTOLIC_KEY = "fatherDiastolic";
    static final String MOTHER_SYSTOLIC_KEY = "motherSystolic";
    static final String MOTHER_DIASTOLIC_KEY = "motherDiastolic";
    static final String GRANDMA_SYSTOLIC_KEY = "grandmaSystolic";
    static final String GRANDMA_DIASTOLIC_KEY = "grandmaDiastolic";
    static final String GRANDPA_SYSTOLIC_KEY = "grandpaSystolic";
    static final String GRANDPA_DIASTOLIC_KEY = "grandpaDiastolic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent i = getIntent();
        double fatherSystolic = i.getDoubleExtra(FATHER_SYSTOLIC_KEY, 0);
        double fatherDiastolic = i.getDoubleExtra(FATHER_DIASTOLIC_KEY, 0);
        double motherSystolic = i.getDoubleExtra(MOTHER_SYSTOLIC_KEY, 0);
        double motherDiastolic = i.getDoubleExtra(MOTHER_DIASTOLIC_KEY, 0);
        double grandmaSystolic = i.getDoubleExtra(GRANDMA_SYSTOLIC_KEY, 0);
        double grandmaDiastolic = i.getDoubleExtra(GRANDMA_DIASTOLIC_KEY, 0);
        double grandpaSystolic = i.getDoubleExtra(GRANDPA_SYSTOLIC_KEY, 0);
        double grandpaDiastolic = i.getDoubleExtra(GRANDPA_DIASTOLIC_KEY, 0);

        TextView titleTextView = findViewById(R.id.titleTextView);

        TextView fatherFamilyMemberTextView = findViewById(R.id.fatherFamilyMemberTextView);
        TextView fatherSystolicTextView = findViewById(R.id.fatherSystolicTextView);
        TextView fatherDiastolicTextView = findViewById(R.id.fatherDiastolicTextView);
        TextView fatherConditionTextView = findViewById(R.id.fatherConditionTextView);

        TextView motherFamilyMemberTextView = findViewById(R.id.motherFamilyMemberTextView);
        TextView motherSystolicTextView = findViewById(R.id.motherSystolicTextView);
        TextView motherDiastolicTextView = findViewById(R.id.motherDiastolicTextView);
        TextView motherConditionTextView = findViewById(R.id.motherConditionTextView);

        TextView grandpaFamilyMemberTextView = findViewById(R.id.grandpaFamilyMemberTextView);
        TextView grandpaSystolicTextView = findViewById(R.id.grandpaSystolicTextView);
        TextView grandpaDiastolicTextView = findViewById(R.id.grandpaDiastolicTextView);
        TextView grandpaConditionTextView = findViewById(R.id.grandpaConditionTextView);

        TextView grandmaFamilyMemberTextView = findViewById(R.id.grandmaFamilyMemberTextView);
        TextView grandmaSystolicTextView = findViewById(R.id.grandmaSystolicTextView);
        TextView grandmaDiastolicTextView = findViewById(R.id.grandmaDiastolicTextView);
        TextView grandmaConditionTextView = findViewById(R.id.grandmaConditionTextView);

        titleTextView.setText(getString(R.string.report_title, getTodayFormattedDate()));

        String[] members = getResources().getStringArray(R.array.familyMember);

        updateAverageReadingsForMember(members[0], fatherSystolic, fatherDiastolic,
                fatherFamilyMemberTextView, fatherSystolicTextView, fatherDiastolicTextView, fatherConditionTextView);
        updateAverageReadingsForMember(members[1], motherSystolic, motherDiastolic,
                motherFamilyMemberTextView, motherSystolicTextView, motherDiastolicTextView, motherConditionTextView);
        updateAverageReadingsForMember(members[2], grandmaSystolic, grandmaDiastolic,
                grandmaFamilyMemberTextView, grandmaSystolicTextView, grandmaDiastolicTextView, grandmaConditionTextView);
        updateAverageReadingsForMember(members[3], grandpaSystolic, grandpaDiastolic,
                grandpaFamilyMemberTextView, grandpaSystolicTextView, grandpaDiastolicTextView, grandpaConditionTextView);
    }

    private String getTodayFormattedDate() {
        SimpleDateFormat format = new SimpleDateFormat("MMM yyyy");
        return format.format(new Date());
    }

    private void updateAverageReadingsForMember(String familyMember, double systolic, double diastolic,
                                       TextView familyMemberTextView, TextView systolicTextView,
                                       TextView diastolicTextView, TextView conditionTextView) {
        familyMemberTextView.setText(familyMember);
        if (systolic != -1) {
            BloodPressureReading fatherReading = new BloodPressureReading(null, null, systolic, diastolic);
            systolicTextView.setText(String.valueOf(systolic));
            diastolicTextView.setText(String.valueOf(diastolic));
            conditionTextView.setText(fatherReading.getConditionStatus(getResources()));
        } else {
            systolicTextView.setText(R.string.not_available);
            diastolicTextView.setText(R.string.not_available);
            conditionTextView.setText(R.string.not_available);
        }
    }
}