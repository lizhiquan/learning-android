package ca.bcit.czone;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    ListView notificationListView;
    DatabaseReference dbRef;
    List<Notification> notifications = new ArrayList<>();

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        notificationListView = view.findViewById(R.id.notification_list);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String deviceId = Settings.System.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        dbRef = FirebaseDatabase.getInstance().getReference("notifications").child(deviceId);
    }

    @Override
    public void onStart() {
        super.onStart();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notifications.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    notifications.add(snapshot.getValue(Notification.class));

                notifications.sort((n1, n2) -> {
                    try {
                        return n2.getReportedDate().compareTo(n1.getReportedDate());
                    } catch (Exception e) {
                        return 0;
                    }
                });

                NotificationListAdapter adapter = new NotificationListAdapter(getContext());
                notificationListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    class NotificationListAdapter extends ArrayAdapter<Notification> {
        private final Context context;

        public NotificationListAdapter(Context context) {
            super(context, R.layout.list_layout, notifications);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_layout, null, true);
            }

            TextView titleTextView = convertView.findViewById(R.id.notification_title);
            TextView messageTextView = convertView.findViewById(R.id.notification_message);
            TextView addressTextView = convertView.findViewById(R.id.notification_address);
            TextView dateTimeTextView = convertView.findViewById(R.id.notification_dateTime);

            Notification item = notifications.get(position);
            titleTextView.setText(item.getTitle());
            messageTextView.setText(item.getDescription());
            addressTextView.setText(item.getAddress());
            dateTimeTextView.setText(item.getDateTime());

            return convertView;
        }
    }
}