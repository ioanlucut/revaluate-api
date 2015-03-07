package com.revaluate.core.views;

//-----------------------------------------------------------------
// User JACKSON views - used for filtering what to be sent back to the user
//-----------------------------------------------------------------

public class Views {

    /**
     * Simple view of the user.
     */
    public static class StrictView {
    }

    /**
     * Detail view of the user
     */
    public static class DetailsView extends StrictView {
    }
}