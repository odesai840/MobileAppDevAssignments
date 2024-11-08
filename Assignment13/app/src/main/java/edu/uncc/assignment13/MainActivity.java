// Assignment 13
// MainActivity.java
// Ohm Desai and Sullivan Crouse

package edu.uncc.assignment13;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.ArrayList;

import edu.uncc.assignment13.fragments.ContactSummaryFragment;
import edu.uncc.assignment13.fragments.ContactsFragment;
import edu.uncc.assignment13.fragments.CreateContactFragment;
import edu.uncc.assignment13.fragments.SelectGroupFragment;
import edu.uncc.assignment13.fragments.SelectPhoneTypeFragment;
import edu.uncc.assignment13.fragments.UpdateContactFragment;
import edu.uncc.assignment13.models.Contact;

public class MainActivity extends AppCompatActivity implements ContactsFragment.ContactsListener,
        CreateContactFragment.CreateContactListener, SelectPhoneTypeFragment.SelectPhoneTypeListener,
        SelectGroupFragment.SelectGroupListener, ContactSummaryFragment.ContactSummaryListener,
        UpdateContactFragment.UpdateContactListener {

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = Room.databaseBuilder(this, AppDatabase.class, "contact.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new ContactsFragment())
                .commit();
    }

    @Override
    public ArrayList<Contact> getAllContacts() {
        //need to get the list from the Rooms DB.
        return new ArrayList<Contact>(db.contactDao().getAll());
    }

    @Override
    public void gotoCreateContact() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new CreateContactFragment(), "create-contact-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoContactSummary(Contact contact) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, ContactSummaryFragment.newInstance(contact))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoEditContact(Contact contact) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, UpdateContactFragment.newInstance(contact), "edit-contact-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void deleteContact(Contact contact) {
        //need to delete from the Rooms DB.
        db.contactDao().delete(contact);
    }

    @Override
    public void clearAllContacts() {
        //need to delete all from the Rooms DB.
        db.contactDao().deleteAll();
    }

    @Override
    public void gotoSelectPhoneType() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectPhoneTypeFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectGroup() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectGroupFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void doneUpdateContact(Contact contact) {
        db.contactDao().update(contact);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelUpdateContact() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void doneCreateContact(Contact contact) {
        //need to save the contact to the Rooms DB.
        db.contactDao().insertAll(contact);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelCreateContact() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onPhoneTypeSelected(String phoneType) {
        CreateContactFragment createContactFragment = (CreateContactFragment) getSupportFragmentManager().findFragmentByTag("create-contact-fragment");
        if (createContactFragment != null) {
            createContactFragment.setSelectedPhoneType(phoneType);
        }

        UpdateContactFragment updateContactFragment = (UpdateContactFragment) getSupportFragmentManager().findFragmentByTag("edit-contact-fragment");
        if (updateContactFragment != null) {
            updateContactFragment.setSelectedPhoneType(phoneType);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onGroupSelected(String group) {
        CreateContactFragment createContactFragment = (CreateContactFragment) getSupportFragmentManager().findFragmentByTag("create-contact-fragment");
        if (createContactFragment != null) {
            createContactFragment.setSelectedGroup(group);
        }

        UpdateContactFragment updateContactFragment = (UpdateContactFragment) getSupportFragmentManager().findFragmentByTag("edit-contact-fragment");
        if (updateContactFragment != null) {
            updateContactFragment.setSelectedGroup(group);
        }

        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelSelection() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void doneContactSummary() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void deleteContactFromSummary(Contact contact) {
        //need to save the contact to the Rooms DB.
        //pop back stack.
        db.contactDao().delete(contact);
        getSupportFragmentManager().popBackStack();
    }
}