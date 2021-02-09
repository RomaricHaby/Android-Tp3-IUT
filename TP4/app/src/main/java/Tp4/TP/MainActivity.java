package Tp4.TP;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import Tp4.TP.SQL.DatabaseHelper;


public class MainActivity extends AppCompatActivity
{
    //Request code
    private final int AddContactCode = 1;
    private  final DatabaseHelper dataBase = new DatabaseHelper(this);

    private ContactAdapter adapter;
    public static ArrayList<Contact> contactList = new ArrayList<Contact>();

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        setUpList();
        setUpOnclickListener();
        setUpAddContact();
        LoadData();
    }

    private void LoadData(){
        contactList.clear();
        ArrayList<Contact> contact = dataBase.loadContact();

        for (int i = 0; i<contact.size(); i++){
            contactList.add(contact.get(i));
        }
    }

    private void setUpAddContact(){
        Button button = findViewById(R.id.ajoutContact);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addContact = new Intent(MainActivity.this, AjoutContactActivity.class);
                startActivityForResult(addContact,AddContactCode);
            }
        });
    }

    private void setUpList()
    {
        listView = (ListView) findViewById(R.id.ContactListView);
        adapter = new ContactAdapter(getApplicationContext(), 0, contactList);
        listView.setAdapter(adapter);
    }

    private void setUpOnclickListener()
    {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                //set title
                alertDialogBuilder.setTitle(R.string.TitleAlert);

                //set dialog message
                alertDialogBuilder
                        .setMessage(R.string.MessageAlert)
                        .setCancelable(false)
                        .setPositiveButton(R.string.PositiveButtonAlert, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                if(dataBase.deleteContact(contactList.get(position).getId(), contactList.get(position))){
                                    contactList.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), R.string.deleteFaild, Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton(R.string.NegativeButtonAlert, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Intent showDetail = new Intent(getApplicationContext(), DetailContactActivity.class);
                showDetail.putExtra("index", position);
                startActivity(showDetail);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case AddContactCode:
                if(resultCode == Activity.RESULT_OK){

                    String tmp = data.getStringExtra("avatar");
                    Contact contact;

                    if(tmp.contains("/")){
                        contact = new Contact(
                                data.getStringExtra("firstName"),
                                data.getStringExtra("lastname"),
                                data.getStringExtra("birthday"),
                                data.getStringExtra("phone"),
                                data.getStringExtra("email"),
                                data.getStringExtra("cap"),
                                data.getStringExtra("postalAdress"),
                                data.getStringExtra("genre"),
                                tmp);
                    }
                    else {
                        contact = new Contact(
                                data.getStringExtra("firstName"),
                                data.getStringExtra("lastname"),
                                data.getStringExtra("birthday"),
                                data.getStringExtra("phone"),
                                data.getStringExtra("email"),
                                data.getStringExtra("cap"),
                                data.getStringExtra("postalAdress"),
                                data.getStringExtra("genre"),
                                Integer.valueOf(tmp));
                    }

                    if (dataBase.insert(contact)){
                        Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "NOT Inserted", Toast.LENGTH_LONG).show();
                    }

                    contactList.add(contact);
                    adapter.notifyDataSetChanged();
                }
            break;
        }
    }
}