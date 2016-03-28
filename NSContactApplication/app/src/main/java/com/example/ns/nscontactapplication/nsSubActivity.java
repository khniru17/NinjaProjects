/*
Name: Niranjana Kandavel Net Id: nxk152730
Name: Sidharth Udhayakumar Net Id: sxu140530
Date: 11-04-2015
Course Number: CS 6301.006
Purpose: To create an Android Application to view a list of contacts, add/modify/delete contacts.
Description: nsMainActivity Class is the home page of the app. It shows the list of contacts.
nsMainActivity has 1 button-Add which takes us to Add Mode where we can add a new contact
and either save it by clicking on save button or cancel it by clicking on the back button.
On clicking any of the contacts in the list takes us to view Mode i.e. of nsSubActivity class
were all the details of that contact is displayed in an editable mode.
In the View Mode either we can delete it by clicking on delete button in action bar or modify it by
clicking on the save button in the action bar. Once the item is deleted or modified, it gets back to the
list mode. By clicking on the back button we can go back to the list view
without any delete or modification
So, the app has 2 Activity:
1. nsMainActivity - To view the Contact list (it has add button)
2. nsSubActivity - To add/delete/modify
classes: contactObject, CustomAdapter, CustomComparator, GlobalVariable, Item, nsMainActivity, nsSubActivity
layout xml file:activity_ns_main.xml(view mode)
                activity_ns_sub.xml (add/modify/delete mode)
                row_layout.xml (item of a list view)
 menu xml file: menu_ns_main.xml(consists add, save and delete button for MainActivity screen),
                menu_ns_sub.xml(consists add, save and delete button for SubActivity screen),
* */
package com.example.ns.nscontactapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

public class nsSubActivity extends AppCompatActivity {

    EditText firstName, lastName, phoneNumber, emailId;
    String strfirstName, strlastName, strphoneNumber, stremailId;
    ArrayList<ContactObject> contactList = new ArrayList<ContactObject>();
    int pos = 0;
    int flag = 0;
    /**
     * Created by Sidharth Udhayakumar on 03-11-2015.
     * to get the selected row from the List View
     * if flag == 0, insert mode - so just display the layout with save button in action bar
     * if flag != 0, modify/delete mode - display the details with save and delete button in action bar
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ns_sub);

        Intent i = getIntent();

        pos = i.getIntExtra("POS", 0);
        flag = i.getIntExtra("FLAG",0);

        contactList = ((GlobalVariable) this.getApplication()).getContactList();

        if (flag!=0){


            firstName = (EditText)findViewById(R.id.firstnametv);
            if (!contactList.get(pos).getFirstName().equals("*")){
              firstName.setText(contactList.get(pos).getFirstName());
            }
            lastName = (EditText)findViewById(R.id.lastnametv);
            if (!contactList.get(pos).getLastName().equals("*")){
                lastName.setText(contactList.get(pos).getLastName());
            }
            phoneNumber = (EditText)findViewById(R.id.phonetv);
            if (!contactList.get(pos).getPhoneNumber().equals("*")){
                phoneNumber.setText(contactList.get(pos).getPhoneNumber());
            }
            emailId = (EditText)findViewById(R.id.emailtv);
            if (!contactList.get(pos).getEmailId().equals("*")){
                emailId.setText(contactList.get(pos).getEmailId());
            }


        }
    }

    /**
     * Created by Sidharth Udhayakumar  on 03-11-2015.
     * if flag == 0, insert mode - display only save button
     * else flag != 0, modify/delete mode - display modify, delete button
     */
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (flag!= 0) {

            menu.findItem(R.id.ins).setVisible(false);
            menu.findItem(R.id.del).setVisible(true).setEnabled(true);
            menu.findItem(R.id.save).setVisible(true).setEnabled(true);
        }else{
            menu.findItem(R.id.ins).setVisible(false);
            menu.findItem(R.id.del).setVisible(false);
            menu.findItem(R.id.save).setVisible(true).setEnabled(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }
    /**
     * Created by Sidharth Udhayakumar  on 03-11-2015.
     * to create action bar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ns_sub, menu);
        return true;
    }
    /**
     * Created by Sidharth Udhayakumar  on 03-11-2015.
     * code for save and delete button
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //when save button is clicked, data in the edit text is stored to the arraylist of objects
        //the data is then added to file
        if (id == R.id.save) {
            firstName = (EditText)findViewById(R.id.firstnametv);

            //when first name is empty - dont save
            if (!firstName.getText().toString().isEmpty()) {
                strfirstName = firstName.getText().toString();
                lastName = (EditText)findViewById(R.id.lastnametv);
                if (!lastName.getText().toString().isEmpty()){
                    strlastName = lastName.getText().toString();}
                else{
                    strlastName = "*";
                }

                phoneNumber = (EditText)findViewById(R.id.phonetv);
                if (!phoneNumber.getText().toString().isEmpty()){
                    strphoneNumber = phoneNumber.getText().toString();
                }else{
                    strphoneNumber = "*";
                }

                emailId = (EditText)findViewById(R.id.emailtv);
                if (!emailId.getText().toString().isEmpty()){
                    stremailId = emailId.getText().toString();
                }else{
                    stremailId = "*";
                }

                //if in modify mode, remove the current entry else add a new entry
                if (flag != 0){
                    contactList.remove(pos);
                }
                contactList.add(new ContactObject(strfirstName, strlastName, strphoneNumber, stremailId));
               //to save the data to file
                saveFile();

            }

            return true;
        }
        //if delete button is clicked, remove the entry
        if (id == R.id.del) {
            contactList.remove(pos);
            saveFile();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Created by Sidharth Udhayakumar  on 03-11-2015.
     * to save data from Arraylist of Objects to the file
     */
    private void saveFile() {

        String FILE_NAME = "nsContactfile.txt";

        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(baseDir, FILE_NAME);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            int k = 0;
            while(k < contactList.size()){
                writer.write(contactList.get(k).getFirstName()+"\t"+contactList.get(k).getLastName()+"\t"+contactList.get(k).getPhoneNumber()+"\t"+contactList.get(k).getEmailId()+"\n");
                k++;
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //go back to the previous activity
        Intent intent = new Intent(getApplicationContext(), nsMainActivity.class);
        startActivity(intent);
    }


}
