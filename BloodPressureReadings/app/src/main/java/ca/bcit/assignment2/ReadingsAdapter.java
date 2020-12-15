package ca.bcit.assignment2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ReadingsAdapter extends ArrayAdapter<BloodPressureReading> {
    private final Context context;
    private final List<BloodPressureReading> readings;

    public ReadingsAdapter(Context context, List<BloodPressureReading> readings) {
        super(context, R.layout.list_layout, readings);
        this.context = context;
        this.readings = readings;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_layout, null, true);
        }

        TextView readingTextView = convertView.findViewById(R.id.readingTextView);
        TextView conditionTextView = convertView.findViewById(R.id.fatherConditionTextView);
        TextView dateTextView = convertView.findViewById(R.id.dateTextView);

        BloodPressureReading reading = readings.get(position);
        String pressureData = (int) reading.getSystolic() + "/" + (int) reading.getDiastolic() + " mm Hg";
        readingTextView.setText(pressureData);
        conditionTextView.setText(reading.getConditionStatus(context.getResources()));
        dateTextView.setText(reading.getFormattedRecordedDate());

        return convertView;
    }
}
