package edu.temple.assignment7;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String title;
    private String author;
    private int id;
    private String cover_url;
    private int duration;
    public Book()
    {
    };

    public Book(String title, String author, int id, String coverURL, int duration)
    {
        this.title = title;
        this.author = author;
        this.id = id;
        this.cover_url = coverURL;
        this.duration = duration;
    }

    protected Book(Parcel in) {
        title = in.readString();
        author = in.readString();
        id = in.readInt();
        cover_url = in.readString();
        duration = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getTitle()
    {
        return title;
    }

    public String getAuthor()
    {
        return author;
    }

    public String setTitle()
    {
        return this.title = title;
    }

    public String setAuthor()
    {
        return this.author = author;
    }

    public int getID()
    {
        return id;
    }

    public int setID()
    {
        return this.id = id;
    }

    public String getCoverURL()
    {
        return cover_url;
    }

    public String setCoverURL()
    {
        return this.cover_url = cover_url;
    }

    public int getDuration()
    {
        return duration;
    }

    public int setDuration()
    {
        return this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeInt(id);
        dest.writeString(cover_url);
        dest.writeInt(duration);
    }
}
