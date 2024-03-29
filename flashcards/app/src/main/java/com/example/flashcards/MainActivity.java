package com.example.flashcards;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.example.flashcards.database.entity.User;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flashcards.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity{

    public static final String TABLE_USERS = "users";
    public static final String GLOBAL_DECKS = "global_decks";

    private static final int SIGN_IN_REQUEST_CODE = 0;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private SharedPreferences prefer;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CardsBank.get().openBank();
        DecksBank.get().openBank();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_decks, R.id.nav_cards, R.id.nav_user)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        checkAuth();
        prefer = getSharedPreferences("user_data", MODE_PRIVATE);
        editor = prefer.edit();
        readSwipeInfo();
    }

    private void readSwipeInfo(){
        try {
            int allSwipe = prefer.getInt("all_swipe", 0);
            User.get().setAllSwipe(allSwipe);
            int knowSwipe = prefer.getInt("know", 0);
            User.get().setKnowSwipe(knowSwipe);
            int dontKnowSwipe = prefer.getInt("dont_know", 0);
            User.get().setDontKnowSwipe(dontKnowSwipe);
        } catch (Exception exception) {
            //
        }

    }

    private void saveSwipeInfo(){
        editor.putInt("all_swipe", User.get().getAllSwipe());
        editor.putInt("know", User.get().getKnowSwipe());
        editor.putInt("dont_know", User.get().getDontKnowSwipe());
        editor.commit();
    }
    private void checkAuth() {
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Запускаем активити регистрации или входа пользователя
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build(),
                    SIGN_IN_REQUEST_CODE
            );
        } else {
            //Пользователь уже зашёл
            Toast.makeText(this,
                            "Welcome " + FirebaseAuth.getInstance()
                                    .getCurrentUser()
                                    .getDisplayName(),
                            Toast.LENGTH_LONG)
                    .show();
        }
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        User.get().setId(userId);
        User.get().setName(userName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this,
                                "Successfully signed in. Welcome!",
                                Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(this,
                                "We couldn't sign you in. Please try again later.",
                                Toast.LENGTH_LONG)
                        .show();

                // Close the app
                finish();
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        NewDecksBank.get().openBank();
        NewCardsBank.get().openBank();
        readSwipeInfo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        DecksBank.get().saveBank();
        NewDecksBank.get().openBank();
        CardsBank.get().saveBank();
        saveSwipeInfo();
    }

    @Override
    protected void onStop() {
        super.onStop();
        DecksBank.get().saveBank();
        CardsBank.get().saveBank();
        saveSwipeInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_sign_out) {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this,
                                            "You have been signed out.",
                                            Toast.LENGTH_LONG)
                                    .show();

                            // Закрываем активити
                            finish();
                        }
                    });
        }
        return super.onOptionsItemSelected(item);
    }
}