package Tp4.TP;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;


public class AjoutContactActivity extends AppCompatActivity {

    //Request code
    private final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private final int PICK_FROM_GALLERY = 2;

    //Regex
    private final String regex_num = "^((\\+)33|0)[1-9](\\d{2}){4}$";
    private final Pattern pattern_num = Pattern.compile(regex_num);
    private final String regex_email = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private final Pattern pattern_email = Pattern.compile(regex_email);

    //Widget
    private EditText lastName;
    private EditText firstName;
    private EditText phone;
    private EditText email;
    private EditText cap;
    private EditText postalAdress;
    private EditText birthday;
    private RadioGroup genreGroup;
    private RadioButton genre;
    private ImageView avatar;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    private String path;
    private int id;
    private int index = 0;

    private  ArrayList<Integer> listCheminImg = new ArrayList<>();
    private Intent returnIntent  = new Intent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setButtonCheck();
        setDate();
        setTakePicture();
        setImportPicture();
        setButtonSMS();

        setCarousell();

        id = R.mipmap.ic_launcher;
        path = null;
    }


    public void  setCarousell(){
        avatar = findViewById(R.id.Avatar);

        listCheminImg.add(R.mipmap.ic_launcher);
        listCheminImg.add(R.mipmap.skyrim);
        listCheminImg.add(R.mipmap.witcher);
        listCheminImg.add(R.mipmap.ciri);
        listCheminImg.add(R.mipmap.archer);

     Button buttonNext = findViewById(R.id.NextImg);

     buttonNext.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if(index + 1 == listCheminImg.size()){
                 index = 0;
                 id = listCheminImg.get(index);
             }
             else{
                 index++;
                 id = listCheminImg.get(index);
             }
             avatar.setImageResource(listCheminImg.get(index));
         }
     });

        Button buttonBefore = findViewById(R.id.BeforeImg);
        buttonBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index == 0){
                    index = listCheminImg.size() - 1;
                    id = listCheminImg.get(index);
                }
                else{
                    index--;
                    id = listCheminImg.get(index);
                }
                avatar.setImageResource(listCheminImg.get(index));
            }
        });
    }
    public void askPermissionsSMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else{
            sendSMS();
        }
    }
    public void sendSMS(){
        String phoneNo = phone.getText().toString();

        try {
            SmsManager smsManager = SmsManager.getDefault();

            smsManager.sendTextMessage(phoneNo, null, getString(R.string.smsSendMessage), null, null);
            Toast.makeText(getApplicationContext(), R.string.smsSend, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.smsFaild, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public void setButtonSMS(){
        Button button = findViewById(R.id.buttonValidationSMS);
        phone = findViewById(R.id.Phone);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pattern_num.matcher(phone.getText().toString()).matches()) {
                    askPermissionsSMS();
                }
                else{
                    phone.setText("");
                    AlertDialog.Builder alert = new AlertDialog.Builder(AjoutContactActivity.this);
                    alert.setTitle(R.string.Error);
                    alert.setMessage(R.string.ErrorPhone);
                    alert.create();
                    alert.show();
                }
            }
        });
    }


    public void setButtonCheck(){
        Button validation = findViewById(R.id.buttValidation);
        //Data
        lastName = findViewById(R.id.LastName);
        firstName = findViewById(R.id.FirstName);
        phone = findViewById(R.id.Phone);
        email = findViewById(R.id.Email);
        cap = findViewById(R.id.Cap);
        postalAdress = findViewById(R.id.PostalAdress);
        genreGroup = findViewById(R.id.groupRadioButton);


        validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !phone.getText().toString().isEmpty()) {
                    if(pattern_num.matcher(phone.getText().toString()).matches()){

                        if(pattern_email.matcher(email.getText().toString()).matches()){
                            genre = findViewById(genreGroup.getCheckedRadioButtonId());


                            returnIntent.putExtra("lastname",lastName.getText().toString());
                            returnIntent.putExtra("firstName",firstName.getText().toString());
                            returnIntent.putExtra("phone",phone.getText().toString());
                            returnIntent.putExtra("cap",cap.getText().toString());
                            returnIntent.putExtra("email",email.getText().toString());
                            returnIntent.putExtra("postalAdress",postalAdress.getText().toString());
                            returnIntent.putExtra("genre",genre.getText().toString());
                            returnIntent.putExtra("birthday",birthday.getText().toString());


                            if(id != -1){ // from de gallery et take photo
                                returnIntent.putExtra("avatar", String.valueOf(id));
                            }
                            else{
                                returnIntent.putExtra("avatar", path);
                            }

                            setResult(Activity.RESULT_OK,returnIntent);
                            finish();
                        }
                        else{
                            email.setText("");
                            AlertDialog.Builder alert = new AlertDialog.Builder(AjoutContactActivity.this);
                            alert.setTitle(R.string.Error);
                            alert.setMessage(R.string.ErrorEmail);
                            alert.create();
                            alert.show();
                        }
                    }
                    else {
                        phone.setText("");
                        AlertDialog.Builder alert = new AlertDialog.Builder(AjoutContactActivity.this);
                        alert.setTitle(R.string.Error);
                        alert.setMessage(R.string.ErrorPhone);
                        alert.create();
                        alert.show();
                    }
                }
                else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(AjoutContactActivity.this);
                    alert.setTitle(R.string.Error);
                    alert.setMessage(R.string.ErrorEmpty);
                    alert.create();
                    alert.show();
                }
            }
        });
    }


    public void setDate(){
        birthday = findViewById(R.id.Birthday);

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AjoutContactActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                birthday.setText(date);
            }
        };
    }

    public void setTakePicture(){
        Button button = findViewById(R.id.takePicture);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(takePictureIntent, 1);
                    setResult(Activity.RESULT_OK, takePictureIntent);
                } catch (ActivityNotFoundException e) {
                    Log.e("Take picture",e.getMessage());
                }

            }
        });
    }

    public void setImportPicture(){
        Button button = findViewById(R.id.importPicture);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (ActivityCompat.checkSelfPermission(AjoutContactActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AjoutContactActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    }
                    else {
                        try{
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent,2);
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Seul les images < 32 MP", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, imageFileName, null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK) {
                    if (data != null && data.getExtras() != null) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        Uri tempUri = getImageUri(AjoutContactActivity.this, photo);
                        File finalFile = new File(getRealPathFromURI(tempUri));
                        path = String.valueOf(finalFile);

                        if(finalFile.exists()){
                            Bitmap myBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                            avatar.setImageBitmap(myBitmap);
                        }
                        id = -1; // pour le sql
                    }
                    else{
                        Log.e("result","photo erreur");
                    }
                }
                break;

            case 2:
                if (resultCode == RESULT_OK) {
                   id = -1;

                    final Uri imageUri = data.getData();
                    path = imageUri.getPath();
                    path = path.substring(5);

                    File imgFile = new  File(path);

                    if(imgFile.exists()){
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        avatar.setImageBitmap(myBitmap);
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSMS();
            }
            else {
                Toast.makeText(getApplicationContext(), R.string.smsNotSend, Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == PICK_FROM_GALLERY) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,2);
            }
            else {
                Toast.makeText(getApplicationContext(), "erreur", Toast.LENGTH_LONG).show();
            }
        }
    }
}
