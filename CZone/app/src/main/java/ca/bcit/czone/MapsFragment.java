package ca.bcit.czone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = MapsFragment.class.getSimpleName();

    static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private static final int DEFAULT_ZOOM = 15;

    private GoogleMap map;
    private CameraPosition cameraPosition;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private boolean locationPermissionGranted;
    private Location lastKnownLocation;
    private final LatLng defaultLocation = new LatLng(49.2835, -123.1153);
    private boolean isAlertShown;
    private Context context;

    DatabaseReference zoneDbRef;
    DatabaseReference notificationDbRef;

    List<Zone> zones = new ArrayList<>();
    List<Circle> circles = new ArrayList<>();

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();
        getZones();
    }

    private void getZones() {
        zoneDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                zones.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    zones.add(snapshot.getValue(Zone.class));

                for (Circle circle : circles)
                    circle.remove();

                circles.clear();
                for (Zone zone : zones)
                    addCircleFromZone(zone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void addCircleFromZone(Zone zone) {
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(zone.getLat(), zone.getLng()))
                .radius(zone.getRadius())
                .strokeColor(Color.BLACK)
                .fillColor(zone.getColor())
                .strokeWidth(2);
        circles.add(map.addCircle(circleOptions));
    }

    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this.getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            map.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });

                LocationRequest locationRequest = LocationRequest.create();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setInterval(5000);
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        if (locationResult == null) {
                            return;
                        }
                        Location lastLocation = locationResult.getLastLocation();
                        if (lastLocation == null) {
                            return;
                        }

                        lastKnownLocation = lastLocation;
//                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                new LatLng(lastKnownLocation.getLatitude(),
//                                        lastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                        showAlertIfNeeded();
                    }
                }, null);
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getAddressLine(0));
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
    }

    private String getWarningMessage(long elapsedDays) {
        if (elapsedDays <= 7)
            return getString(R.string.warning_message_1_week);
        if (elapsedDays <= 14)
            return getString(R.string.warning_message_2_week);
        if (elapsedDays <= 21)
            return getString(R.string.warning_message_3_week);
        return getString(R.string.warning_message_over_3_week);
    }

    private void showAlertIfNeeded() {
        if (lastKnownLocation == null || zones.isEmpty())
            return;

        for (Zone zone : zones) {
            if (zone.containsLocation(lastKnownLocation)) {
                if (!isAlertShown) {
                    try {
                        new AlertDialog.Builder(getContext())
                                .setTitle(R.string.warning)
                                .setMessage(getWarningMessage(zone.getElapsedDays()))
                                .setPositiveButton(android.R.string.ok, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        saveNotification(zone);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    isAlertShown = true;
                }
                return;
            }
        }
        isAlertShown = false;
    }

    private void saveNotification(Zone zone) throws ParseException {
        String warning_title = getString(R.string.warning);
        String warning_message = getWarningMessage(zone.getElapsedDays());
        String address = getAddress(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        String dateTime = new SimpleDateFormat("MM-dd-YYYY HH:mm").format(new Date());
        String id = notificationDbRef.push().getKey();
        Notification notification = new Notification(id, warning_title, warning_message, address, dateTime);
        notificationDbRef.child(id).setValue(notification);
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
        String deviceId = Settings.System.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        notificationDbRef = FirebaseDatabase.getInstance().getReference("notifications").child(deviceId);
        zoneDbRef = FirebaseDatabase.getInstance().getReference("zones");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (map != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }
}