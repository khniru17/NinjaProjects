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

/**
 * Created by Niranjana on 05-11-2015.
 * This Class is to save the item view of a List View
 */
public class Item {
    private String name;
    private String phoneno;

    public Item(String name, String phoneno) {
        super();
        this.name = name;
        this.phoneno = phoneno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}
