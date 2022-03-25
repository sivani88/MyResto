package view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myresto.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    androidx.appcompat.widget.Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignIn mGoogleSignIn;
    ActivityResultLauncher<String[]> mPermissionResultLauncher;
    private boolean isReadPermissionGranted = false;
    private boolean isLocationPermissionGranted = false;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(new MapsFragment());
        mToolbar = findViewById(R.id.topAppBar);

        getSetonClickListenerBottom();
        getClickListenerNavigationDrawer();
        getItemSelectedListener();



        mPermissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                new ActivityResultCallback<Map<String, Boolean>>() {
                    @Override
                    public void onActivityResult(Map<String, Boolean> result) {
                        if (result.get(Manifest.permission.READ_EXTERNAL_STORAGE) != null) {
                            isReadPermissionGranted = result.get(Manifest.permission.READ_EXTERNAL_STORAGE);
                        }
                        if (result.get(Manifest.permission.ACCESS_FINE_LOCATION) != null) {
                            isReadPermissionGranted = result.get(Manifest.permission.ACCESS_FINE_LOCATION);
                        }

                    }
                });
        requestPermission();

    }


    private void requestPermission() {
        isReadPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
        isLocationPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;


        List<String > permissionRequest = new ArrayList<String>();
        if (!isReadPermissionGranted){
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        }
        if(!isLocationPermissionGranted) {
            permissionRequest.add(Manifest.permission.ACCESS_FINE_LOCATION);

        }

        if(!permissionRequest.isEmpty()) {
            mPermissionResultLauncher.launch(permissionRequest.toArray(permissionRequest.toArray(new String[0])));
        }
    }

    private void initHeaderInNavigationView() {

        ImageView mImageAvatar;
        TextView userName;
        TextView userEmail;

        NavigationView mNavigationView = findViewById(R.id.navigationView);
        View hView = mNavigationView.getHeaderView(0);
        mImageAvatar = hView.findViewById(R.id.image_avatar);
        userName = hView.findViewById(R.id.textviname);
        userEmail = hView.findViewById(R.id.textViewemail);


        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            userName.setText(signInAccount.getDisplayName());
            userEmail.setText(signInAccount.getEmail());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mImageAvatar.setImageIcon(Icon.createWithContentUri(signInAccount.getPhotoUrl()));
            }

        }
    }


//navigation drawer implementation

    private void getItemSelectedListener() {
        NavigationView mNavigationView = findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                item.setChecked(true);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                switch (id) {
                    case R.id.nav_your_lunch:
                        replaceFragment(new ListFragment());
                        Toast.makeText(MainActivity.this, "Restaurant is clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_settings:
                        Toast.makeText(MainActivity.this, "Settings is clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(), Authentication.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Logout is clicked", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }

                return true;
            }
        });
    }

    private void getClickListenerNavigationDrawer() {
        mToolbar = findViewById(R.id.topAppBar);
        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
                initHeaderInNavigationView();
            }
        });

    }

    //Bottom toolbar implementation fragment
    public void getSetonClickListenerBottom() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_app_bar);
        bottomNav.setOnItemSelectedListener(item -> {
            Log.e("replaceFragment", String.valueOf(item.getItemId()));
            switch (item.getItemId()) {
                case R.id.map:
                    replaceFragment(new MapsFragment());
                    Log.e("replaceFragment", "Maps");
                    break;
                case R.id.list:
                    replaceFragment(new ListFragment());
                    Log.e("replaceFragment", "LIST");
                    break;
                case R.id.workmates:
                    replaceFragment(new CoworkerFragment());
                    Log.e("replaceFragment", "workmate");
                    break;
            }

            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frameLayout, fragment);
        mFragmentTransaction.commit();
    }


}


