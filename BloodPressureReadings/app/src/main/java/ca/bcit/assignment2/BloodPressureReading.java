package ca.bcit.assignment2;

import android.content.res.Resources;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BloodPressureReading {

    public enum Condition {
        NORMAL,
        ELEVATED,
        STAGE_1,
        STAGE_2,
        HYPERTENSIVE,
    }

    private String id;
    private String familyMember;
    private double systolic;
    private double diastolic;
    private Date recordedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFamilyMember()  {
        return familyMember;
    }

    public double getSystolic() {
        return systolic;
    }

    public double getDiastolic() {
        return diastolic;
    }

    public Date getRecordedDate() {
        return recordedDate;
    }

    public String getFormattedRecordedDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return format.format(recordedDate);
    }

    public Condition getCondition() {
        if (systolic < 120 && diastolic < 80) {
            return Condition.NORMAL;
        } else if (systolic < 130 && diastolic < 80) {
            return Condition.ELEVATED;
        } else if (systolic < 140 || diastolic < 90) {
            return Condition.STAGE_1;
        } else if (systolic < 180 || diastolic < 120) {
            return Condition.STAGE_2;
        } else {
            return Condition.HYPERTENSIVE;
        }
    }

    public String getConditionStatus(Resources r) {
        int conditionResourceId = 0;
        switch (getCondition()) {
            case NORMAL:
                conditionResourceId = R.string.normal_condition;
                break;
            case ELEVATED:
                conditionResourceId = R.string.elevated_condition;
                break;
            case STAGE_1:
                conditionResourceId = R.string.stage_1_condition;
                break;
            case STAGE_2:
                conditionResourceId = R.string.stage_2_condition;
                break;
            case HYPERTENSIVE:
                conditionResourceId = R.string.hypertensive_condition;
                break;
        }
        return r.getText(conditionResourceId).toString();
    }

    public BloodPressureReading() {}

    public BloodPressureReading(String id, String familyMember, double systolic, double diastolic) {
        this.id = id;
        this.familyMember = familyMember;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.recordedDate = new Date();
    }
}
