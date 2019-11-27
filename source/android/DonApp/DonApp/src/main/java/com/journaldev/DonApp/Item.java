package com.journaldev.DonApp;

/** \brief This is a class for details of item

 */
public class Item {

    // Store the id of the  movie poster
    private int mImageDrawable;
    // Store the name of the movie
    private String mName;
    // Store the release date of the movie
    private String mRelease;

    // Constructor that is used to create an instance of the Item object
    /** Constructor that is used to create an instance of the Item object
     @param mImageDrawable: Locally stored image
     @param mName: Name of Item
     @param mRelease: Available at place where item is available
     */
    public Item(int mImageDrawable, String mName, String mRelease) {
        this.mImageDrawable = mImageDrawable;
        this.mName = mName;
        this.mRelease = mRelease;
    }
    /** A function that returns drawable image
     @return Drawable Image

     */
    public int getmImageDrawable() {
        return mImageDrawable;
    }

    /**Function to return name
     @return It returns the name of item
     */
    public String getmName() {
        return mName;
    }

    /**Function to return the place where object is present
     @return It returns the name of the place
     */
    public String getmRelease() {
        return mRelease;
    }

}