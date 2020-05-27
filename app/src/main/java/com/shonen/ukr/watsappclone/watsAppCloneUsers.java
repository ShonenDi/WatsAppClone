package com.shonen.ukr.watsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WatsAppCloneUsers extends AppCompatActivity {
    private ArrayList<String> watsAppUsers;
    private ListView listOfUsers;
    private ArrayAdapter<String> watsAppUsersAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wats_app_clone_users);

        setTitle("List of users:");
        swipeRefreshLayout = findViewById(R.id.pullToRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    final ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
                    parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
                    parseQuery.whereNotContainedIn("username", watsAppUsers);
                    parseQuery.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> objects, ParseException e) {
                            if (objects.size() > 0) {
                                if (e == null) {
                                    for (ParseUser user : objects) {
                                        watsAppUsersAdapter.notifyDataSetChanged();
                                        if (swipeRefreshLayout.isRefreshing()) {
                                            swipeRefreshLayout.setRefreshing(false);
                                        }
                                    }
                                }
                            } else {
                                if (swipeRefreshLayout.isRefreshing()) {
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        }
                    });

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        });
        listOfUsers = findViewById(R.id.lstOfUsers);
        watsAppUsers = new ArrayList<>();
        watsAppUsersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, watsAppUsers);

        try {
            ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
            parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            parseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (objects.size() > 0) {
                        if (e == null) {
                            for (ParseUser user : objects) {
                                watsAppUsers.add(user.getUsername());
                            }
                            listOfUsers.setAdapter(watsAppUsersAdapter);
                        }
                    }
                }
            });

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logOutItem) {
            ParseUser.getCurrentUser().logOut();
            finish();
            Intent intent = new Intent(WatsAppCloneUsers.this, SingUp.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
