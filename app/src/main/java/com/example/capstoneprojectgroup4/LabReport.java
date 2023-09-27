package com.example.capstoneprojectgroup4;
import android.net.Uri;

class LabReport
{
    private String title;
    private Uri imageUri;

    public LabReport(String title, Uri imageUri)
    {
        this.title = title;
        this.imageUri = imageUri;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Uri getImageUri()
    {
        return imageUri;
    }

    public void setImageUri(Uri imageUri)
    {
        this.imageUri = imageUri;
    }
}
