package com.shonen.ukr.watsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class WatsAppChatActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView massageListView;
    private ArrayList<String>massages;
    private ArrayAdapter arrayAdapter;
    private String selectedUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wats_app_chat);

        selectedUser = getIntent().getStringExtra("selectUser");
        Toast.makeText(WatsAppChatActivity.this,"Chat with " + selectedUser,Toast.LENGTH_SHORT).show();

        findViewById(R.id.btnSendMassage).setOnClickListener(this);
        massageListView = findViewById(R.id.massageListView);
        massages = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,massages);
        massageListView.setAdapter(arrayAdapter);

        try {
            ParseQuery<ParseObject> firstUserMassage = ParseQuery.getQuery("Chat");
            ParseQuery<ParseObject> secondUserMassage = ParseQuery.getQuery("Chat");

            firstUserMassage.whereEqualTo("inName",ParseUser.getCurrentUser().getUsername());
            firstUserMassage.whereEqualTo("outName",selectedUser);

            secondUserMassage.whereEqualTo("inName",selectedUser);
            secondUserMassage.whereEqualTo("outName",ParseUser.getCurrentUser().getUsername());

            ArrayList<ParseQuery<ParseObject>> allQuery = new ArrayList<>();
            allQuery.add(firstUserMassage);
            allQuery.add(secondUserMassage);

            ParseQuery<ParseObject> myQuery = ParseQuery.or(allQuery);
            myQuery.orderByAscending("createdAt");

            myQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(objects.size()>0 && e == null){
                        for(ParseObject chatobj:objects){
                            String txtMassage = chatobj.get("txtMassage") + "";

                            if(chatobj.get("inName").equals(ParseUser.getCurrentUser().getUsername())){
                                txtMassage = selectedUser +": " + txtMassage;
                            }if(chatobj.get("inName").equals(selectedUser)){
                                txtMassage = ParseUser.getCurrentUser().getUsername() +": " + txtMassage;
                            }
                            massages.add(txtMassage);

                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnSendMassage){
            final EditText sendingMassage = findViewById(R.id.edtMassage);
            ParseObject chat = new ParseObject("Chat");
            chat.put("outName", ParseUser.getCurrentUser().getUsername());
            chat.put("inName",selectedUser);
            chat.put("txtMassage",sendingMassage.getText().toString());
            chat.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        Toast.makeText(WatsAppChatActivity.this,"You send massage to " + selectedUser,Toast.LENGTH_SHORT).show();
                        massages.add(ParseUser.getCurrentUser().getUsername() + ": " + sendingMassage.getText().toString() );
                        arrayAdapter.notifyDataSetChanged();
                        sendingMassage.setText("");
                    }else {
                        e.getMessage();
                    }
                }
            });
        }
    }
}
