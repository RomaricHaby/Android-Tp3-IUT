package Tp4.TP;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.util.List;


public class ContactAdapter extends ArrayAdapter<Contact>
{

    public ContactAdapter(Context context, int resource, List<Contact> contactList)
    {
        super(context,resource, contactList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Contact contact = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_cell, parent, false);
        }

        TextView firstname = (TextView) convertView.findViewById(R.id.ContactFistName);
        ImageView avatar = (ImageView) convertView.findViewById(R.id.AvatarContact);

        firstname.setText(contact.getFirstName());

        if(contact.getImage() != -1){
            avatar.setImageResource(contact.getImage());
        }
        else{
            File imgFile = new  File(contact.getPathImg());

            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                avatar.setImageBitmap(myBitmap);
            }
        }
        return convertView;
    }
}
