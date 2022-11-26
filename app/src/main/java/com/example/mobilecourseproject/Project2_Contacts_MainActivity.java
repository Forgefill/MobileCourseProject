package com.example.mobilecourseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Models.Contact;

public class Project2_Contacts_MainActivity extends AppCompatActivity {

    Button btn_viewAllContacts, btn_view050Contacts;
    ListView contactsListView;
    ArrayList<Contact> contacts = new ArrayList<>();
    ArrayList<String> contactsString = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project2_contacts_main);
        btn_viewAllContacts = findViewById(R.id.btn_viewAllContacts);
        btn_view050Contacts = findViewById(R.id.btn_view050Contacts);
        contactsListView = findViewById(R.id.contactsListView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactsString);

        contactsListView.setAdapter(arrayAdapter);

        btn_viewAllContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPhoneContacts(null);
                contactsToStrings();
                arrayAdapter.notifyDataSetChanged();
            }
        });

        btn_view050Contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPhoneContacts(ContactsContract.CommonDataKinds.Phone.NUMBER + " LIKE '%050%'");
                contactsToStrings();
                arrayAdapter.notifyDataSetChanged();
            }
        });

        contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String location = contacts.get(i).getAddress();

                openMap(location);

            }
        });
    }

    private void contactsToStrings()
    {
        contactsString.clear();

        for (Contact item: contacts)
        {
            contactsString.add(item.toString());
        }
    }

    private void getPhoneContacts(String cursorSelectQuery) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 0);
        }

            contacts.clear();
            ContentResolver contentResolver = getContentResolver();
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

            String CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
            String DISPLAY_NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
            String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
            String STREET = ContactsContract.CommonDataKinds.StructuredPostal.STREET;

            Cursor cursor = contentResolver.query(uri, null,  cursorSelectQuery , null, null);

            if (cursor.moveToFirst()) {
                do {

                    String contactId = cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_ID));
                    String contactName = cursor.getString(cursor.getColumnIndexOrThrow(DISPLAY_NAME));
                    String contactNumber = cursor.getString(cursor.getColumnIndexOrThrow(NUMBER));
                    String contactAddress = "";
                    Uri postal_uri = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
                    Cursor postal_cursor = contentResolver.query(postal_uri, null, ContactsContract.Data.CONTACT_ID +
                            " = " + contactId, null, null);

                    if (postal_cursor.moveToFirst()) {

                        do {
                            contactAddress = postal_cursor.getString(postal_cursor.getColumnIndexOrThrow(STREET));
                        } while (postal_cursor.moveToNext());

                        postal_cursor.close();
                    }

                    Contact toAdd = new Contact(contactId, contactName, contactNumber, contactAddress);
                    contacts.add(toAdd);

                } while (cursor.moveToNext());

                cursor.close();
            }
        }

    public void openMap(String destination)
    {
        if(destination != null) {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("google.navigation:q="+destination + "&mode=w"));
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Selected contact address is null", Toast.LENGTH_SHORT).show();
        }
    }


}