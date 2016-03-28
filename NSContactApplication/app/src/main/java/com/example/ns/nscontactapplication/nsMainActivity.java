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

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class nsMainActivity extends AppCompatActivity {

   public ArrayList<ContactObject> contactList= new ArrayList<ContactObject>();
    /**
     * Created by Niranjana on 03-11-2015.
     * to load the initial screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ns_main);
        try {
            //Populate all data from File to Arraylist of Objects
            populateDataGridFromFile();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),
                    "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        if (contactList.size() > 0){
            //Populate the list view
            populateListView();
        }
    }
    /**
     * Created by Niranjana on 03-11-2015.
     * to populate the datagrid ArrayList of objects from the external file
     */
    private void populateDataGridFromFile() throws IOException{

        String str=""; int j = 0;
        String FILE_NAME = "nsContactfile.txt";

            String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            File file = new File(baseDir, FILE_NAME);
            try {
                FileReader fReader = new FileReader(file);
                BufferedReader bReader = new BufferedReader(fReader);
                while( (str = bReader.readLine()) != null  ){
                    String w[] =  str.split("\t");
                    contactList.add(new ContactObject(w[0], w[1], w[2], w[3]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
       // contactList.add(new ContactObject("first", "last", "456", "khniru@gh.com"));

        Collections.sort(contactList, new CustomComparator());
        ((GlobalVariable) this.getApplication()).setContactList(contactList);

    }


    /**
     * Created by Niranjana on 03-11-2015.
     * Populate the list view using Custom Adapter
     */
    private void populateListView() {
        final ArrayList<Item> alist = new ArrayList<>();
        int k = 0;
        while (k < contactList.size()){
             //String tempLine = contactList.get(k).getFirstName() + "\t" +contactList.get(k).getLastName()+ "\t" +"\t"+"\t" + contactList.get(k).getPhoneNumber();
            String tempLine1, tempLine2;

            if (contactList.get(k).getLastName().equals("*")){
                tempLine1 = contactList.get(k).getFirstName();
            }else{
                tempLine1 = contactList.get(k).getFirstName() + "\t" +"\t" +contactList.get(k).getLastName();
            }
            if (contactList.get(k).getPhoneNumber().equals("*")){
                tempLine2 = "";
            }else{
                tempLine2 = contactList.get(k).getPhoneNumber();
            }

            alist.add(new Item(tempLine1, tempLine2));
            k++;
        }
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row_layout, R.id.item1TextView, alist);
        CustomAdapter adapter = new CustomAdapter(this, alist);
        ListView list = (ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //when an item in the list is clicked, it is directed to its detail activity
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), nsSubActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("POS", position);
                intent.putExtra("FLAG", 1);
                startActivity(intent);

            }
        });
    }


    /**
     * Created by Niranjana on 03-11-2015.
     * to make the "add" button visible in action bar
     */
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.ins).setVisible(true).setEnabled(true);
        menu.findItem(R.id.del).setVisible(false);
        menu.findItem(R.id.save).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Created by Niranjana on 03-11-2015.
     * to  create action bar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ns_main, menu);
        return true;
    }

    /**
     * Created by Niranjana on 03-11-2015.
     * to handle the 'insert' icon of the action bar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //When insert button is clicked, it opens another activity
        if (id == R.id.ins) {
          Intent intent = new Intent(getApplicationContext(), nsSubActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("FLAG", 0);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
