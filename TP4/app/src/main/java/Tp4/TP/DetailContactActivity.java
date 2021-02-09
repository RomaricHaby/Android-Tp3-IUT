package Tp4.TP;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;


public class DetailContactActivity extends AppCompatActivity
{
    private Contact selectedContact;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSelectedContact();
        setValues();
    }

    private void getSelectedContact()
    {
        int index = getIntent().getIntExtra("index",0);
        selectedContact = MainActivity.contactList.get(index);
    }

    private void setValues()

    {
        TextView firstname = (TextView) findViewById(R.id.ContactFistName);
        TextView lastname = (TextView) findViewById(R.id.ContactLastName);
        TextView birthday = (TextView) findViewById(R.id.BirthdayText);
        TextView phone = (TextView) findViewById(R.id.PhoneText);
        TextView Email = (TextView) findViewById(R.id.EmailText);
        TextView cap = (TextView) findViewById(R.id.AdressCapText);
        TextView addressPostal = (TextView) findViewById(R.id.AdressPostalText);
        TextView genre = (TextView) findViewById(R.id.GenreText);

        ImageView avatar = (ImageView) findViewById(R.id.AvatarContact);

        firstname.setText(selectedContact.getFirstName());
        lastname.setText(selectedContact.getLastName());
        birthday.setText(selectedContact.getBirthday());
        phone.setText(selectedContact.getPhone());
        Email.setText(selectedContact.getEmail());
        cap.setText(selectedContact.getCap());
        addressPostal.setText(selectedContact.getAdressPostal());
        genre.setText(selectedContact.getGenre());


        if(selectedContact.getImage() != -1){
            avatar.setImageResource(selectedContact.getImage());
        }
        else{
            File imgFile = new  File(selectedContact.getPathImg());

            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                avatar.setImageBitmap(myBitmap);
            }
        }

    }
}