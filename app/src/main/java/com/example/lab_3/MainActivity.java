package com.example.lab_3;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int SOME_REQUEST_CODE = 333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.button_website);
        btn1.setOnClickListener(this);
        Button btn2 = findViewById(R.id.button_dial);
        btn2.setOnClickListener(this);
        Button btn3 = findViewById(R.id.button_contact);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText editText = findViewById(R.id.editText);
        switch (v.getId()) {
            case R.id.button_website:
                Intent intent= new Intent(MainActivity.this, BrowserActivity.class);
                String url = editText.getText().toString();
                if(!url.startsWith("www.")&& !url.startsWith("http://")&& !url.startsWith("https://")){
                    url = "www." + url;
                }
                if(!url.startsWith("http://")&& !url.startsWith("https://")){
                    url = "https://" + url;
                }
                intent.putExtra("url", url);
                startActivity(intent);
                break;
            case R.id.button_dial:
                String phone = editText.getText().toString();
                Intent intent_dial= new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(intent_dial);
                break;
            case R.id.button_contact:
                Intent intent_contact= new Intent(Intent.ACTION_PICK);
                intent_contact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent_contact, SOME_REQUEST_CODE);
                break;
        }
    }
    private void getContactNumber(Intent intent){
        Uri contactUri = intent.getData(); // get data from intent
        String[] projection = new String[]{ // create a search filter for all values we need by putting their types into an array
                ContactsContract.CommonDataKinds.Phone.NUMBER // we only use 1 here - the phone number
        };
        // run a query on the SQLite Database containing all contacts using the parameters from above, ignore the last 3 nulls for now
        Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
        // If the cursor returned is valid, get the phone number
        if (cursor != null && cursor.moveToFirst()) {
            // since we will only have 1 entry for the phone number, no need to loop through the entire table, just get the FIRST row
            int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER); // find out what the index for the phone number column is
            String number = cursor.getString(numberIndex); // use this index to get the string value from the column in the current row
            ((EditText)findViewById(R.id.editText)).setText(number); // set the text of the input field to the phone number string
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if (requestCode == SOME_REQUEST_CODE && resultCode == RESULT_OK) {
            getContactNumber(i);
        }
    }
}
